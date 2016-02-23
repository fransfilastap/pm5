package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="supervisor_area_to_persons_v2",catalog="geolv2")
public class SupervisorAreaToPerson {
	
	@Id @GeneratedValue
	@Column(name="id")
	private int id;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="supervisor_area_id")
	private SupervisorArea supervisorArea;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="person_id")
	private Person person;

	
	public SupervisorAreaToPerson() {
		super();
	}

	public SupervisorAreaToPerson(int id, SupervisorArea supervisorArea, Person person) {
		super();
		this.id = id;
		this.supervisorArea = supervisorArea;
		this.person = person;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SupervisorArea getSupervisorArea() {
		return supervisorArea;
	}

	public void setSupervisorArea(SupervisorArea supervisorArea) {
		this.supervisorArea = supervisorArea;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	

}
