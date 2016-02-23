package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="supervisor_areas_v2",catalog="geolv2")
public class SupervisorArea {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id",unique=true)
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="manager_area_id")
	private ManagerArea managerArea;
	
	@Column(name="supervisor_area")
	private String supervisorAreaName;

	public SupervisorArea(int id, ManagerArea managerArea, String supervisorAreaName) {
		super();
		this.id = id;
		this.managerArea = managerArea;
		this.supervisorAreaName = supervisorAreaName;
	}

	public SupervisorArea() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ManagerArea getManagerArea() {
		return managerArea;
	}

	public void setManagerArea(ManagerArea managerArea) {
		this.managerArea = managerArea;
	}

	public String getSupervisorAreaName() {
		return supervisorAreaName;
	}

	public void setSupervisorAreaName(String supervisorAreaName) {
		this.supervisorAreaName = supervisorAreaName;
	}
	
	

}
