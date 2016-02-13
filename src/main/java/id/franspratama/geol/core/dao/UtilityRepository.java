package id.franspratama.geol.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("utilityRepository")
public class UtilityRepository {

	@Autowired
	JdbcTemplate jdbc;
	
	private static final String REGION_LIST_QUERY = "SELECT distinct region from availabilities_cluster";
	
	public List<String> getClusterRegion(){
		List<String> regionList = jdbc.query(REGION_LIST_QUERY,new RowMapper<String>(){

			@Override
			public String mapRow(ResultSet rs, int i) throws SQLException {
				return rs.getString("region");
			}
		});
		
		return regionList;
	}
	
}
