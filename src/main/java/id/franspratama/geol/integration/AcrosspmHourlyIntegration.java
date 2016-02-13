package id.franspratama.geol.integration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import id.franspratama.geol.core.dao.AcrosspmAvailabilityRepository;
import id.franspratama.geol.core.dao.AppDataSourceService;
import id.franspratama.geol.core.io.Unzip;
import id.franspratama.geol.core.pojo.ClusterAvailability;
import id.franspratama.geol.core.pojo.RegionAvailability;
import id.franspratama.geol.task.Task;

@Component("acrosspmAvailability")
public class AcrosspmHourlyIntegration implements Task{

	private SimpleDateFormat dateFormat;
	private SimpleDateFormat hourFormat;
	
	@Autowired @Qualifier("regionAvailbailityParser")
	private Parser<RegionAvailability> regionParser;
	
	@Autowired @Qualifier("clusterAvailabilityParser")
	private Parser<ClusterAvailability> clusterParser;
	
	@Autowired
	@Qualifier("acrosspmRegionAvailabilityRepository")
	private AcrosspmAvailabilityRepository<RegionAvailability> regionAvailRepository;
	
	@Autowired
	@Qualifier("acrosspmClusterAvailabilityRepository")
	private AcrosspmAvailabilityRepository<ClusterAvailability> clusterAvailRepository;
	
	@Autowired
	@Qualifier("dbBasedAppDataSourceService")
	private AppDataSourceService appSourceService;
	
	private Unzip unzip;
	
	@PostConstruct
	public void initialize(){
		//initialize dateformat in filename
        dateFormat = new SimpleDateFormat("yyyyMMdd");
        hourFormat = new SimpleDateFormat("HH");
        
        //our super unziper object
        this.unzip = new Unzip();
	}
	

	@Override
	@Scheduled(cron="0 0/30 * * * *")
	public void doTask() {
		hourlyAvailability();   
	}
	
	public void hourlyAvailability(){
		
		File parentDirectory = appSourceService.getSourceDir("ACROSSPM_NAV");
		
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.HOUR, -1);
		
		Date oneHourBack = cal.getTime();
		
        StringBuilder filename = new StringBuilder("GEOL_ALL_REGION&CLUSTER_");
        filename.append(dateFormat.format(oneHourBack));
        filename.append(hourFormat.format(oneHourBack)).append("\\d+_").append("\\d+\\.zip");
	
        String regex= filename.toString();		
		
        File dir = new File( parentDirectory, dateFormat.format(oneHourBack) + "/");
        
        //is folder exists ?
        if( dir.exists() ){
            //find new file which match with current time
        	File[] files = dir.listFiles((File dir1, String name) -> name.matches(regex));
            //if file exists
        	if( files.length > 0 ){
                File __target = files[files.length - 1];

                File excelDir = new File( parentDirectory ,"/extracted/excel/");
                File extractLocation = new File( parentDirectory ,"/extracted/");

                if (excelDir.exists()) {
                    try {
                        FileUtils.deleteDirectory(excelDir);
                    } catch (IOException ex) {
                        Logger.getLogger(AcrosspmHourlyIntegration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                this.unzip.unZipIt( __target.getAbsolutePath(),extractLocation.getAbsolutePath());
            
                if( excelDir.exists() ){
                	
                	String clusterFileRegex = "Cluster_2G&3G_Availability_Excel_"
							+dateFormat.format(oneHourBack)+hourFormat.format(oneHourBack)+"\\d+\\.xlsx";
                	String regionFileRegex = "Region_2G&3G_Availability_Excel_"
							+dateFormat.format(oneHourBack)+hourFormat.format(oneHourBack)+"\\d+\\.xlsx";
                	
                    File[] __regionFiles 	= excelDir.listFiles((File dir1, String name) 
                    										-> name.matches( regionFileRegex ));
                    File[] __clusterFiles 	= excelDir.listFiles((File dir1, String name) 
                    									-> name.matches( clusterFileRegex ));
                    
                    //parsing excel file
                    if( __regionFiles.length > 0 ){
                    	File _aFile = __regionFiles[ __regionFiles.length - 1 ];
                    	List<RegionAvailability> regionAvailabilities =  regionParser.parse( _aFile );
                    	regionAvailRepository.store(regionAvailabilities);
                    }
                    
                    if( __clusterFiles.length > 0 ){
                    	File _aFile = __clusterFiles[ __clusterFiles.length - 1 ];
                    	List<ClusterAvailability> clusterAvailabilities = clusterParser.parse( _aFile );
                    	clusterAvailRepository.store(clusterAvailabilities);
                    }
                    
        			//if done, rename file as marker that file had been exported
        			String newFileName = __target.getAbsolutePath().replace(".zip", "_imported.zip");
        			File newFile = new File( newFileName );
        			
        			if( __target.renameTo( newFile )  ){
        				System.out.println("File renamed");
        			}
        			else{
        				System.out.println(newFileName);
        			}
                }
        	}
            
        }		
	}
	
}
