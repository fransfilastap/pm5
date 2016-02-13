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

import id.franspratama.geol.core.pojo.NetworkTechnology;
import id.franspratama.geol.core.pojo.Region;
import id.franspratama.geol.core.pojo.RegionAvailability;
import id.franspratama.geol.core.pojo.TimeSpan;

@Repository("jdbcRegionAvailabilityRepository")
public class RegionAvailabilityJDBCRepository implements ITimespanBasedAvailabilityRepository<RegionAvailability>{

	@Autowired
	public JdbcTemplate jdbc;

	private HashMap<TimeSpan, String> timespanMap;

	private static final String QUERY = "SELECT * FROM availabilities_region # @";
	
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
	public List<RegionAvailability> getAvailability(TimeSpan span) {
		
		String q = QUERY.replaceAll("#", timespanMap.get(span));
			   q = q.replaceAll("@", "");
			   
		List<RegionAvailability> navs = jdbc.query(q, new RowMapper<RegionAvailability>(){

			@Override
			public RegionAvailability mapRow(ResultSet res, int i) throws SQLException {
				
				RegionAvailability nav = new RegionAvailability();
				nav.setAvailability( res.getDouble("Availability") );
				nav.setRegion( res.getString("Region") );
				nav.setTechnology( res.getString("Technology") );
				nav.setTime( res.getTimestamp("Time") );
				nav.setId( res.getInt("id") );
				
				return nav;
			}
			
		});
		return navs;
	}

	@Override
	public List<RegionAvailability> getAvailability(TimeSpan span, Region region, NetworkTechnology technology) {
		
		String q = QUERY.replaceAll("#", timespanMap.get(span));
		   	   q = q.replaceAll("@", " AND technology = ? ");
		   	   System.out.println( q );
		   
		List<RegionAvailability> navs = jdbc.query(q, new Object[]{ technology.getTechnology() } ,new RowMapper<RegionAvailability>(){
	
			@Override
			public RegionAvailability mapRow(ResultSet res, int i) throws SQLException {
				
				RegionAvailability nav = new RegionAvailability();
				nav.setAvailability( res.getDouble("Availability") );
				nav.setRegion( res.getString("Region") );
				nav.setTechnology( res.getString("Technology") );
				nav.setTime( res.getTimestamp("Time") );
				nav.setId( res.getInt("id") );
				
				return nav;
			}
			
		});
		return navs;
	}

}
