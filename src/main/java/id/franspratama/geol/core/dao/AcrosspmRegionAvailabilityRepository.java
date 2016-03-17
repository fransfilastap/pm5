package id.franspratama.geol.core.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import id.franspratama.geol.core.pojo.RegionAvailability;

/**
 * 
 * 
 * 
 * 
 * 
 * 
 * @author fransfilastap
 *
 */
@Repository("acrosspmRegionAvailabilityRepository")
public class AcrosspmRegionAvailabilityRepository implements AcrosspmAvailabilityRepository<RegionAvailability>{
	
	private @Autowired JdbcTemplate jdbcTemplate;
	private final String TRUNCATE_TEMP_TABLE = "TRUNCATE TABLE availabilities_region_temp";
	private final String INSERT_TEMP_TABLE = "INSERT INTO availabilities_region_temp(Time,Region,Availability,Technology) VALUES(?,?,?,?)";
	
	
	public void store(List<RegionAvailability> availabilities){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//empty availability temp table first
		jdbcTemplate.update( TRUNCATE_TEMP_TABLE );
		//insert new temporary data
		jdbcTemplate.batchUpdate(INSERT_TEMP_TABLE, new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				return availabilities.size();
				}

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				RegionAvailability availability = availabilities.get(i);				
				ps.setString(1, dateFormat.format(availability.getTime()));
				ps.setString(2, availability.getRegion());
				ps.setDouble(3, availability.getAvailability());
				ps.setString(4, availability.getTechnology());
				
			}
		});
		
		jdbcTemplate.update("DELETE FROM availabilities_region WHERE DATE(Time) = DATE(NOW())");
		
		jdbcTemplate.update("INSERT INTO availabilities_region(Time,Region, Availability, Technology)"
				+ " SELECT Time, Region, Availability, Technology FROM availabilities_region_temp");
		
	}
}
