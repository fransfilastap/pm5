package id.franspratama.geol.core.dao;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.AlarmFilter;
import id.franspratama.geol.core.pojo.Site;
import id.franspratama.geol.web.api.GisDTO;


@Component("alarmToSiteMatcherStrategyV1")
public class AlarmToSiteMatcherStrategy implements IAlarmToSiteStartegy{

	@Override
	public Set<GisDTO> match(List<Site> sites, List<ActiveAlarm> alarms, List<AlarmFilter> filters) {
		
		Map<String, StringBuilder> alarmMap = new HashMap<>();
		
		alarms.stream().forEach( a ->{
			if( alarmMap.get( a.getSiteId() ) == null ){
				alarmMap.put( a.getSiteId() , new StringBuilder() );
			}
			alarmMap.get(a.getSiteId()).append( "#"+a.getSummary() );
		});
		
		
		Set<GisDTO> result = new HashSet<>();
		
		for(Site s : sites){
			
			GisDTO gisDTO = new GisDTO();
				   gisDTO.setLatitude( s.getLatitue() );
				   gisDTO.setLongitude( s.getLongitude() );
				   gisDTO.setSiteId( s.getSiteId() );
				   gisDTO.setSiteName( s.getSiteName() );
		
		   if( alarmMap.get(s.getSiteId()) == null ){
				result.add(gisDTO);
				continue;
		   }
		   
		   for( AlarmFilter f : filters ){
				if( org.apache.commons.lang3.StringUtils.contains( alarmMap.get(s.getSiteId()), f.getFilter() ) ){
					gisDTO.setSeverity(f.getSeverity());
					break;
				}
			}
   
			
			result.add(gisDTO);
		}
		
		return result;
	}

}
