package es.uned.pfg.ae.chart;

import es.uned.pfg.ae.ResultadoBenchmark;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.MiBoxAndWhiskerRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

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

        List<Double> list = resultado.getFitness();
        Collections.sort(list);

        System.out.println(nombre + "  " + list);

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

        renderer.setSeriesPaint(0, new Color(226, 34, 34));
        renderer.setSeriesPaint(1, new Color(83, 152, 218));
        renderer.setSeriesPaint(2, new Color(246, 162, 54));
        renderer.setSeriesPaint(3, new Color(96, 189, 104));
        renderer.setSeriesPaint(4, new Color(171, 107, 235));
        renderer.setSeriesPaint(5, new Color(178, 145, 47));
        renderer.setSeriesPaint(6, new Color(222, 207, 63));
        renderer.setSeriesPaint(7, new Color(77, 77, 77));


        renderer.setWhiskerWidth(0.4);
        renderer.setMedianWidth(6);

        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        plot.setOutlineVisible(false);
        plot.setBackgroundPaint(new Color(247, 247, 247));
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinePaint(Color.WHITE);

        JFreeChart chart = new JFreeChart("Benchmark " + titulo, plot);

        return chart;
    }

    public void guardar(String archivo, int ancho, int alto) {
        try {
            archivo = archivo.replace(' ', '_');

            System.out.println("Guardando el archivo en " +
                                new File(archivo).getAbsolutePath());

            ChartUtilities.saveChartAsPNG(new File(archivo), crearPlot(),
                                          ancho, alto);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
