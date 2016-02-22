package id.franspratama.geol.core.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.AlarmFilter;
import id.franspratama.geol.core.pojo.Severity;
import id.franspratama.geol.core.pojo.Site;
import id.franspratama.geol.core.pojo.VipGroup;
import id.franspratama.geol.core.pojo.WFMTicketAndWorkOrder;
import id.franspratama.geol.web.api.ActiveAlarmDTO;
import id.franspratama.geol.web.api.DTOConverter;
import id.franspratama.geol.web.api.GisDTO;
import id.franspratama.geol.web.api.LocationDTO;

/**
 * A GIS Facade
 * 
 * 
 * @author fransfilastap
 *
 */
@Service("gisServiceV1")
public class GisService implements IGisService{
	
	@Autowired
	ISiteRepository siteRepository;
	
	@Autowired
	IActiveAlarmRepository activeAlarmRepository;
	
	@Autowired
	@Qualifier("alarmToSiteMatcherStrategyV1")
	IAlarmToSiteStartegy<Site> strategy;
	
	@Autowired
	IAlarmFilterRepository alarmFilterRepository;
	
	@Autowired
	IWFMTicketAndWORepository wfmRepository;
	
	@Autowired
	IVipGroupRepository vipgroupRepository;
	
	@Autowired
	IVipSiteRepository vipSiteRepository;

	@Override
	public Set<GisDTO> getSiteStatus(double lat, double lng, double radius) {
		
		List<Site> sites 			= siteRepository.getSitesWithinRadius(lat, lng, radius);
		List<ActiveAlarm> alarms  	= activeAlarmRepository.findActiveAlarmWithinRadius(lat, lng, radius);
		List<AlarmFilter> filters 	= alarmFilterRepository.findBySeverity(Severity.DOWN);
		
		return strategy.match(sites, alarms, filters);
	}

	@Override
	public Set<GisDTO> getSiteStatusNearPath(List<LocationDTO> dto) {

		List<AlarmFilter> filters 	= alarmFilterRepository.findBySeverity(Severity.DOWN);
		
		List<Site> sites = new ArrayList<>();
		
		dto.parallelStream().forEach( d->{
			List<Site> sTemp = siteRepository.getSitesWithinRadius(d.getLatitude(), d.getLongitude(), DEFAULT_RADIUS);
			sites.addAll(sTemp);
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

	@Override
	public Set<ActiveAlarmDTO> getSiteAlarms(String site) {
		
		Set<ActiveAlarmDTO> dtos = new HashSet<>();
		List<WFMTicketAndWorkOrder> woList = wfmRepository.findBySiteId(site);
		Map<String,WFMTicketAndWorkOrder> map = new HashMap<>();
		
		woList.stream().forEach(wo->{
			map.put(wo.getTicketId(), wo);
		});
		
		activeAlarmRepository.findAlarmBySiteId(site)
				.stream().forEach(a->{
					   //Work oder
						System.out.println( a.getTTNO() );
					   WFMTicketAndWorkOrder wo = map.get(a.getTTNO());
					   
					   dtos.add( DTOConverter.createAlarmDTO(a, wo) );
				});
		
		return dtos;
	}

	@Override
	public GisDTO getBriefInformationOfSite(String site) {
		Site s = siteRepository.findBySiteId(site);
		return DTOConverter.createGisDTO(s);
	}

	@Override
	public Site getFullInformationOfSite(String siteId) {
		return siteRepository.findBySiteId(siteId);
	}

	@Override
	public Set<GisDTO> getSiteStatus(VipGroup group) {

		List<AlarmFilter> filters 	= alarmFilterRepository.findBySeverity(Severity.DOWN);
		List<ActiveAlarm> alarms 	= activeAlarmRepository.findActiveAlarmByGroup( group.getId() );
		List<Site> sSite 			= siteRepository.getSitesByGroup( group.getId() );
		
		return strategy.match(sSite, alarms, filters);
		
	}
	

}
