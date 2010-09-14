package gui.resourceMonitor.resourceMonitorPlotter.impl;

import gui.resourceMonitor.resourceMonitorPlotter.ResourceMonitorPlotter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: May 15, 2010
 * Time: 5:18:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceMonitorBarChartPlotter extends ResourceMonitorPlotter {

    private XYSeries series;
    private CategoryPlot plot;
    private String xAxisLabel;
    private DefaultCategoryDataset dataset;

    public ResourceMonitorBarChartPlotter(String resourceName, String xAxisLabel, int minimumValue, int maximumValue) {
        super(resourceName);

        this.xAxisLabel = xAxisLabel;
        series = new XYSeries(xAxisLabel);
        setup(minimumValue, maximumValue);
    }


    @Override
    protected void setup(int minimumValue, int maximumValue) {

        dataset = new DefaultCategoryDataset();
        dataset.addValue(0, xAxisLabel, "");
        JFreeChart chart = ChartFactory.createBarChart(
                "",  // Title
                xAxisLabel,    // X-Axis label
                "",    // Y-Axis label
                dataset,     // Dataset
                PlotOrientation.VERTICAL,
                false,                // Show legend
                false, false
        );

        CategoryPlot plot = chart.getCategoryPlot();

        plot.setBackgroundPaint(Color.BLACK);

        ValueAxis yAxis = plot.getRangeAxis();
        yAxis.setLabelFont(LABEL_FONT);
        yAxis.setRange(minimumValue, maximumValue);

        CategoryAxis xAxis = plot.getDomainAxis();
        xAxis.setLabelFont(LABEL_FONT);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setPaint(Color.GREEN);
        ChartPanel chartPanel = new ChartPanel(chart);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);

        graphPanel = panel;

    }

    public void setCurrentValue(Object currentValue) {
        dataset.setValue((Integer) currentValue, xAxisLabel, "");
    }

}
