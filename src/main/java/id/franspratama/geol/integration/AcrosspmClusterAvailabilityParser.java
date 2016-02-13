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
import id.franspratama.geol.core.pojo.ClusterAvailability;


@Component("clusterAvailabilityParser")
public class AcrosspmClusterAvailabilityParser implements Parser<ClusterAvailability>{

	private String[] sheetNames = {"Cluster_2G&3G_CENTRAL","Cluster_2G&3G_EAST",
			"Cluster_2G&3G_JABODETABEK_1","Cluster_2G&3G_JABODETABEK_2","Cluster_2G&3G_NORTH"
			,"Cluster_2G&3G_N_SUMATERA","Cluster_2G&3G_S_SUMATERA"};
	
	@Override
	public List<ClusterAvailability> parse(File file) {

		List<ClusterAvailability> availabilities = new ArrayList<>();
		XSSFWorkbook workbook;
		
		try {
			workbook = new XSSFWorkbook( file );
			SimpleDateFormat sqlTimestampFormat = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");

			for (int i = 0; i < sheetNames.length; i++) {
				XSSFSheet sheet = workbook.getSheet( sheetNames[i] );
				System.out.println("Parsing "+sheetNames[i]);
				int lastRowNum = sheet.getLastRowNum();
				
				Calendar cal = Calendar.getInstance();
				Date now = new Date();

				List<ClusterAvailability> clusterAvailabilities = new ArrayList<>();
				
				for (int j = 1; j < lastRowNum; j++) {

					Row row = sheet.getRow(j);
					Cell dateCell = row.getCell(0);
					
					Date time = null;
					
					switch( dateCell.getCellType() ) {
					case Cell.CELL_TYPE_NUMERIC:
						time = dateCell.getDateCellValue();
						break;
					case Cell.CELL_TYPE_STRING:
						time = sqlTimestampFormat.parse( dateCell.getStringCellValue() );
						break;
					default:
						break;
					}

					Cell clusterCell = row.getCell(1);
					Cell regionCell = row.getCell(2);
					Cell tchCell = row.getCell(4);
					Cell cellCell = row.getCell(3);
					
					String clusterVal = clusterCell.getStringCellValue();
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
					
					ClusterAvailability avl2g = new ClusterAvailability();
								 avl2g.setAvailability( tchVal );
								 avl2g.setCluster(clusterVal);
								 avl2g.setTechnology("2G");
								 avl2g.setTime(time);
								 avl2g.setRegion( regionVal );
					
					ClusterAvailability avl3g = new ClusterAvailability();
								 avl3g.setAvailability( cellVal );
								 avl3g.setCluster(clusterVal);
								 avl3g.setTechnology("3G");
								 avl3g.setTime(time);
								 avl3g.setRegion( regionVal );
					
					clusterAvailabilities.add( avl2g );
					clusterAvailabilities.add( avl3g );
				}
				
				List<ClusterAvailability> availabilitiesFiltered = clusterAvailabilities.stream().filter( avl ->{
					return ( DateHelper.compareDate(avl.getTime(), now)  == 0 && avl.getAvailability() > -1);
				}).collect(Collectors.toList());
				
				availabilitiesFiltered.forEach(a->{
					System.out.println( a.getTime()+"\t"+a.getRegion()+"\t"+a.getTechnology()+"\t"+a.getAvailability() );
				});
				
				availabilities.addAll( availabilitiesFiltered );
			}
			
			workbook.close();
			
		} catch (InvalidFormatException | IOException | ParseException e) {
			e.printStackTrace();
		}
		

		return availabilities;
	}

}
