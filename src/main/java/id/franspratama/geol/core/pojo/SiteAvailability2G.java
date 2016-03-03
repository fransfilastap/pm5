package id.franspratama.geol.core.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="2g_pm",catalog="pm")
public class SiteAvailability2G implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5152597530491930086L;

	@Id @GeneratedValue
	@Column(name="id")
	private long id;
	
	@Column(name="result_time")
	private Date resultTime;
	
	@Column(name="cellname")
	private String cellName;
	
	@Column(name="siteId")
	private String siteId;
	
	@Column(name="poc")
	private String poc;
	
	@Column(name="region")
	private String region;
	
	@Column(name="tch_avail")
	private double availability;

	public SiteAvailability2G() {
		super();
	}

	public SiteAvailability2G(long id, Date resultTime, String cellName, String siteId, String poc, String region,
			double availability) {
		super();
		this.id = id;
		this.resultTime = resultTime;
		this.cellName = cellName;
		this.siteId = siteId;
		this.poc = poc;
		this.region = region;
		this.availability = availability;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

