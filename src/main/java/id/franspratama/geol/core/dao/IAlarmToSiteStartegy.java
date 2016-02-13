package id.franspratama.geol.core.dao;


import java.util.List;
import java.util.Set;

import id.franspratama.geol.core.pojo.Site;
import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.AlarmFilter;
import id.franspratama.geol.web.api.GisDTO;

public interface IAlarmToSiteStartegy {
	public Set<GisDTO> match(List<Site> sites, List<ActiveAlarm> alarms,List<AlarmFilter> filters);
}
