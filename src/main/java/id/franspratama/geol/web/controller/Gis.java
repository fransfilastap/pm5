package id.franspratama.geol.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import id.franspratama.geol.core.dao.IGisService;
import id.franspratama.geol.core.dao.IVipGroupRepository;
import id.franspratama.geol.core.pojo.VipGroup;

@Controller
public class Gis extends BaseController{
	
	@Autowired
	IGisService gisService;
	
	@Autowired
	IVipGroupRepository vipGroupRepository;
	
	@RequestMapping(value={"/customer-complaint"})
	public ModelAndView customerComplaint(){
		
		Map<String,Object> model = new HashMap<>();
		
		model.put("module", "customer_complaint");
		model.put("fragment","gis");
		model.put("resource","cc");
		
		return render(model);
		
	}
	
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
	

	
}
