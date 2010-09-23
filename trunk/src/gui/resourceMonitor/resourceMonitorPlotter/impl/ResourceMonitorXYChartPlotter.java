package gui.resourceMonitor.resourceMonitorPlotter.impl;

import gui.resourceMonitor.resourceMonitorPlotter.ResourceMonitorPlotter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: May 15, 2010
 * Time: 5:18:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceMonitorXYChartPlotter extends ResourceMonitorPlotter {

    private XYSeries series;
    private XYPlot plot;
    private String xAxisLabel;
    private String yAxisLabel;
    private JScrollBar scrollBar;
    private boolean autoAdjustRange = true;

    public ResourceMonitorXYChartPlotter(String resourceName, String xAxisLabel, String yAxisLabel, int minimumValue, int maximumValue) {
        super(resourceName);

        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        series = new XYSeries(xAxisLabel);
        setup(minimumValue, maximumValue);
    }


    @Override
    protected void setup(int minimumValue, int maximumValue) {
        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                resourceName,  // Title
                xAxisLabel,    // X-Axis label
                yAxisLabel,    // Y-Axis label
                xyDataset,     // Dataset
                PlotOrientation.VERTICAL,
                false,                // Show legend
                false, false
        );


        plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.BLACK);
        XYItemRenderer renderer = plot.getRenderer();
        renderer.setPaint(Color.GREEN);
        renderer.setStroke(new BasicStroke(3));
        
        ValueAxis yAxis = plot.getRangeAxis();
        yAxis.setLabelFont(LABEL_FONT);
        yAxis.setRange(minimumValue, maximumValue);

        ValueAxis xAxis = plot.getDomainAxis();
        xAxis.setLabelFont(LABEL_FONT);
        xAxis.setRange(minTimeRange, maxTimeRange);



        ChartPanel chartPanel = new ChartPanel(chart);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);
        scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);

        scrollBar.setMinimum(minTimeRange);
        scrollBar.setMaximum(maxTimeRange);

        scrollBar.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                int adjustment = scrollBar.getValue();
                if (adjustment < minTimeRange && adjustment > 0) {
                    autoAdjustRange = false;
                } else {
                    autoAdjustRange = true;
                }
            }

        });
        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int adjustment = e.getValue();
                if (adjustment > 0) {
                    ValueAxis domain = plot.getDomainAxis();
                    domain.setRange(adjustment - maxTimeRange, adjustment);
                }
            }
        });

        panel.add(scrollBar, "South");
        panel.repaint();
        scrollBar.repaint();
        graphPanel = panel;

    }

    @Override
    public void setMaxTimeRange(int maxTimeRange) {
        super.setMaxTimeRange(maxTimeRange);
        ValueAxis domain = plot.getDomainAxis();
        domain.setRange(minTimeRange, minTimeRange + maxTimeRange);
        scrollBar.setMaximum(minTimeRange + maxTimeRange);
    }

    public void setCurrentValue(Object currentValue) {
        if (snapshotCount > maxTimeRange - 1 && autoAdjustRange) {
            ValueAxis domain = plot.getDomainAxis();
            // domain.setAutoRange(false);
            minTimeRange += snapshotIncrement;
            domain.setRange(minTimeRange, minTimeRange + maxTimeRange);
        }
        ValueAxis value = plot.getRangeAxis();
        Range valueRange = value.getRange();
        if (valueRange.getUpperBound() <= (Integer) currentValue) {
            value.setRange(valueRange.getLowerBound(), (Integer) currentValue + 10);
        }
        series.add(snapshotCount, (Integer) currentValue);
        snapshotCount += snapshotIncrement;
        scrollBar.repaint();
        scrollBar.setMaximum(scrollBar.getMaximum() + 1);
//        if (scrollBar.getValue() >= maxTimeRange + minTimeRange) {
//            ValueAxis domain = plot.getDomainAxis();
//            domain.setRange(minTimeRange, minTimeRange + maxTimeRange);
//        }
    }

}