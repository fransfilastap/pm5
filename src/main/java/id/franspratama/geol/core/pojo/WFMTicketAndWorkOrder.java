package id.franspratama.geol.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wfm_tt_wo",catalog="geolv2")
public class WFMTicketAndWorkOrder {
	
	@Id @GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="ticket_id")
	private String ticketId;
	
	@Column(name="wo_id")
	private String woId;
	
	@Column(name="pic")
	private String pic;
	
	@Column(name="device_id")
	private String deviceId;
	
	@Column(name="site_id")
	private String siteId;
	
	@Column(name="notification_type")
	private String notificationType;
	
	@Column(name="wo_status")
	private String woStatus;
	
	@Column(name="insert_time")
	private Date insertTime;
	
	@Column(name="update_time")
	private Date updateTime;


	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public WFMTicketAndWorkOrder() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getWoId() {
		return woId;
	}

	public void setWoId(String woId) {
		this.woId = woId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getWoStatus() {
		return woStatus;
	}

	public void setWoStatus(String woStatus) {
		this.woStatus = woStatus;
	}

}
