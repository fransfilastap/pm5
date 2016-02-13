package id.franspratama.geol.view;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import id.franspratama.geol.core.pojo.DailyRegionAvailability;

@Component("dailyAvailabilityExcelBuilder")
public class DailyAvailabilityExcelBuilder implements AppExcelBuilder<DailyRegionAvailability>{

	@Override
	public Workbook createWorkbook(List<DailyRegionAvailability> data) {
		
		Workbook workbook = new XSSFWorkbook();
		fillData(workbook,data);
		return workbook;
	}

	@Override
	public Workbook createWorkbook(Workbook workbook, List<DailyRegionAvailability> data) {
		fillData(workbook, data);
		return workbook;
	}
	
	private void fillData(Workbook workbook,List<DailyRegionAvailability> data){
		//header
		String[] headers = {"DATE","REGION","TYPE","NAV"};

		CellStyle cellStyle = workbook.createCellStyle();
		
		//create new sheet
		Sheet sheet = workbook.createSheet("NAV DATA");
		//header row
		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			headerRow.createCell(i).setCellValue( headers[i] );
		}
		
		//row index start from line 1 (which is actually line 2)
		int rowIndex = 1;
		for (DailyRegionAvailability dNav : data) {
			Row row = sheet.createRow( rowIndex );
			
			Cell dateCell = row.createCell(0);
				cellStyle.setDataFormat(DateCellFormatter.createDateFormatter("dd-MM-yyyy", workbook));
				dateCell.setCellValue( dNav.getTime() );
				dateCell.setCellStyle(cellStyle);
				
			Cell regionCell = row.createCell(1);
				regionCell.setCellValue( dNav.getRegion() );
				
			Cell typeCell = row.createCell(2);
				typeCell.setCellValue( dNav.getTechnology() );
				
			Cell navCell = row.createCell(3);
				navCell.setCellValue( dNav.getAvailability() );
			
			rowIndex++;
		}

	}
}
