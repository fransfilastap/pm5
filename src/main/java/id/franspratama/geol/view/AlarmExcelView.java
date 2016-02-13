package id.franspratama.geol.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;;

public class AlarmExcelView extends AbstractXlsxView{



	@Override
	protected void buildExcelDocument(Map<String, Object> data, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Sheet sheetAlarm = workbook.createSheet("ALARMS");
        String[] columns = new String[]{"REGION", "SITE ID", "SITE NAME", "SITE TYPE","BTS PRIORITY",
            "TOWER PROVIDER", "TOWER ID","SOURCE POWER", "SPV", "MGR", "ALARM", "IS DOWN" };
        
        Row header = sheetAlarm.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            header.createCell(i).setCellValue(column);
        }
        
        response.setHeader("Content-Disposition", "attachment; filename=\"MASSCHECK_RESULT.xls\"");
        workbook.write( response.getOutputStream() );
	}

}
