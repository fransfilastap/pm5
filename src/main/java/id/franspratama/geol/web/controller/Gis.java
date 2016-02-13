package id.franspratama.geol.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Gis extends BaseController{
	
	@RequestMapping(value={"/customer-complaint"})
	public ModelAndView customerComplaint(){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("layout/main");
		modelAndView.addObject("module", "gis");
		modelAndView.addObject("fragment","gis");
		modelAndView.addObject("toolbar","toolbar_customer_complaint");
		modelAndView.addObject("resource","cc");
		
		return modelAndView;
		
	}
}
