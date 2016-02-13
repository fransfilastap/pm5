package id.franspratama.geol.web.controller;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import id.franspratama.geol.core.dao.AvailabilityService;
import id.franspratama.geol.core.dao.NEDownService;
import id.franspratama.geol.core.dao.UtilityRepository;
import id.franspratama.geol.core.pojo.AlarmDown;
import id.franspratama.geol.core.pojo.ClusterAvailability;
import id.franspratama.geol.core.pojo.DailyRegionAvailability;
import id.franspratama.geol.core.pojo.NetworkTechnology;
import id.franspratama.geol.core.pojo.Region;
import id.franspratama.geol.core.pojo.RegionAvailability;
import id.franspratama.geol.core.pojo.TimeSpan;
import id.franspratama.geol.view.ClusterAvailabilityExcelView;
import id.franspratama.geol.view.DailyAvailabilityExcelView;
import id.franspratama.geol.view.NEDownExcelView;
import id.franspratama.geol.view.RegionAvailabilityExcelView;

@Controller
public class Dashboard extends BaseController{
	
	@Autowired
	UtilityRepository utilityRepository;
	
	@Autowired
	AvailabilityService navService;
	
	@Autowired
	NEDownService nedownService;
	
	public Dashboard() {
		super();
	}

	@RequestMapping(value={"/","/dashboard"},method=RequestMethod.GET)
	@Secured(value={"ROLE_ADMIN"})
	public ModelAndView dashboard(){

		Map<String,Object> data = new HashMap<>();
		data.put("module", "dashboard");
		data.put("fragment", "dashboard");
		data.put("regions",utilityRepository.getClusterRegion());
		
		return render(data);
	}

	@RequestMapping(value={"/export-dailynav"},method=RequestMethod.GET)
	public ModelAndView exportDailyRegionAvailability(@RequestParam(value="span",required=true) String span){
		
		List<DailyRegionAvailability> resultList = navService.getAvailabilities( TimeSpan.valueOf(span.toUpperCase()), DailyRegionAvailability.class);
		
		Map<String,Object> data = new HashMap<>();
						   data.put("data", resultList);
						   
		return new ModelAndView(new DailyAvailabilityExcelView(),data);
	}
	
	@RequestMapping(value={"/export-clusternav"},method=RequestMethod.GET)
	public ModelAndView exportClusterAvailability( @RequestParam(value="region",required=true) String region,
													@RequestParam(value="span",required=true) String span)
	{
		List<ClusterAvailability> availabilities2G = navService.getAvailabilities(
				new NetworkTechnology(0,"2G",""), 
				new Region(0, region), 
				TimeSpan.valueOf(span.toUpperCase()),
				ClusterAvailability.class);
		
		List<ClusterAvailability> availabilities3G = navService.getAvailabilities(
				new NetworkTechnology(0,"3G",""), 
				new Region(0, region), 
				TimeSpan.valueOf(span.toUpperCase()),
				ClusterAvailability.class);
		
		List<ClusterAvailability> availabilities = new ArrayList<>();
								  availabilities.addAll(availabilities2G);
								  availabilities.addAll(availabilities3G);
								  
		Map<String, Object> model = new HashMap<>();
		model.put("data", availabilities);
		
		return new ModelAndView( new ClusterAvailabilityExcelView(), model );
	}
	
	@RequestMapping(value={"/export-regionalnav"},method=RequestMethod.GET)	
	public ModelAndView exportRegionAvailability(@RequestParam(value="span",required=true) String span ){
		List<RegionAvailability> availabilities = navService.getAvailabilities(TimeSpan.valueOf(span), RegionAvailability.class);
		Map<String, Object> model = new HashMap<>();
		model.put("data", availabilities);
		
		return new ModelAndView(new RegionAvailabilityExcelView(),model);
	}
	
	@RequestMapping(value="/export-nedown",method=RequestMethod.GET,produces="application/json")
	public ModelAndView exportCurrentNEDown(){
		
		Map<String,Object> model = new HashMap<>();
		List<AlarmDown> data = nedownService.getCurrentNEDownList();
		model.put("data", data);
		return new ModelAndView(new NEDownExcelView(), model);
	}
	
	@InitBinder 
	protected void initBinder(WebDataBinder binder) { 
		binder.registerCustomEditor(TimeSpan.class, new TimespanEnumConverter());
	}

}
