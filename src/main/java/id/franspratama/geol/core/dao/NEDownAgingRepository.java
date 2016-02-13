package id.franspratama.geol.core.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.franspratama.geol.core.pojo.NEDownAging;

@Repository
public class NEDownAgingRepository implements INEDownAgingRepository{

	@Autowired
	EntityManager entityManager;
	
	private static final int FILTER_ID = 2;
	
	private static final String QUERY = "SELECT "
					+" 1 as ID, REGION,"
					+" COUNT( CASE WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) >= 0 AND HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) < 4 THEN 1 ELSE NULL END ) ZERO_TO_FOUR_H, "
					+" COUNT( CASE WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) >= 4 AND HOUR( TIMEDIFF( FirstOccurrence, NOW() ) ) < 24  THEN 1 ELSE NULL END ) FOUR_TO_TWENTYFOUR_H, "
					+" COUNT( CASE WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() )) >= 24 AND HOUR( TIMEDIFF( FirstOccurrence, NOW() )) < 72  THEN 1 ELSE NULL END ) ONE_TO_THREE_D, "
					+" COUNT( CASE WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() )) >= 72 AND HOUR( TIMEDIFF( FirstOccurrence, NOW() )) < 168  THEN 1 ELSE NULL END ) THREE_TO_SEVEN_D, "
					+" COUNT( CASE WHEN HOUR( TIMEDIFF( FirstOccurrence, NOW() )) >= 168  THEN 1 ELSE NULL END ) MORE_THAN_SEVEN_D "
					+" FROM "
					+" ( "
					+" SELECT activealarm.* FROM netcool.activealarm "
					+" INNER JOIN geolv2.`FILTERS` ON summary LIKE CONCAT('%',FILTER,'%') WHERE FILTER_TYPE  = "+FILTER_ID+" "
					+" GROUP BY siteid "
					+" ) AS alarm "
					+" INNER JOIN geolv2.`NE` ON alarm.siteid = NE.btsid "
					+" GROUP BY region";
	
	@Override
	public List<NEDownAging> getCurrentNEDownAging() {
		Query query = entityManager.createNativeQuery( QUERY );
		
		List<Object[]> resultListRaw = query.getResultList();
		List<NEDownAging> resultList = new ArrayList<>();
		
		resultListRaw.stream().forEach( object ->{
			NEDownAging neDownAging = new NEDownAging();
						neDownAging.setRegion( (String) object[1] );
						neDownAging.setOneHourToFour( ((BigInteger) object[2] ).intValue() );
						neDownAging.setFourTo24Hour( ( (BigInteger) object[3] ).intValue() );
						neDownAging.setOneToThreeDay( ( (BigInteger) object[4] ).intValue()  );
						neDownAging.setThreeToSevendDay( ( (BigInteger) object[5]  ).intValue() );
						neDownAging.setMoreThanSevenDay( ( (BigInteger) object[6] ).intValue() );
			
			resultList.add(neDownAging);
		});
		
		return resultList;
	}

}
