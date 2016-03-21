package id.franspratama.geol.core.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <h3>UNTUK PROGRAMMER SELANJUTNYA</h3>
 * 
 * 
 * <p>Sebaiknya kelas ini diganti ke class <b>id.franspratama.geol.core.pojo.Site</b> .
 * Sebaiknya table ne_v2 dan sites di-<i>merge</i>. Gunakan saja table sites. 
 * Jika anda telah menggunakantable sites, maka lakukan sedikit perubahan pada query query yang berhubungan
 * dengan NE Down. Query-query yang berhubungan dengan NE down terdapat pada kelas-kelas pada package  
 * <b>id.franspratama.geol.core.dao</b></p>
 * 
 * @author fransfilastap
 *
 */

@Entity
@Table(name="ne_v2", catalog="geolv2")
public class NE implements Serializable{
	
	
	@Id @GeneratedValue
	@Column(name="id")
	private long id;
	
	@Column(name="btsid",unique=true,nullable=false)
	private String btsId;
	
	@Column(name="btsname",nullable=false)
	private String btsname;
	
	@Column(name="node",nullable=false)
	private String node;
	
	@Column(name="region",nullable=false)
	private String region;
	
	@Column(name="siteType",nullable=true)
	private String siteType;
	
	@Column(name="userlabel")
	private String userLabel;
	
	@Column(name="mgrarea")
	private String managerArea;
	
	@Column(name="spvarea")
	private String supervisorArea;
	
	@Column(name="msPartner")
	private String msPartner;
	
	@Column(name="kabupatenKodya")
	private String kabupatenKota;
	
	@Column(name="sourcePower")
	private String sourcePower;
	
	@Column(name="btsPriority")
	private String btsPriority;
	
	@Column(name="towerProvider")
	private String towerProvider;
	
	@Column(name="siteOwner")
	private String siteOwner;
	
	@Column(name="address")
	private String address;


	public NE() {
		super();
	}


	public NE(long id, String btsId, String btsname, String node, String region, String siteType, String userLabel,
			String managerArea, String supervisorArea, String msPartner, String kabupatenKota, String sourcePower,
			String btsPriority, String towerProvider, String siteOwner, String address) {
		super();
		this.id = id;
		this.btsId = btsId;
		this.btsname = btsname;
		this.node = node;
		this.region = region;
		this.siteType = siteType;
		this.userLabel = userLabel;
		this.managerArea = managerArea;
		this.supervisorArea = supervisorArea;
		this.msPartner = msPartner;
		this.kabupatenKota = kabupatenKota;
		this.sourcePower = sourcePower;
		this.btsPriority = btsPriority;
		this.towerProvider = towerProvider;
		this.siteOwner = siteOwner;
		this.address = address;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getBtsname() {
		return btsname;
	}



	public void setBtsname(String btsname) {
		this.btsname = btsname;
	}



	public String getNode() {
		return node;
	}



	public void setNode(String node) {
		this.node = node;
	}



	public String getRegion() {
		return region;
	}



	public void setRegion(String region) {
		this.region = region;
	}



	public String getSiteType() {
		return siteType;
	}



	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}



	public String getUserLabel() {
		return userLabel;
	}



	public void setUserLabel(String userLabel) {
		this.userLabel = userLabel;
	}



	public String getManagerArea() {
		return managerArea;
	}



	public void setManagerArea(String managerArea) {
		this.managerArea = managerArea;
	}



	public String getSupervisorArea() {
		return supervisorArea;
	}



	public void setSupervisorArea(String supervisorArea) {
		this.supervisorArea = supervisorArea;
	}



	public String getMsPartner() {
		return msPartner;
	}



	public void setMsPartner(String msPartner) {
		this.msPartner = msPartner;
	}



	public String getKabupatenKota() {
		return kabupatenKota;
	}



	public void setKabupatenKota(String kabupatenKota) {
		this.kabupatenKota = kabupatenKota;
	}



	public String getSourcePower() {
		return sourcePower;
	}



	public void setSourcePower(String sourcePower) {
		this.sourcePower = sourcePower;
	}



	public String getSiteOwner() {
		return siteOwner;
	}



	public void setSiteOwner(String siteOwner) {
		this.siteOwner = siteOwner;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getBtsId() {
		return btsId;
	}



	public void setBtsId(String btsId) {
		this.btsId = btsId;
	}


	public String getBtsPriority() {
		return btsPriority;
	}


	public void setBtsPriority(String btsPriority) {
		this.btsPriority = btsPriority;
	}


	public String getTowerProvider() {
		return towerProvider;
	}


	public void setTowerProvider(String towerProvider) {
		this.towerProvider = towerProvider;
	}
	
	
	
	

}
