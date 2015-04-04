package es.uned.pfg.ae;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

import es.uned.pfg.ae.funcion.Funcion;
import es.uned.pfg.ae.funcion.FuncionSchwefel;
import es.uned.pfg.ae.mutacion.Mutacion;
import es.uned.pfg.ae.mutacion.MutacionNormal;
import es.uned.pfg.ae.params.Parametros;
import es.uned.pfg.ae.poblacion.Poblacion;
import es.uned.pfg.ae.poblacion.PoblacionGeneracional;
import es.uned.pfg.ae.recombinacion.Recombinacion;
import es.uned.pfg.ae.recombinacion.RecombinacionK;
import es.uned.pfg.ae.seleccion.Seleccion;
import es.uned.pfg.ae.seleccion.SeleccionTorneo;
import es.uned.pfg.ae.terminacion.Terminacion;

/**
 * 
 * @author Fermin Silva < fermins@olx.com >
 */
public class Bootstrap {

	private static void display(String name) throws Exception {
		Runtime.getRuntime().exec(new String[]{ "eog", name });
	}
	
	public static void main(String[] args) throws Exception {
		Configuracion conf = new Configuracion(Parametros.crear(args));
		
//		Profiler profiler = new Profiler();
//		profiler.startCollecting();
		
//		GraficadorFitness graficadorFitness = new GraficadorFitness(ITERACIONES);
		Funcion f = new FuncionSchwefel(conf.getDimension());
		
//		Seleccion seleccion = new SeleccionNoOp();
		Seleccion seleccion = new SeleccionTorneo(conf.getTamañoTorneo(), Configuracion.ALEATORIO_ESTATICO);
//		Seleccion seleccion = new SeleccionEstocasticaUniversal(Configuracion.ALEATORIO_ESTATICO);
//		Recombinacion recombinacion = new RecombinacionNoOp();
//		Recombinacion recombinacion = new RecombinacionAritmeticaCompleta(f, 0.3, 1, Configuracion.ALEATORIO_ESTATICO);
//		Recombinacion recombinacion = new RecombinacionUnica(Configuracion.ALEATORIO_ESTATICO, f, 0.3);
		Recombinacion recombinacion = new RecombinacionK(Configuracion.ALEATORIO_ESTATICO, f, conf.getK());
		
//		Mutacion mutacion = new MutacionUniforme(f.getMin(), f.getMax(), Configuracion.ALEATORIO_ESTATICO,
//												 0.01);
		
		Mutacion mutacion = new MutacionNormal(conf.getDesviacionMutacion(), Configuracion.ALEATORIO_ESTATICO, f.getMin(), f.getMax());
//		Mutacion mutacion = new MutacionNoOp();
		
		Individuo[] individuos = getIndividuosInicial(conf.getTamañoPoblacion(), f);
		
//		System.out.print("===   ANTES   == \n");
//		System.out.println(Utils.toShortString(individuos));
//		for (Individuo individuo : individuos) {
//			System.out.println(individuo.getValores()[0] + "\t" + individuo.getValores()[1]);
//		}
		
		Poblacion poblacion = new PoblacionGeneracional(individuos, conf.getElitismo());
		
		Terminacion terminacion = new Terminacion() {
			public boolean isTerminado(int iteracion, Poblacion p) {
				return false;
			}
		};
		
		
		AlgoritmoGenetico ag = new AlgoritmoGenetico(poblacion, seleccion, 
													 recombinacion, mutacion, 
													 terminacion);

//		graficadorFitness.agregar(0, poblacion.getMejorIndividuo().getFitness());
//		graficadorFitness.salvar("fitness_" + String.format("%03d", 0) + ".png", 460, 360);
		
//		BufferedImage fitnessImagen = graficadorFitness.getImagen(460, 260);
//		BufferedImage poblacionImagen = getAsImagen(individuos, "iteracion_" + String.format("%03d", 0));
//		
//		BufferedImage merge = Bootstrap.joinBufferedImage(poblacionImagen, fitnessImagen);
//	    ImageIO.write(merge, "png", new File("combined_" + String.format("%03d", 0) + ".png"));
	    
//		plot(individuos, "iteracion_" + String.format("%03d", 0));
		
		for (int i = 1; i <= conf.getGeneraciones(); i++) {
			ag.iteracion(i);
//			((MutacionNormal)mutacion).iteracion();
			
//			if (i < 10 || (i < 100 && i % 10 == 0) || (i < 1000 && i % 100 == 0) || i % 1000 == 0) {
				individuos = poblacion.getIndividuos().clone();
				
				System.out.print("===  ITER " + String.format("%4d", i) + " == ");
				Arrays.sort(individuos);
				System.out.println(individuos[0]);

//				plot(individuos, "iteracion_" + String.format("%03d", i));
				
//				System.out.println(Utils.toShortString(individuos));
//				for (Individuo individuo : individuos) {
//					System.out.println(individuo.getValores()[0] + "\t" + individuo.getValores()[1] + " ---> " + individuo.getFitness());
//				}
//			}
				
//			graficadorFitness.agregar(i, poblacion.getMejorIndividuo().getFitness());
//			graficadorFitness.salvar("fitness_" + String.format("%03d", i) + ".png", 460, 360);
			
//			fitnessImagen = graficadorFitness.getImagen(460, 260);
//			poblacionImagen = getAsImagen(individuos, "iteracion_" + String.format("%03d", i));
//			
//			merge = Bootstrap.joinBufferedImage(poblacionImagen, fitnessImagen);
//		    ImageIO.write(merge, "png", new File("combined_" + String.format("%03d", i) + ".png"));
		}
		
		individuos = poblacion.getIndividuos();
		Arrays.sort(individuos);
		
		System.out.println("=== Top Individuos ===");
		for (int i = 0; i < Math.min(20, individuos.length); i++) {
			System.out.println(individuos[i]);
		}
		
//		System.out.println("=== Profiling ===");
//		System.out.println(profiler.getTop(3));
		
//		plot(individuos, "final");
//		graficadorFitness.salvar("fitness.png", 460, 360);
//		display("fitness.png");
	}
	
    private static void plot(Individuo[] individuos, String name) throws Exception {
    	JFreeChart chart = createDemoPanel(individuos, name);
        
	    //Save chart as PNG
	    ChartUtilities.saveChartAsPNG(new File(name + ".png"), chart, 480, 360);
    }
    
    public static BufferedImage getAsImagen(Individuo[] individuos, String name) {
    	JFreeChart chart = createDemoPanel(individuos, name);
    	
    	return getImagen(chart, 480, 360);
    }
    
    public static BufferedImage getImagen(JFreeChart chart, int ancho, int alto) {
		BufferedImage img =	new BufferedImage(ancho , alto, 
											  BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2 = img.createGraphics();
		
		chart.draw(g2, new Rectangle2D.Double(0, 0, ancho, alto));
		g2.dispose();
		
		return img;
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
                    return ShapeUtilities.createDiagonalCross(6, 1);
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
        xyPlot.getRenderer().setSeriesOutlineStroke(5, null);
        xyPlot.getRenderer().setSeriesVisibleInLegend(0, false);
        xyPlot.getRenderer().setSeriesVisibleInLegend(1, false);
        xyPlot.getRenderer().setSeriesVisibleInLegend(2, false);
        xyPlot.getRenderer().setSeriesVisibleInLegend(3, false);
        xyPlot.getRenderer().setSeriesVisibleInLegend(4, false);
        xyPlot.getRenderer().setSeriesVisibleInLegend(5, false);
        xyPlot.getRenderer().setSeriesStroke(0, null);
        xyPlot.getRenderer().setSeriesStroke(1, null);
        xyPlot.getRenderer().setSeriesStroke(2, null);
        xyPlot.getRenderer().setSeriesStroke(3, null);
        xyPlot.getRenderer().setSeriesStroke(4, null);
        xyPlot.getRenderer().setSeriesStroke(5, null);
        xyPlot.getRenderer().setSeriesShape(0, new Ellipse2D.Double(0,0,2,2));
        xyPlot.getRenderer().setSeriesShape(1, new Ellipse2D.Double(0,0,2,2));
        xyPlot.getRenderer().setSeriesShape(2, new Ellipse2D.Double(0,0,2,2));
        xyPlot.getRenderer().setSeriesShape(3, new Ellipse2D.Double(0,0,2,2));
        xyPlot.getRenderer().setSeriesShape(4, new Ellipse2D.Double(0,0,2,2));
        xyPlot.getRenderer().setSeriesShape(5, new Ellipse2D.Double(0,0,2,2));
        xyPlot.getRenderer().setSeriesPaint(0, new Color(0, 0, 255, 180));
        xyPlot.getRenderer().setSeriesPaint(1, new Color(0, 168, 0, 180));
        xyPlot.getRenderer().setSeriesPaint(2, new Color(168, 0, 0, 180));
        xyPlot.getRenderer().setSeriesPaint(3, new Color(222, 133, 0, 180));
        xyPlot.getRenderer().setSeriesPaint(4, new Color(255, 0, 255, 180));
        xyPlot.getRenderer().setSeriesPaint(5, Color.cyan);
        
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
				valores[j] = Configuracion.ALEATORIO_ESTATICO.getEntre(f.getMin(), f.getMax());
			}
			
			individuos[i] = new Individuo(i, valores, f);
		}
		
		return individuos;
	}
	
    /**
     * join two BufferedImage
     * you can add a orientation parameter to control direction
     * you can use a array to join more BufferedImage
     */

    public static BufferedImage joinBufferedImage(BufferedImage img1,BufferedImage img2) {
        //do some calculate first
        int offset  = 5;
//        int wid = img1.getWidth()+img2.getWidth()+offset;
        
        int wid = Math.max(img1.getWidth(), img2.getWidth());
        
//        int height = Math.max(img1.getHeight(),img2.getHeight())+offset;
        
        int height = img1.getHeight() + img2.getHeight() + offset;
        
        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(wid,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        //fill background
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, wid, height);
        //draw image
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
//        g2.drawImage(img2, null, img1.getWidth()+offset, 0);
        g2.drawImage(img2, null, 0, img1.getHeight() + offset);
        g2.dispose();
        
        return newImage;
    }
}
