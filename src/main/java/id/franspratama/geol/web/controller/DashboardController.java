package id.franspratama.geol.web.controller;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import id.franspratama.geol.core.dao.UtilityRepository;
import id.franspratama.geol.core.pojo.AlarmDown;
import id.franspratama.geol.core.pojo.ClusterAvailability;
import id.franspratama.geol.core.pojo.DailyRegionAvailability;
import id.franspratama.geol.core.pojo.NetworkTechnology;
import id.franspratama.geol.core.pojo.Region;
import id.franspratama.geol.core.pojo.RegionAvailability;
import id.franspratama.geol.core.pojo.TimeSpan;
import id.franspratama.geol.core.services.AvailabilityService;
import id.franspratama.geol.core.services.NEDownService;
import id.franspratama.geol.view.ClusterAvailabilityExcelView;
import id.franspratama.geol.view.DailyAvailabilityExcelView;
import id.franspratama.geol.view.NEDownExcelView;
import id.franspratama.geol.view.RegionAvailabilityExcelView;

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
@Controller
public class DashboardController extends BaseController{
	
	@Autowired
	UtilityRepository utilityRepository;
	
	@Autowired
	AvailabilityService navService;
	
	@Autowired
	NEDownService nedownService;
	
	public DashboardController() {
		super();
	}

	
	/**
	 * 
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(value={"/","/dashboard"},method=RequestMethod.GET)
	@PreAuthorize("hasRole('DEV') OR hasRole('DASHBOARD_USER')")
	public ModelAndView dashboard(){

		Map<String,Object> data = new HashMap<>();
		data.put("module", "dashboard");
		data.put("fragment", "dashboard");
		data.put("regions",utilityRepository.getClusterRegion());
		
		return render(data);
	}

	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param span
	 * @return
	 */
	@RequestMapping(value={"/export-dailynav"},method=RequestMethod.GET)
	@PreAuthorize("hasRole('DEV') OR hasRole('DASHBOARD_USER')")
	public ModelAndView exportDailyRegionAvailability(@RequestParam(value="span",required=true) String span){
		
		List<DailyRegionAvailability> resultList = navService.getAvailabilities( TimeSpan.valueOf(span.toUpperCase()), DailyRegionAvailability.class);
		
		Map<String,Object> data = new HashMap<>();
						   data.put("data", resultList);
						   
		return new ModelAndView(new DailyAvailabilityExcelView(),data);
	}
	
	
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param region
	 * @param span
	 * @return
	 */
	@RequestMapping(value={"/export-clusternav"},method=RequestMethod.GET)
	@PreAuthorize("hasRole('DEV') OR hasRole('DASHBOARD_USER')")
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
	
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param span
	 * @return
	 */
	@RequestMapping(value={"/export-regionalnav"},method=RequestMethod.GET)	
	@PreAuthorize("hasRole('DEV') OR hasRole('DASHBOARD_USER')")
	public ModelAndView exportRegionAvailability(@RequestParam(value="span",required=true) String span ){
		List<RegionAvailability> availabilities = navService.getAvailabilities(TimeSpan.valueOf(span), RegionAvailability.class);
		Map<String, Object> model = new HashMap<>();
		model.put("data", availabilities);
		
		return new ModelAndView(new RegionAvailabilityExcelView(),model);
	}
	
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(value="/export-nedown",method=RequestMethod.GET,produces="application/json")
	@PreAuthorize("hasRole('DEV') OR hasRole('DASHBOARD_USER')")
	public ModelAndView exportCurrentNEDown(){
		
		Map<String,Object> model = new HashMap<>();
		List<AlarmDown> data = nedownService.getCurrentNEDownList();
		model.put("data", data);
		return new ModelAndView(new NEDownExcelView(), model);
	}

}
