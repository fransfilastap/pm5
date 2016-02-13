package id.franspratama.geol.core.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.AlarmFilter;
import id.franspratama.geol.core.pojo.Site;
import id.franspratama.geol.web.api.GisDTO;
import id.franspratama.geol.web.api.LocationDTO;

@Service("gisServiceV1")
public class GisService implements IGisService{
	
	@Autowired
	ISiteRepository siteRepository;
	
	@Autowired
	IActiveAlarmRepository activeAlarmRepository;
	
	@Autowired
	@Qualifier("alarmToSiteMatcherStrategyV1")
	IAlarmToSiteStartegy strategy;
	
	@Autowired
	IAlarmFilterRepository alarmFilterRepository;

	@Override
	public Set<GisDTO> getSiteStatus(double lat, double lng, double radius) {
		
		List<Site> sites 			= siteRepository.getSitesWithinRadius(lat, lng, radius);
		List<ActiveAlarm> alarms  		= activeAlarmRepository.findAll();
		List<AlarmFilter> filters 	= alarmFilterRepository.findAll();
		
		return strategy.match(sites, alarms, filters);
	}

	@Override
	public Set<GisDTO> getSiteStatusNearPath(List<LocationDTO> dto) {

		List<AlarmFilter> filters 	= alarmFilterRepository.findAll();
		
		List<Site> sites = new ArrayList<>();
		
		dto.parallelStream().forEach( d->{
			List<Site> sTemp = siteRepository.getSitesWithinRadius(d.getLatitude(), d.getLongitude(), DEFAULT_RADIUS);
			sTemp.addAll(sTemp);
		});
		
		List<ActiveAlarm> alarms = activeAlarmRepository.findAll();
		
		return strategy.match(sites, alarms, filters);
	}

	@Override
	public Set<GisDTO> getSiteStatus(Site site, double rad) {
		Site sDb = siteRepository.getOne((int)site.getId());
		return getSiteStatus(sDb.getLatitue(), sDb.getLongitude(), rad);
	}

	@Override
	public Set<GisDTO> getSiteStatus(String site, double rad) {
		Site sDb = siteRepository.findBySiteId(site);
		return getSiteStatus(sDb.getLatitue(), sDb.getLongitude(), rad);
	}

}
