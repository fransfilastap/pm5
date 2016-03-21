package id.franspratama.geol.web.controller;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import id.franspratama.geol.task.EmailTask;

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
public class EmailController {
	
	@Autowired
	EmailTask task;
	
	
	
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	@RequestMapping(value={"/sendDashboardEmail"},produces="application/json")
	public @ResponseBody String sendDashboard() throws IOException, MessagingException{
		task.sendDashboard();
		return "ok";
	}

	
	
	
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	@RequestMapping(value={"/sendDailyNav"},produces="application/json")
	public @ResponseBody String sendDailyNav() throws IOException, MessagingException{
		task.sendDailyAvailability();
		return "0k";
		
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
	 * @throws IOException
	 */
	@RequestMapping(value={"/sendNeDownAlert"},produces="application/json")
	public @ResponseBody String sendNeDownAlert() throws IOException{
		return "ok";
	}	
	
}
