package id.franspratama.geol.web.api;

import java.util.Date;

public class SiteAvailabilityDTO {
	
	private Date resultTime;
	private String cellName;
	private String siteId;
	private String poc;
	private String region;
	private double availability;
	
	public SiteAvailabilityDTO() {
		super();
	}

	public SiteAvailabilityDTO(Date resultTime, String cellName, String siteId, String poc, String region,
			double availability) {
		super();
		this.resultTime = resultTime;
		this.cellName = cellName;
		this.siteId = siteId;
		this.poc = poc;
		this.region = region;
		this.availability = availability;
	}

	public Date getResultTime() {
		return resultTime;
	}
	
	public void setResultTime(Date resultTime) {
		this.resultTime = resultTime;
	}
	
	public String getCellName() {
		return cellName;
	}
	
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	
	public String getSiteId() {
		return siteId;
	}
	
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
	public String getPoc() {
		return poc;
	}
	
	public void setPoc(String poc) {
		this.poc = poc;
	}
	
	public String getRegion() {
		return region;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public double getAvailability() {
		return availability;
	}
	
	public void setAvailability(double availability) {
		this.availability = availability;
	}
	
	
}
