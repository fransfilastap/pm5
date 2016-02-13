package id.franspratama.geol.core.dao;

import java.util.List;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import id.franspratama.geol.core.pojo.DailyRegionAvailability;

public interface IDateBasedDailyRegionAvailabilityRepository extends Repository<DailyRegionAvailability, Integer>{

	@Query( value = "SELECT * FROM daily_availability_region "+
			"WHERE TIME BETWEEN :from AND :to "+
			"GROUP BY TIME,region,technology", nativeQuery=true )
	
	public List<DailyRegionAvailability> getAvaialbility(@Param("from") Date from, 
														@Param("to") Date to); 
	
	@Query( value = "SELECT * FROM daily_availability_region "+
			"WHERE TIME BETWEEN :from AND :to AND region = :region AND technology = :technology"+
			"GROUP BY TIME,region,technology", nativeQuery=true )
	public List<DailyRegionAvailability> getAvailability( @Param("from") Date from,
														@Param("to") Date to,
														@Param("region") String region,
														@Param("technology") String technology);
	
}
