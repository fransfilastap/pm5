package id.franspratama.geol.core.services;

import java.util.List;

import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.AlarmRemark;
import id.franspratama.geol.web.api.ActiveAlarmDetailDTO;

public interface IActiveAlarmDetailFulfillmentStrategy {
	
	public List<ActiveAlarmDetailDTO> fill( List<ActiveAlarm> alarms, List<AlarmRemark> remarks);

}
