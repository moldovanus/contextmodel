package utils.negotiator.impl;

import model.interfaces.resources.CPU;
import model.interfaces.resources.Core;
import model.interfaces.resources.MEM;
import model.interfaces.resources.ServiceCenterServer;
import model.interfaces.resources.applications.ApplicationActivity;
import utils.negotiator.Negotiator;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Apr 19, 2010
 * Time: 6:36:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class NashNegotiator implements Negotiator {

    private int quality = 10;

    public double negotiateGivenRanges(int minValue1, int maxValue1, int minValue2, int maxValue2, int optimalValue1, int optimalValue2) {
        int increment1 = (maxValue1 - minValue1) / quality;   //task
        int increment2 = (maxValue2 - minValue2) / quality;        //server

        if (minValue1 < minValue2) minValue1 = maxValue2;
        else minValue2 = minValue1;

        boolean[][] maxUtilities1 = new boolean[quality][quality];
        boolean[][] maxUtilities2 = new boolean[quality][quality];
        double utilities1[][] = new double[quality][quality];
        double utilities2[][] = new double[quality][quality];
        for (int i = 0; i < quality; i++) {
            int currentValue1 = i * increment1 + minValue1;
            double max = 0;
            int pos = 0;
            for (int j = 0; j < quality; j++) {
                int currentValue2 = j * increment2 + minValue2;

                double penalty1 = 1 / (currentValue2 - currentValue1 + 0.00001);
                double penalty2 = (currentValue2 - currentValue1);
                if (penalty1 < 0) {
                    penalty1 *= (-1);
                }
                if (penalty2 < 0) {
                    penalty2 *= (-1);
                }
                double reward1 = (currentValue1 - minValue1) / (maxValue1 - minValue1 + 1.00001);
                double reward2 = (currentValue2 - maxValue2) / (maxValue2 - minValue2 + 1.00001);
                utilities1[i][j] = reward1 * penalty1;
                utilities2[i][j] = reward2 * penalty2;
                /*
              double penalty1 = 1/(currentValue2 - currentValue1+0.00001);
              double penalty2 = (currentValue2 - currentValue1) ;
                 if (penalty1 < 0) {
                  penalty1 *= (-1);
              }
              if (penalty2 < 0) {
                  penalty2 *= (-1);
              }
              utilities1[i][j] = Math.exp(currentValue1/increment1) *penalty1;
              utilities2[i][j] = -Math.log(currentValue2/increment2) *penalty2;
                */

                System.out.print(utilities1[i][j] + " ");
                if (j == 0) {
                    max = utilities1[i][j];
                }
                if (max < utilities1[i][j]) {
                    max = utilities1[i][j];
                    pos = j;
                }
            }
            System.out.println("\n");
            maxUtilities1[i][pos] = true;
        }
        System.out.println("\n");
        for (int j = 0; j < quality; j++) {
            double max = 0;
            int pos = 0;
            for (int i = 0; i < quality; i++) {
                System.out.print(utilities2[i][j] + " ");
                if (i == 0) {
                    max = utilities2[i][j];
                }
                if (max < utilities2[i][j]) {
                    max = utilities2[i][j];
                    pos = i;
                }
            }
            System.out.println("\n");
            maxUtilities2[pos][j] = true;

        }
        double x = 0;
        double maxutility = -50000000;
        int p = 0;
        int q = 0;
        for (int i = 0; i < quality; i++) {
            for (int j = 0; j < quality; j++) {
                if (maxUtilities1[i][j] && maxUtilities2[i][j]) {
                    int currentValue1 = i * increment1 + minValue1;
                    int currentValue2 = j * increment2 + minValue2;
                    if (((utilities1[i][j] + utilities2[i][j]) / 2.0) > maxutility) {
                        p = i;
                        q = j;
                        x = (double) (currentValue1 + currentValue2) / 2;
                        maxutility = (utilities1[i][j] + utilities2[i][j]) / 2.0;
                    }
                    //TODO : if not equal, optimize function :D
                    System.out.println("Equilibrium Found!!!" + "(" + i + "," + j + ") negotiatedValue: " + (currentValue1 + currentValue2) / 2 + " utility1 " + utilities1[i][j] + "utility2  " + utilities2[i][j]);

                    if ((i == 0) && (j == 0) && (x < currentValue1)) x = currentValue1;

                }
            }
        }
        if (x > maxValue2) return maxValue2;
        System.out.println("Returned " + p + " " + q + "   value:" + x);
        return (x);

    }

    public Map<String, Double> negotiate(ServiceCenterServer server, ApplicationActivity task) {
        double negotiatedCpu = 0.0d;
        double negotiatedStorage = 0.0d;
        double negotiatedMemory = 0.0d;
        Map<String, Double> negotiatedValues = new HashMap<String, Double>();
        /***Memory*********************************/
        if (task.getMemRequiredMinValue() > task.getMemAllocatedValue()) {
            MEM memory = server.getMemResources().iterator().next();
            double memMaxAcceptable = (memory.getMaximumWorkLoad() + memory.getOptimalWorkLoad()) / 2.0;
            double memMinAcceptable = memory.getOptimalWorkLoad() / 2.0;
            if (memMaxAcceptable - (memory.getCurrentWorkLoad() + task.getMemRequiredMaxValue()) < 0) {
                int optimalValueForServer = (int) (memMaxAcceptable - memory.getCurrentWorkLoad());
                if (optimalValueForServer == 0) {
                    optimalValueForServer++;
                }
                negotiatedMemory = negotiateGivenRanges((int) task.getMemRequiredMinValue(), (int) task.getMemRequiredMaxValue(), 0,
                        (int) task.getMemRequiredMinValue(), (int) (memory.getMaximumWorkLoad() - memory.getCurrentWorkLoad()), (int) optimalValueForServer);
                if ((negotiatedCpu + optimalValueForServer) > (memory.getMaximumWorkLoad() - memory.getCurrentWorkLoad()))
                    negotiatedValues.put(NEGOTIATED_MEMORY, (double) (memory.getMaximumWorkLoad() - memory.getCurrentWorkLoad()));
                else
                    negotiatedValues.put(NEGOTIATED_MEMORY, (negotiatedMemory + optimalValueForServer));
            }  else negotiatedValues.put(NEGOTIATED_MEMORY, (double)task.getMemRequiredMaxValue());
        } else negotiatedValues.put(NEGOTIATED_MEMORY, (double)task.getMemRequiredMaxValue());
//        /***Storage*******************************/

//        if (reqTask.getStorageMinAcceptableValue() > task.getReceivedInfo().getStorageReceived())
//            if (server.getAssociatedStorage().getMaxAcceptableValue() - (server.getAssociatedStorage().getUsed() + reqTask.getStorageMinAcceptableValue()) < 0) {
//                int optimalValueForServer = server.getAssociatedStorage().getMaxAcceptableValue() - server.getAssociatedStorage().getUsed();
//                if (optimalValueForServer == 0) {
//                    optimalValueForServer++;
//                }
//                negotiatedStorage = negotiateGivenRanges(reqTask.getStorageMinAcceptableValue(), reqTask.getStorageMaxAcceptableValue(), 0,
//                        server.getAssociatedStorage().getTotal() - server.getAssociatedStorage().getUsed(),
//                        reqTask.getStorageMinAcceptableValue(), optimalValueForServer);
//                if ((negotiatedCpu + optimalValueForServer) > (server.getAssociatedStorage().getTotal() - server.getAssociatedStorage().getUsed()))
//                    negotiatedValues.put(NEGOTIATED_STORAGE, (double) (server.getAssociatedStorage().getTotal() - server.getAssociatedStorage().getUsed()));
//                else
//                    negotiatedValues.put(NEGOTIATED_STORAGE, (negotiatedStorage + optimalValueForServer));
//            }
        /***Cpu*******************************/
        Collection<Core> cores = ((CPU) server.getCpuResources().iterator().next()).getAssociatedCores();
        Core core = cores.iterator().next();
        if (task.getCpuRequiredMinValue() > task.getCpuAllocatedValue()) {
            double maxAcceptable = (core.getMaximumWorkLoad() + core.getOptimalWorkLoad()) / 2.0;

            if (maxAcceptable - (core.getCurrentWorkLoad() + task.getCpuRequiredMaxValue()) < 0) {
                int optimalValueForServer = (int) (maxAcceptable - core.getCurrentWorkLoad());
                if (optimalValueForServer == 0) {
                    optimalValueForServer++;
                }
                negotiatedCpu = negotiateGivenRanges((int) (optimalValueForServer - task.getCpuRequiredMinValue()), (int) (task.getCpuRequiredMaxValue() - optimalValueForServer),
                        0, (int) (core.getMaximumWorkLoad() - core.getCurrentWorkLoad() - optimalValueForServer), 0, 0);
                System.out.println("Negotiated value " + (negotiatedCpu + optimalValueForServer));
                if ((negotiatedCpu + optimalValueForServer) > (core.getMaximumWorkLoad() - core.getCurrentWorkLoad()))
                    negotiatedValues.put(NEGOTIATED_CPU, (double) (core.getMaximumWorkLoad() - core.getCurrentWorkLoad()));
                else
                    negotiatedValues.put(NEGOTIATED_CPU, (negotiatedCpu + optimalValueForServer));
            }else
            negotiatedValues.put(NEGOTIATED_CPU, (double)task.getCpuRequiredMaxValue());
        } else
            negotiatedValues.put(NEGOTIATED_CPU, (double)task.getCpuRequiredMaxValue());

        return negotiatedValues;
    }
}
