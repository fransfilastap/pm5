package id.franspratama.geol.core.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.franspratama.geol.core.pojo.LebaranRoute;
import id.franspratama.geol.core.pojo.LebaranRouteType;


/**
 * The repository for Jalur lebaran data.
 * 
 * 
 * 
 * 
 * @author fransfilastap
 *
 */
public interface ILebaranRouteRepository extends JpaRepository<LebaranRoute, Integer>{

	
	
	/**
	 * 
	 * Get Lebaran route 
	 * 
	 * 
	 * @param routename
	 * @return
	 */
	public LebaranRoute findByRoutename(String routename);
	
	
	
	
	
	
	/**
	 * 
	 * Get Lebaran Route list per region and route type
	 * 
	 * 
	 * @param routeType
	 * @param region
	 * @return
	 */
	public Set<LebaranRoute> findByTypeAndRegion(LebaranRouteType type, String region);
	
}
