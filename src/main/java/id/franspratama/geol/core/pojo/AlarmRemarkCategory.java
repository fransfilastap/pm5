package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="alarm_remark_categories",catalog="geolv2")
public class AlarmRemarkCategory {

	@Id @GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="remark_category")
	private String category;
	
	public AlarmRemarkCategory(int id, String category) {
		super();
		this.id = id;
		this.category = category;
	}

	public AlarmRemarkCategory() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
}
