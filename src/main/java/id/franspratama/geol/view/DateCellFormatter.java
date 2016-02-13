package id.franspratama.geol.view;

import java.util.Locale;

import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.DateFormatConverter;

public class DateCellFormatter {
	
	public static short createDateFormatter(String pattern,Workbook workbook){
		String excelFormatPattern = DateFormatConverter.convert(Locale.getDefault(), pattern);
		DataFormat poiFormat = workbook.createDataFormat();
					
		return poiFormat.getFormat(excelFormatPattern);
	}

}
