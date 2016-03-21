package id.franspratama.geol.web.api;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.franspratama.geol.core.services.ActiveAlarmService;

/**
 * 
 * 
 * 
 * 
 * 
 * 
 * @author fransfilastap
 *
 */
@RestController
public class ActiveAlarmAPI {

	@Autowired
	ActiveAlarmService activeAlarmService;
	
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param order
	 * @param page
	 * @param rows
	 * @param sort
	 * @return
	 */
	@RequestMapping(value="/active-alarm-list",method=RequestMethod.POST)
	public ActiveAlarmDetailResponseWrapper getActiveAlarmList( 	
			@RequestParam(name="order",required=false) String order, 
			@RequestParam(name="page",required=true) int page, 
			@RequestParam(name="rows",required=true) int rows, 
			@RequestParam(name="sort",required=false) String sort 
		 ){
		
		Integer pageNumber 			= page;
		Sort.Direction direction	= Sort.DEFAULT_DIRECTION.ASC;
		String[] fields 			= {};
		String sortField 			= "LastOccurrence"; //default
		
		if( order != null ){
			if( order.equalsIgnoreCase("desc") ){
				direction = Sort.DEFAULT_DIRECTION.DESC;
			}
		}
		
		if( sort != null ){
			if( sort.equalsIgnoreCase("sort") ){
				fields = new String[]{ sort };
			}			
		}
		
		Sort sSort = new Sort(direction, sortField);
		
		ActiveAlarmDetailResponseWrapper responseWrapper = activeAlarmService.getActiveAlarmList(pageNumber, sSort, fields);
		
		return responseWrapper;
	}
	
}
