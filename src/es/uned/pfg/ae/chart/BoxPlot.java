package es.uned.pfg.ae.chart;

import es.uned.pfg.ae.ResultadoBenchmark;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.MiBoxAndWhiskerRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Fermin Silva < fermins@olx.com >
 */
public class BoxPlot {

    private DefaultBoxAndWhiskerCategoryDataset dataset;
    private String titulo;

    public BoxPlot() {
        this.dataset = new DefaultBoxAndWhiskerCategoryDataset();
    }

    public void agregar(ResultadoBenchmark resultado) {
        String nombre = resultado.getRecombinacion().toString()
                                 .replace("Recombinacion", "");

        String categoria = resultado.getFuncion().toString()
                                    .replace("Funcion", "");

        dataset.add(resultado.getFitness(), nombre, categoria);

        if (titulo == null) {
            titulo = categoria;
        }
    }

    public void agregar(List<Double> data, String rowLabel, String colLabel) {
        dataset.add(data, rowLabel, colLabel);
    }

    public JFreeChart crearPlot() {
        CategoryAxis xAxis = new CategoryAxis("Category");
        xAxis.setVisible(false);

        NumberAxis yAxis = new NumberAxis("Value");

        MiBoxAndWhiskerRenderer renderer = new MiBoxAndWhiskerRenderer();
        renderer.setMaximumBarWidth(0.3);
        renderer.setShowOutliers(false);
        renderer.setSeriesPaint(0, new Color(38, 197, 17));
        renderer.setSeriesPaint(1, new Color(42, 57, 255));
        renderer.setSeriesPaint(2, new Color(244, 53, 45));
        renderer.setSeriesPaint(3, new Color(28, 135, 224));
        renderer.setSeriesPaint(4, new Color(187, 71, 230));
        renderer.setSeriesPaint(5, new Color(255, 108, 0));
//        renderer.setSeriesOutlinePaint(2, new Color(255, 0, 0));
        renderer.setWhiskerWidth(0.4);
        renderer.setMedianWidth(6);
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        JFreeChart chart = new JFreeChart("Benchmark " + titulo, plot);

        return chart;
    }

    //TODO metodo para salvar el plot como imagen al disco

    public void show() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new ChartPanel(crearPlot()), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        BoxPlot boxPlot = new BoxPlot();
        Random random = new Random();

        List<Double> data1 = new LinkedList<Double>();
        for (int i = 0; i < 100; i++) {
            data1.add(random.nextGaussian() * 5);
        }

        boxPlot.agregar(data1, "data1", "cat1");

        data1 = new LinkedList<Double>();
        for (int i = 0; i < 100; i++) {
            data1.add(random.nextGaussian() * 3 + 1);
        }

        boxPlot.agregar(data1, "data2", "cat1");

        data1 = new LinkedList<Double>();
        for (int i = 0; i < 100; i++) {
            data1.add(random.nextDouble() * 5);
        }

        boxPlot.agregar(data1, "data3", "cat1");

        boxPlot.show();
    }
}
