package id.franspratama.geol.web.api;

import java.util.List;

public class ActiveAlarmDetailResponseWrapper {

	private int total;
	private List<ActiveAlarmDetailDTO> rows;
	
	
	public ActiveAlarmDetailResponseWrapper(int total, List<ActiveAlarmDetailDTO> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}


	public ActiveAlarmDetailResponseWrapper() {
		super();
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public List<ActiveAlarmDetailDTO> getRows() {
		return rows;
	}


	public void setRows(List<ActiveAlarmDetailDTO> rows) {
		this.rows = rows;
	}
	
	
	
}
