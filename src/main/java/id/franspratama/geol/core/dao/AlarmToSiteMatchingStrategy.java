package id.franspratama.geol.core.dao;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.AlarmFilter;
import id.franspratama.geol.core.pojo.Severity;
import id.franspratama.geol.core.pojo.Site;
import id.franspratama.geol.web.api.DTOConverter;
import id.franspratama.geol.web.api.GisDTO;


@Component("alarmToSiteMatcherStrategyV1")
public class AlarmToSiteMatchingStrategy implements IAlarmToSiteStartegy<Site>{

	@Override
	public Set<GisDTO> match(List<Site> sites, List<ActiveAlarm> alarms, List<AlarmFilter> filters) {
		
		Map<String, List<ActiveAlarm>> alarmMap = new HashMap<>();
	
		alarmMap = alarms.stream().collect(Collectors.groupingBy( a -> a.getSiteId() ));
		
		Set<GisDTO> result = new HashSet<>();
		
		for(Site s : sites){
   
		   GisDTO gisDTO = DTOConverter.createGisDTO(s);
			
		   if( alarmMap.get(s.getSiteId()) == null ){
				result.add( gisDTO );
				continue;
		   }
		   
		   for( AlarmFilter f : filters ){
			   long alarmDown = alarmMap.get(s.getSiteId()).stream().filter(a->{
				   return (org.apache.commons.lang3.StringUtils.contains( a.getSummary(), f.getFilter() ));
			   }).count();;
			   
			  if( alarmDown > 0 ){
				  gisDTO.setSeverity(Severity.DOWN);
				  break;
			  } 
			  
			  gisDTO.setSeverity(Severity.MINOR);
		   }
				   
		   result.add(gisDTO);
		}
		
		return result;
	}

}
