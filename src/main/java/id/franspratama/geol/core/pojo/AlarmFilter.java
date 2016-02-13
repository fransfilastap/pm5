package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="filters_v2", catalog="geolv2")
public class AlarmFilter {
	
	@Id @GeneratedValue
	@Column(name="ID")
	private long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FILTER_TYPE")
	private AlarmFilterType type;
	
	@Column(name="FILTER")
	private String filter;
	
	@Column(name="MANAGER")
	private String manager;
	
	@Enumerated(EnumType.STRING)
	@Column(name="SEVERITY")
	private Severity severity;
	
	public AlarmFilter(long id, AlarmFilterType type, String filter, String manager, Severity severity) {
		super();
		this.id = id;
		this.type = type;
		this.filter = filter;
		this.manager = manager;
		this.severity = severity;
	}

	public AlarmFilter() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AlarmFilterType getType() {
		return type;
	}

	public void setType(AlarmFilterType type) {
		this.type = type;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	@Override
	public String toString() {
		return "AlarmFilter [id=" + id + ", type=" + type + ", filter=" + filter + ", manager=" + manager
				+ ", severity=" + severity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filter == null) ? 0 : filter.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + ((severity == null) ? 0 : severity.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		AlarmFilter other = (AlarmFilter) obj;
		if (filter == null) {
			if (other.filter != null)
				return false;
		} else if (!filter.equals(other.filter))
			return false;
		if (id != other.id)
			return false;
		if (manager == null) {
			if (other.manager != null)
				return false;
		} else if (!manager.equals(other.manager))
			return false;
		if (severity != other.severity)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
	
	
}
