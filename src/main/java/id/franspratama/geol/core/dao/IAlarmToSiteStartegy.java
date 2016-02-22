package id.franspratama.geol.core.dao;


import java.util.List;
import java.util.Set;

import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.AlarmFilter;
import id.franspratama.geol.web.api.GisDTO;

public interface IAlarmToSiteStartegy<T> {
	public Set<GisDTO> match(List<T> sites, List<ActiveAlarm> alarms,List<AlarmFilter> filters);
}
