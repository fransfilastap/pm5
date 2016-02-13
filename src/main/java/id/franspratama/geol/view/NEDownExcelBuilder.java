package id.franspratama.geol.view;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import id.franspratama.geol.core.pojo.AlarmDown;

@Component("neDownExcelBuilder")
public class NEDownExcelBuilder implements AppExcelBuilder<AlarmDown>{
	

	@Override
	public Workbook createWorkbook(List<AlarmDown> data) {
        Workbook workbook = new XSSFWorkbook();
        fillWorkbook(workbook,data);
        return workbook;
	}

	@Override
	public Workbook createWorkbook(Workbook workbook, List<AlarmDown> data) {
		fillWorkbook(workbook, data);
		return workbook;
	}
	
	private void fillWorkbook(Workbook workbook,List<AlarmDown> data){
		
        Sheet sheet =  workbook.createSheet("RESULT");
        
        Row header = sheet.createRow(0);
        String[] columns = { "REGION","BTS ID", "BTS NAME", "NODE","TYPE", 
        					 "SUMMARY","TTNO", "FIRST OCCURRENCE", "PERSIST RANGE", 
        					 "SITE TYPE", "TOWER ID", "SPV AREA", "MANAGER AREA", "KABUPATEN/KOTA", 
        					 "MS PARTNER", "BTS PRIORITY", "SOURCE POWER",
        					 "WORK ORDER ID","PIC"};
        
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            Cell cell = header.createCell(i);
            cell.setCellValue( column );
        }
        
        int x = 1;
        CellStyle cs = workbook.createCellStyle();
        cs.setWrapText(false);
        cs.setVerticalAlignment( CellStyle.VERTICAL_TOP );
        
        CellStyle cs2 = workbook.createCellStyle();
        cs2.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        for (AlarmDown next : data) {
            Row row = sheet.createRow(x);
            
            Cell group = row.createCell(0);
            group.setCellValue( next.getGroup() );
            
            Cell siteId = row.createCell(1);
            siteId.setCellValue( next.getSiteId() );
            siteId.setCellStyle(cs2);
            
            Cell site = row.createCell(2);
            site.setCellValue( next.getSite() );
            site.setCellStyle(cs2);
            
            Cell node = row.createCell(3);
            node.setCellValue( next.getNode());
            node.setCellStyle(cs2);
            
            Cell neType = row.createCell(4);
            neType.setCellValue( next.getNe());
            neType.setCellStyle(cs2);
            
            Cell summary = row.createCell(5);
            summary.setCellValue( next.getSummary());
            summary.setCellStyle(cs);
            
            Cell ttno = row.createCell(6);
            ttno.setCellValue( next.getTtno() );
            ttno.setCellStyle(cs2);
            
            Cell firstO = row.createCell(7);
            firstO.setCellValue( next.getFirstOccurrence());
            firstO.setCellStyle(cs2);
            
            Cell persistRange = row.createCell(8);
            persistRange.setCellValue(next.getAgingCategory());

            
            Cell siteTypeCell = row.createCell(9);
            siteTypeCell.setCellValue( next.getSiteType() );
            
            Cell towerIdCell = row.createCell(10);
            towerIdCell.setCellValue(next.getTowerId());
            
            Cell spvAreaCell = row.createCell(11);
            spvAreaCell.setCellValue(next.getSupervisorArea());
            
            Cell mgrAreaCell = row.createCell(12);
            mgrAreaCell.setCellValue( next.getManagerArea() );
            
            Cell cityCell = row.createCell(13);
            cityCell.setCellValue(next.getCity());
            
            Cell msPartnerCell = row.createCell(14);
            msPartnerCell.setCellValue(next.getMsPartner());
            
            Cell btsPriorityCell = row.createCell(15);
            btsPriorityCell.setCellValue(next.getPriority());
            
            Cell sourcePowerCell = row.createCell(16);
            sourcePowerCell.setCellValue(next.getSourcePower());
            
            Cell workOrderCell = row.createCell(17);
            workOrderCell.setCellValue( next.getTtAndWO() == null ? "N/A" : next.getTtAndWO().getWoId() );
            
            Cell picCell = row.createCell(18);
            picCell.setCellValue( next.getTtAndWO() == null ? "N/A" : next.getTtAndWO().getPic() );
            
            x++;
        }
	}

}
