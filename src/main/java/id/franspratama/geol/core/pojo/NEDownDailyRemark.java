package id.franspratama.geol.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ne_down_list_daily_remarks")
public class NEDownDailyRemark {
	
	@Id @GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="time")
	private Date time;
	
	@Column(name="firstoccurrence")
	private Date firstOccurrence;
	
	@Column(name="btsid")
	private String btsId;
	
	@Column(name="btsname")
	private String btsName;
	
	@Column(name="node")
	private String node;
	
	@Column(name="region")
	private String region;
	
	@Column(name="alarms")
	private String alarms;
	
	@Column(name="ttno")
	private String ttno;
	
	@Enumerated(EnumType.STRING)
	@Column(name="remark")
	private NEDownRemark remark;

	public NEDownDailyRemark(int id, Date time, Date firstOccurrence, String btsId, String btsName, String node,
			String region, String alarms, String ttno, NEDownRemark remark) {
		super();
		this.id = id;
		this.time = time;
		this.firstOccurrence = firstOccurrence;
		this.btsId = btsId;
		this.btsName = btsName;
		this.node = node;
		this.region = region;
		this.alarms = alarms;
		this.ttno = ttno;
		this.remark = remark;
	}

	public NEDownDailyRemark() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getFirstOccurrence() {
		return firstOccurrence;
	}

	public void setFirstOccurrence(Date firstOccurrence) {
		this.firstOccurrence = firstOccurrence;
	}

	public String getBtsId() {
		return btsId;
	}

	public void setBtsId(String btsId) {
		this.btsId = btsId;
	}

	public String getBtsName() {
		return btsName;
	}

	public void setBtsName(String btsName) {
		this.btsName = btsName;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAlarms() {
		return alarms;
	}

	public void setAlarms(String alarms) {
		this.alarms = alarms;
	}

	public String getTtno() {
		return ttno;
	}

	public void setTtno(String ttno) {
		this.ttno = ttno;
	}

	public NEDownRemark getRemark() {
		return remark;
	}

	public void setRemark(NEDownRemark remark) {
		this.remark = remark;
	}

	
	
}
