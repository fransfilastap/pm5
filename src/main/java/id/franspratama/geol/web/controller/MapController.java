package id.franspratama.geol.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MapController extends BaseController{
	
	public MapController(){
		super();
	}

	@RequestMapping(value={"/map"})
	public ModelAndView map(@RequestParam(value = "cc", required = false) String type){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("layout/main");
		modelAndView.addObject("module", "gis");
		modelAndView.addObject("fragment","gis");
		modelAndView.addObject("toolbar","cc");
		modelAndView.addObject("resource","cc");
		
		return modelAndView;
		
	}
	
}
