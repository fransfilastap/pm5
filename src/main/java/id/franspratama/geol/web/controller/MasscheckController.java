package id.franspratama.geol.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Masscheck module controller
 * 
 * 
 * 
 * 
 * @author fransfilastap
 *
 */

@Controller
public class MasscheckController extends BaseController{
	/**
	 * 
	 * 
	 * 
	 */
	public MasscheckController(){
		super();
	}
	
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(value="/masscheck",method=RequestMethod.GET)
	public ModelAndView index(){
		
		Map<String,Object> data = new HashMap<>();
		data.put("module", "masscheck");
		data.put("fragment", "masscheck");
		
		return render(data);
	}
}
