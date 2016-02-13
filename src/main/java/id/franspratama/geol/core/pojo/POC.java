package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pocs",catalog="geolv2")
public class POC {

	@Id @GeneratedValue
	@Column(name="ID")
	private long id;
	
	@Column(name="POC_NAME")
	private String pocName;
	
	@Column(name="DESC")
	private String desc;

	public POC(long id, String pocName, String desc) {
		super();
		this.id = id;
		this.pocName = pocName;
		this.desc = desc;
	}

	public POC() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPocName() {
		return pocName;
	}

	public void setPocName(String pocName) {
		this.pocName = pocName;
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
		result = prime * result + ((pocName == null) ? 0 : pocName.hashCode());
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
		POC other = (POC) obj;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (id != other.id)
			return false;
		if (pocName == null) {
			if (other.pocName != null)
				return false;
		} else if (!pocName.equals(other.pocName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "POC [id=" + id + ", pocName=" + pocName + ", desc=" + desc + "]";
	}
	
	
	
	
}
