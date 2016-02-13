package id.franspratama.geol.core.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wfm_file_log",catalog="geolv2")
public class WFMTicketAndWorkOrderFileLog implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6815219871777162466L;

	@Id @GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="filename")
	private String filename;
	
	@Column(name="insert_time")
	private Date insert_time;

	
	public WFMTicketAndWorkOrderFileLog() {
		super();
	}

	public WFMTicketAndWorkOrderFileLog(int id, String filename, Date insert_time) {
		super();
		this.id = id;
		this.filename = filename;
		this.insert_time = insert_time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}

	
}
