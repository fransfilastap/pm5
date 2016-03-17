package id.franspratama.geol.core.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="alarm_remarks",catalog="geolv2")
public class AlarmRemark implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8485811866609846521L;

	@Id @GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="eventid")
	private long eventId;
	
	@Column(name="alarm_name")
	private String alarmName;
	
	@Column(name="node")
	private String node;
	
	@Column(name="site_id")
	private String siteId;
	
	@Column(name="site")
	private String site;
	
	@Column(name="zone")
	private String zone;
	
	@Column(name="severity")
	private String severity;
	
	@Column(name="ttno")
	private String ttno;
	
	@Column(name="first_occurrence")
	private Date firstOccurrence;
	
	@Column(name="last_occurrence")
	private Date lastOccurrence;
	
	@Column(name="first_received")
	private Date firstReceived;
	
	@Column(name="last_received")
	private Date lastReceived;
	
	@Column(name="summary")
	private String summary;
	
	@Column(name="manager")
	private String manager;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="remark_category")
	private AlarmRemarkCategory category;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="remark_time")
	private Date remarkTime;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="remarked_by")
	private User user;
	
	
	public AlarmRemark(int id, long eventId, String alarmName, String node, String siteId, String site, String zone,
			String severity, String ttno, Date firstOccurrence, Date lastOccurrence, Date firstReceived,
			Date lastReceived, String summary, String manager, AlarmRemarkCategory category, String remark,
			Date remarkTime, User user) {
		super();
		this.id = id;
		this.eventId = eventId;
		this.alarmName = alarmName;
		this.node = node;
		this.siteId = siteId;
		this.site = site;
		this.zone = zone;
		this.severity = severity;
		this.ttno = ttno;
		this.firstOccurrence = firstOccurrence;
		this.lastOccurrence = lastOccurrence;
		this.firstReceived = firstReceived;
		this.lastReceived = lastReceived;
		this.summary = summary;
		this.manager = manager;
		this.category = category;
		this.remark = remark;
		this.remarkTime = remarkTime;
		this.user = user;
	}


	public AlarmRemark() {
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public long getEventId() {
		return eventId;
	}


	public void setEventId(long eventId) {
		this.eventId = eventId;
	}


	public String getAlarmName() {
		return alarmName;
	}


	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}


	public String getNode() {
		return node;
	}


	public void setNode(String node) {
		this.node = node;
	}


	public String getSiteId() {
		return siteId;
	}


	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}


	public String getSite() {
		return site;
	}


	public void setSite(String site) {
		this.site = site;
	}


	public String getZone() {
		return zone;
	}


	public void setZone(String zone) {
		this.zone = zone;
	}


	public String getSeverity() {
		return severity;
	}


	public void setSeverity(String severity) {
		this.severity = severity;
	}


	public String getTtno() {
		return ttno;
	}


	public void setTtno(String ttno) {
		this.ttno = ttno;
	}


	public Date getFirstOccurrence() {
		return firstOccurrence;
	}


	public void setFirstOccurrence(Date firstOccurrence) {
		this.firstOccurrence = firstOccurrence;
	}


	public Date getLastOccurrence() {
		return lastOccurrence;
	}


	public void setLastOccurrence(Date lastOccurrence) {
		this.lastOccurrence = lastOccurrence;
	}


	public Date getFirstReceived() {
		return firstReceived;
	}


	public void setFirstReceived(Date firstReceived) {
		this.firstReceived = firstReceived;
	}


	public Date getLastReceived() {
		return lastReceived;
	}


	public void setLastReceived(Date lastReceived) {
		this.lastReceived = lastReceived;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getManager() {
		return manager;
	}


	public void setManager(String manager) {
		this.manager = manager;
	}


	public AlarmRemarkCategory getCategory() {
		return category;
	}


	public void setCategory(AlarmRemarkCategory category) {
		this.category = category;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Date getRemarkTime() {
		return remarkTime;
	}


	public void setRemarkTime(Date remarkTime) {
		this.remarkTime = remarkTime;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	


}
