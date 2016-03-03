package id.franspratama.geol.web.api;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Collection;
import java.util.Map;

import id.franspratama.geol.core.pojo.ActiveAlarmExport;
import id.franspratama.geol.core.pojo.AlarmFilter;
import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.Site;
import id.franspratama.geol.core.pojo.WFMTicketAndWorkOrder;

public class ActiveAlarmExportBuilder {
	
	public static Set<ActiveAlarmExport> export(Collection<ActiveAlarm> activeAlarms, 
										 Collection<Site> sites,
										 Collection<AlarmFilter> filters,
										 Collection<WFMTicketAndWorkOrder> wo,
										 String group){
		
		Set<ActiveAlarmExport> exports 	= new HashSet<>();
		final Map<String, Site> siteMap = sites.stream().collect(Collectors.toMap( Site::getSiteId, Function.identity()));
		final Map<String, WFMTicketAndWorkOrder> woMap = wo.stream().collect(Collectors.toMap(WFMTicketAndWorkOrder::getTicketId, Function.identity()));
		
		activeAlarms.parallelStream().forEach( act ->{
			
			Site sitex 						= siteMap.get( act.getSiteId() );
			
			ActiveAlarmExport export = new ActiveAlarmExport();
							  export.setAlarm(act);
							  export.setSite(sitex);
							  export.setGroup(group);
							  export.setTicketAndWorkorder( woMap.get( act.getTTNO() ) != null ? woMap.get( act.getTTNO() ) : new WFMTicketAndWorkOrder() );
			
			boolean isOss = false;
							  
			for(AlarmFilter f : filters){
				if( org.apache.commons.lang3.StringUtils.contains( act.getSummary(), f.getFilter() ) ){
					isOss = true;
					break;
				}
			}
			
			if( isOss  )
				export.setStatus( ActiveAlarmExport.OssStatus.OSS );
			else
				export.setStatus( ActiveAlarmExport.OssStatus.IN_SERVICE );
			
			exports.add(export);
		});
		
		return exports;
	}
	
}
