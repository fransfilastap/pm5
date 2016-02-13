package id.franspratama.geol.web.api;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.franspratama.geol.core.dao.IGisService;

@RestController("/gis")
public class GisAPI {

	@Autowired
	IGisService gisServices;
	
	@RequestMapping(value="/statWithinRadius",method=RequestMethod.GET,produces="application/json")
	public Set<GisDTO> getSiteStatusWithinRadius( @RequestParam(name="lat",required=true) double lat,
										    @RequestParam(name="lng",required=true) double lng,
										    @RequestParam(name="rad",required=true) double rad){
		return gisServices.getSiteStatus(lat, lng, rad);
	}
	
	@RequestMapping(value="/statWithinRadiusOfSite",method=RequestMethod.GET,produces="application/json")
	public Set<GisDTO> getSiteStatusWithinRadius(@RequestParam(name="site",required=true) String siteid,
												@RequestParam(name="rad",required=true) double rad){
		return gisServices.getSiteStatus(siteid, rad);
	}
	
	@RequestMapping(value="/statNearPath",method=RequestMethod.POST,produces="application/json",consumes="application/json")
	public Set<GisDTO> getSiteStatusNearPath(@RequestBody List<LocationDTO> locations){
		return gisServices.getSiteStatusNearPath(locations);
	}
	
}
