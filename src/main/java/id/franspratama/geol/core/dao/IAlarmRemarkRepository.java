package id.franspratama.geol.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import id.franspratama.geol.core.pojo.AlarmRemark;

/**
 * 
 * 
 * @author fransfilastap
 *
 */
public interface IAlarmRemarkRepository extends JpaRepository<AlarmRemark, Integer>{
	
	
	
	/**
	 * Get current active alarm remark
	 * 
	 * 
	 * @param eventIds
	 * @return
	 */
	@Query(name="currentActiveAlarmRemarks",value="select remarkeds.* from ( "+
		"    select * from( "+
		"           select geolv2.alarm_remarks.* "+
 		"            from geolv2.alarm_remarks "+
		"            inner join( "+
		"                select max(id) max_id "+
		"                from geolv2.alarm_remarks "+
		"                group by site_id "+
		"           ) xx on xx.max_id = alarm_remarks.id "+
		"    ) xxx "+
		") remarkeds inner join netcool.activealarm "+
		" on remarkeds.site_id = activealarm.SiteId", nativeQuery=true)
	public List<AlarmRemark> getActiveAlarmRemarks();
	

}
