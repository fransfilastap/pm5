package id.franspratama.geol.integration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import id.franspratama.geol.core.dao.AppDataSourceService;
import id.franspratama.geol.core.dao.IWFMTicketAndWORepository;
import id.franspratama.geol.core.dao.IWFMTicketAndWorkOrderFileLogRepository;
import id.franspratama.geol.core.pojo.WFMTicketAndWorkOrder;
import id.franspratama.geol.core.pojo.WFMTicketAndWorkOrderFileLog;
import id.franspratama.geol.task.Task;
import id.franspratama.geol.util.FilePair;

@Component("wfmIntegration")
public class WFMIntegration implements Task{

	@Autowired
	IWFMTicketAndWORepository wfmTicketAndWoRepository;
	
	@Autowired
	IWFMTicketAndWorkOrderFileLogRepository wfmTicketAndWorkOrderFileLog;

	@Autowired
	Parser<WFMTicketAndWorkOrder> parser;
	
	@Autowired
	@Qualifier("dbBasedAppDataSourceService")
	private AppDataSourceService appSourceService;
	
	@Override
	@Scheduled(cron="0 0/5 * * * *")
	public void doTask() {
		a();
	}
	
	private void a(){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-");
		
		Date date = new Date();
		File parentDirectory = appSourceService.getSourceDir("WFM_TT_WO");
		StringBuilder filename = new StringBuilder("TT_WO_FOR_GEOL_");
		filename.append( df.format(date) );
		filename.append("\\d+\\:\\d+\\d.csv");
		String regex = filename.toString();
		
		File[] files = parentDirectory.listFiles( (File dir, String name) -> {
			return name.matches(regex);
		});
		
		Date now = new Date();
		for(File file : files){
			if( wfmTicketAndWorkOrderFileLog.findByFilename(file.getName()) != null ){
				continue;
			}

			List<WFMTicketAndWorkOrder> ttAndWoList = parser.parse(file);
			wfmTicketAndWoRepository.save(ttAndWoList);
			
			//if done, rename file as marker that file had been exported
			String newFileName = file.getAbsolutePath().replace(".csv", "_imported.csv");
			File newFile = new File( newFileName );
			file.renameTo( newFile );
			
			WFMTicketAndWorkOrderFileLog fileLog = new WFMTicketAndWorkOrderFileLog();
			fileLog.setFilename(file.getName());
			fileLog.setInsert_time( now );
			
			wfmTicketAndWorkOrderFileLog.save( fileLog );
		}
		
	}

	private File getLatestFile(File[] files){
		FilePair[] pairs = new FilePair[files.length];
		for (int i = 0; i < files.length; i++)
		    pairs[i] = new FilePair(files[i]);
	
		Arrays.sort(pairs);
		
		return pairs[ pairs.length - 1 ].file;
	}
	
	
}
