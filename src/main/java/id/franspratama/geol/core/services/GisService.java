package id.franspratama.geol.core.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import id.franspratama.geol.core.pojo.ActiveAlarmExport;
import id.franspratama.geol.core.dao.IActiveAlarmRepository;
import id.franspratama.geol.core.dao.IAlarmFilterRepository;
import id.franspratama.geol.core.dao.IAlarmToSiteStartegy;
import id.franspratama.geol.core.dao.ISiteAvailability2GRepository;
import id.franspratama.geol.core.dao.ISiteAvailability3GRepository;
import id.franspratama.geol.core.dao.ISiteRepository;
import id.franspratama.geol.core.dao.IVipGroupRepository;
import id.franspratama.geol.core.dao.IVipSiteRepository;
import id.franspratama.geol.core.dao.IWFMTicketAndWORepository;
import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.AlarmFilter;
import id.franspratama.geol.core.pojo.Severity;
import id.franspratama.geol.core.pojo.Site;
import id.franspratama.geol.core.pojo.SiteAvailability2G;
import id.franspratama.geol.core.pojo.SiteAvailability3G;
import id.franspratama.geol.core.pojo.VipGroup;
import id.franspratama.geol.core.pojo.WFMTicketAndWorkOrder;
import id.franspratama.geol.web.api.ActiveAlarmDTO;
import id.franspratama.geol.web.api.ActiveAlarmExportBuilder;
import id.franspratama.geol.web.api.DTOConverter;
import id.franspratama.geol.web.api.GisDTO;
import id.franspratama.geol.web.api.LocationDTO;
import id.franspratama.geol.web.api.SiteAvailabilityDTO;

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
	
	@Autowired
	ISiteAvailability2GRepository siteAvailability2GRepository;
	
	@Autowired
	ISiteAvailability3GRepository siteAvailability3GRepository;

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
		List<Site> sites 			= new ArrayList<>();
		
		dto.parallelStream().forEach( d->{
			List<Site> sTemp = siteRepository.getSitesWithinRadius(d.getLatitude(), d.getLongitude(), DEFAULT_RADIUS);
			sites.addAll(sTemp);
		});
		
		List<ActiveAlarm> alarms 	= activeAlarmRepository.findAll();
		
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

	@Override
	public Set<ActiveAlarmExport> getActiveAlarmExport(double latitude, double longitude, double radius) {
		
		List<Site> sites  				= siteRepository.getSitesWithinRadius(latitude, longitude, radius);
		List<ActiveAlarm> alarms 		= activeAlarmRepository.findActiveAlarmWithinRadius(latitude, longitude, radius);
		List<AlarmFilter> filters 		= alarmFilterRepository.findBySeverity(Severity.DOWN);
		List<WFMTicketAndWorkOrder> wos = wfmRepository.getWFMTicketAndWO(latitude, longitude, radius);
		String radiusGroup				= "CUstomer Complaint Export, latitude = "+latitude+"; longitude = "+longitude+", radius = "+radius;
		
		return ActiveAlarmExportBuilder.export(alarms, sites, filters, wos, radiusGroup);
	}

	@Override
	public Set<ActiveAlarmExport> getActiveAlarmExport(List<LocationDTO> dto) {
		
		List<AlarmFilter> filters 	= alarmFilterRepository.findBySeverity(Severity.DOWN);
		List<Site> sites 			= new ArrayList<>();
		
		dto.parallelStream().forEach( d->{
			List<Site> sTemp = siteRepository.getSitesWithinRadius(d.getLatitude(), d.getLongitude(), DEFAULT_RADIUS);
			sites.addAll(sTemp);
		});
		
		List<ActiveAlarm> alarms 	= activeAlarmRepository.findAll();
		String group 				= "Path export";
		List<WFMTicketAndWorkOrder> wos = wfmRepository.findAll();
		
		return ActiveAlarmExportBuilder.export(alarms, sites, filters, wos, group);
	}

	@Override
	public Set<ActiveAlarmExport> getActiveAlarmExport(VipGroup group) {
		
		List<AlarmFilter> filters 		= alarmFilterRepository.findBySeverity(Severity.DOWN);
		List<ActiveAlarm> alarms 		= activeAlarmRepository.findActiveAlarmByGroup( group.getId() );
		List<Site> sSite 				= siteRepository.getSitesByGroup( group.getId() );
		List<WFMTicketAndWorkOrder> wos = wfmRepository.getWFMTicketAndWO(group.getId());
		
		return ActiveAlarmExportBuilder.export(alarms, sSite, filters, wos, group.getGroupName());
	}

	@Override
	public Set<SiteAvailabilityDTO> getSiteAvailability(String siteid, String technology) {
		
		Set<SiteAvailabilityDTO> availabilities = new HashSet<>();
		
		if( technology.equalsIgnoreCase("2G") ){
			siteAvailability3GRepository.getSiteAvailability(siteid)
					.parallelStream()
						.forEach( avail ->{
							availabilities.add( convert( avail )  );
						});
		}
		else if( technology.equalsIgnoreCase("3G") ){
			siteAvailability2GRepository.getSiteAvailability(siteid)
			.parallelStream()
				.forEach( avail ->{
					availabilities.add( convert( avail )  );
				});
		}
		
		return availabilities;
	}

	//Date resultTime, String cellName, String siteId, String poc, String region,double availability
	
	public SiteAvailabilityDTO convert(SiteAvailability2G avail){
		return new SiteAvailabilityDTO( 
				avail.getResultTime(), 
				avail.getCellName(), 
				avail.getSiteId(),
				avail.getPoc(),
				avail.getRegion(),
				avail.getAvailability());
	}
	
	public SiteAvailabilityDTO convert(SiteAvailability3G avail){
		return new SiteAvailabilityDTO( 
				avail.getResultTime(), 
				avail.getCellName(), 
				avail.getSiteId(),
				avail.getPoc(),
				avail.getRegion(),
				avail.getAvailability());
	}
	
	
	

	

}
