package id.franspratama.geol.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="activealarm", catalog="netcool")
public class ActiveAlarm implements java.io.Serializable{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -9094947659406842618L;

	/**
	 * 
	 */
	@Id @GeneratedValue
	@Column(name="eventid")
	private long eventID;
	
	@Column(name="alarmname")
	private String alarmName;
	
	@Column(name="node")
	private String node;
	
	@Column(name="siteid")
	private String siteId;
	
	@Column(name="site")
	private String site;
	
	@Column(name="zone")
	private String zone;
	
	@Column(name="severity")
	private String severity;
	
	@Column(name="ttno")
	private String TTNO;
	
	@Column(name="firstoccurrence")
	private Date firstOccurrence;
	
	@Column(name="lastoccurrence")
	private Date lastOccurrence;
	
	@Column(name="firstreceived")
	private Date firstReceived;
	
	@Column(name="lastreceived")
	private Date lastReceived;
	
	@Column(name="summary")
	private String summary;
	
	@Column(name="manager")
	private String manager;
	

	
	public ActiveAlarm(long eventID, String alarmName, String node, String siteId, String site, String zone, String severity,
			String tTNO, Date firstOccurrence, Date lastOccurrence, Date firstReceived, Date lastReceived,
			String summary, String manager) {
		super();
		this.eventID = eventID;
		this.alarmName = alarmName;
		this.node = node;
		this.siteId = siteId;
		this.site = site;
		this.zone = zone;
		this.severity = severity;
		this.TTNO = tTNO;
		this.firstOccurrence = firstOccurrence;
		this.lastOccurrence = lastOccurrence;
		this.firstReceived = firstReceived;
		this.lastReceived = lastReceived;
		this.summary = summary;
		this.manager = manager;
	}

	public ActiveAlarm(){
		
	}

	public long getEventID() {
		return eventID;
	}

	public void setEventID(long eventID) {
		this.eventID = eventID;
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

	public String getTTNO() {
		return TTNO;
	}

	public void setTTNO(String tTNO) {
		TTNO = tTNO;
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

	@Override
	public String toString() {
		return "Alarm [node=" + node + ", siteId=" + siteId + ", site=" + site + ", zone=" + zone + ", severity="
				+ severity + ", TTNO=" + TTNO + ", firstOccurrence=" + firstOccurrence + ", summary=" + summary
				+ ", manager=" + manager + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((TTNO == null) ? 0 : TTNO.hashCode());
		result = prime * result + ((alarmName == null) ? 0 : alarmName.hashCode());
		result = prime * result + (int) (eventID ^ (eventID >>> 32));
		result = prime * result + ((firstOccurrence == null) ? 0 : firstOccurrence.hashCode());
		result = prime * result + ((firstReceived == null) ? 0 : firstReceived.hashCode());
		result = prime * result + ((lastOccurrence == null) ? 0 : lastOccurrence.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		result = prime * result + ((severity == null) ? 0 : severity.hashCode());
		result = prime * result + ((site == null) ? 0 : site.hashCode());
		result = prime * result + ((siteId == null) ? 0 : siteId.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		result = prime * result + ((zone == null) ? 0 : zone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActiveAlarm other = (ActiveAlarm) obj;
		if (TTNO == null) {
			if (other.TTNO != null)
				return false;
		} else if (!TTNO.equals(other.TTNO))
			return false;
		if (alarmName == null) {
			if (other.alarmName != null)
				return false;
		} else if (!alarmName.equals(other.alarmName))
			return false;
		if (eventID != other.eventID)
			return false;
		if (firstOccurrence == null) {
			if (other.firstOccurrence != null)
				return false;
		} else if (!firstOccurrence.equals(other.firstOccurrence))
			return false;
		if (firstReceived == null) {
			if (other.firstReceived != null)
				return false;
		} else if (!firstReceived.equals(other.firstReceived))
			return false;
		if (lastOccurrence == null) {
			if (other.lastOccurrence != null)
				return false;
		} else if (!lastOccurrence.equals(other.lastOccurrence))
			return false;
		if (manager == null) {
			if (other.manager != null)
				return false;
		} else if (!manager.equals(other.manager))
			return false;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		if (severity == null) {
			if (other.severity != null)
				return false;
		} else if (!severity.equals(other.severity))
			return false;
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
			return false;
		if (siteId == null) {
			if (other.siteId != null)
				return false;
		} else if (!siteId.equals(other.siteId))
			return false;
		if (summary == null) {
			if (other.summary != null)
				return false;
		} else if (!summary.equals(other.summary))
			return false;
		if (zone == null) {
			if (other.zone != null)
				return false;
		} else if (!zone.equals(other.zone))
			return false;
		return true;
	}
	
	
	

}
