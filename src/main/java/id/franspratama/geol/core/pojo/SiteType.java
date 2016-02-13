package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="site_types_v2",catalog="geolv2")
public class SiteType {
	
	@Id @GeneratedValue
	@Column(name="ID")
	private long id;
	
	@Column(name="SITE_TYPE")
	private String siteType;
	
	@Column(name="DESC")
	private String desc;

	public SiteType(long id, String siteType, String desc) {
		super();
		this.id = id;
		this.siteType = siteType;
		this.desc = desc;
	}

	public SiteType() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((siteType == null) ? 0 : siteType.hashCode());
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
		SiteType other = (SiteType) obj;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (id != other.id)
			return false;
		if (siteType == null) {
			if (other.siteType != null)
				return false;
		} else if (!siteType.equals(other.siteType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SiteType [id=" + id + ", siteType=" + siteType + ", desc=" + desc + "]";
	}

	 
	
}
