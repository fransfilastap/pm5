package id.franspratama.geol.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import id.franspratama.geol.core.dao.IAlarmDownRepository;
import id.franspratama.geol.core.dao.IDateBasedDailyRegionAvailabilityRepository;
import id.franspratama.geol.core.dao.INEDownAgingRepository;
import id.franspratama.geol.core.dao.ISiteRepository;
import id.franspratama.geol.core.dao.NEDownService;
import id.franspratama.geol.core.pojo.AlarmDown;
import id.franspratama.geol.core.pojo.DailyRegionAvailability;
import id.franspratama.geol.core.pojo.NEDownAging;
import id.franspratama.geol.core.pojo.Site;
import id.franspratama.geol.view.NEDownExcelView;


@RestController
public class Test {

	@Autowired
	IDateBasedDailyRegionAvailabilityRepository repo;
	@Autowired IAlarmDownRepository repo2;
	@Autowired INEDownAgingRepository repo3;
	@Autowired NEDownService neDownService;
	@Autowired ISiteRepository siteRepository;
	
	@RequestMapping(value="/testjpa",method=RequestMethod.GET,produces="application/json")
	public List<DailyRegionAvailability> test() throws ParseException{
		String from = "2016-01-01";
		String to = "2016-01-31";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dFrom = df.parse(from);
		Date dTo = df.parse(to);
		return repo.getAvaialbility( dFrom , dTo);
	}

	@RequestMapping(value="/testjpa2",method=RequestMethod.GET,produces="application/json")
	public List<AlarmDown> test2(){
		return repo2.getCurrentNeDownList();
	}
	
	@RequestMapping(value="/testjpa3",method=RequestMethod.GET,produces="application/json")
	public List<NEDownAging> test3(){
		return repo3.getCurrentNEDownAging();
	}
	
	@RequestMapping(value="/tes",method=RequestMethod.GET,produces="application/json")
	public ModelAndView tes(){
		
		Map<String,Object> model = new HashMap<>();
		List<AlarmDown> data = neDownService.getCurrentNEDownList();
		model.put("data", data);
		return new ModelAndView(new NEDownExcelView(), model);
	}
	
	@RequestMapping(value="/sites",method=RequestMethod.GET,produces="application/json")
	public List<Site> getList(){
		return siteRepository.getSitesWithinRadius(-6.2234585, 106.84260930000005, 1.0);
	}
}
