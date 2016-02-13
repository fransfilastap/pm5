package id.franspratama.geol.core.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import id.franspratama.geol.core.pojo.AlarmDown;
import id.franspratama.geol.core.pojo.ClusterAvailability;
import id.franspratama.geol.core.pojo.ContentIDDTO;
import id.franspratama.geol.core.pojo.NEDown;
import id.franspratama.geol.core.pojo.NEDownMovement;
import id.franspratama.geol.core.pojo.NEDownStatus;
import id.franspratama.geol.core.pojo.NeDownSummary;
import id.franspratama.geol.core.pojo.RegionAvailability;
import id.franspratama.geol.util.AppChart;
import id.franspratama.geol.util.Email;
import id.franspratama.geol.view.AppExcelBuilder;

/**
 * 
 * Strategy for compose dahsboard mail
 * 
 * 
 * @author fransfilastap
 *
 */

@Component
public class DashboardMessageComposingStrategy implements MessageComposingStrategy{

	@Autowired
	@Qualifier("regionAvailabilityChart")
	private AppChart<RegionAvailability> regionChartBuilder; //regional chart builder
	
	@Autowired
	@Qualifier("clusterAvailabilityChart")
	private AppChart<ClusterAvailability> clusterChartBuilder;
	
	@Autowired
	@Qualifier("neDownMovementChart")
	private AppChart<NEDownMovement> neDownMovementChartBuilder;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private AppExcelBuilder<AlarmDown> neDownExcelBuilder;
	

	private File parentFolder = new File( new File(".").getAbsolutePath() );

	
	public static final String REGION_NAV_LIST = "region_nav_list";
	public static final String CLUSTER_NAV_LIST= "cluster_nav_list";
	public static final String NEDOWN_LIST = "nedown_list";
	public static final String NEDOWN_MOVEMENT_LIST = "nedown_movement_list";
	public static final String NEDOWN_STATUS = "nedown_status";
	public static final String NEDOWN_SUMMARY = "nedown_summary";
	
		
	@Override
	public void composeMessage(Map<Object, Object> content, MessagingContext context) {
		
		
		Map<String, File> imageAttachments 					= this.prepareRegionImageAttachements( ( List<RegionAvailability> ) content.get(REGION_NAV_LIST) );
		Map<String,Map<String,File>> clusterImageAttachments= this.prepareClusterImageAttachments( ( List<ClusterAvailability> ) content.get(CLUSTER_NAV_LIST)  );
		File neDownChartFile 								= this.prepareNEDownMovementImage( (List<NEDownMovement>) content.get(NEDOWN_MOVEMENT_LIST) );
		File neDownAttachments 								= this.prepareNEDownAttachment( ( List<AlarmDown> ) content.get(NEDOWN_LIST) );
		List<NEDownStatus> neDownStatus 					= (List<NEDownStatus>) content.get( NEDOWN_STATUS );
		List<NEDown> neDowns 								= (List<NEDown>) content.get( NEDOWN_SUMMARY );
		
		
		List<NeDownSummary> neDownSums = new ArrayList<>();
		
		neDowns.forEach(ne->{
			int totalNe = ne.getTotalNe2g()+ne.getTotalNe3g();
			int totalNeDown = ne.getNeDown2g()+ne.getNeDown3g();
			double percentage = (double)(totalNeDown)*100/(totalNe);
			String region = ne.getRegion();
			
			neDownSums.add(new NeDownSummary(region, totalNe, totalNeDown, percentage));
			
		});
		
		String region2gContentId = Email.getContentId();
		String region3gContentId = Email.getContentId();
		String neDownTrendContentId = Email.getContentId();
		String logoContentID = Email.getContentId();
		
		final Context thymeleafContext = new Context();
		
		thymeleafContext.setVariable("region2gImageResource", region2gContentId);
		thymeleafContext.setVariable("region3gImageResource", region3gContentId);
		thymeleafContext.setVariable("neDownTrendImageResource", neDownTrendContentId);
		thymeleafContext.setVariable("statuses", neDownStatus);
		thymeleafContext.setVariable("summaries", neDownSums);
		thymeleafContext.setVariable("emailDate", new Date());
		thymeleafContext.setVariable("logoImageResource", logoContentID);

		
        
        try {
        	MimeBodyPart avail2gregionImagePart = new MimeBodyPart();
        	avail2gregionImagePart.attachFile( imageAttachments.get("2G_REGION") );
            avail2gregionImagePart.setContentID("<" + region2gContentId + ">");
            avail2gregionImagePart.setDisposition(MimeBodyPart.INLINE);
            
            MimeBodyPart avail3gregionImagePart = new MimeBodyPart();
            avail3gregionImagePart.attachFile( imageAttachments.get("3G_REGION") );
            avail3gregionImagePart.setContentID("<" + region3gContentId + ">");
            avail3gregionImagePart.setDisposition(MimeBodyPart.INLINE);
            
            
    		List<ContentIDDTO> dtos = new ArrayList<>();
    		
    		clusterImageAttachments.entrySet().forEach(entry->{
    			
    			String cluster = entry.getKey();
    			Map<String,File> type = entry.getValue();
    			
    			String contentId2g = Email.getContentId();
    			String contentId3g = Email.getContentId();
    			
    			File file2g = type.get("2G");
    			File file3g = type.get("3G");
    			
    			try {
    		        MimeBodyPart clusterAvailability2gImageResouce = new MimeBodyPart();
    		        clusterAvailability2gImageResouce.attachFile( file2g );
    		        clusterAvailability2gImageResouce.setContentID("<" + contentId2g + ">");
    		        clusterAvailability2gImageResouce.setDisposition(MimeBodyPart.INLINE);
    		        
    		        MimeBodyPart clusterAvailability3gImageResouce = new MimeBodyPart();
    		        clusterAvailability3gImageResouce.attachFile( file3g );
    		        clusterAvailability3gImageResouce.setContentID("<" + contentId3g + ">");
    		        clusterAvailability3gImageResouce.setDisposition(MimeBodyPart.INLINE);
    		        
    		        context.getMultipart().addBodyPart( clusterAvailability2gImageResouce );
    		        context.getMultipart().addBodyPart( clusterAvailability3gImageResouce );
    		        
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    			
    			ContentIDDTO dto = new ContentIDDTO();
    			dto.setName(cluster);
    			dto.setContentId2g(contentId2g);
    			dto.setContentId3g(contentId3g);
    			
    			dtos.add(dto);
    		});
    		
    		thymeleafContext.setVariable("clusters", dtos);
    		
    		MimeBodyPart bodyPart = new MimeBodyPart();
    		
    		final String htmlContent = this.templateEngine.process("mail/dashboard", thymeleafContext);
    		bodyPart.setContent(htmlContent, "text/html");
    		context.getMessage().setSentDate(new Date());
    		
    		//ne down chart
    		MimeBodyPart neDownTrendImagePart = new MimeBodyPart();
    		neDownTrendImagePart.attachFile(neDownChartFile);
    		neDownTrendImagePart.setContentID("<" + neDownTrendContentId + ">");
    		neDownTrendImagePart.setDisposition(MimeBodyPart.INLINE);
    		
    		//logo image
    		//MimeBodyPart logoImagePart = new MimeBodyPart();
    		//logoImagePart.attachFile();
    		//logoImagePart.setContentID("<" + logoContentID + ">");
    		//logoImagePart.setDisposition(MimeBodyPart.INLINE);
    		
    		MimeBodyPart neDownAttch = new MimeBodyPart();
            DataSource source = new FileDataSource( neDownAttachments );
            neDownAttch.setDataHandler(new DataHandler(source));
            neDownAttch.setFileName( neDownAttachments.getName() );
            
            context.getMultipart().addBodyPart(avail2gregionImagePart);
            context.getMultipart().addBodyPart(avail3gregionImagePart);
            context.getMultipart().addBodyPart(neDownTrendImagePart);
            context.getMultipart().addBodyPart(neDownAttch);
            //context.getMultipart().addBodyPart(logoImagePart);
            context.getMultipart().addBodyPart(bodyPart);
         
            context.getMessage().setContent( context.getMultipart() );		
        
        } catch (IOException | MessagingException e1) {
			e1.printStackTrace();
		}
	}

	private Map<String,File> prepareRegionImageAttachements(List<RegionAvailability> dataNav){
		
		Map<String, File> maps = new HashMap<>();
		List<RegionAvailability> regionAvailabilityRaw = dataNav;

		//region 2g
		List<RegionAvailability> regionAvailability2g = regionAvailabilityRaw.stream().filter(nav->{
			return ( nav.getTechnology().equalsIgnoreCase("2G") );
		}).collect(Collectors.toList());
		
		//region 3g
		List<RegionAvailability> regionAvailability3g = regionAvailabilityRaw.stream().filter(nav->{
			return ( nav.getTechnology().equalsIgnoreCase("3G") );
		}).collect(Collectors.toList());		
		
		JFreeChart regionChart2G = regionChartBuilder.createChart( regionAvailability2g ,"2G REGION AVAILABILITY");
		JFreeChart regionChart3G = regionChartBuilder.createChart( regionAvailability3g , "3G REGION AVAILABILITY");
		
		
		//image file
		File region2GChartImage = new File( parentFolder,"2G_NAV_REGION.png" );
		File region3GChartImage = new File( parentFolder,"3G_NAV_REGION.png"  );
		
		maps.put("2G_REGION", region2GChartImage);
		maps.put("3G_REGION", region3GChartImage);
		
		//save region nav chart as image
		try {
			ChartUtilities.saveChartAsPNG( region2GChartImage, regionChart2G, 700, 400);
			ChartUtilities.saveChartAsPNG( region3GChartImage, regionChart3G, 700, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return maps;
	}
	
	private Map<String,Map<String,File>> prepareClusterImageAttachments( List<ClusterAvailability> dataNav ){
		
		Map<String,Map<String,File>> maps = new HashMap<>();
		
		//clusters
		List<ClusterAvailability> clustersAvailability = dataNav;
		Map<String , List<ClusterAvailability>> clusterNAVPerRegion = new HashMap<>();
		
		clustersAvailability.stream().forEachOrdered(nav->{
			String key = nav.getRegion();
			if( !clusterNAVPerRegion.containsKey( key ) ){
				clusterNAVPerRegion.put( key , new ArrayList<>());
			}
			clusterNAVPerRegion.get( key ).add(nav);
		});
		
		
		//save cluster nav chart as image
		clusterNAVPerRegion.entrySet().forEach(entry->{
			
			List<ClusterAvailability> availabilities2g = entry.getValue().stream().filter(nav->{
				return (nav.getTechnology().equalsIgnoreCase("2G"));
			}).collect(Collectors.toList());

			List<ClusterAvailability> availabilities3g = entry.getValue().stream().filter(nav->{
				return (nav.getTechnology().equalsIgnoreCase("3G"));
			}).collect(Collectors.toList());
			
			String _file2gName = "2G_".concat( entry.getKey() );
			String _file3gName = "3G_".concat( entry.getKey() );
			
			File file2g = new File( parentFolder, _file2gName.concat(".png") );
			File file3g = new File( parentFolder, _file3gName.concat(".png")  );
			
			Map<String,File> typeMap = new HashMap<>();
			typeMap.put("2G", file2g);
			typeMap.put("3G", file3g);
			
			maps.put(entry.getKey(), typeMap);
			
			JFreeChart chart2g = clusterChartBuilder.createChart( availabilities2g, _file2gName);
			JFreeChart chart3g = clusterChartBuilder.createChart( availabilities3g, _file3gName);
			
			try {
				ChartUtilities.saveChartAsPNG(file2g, chart2g, 700, 400);
				ChartUtilities.saveChartAsPNG(file3g, chart3g, 700, 400);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});		
		
		return maps;
	}
	
	private File prepareNEDownMovementImage(List<NEDownMovement> dataMovement){
		List<NEDownMovement> neDownMovements = dataMovement;
		JFreeChart chart = neDownMovementChartBuilder.createChart(neDownMovements, "NE DOWN TREND");
		
		File chartFile = new File(parentFolder,"NEDOWN_TREND.png");
		
		try {
			ChartUtilities.saveChartAsPNG(chartFile, chart, 700, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return chartFile;
	}
	
    private File prepareNEDownAttachment(List<AlarmDown> alarms){
    	
		File neDownListAttachment = new File(parentFolder, "CURRENT_NEDOWN_LIST.xlsx");
    	Workbook workbook = neDownExcelBuilder.createWorkbook(alarms);
    	FileOutputStream os = null;
		try {
			os = new FileOutputStream(neDownListAttachment);
			workbook.write( os );
		} catch (IOException e) {
			e.printStackTrace();
		}

    	
    	return neDownListAttachment;
    }
	
}
