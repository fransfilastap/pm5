package id.franspratama.geol.core.services;

import java.util.List;

import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.AlarmRemark;
import id.franspratama.geol.core.pojo.NE;
import id.franspratama.geol.web.api.ActiveAlarmDetailDTO;


/**
 * 
 * Strategy Interface for matching remark and alarm
 * 
 * 
 * 
 * @author fransfilastap
 *
 */
public interface IActiveAlarmDetailFulfillmentStrategy {
	
	/**
	 * <p>method skeleton for matching alarm with it's remark</p>
	 * 
	 * 
	 * 
	 * 
	 * @param alarms
	 * @param remarks
	 * @return
	 */
	public List<ActiveAlarmDetailDTO> fill( List<ActiveAlarm> alarms, List<AlarmRemark> remarks, List<NE> ne);

}
