package id.franspratama.geol.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import id.franspratama.geol.core.pojo.ClusterAvailability;

public class ClusterAvailabilityExcelView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> data, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		AppExcelBuilder<ClusterAvailability> excelBuilder = new ClusterAvailabilityExcelBuilder();
		
		@SuppressWarnings("unchecked")
		List<ClusterAvailability> availabilities = (List<ClusterAvailability>) data.get("data");
		excelBuilder.createWorkbook(workbook, availabilities);
		
        response.setHeader("Content-Disposition", "attachment; filename=\"CLUSTER_NAV_EXPORT.xls\"");
        workbook.write( response.getOutputStream() );
	}

}
