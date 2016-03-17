package id.franspratama.geol.core.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.franspratama.geol.core.pojo.LebaranSite;

public interface ILebaranRouteSiteRepository extends JpaRepository<LebaranSite, Integer>{

	
	/**
	 * Get site lebaran
	 * 
	 * 
	 * @return
	 */
	
	@Query(name="getLebaranSite",value="SELECT * FROM ",nativeQuery=true)
	public Set<LebaranSite> getLebaranSitesByRouteId(@Param("route") int id);
	
	
}
