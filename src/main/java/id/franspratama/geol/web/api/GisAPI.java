package id.franspratama.geol.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.franspratama.geol.core.dao.ILebaranRouteRepository;
import id.franspratama.geol.core.dao.IVipGroupRepository;
import id.franspratama.geol.core.pojo.LebaranRoute;
import id.franspratama.geol.core.pojo.LebaranRouteType;
import id.franspratama.geol.core.pojo.VipGroup;
import id.franspratama.geol.core.services.IGisService;

/**
 * <p>This class is provide information of sites based on geographical information.
 * Information returned by this API class contains geographical properties such as latitude and longitude
 * that will be usefull for visualized in map</p>
 * 
 * @author fransfilastap
 *
 */

@RestController("/gis")
public class GisAPI {

	@Autowired
	IGisService gisServices;
	
	@Autowired
	IVipGroupRepository vipGroupRepository;
	
	@Autowired
	ILebaranRouteRepository lebaranRouteRepository;

	@Autowired
	PasswordEncoder encoder;
	
	
	
	
	
	/**
	 * 
	 * <p> Get a set of GisDTO </p>
	 * 
	 * 
	 * 
	 * @param lat
	 * @param lng
	 * @param rad
	 * @return
	 */
	@RequestMapping(value="/statWithinRadius",method=RequestMethod.GET,produces="application/json")
	public Set<GisDTO> getSiteStatusWithinRadius( @RequestParam(name="lat",required=true) double lat,
										    @RequestParam(name="lng",required=true) double lng,
										    @RequestParam(name="rad",required=true) double rad,
										    @RequestParam(name="type",required=true) String type){
		
		Set<GisDTO> dtos = gisServices.getSiteStatus(lat, lng, rad);
		
		if( !type.equalsIgnoreCase( "all" ) ){
			return dtos.stream().filter(dto->{
				return dto.getTechnology().getTechnology().equalsIgnoreCase( type );
			}).collect(Collectors.toSet());
		}
			
		return dtos;
	}
	
	
	
	
	
	/**
	 * 
	 * Get site status within radius of circle with a site in center 
	 * 
	 * @param siteid 
	 * @param rad , radius of circle
	 * @return
	 */
	@RequestMapping(value="/statWithinRadiusOfSite",method=RequestMethod.GET,produces="application/json")
	public Set<GisDTO> getSiteStatusWithinRadius(@RequestParam(name="site",required=true) String siteid,
												@RequestParam(name="rad",required=true) double rad,
												@RequestParam(name="type",required=true) String type){
		
		
		Set<GisDTO> dtos = gisServices.getSiteStatus(siteid, rad);
		
		if( !type.equalsIgnoreCase( "all" ) ){
			return dtos.stream().filter(dto->{
				return dto.getTechnology().getTechnology().equalsIgnoreCase( type );
			}).collect(Collectors.toSet());
		}
			
		return dtos;
	}
	
	
	
	
	
	
	/**
	 * get site status near path / line
	 * 
	 * 
	 * @param locationWrapper
	 * @return
	 */
	@RequestMapping(value="/statNearPath",method=RequestMethod.POST,produces="application/json",consumes="application/json")
	public Set<GisDTO> getSiteStatusNearPath(@RequestBody LocationDTOWrapper locationWrapper ){
		return gisServices.getSiteStatusNearPath( locationWrapper.getPoints() );
	}
	
	
	
	
	
	
	/**
	 * Get brief information of selected site
	 * 
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value="/getSiteInformation",method=RequestMethod.GET,produces="application/json")
	public GisDTO getBriefInformation(@RequestParam(name="site",required=true) String siteId){
		return gisServices.getBriefInformationOfSite(siteId);
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param siteId
	 * @return Set of active alarm
	 */
	@RequestMapping(value="/getSiteAlarm",method=RequestMethod.GET,produces="application/json")
	public Set<ActiveAlarmDTO> getSitesActiveAlarm1(@RequestParam(name="site",required=true)String siteId){
		return gisServices.getSiteAlarms(siteId);
	}

	
	
	
	
	
	/**
	 * 
	 * @param siteId
	 * @return active alarm map
	 */
	@RequestMapping(value="/getSiteAlarm2",method=RequestMethod.GET,produces="application/json")
	public Map<String,Object> getSitesActiveAlarm2(@RequestParam(name="site",required=true)String siteId){
		
		Map<String,Object> map = new HashMap<>();
						   map.put("site", gisServices.getBriefInformationOfSite(siteId));
						   map.put("alarm",gisServices.getSiteAlarms(siteId));
		return map;
	}
	
	/**
	 * 
	 * @param groupid
	 * @return collection of site status
	 */
	@RequestMapping(value="/vipgroupstats",method=RequestMethod.GET,produces="application/json")
	public Set<GisDTO> getVipGroupStats(
										@RequestParam(name="group",required=true) long groupid,
										@RequestParam(name="type",required=true) String type){
		
		VipGroup group = vipGroupRepository.findOne( groupid );
		
		Set<GisDTO> dtos = gisServices.getSiteStatus(group).stream().filter(dto->{
			return ( type.equalsIgnoreCase("all") ? true : dto.getTechnology().getTechnology().equalsIgnoreCase( type ) );
		}).collect(Collectors.toSet());
		
		return dtos;
	}
	
	
	
	
	
	
	/**
	 * Get Site Availability
	 * 
	 * 
	 * @param siteid
	 * @param version
	 * @return
	 */
	@RequestMapping(value="/sites-availability",method=RequestMethod.GET,produces="application/json")
	public Map<String, List<SiteAvailabilityDTO>> getSiteAvailability( 
			@RequestParam(name="site", required=true) String siteid,
			@RequestParam(name="version",required=false) String version){
		
		Map<String, List<SiteAvailabilityDTO>> dtos = gisServices.getSiteAvailability(siteid, version).stream().collect(Collectors.groupingBy(SiteAvailabilityDTO::getCellName));

		return dtos;
	}
	
	
	
	
	
	
	/**
	 * Get lebaran route list
	 * 
	 * 
	 * 
	 * @param region
	 * @param type
	 * @return json
	 */
	@RequestMapping(value="/fetch-lebaran-route",method=RequestMethod.GET, produces="application/json")
	public Set<LebaranRoute> getLebaranRoutesByType(
								@RequestParam(name="region", required=true) String region, 
								@RequestParam(name="type",required=true) int type){
		
		Set<LebaranRoute> lebaranRoutes = lebaranRouteRepository.findByTypeAndRegion(new LebaranRouteType( type, "" ), region);
		
		return lebaranRoutes;
	}
	
	
	
	
	
	/**
	 * 
	 * Get lebaran route status
	 * 
	 * 
	 * 
	 * @param route
	 * @param version
	 * @return json
	 */
	@RequestMapping(value="/lebaran-route-status",method=RequestMethod.GET, produces="application/json")
	public Set<GisDTO> getLebaranSiteStatus( 
					@RequestParam(name="route",required=true) int route,
					@RequestParam(name="version",required=false) String version
				){
		
		LebaranRoute oRoute = new LebaranRoute();
					 oRoute.setId( route );
		
		Set<GisDTO> dtos = gisServices.getSiteStatus( oRoute ).stream().filter(dto->{
			return ( version.equalsIgnoreCase("all") ? true : dto.getTechnology().getTechnology().equalsIgnoreCase( version ) );
		}).collect(Collectors.toSet());
		
		
		return dtos;
	}

}
