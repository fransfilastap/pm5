
package id.franspratama.geol.web.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.ModelAndView;

import id.franspratama.geol.core.dao.IUserRepository;

/**
 * <p>Ini adalah kelas abstract dengan template untuk parsing module view</p>
 * 
 * 
 * 
 * 
 * 
 * 
 * @author fransfilastap
 *
 */
public abstract class BaseController {
	
	protected User me;
	protected id.franspratama.geol.core.pojo.User meApp;
	protected @Autowired IUserRepository userRepository;
	

	/**
	 * 
	 * 
	 * 
	 * 
	 * @param data
	 * @return
	 */
	protected ModelAndView render( Map<String,Object> data  ){
		ModelAndView modelAndView = new ModelAndView();
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		me = (User)authentication.getPrincipal();
		meApp = userRepository.findUserByUsername(me.getUsername());
		
		modelAndView.addObject("username", meApp.getFullname());
		modelAndView.addObject("userid",meApp.getId());
		modelAndView.setViewName("layout/main");
		
		if( data.get("toolbar") == null ){
			data.put("toolbar", "toolbar_default");
		}
		
		if( data.get("submodule") == null ){
			data.put("submodule",data.get("module"));
		}
		
		modelAndView.addAllObjects(data);	
		return modelAndView;
	}
	
	/**
	 * 
	 * 
	 * 
	 * @return
	 */
	protected String getUsername(){
		return me.getUsername();
	}

	

}