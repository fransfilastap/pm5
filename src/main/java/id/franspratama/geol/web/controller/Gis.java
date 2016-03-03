package id.franspratama.geol.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import id.franspratama.geol.core.dao.IVipGroupRepository;
import id.franspratama.geol.core.pojo.ActiveAlarmExport;
import id.franspratama.geol.core.pojo.VipGroup;
import id.franspratama.geol.core.services.IGisService;
import id.franspratama.geol.view.ActiveAlarmExcelView;

/**
 * 
 * A controller for GIS feature
 * 
 * 
 * @author fransfilastap
 *
 */
@Controller
public class Gis extends BaseController{
	
	@Autowired
	IGisService gisService;
	
	@Autowired
	IVipGroupRepository vipGroupRepository;
	
	/**
	 * Return view for customer complaint
	 * 
	 * 
	 * @return
	 */
	
	@RequestMapping(value={"/customer-complaint"})
	public ModelAndView customerComplaint(){
		
		Map<String,Object> model = new HashMap<>();
		
		model.put("module", "customer_complaint");
		model.put("fragment","gis");
		model.put("resource","cc");
		
		return render(model);
		
	}
	
	
	/**
	 * Return view for vip-group module
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(value={"/vip-group"})
	public ModelAndView vipGroup(){
		
		Map<String,Object> model = new HashMap<>();
		
		List<VipGroup> groups = vipGroupRepository.findAll();
		
		model.put("module", "vipgroup");
		model.put("fragment","gis");
		model.put("groups", groups );
		model.put("resource","cc");
		
		return render(model);
		
	}
	
	
	/**
	 * 
	 * Generate excel file based on selected VIP GROUP 
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value={"/vip-group-export"})
	public ModelAndView vipGroupGisExpor( @RequestParam(name="group",required=true) long id ){
		
		VipGroup group 						= vipGroupRepository.findOne(id);
		Set<ActiveAlarmExport> exportData 	= gisService.getActiveAlarmExport( group );
		Map<String,Object> model 			= new HashMap<>();
		model.put("data", exportData);
		
		return new ModelAndView(new ActiveAlarmExcelView(), model);
	}
	
	/**
	 * 
	 * Generate excel file based on customer complaint location and radius
	 * 
	 * @param lat
	 * @param lon
	 * @param rad
	 * @return
	 */
	
	@RequestMapping(value={"/customer-complaint-export"})
	public ModelAndView customerComplaintExport( @RequestParam(name="lat",required=true) double lat, 
			@RequestParam(name="lon",required=true) double lon, @RequestParam(name="rad") double rad){

		Set<ActiveAlarmExport> exportData 	= gisService.getActiveAlarmExport(lat, lon, rad);
		Map<String,Object> model 			= new HashMap<>();
		model.put("data", exportData);
		
		return new ModelAndView(new ActiveAlarmExcelView(), model);
		
	}
	

	
}
