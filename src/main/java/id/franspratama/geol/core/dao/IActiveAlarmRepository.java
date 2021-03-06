package id.franspratama.geol.core.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.franspratama.geol.core.pojo.ActiveAlarm;

public interface IActiveAlarmRepository extends JpaRepository<ActiveAlarm, Long>{
	
	/**
	 * 
	 * Get alarm of sites within radius
	 * 
	 * @param latitude
	 * @param longitude
	 * @param radius
	 * @return
	 */
	@Query(name="activeAlarmWithinRadius",value="SELECT activealarm.* FROM netcool.activealarm "+
								" INNER JOIN ( "+
								" SELECT siteid FROM( "+
								" SELECT siteid "+
								"		FROM ( "+
								"			SELECT siteid,	( 6371 * ACOS( COS( RADIANS(:latitude) ) * "+
								"				COS( RADIANS( latitude ) ) "+
								"					* COS( RADIANS( longitude ) - RADIANS(:longitude) "+ 
								"					) + SIN( RADIANS(:latitude) ) * "+
								"					SIN( RADIANS( latitude ) ) ) "+
								"				        ) AS distance "+
								"					FROM geolv2.sites  "+
								"					WHERE latitude IS NOT NULL AND longitude IS NOT NULL "+
								"					HAVING distance <= :radius "+
								"		 ) yyy "+
								") asd "+
							    ")xxx on activealarm.siteid = xxx.siteid",nativeQuery=true)
	public List<ActiveAlarm> findActiveAlarmWithinRadius( @Param("latitude") double latitude,
															@Param("longitude") double longitude,
															@Param("radius") double radius);
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * @param id
	 * @return
	 */
	@Query(name="vipgroupActiveAlarm",value="SELECT a.* FROM `netcool`.`activealarm` a "
			+ "INNER JOIN ("
			+ " SELECT sites.* FROM sites INNER JOIN vipsites_v2 v ON v.site_id = sites.id "
			+ " WHERE v.groupid = :groupid"
			+ ") s ON s.siteid = a.siteid",nativeQuery=true)
	public List<ActiveAlarm> findActiveAlarmByGroup(@Param("groupid") long id);
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param route
	 * @param type
	 * @return
	 */
	
	
	@Query(name="lebaranGroupActiveAlarm",value="SELECT activealarm.* FROM netcool.activealarm "+
				"INNER JOIN( "+
				"	 SELECT s.SITEID FROM geolv2.sites s "+ 
				"	 INNER JOIN ( "+
				"		SELECT lrs.site_id "+
				"		FROM lebaran_route_sites lrs "+
				"		LEFT JOIN lebaran_routes lr ON lr.ID = lrs.route_id "+
				"		WHERE lr.ID = :route "+
				"	 ) ls on ls.site_id = s.ID "+
				") sites ON sites.siteid = activealarm.siteid",nativeQuery=true)
	public List<ActiveAlarm> getActiveAlarmByLebaranRoute(@Param("route") int route);
	
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * @param siteId
	 * @return
	 */
	public List<ActiveAlarm> findAlarmBySiteId(String siteId);
	
	
}
