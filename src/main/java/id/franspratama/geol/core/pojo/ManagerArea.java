package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="manager_areas_v2",catalog="geolv2")
public class ManagerArea {

	@Id @GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="area_manager")
	private String areaManager;

	
	public ManagerArea() {
		super();
	}

	public ManagerArea(int id, String areaManager) {
		super();
		this.id = id;
		this.areaManager = areaManager;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAreaManager() {
		return areaManager;
	}

	public void setAreaManager(String areaManager) {
		this.areaManager = areaManager;
	}
		
	
	
}
