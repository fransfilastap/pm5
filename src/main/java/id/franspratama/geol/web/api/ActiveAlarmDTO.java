package id.franspratama.geol.web.api;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import id.franspratama.geol.util.CustomDateTimeSerializer;

public class ActiveAlarmDTO {
	
    private String alarmName;
    private String node;
    private String siteId;
    private String site;
    private String ttno;
    private Date firstOccurrence;
    private Date lastOccurrence;
    private Date lastReceived;
    private Date firstReceived;
    private String summary;
    private String severity;
    private String woId;
    private String pic;
    private String woStatus;
    
	public ActiveAlarmDTO() {
		super();
	}
	
	public ActiveAlarmDTO(String alarmName, String node, String siteId, String site, String ttno, Date firstOccurrence,
			Date lastOccurrence, Date lastReceived, Date firstReceived, String summary, String severity, String woId,
			String pic, String woStatus) {
		super();
		this.alarmName = alarmName;
		this.node = node;
		this.siteId = siteId;
		this.site = site;
		this.ttno = ttno;
		this.firstOccurrence = firstOccurrence;
		this.lastOccurrence = lastOccurrence;
		this.lastReceived = lastReceived;
		this.firstReceived = firstReceived;
		this.summary = summary;
		this.severity = severity;
		this.woId = woId;
		this.pic = pic;
		this.woStatus = woStatus;
	}





	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getWoStatus() {
		return woStatus;
	}





	public void setWoStatus(String woStatus) {
		this.woStatus = woStatus;
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

	public String getTtno() {
		return ttno;
	}

	public void setTtno(String ttno) {
		this.ttno = ttno;
	}

	public Date getFirstOccurrence() {
		return firstOccurrence;
	}

	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public void setFirstOccurrence(Date firstOccurrence) {
		this.firstOccurrence = firstOccurrence;
	}

	public Date getLastOccurrence() {
		return lastOccurrence;
	}

	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public void setLastOccurrence(Date lastOccurrence) {
		this.lastOccurrence = lastOccurrence;
	}

	public Date getLastReceived() {
		return lastReceived;
	}

	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public void setLastReceived(Date lastReceived) {
		this.lastReceived = lastReceived;
	}

	public Date getFirstReceived() {
		return firstReceived;
	}

	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public void setFirstReceived(Date firstReceived) {
		this.firstReceived = firstReceived;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getWoId() {
		return woId;
	}

	public void setWoId(String woId) {
		this.woId = woId;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	
	
}
