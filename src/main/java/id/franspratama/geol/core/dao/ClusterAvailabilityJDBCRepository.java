package id.franspratama.geol.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import id.franspratama.geol.core.pojo.ClusterAvailability;
import id.franspratama.geol.core.pojo.NetworkTechnology;
import id.franspratama.geol.core.pojo.Region;
import id.franspratama.geol.core.pojo.TimeSpan;

@Repository("jdbcClusterAvailabilityRepository")
public class ClusterAvailabilityJDBCRepository implements ITimespanBasedAvailabilityRepository<ClusterAvailability>{

	@Autowired
	public JdbcTemplate jdbc;
	
	private final String QUERY = "SELECT `Time`,IF( area_name IS NULL, Cluster, area_name ) AS Clusterx,`Region`,TRUNCATE(AVG(`Availability`),2) AS availability,`technology` FROM availabilities_cluster"+
			" LEFT JOIN `acrosspm_cluster_map` ON `acrosspm_cluster_map`.`cluster_code` = availabilities_cluster.cluster"+
			" # *"+
			" GROUP BY Clusterx,TIME,technology";
	
	private HashMap<TimeSpan, String> timespanMap;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@PostConstruct
	public void init(){
		timespanMap = new HashMap<>();
		timespanMap.put(TimeSpan.TODAY, "WHERE DATE(TIME) =  CURDATE()");
		timespanMap.put(TimeSpan.LAST_2_DAY, "WHERE DATE(TIME) BETWEEN DATE_SUB(CURDATE(), INTERVAL 1 DAY) AND DATE_ADD(CURDATE(), INTERVAL 1 DAY)");
		timespanMap.put(TimeSpan.LAST_3_DAY, "WHERE DATE(TIME) BETWEEN DATE_SUB(CURDATE(), INTERVAL 2 DAY) AND DATE_ADD(CURDATE(), INTERVAL 1 DAY)");
		timespanMap.put(TimeSpan._1WEEK, "WHERE DATE(TIME) BETWEEN DATE_SUB(CURDATE(), INTERVAL 1 WEEK) AND DATE_ADD(CURDATE(), INTERVAL 1 DAY)");
		timespanMap.put(TimeSpan._2WEEK, "WHERE DATE(TIME) BETWEEN DATE_SUB(CURDATE(), INTERVAL 2 WEEK) AND DATE_ADD(CURDATE(), INTERVAL 1 DAY)");
		timespanMap.put(TimeSpan._3WEEK, "WHERE DATE(TIME) BETWEEN DATE_SUB(CURDATE(), INTERVAL 3 WEEK) AND DATE_ADD(CURDATE(), INTERVAL 1 DAY)");
		timespanMap.put(TimeSpan._4WEEK, "WHERE DATE(TIME) BETWEEN DATE_SUB(CURDATE(), INTERVAL 4 WEEK) AND DATE_ADD(CURDATE(), INTERVAL 1 DAY)");
	}

	@Override
	public List<ClusterAvailability> getAvailability(TimeSpan span) {
		String q = QUERY.replaceFirst("#", timespanMap.get(span));
				q = q.replaceAll("\\*", "");
		
		List<ClusterAvailability> availabilities = jdbc.query(q, new RowMapper<ClusterAvailability>(){

			@Override
			public ClusterAvailability mapRow(ResultSet rs, int i) throws SQLException {
				ClusterAvailability availability = new ClusterAvailability();
				availability.setCluster( rs.getString("Clusterx") );
				availability.setRegion(rs.getString("region"));
				availability.setAvailability(rs.getDouble("Availability"));
				availability.setTechnology(rs.getString("technology"));
				try {
					availability.setTime( df.parse(rs.getString("time")) );
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return availability;
			}
				
		});
		
		return availabilities;
	}


	@Override
	public List<ClusterAvailability> getAvailability(TimeSpan span, Region region, NetworkTechnology netype) {
		String q = QUERY.replaceFirst("#", timespanMap.get(span));
		q = q.replaceAll("\\*", " AND (region = ? AND technology = ?)");
		
		List<ClusterAvailability> availabilities = jdbc.query(q, new Object[]{ region.getRegion(), netype.getTechnology() }, new RowMapper<ClusterAvailability>(){

			@Override
			public ClusterAvailability mapRow(ResultSet rs, int i) throws SQLException {
				ClusterAvailability availability = new ClusterAvailability();
				availability.setCluster( rs.getString("Clusterx") );
				availability.setRegion(rs.getString("region"));
				availability.setAvailability(rs.getDouble("Availability"));
				availability.setTechnology(rs.getString("technology"));
				try {
					availability.setTime( df.parse(rs.getString("time")) );
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return availability;
			}
				
		});
		
		return availabilities;
	}
	
	

}
