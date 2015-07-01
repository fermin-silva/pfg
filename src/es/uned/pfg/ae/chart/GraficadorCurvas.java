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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Clase generadora de graficos de curvas. Proporciona una interfaz a alto nivel
 * por sobre JFreeChart. <br>
 * Se utiliza principalmente para generar graficos con la curva de progreso y
 * curva de convergencia para cada una de las recombinaciones implementadas.
 *
 * @author Fermin Silva
 */
public class GraficadorCurvas {

	private final String titulo;
	private final String nombreEje;

	/**
	 * mapa de nombre de recombinacion a estructura de datos que almacena
	 * la lista de puntos para formar la curva
	 */
	private Map<String, XYSeries> series;

	/**
	 * se especifica la maxima generacion/iteracion para poder terminar el eje
	 * de las x alli y que este no sea ni mas corto ni mas largo
	 */
	private int maxIteracion;
	private boolean mostrarLeyenda = true;


	public GraficadorCurvas(String titulo, String nombreEje,
							int maxIteraciones)
	{
		this.series = new LinkedHashMap<String, XYSeries>();

		this.maxIteracion = maxIteraciones;
		this.titulo = titulo;
		this.nombreEje = nombreEje;
	}

	public void setMostrarLeyenda(boolean mostrarLeyenda) {
		this.mostrarLeyenda = mostrarLeyenda;
	}

	/**
	 * Agrega la curva (lista de puntos) a la estructura de datos identificada
	 * por el nombre (por ejemplo de la recombinacion)
	 */
	public void agregar(String nombre, List<Double> progreso) {
		int i = 0;

		for (Double d : progreso) {
			agregar(nombre, i++, d);
		}
	}

	/**
	 *
	 * @param nombre Nombre del conjunto de datos (por ejemplo de la recombinacion)
	 * @param iteracion Numero de iteracion para el eje de las X
	 * @param fitness Valor a graficar en el eje Y
	 */
	public void agregar(String nombre, int iteracion, double fitness) {
		nombre = nombre.replace("Recombinacion", "");

		//obtener la serie de datos del mapa
		XYSeries serie = this.series.get(nombre);

		//si es nula, es la primera vez que se inserta un valor en dicha serie
		//por lo que debe ser creada
		if (serie == null) {
			serie = new XYSeries(nombre);
			series.put(nombre, serie);
		}

		serie.add(iteracion, fitness);
	}

	/**
	 * Almacena el grafico generado por esta misma clase en un archivo con el
	 * doble de las dimensiones especificadas. Se utiliza un escalado doble
	 * para aumentar la calidad del grafico si este luego se achica a la mitad.
	 */
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

	/**
	 * Crea un objeto de JFreeChart con el grafico, configurando los colores
	 * y los elementos del grafico. Este objeto puede ser luego almacenado en
	 * disco o devuelto por un Stream.
	 */
	public JFreeChart getChart() {
		final XYSeriesCollection dataset = new XYSeriesCollection();

		for (XYSeries serie : series.values()) {
			dataset.addSeries(serie);
		}

		JFreeChart chart = ChartFactory.createXYLineChart(
				titulo,
				"Iteracion",           // etiqueta del eje x
				nombreEje,             // etiqueta del eje y
				dataset,
				PlotOrientation.VERTICAL,
				true,                  // incluir leyenda
				true,                  // tooltips
				false                  // urls
		);

		chart.setBackgroundPaint(Color.white);
		chart.setAntiAlias(true);

		XYPlot plot = chart.getXYPlot();

		plot.getDomainAxis().setRange(0, maxIteracion);

		plot.setOutlineVisible(false);
		plot.setBackgroundPaint(new Color(247, 247, 247));
		plot.setDomainGridlinePaint(Color.WHITE);
		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinePaint(Color.WHITE);

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setBaseShapesVisible(false);

		for (int i = 0; i < 10; i++) {
			renderer.setSeriesStroke(i, new BasicStroke(2));
		}

		//color para cada una de las lineas
		renderer.setSeriesPaint(0, new Color(44, 62, 80, 200));
		renderer.setSeriesPaint(1, new Color(226, 34, 34, 200));
		renderer.setSeriesPaint(2, new Color(246, 162, 54, 200));
		renderer.setSeriesPaint(3, new Color(96, 189, 104, 200));
		renderer.setSeriesPaint(4, new Color(171, 107, 235, 200));
		renderer.setSeriesPaint(5, new Color(178, 145, 47, 200));
		renderer.setSeriesPaint(6, new Color(222, 207, 63, 200));
		renderer.setSeriesPaint(7, new Color(77, 77, 77, 200));

		renderer.setBaseSeriesVisibleInLegend(this.mostrarLeyenda);

		plot.setRenderer(renderer);

		return chart;
	}
}

