package id.franspratama.geol.core.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.franspratama.geol.core.pojo.Site;

public interface ISiteRepository extends JpaRepository<Site, Integer>{

	@Query(name="siteWithinRadius",value="SELECT sites.*,( 6371 * ACOS( COS( RADIANS(:latitude) ) * "+
						" COS( RADIANS( latitude ) )"+
						" * COS( RADIANS( longitude ) - RADIANS(:longitude) "+
						" ) + SIN( RADIANS(:latitude) ) * "+
						" SIN( RADIANS( latitude ) ) ) "+
					    "   ) AS distance "+
					    " FROM sites WHERE LATITUDE IS NOT NULL AND LONGITUDE IS NOT NULL" +
					    " HAVING distance <= :radius",nativeQuery=true)
	public List<Site> getSitesWithinRadius(@Param("latitude") double latitude,
											@Param("longitude") double longitude,
											@Param("radius") double radius);
	
	@Query(name="getSites",value="SELECT s.* FROM sites s INNER JOIN vipsites_v2 s2 ON s.id = s2.site_id "
			+ " WHERE groupid = :id",nativeQuery=true)
	public List<Site> getSitesByGroup(@Param("id") long id);
	
	public Site findBySiteId(String siteId);
	
}
