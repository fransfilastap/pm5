package id.franspratama.geol.web.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class Email {
	
	
	@RequestMapping(value={"/sendDashboardEmail"},produces="application/json")
	public @ResponseBody String sendDashboard() throws IOException{
		return "ok";
	}

	@RequestMapping(value={"/sendDailyNav"},produces="application/json")
	public @ResponseBody String sendDailyNav() throws IOException{

		return "0k";
		
	}
	
	@RequestMapping(value={"/sendNeDownAlert"},produces="application/json")
	public @ResponseBody String sendNeDownAlert() throws IOException{
		return "ok";
	}	
	
}
