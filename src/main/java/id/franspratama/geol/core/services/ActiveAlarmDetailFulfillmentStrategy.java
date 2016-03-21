package id.franspratama.geol.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.AlarmRemark;
import id.franspratama.geol.core.pojo.NE;
import id.franspratama.geol.web.api.ActiveAlarmDetailDTO;

/**
 * 
 * 
 * 
 * @author fransfilastap
 *
 */

@Component
public class ActiveAlarmDetailFulfillmentStrategy implements IActiveAlarmDetailFulfillmentStrategy{

	@Override
	public List<ActiveAlarmDetailDTO> fill(List<ActiveAlarm> alarms, List<AlarmRemark> remarks, List<NE> ne) {
		
		List<ActiveAlarmDetailDTO> detailDTOList = new ArrayList<>();
		
		Map<String, List<AlarmRemark>> remarkMaps	= remarks.stream().collect(Collectors.groupingBy( AlarmRemark::getSiteId ));
		Map<String, List<NE>> neMap 				= ne.stream().collect( Collectors.groupingBy( NE::getBtsname ) );
		
		alarms.stream().forEach( alarm->{
			
			ActiveAlarmDetailDTO detailDTO = new ActiveAlarmDetailDTO();
			detailDTO.setAlarmName( alarm.getAlarmName() );
			
			if( remarkMaps.get( alarm.getSiteId() ) != null ){
				detailDTO.setCategory( remarkMaps.get( alarm.getSiteId() ).get(0).getCategory().getCategory() );
				detailDTO.setRemark( remarkMaps.get( alarm.getSiteId() ).get(0).getRemark() );
			}
			
			detailDTO.setFirstOccurrence( alarm.getFirstOccurrence() );
			detailDTO.setFirstReceived( alarm.getFirstReceived() );
			detailDTO.setLastOccurrence( alarm.getLastOccurrence() );
			detailDTO.setLastReceived( alarm.getLastReceived() );
			detailDTO.setNode( alarm.getNode() );
			detailDTO.setSeverity( alarm.getSeverity() );
			detailDTO.setSite( alarm.getSite() );
			detailDTO.setTtno( alarm.getTTNO() );
			detailDTO.setZone( alarm.getZone() );
			detailDTO.setSiteId( alarm.getSiteId() );
			detailDTO.setSummary( alarm.getSummary() );
			
			if( neMap.get( alarm.getSiteId() ) != null ){
				detailDTO.setSupervisorArea( neMap.get( alarm.getSiteId() ).get(0).getSupervisorArea() );
				detailDTO.setManagerArea( neMap.get( alarm.getSiteId() ).get(0).getManagerArea() );
			}
			
			detailDTOList.add(detailDTO);
			
		});
		
		
		return detailDTOList;
	}

}
