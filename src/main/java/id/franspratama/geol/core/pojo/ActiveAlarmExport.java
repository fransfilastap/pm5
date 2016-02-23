package id.franspratama.geol.core.pojo;

import java.util.Date;

public class ActiveAlarmExport {
	
	private String group;
	private String siteId;
	private String node;
	private String towerId;
	private String priority;
	private String summary;
	private String ttno;
	private String wo;
	private String pic;
	private String woStatus;
	private String spv;
	private String mgr;
	private String type;
	private Date lastOccurrence;
	private String down;
	
	public ActiveAlarmExport(String group, String siteId, String node, String towerId, String priority, String summary,
			String ttno, String wo, String pic, String woStatus, String spv, String mgr, String type,
			Date lastOccurrence, String down) {
		super();
		this.group = group;
		this.siteId = siteId;
		this.node = node;
		this.towerId = towerId;
		this.priority = priority;
		this.summary = summary;
		this.ttno = ttno;
		this.wo = wo;
		this.pic = pic;
		this.woStatus = woStatus;
		this.spv = spv;
		this.mgr = mgr;
		this.type = type;
		this.lastOccurrence = lastOccurrence;
		this.down = down;
	}

	public ActiveAlarmExport() {
		super();
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getTowerId() {
		return towerId;
	}

	public void setTowerId(String towerId) {
		this.towerId = towerId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTtno() {
		return ttno;
	}

	public void setTtno(String ttno) {
		this.ttno = ttno;
	}

	public String getWo() {
		return wo;
	}

	public void setWo(String wo) {
		this.wo = wo;
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

	public String getSpv() {
		return spv;
	}

	public void setSpv(String spv) {
		this.spv = spv;
	}

	public String getMgr() {
		return mgr;
	}

	public void setMgr(String mgr) {
		this.mgr = mgr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getLastOccurrence() {
		return lastOccurrence;
	}

	public void setLastOccurrence(Date lastOccurrence) {
		this.lastOccurrence = lastOccurrence;
	}

	public String getDown() {
		return down;
	}

	public void setDown(String down) {
		this.down = down;
	}
	
	
	

	
}
