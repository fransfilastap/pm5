package id.franspratama.geol.core.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import id.franspratama.geol.core.pojo.DailyRegionAvailability;

/**
 * 
 * 
 * 
 * 
 * 
 * @author fransfilastap
 *
 */
@Repository("acrosspmDailyRegionAvailabilityRepository")
public class AcrosspmDailyRegionAvailabilityRepository implements AcrosspmAvailabilityRepository<DailyRegionAvailability>{

	@Autowired
	private JdbcTemplate jdbc;
	
	private final String INSERT_TO_TEMP_TABLE = "INSERT INTO daily_availability_region_temp(Time,Region,Availability,technology) VALUE(?,?,?,?)";
	private final String INSERT_TO_REAL_TABLE = "INSERT INTO daily_availability_region(Time,Region,Availability,technology) "+
								"SELECT Time,Region,Availability,technology FROM daily_availability_region_temp";
	private final String TRUNCATE_TEMP_TABLE = "TRUNCATE daily_availability_region_temp";
	private final String DELETE_TODAY_DATA = "DELETE FROM daily_availability_region WHERE MONTH(CURRENT_DATE) = MONTH(Time) AND YEAR(CURRENT_DATE) = YEAR(Time)";
	
	@Override
	public void store(List<DailyRegionAvailability> availabilities) {
		SimpleDateFormat sqlTimestampFormat = new SimpleDateFormat("yyyy-MM-dd");
		jdbc.update( TRUNCATE_TEMP_TABLE );
		jdbc.batchUpdate( INSERT_TO_TEMP_TABLE, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				DailyRegionAvailability availability = availabilities.get(i);
				
				ps.setString(1, sqlTimestampFormat.format(availability.getTime()));
				ps.setString(2, availability.getRegion());
				ps.setDouble(3, availability.getAvailability());
				ps.setString(4, availability.getTechnology());
			}
			
			@Override
			public int getBatchSize() {
				return availabilities.size();
			}
		});
		
		jdbc.update( DELETE_TODAY_DATA );
		jdbc.update( INSERT_TO_REAL_TABLE );
	}

}
