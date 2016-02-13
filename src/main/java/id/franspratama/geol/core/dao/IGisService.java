package id.franspratama.geol.core.dao;

import java.util.List;
import java.util.Set;

import id.franspratama.geol.core.pojo.Site;
import id.franspratama.geol.web.api.GisDTO;
import id.franspratama.geol.web.api.LocationDTO;

public interface IGisService {
	public double  DEFAULT_RADIUS = 0.5; // ib kilometer
	public Set<GisDTO> getSiteStatus(double lat,double lng,double radius);
	public Set<GisDTO> getSiteStatusNearPath(List<LocationDTO> dto);
	public Set<GisDTO> getSiteStatus(Site site,double radius);
	public Set<GisDTO> getSiteStatus(String site,double radius);

}
