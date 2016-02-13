package id.franspratama.geol.core.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="acrosspm_cluster_map",catalog="geolv2")
public class AcrosspmCluster implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8024848073674821832L;

	@Id @GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="cluster_code")
	private String clusterCode;
	
	@Column(name="area_name")
	private String areaName;
	
	@Column(name="region1_code")
	private String region1Code;
	
	@Column(name="region3_code")
	private String region3Code;
	
	@Column(name="region5_code")
	private String region5Code;
	
	@Column(name="region_optim")
	private String regionOptim;
	
	@Column(name="region6_code")
	private String region6Code;
	
	@Column(name="availability_region")
	private String availabilityRegion;
	
	public AcrosspmCluster() {
		super();
	}
	
	public AcrosspmCluster(String clusterCode, String areaName, String region1Code, String region3Code,
			String region5Code, String regionOptim, String region6Code, String availabilityRegion) {
		super();
		this.clusterCode = clusterCode;
		this.areaName = areaName;
		this.region1Code = region1Code;
		this.region3Code = region3Code;
		this.region5Code = region5Code;
		this.regionOptim = regionOptim;
		this.region6Code = region6Code;
		this.availabilityRegion = availabilityRegion;
	}
	public String getClusterCode() {
		return clusterCode;
	}
	public void setClusterCode(String clusterCode) {
		this.clusterCode = clusterCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getRegion1Code() {
		return region1Code;
	}
	public void setRegion1Code(String region1Code) {
		this.region1Code = region1Code;
	}
	public String getRegion3Code() {
		return region3Code;
	}
	public void setRegion3Code(String region3Code) {
		this.region3Code = region3Code;
	}
	public String getRegion5Code() {
		return region5Code;
	}
	public void setRegion5Code(String region5Code) {
		this.region5Code = region5Code;
	}
	public String getRegionOptim() {
		return regionOptim;
	}
	public void setRegionOptim(String regionOptim) {
		this.regionOptim = regionOptim;
	}
	public String getRegion6Code() {
		return region6Code;
	}
	public void setRegion6Code(String region6Code) {
		this.region6Code = region6Code;
	}
	public String getAvailabilityRegion() {
		return availabilityRegion;
	}
	public void setAvailabilityRegion(String availabilityRegion) {
		this.availabilityRegion = availabilityRegion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
