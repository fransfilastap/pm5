package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="projects",catalog="geolv2")
public class Project {
	
	@Id @GeneratedValue
	@Column(name="ID")
	private int id;
	
	@Column(name="PROJECT_NAME")
	private String projectName;
	
	@Column(name="ENABLED")
	private boolean enabled;
	
	public Project() {
		super();
	}

	public Project(int id, String projectName, boolean enabled) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.enabled = enabled;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	@Override
	public String toString() {
		return "Project [id=" + id + ", projectName=" + projectName + ", enabled=" + enabled + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
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
		Project other = (Project) obj;
		if (enabled != other.enabled)
			return false;
		if (id != other.id)
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		return true;
	}
	
}
