package id.franspratama.geol.task;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import id.franspratama.geol.core.pojo.EmailModule;
import id.franspratama.geol.core.pojo.NEDown;
import id.franspratama.geol.core.pojo.NEDownAge;
import id.franspratama.geol.core.pojo.NEDownMovement;
import id.franspratama.geol.core.pojo.NEDownStatus;
import id.franspratama.geol.core.pojo.RegionAvailability;
import id.franspratama.geol.core.dao.IEmailModulRepository;
import id.franspratama.geol.core.dao.INEDownRepository;
import id.franspratama.geol.core.dao.ITimespanBasedAvailabilityRepository;
import id.franspratama.geol.core.mail.DailyRegionAvailabilityMessageComposingStrategy;
import id.franspratama.geol.core.mail.DashboardMessageComposingStrategy;
import id.franspratama.geol.core.mail.DefaultMessagingContext;
import id.franspratama.geol.core.mail.MessagingContext;
import id.franspratama.geol.core.pojo.AlarmDown;
import id.franspratama.geol.core.pojo.ClusterAvailability;
import id.franspratama.geol.core.pojo.DailyRegionAvailability;
import id.franspratama.geol.core.pojo.TimeSpan;
import id.franspratama.geol.core.services.NEDownService;
import id.franspratama.geol.core.helper.DateHelper;;


@Component
public class EmailTask{
	
	@Value("${spring.mail.host}")
	private String host;
	@Value("${spring.mail.port}")
	private String port;
	
	@Autowired
	IEmailModulRepository emailModuleRepo;
	
	@Autowired
	DailyRegionAvailabilityMessageComposingStrategy dailyAvailabilityMessage;
	
	@Autowired
	DashboardMessageComposingStrategy dashboardAvailabilityMessage;
	
	@Autowired
	@Qualifier("dailyRegionJDBCAvailabilityRepository")
	ITimespanBasedAvailabilityRepository<DailyRegionAvailability> dailyRegionNAVRepo;
	
	@Autowired
	@Qualifier("jdbcRegionAvailabilityRepository")
	private ITimespanBasedAvailabilityRepository<RegionAvailability> regionNAVRepo;
	
	
	@Autowired
	@Qualifier("jdbcClusterAvailabilityRepository")
	private ITimespanBasedAvailabilityRepository<ClusterAvailability> clusterNAVRepo;
	
	@Autowired
	private INEDownRepository neDownRepository;
	
	@Autowired
	private NEDownService neDownservice;	
	
	@Scheduled(cron="0 15 4,8,12,16,20,23 * * *")
	public void sendDashboard() throws MessagingException, IOException{
		
		EmailModule moduleDashboard = emailModuleRepo.findByModuleKey("DASHBOARD_EMAIL");
		
		if( moduleDashboard.getModuleParam().equalsIgnoreCase("on") ){
			
			List<RegionAvailability> regionAvailabilityRaw 	= regionNAVRepo.getAvailability(TimeSpan.TODAY);
			List<ClusterAvailability> clustersAvailability 	= clusterNAVRepo.getAvailability(TimeSpan.TODAY);
			List<NEDownMovement> neDownMovements 			= neDownRepository.getNeDownMovementDaily(NEDownAge.ALL, TimeSpan._1WEEK);
			List<AlarmDown> alarmDowns 						= neDownservice.getCurrentNEDownList();
			List<NEDownStatus> neDownStatus 				= neDownRepository.getNeDownStatusHourly();
			List<NEDown> neDowns 							= neDownRepository.getNEDownSummary();
		
			Map<Object,Object> content = new HashMap<>();
							   content.put(DashboardMessageComposingStrategy.REGION_NAV_LIST, regionAvailabilityRaw);
							   content.put(DashboardMessageComposingStrategy.CLUSTER_NAV_LIST, clustersAvailability);
							   content.put(DashboardMessageComposingStrategy.NEDOWN_MOVEMENT_LIST, neDownMovements);
							   content.put(DashboardMessageComposingStrategy.NEDOWN_LIST, alarmDowns);
							   content.put(DashboardMessageComposingStrategy.NEDOWN_STATUS, neDownStatus);
							   content.put(DashboardMessageComposingStrategy.NEDOWN_SUMMARY, neDowns);
			
			MessagingContext message = new DefaultMessagingContext(host, port);
								 message.getMessage().addRecipients( RecipientType.TO, moduleDashboard.getRecepients() );
								 message.getMessage().setRecipients( RecipientType.CC , moduleDashboard.getCc());
								 message.getMessage().setSubject( moduleDashboard.getEmailSubject() );
								 message.getMessage().setFrom( moduleDashboard.getEmailAddress() );
								 
			dashboardAvailabilityMessage.composeMessage(content, message);
				
			Transport.send(message.getMessage());			
				
		}
	}
	
	@Scheduled(cron="0 30 8 * * *")
	public void sendDailyAvailability() throws MessagingException, IOException{
		EmailModule moduleDailyNav = emailModuleRepo.findByModuleKey("DASHBOARD_EMAIL_MONTHLY");
		if( moduleDailyNav.getModuleParam().equalsIgnoreCase("on") ){
			
			//if it's begining of month, we send all data from previous month
			TimeSpan span = ( DateHelper.isBeginingOfMonth(new Date()) ? TimeSpan.LAST_MONTH : TimeSpan.THIS_MONTH );
			
			List<DailyRegionAvailability> dataNav = dailyRegionNAVRepo.getAvailability( span );
			
			Map<Object,Object> content = new HashMap<>();
			  	  			   content.put("nav", dataNav);
			
			MessagingContext message = new DefaultMessagingContext(host, port);
							 message.getMessage().addRecipients( RecipientType.TO, moduleDailyNav.getRecepients() );
							 message.getMessage().setRecipients( RecipientType.CC , moduleDailyNav.getCc());
							 message.getMessage().setSubject( moduleDailyNav.getEmailSubject() );
							 message.getMessage().setFrom( moduleDailyNav.getEmailAddress() );
							 
			dailyAvailabilityMessage.composeMessage(content, message);
			
			Transport.send( message.getMessage() );			
			 
		}
	}

}
	