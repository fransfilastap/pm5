package id.franspratama.geol.core.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.franspratama.geol.core.pojo.SiteAvailability2G;

public interface ISiteAvailability2GRepository extends JpaRepository<SiteAvailability2G,Long>{

	@Query(name="getAvailability2G",value="SELECT * "+
				"FROM pm.2g_pm " +
				"WHERE siteid LIKE concat(:siteId, '_%') "+
				"		AND "+
				"	CONCAT(DATE(RESULT_TIME),' 00:00:00') > SUBDATE(CONCAT(DATE(NOW()),' 00:00:00'), INTERVAL 2 DAY) "+
				"	AND MINUTE(result_time) = 0 "+
				"	GROUP BY result_time,cellname",nativeQuery=true)
	public Set<SiteAvailability2G> getSiteAvailability( @Param("siteId") String siteId);
	
}
