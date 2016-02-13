package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="filter_types_v2",catalog="geolv2")
public class AlarmFilterType implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3297756836577198828L;

	@Id @GeneratedValue
	@Column(name="ID")
	private long id;
	
	@Column(name="FILTER_TYPE")
	private String filterName;

	public AlarmFilterType(long id, String filterName) {
		super();
		this.id = id;
		this.filterName = filterName;
	}

	public AlarmFilterType() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filterName == null) ? 0 : filterName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		AlarmFilterType other = (AlarmFilterType) obj;
		if (filterName == null) {
			if (other.filterName != null)
				return false;
		} else if (!filterName.equals(other.filterName))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FilterType [id=" + id + ", filterName=" + filterName + "]";
	}
	
	
	
	
	

}
