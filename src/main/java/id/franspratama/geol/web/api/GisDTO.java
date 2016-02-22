package id.franspratama.geol.web.api;

import id.franspratama.geol.core.pojo.NetworkTechnology;
import id.franspratama.geol.core.pojo.Severity;
import id.franspratama.geol.core.pojo.SiteType;

public class GisDTO {
	
	private String siteName;
	private String siteId;
	private String towerProvider;
	private Severity severity;
	private SiteType type;
	private NetworkTechnology technology;
	private double latitude;
	private double longitude;
	
	
	public GisDTO() {
		super();
	}


	public GisDTO(String siteName, String siteId, String towerProvider, Severity severity, SiteType type, NetworkTechnology technology,
			double latitude, double longitude) {
		super();
		this.siteName = siteName;
		this.siteId = siteId;
		this.severity = severity;
		this.towerProvider=towerProvider;
		this.type = type;
		this.technology = technology;
		this.latitude = latitude;
		this.longitude = longitude;
	}





	public NetworkTechnology getTechnology() {
		return technology;
	}





	public void setTechnology(NetworkTechnology technology) {
		this.technology = technology;
	}





	public SiteType getType() {
		return type;
	}


	public void setType(SiteType type) {
		this.type = type;
	}


	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public Severity getSeverity() {
		return severity;
	}
	public void setSeverity(Severity severity) {
		this.severity = severity;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public String getTowerProvider() {
		return towerProvider;
	}


	public void setTowerProvider(String towerProvider) {
		this.towerProvider = towerProvider;
	}
	
	
	
}
