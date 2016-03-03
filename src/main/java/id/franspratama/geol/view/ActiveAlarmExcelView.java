package id.franspratama.geol.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import id.franspratama.geol.core.pojo.ActiveAlarmExport;

public class ActiveAlarmExcelView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		@SuppressWarnings("unchecked")
		Set<ActiveAlarmExport> data = (Set<ActiveAlarmExport>) model.get("data");
		List<ActiveAlarmExport> toSet = new ArrayList<>(data);
		
		AppExcelBuilder<ActiveAlarmExport> excelBuilder = new ActiveAlarmExcelViewBuilder();
		
		excelBuilder.createWorkbook(workbook, toSet);
		
        response.setHeader("Content-Disposition", "attachment; filename=\"ACTIVE_ALARM_GIS.xls\"");
        workbook.write( response.getOutputStream() );
	}

}
