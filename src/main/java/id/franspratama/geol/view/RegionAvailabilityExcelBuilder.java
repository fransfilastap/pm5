package id.franspratama.geol.view;

import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import id.franspratama.geol.core.pojo.RegionAvailability;

public class RegionAvailabilityExcelBuilder implements AppExcelBuilder<RegionAvailability>{

	@Override
	public Workbook createWorkbook(List<RegionAvailability> data) {
		Workbook workbook = new XSSFWorkbook();
		return this.createWorkbook(workbook, data);
	}

	@Override
	public Workbook createWorkbook(Workbook workbook, List<RegionAvailability> data) {
		
		String[] headers = {"Time","Region","Availability","Type"};
		
		Sheet sheet 	= workbook.createSheet("NAV");
		Row headerRow 	= sheet.createRow(0);
		
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
				 cell.setCellValue( headers[i] );
		}
		
		int rowIndex = 1;
		CellStyle style = workbook.createCellStyle();
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			RegionAvailability regionAvailability = (RegionAvailability) iterator.next();
			
			Row newRow = sheet.createRow( rowIndex );
			
			Cell timeCell = newRow.createCell(0);
				 style.setDataFormat( DateCellFormatter.createDateFormatter("dd/MM/yyyy HH:mm", workbook) );
				 timeCell.setCellValue( regionAvailability.getTime() );
				 timeCell.setCellStyle(style);
			
			Cell regionCell = newRow.createCell(1);
				 regionCell.setCellValue( regionAvailability.getRegion() );
			
			Cell availabilityCell = newRow.createCell(2);
				 availabilityCell.setCellValue( regionAvailability.getAvailability() );
			
			Cell typeCell = newRow.createCell(3);
				 typeCell.setCellValue( regionAvailability.getTechnology() );
				 
			rowIndex++;
			
		};
		
		return workbook;
	}
	
	

}
