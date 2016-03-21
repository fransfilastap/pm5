package id.franspratama.geol.web.api;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import id.franspratama.geol.util.CustomDateTimeSerializer;

/**
 * <h1>Active Alarm DTO</h1>
 * 
 * 
 * 
 * 
 * 
 * @author fransfilastap
 *
 */
public class ActiveAlarmDetailDTO extends ActiveAlarmDTO{
	
	private String category;
	private String remark;
	private Date time;
	private String remarker;
	private String supervisorArea;
	private String managerArea;
	

	public ActiveAlarmDetailDTO(String category, String remark, Date time, String remarker, String supervisor,
			String manager) {
		super();
		this.category = category;
		this.remark = remark;
		this.time = time;
		this.remarker = remarker;
		this.supervisorArea = supervisor;
		this.managerArea = manager;
	}

	public ActiveAlarmDetailDTO() {
		super();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getTime() {
		return time;
	}

	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public void setTime(Date time) {
		this.time = time;
	}

	public String getRemarker() {
		return remarker;
	}

	public void setRemarker(String remarker) {
		this.remarker = remarker;
	}

	public String getSupervisorArea() {
		return supervisorArea;
	}

	public void setSupervisorArea(String supervisor) {
		this.supervisorArea = supervisor;
	}

	public String getManagerArea() {
		return managerArea;
	}

	public void setManagerArea(String manager) {
		this.managerArea = manager;
	}
	
	
	

}
