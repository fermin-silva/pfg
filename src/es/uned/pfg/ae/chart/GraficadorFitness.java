package es.uned.pfg.ae.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class GraficadorFitness {

	private XYSeries series;
	private XYSeriesCollection coleccion;
	private JFreeChart chart;
	
	private int maxIteracion;
	
	
	public GraficadorFitness(int maxIteraciones) {
		this.series = new XYSeries("Fitness");
		
		this.coleccion = new XYSeriesCollection(series);
		this.maxIteracion = maxIteraciones;
		
		configurar();
		agregar(-1, 0);
	}
	
	public void agregar(int iteracion, double fitness) {
		series.add(iteracion, fitness);
	}
	
	public void configurar() {
		chart = ChartFactory.createXYAreaChart("Curva Progreso", "Iteracion", 
											   "Fitness", coleccion);
		
		XYPlot plot = (XYPlot) chart.getPlot();
		
		plot.getDomainAxis().setRange(0, maxIteracion);
		
		//ademas de quedar la coleccion en 0, se vuelve a poner en el indice 1
		//esto genera que se dibuje 2 veces, la primera para el area y la 2da
		//para la linea con figuras encima
		plot.setDataset(1, coleccion);
		
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.DARK_GRAY);
        
		plot.setDomainCrosshairVisible(false);
        plot.setDomainGridlinesVisible(false);

        plot.setRangeCrosshairVisible(false);
        plot.setDomainGridlinesVisible(false);

        plot.setBackgroundPaint(Color.WHITE);
        plot.setBackgroundAlpha(1f);
        plot.setForegroundAlpha(0.6f);
        
        //la primera vez que se dibuja la coleccion es de esta forma
        plot.getRenderer(0).setSeriesPaint(0, Color.GRAY);
        plot.getRenderer(0).setSeriesVisibleInLegend(0, false);
        
        //la segunda vez que se dibuja es con el line renderer
        plot.setRenderer(1, getLineRenderer());
	}
	
	private XYLineAndShapeRenderer getLineRenderer() {
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, true);	
		
		renderer.setSeriesShape(0, new Rectangle(-1, -1, 2, 2));
		renderer.setSeriesPaint(0, Color.DARK_GRAY);
		renderer.setSeriesVisibleInLegend(0, false);
		
		return renderer;
	}
	
	public void salvar(String file, int ancho, int alto) throws IOException {
		ChartUtilities.saveChartAsPNG(new File(file), getChart(), ancho, alto);
	}

	public BufferedImage getImagen(int ancho, int alto) {
		BufferedImage img =	new BufferedImage(ancho , alto, 
											  BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2 = img.createGraphics();
		
		chart.draw(g2, new Rectangle2D.Double(0, 0, ancho, alto));
		g2.dispose();
		
		return img;
	} 
	
	public JFreeChart getChart() {
		return chart;
	}
}

