package id.franspratama.geol.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import id.franspratama.geol.core.pojo.DailyRegionAvailability;
import id.franspratama.geol.core.pojo.NetworkTechnology;
import id.franspratama.geol.core.pojo.Region;
import id.franspratama.geol.core.pojo.TimeSpan;

@Repository("dailyRegionJDBCAvailabilityRepository")
public class DailyRegionJDBCAvailabilityRepository implements ITimespanBasedAvailabilityRepository<DailyRegionAvailability>{

	@Autowired
	public JdbcTemplate jdbc;
	
	private HashMap<TimeSpan, String> timespanMap;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private static final String QUERY  = "SELECT * FROM daily_availability_region WHERE MONTH(NOW()) = MONTH(TIME) AND YEAR(NOW()) = YEAR(TIME)";
	private static final String LAST_MONTH_QUERY = "SELECT * FROM daily_availability_region "+
			"WHERE YEAR(time) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)"+
			"AND MONTH(time) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)"
			+ " GROUP BY TIME,region,technology";
	
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
	public List<DailyRegionAvailability> getAvailability(TimeSpan span) {

		String finalQuery = QUERY;
		if( span == TimeSpan.LAST_MONTH ){
			finalQuery = LAST_MONTH_QUERY;
		}
		else if( span == TimeSpan.THIS_MONTH ){
			finalQuery = QUERY;
		}
		
		List<DailyRegionAvailability> navs = jdbc.query(finalQuery, new RowMapper<DailyRegionAvailability>(){

			@Override
			public DailyRegionAvailability mapRow(ResultSet res, int i) throws SQLException {
				
				DailyRegionAvailability nav = new DailyRegionAvailability();
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
	public List<DailyRegionAvailability> getAvailability(TimeSpan span, Region region, NetworkTechnology technology) {
		
		return getAvailability(span).stream().filter(p->{
			return p.getTechnology().equalsIgnoreCase(technology.getTechnology());
		}).collect(Collectors.toList());
	}

}
