package id.franspratama.geol.core.pojo;


public class ActiveAlarmExport {
	
	private String group;
	private ActiveAlarm alarm;
	private Site site;
	private WFMTicketAndWorkOrder ticketAndWorkorder;
	private OssStatus status;
	


	public ActiveAlarmExport(String group, ActiveAlarm alarm, Site site, WFMTicketAndWorkOrder ticketAndWorkorder,
			OssStatus status) {
		super();
		this.group = group;
		this.alarm = alarm;
		this.site = site;
		this.ticketAndWorkorder = ticketAndWorkorder;
		this.status = status;
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

	public ActiveAlarm getAlarm() {
		return alarm;
	}

	public void setAlarm(ActiveAlarm alarm) {
		this.alarm = alarm;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public WFMTicketAndWorkOrder getTicketAndWorkorder() {
		return ticketAndWorkorder;
	}

	public void setTicketAndWorkorder(WFMTicketAndWorkOrder ticketAndWorkorder) {
		this.ticketAndWorkorder = ticketAndWorkorder;
	}
	
	
	public OssStatus getStatus() {
		return status;
	}

	public void setStatus(OssStatus status) {
		this.status = status;
	}



	public enum OssStatus {
		OSS, IN_SERVICE
	}

	
	
}
