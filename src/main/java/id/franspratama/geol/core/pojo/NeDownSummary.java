package id.franspratama.geol.core.pojo;

public class NeDownSummary {
	
	private String region;
	private int totalNe;
	private int totalDown;
	private double percentage;
	
	public NeDownSummary(String region, int totalNe, int totalDown, double percentage) {
		super();
		this.region = region;
		this.totalNe = totalNe;
		this.totalDown = totalDown;
		this.percentage = percentage;
	}
	
	

	public NeDownSummary() {
		super();
	}



	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getTotalNe() {
		return totalNe;
	}

	public void setTotalNe(int totalNe) {
		this.totalNe = totalNe;
	}

	public int getTotalDown() {
		return totalDown;
	}

	public void setTotalDown(int totalDown) {
		this.totalDown = totalDown;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	

}
