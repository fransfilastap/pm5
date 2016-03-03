package id.franspratama.geol.integration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import id.franspratama.geol.core.dao.AcrosspmAvailabilityRepository;
import id.franspratama.geol.core.io.Unzip;
import id.franspratama.geol.core.pojo.DailyRegionAvailability;
import id.franspratama.geol.core.services.AppDataSourceService;
import id.franspratama.geol.task.Task;


@Component("acrosspmDailyIntegration")
public class AcrosspmDailyIntegration implements Task{

	@Autowired
	@Qualifier("dbBasedAppDataSourceService")
	private AppDataSourceService appSourceService;
	
	@Autowired @Qualifier("acrosspmDailyRegioAvailabilityParser")
	private Parser<DailyRegionAvailability> regionParser;
	
	@Autowired
	@Qualifier("acrosspmDailyRegionAvailabilityRepository")
	private AcrosspmAvailabilityRepository<DailyRegionAvailability> regionAvailRepository;
	
	private Unzip unzip;
	private SimpleDateFormat dateFormat;
	
	public AcrosspmDailyIntegration() {
		//initialize dateformat in filename
        dateFormat = new SimpleDateFormat("yyyyMMdd");
        
        //our super unziper object
        this.unzip = new Unzip();
	}
	
	@Override
	@Scheduled(cron="0 0/5 * * * *")
	public void doTask() {
		loadLatestDailyAvailability();
	}
	

	public void loadLatestDailyAvailability(){
		
		Date date = new Date();
		File parentDirectory = appSourceService.getSourceDir("ACROSSPM_NAV");
		
		StringBuilder filename = new StringBuilder("GEOL_ALL_REGION_30Days_");
		filename.append(dateFormat.format(date)).append("\\d+_\\d+\\.zip");
		
		String regex = filename.toString();
		
		File dir = new File( parentDirectory, dateFormat.format(date));

        //is folder exists ?
        if( dir.exists() ){
            //find new file which match with current time
        	File[] files = dir.listFiles((File dir1, String name) -> name.matches(regex));
            //if file exists
        	if( files.length > 0 ){
                File __target = files[files.length - 1];

                File excelDir = new File( parentDirectory ,"/extracted/daily/excel/");
                File extractLocation = new File( parentDirectory ,"/extracted/daily/");

                if (excelDir.exists()) {
                    try {
                        FileUtils.deleteDirectory(excelDir);
                    } catch (IOException ex) {
                        Logger.getLogger(AcrosspmHourlyIntegration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                this.unzip.unZipIt( __target.getAbsolutePath(),extractLocation.getAbsolutePath());
            
                if( excelDir.exists() ){
                	
                	String fileRegex = "Region_2G&3G_Availability_30Days_\\d+_\\d+.xlsx";
                    File[] __regionFiles 	= excelDir.listFiles((File dir1, String name) 
                    										-> name.matches( fileRegex ));
                    
                    //parsing excel file
                    if( __regionFiles.length > 0 ){
                    	File _aFile = __regionFiles[ __regionFiles.length - 1 ];
                    	List<DailyRegionAvailability> regionAvailabilities =  regionParser.parse( _aFile );
                    	regionAvailRepository.store( regionAvailabilities );
                    }                
                    
        			//if done, rename file as marker that file had been exported
        			String newFileName = __target.getAbsolutePath().replace(".zip", "_imported.zip");
        			File newFile = new File( newFileName );
        			
        			if( __target.renameTo( newFile )  ){
        				System.out.println("File renamed");
        			}
        			else{
        				System.out.println("can't rename file");
        			}
                }
        	}
        }
     }

}
