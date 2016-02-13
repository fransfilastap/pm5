package id.franspratama.geol.util;

import java.util.List;

import org.jfree.chart.JFreeChart;

public interface AppChart<T> {
	
	public JFreeChart createChart(List<T> rawData,String chartTitle);
	public void setChartTitle(String title);
	
}
