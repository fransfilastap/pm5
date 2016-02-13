package id.franspratama.geol.core.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.franspratama.geol.core.pojo.AlarmDown;

/**
 * 
 * @author franspotter
 *
 */

@Repository("alarmDownRepositoryJPA")
public class AlarmDownRepository implements IAlarmDownRepository{

	@Autowired EntityManager entityManager;

	private static final String NATIVE_QUERY = "SELECT \n" +
            "eventid,\n" + //0
            "NE.region,\n" + //1
            "NE.btsid,\n" + //2
            "NE.`btsname`,\n" + //3
            "NE.`node`," //4
            + "NE.towerid," //5
            + "NE.mgrarea," //6
            + "NE.spvarea," //7
            + "NE.ms_partner," //8
            + "NE.kabupaten_kodya," //9
            + "NE.source_power," //10
            + "NE.bts_priority," //11
            + "NE.site_type,\n" + //12
            "(CASE WHEN NE.btsid REGEXP 'g$' THEN '3G' ELSE '2G' END) NE_TYPE,\n" + //13
            "GROUP_CONCAT(`summary` SEPARATOR '#') AS `summary`,\n" + //14
            "ttno,\n" + //15
            "FirstOccurrence,\n" + //16
            "( CASE \n" +
            "	WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) >= 0 AND HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) < 4 THEN  '0 - 4 HOURS'\n" +
            "	WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) >= 4 AND HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) < 24 THEN  '4 - 24 HOURS'\n" +
            "	WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) >= 24 AND HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) < 72 THEN  '1 - 3 DAYS'\n" +
            "	WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) >= 72 AND HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) <= 168 THEN  '3 - 7 DAYS'\n" +
            "	WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) >= 168 THEN  '> 7 DAYS'\n" +
            "	ELSE '' \n" +
            "END\n" +
            ") ALARM_PERSIST_TIME,\n" + //17
            "TIME_TO_SEC(TIMEDIFF(TIMESTAMP(NOW()),FirstOccurrence)) AS alarm_age_in_seconds\n" + //18
            "FROM ( \n" +
            " SELECT * FROM \n" +
            " (\n" +
            "	SELECT activealarm.* FROM netcool.activealarm INNER JOIN geolv2.`FILTERS` ON summary LIKE CONCAT('%',FILTER,'%') WHERE FILTER_TYPE  = 2\n" +
            " ) activealarm \n" +
            ") activealarm\n" +
            " INNER JOIN geolv2.`NE` ON activealarm.siteid = NE.btsid\n" +
            "GROUP BY siteid";
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	@Override
	public List<AlarmDown> getCurrentNeDownList() {
		
		 Query query = entityManager.createNativeQuery( NATIVE_QUERY );
		 List<Object[]> rawResults = query.getResultList();
		 List<AlarmDown> alarmDownList = new ArrayList<>();
		 
		 rawResults.stream().forEach( o ->{
			 
			 AlarmDown alarm = new AlarmDown();
			 alarm.setEventId( ((BigInteger) o[0]).intValue() );
			 alarm.setGroup((String) o[1] );
			 alarm.setSiteId( (String) o[2] );
			 alarm.setSite( (String) o[3] );
			 alarm.setNode( (String) o[4] );
			 alarm.setTowerId( (String) o[5] );
			 alarm.setManagerArea( (String) o[6] );
			 alarm.setSupervisorArea( (String) o[7] );
			 alarm.setMsPartner( (String) o[8] );
			 alarm.setCity( (String) o[9] );
			 alarm.setSourcePower( (String) o[10] );
			 alarm.setPriority( (String) o[11] );
			 alarm.setSiteType( (String) o[12] );
			 alarm.setNe( (String) o[13] );
			 alarm.setSummary( (String) o[14] );
			 alarm.setTtno( (String) o[15] );
			 //date
			 Timestamp dat = (Timestamp) o[16];
			 Date date = dat;
			 alarm.setFirstOccurrence( dateFormat.format(date) );
			 alarm.setAgingCategory((String) o[17]);
			 alarm.setAge( ((BigInteger) o[18]).longValue());
			 
			 alarmDownList.add( alarm );
		 });
		return alarmDownList;
	}

}
