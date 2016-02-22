package id.franspratama.geol.web.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.franspratama.geol.core.dao.IGisService;
import id.franspratama.geol.core.dao.IVipGroupRepository;
import id.franspratama.geol.core.pojo.VipGroup;

/**
 * This class is provide information of sites based on geographical information.
 * Information returned by this API class contains geographical properties such as latitude and longitude
 * that will be usefull for visualized in map
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
	PasswordEncoder encoder;
	
	
	/**
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
										    @RequestParam(name="rad",required=true) double rad){
		return gisServices.getSiteStatus(lat, lng, rad);
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
												@RequestParam(name="rad",required=true) double rad){
		return gisServices.getSiteStatus(siteid, rad);
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
	public Set<GisDTO> getVipGroupStats(@RequestParam(name="group",required=true) long groupid){
		VipGroup group = vipGroupRepository.findOne( groupid );
		return gisServices.getSiteStatus(group);
	}
	
	
	
	
}
