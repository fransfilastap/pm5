package id.franspratama.geol.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ShapeUtilities;
import org.springframework.stereotype.Component;

import id.franspratama.geol.core.pojo.RegionAvailability;

@Component("regionAvailabilityChart")
public class RegionAvailabilityChart implements AppChart<RegionAvailability>{
	
	private Shape[] shapes = new Shape[]{
            ShapeUtilities.createDiamond(5),
            ShapeUtilities.createDownTriangle(5),
            ShapeUtilities.createUpTriangle(5),
            new Rectangle(0, -3, 7, 7),
            new Ellipse2D.Double(0, -4, 10, 10)
    };
	
	private String chartTitle = "REGION AVAILABILITY";
	private String xAxisTitle = "TIME";
	private String yAxisTitle = "NAV";
	

	@Override
	public JFreeChart createChart(List<RegionAvailability> rawData,String chartTitle) {
        this.chartTitle = chartTitle;
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                this.chartTitle,
                xAxisTitle, 
                yAxisTitle,
                createDataSet(rawData),
                true, true, false
        );

        chart.getTitle().setFont(new Font(Font.DIALOG,Font.BOLD , 15));
        chart.getTitle().setPaint(Color.red);
        
        chart.setBackgroundPaint(Color.WHITE);
        
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.darkGray);
        plot.setRangeGridlinePaint(Color.darkGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
        
        int shi = 0;
        for (int i = 0; i < plot.getSeriesCount(); i++) {
            if( shi >= shapes.length )
                shi = 0;
      
            r.setSeriesShape(i, shapes[ shi ]);
            r.setSeriesShapesVisible(i, true);
            
            shi++;
        }
        
        return chart;
	}



	private TimeSeriesCollection createDataSet(List<RegionAvailability> avails) {
        TimeSeriesCollection collection = new TimeSeriesCollection();
        
        Map<String,List> x = new HashMap<>();
        
        avails.stream().forEach( nd ->{
            if( x.get(nd.getRegion()) == null ){
                List<RegionAvailability> availsx = new ArrayList<>();
                x.put(nd.getRegion(), availsx);
            }
            x.get(nd.getRegion()).add(nd);
        } );
        
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        
        x.entrySet().stream().map((entry) -> {
            String region = entry.getKey();
            List<RegionAvailability> values = entry.getValue();
            TimeSeries series = new TimeSeries(region);
            values.stream().forEach( v ->{
                series.add(new Hour( v.getTime() ), v.getAvailability());
            });
            return series;
        }).forEach((series) -> {
            collection.addSeries(series);
        });
        
        return collection;
	}



	@Override
	public void setChartTitle(String title) {
		this.chartTitle = title;
	}


}
