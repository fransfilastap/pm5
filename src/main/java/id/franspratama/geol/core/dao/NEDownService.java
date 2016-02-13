package id.franspratama.geol.core.dao;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import id.franspratama.geol.core.pojo.AlarmDown;
import id.franspratama.geol.core.pojo.NEDown;
import id.franspratama.geol.core.pojo.NEDownAge;
import id.franspratama.geol.core.pojo.NEDownAging;
import id.franspratama.geol.core.pojo.NEDownMovement;
import id.franspratama.geol.core.pojo.NEDownStatus;
import id.franspratama.geol.core.pojo.TimeSpan;
import id.franspratama.geol.core.pojo.WFMTicketAndWorkOrder;

@Service
public class NEDownService {
	
	@Autowired
	IWFMTicketAndWORepository wfmRepository;
	
	@Autowired
	@Qualifier("nedownJdbcRepository")
	public INEDownRepository neDownRepository;
	
	@Autowired
	INEDownStatusRepository neDownStatusRepository;
	
	@Autowired
	INEDownSummaryRepository neDownSummaryRepository;
	
	@Autowired
	INEDownAgingRepository neDownAgingRepository;
	
	public List<AlarmDown> getCurrentNEDownList(){
		
		List<AlarmDown> alarmList = neDownRepository.getCurrentNeDownList();
		Iterable<WFMTicketAndWorkOrder> ttAndWOList = wfmRepository.findAll();
		
		Map<String,WFMTicketAndWorkOrder> ticketAndWOMap = new HashMap<>();
		
		for (WFMTicketAndWorkOrder wfmTicketAndWorkOrder : ttAndWOList) {
			String ticketId = wfmTicketAndWorkOrder.getTicketId();
			ticketAndWOMap.put( ticketId , wfmTicketAndWorkOrder);
		}
		
		alarmList.stream().forEach(p->{
			if( p.getTtno() != null ){
				p.setTtAndWO( ticketAndWOMap.get(p.getTtno()) );
			}
		});
		
		return alarmList;
	}
	
	public List<NEDownMovement> getNEDownMovementHourly(NEDownAge age,TimeSpan span){
		return neDownRepository.getNeDownMovementHourly(age, span);
	}
	
	public List<NEDownMovement> getNEDownMovementDaily(NEDownAge age,TimeSpan span){
		return neDownRepository.getNeDownMovementDaily(age, span);
	}
	
	
	public List<NEDownStatus> getNeDownStatusHourly(){
		return neDownStatusRepository.getNeDownStatusHourly();
	}
	
	public List<NEDownStatus> getNeDownStatusDaily(){
		return neDownStatusRepository.geNeDownStatusDaily();
	}
	
	public List<NEDown> getNEDownSummary(){
		return neDownSummaryRepository.getNEDownSummary();
	}
	
	public List<NEDownAging> getCurrentNEDownAging(){
		return neDownAgingRepository.getCurrentNEDownAging();
	}
}
