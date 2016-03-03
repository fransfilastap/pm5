package id.franspratama.geol.core.services;

import java.util.List;
import java.util.Set;

import id.franspratama.geol.core.pojo.ActiveAlarmExport;
import id.franspratama.geol.core.pojo.Site;
import id.franspratama.geol.core.pojo.SiteAvailability2G;
import id.franspratama.geol.core.pojo.SiteAvailability3G;
import id.franspratama.geol.core.pojo.VipGroup;
import id.franspratama.geol.web.api.ActiveAlarmDTO;
import id.franspratama.geol.web.api.GisDTO;
import id.franspratama.geol.web.api.LocationDTO;
import id.franspratama.geol.web.api.SiteAvailabilityDTO;

public interface IGisService {
	public double  DEFAULT_RADIUS = 0.5; // in kilometer
	public Set<GisDTO> getSiteStatus(double lat,double lng,double radius);
	public Set<GisDTO> getSiteStatusNearPath(List<LocationDTO> dto);
	public Set<GisDTO> getSiteStatus(Site site,double radius);
	public Set<GisDTO> getSiteStatus(String site,double radius);
	public Set<GisDTO> getSiteStatus(VipGroup group);
	public Set<ActiveAlarmDTO> getSiteAlarms(String site);
	public Set<ActiveAlarmExport> getActiveAlarmExport(double latitude,double longitude,double radius);
	public Set<ActiveAlarmExport> getActiveAlarmExport(List<LocationDTO> dto);
	public Set<ActiveAlarmExport> getActiveAlarmExport(VipGroup group);	
	public GisDTO getBriefInformationOfSite(String site);
	public Site getFullInformationOfSite(String siteId);
	public Set<SiteAvailabilityDTO> getSiteAvailability(String siteid,String technology);
	
}
