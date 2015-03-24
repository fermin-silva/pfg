package es.uned.pfg.ae;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.Arrays;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.funcion.FuncionSchwefel;
import es.uned.pfg.ae.mutacion.Mutacion;
import es.uned.pfg.ae.mutacion.MutacionNormal;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.poblacion.PoblacionGeneracional;
import es.uned.pfg.ae.recombinacion.Recombinacion;
import es.uned.pfg.ae.recombinacion.RecombinacionAritmeticaCompleta;
import es.uned.pfg.ae.recombinacion.RecombinacionAritmeticaSimple;
import es.uned.pfg.ae.seleccion.Seleccion;
import es.uned.pfg.ae.seleccion.SeleccionTorneo;
import es.uned.pfg.ae.terminacion.Terminacion;
import es.uned.pfg.ae.utils.Aleatorio;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class Bootstrap {

	private static Aleatorio aleatorio = new Aleatorio(1425117445324L);
//	private static Aleatorio aleatorio = new Aleatorio();
	
	private static void display(String name) throws Exception {
		Runtime.getRuntime().exec(new String[]{ "eog", name });
	}
	
	public static void main(String[] args) throws Exception {
//		Profiler profiler = new Profiler();
//		profiler.startCollecting();
		
		Funcion f = new FuncionSchwefel(10);
		
//		Seleccion seleccion = new SeleccionNoOp();
		Seleccion seleccion = new SeleccionTorneo(4, aleatorio);
//		Seleccion seleccion = new SeleccionEstocasticaUniversal(aleatorio);
//		Recombinacion recombinacion = new RecombinacionNoOp();
//		Recombinacion recombinacion = new RecombinacionAritmeticaCompleta(f, 0.3, 0.6, aleatorio);
		Recombinacion recombinacion = new RecombinacionAritmeticaSimple(aleatorio, f, true, f.getMin(), f.getMax());
		
//		Mutacion mutacion = new MutacionUniforme(f.getMin(), f.getMax(), aleatorio,
//												 0.01);
		
		Mutacion mutacion = new MutacionNormal(50, aleatorio, f.getMin(), f.getMax());
//		Mutacion mutacion = new MutacionNoOp();
		
		Configuracion conf = new Configuracion();
		
		Individuo[] individuos = getIndividuosInicial(2000, f);
		
//		System.out.print("===   ANTES   == \n");
//		System.out.println(Utils.toShortString(individuos));
//		for (Individuo individuo : individuos) {
//			System.out.println(individuo.getValores()[0] + "\t" + individuo.getValores()[1]);
//		}
		
		Poblacion poblacion = new PoblacionGeneracional(individuos, true);
		
		Terminacion terminacion = new Terminacion() {
			public boolean isTerminado(int iteracion, Poblacion p) {
				return false;
			}
		};
		
		
		AlgoritmoGenetico ag = new AlgoritmoGenetico(conf, poblacion, seleccion, 
													 recombinacion, mutacion, 
													 terminacion);

		for (int i = 1; i < 50; i++) {
			ag.iteracion(i);
			((MutacionNormal)mutacion).iteracion(); 
//			if (i < 10 || (i < 100 && i % 10 == 0) || (i < 1000 && i % 100 == 0) || i % 1000 == 0) {
				individuos = poblacion.getIndividuos().clone();
				
				System.out.print("===  ITER " + String.format("%4d", i) + " == ");
				Arrays.sort(individuos);
				System.out.println(individuos[0]);

				plot(individuos, "iteracion_" + String.format("%03d", i));
				
//				System.out.println(Utils.toShortString(individuos));
//				for (Individuo individuo : individuos) {
//					System.out.println(individuo.getValores()[0] + "\t" + individuo.getValores()[1] + " ---> " + individuo.getFitness());
//				}
//			}
		}
		
		individuos = poblacion.getIndividuos();
		Arrays.sort(individuos);
		
		System.out.println("=== Top Individuos ===");
		for (int i = 0; i < Math.min(20, individuos.length); i++) {
			System.out.println(individuos[i]);
		}
		
//		System.out.println("=== Profiling ===");
//		System.out.println(profiler.getTop(3));
		
		plot(individuos, "final");
		display("final.png");
	}
	
    private static void plot(Individuo[] individuos, String name) throws Exception {
    	JFreeChart chart = createDemoPanel(individuos, name);
        
	    //Save chart as PNG
	    ChartUtilities.saveChartAsPNG(new File(name + ".png"), chart, 480, 360);
    }
    
    private static JFreeChart createDemoPanel(Individuo[] individuos, String name) {
        JFreeChart chart = ChartFactory.createScatterPlot(
            String.format("%s   min %7s", name, String.format("%.2f", Math.abs(individuos[0].getFitness()))), "X", "Y", createSampleData(individuos),
            PlotOrientation.VERTICAL, true, false, false);
        
        XYPlot xyPlot = (XYPlot) chart.getPlot();
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        xyPlot.setDomainGridlinesVisible(true);
        xyPlot.setBackgroundPaint(new Color(255, 255, 255, 0));
        xyPlot.setBackgroundAlpha(0f);
        xyPlot.setBackgroundImageAlpha(0f);
        
        xyPlot.setRenderer(new XYLineAndShapeRenderer(false, true) {

        	@Override
        	public Paint getItemPaint(int row, int column) {
        		if (row == 0 && column == 0) {
        			return Color.RED;
        		}
        		
//        		if (column == 1) {
//        			return Color.GREEN;
//        		}
        		
//        		double x = this.getPlot().getDataset().getXValue(row, column);
//				double y = this.getPlot().getDataset().getYValue(row, column);
//        				
//				System.out.printf("Row %d Col %d -> x = %f, y = %f\n", row, column, x, y);
        		
        		return super.getItemPaint(row, column);
        	}
        	
            @Override
            public Shape getItemShape(int row, int col) {
                if (row == 0 && col == 0) {
                    return ShapeUtilities.createDiagonalCross(4, 2);
                }
//                else if (col == 1) {
//                	return ShapeUtilities.createDiamond(4);
//                } 
                else {
                    return super.getItemShape(row, col);
                }
            }
        });
        
        xyPlot.getDomainAxis().setRange(-500, 500);
        xyPlot.getRangeAxis().setRange(-500, 500);
        
        xyPlot.getRenderer().setSeriesOutlineStroke(0, null);
        xyPlot.getRenderer().setSeriesOutlineStroke(1, null);
        xyPlot.getRenderer().setSeriesOutlineStroke(2, null);
        xyPlot.getRenderer().setSeriesOutlineStroke(3, null);
        xyPlot.getRenderer().setSeriesOutlineStroke(4, null);
        xyPlot.getRenderer().setSeriesVisibleInLegend(0, false);
        xyPlot.getRenderer().setSeriesVisibleInLegend(1, false);
        xyPlot.getRenderer().setSeriesVisibleInLegend(2, false);
        xyPlot.getRenderer().setSeriesVisibleInLegend(3, false);
        xyPlot.getRenderer().setSeriesVisibleInLegend(4, false);
        xyPlot.getRenderer().setSeriesStroke(0, null);
        xyPlot.getRenderer().setSeriesStroke(1, null);
        xyPlot.getRenderer().setSeriesStroke(2, null);
        xyPlot.getRenderer().setSeriesStroke(3, null);
        xyPlot.getRenderer().setSeriesStroke(4, null);
        xyPlot.getRenderer().setSeriesShape(0, new Ellipse2D.Double(0,0,2,2));
        xyPlot.getRenderer().setSeriesShape(1, new Ellipse2D.Double(0,0,2,2));
        xyPlot.getRenderer().setSeriesShape(2, new Ellipse2D.Double(0,0,2,2));
        xyPlot.getRenderer().setSeriesShape(3, new Ellipse2D.Double(0,0,2,2));
        xyPlot.getRenderer().setSeriesShape(4, new Ellipse2D.Double(0,0,2,2));
        xyPlot.getRenderer().setSeriesPaint(0, new Color(0, 0, 255, 180));
        xyPlot.getRenderer().setSeriesPaint(1, new Color(0, 168, 0, 180));
        xyPlot.getRenderer().setSeriesPaint(2, new Color(168, 0, 0, 180));
        xyPlot.getRenderer().setSeriesPaint(3, new Color(222, 133, 0, 180));
        xyPlot.getRenderer().setSeriesPaint(4, new Color(67, 53, 99, 180));
        
//        xyPlot.getRenderer().setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
//        xyPlot.getRenderer().setBaseItemLabelsVisible(true);
        
        
        
        return chart;
    }

    private static XYDataset createSampleData(Individuo[] individuos) {
    	
    	XYSeriesCollection xySeriesCollection = new XYSeriesCollection();

    	for (int i = 0; i < individuos[0].getValores().length - 1; i += 2) {
    		XYSeries series = new XYSeries("Dim" + i, false);
    		
    		//el minimo de la funcion schwefel
            //TODO parametrizar esto?
            series.add(418.9829, 418.9829);
            
            for (Individuo individuo : individuos) {
    			series.add(individuo.getValores()[i], individuo.getValores()[i + 1]);
    		}
            
            xySeriesCollection.addSeries(series);
    	}
        
        return xySeriesCollection;
    }
    
	public static Individuo[] getIndividuosInicial(int tamaño, Funcion f) {
		int dimension = f.getDimension();
		
		Individuo[] individuos = new Individuo[tamaño];
		
		for (int i = 0; i < individuos.length; i++) {
			double[] valores = new double[dimension];
			
			for (int j = 0; j < valores.length; j++) {
				valores[j] = aleatorio.getEntre(f.getMin(), f.getMax());
			}
			
			individuos[i] = new Individuo(i, valores, f);
		}
		
		return individuos;
	}
}
