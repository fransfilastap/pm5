package id.franspratama.geol.core.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.franspratama.geol.core.pojo.NEDown;

/**
 * 
 * JPA implementation of NEDownSummary interface. 
 * this class is used for get hourly summary of ne down based on ne type (2g/3g)
 * 
 * @author franspotter
 *
 */

@Repository
public class NEDownSummaryRepository implements INEDownSummaryRepository{

	@Autowired
	EntityManager entityManager;
	
	private static final String QUERY = "SELECT TSTATUS.REGION, "
			+ "TSTATUS.NEDOWN_2G,"
			+ "TSTATUS.NEDOWN_3G, "
			+ "TALL.TOTAL_2G_NE, "
			+ "TALL.TOTAL_3G_NE FROM( "+
			"	SELECT "+
			"	REGION, "+
			"	COUNT( CASE WHEN btsid NOT REGEXP 'g$' THEN 1 ELSE NULL END ) NEDOWN_2G, "+
			"	COUNT( CASE WHEN btsid REGEXP 'g$' THEN 1 ELSE NULL END ) NEDOWN_3G "+
			"	FROM "+
			"	( "+
			"	SELECT activealarm.* FROM netcool.activealarm "+
			"	INNER JOIN geolv2.`FILTERS` ON summary LIKE CONCAT('%',FILTER,'%') WHERE FILTER_TYPE  = 2 "+
			"	GROUP BY siteid "+
			"	) AS alarm "+
			"	INNER JOIN geolv2.`NE` ON alarm.siteid = NE.btsid "+
			"	GROUP BY region "+
			" ) TSTATUS "+
			"INNER JOIN ( "+	
			"SELECT "+
			"	REGION, "+
			"	COUNT( CASE WHEN btsid NOT REGEXP 'g$' THEN 1 ELSE NULL END ) TOTAL_2G_NE, "+
			"	COUNT( CASE WHEN btsid REGEXP 'g$' THEN 1 ELSE NULL END ) TOTAL_3G_NE "+
			"	FROM geolv2.NE GROUP BY region  "+
			") TALL ON TSTATUS.REGION = TALL.REGION";
	
	@Override
	public List<NEDown> getNEDownSummary() {
		Query query = entityManager.createNativeQuery( QUERY );
		List<Object[]> rawResults = query.getResultList();
		List<NEDown> result = new ArrayList<>();
		
		rawResults.stream().forEach(object->{
			NEDown mObj = new NEDown();
				   mObj.setRegion( (String) object[0] );
				   mObj.setNeDown2g((( BigInteger ) object[1]).intValue()  );
				   mObj.setNeDown3g( ((BigInteger) object[2]).intValue() );
				   mObj.setTotalNe2g( ( (BigInteger) object[3] ).intValue() );
				   mObj.setTotalNe3g( ((BigInteger) object[4]).intValue() );
				   mObj.setPercentageNeDown2g( ( ( double )mObj.getNeDown2g()/( double )mObj.getTotalNe2g() )*100 );
				   mObj.setPercentageNeDown3g( ( ( double )mObj.getNeDown3g()/( double )mObj.getTotalNe3g() )*100 );
			
			result.add( mObj );
		});
		
		return result;
	}

}
