package id.franspratama.geol.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import id.franspratama.geol.core.pojo.AlarmDown;
import id.franspratama.geol.core.pojo.NEDown;
import id.franspratama.geol.core.pojo.NEDownAge;
import id.franspratama.geol.core.pojo.NEDownMovement;
import id.franspratama.geol.core.pojo.NEDownStatus;
import id.franspratama.geol.core.pojo.TimeSpan;

@Repository("nedownJdbcRepository")
public class NEDownJDBCRepository implements INEDownRepository{
	
	private static final String NEDOWN_MOVEMENT_QUERY="SELECT TIME,`REGION`, ( MAX( `ONE_TO_FOUR_H` ) + MAX( `FOUR_TO_24_H` ) + MAX( `ONE_TO_THREE_D` ) + MAX( `THREE_TO_SEVEN_D` )+ MAX( `MORE_THAN_SEVEN_D`)) as VAL\n"+
            "FROM geolv2.`NEDOWN_BY_AGING_1HOUR` WHERE TIME BETWEEN DATE_SUB( CURDATE(), INTERVAL 3 DAY ) AND DATE_ADD(CURDATE(), INTERVAL 1 DAY) GROUP BY region,TIME";
	
	private static final String NEDOWN_STATUS_QUERT = "SELECT\n" +
			"									TIME,\n" +
			"									DATE_SUB(TIME, INTERVAL 1 HOUR) AS _BEFORE,\n" +
			"									REGION,\n" +
			"									COUNT(\n" +
			"										CASE WHEN REMARK = 'OPEN' THEN 1 ELSE NULL END\n" +
			"									) _OPEN,\n" +
			"									COUNT(\n" +
			"										CASE WHEN REMARK = 'NEW' THEN 1 ELSE NULL END\n" +
			"									) _NEW,\n" +
			"									COUNT(\n" +
			"										CASE WHEN REMARK = 'CLOSED' THEN 1 ELSE NULL END\n" +
			"									) _CLOSED\n" +
			"								FROM\n" +
			"								geolv2.`NEDOWN_LIST_HOURLY_REMARKS`\n" +
			"								WHERE DATE(TIME) = DATE(NOW()) AND HOUR(TIME) = HOUR(NOW())\n" +
			"								GROUP BY TIME,REGION";
	
	private static final String NEDOWN_LIST = "SELECT \n" +
            "eventid,\n" +
            "NE.region,\n" +
            "NE.btsid,\n" +
            "NE.`btsname`,\n" +
            "NE.`node`,NE.towerid,NE.mgrarea,NE.spvarea,NE.ms_partner,NE.kabupaten_kodya,NE.source_power,NE.bts_priority,NE.site_type,\n" +
            "(CASE WHEN NE.btsid REGEXP 'g$' THEN '3G' ELSE '2G' END) NE_TYPE,\n" +
            "GROUP_CONCAT(`summary` SEPARATOR '#') AS `summary`,\n" +
            "ttno,\n" +
            "FirstOccurrence,\n" +
            "( CASE \n" +
            "	WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) >= 0 AND HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) < 4 THEN  '0 - 4 HOURS'\n" +
            "	WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) >= 4 AND HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) < 24 THEN  '4 - 24 HOURS'\n" +
            "	WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) >= 24 AND HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) < 72 THEN  '1 - 3 DAYS'\n" +
            "	WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) >= 72 AND HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) <= 168 THEN  '3 - 7 DAYS'\n" +
            "	WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) >= 168 THEN  '> 7 DAYS'\n" +
            "	ELSE '' \n" +
            "END\n" +
            ") ALARM_PERSIST_TIME,\n" +
            "TIME_TO_SEC(TIMEDIFF(TIMESTAMP(NOW()),FirstOccurrence)) AS alarm_age_in_seconds\n" +
            "FROM ( \n" +
            " SELECT * FROM \n" +
            " (\n" +
            "	SELECT activealarm.* FROM netcool.activealarm INNER JOIN geolv2.`FILTERS` ON summary LIKE CONCAT('%',FILTER,'%') WHERE FILTER_TYPE  = 2\n" +
            " ) activealarm \n" +
            ") activealarm\n" +
            " INNER JOIN geolv2.`NE` ON activealarm.siteid = NE.btsid\n" +
            "GROUP BY siteid";
	
	
	private static final String QUERY_TOTAL_NE_DOWN_PER_REGION = "SELECT NE.REGION,COUNT( CASE WHEN btsid NOT REGEXP 'g$' THEN 1 ELSE NULL END ) NEDOWN_2G, COUNT( CASE WHEN btsid REGEXP 'g$' THEN 1 ELSE NULL END ) NEDOWN_3G,"+
							" TOTAL_2G_NE,TOTAL_3G_NE "+
							" FROM "+
							" ( "+
							"	SELECT activealarm.* FROM netcool.activealarm"+
							"	INNER JOIN geolv2.`FILTERS` ON summary LIKE CONCAT('%',FILTER,'%') WHERE FILTER_TYPE  = 2"+
							"	GROUP BY siteid"+
							" ) AS alarm"+
							" INNER JOIN geolv2.`NE` ON alarm.siteid = NE.btsid"+
							" INNER JOIN ("+ 
							" SELECT"+
							" REGION,"+
							" COUNT( CASE WHEN btsid NOT REGEXP 'g$' THEN 1 ELSE NULL END ) TOTAL_2G_NE,"+
							" COUNT( CASE WHEN btsid REGEXP 'g$' THEN 1 ELSE NULL END ) TOTAL_3G_NE"+
							" FROM geolv2.NE GROUP BY region"+
							" ) zzz ON zzz.region = NE.region"+
							" GROUP BY region";
	

	private static final String NE_DOWN_AGING_MOVEMENT_HOURLY_QUERY = "SELECT `TIME` AS TIME,`REGION`, @ as VAL "+
			"FROM geolv2.`NEDOWN_BY_AGING_1HOUR` WHERE TIME BETWEEN DATE_SUB( CURDATE(), INTERVAL # ) AND DATE_ADD(CURDATE(), INTERVAL 1 DAY) GROUP BY region,TIME";
	
	private static final String NE_DOWN_AGING_MOVEMENT_DAILY_QUERY = "SELECT `TIME` AS TIME,`REGION`, @ as VAL "+
			"FROM geolv2.`NEDOWN_BY_AGING_1HOUR` WHERE TIME BETWEEN DATE_SUB( CURDATE(), INTERVAL # ) AND DATE_ADD(CURDATE(), INTERVAL 1 DAY) GROUP BY region,DATE(TIME)";
	
	
	private HashMap<NEDownAge, String> agingMap;
	private HashMap<TimeSpan, String> timespanMap;
	
	@Autowired
	JdbcTemplate jdbc;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@PostConstruct
	public void init() {
		
		agingMap = new HashMap<>();
		agingMap.put(NEDownAge.ALL, "( MAX( `ONE_TO_FOUR_H` ) + MAX( `FOUR_TO_24_H` ) + MAX( `ONE_TO_THREE_D` ) + MAX( `THREE_TO_SEVEN_D` )+ MAX( `MORE_THAN_SEVEN_D`))");
		agingMap.put(NEDownAge.ONE_TO_FOUR_HOUR, "MAX(`ONE_TO_FOUR_H`)");
		agingMap.put(NEDownAge.FOUR_TO_24_HOUR, "MAX( `FOUR_TO_24_H` )");
		agingMap.put(NEDownAge.ONE_TO_THREE_DAY, "MAX( `ONE_TO_THREE_D` )");
		agingMap.put(NEDownAge.LTOA, "MAX(( `THREE_TO_SEVEN_D` )+( `MORE_THAN_SEVEN_D` ))");
		
		timespanMap = new HashMap<>();
		timespanMap.put(TimeSpan._1WEEK, "1 WEEK");
		timespanMap.put(TimeSpan._2WEEK, "2 WEEK");
		timespanMap.put(TimeSpan._3WEEK, "3 WEEK");
		timespanMap.put(TimeSpan._4WEEK, "4 WEEK");
		
		
	}
	

	@Override
	public List<NEDownStatus> getNeDownStatusHourly() {
		List<NEDownStatus> nedownStatus = jdbc.query(NEDOWN_STATUS_QUERT, new RowMapper<NEDownStatus>(){

			@Override
			public NEDownStatus mapRow(ResultSet rs, int i) throws SQLException {
				NEDownStatus status  = new NEDownStatus();
				status.setRegion( rs.getString("REGION") );
				status.setDownClosed( rs.getInt("_CLOSED") );
				status.setDownNew( rs.getInt("_NEW") );
				status.setDownOpen( rs.getInt("_OPEN") );
				try {
					status.setTime( df.parse(rs.getString("TIME")) );
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return status;
			}
			
		});
		return nedownStatus;
	}

	@Override
	public List<AlarmDown> getCurrentNeDownList() {
		
		List<AlarmDown> alarms = jdbc.query(NEDOWN_LIST, new RowMapper<AlarmDown>(){

			@Override
			public AlarmDown mapRow(ResultSet rs, int i) throws SQLException {
                AlarmDown alarm = new AlarmDown();
                alarm.setEventId( rs.getInt("eventid") );
                alarm.setGroup( rs.getString("region") );
                alarm.setSiteId( rs.getString("btsid") );
                alarm.setSite( rs.getString("btsname") );
                alarm.setNode( rs.getString("node") );
                alarm.setNe( rs.getString("NE_TYPE") );
                alarm.setSummary( rs.getString("summary") );
                alarm.setTtno( rs.getString("ttno") );
                alarm.setFirstOccurrence(rs.getString("firstOccurrence"));
                alarm.setAgingCategory( rs.getString("ALARM_PERSIST_TIME") );
                alarm.setAge( rs.getInt("alarm_age_in_seconds") );
                alarm.setTowerId(rs.getString("towerid"));
                alarm.setManagerArea( rs.getString("mgrarea") );
                alarm.setSupervisorArea(rs.getString("spvarea"));
                alarm.setMsPartner(rs.getString("ms_partner"));
                alarm.setCity(rs.getString("kabupaten_kodya"));
                alarm.setSourcePower(rs.getString("source_power"));
                alarm.setPriority(rs.getString("bts_priority"));
                alarm.setSiteType( rs.getString("site_type") );
                
                return alarm;
			}
			
		});
		return alarms;
	}

	@Override
	public List<NEDown> getNEDownSummary() {

		List<NEDown> neDowns = jdbc.query(QUERY_TOTAL_NE_DOWN_PER_REGION, new RowMapper<NEDown>(){

			@Override
			public NEDown mapRow(ResultSet rs, int i) throws SQLException {
				
				NEDown neDown = new NEDown();
				
				String region 	= rs.getString("REGION");
				int neDown2g 	= rs.getInt("NEDOWN_2G");
				int neDown3g 	= rs.getInt("NEDOWN_3G");
				int total2gNe 	= rs.getInt("TOTAL_2G_NE");
				int total3gNe 	= rs.getInt("TOTAL_3G_NE");
				
				System.out.println( total2gNe );
				
				double percentage2GDown = (neDown2g/total2gNe)/100;
				double percentage3GDown = (neDown3g/total3gNe)/100;
				
 				neDown.setRegion( region );
				neDown.setNeDown2g( neDown2g  );
				neDown.setNeDown3g( neDown3g );
				neDown.setTotalNe2g( total2gNe  );
				neDown.setTotalNe3g( total3gNe );
				neDown.setPercentageNeDown3g( percentage3GDown );
				neDown.setPercentageNeDown2g( percentage2GDown );
				
				return neDown;
			}
			
		});
		
		return neDowns;
	}

	@Override
	public List<NEDownMovement> getNeDownMovementHourly(NEDownAge age,TimeSpan timespan) {
		
		String query = NE_DOWN_AGING_MOVEMENT_HOURLY_QUERY;
			  query = query.replaceFirst("@",agingMap.get(age) );
			  query = query.replaceAll("#", timespanMap.get(timespan));
		
		List<NEDownMovement> movements = jdbc.query(query,new RowMapper<NEDownMovement>(){
			@Override
			public NEDownMovement mapRow(ResultSet rs, int i) throws SQLException {
				NEDownMovement movement = new NEDownMovement();
				movement.setRegion(rs.getString("REGION"));
				movement.setTotalDown(rs.getInt("VAL"));;
				movement.setTime( rs.getTimestamp("TIME") );
				return movement;
			}
		});
		return movements;
	}

	@Override
	public List<NEDownMovement> getNeDownMovementDaily(NEDownAge age, TimeSpan timespan) {
		String query = NE_DOWN_AGING_MOVEMENT_DAILY_QUERY;
			  query = query.replaceFirst("@",agingMap.get(age) );
			  query = query.replaceAll("#", timespanMap.get(timespan));
		
		List<NEDownMovement> movements = jdbc.query(query,new RowMapper<NEDownMovement>(){
			@Override
			public NEDownMovement mapRow(ResultSet rs, int i) throws SQLException {
				NEDownMovement movement = new NEDownMovement();
				movement.setRegion(rs.getString("REGION"));
				movement.setTotalDown(rs.getInt("VAL"));;
				movement.setTime( rs.getTimestamp("TIME") );
				
				return movement;
			}
		});
		return movements;
	}
	

}
