package id.franspratama.geol.core.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import id.franspratama.geol.core.pojo.ClusterAvailability;

@Repository("acrosspmClusterAvailabilityRepository")
public class AcrosspmClusterAvailabilityRepository implements AcrosspmAvailabilityRepository<ClusterAvailability>{

	private @Autowired JdbcTemplate jdbcTemplate;
	private final String TRUNCATE_TEMP_TABLE = "TRUNCATE TABLE availabilities_cluster_temp";
	private final String INSERT_TEMP_TABLE = "INSERT INTO availabilities_cluster_temp(Time,Cluster,Region,Availability,Technology) VALUES(?,?,?,?,?)";
	
	@Override
	public void store(List<ClusterAvailability> availabilities) {
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
				ClusterAvailability availability = availabilities.get(i);
				
				ps.setString(1, dateFormat.format(availability.getTime()));
				ps.setString(2, availability.getCluster());
				ps.setString(3, availability.getRegion());
				ps.setDouble(4, availability.getAvailability());
				ps.setString(5, availability.getTechnology());
				
			}
		});
		
		jdbcTemplate.update("DELETE FROM availabilities_cluster WHERE DATE(Time) = DATE(NOW())");
		
		jdbcTemplate.update("INSERT INTO availabilities_cluster(Time,Cluster,Region, Availability, Technology)"
				+ " SELECT Time,Cluster, Region, Availability, Technology FROM availabilities_cluster_temp");
		
	}

}
