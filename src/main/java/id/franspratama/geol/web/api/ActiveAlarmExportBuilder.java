package id.franspratama.geol.web.api;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.List;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import id.franspratama.geol.core.pojo.ActiveAlarmExport;
import id.franspratama.geol.core.pojo.AlarmFilter;
import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.Site;
import id.franspratama.geol.core.pojo.WFMTicketAndWorkOrder;

public class ActiveAlarmExportBuilder {
	
	public Set<ActiveAlarmExport> export(Collection<ActiveAlarm> activeAlarms, 
										 Collection<Site> sites,
										 Collection<AlarmFilter> filters,
										 Collection<WFMTicketAndWorkOrder> wo,
										 String group){
		
		Set<ActiveAlarmExport> exports 	= new HashSet<>();
		final Map<String, Site> siteMap = sites.stream().collect(Collectors.toMap( Site::getSiteId, Function.identity()));
		
		activeAlarms.parallelStream().forEach( act ->{
			
			Site sitex = siteMap.get( act.getSiteId() );
			
			ActiveAlarmExport export = new ActiveAlarmExport();
							  export.setGroup(group);
							  export.setLastOccurrence( act.getLastOccurrence() );
							  export.setPriority( sitex.getPriority().getPriority() );
							  //export.setMgr(  );
			exports.add(export);
		});
		
		
		
		return exports;
	}
	
}
