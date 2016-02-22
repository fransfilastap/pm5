package id.franspratama.geol.web.api;

import id.franspratama.geol.core.pojo.ActiveAlarm;
import id.franspratama.geol.core.pojo.Severity;
import id.franspratama.geol.core.pojo.Site;
import id.franspratama.geol.core.pojo.WFMTicketAndWorkOrder;

public class DTOConverter {
	
	public static ActiveAlarmDTO createAlarmDTO(ActiveAlarm a, WFMTicketAndWorkOrder workorder){
		ActiveAlarmDTO dto = new ActiveAlarmDTO();
		   dto.setAlarmName( a.getAlarmName() );
		   dto.setSite( a.getSite() );
		   dto.setSiteId( a.getSiteId() );
		   dto.setSummary( a.getSummary() );
		   dto.setFirstOccurrence( a.getFirstOccurrence() );
		   dto.setFirstReceived( a.getFirstReceived() );
		   dto.setLastOccurrence( a.getLastOccurrence() );
		   dto.setLastReceived( a.getLastReceived() );
		   dto.setNode( a.getNode() );
		   dto.setSeverity( a.getSeverity() );
		   dto.setTtno( a.getTTNO() );
	
		   if( workorder != null ){
			   dto.setWoId( workorder.getWoId() );
			   dto.setPic( workorder.getPic() );
			   dto.setWoStatus( workorder.getWoStatus() );
		   }
		   
		   return dto;	
	}
	
	public static GisDTO createGisDTO(Site s){
		
		GisDTO gisDTO = new GisDTO();
		gisDTO.setLatitude( s.getLatitue() );
		gisDTO.setLongitude( s.getLongitude() );
		gisDTO.setSiteId( s.getSiteId() );
		gisDTO.setSiteName( s.getSiteName() );
		gisDTO.setType( s.getType() );
		gisDTO.setTechnology(s.getTechnology());
		gisDTO.setTowerProvider( s.getTowerProvider() );
		
		return gisDTO;
	}
	
	public static GisDTO createGisDTO(Site s,Severity severity){
		GisDTO gisDTO = createGisDTO(s);
		gisDTO.setSeverity( severity );
		return gisDTO;
	}
	
	

}
