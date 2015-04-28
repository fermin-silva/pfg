package es.uned.pfg.ae.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class GraficadorCurvas {

	private final String titulo;
	private final String nombreEje;

	private Map<String, XYSeries> series;

	private int maxIteracion;


	public GraficadorCurvas(String titulo, String nombreEje,
							int maxIteraciones)
	{
		this.series = new LinkedHashMap<String, XYSeries>();

		this.maxIteracion = maxIteraciones;
		this.titulo = titulo;
		this.nombreEje = nombreEje;
	}

	public void agregar(String nombre, List<Double> progreso) {
		int i = 0;

		for (Double d : progreso) {
			agregar(nombre, i++, d);
		}
	}

	public void agregar(String nombre, int iteracion, double fitness) {
		nombre = nombre.replace("Recombinacion", "");

		XYSeries serie = this.series.get(nombre);

		if (serie == null) {
			serie = new XYSeries(nombre);
			series.put(nombre, serie);
		}

		serie.add(iteracion, fitness);
	}
	
	public void guardar(String archivo, int ancho, int alto) {
		try {
			archivo = archivo.replace(' ', '_');

			System.out.println("Guardando el archivo en " +
					new File(archivo).getAbsolutePath());

			//doble de escala (2, 2) para mejorar DPI y aliasing en Retina
			ChartUtilities.writeScaledChartAsPNG(new FileOutputStream(archivo),
												 getChart(), ancho, alto, 2, 2);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public JFreeChart getChart() {
		final XYSeriesCollection dataset = new XYSeriesCollection();

		for (XYSeries serie : series.values()) {
			dataset.addSeries(serie);
		}

		JFreeChart chart = ChartFactory.createXYLineChart(
				titulo,
				"Iteracion",           // x axis label
				nombreEje,             // y axis label
				dataset,
				PlotOrientation.VERTICAL,
				true,                     // incluir leyenda
				true,                     // tooltips
				false                     // urls
		);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.getDomainAxis().setRange(0, maxIteracion);

		plot.setOutlineVisible(false);
		plot.setBackgroundPaint(new Color(247, 247, 247));
		plot.setDomainGridlinePaint(Color.WHITE);
		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinePaint(Color.WHITE);
//		plot.setRangeGridlineStroke(new BasicStroke(1));

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setBaseShapesVisible(false);

		for (int i = 0; i < 10; i++) {
			renderer.setSeriesStroke(i, new BasicStroke(2));
		}

		renderer.setSeriesPaint(0, new Color(226, 34, 34, 180));
		renderer.setSeriesPaint(1, new Color(83, 152, 218, 180));
		renderer.setSeriesPaint(2, new Color(246, 162, 54, 180));
		renderer.setSeriesPaint(3, new Color(96, 189, 104, 180));
		renderer.setSeriesPaint(4, new Color(171, 107, 235, 180));
		renderer.setSeriesPaint(5, new Color(178, 145, 47, 180));
		renderer.setSeriesPaint(6, new Color(222, 207, 63, 180));
		renderer.setSeriesPaint(7, new Color(77, 77, 77, 180));

		plot.setRenderer(renderer);

		return chart;
	}

	public void show() {
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(1280, 1280));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(new ChartPanel(getChart(), false), BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	//TODO eliminar este main de prueba
	public static void main(String[] args) {
		GraficadorCurvas graf = new GraficadorCurvas("Prueba", "y", 300);
		Random rnd = new Random();

		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 100; i++) {
				graf.agregar("funcion" + j, i, rnd.nextInt(300));
			}
		}

		graf.show();
	}
}

