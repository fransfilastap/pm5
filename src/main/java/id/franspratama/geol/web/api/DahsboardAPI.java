package id.franspratama.geol.web.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import id.franspratama.geol.core.dao.AvailabilityService;
import id.franspratama.geol.core.dao.INEDownRepository;
import id.franspratama.geol.core.dao.NEDownService;
import id.franspratama.geol.core.pojo.ClusterAvailability;
import id.franspratama.geol.core.pojo.DailyRegionAvailability;
import id.franspratama.geol.core.pojo.NEDown;
import id.franspratama.geol.core.pojo.NEDownAging;
import id.franspratama.geol.core.pojo.NEDownMovement;
import id.franspratama.geol.core.pojo.NEDownStatus;
import id.franspratama.geol.core.pojo.NeDownSummary;
import id.franspratama.geol.core.pojo.RegionAvailability;
import id.franspratama.geol.core.pojo.SpanType;
import id.franspratama.geol.core.pojo.TimeSpan;


@RestController
@RequestMapping("/api/dashboard")
public class DahsboardAPI {
	
	public @Autowired AvailabilityService availabilityService;
	
	public @Autowired NEDownService neDownService;
	
	@RequestMapping(value="/nedowntrend",consumes="application/json",produces="application/json",method=RequestMethod.POST)
	public Map<String,List<NEDownMovement>> neDownMovement(@RequestBody NEDownMovementRequestHolder holder){
		
		List<NEDownMovement> movements = null;
		
		if( holder.getSpanTypeValue() == SpanType.HOURLY )
			movements = neDownService.getNEDownMovementHourly(holder.getAgeValue(), holder.getTimeSpanValue());
		else
			movements = neDownService.getNEDownMovementDaily(holder.getAgeValue(), holder.getTimeSpanValue());
		
		Map<String, List<NEDownMovement>> map = new HashMap<>();
		
		movements.stream().forEach(ne->{
			String region = ne.getRegion();
			if( map.get( region ) == null ){
				map.put( region, new ArrayList<>());
			}
			map.get(region).add(ne);
		});
		
		return map;
	}
	
	@RequestMapping(value="/clusteravailbility",consumes="application/json",produces="application/json",method=RequestMethod.POST)
	public Map<String,List<ClusterAvailability>> getClusterAvailabilityTrend(@RequestBody AvailabilityRequestHolder holder){
		List<ClusterAvailability> availabilities = availabilityService.getAvailabilities(
									holder.getNetworkTechnologyValue(), 
									holder.getRegionValue(), 
									holder.getTimeSpanValue(),
									ClusterAvailability.class);
		
		
		Map<String, List<ClusterAvailability>> map = new HashMap<>();
		
		availabilities.forEach(nav->{
			if( map.get(nav.getCluster()) == null ){
				map.put(nav.getCluster(), new ArrayList<ClusterAvailability>());
			}
			map.get(nav.getCluster()).add(nav);
		});
		
		return map;
	}
	
	@RequestMapping(value="/regionavailability",consumes="application/json",produces="application/json",method=RequestMethod.POST)
	public Map<String, List<RegionAvailability>> getRegionAvailabilityTrend(@RequestBody AvailabilityRequestHolder holder){
		List<RegionAvailability> availabilities = availabilityService
															.getAvailabilities(holder.getNetworkTechnologyValue(), holder.getRegionValue(), holder.getTimeSpanValue(), RegionAvailability.class);
		
		Map<String, List<RegionAvailability>> map = new HashMap<>();
		
		availabilities.forEach(nav->{
			if( map.get(nav.getRegion()) == null ){
			map.put(nav.getRegion(), new ArrayList<RegionAvailability>());
			}
			map.get(nav.getRegion()).add(nav);
		});
		
		return map;
	}
	
	
	@RequestMapping(value="/dailyregionavailability",consumes="application/json",produces="application/json",method=RequestMethod.POST)
	public Map<String, List<DailyRegionAvailability>> getDailyRegionAvailabilityTrend(@RequestBody AvailabilityRequestHolder holder){
		
		List<DailyRegionAvailability> availabilities = availabilityService
															.getAvailabilities(
																	holder.getNetworkTechnologyValue(),
																	holder.getRegionValue(),
																	holder.getTimeSpanValue(),
																	DailyRegionAvailability.class
															);
		
		Map<String, List<DailyRegionAvailability>> map = new HashMap<>();
		
		availabilities.forEach(nav->{
			if( map.get(nav.getRegion()) == null ){
			map.put(nav.getRegion(), new ArrayList<DailyRegionAvailability>());
			}
			map.get(nav.getRegion()).add(nav);
		});
		
		return map;
	}
	
	@RequestMapping(value="/nedownsummary",produces="application/json",method=RequestMethod.GET)
	public List<NEDown> getNeDownSummary(){
		return neDownService.getNEDownSummary();
	}

	@RequestMapping(value="/nedownstatushourly",produces="application/json",method=RequestMethod.GET)
	public List<NEDownStatus> getNEDownStatusHourly(){
		return neDownService.getNeDownStatusHourly();
	}
	
	@RequestMapping(value="/nedownstatusdaily",produces="application/json",method=RequestMethod.GET)
	public List<NEDownStatus> getNEDownStatusDaily(){
		return neDownService.getNeDownStatusDaily();
	}
	
	@RequestMapping(value="/nedownaging",produces="application/json",method=RequestMethod.GET)
	public List<NEDownAging> getCurrentNEDownAging(){
		return neDownService.getCurrentNEDownAging();
	}
	
}
