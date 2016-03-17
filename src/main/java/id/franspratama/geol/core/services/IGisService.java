package id.franspratama.geol.core.services;

import java.util.List;
import java.util.Set;

import id.franspratama.geol.core.pojo.ActiveAlarmExport;
import id.franspratama.geol.core.pojo.LebaranRoute;
import id.franspratama.geol.core.pojo.Site;
import id.franspratama.geol.core.pojo.VipGroup;
import id.franspratama.geol.web.api.ActiveAlarmDTO;
import id.franspratama.geol.web.api.GisDTO;
import id.franspratama.geol.web.api.LocationDTO;
import id.franspratama.geol.web.api.SiteAvailabilityDTO;


/**
 * 
 * GIS
 * 
 * 
 * 
 * 
 * 
 * @author fransfilastap
 *
 */
public interface IGisService {
	
	public double  DEFAULT_RADIUS = 0.5; // in kilometer
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * @param lat
	 * @param lng
	 * @param radius
	 * @return
	 */
	public Set<GisDTO> getSiteStatus(double lat,double lng,double radius);
	
	
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param dto
	 * @return
	 */
	public Set<GisDTO> getSiteStatusNearPath(List<LocationDTO> dto);
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param site
	 * @param radius
	 * @return
	 */
	public Set<GisDTO> getSiteStatus(Site site,double radius);
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param site
	 * @param radius
	 * @return
	 */
	public Set<GisDTO> getSiteStatus(String site,double radius);
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param group
	 * @return
	 */
	public Set<GisDTO> getSiteStatus(VipGroup group);
	
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param route
	 * @return
	 */
	public Set<GisDTO> getSiteStatus(LebaranRoute route);
	
	
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param site
	 * @return
	 */
	public Set<ActiveAlarmDTO> getSiteAlarms(String site);
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param latitude
	 * @param longitude
	 * @param radius
	 * @return
	 */
	public Set<ActiveAlarmExport> getActiveAlarmExport(double latitude,double longitude,double radius);
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param dto
	 * @return
	 */
	public Set<ActiveAlarmExport> getActiveAlarmExport(List<LocationDTO> dto);
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param group
	 * @return
	 */
	public Set<ActiveAlarmExport> getActiveAlarmExport(VipGroup group);	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param group
	 * @return
	 */
	public Set<ActiveAlarmExport> getActiveAlarmExport(LebaranRoute route);	

	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param site
	 * @return
	 */
	public GisDTO getBriefInformationOfSite(String site);
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param siteId
	 * @return
	 */
	public Site getFullInformationOfSite(String siteId);
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param siteid
	 * @param technology
	 * @return
	 */
	public Set<SiteAvailabilityDTO> getSiteAvailability(String siteid,String technology);
}
