package id.franspratama.geol.view;

import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import id.franspratama.geol.core.pojo.ClusterAvailability;

public class ClusterAvailabilityExcelBuilder implements AppExcelBuilder<ClusterAvailability>{

	@Override
	public Workbook createWorkbook(List<ClusterAvailability> data) {
		
		Workbook workbook = new XSSFWorkbook();
		
		fillWorkbook(workbook, data);

		return workbook;
	}

	@Override
	public Workbook createWorkbook(Workbook workbook, List<ClusterAvailability> data) {
		fillWorkbook(workbook, data);
		return workbook;
	}
	
	private void fillWorkbook(Workbook workbook, List<ClusterAvailability> data){
		String[] headers = { "Time", "Cluster", "Region", "Availability", "Type" };
		
		Sheet sheet = workbook.createSheet("NAV");
		Row headerRow = sheet.createRow(0);
		
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(0);
				 cell.setCellValue( headers[0] );
		}
		
		//row index start from 1;
		int rowIndex = 1;
		
		//create cellstyle
		CellStyle cellStyle = workbook.createCellStyle();
		
		for (Iterator<ClusterAvailability> iterator = data.iterator(); iterator.hasNext();) {
			ClusterAvailability clusterAvailability = (ClusterAvailability) iterator.next();
			
			Row newRow = sheet.createRow( rowIndex );
			
			Cell timeCell = newRow.createCell(0);
				 cellStyle.setDataFormat( DateCellFormatter.createDateFormatter("dd-MM-yyyy HH:mm", workbook) );
				 timeCell.setCellValue( clusterAvailability.getTime() );
				 timeCell.setCellStyle(cellStyle);
				 
			Cell clusterCell = newRow.createCell(1);
				 clusterCell.setCellValue( clusterAvailability.getCluster() );
			
			Cell regionCell = newRow.createCell(2);
				 regionCell.setCellValue( clusterAvailability.getRegion() );
			
			Cell availabilityCell = newRow.createCell(3);
				 availabilityCell.setCellValue( clusterAvailability.getAvailability() );
			
			Cell networkTypeCell = newRow.createCell(4);
				 networkTypeCell.setCellValue( clusterAvailability.getTechnology() );
				 
			rowIndex++;
		 }
	}

}
