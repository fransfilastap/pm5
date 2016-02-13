package id.franspratama.geol.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import id.franspratama.geol.core.pojo.AlarmDown;

public class NEDownExcelView extends AbstractXlsxView{
	
	@Override
	protected void buildExcelDocument(Map<String, Object> data, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		NEDownExcelBuilder excelBuilder = new NEDownExcelBuilder();
		@SuppressWarnings("unchecked")
		List<AlarmDown> neDown = (List<AlarmDown>) data.get("data"); 
		excelBuilder.createWorkbook(workbook, neDown);
		
        response.setHeader("Content-Disposition", "attachment; filename=\"NEDOWN_EXPORT.xls\"");
        workbook.write( response.getOutputStream() );
		
	}

}
