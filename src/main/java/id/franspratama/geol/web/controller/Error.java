package id.franspratama.geol.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class Error {
	
	@RequestMapping( value={"/error"}, method=RequestMethod.GET  )
	public ModelAndView error(){
		ModelAndView modelAndView = new ModelAndView();
		
		return modelAndView;
	}
	
}
