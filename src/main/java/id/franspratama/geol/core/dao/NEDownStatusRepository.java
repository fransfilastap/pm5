package id.franspratama.geol.core.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.franspratama.geol.core.pojo.NEDownStatus;

@Repository
public class NEDownStatusRepository implements INEDownStatusRepository{

	@Autowired
	EntityManager entityManager;
	
	private static final String QUERY = "SELECT "+
								" TIME,"+
								"DATE_SUB(TIME, INTERVAL 1 HOUR) AS _BEFORE, "+
								"REGION, "+
								"COUNT( "+
								"	CASE WHEN REMARK = 'OPEN' THEN 1 ELSE NULL END "+
								") _OPEN,"+
								"COUNT( "+
								"	CASE WHEN REMARK = 'NEW' THEN 1 ELSE NULL END "+
								") _NEW, "+
								"COUNT( "+
								"	CASE WHEN REMARK = 'CLOSED' THEN 1 ELSE NULL END "+
								") _CLOSED "+
							"FROM "+
							"geolv2.`NEDOWN_LIST_HOURLY_REMARKS` "+
							"WHERE DATE(TIME) = DATE(NOW()) AND HOUR(TIME) = HOUR(NOW()) "+
							"GROUP BY TIME,REGION";
	
	private static final String QUERY2 = "SELECT "+
			" TIME,"+
			"DATE_SUB(TIME, INTERVAL 1 DAY) AS _BEFORE, "+
			"REGION, "+
			"COUNT( "+
			"	CASE WHEN REMARK = 'OPEN' THEN 1 ELSE NULL END "+
			") _OPEN,"+
			"COUNT( "+
			"	CASE WHEN REMARK = 'NEW' THEN 1 ELSE NULL END "+
			") _NEW, "+
			"COUNT( "+
			"	CASE WHEN REMARK = 'CLOSED' THEN 1 ELSE NULL END "+
			") _CLOSED "+
		"FROM "+
		"geolv2.`NEDOWN_LIST_DAILY_REMARKS` "+
		"WHERE DATE(TIME) = DATE(NOW()) "+
		"GROUP BY TIME,REGION";

	@Override
	public List<NEDownStatus> getNeDownStatusHourly() {
		
		Query query = entityManager.createNativeQuery( QUERY );
		List<Object[]> rawResults = query.getResultList();
		List<NEDownStatus> result = new ArrayList<>();
		
		rawResults.stream().forEach(object->{
			
			NEDownStatus mObj = new NEDownStatus();
						 mObj.setRegion( (String) object[2] );
						 mObj.setTime( (Date)(object[0]) );
						 mObj.setTimeBefore( (Date)(object[1]) );
						 mObj.setDownNew( ( (BigInteger) object[4] ).intValue() );
						 mObj.setDownClosed( ( (BigInteger) object[5] ).intValue() );
						 mObj.setDownOpen( ( (BigInteger) object[3] ).intValue() );
						 
			result.add(mObj);
		});
		
		return result;
	}

	@Override
	public List<NEDownStatus> geNeDownStatusDaily() {
		Query query = entityManager.createNativeQuery( QUERY2 );
		List<Object[]> rawResults = query.getResultList();
		List<NEDownStatus> result = new ArrayList<>();
		
		rawResults.stream().forEach(object->{
			
			NEDownStatus mObj = new NEDownStatus();
						 mObj.setRegion( (String) object[2] );
						 mObj.setTime( (Date)(object[0]) );
						 mObj.setTimeBefore( (Date)(object[1]) );
						 mObj.setDownNew( ( (BigInteger) object[4] ).intValue() );
						 mObj.setDownClosed( ( (BigInteger) object[5] ).intValue() );
						 mObj.setDownOpen( ( (BigInteger) object[3] ).intValue() );
						 
			result.add(mObj);
		});
		
		return result;	}

}
