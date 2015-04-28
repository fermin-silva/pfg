package es.uned.pfg.ae.chart;

import es.uned.pfg.ae.ResultadoBenchmark;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.SunPNGEncoderAdapter;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.MiBoxAndWhiskerRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
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

//        List<Double> list = resultado.getFitness();
//        Collections.sort(list);
//
//        System.out.println(nombre + "  " + list);

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

        NumberAxis yAxis = new NumberAxis("Fitness");

        MiBoxAndWhiskerRenderer renderer = new MiBoxAndWhiskerRenderer();
        renderer.setMaximumBarWidth(0.3);
        renderer.setShowOutliers(false);

        renderer.setSeriesPaint(0, new Color(226, 34, 34, 180));
        renderer.setSeriesPaint(1, new Color(83, 152, 218, 180));
        renderer.setSeriesPaint(2, new Color(246, 162, 54, 180));
        renderer.setSeriesPaint(3, new Color(96, 189, 104, 180));
        renderer.setSeriesPaint(4, new Color(171, 107, 235, 180));
        renderer.setSeriesPaint(5, new Color(178, 145, 47, 180));
        renderer.setSeriesPaint(6, new Color(222, 207, 63, 180));
        renderer.setSeriesPaint(7, new Color(77, 77, 77, 180));

        
        renderer.setWhiskerWidth(0.4);
        renderer.setMedianWidth(6);

        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        plot.setOutlineVisible(false);
        plot.setBackgroundPaint(new Color(247, 247, 247));
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinePaint(Color.WHITE);
//        plot.setRangeGridlineStroke(new BasicStroke(1));

        JFreeChart chart = new JFreeChart("Benchmark " + titulo, plot);
        chart.setBackgroundPaint(Color.white);

        return chart;
    }

    public void guardar(String archivo, int ancho, int alto) {
        try {
            archivo = archivo.replace(' ', '_');

            System.out.println("Guardando el archivo en " +
                                new File(archivo).getAbsolutePath());

            //doble de escala (2, 2) para mejorar DPI y aliasing en Retina
            ChartUtilities.writeScaledChartAsPNG(new FileOutputStream(archivo),
                                                 crearPlot(), ancho, alto, 2, 2);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
