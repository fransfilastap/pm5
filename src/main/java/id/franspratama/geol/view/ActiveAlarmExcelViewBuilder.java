package id.franspratama.geol.view;

import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import id.franspratama.geol.core.pojo.ActiveAlarmExport;
import id.franspratama.geol.core.pojo.WFMTicketAndWorkOrder;

public class ActiveAlarmExcelViewBuilder implements AppExcelBuilder<ActiveAlarmExport>{

	@Override
	public Workbook createWorkbook(List<ActiveAlarmExport> data) {
		Workbook workbook = new XSSFWorkbook();
		return createWorkbook(workbook,data);
	}

	@Override
	public Workbook createWorkbook(Workbook workbook, List<ActiveAlarmExport> data) {
 
		String[] headers = { "GROUP", "SITE ID", "USERLABEL", "NODE", "LAST OCCURRENCE", 
								"SUMMARY", "TROUBLE TICKET", "WORK ORDER", 
										"WORK ORDER STATUS", "PIC" , "SUPERVISOR", "MANAGER", 
											"IS OUT OF SERVICE ?" };
		
		Sheet sheet = workbook.createSheet("ACTIVE ALARMS");
		
		Row row = sheet.createRow(0);

		for (int i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
				 cell.setCellValue( headers[i] );
		}
		
		int startRow = 1;
		
		for (Iterator<ActiveAlarmExport> iterator = data.iterator(); iterator.hasNext();) {
			ActiveAlarmExport activeAlarmExport = (ActiveAlarmExport) iterator.next();
			
			Row dataRow = sheet.createRow( startRow );
			
			Cell groupCell = dataRow.createCell(0);
			groupCell.setCellValue( activeAlarmExport.getGroup() );
			
			Cell siteIdCell = dataRow.createCell(1);
			siteIdCell.setCellValue( activeAlarmExport.getSite().getSiteId() );
			
			Cell userLabelCell = dataRow.createCell(2);
			userLabelCell.setCellValue( activeAlarmExport.getSite().getSiteName() );
			
			Cell nodeCell = dataRow.createCell(3);
			nodeCell.setCellValue( activeAlarmExport.getSite().getBscOrRnc() );
			
			Cell lastOccurrenceCell = dataRow.createCell(4);
			lastOccurrenceCell.setCellValue( activeAlarmExport.getAlarm().getLastOccurrence() );
			
			Cell summaryCell = dataRow.createCell(5);
			summaryCell.setCellValue( activeAlarmExport.getAlarm().getSummary() );
			
			Cell troubleTicketCell = dataRow.createCell(6);
			troubleTicketCell.setCellValue( activeAlarmExport.getAlarm().getTTNO() );
			
			if( activeAlarmExport.getTicketAndWorkorder().getWoId() != null ){
				
				//wfm ticket information
				WFMTicketAndWorkOrder wfm =activeAlarmExport.getTicketAndWorkorder();
				
				Cell workOrderCell = dataRow.createCell(7);
				workOrderCell.setCellValue( wfm.getWoId()  );
				
				Cell workOrderStatusCell = dataRow.createCell(8);
				workOrderStatusCell.setCellValue( wfm.getWoStatus() );
				
				Cell workOrderPIC = dataRow.createCell(9);
				workOrderPIC.setCellValue( wfm.getPic() );
				
			}
			
			Cell supervisorCell = dataRow.createCell(10);
			supervisorCell.setCellValue( ( activeAlarmExport.getSite().getSupervisorArea() == null ? "N\\A" : activeAlarmExport.getSite().getSupervisorArea().getSupervisorAreaName() ) );
			
			Cell managerAreaCell = dataRow.createCell(11);
			managerAreaCell.setCellValue( ( activeAlarmExport.getSite().getManagerArea() == null ? "N\\A" : activeAlarmExport.getSite().getManagerArea().getAreaManager() ) );
			
			Cell isOssCell = dataRow.createCell(12);
			isOssCell.setCellValue( activeAlarmExport.getStatus().toString() );
			
			startRow++;
		}
			
		
		return workbook;
	}

}
