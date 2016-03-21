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
import id.franspratama.geol.core.dao.INeRepository;
import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.AlarmRemark;
import id.franspratama.geol.core.pojo.NE;
import id.franspratama.geol.web.api.ActiveAlarmDetailDTO;
import id.franspratama.geol.web.api.ActiveAlarmDetailResponseWrapper;

@Service
public class ActiveAlarmService implements IActiveAlarmService{

	@Autowired
	IActiveAlarmRepository activeAlarmRepository;
	
	@Autowired
	IAlarmRemarkRepository alarmRemarkRepository;
	
	@Autowired
	INeRepository neDownRepository;
	
	@Autowired
	IActiveAlarmDetailFulfillmentStrategy strategy;
	
	
	@Override
	public ActiveAlarmDetailResponseWrapper getActiveAlarmList(Integer pageNumber, Sort  sort, String[] fields) {
		
		PageRequest pageRequest 				 	= new PageRequest(pageNumber, PAGE_SIZE,sort);
		Page<ActiveAlarm> page  				 	= activeAlarmRepository.findAll( pageRequest );		
		List<ActiveAlarm> rows  				 	= page.getContent();
		
		List<NE> neList								= neDownRepository.getActiveAlarmNe();
		List<AlarmRemark> remarks 				 	= alarmRemarkRepository.getActiveAlarmRemarks();

		
		ActiveAlarmDetailResponseWrapper wrapper 	= new ActiveAlarmDetailResponseWrapper();
		List<ActiveAlarmDetailDTO> dtos 		 	= strategy.fill(rows, remarks, neList);
		
		long total = activeAlarmRepository.count();
		
		wrapper.setRows( dtos );
		wrapper.setTotal( (int) total );
		
		return wrapper;
	}



}
