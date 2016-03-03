package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="sites",catalog="geolv2")
public class Site implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6010398256750623541L;

	@Id @GeneratedValue
	@Column(name="ID")
	private long id;
	
	@Column(name="SITEID")
	private String siteId;
	
	@Column(name="SITENAME")
	private String siteName;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REGION_ID")
	private Region region;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CITY")
	private City city;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CLUSTER_ID")
	private Cluster cluster;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PRIORITY_ID")
	private Priority priority;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="TECHNOLOGY_ID")
	private NetworkTechnology technology;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TOWER_ID")
	private Tower tower;
	
	@Column(name="BSC_RNC")
	private String bscOrRnc;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="LATITUDE")
	private Double latitude;
	
	@Column(name="LONGITUDE")
	private Double longitude;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="SITE_TYPE")
	private SiteType type;
	
	@Column(name="TOWER_PROVIDER")
	private String towerProvider;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="manager_area_id")
	private ManagerArea managerArea;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="supervisor_area_id")
	private SupervisorArea supervisorArea;
	
	@Column(name="CREATED_AT")
	private Date createdAt;
	
	@Column(name="UPDATED_AT")
	private Date updatedAt;
	
	@Column(name="DELETED_AT")
	private Date deletedAt;



	public Site(long id, String siteId, String siteName, Region region, City city, Cluster cluster, Priority priority,
			NetworkTechnology technology, Tower tower, String bscOrRnc, String address, Double latitude,
			Double longitude, SiteType type, String towerProvider, ManagerArea managerArea,
			SupervisorArea supervisorArea, Date createdAt, Date updatedAt, Date deletedAt) {
		super();
		this.id = id;
		this.siteId = siteId;
		this.siteName = siteName;
		this.region = region;
		this.city = city;
		this.cluster = cluster;
		this.priority = priority;
		this.technology = technology;
		this.tower = tower;
		this.bscOrRnc = bscOrRnc;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.type = type;
		this.towerProvider = towerProvider;
		this.managerArea = managerArea;
		this.supervisorArea = supervisorArea;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public Site() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public NetworkTechnology getTechnology() {
		return technology;
	}

	public void setTechnology(NetworkTechnology technology) {
		this.technology = technology;
	}

	public String getBscOrRnc() {
		return bscOrRnc;
	}

	public void setBscOrRnc(String bscOrRnc) {
		this.bscOrRnc = bscOrRnc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitue() {
		return ( latitude == null ? 0 : latitude );
	}

	public void setLatitue(double latitue) {
		this.latitude = latitue;
	}

	public double getLongitude() {
		return ( longitude == null ? 0 : longitude );
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public SiteType getType() {
		return type;
	}

	public void setType(SiteType type) {
		this.type = type;
	}

	public String getTowerProvider() {
		return towerProvider;
	}

	public void setTowerProvider(String towerProvider) {
		this.towerProvider = towerProvider;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	
	

	public Tower getTower() {
		return tower;
	}

	public void setTower(Tower tower) {
		this.tower = tower;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public ManagerArea getManagerArea() {
		return managerArea;
	}

	public void setManagerArea(ManagerArea managerArea) {
		this.managerArea = managerArea;
	}

	public SupervisorArea getSupervisorArea() {
		return supervisorArea;
	}

	public void setSupervisorArea(SupervisorArea supervisorArea) {
		this.supervisorArea = supervisorArea;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Site [id=" + id + ", siteId=" + siteId + ", siteName=" + siteName + ", region=" + region + ", city="
				+ city + ", cluster=" + cluster + ", priority=" + priority + ", technology=" + technology
				+ ", bscOrRnc=" + bscOrRnc + ", address=" + address + ", latitue=" + latitude + ", longitude="
				+ longitude + ", type=" + type + ", towerProvider=" + towerProvider + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((bscOrRnc == null) ? 0 : bscOrRnc.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((cluster == null) ? 0 : cluster.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((deletedAt == null) ? 0 : deletedAt.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((siteId == null) ? 0 : siteId.hashCode());
		result = prime * result + ((siteName == null) ? 0 : siteName.hashCode());
		result = prime * result + ((technology == null) ? 0 : technology.hashCode());
		result = prime * result + ((towerProvider == null) ? 0 : towerProvider.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Site other = (Site) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (bscOrRnc == null) {
			if (other.bscOrRnc != null)
				return false;
		} else if (!bscOrRnc.equals(other.bscOrRnc))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (cluster == null) {
			if (other.cluster != null)
				return false;
		} else if (!cluster.equals(other.cluster))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (deletedAt == null) {
			if (other.deletedAt != null)
				return false;
		} else if (!deletedAt.equals(other.deletedAt))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (siteId == null) {
			if (other.siteId != null)
				return false;
		} else if (!siteId.equals(other.siteId))
			return false;
		if (siteName == null) {
			if (other.siteName != null)
				return false;
		} else if (!siteName.equals(other.siteName))
			return false;
		if (technology == null) {
			if (other.technology != null)
				return false;
		} else if (!technology.equals(other.technology))
			return false;
		if (towerProvider == null) {
			if (other.towerProvider != null)
				return false;
		} else if (!towerProvider.equals(other.towerProvider))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}
	
	
	
}

