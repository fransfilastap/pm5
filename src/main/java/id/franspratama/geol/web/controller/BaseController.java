package id.franspratama.geol.web.controller;

import java.util.Map;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.ModelAndView;

import id.franspratama.geol.core.dao.IUserRepository;

public abstract class BaseController {
	
	protected User me;
	protected id.franspratama.geol.core.pojo.User meApp;
	protected @Autowired IUserRepository userRepository;
	

	protected ModelAndView render( Map<String,Object> data  ){
		ModelAndView modelAndView = new ModelAndView();
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		me = (User)authentication.getPrincipal();
		meApp = userRepository.findUserByUsername(me.getUsername());
		
		modelAndView.addObject("username", meApp.getFullname());
		modelAndView.setViewName("layout/main");
		
		if( data.get("toolbar") == null ){
			data.put("toolbar", "toolbar_default");
		}
		
		modelAndView.addAllObjects(data);	
		return modelAndView;
	}
	
	
	protected String getUsername(){
		return me.getUsername();
	}

	

}
