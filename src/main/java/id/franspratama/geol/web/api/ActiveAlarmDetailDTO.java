package id.franspratama.geol.web.api;

import java.util.Date;

public class ActiveAlarmDetailDTO extends ActiveAlarmDTO{
	
	private String category;
	private String remark;
	private Date time;
	private String remarker;
	
	public ActiveAlarmDetailDTO(String category, String remark, Date time, String remarker) {
		super();
		this.category = category;
		this.remark = remark;
		this.time = time;
		this.remarker = remarker;
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

	public void setTime(Date time) {
		this.time = time;
	}

	public String getRemarker() {
		return remarker;
	}

	public void setRemarker(String remarker) {
		this.remarker = remarker;
	}
	
	
	

}
