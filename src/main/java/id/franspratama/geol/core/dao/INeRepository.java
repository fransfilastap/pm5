package id.franspratama.geol.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import id.franspratama.geol.core.pojo.NE;

public interface INeRepository extends JpaRepository<NE, Long>{
	
	@Query(name="currentActiveAlarmNE",value="select NE.* FROM geolv2.NE INNER JOIN ( "
			+ " SELECT * FROM netcool.activealarm"
			+ ") activealarm ON activealarm.SiteId = NE.btsid",nativeQuery=true)
	public List<NE> getActiveAlarmNe();

}
