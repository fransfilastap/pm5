package id.franspratama.geol.core.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import id.franspratama.geol.core.dao.IActiveAlarmRepository;
import id.franspratama.geol.core.dao.IAlarmRemarkRepository;
import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.AlarmRemark;
import id.franspratama.geol.web.api.ActiveAlarmDetailDTO;
import id.franspratama.geol.web.api.ActiveAlarmDetailResponseWrapper;

@Service
public class ActiveAlarmService implements IActiveAlarmService{

	@Autowired
	IActiveAlarmRepository activeAlarmRepository;
	
	@Autowired
	IAlarmRemarkRepository alarmRemarkRepository;
	
	@Override
	public ActiveAlarmDetailResponseWrapper getActiveAlarmList(Integer pageNumber, Sort  sort, String[] fields) {
		
		PageRequest pageRequest 				 	= new PageRequest(pageNumber, PAGE_SIZE,sort);
		Page<ActiveAlarm> page  				 	= activeAlarmRepository.findAll( pageRequest );		
		List<ActiveAlarm> rows  				 	= page.getContent();
		
		List<String> siteIds 					 	= rows.stream().map(ActiveAlarm::getSiteId).collect(Collectors.toList());
		List<AlarmRemark> remarks 				 	= alarmRemarkRepository.getActiveAlarmRemarks();
		Map<String, List<AlarmRemark>> remarkMaps	= remarks.stream().collect(Collectors.groupingBy( AlarmRemark::getSiteId ));
		
		ActiveAlarmDetailResponseWrapper wrapper 	= new ActiveAlarmDetailResponseWrapper();
		List<ActiveAlarmDetailDTO> dtos 		 	= new ArrayList<>();
		
		rows.parallelStream().forEach( activealarm -> {
			
			ActiveAlarmDetailDTO dto 			 	= new ActiveAlarmDetailDTO();
			
			dto.setAlarmName( activealarm.getAlarmName() );
			dto.setFirstOccurrence( activealarm.getFirstOccurrence() );
			dto.setFirstReceived( activealarm.getFirstReceived()  );
			dto.setLastOccurrence( activealarm.getFirstReceived() );
			dto.setLastReceived( activealarm.getLastReceived() );
			dto.setNode( activealarm.getNode() );
			
			
			dtos.add(dto);
		});
		
		wrapper.setRows( dtos );
		wrapper.setTotal( 700000 );
		
		return wrapper;
	}



}
