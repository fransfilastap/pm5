package id.franspratama.geol.core.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import id.franspratama.geol.core.dao.AcrosspmAvailabilityRepository;
import id.franspratama.geol.core.dao.ITimespanBasedAvailabilityRepository;
import id.franspratama.geol.core.pojo.ClusterAvailability;
import id.franspratama.geol.core.pojo.DailyRegionAvailability;
import id.franspratama.geol.core.pojo.NetworkTechnology;
import id.franspratama.geol.core.pojo.Region;
import id.franspratama.geol.core.pojo.RegionAvailability;
import id.franspratama.geol.core.pojo.TimeSpan;

/**
 * Availability facade
 * 
 * 
 * @author franspotter
 *
 */

@Service
public class AvailabilityService {

	
	public @Autowired AcrosspmAvailabilityRepository<ClusterAvailability> acrosspmRepositoryC;
	public @Autowired AcrosspmAvailabilityRepository<RegionAvailability> acrosspmRepositoryR;
	public @Autowired AcrosspmAvailabilityRepository<DailyRegionAvailability> acrosspmRepositoryD;
	public @Autowired ITimespanBasedAvailabilityRepository<RegionAvailability> rRepository;
	public @Autowired ITimespanBasedAvailabilityRepository<ClusterAvailability> cRepository;
	public @Autowired @Qualifier("dailyRegionJDBCAvailabilityRepository") ITimespanBasedAvailabilityRepository<DailyRegionAvailability> dRepository;
	
	private Map<Class,ITimespanBasedAvailabilityRepository> repositoryMapper = new HashMap<>();
	private Map<Class, AcrosspmAvailabilityRepository> accrosspmRepositoryMapper = new HashMap<>();
	
	@PostConstruct
	private void init(){
		repositoryMapper.put(ClusterAvailability.class, cRepository);
		repositoryMapper.put(RegionAvailability.class, rRepository);
		repositoryMapper.put(DailyRegionAvailability.class, dRepository);
		accrosspmRepositoryMapper.put(ClusterAvailability.class, acrosspmRepositoryC);
		accrosspmRepositoryMapper.put(RegionAvailability.class, acrosspmRepositoryR);
		accrosspmRepositoryMapper.put(DailyRegionAvailability.class, acrosspmRepositoryD);
		
	}
	
	/**
	 * 
	 * @param availabilities
	 * @param clazz
	 * 
	 * Store batch availabilities
	 */
	
	public void storeBatch(List availabilities, Class clazz){
		accrosspmRepositoryMapper.get(clazz).store(availabilities);
	}
	
	/**
	 * 
	 * @param timeSpan
	 * @param clazz
	 * @return List of availability
	 */
	
	public List getAvailabilities(TimeSpan timeSpan,Class clazz){
		return repositoryMapper.get(clazz).getAvailability(timeSpan);
	}
	
	/**
	 * 
	 * @param networkTechnology
	 * @param region
	 * @param span
	 * @return ClusterAvaialbility List
	 * 
	 * 
	 */
	public List getAvailabilities(NetworkTechnology networkTechnology,Region region, TimeSpan span,Class clazz){
		return repositoryMapper.get(clazz).getAvailability(span, region, networkTechnology);
	}
	
}
