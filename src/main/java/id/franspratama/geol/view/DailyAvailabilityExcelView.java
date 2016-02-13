package id.franspratama.geol.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import id.franspratama.geol.core.pojo.DailyRegionAvailability;

public class DailyAvailabilityExcelView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> data, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		DailyAvailabilityExcelBuilder excelBuilder = new DailyAvailabilityExcelBuilder();
		@SuppressWarnings("unchecked")
		List<DailyRegionAvailability> neDown = (List<DailyRegionAvailability>) data.get("data"); 
		excelBuilder.createWorkbook(workbook, neDown);
		
        response.setHeader("Content-Disposition", "attachment; filename=\"DAILYNAV_EXPORT.xls\"");
        workbook.write( response.getOutputStream() );
		
	}

}
