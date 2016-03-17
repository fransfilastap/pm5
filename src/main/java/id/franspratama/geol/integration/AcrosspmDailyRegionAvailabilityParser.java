package id.franspratama.geol.integration;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import id.franspratama.geol.core.helper.DateHelper;
import id.franspratama.geol.core.pojo.DailyRegionAvailability;

/**
 * 
 * TOLONG DIPERHATIKAN UNTUK PROGRAMMER SELANJUTNYA
 * Kolom TCH adalah untuk teknologi 2G dan Cell untuk teknologi 3G
 * 
 * 
 * 
 * 
 * 
 * @author fransfilastap
 *
 */
@Component("acrosspmDailyRegioAvailabilityParser")
public class AcrosspmDailyRegionAvailabilityParser implements Parser<DailyRegionAvailability>{

	@Override
	public List<DailyRegionAvailability> parse(File file) {
		
		List<DailyRegionAvailability> availabilities = new ArrayList<>();
		XSSFWorkbook workbook;
		
		try {
			workbook = new XSSFWorkbook( file );
			SimpleDateFormat sqlTimestampFormat = new SimpleDateFormat("yyyy-MM-dd");
			XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
		
			int lastRowNum = sheet.getLastRowNum();
			
			Calendar cal = Calendar.getInstance();

			for (int i = 2; i <= lastRowNum; i++) {
				
				Row row = sheet.getRow(i);
				Cell dateCell = row.getCell(0);
				
				Date time = null;

				switch (dateCell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					time = ( sqlTimestampFormat.parse(dateCell.getStringCellValue()) );
					break;
				case Cell.CELL_TYPE_NUMERIC:
					time = dateCell.getDateCellValue();
					break;
				default:
					break;
				}	

				Cell regionCell = row.getCell(1);
				Cell cellCell = row.getCell(2);
				Cell tchCell = row.getCell(3);
				
				String regionVal = regionCell.getStringCellValue();
				double tchVal = 0;
				double cellVal = 0;

				cal.setTime(time);
				cal.add(Calendar.HOUR, +1);
				time = cal.getTime();
				
				switch (tchCell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					tchVal = ( tchCell.getStringCellValue().trim().isEmpty() ? -1 : Double.parseDouble(tchCell.getStringCellValue()) );
					break;
				case Cell.CELL_TYPE_NUMERIC:
					tchVal = tchCell.getNumericCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					tchVal = -1;
					break;
				default:
					break;
				}
				
				switch (cellCell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					cellVal = ( cellCell.getStringCellValue().trim().isEmpty() ? -1 : Double.parseDouble(cellCell.getStringCellValue()) );
					break;
				case Cell.CELL_TYPE_NUMERIC:
					cellVal = cellCell.getNumericCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					cellVal = -1;
					break;
				default:
					break;
				}	
				
				DailyRegionAvailability avl2g = new DailyRegionAvailability();
							 avl2g.setAvailability( tchVal );
							 avl2g.setTechnology("2G");
							 avl2g.setTime(time);
							 avl2g.setRegion(regionVal);
				
				DailyRegionAvailability avl3g = new DailyRegionAvailability();
							 avl3g.setAvailability( cellVal );
							 avl3g.setTechnology("3G");
							 avl3g.setTime(time);
							 avl3g.setRegion(regionVal);
				
				availabilities.add( avl2g );
				availabilities.add( avl3g );
				
			}
			
			workbook.close();
			
			Date now = new Date();
			
			List<DailyRegionAvailability> availabilitiesFiltered = availabilities.stream().filter( avl ->{
				return extracted(now, avl);
			}).collect(Collectors.toList());
			
			return availabilitiesFiltered;
			
		} catch (InvalidFormatException | IOException | ParseException e) {
			e.printStackTrace();
		}

		return availabilities;
	}

	private boolean extracted(Date now, DailyRegionAvailability avl) {
		return ( DateHelper.isInSameMonth(avl.getTime(), now) && avl.getAvailability() > -1);
	}
}
