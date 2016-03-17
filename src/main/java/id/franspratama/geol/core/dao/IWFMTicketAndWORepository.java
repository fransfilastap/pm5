package id.franspratama.geol.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.franspratama.geol.core.pojo.WFMTicketAndWorkOrder;

/**
 * 
 * @author fransfilastap
 *
 */
public interface IWFMTicketAndWORepository extends JpaRepository<WFMTicketAndWorkOrder, Integer>{
	
	/**
	 * Find WFM WO & Ticket in String
	 * 
	 * 
	 * @param siteId
	 * @return
	 * 
	 */
	public List<WFMTicketAndWorkOrder> findBySiteId(String siteId);
	
	/**
	 * 
	 * 
	 * 
	 * @param groupid
	 * @return
	 */
	@Query(name="woVipGroup",value="SELECT wo.* FROM("
			+ " SELECT aa.* FROM netcool.activealarm aa INNER JOIN"
			+ " ("
			+ "	SELECT s.SITEID FROM vipsites_v2 v INNER JOIN sites s ON s.id = v.id WHERE groupid = :groupid"
			+ ") sites ON aa.siteid = sites.siteid"
			+ ") alarms "
			+ " INNER JOIN wfm_tt_wo wo ON wo.ticket_id = alarms.ttno group by wo.ticket_id",nativeQuery=true)
	public List<WFMTicketAndWorkOrder> getWFMTicketAndWO( @Param("groupid") long groupid);

	/**
	 * 
	 * 
	 * 
	 * @param latitude
	 * @param longitude
	 * @param radius
	 * @return
	 */
	@Query(name="woRadius",value="SELECT wo.* FROM("
			+ " SELECT aa.* FROM netcool.activealarm aa INNER JOIN"
			+ " ("
			+ 								"			SELECT siteid,	( 6371 * ACOS( COS( RADIANS(:latitude) ) * "+
			"				COS( RADIANS( latitude ) ) "+
			"					* COS( RADIANS( longitude ) - RADIANS(:longitude) "+ 
			"					) + SIN( RADIANS(:latitude) ) * "+
			"					SIN( RADIANS( latitude ) ) ) "+
			"				        ) AS distance "+
			"					FROM geolv2.sites  "+
			"					WHERE latitude IS NOT NULL AND longitude IS NOT NULL "+
			"					HAVING distance <= :radius "+
			") sites ON aa.siteid = sites.siteid"
			+ ") alarms "
			+ "INNER JOIN wfm_tt_wo wo ON wo.ticket_id = alarms.ttno group by wo.ticket_id",nativeQuery=true)
	public List<WFMTicketAndWorkOrder> getWFMTicketAndWO(@Param("latitude") double latitude,
			@Param("longitude") double longitude,
			@Param("radius") double radius);
	
	
	@Query(name="woLebaran",value="SELECT wo.* FROM wfm_tt_wo wo INNER JOIN( "+
			" SELECT activealarm.* FROM netcool.activealarm "+
			"			INNER JOIN( "+
			"				 SELECT s.SITEID FROM geolv2.sites s "+
			"				 INNER JOIN ( "+
			"					SELECT lrs.site_id "+
			"					FROM lebaran_route_sites lrs "+
			"					LEFT JOIN lebaran_routes lr ON lr.ID = lrs.route_id "+
			"					WHERE lr.ID = :route "+
			"				 ) ls on ls.site_id = s.ID "+
			"			) sites ON sites.siteid = activealarm.siteid "+
			") alarms on wo.ticket_id = alarms.ttno group by wo.ticket_id",nativeQuery=true)
	public List<WFMTicketAndWorkOrder> getWFMTicketAndWO( @Param("route") int route );
}
