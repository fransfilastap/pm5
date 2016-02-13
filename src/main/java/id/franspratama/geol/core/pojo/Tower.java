package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="towers",catalog="geolv2")
public class Tower implements java.io.Serializable{

	@Id @GeneratedValue
	@Column(name="ID")
	private long id;
	
	@Column(name="TOWER_ID")
	private String towerId;
	
	@Column(name="TOWER_PROVIDER_TEXT")
	private String towerProvider;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="POC_ID")
	private POC poc;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PRIORITY_ID")
	private Priority priority;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CITY_ID")
	private City city;

	public Tower(long id, String towerId, String towerProvider, POC poc, Priority priority, City city) {
		super();
		this.id = id;
		this.towerId = towerId;
		this.towerProvider = towerProvider;
		this.poc = poc;
		this.priority = priority;
		this.city = city;
	}

	public Tower() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTowerId() {
		return towerId;
	}

	public void setTowerId(String towerId) {
		this.towerId = towerId;
	}

	public String getTowerProvider() {
		return towerProvider;
	}

	public void setTowerProvider(String towerProvider) {
		this.towerProvider = towerProvider;
	}

	public POC getPoc() {
		return poc;
	}

	public void setPoc(POC poc) {
		this.poc = poc;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((poc == null) ? 0 : poc.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((towerId == null) ? 0 : towerId.hashCode());
		result = prime * result + ((towerProvider == null) ? 0 : towerProvider.hashCode());
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
		Tower other = (Tower) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (id != other.id)
			return false;
		if (poc == null) {
			if (other.poc != null)
				return false;
		} else if (!poc.equals(other.poc))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (towerId == null) {
			if (other.towerId != null)
				return false;
		} else if (!towerId.equals(other.towerId))
			return false;
		if (towerProvider == null) {
			if (other.towerProvider != null)
				return false;
		} else if (!towerProvider.equals(other.towerProvider))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tower [id=" + id + ", towerId=" + towerId + ", towerProvider=" + towerProvider + ", poc=" + poc
				+ ", priority=" + priority + ", city=" + city + "]";
	}
	
	  
	
}
