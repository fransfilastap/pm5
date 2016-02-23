package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="manager_area_to_persons_v2",catalog="geolv2")
public class ManagerAreaToPerson {
	
	@Id @GeneratedValue
	@Column(name="id")
	private int id;
	
	@OneToOne(fetch=FetchType.EAGER)
	private ManagerArea managerArea;
	
	@OneToOne(fetch=FetchType.EAGER)
	private Person person;
	
	public ManagerAreaToPerson(int id, ManagerArea managerArea, Person person) {
		super();
		this.id = id;
		this.managerArea = managerArea;
		this.person = person;
	}
	
	public ManagerAreaToPerson() {
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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	

}
