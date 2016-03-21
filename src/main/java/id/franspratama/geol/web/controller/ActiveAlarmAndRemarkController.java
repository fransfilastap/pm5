package id.franspratama.geol.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>Active Alarm Remark Controller</p>
 * 
 * 
 * 
 * @author fransfilastap
 *
 */

@Controller
public class ActiveAlarmAndRemarkController extends BaseController{


	
	public ActiveAlarmAndRemarkController(){
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
	@RequestMapping(value="/active-alarm",method=RequestMethod.GET)
	public ModelAndView index(){
		
		Map<String,Object> data = new HashMap<>();
		data.put("module", "activealarm");
		data.put("fragment", "activealarm");
		
		return render(data);
	}
	
	
}
