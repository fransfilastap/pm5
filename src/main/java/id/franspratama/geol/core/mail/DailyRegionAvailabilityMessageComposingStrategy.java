package id.franspratama.geol.core.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

import org.apache.poi.ss.usermodel.Workbook;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import id.franspratama.geol.core.pojo.DailyRegionAvailability;
import id.franspratama.geol.util.AppChart;
import id.franspratama.geol.util.Email;
import id.franspratama.geol.view.AppExcelBuilder;

/**
 * 
 * Message Composing strategy for daily regional network availability email
 * 
 * 
 * @author fransfilastap
 *
 */

@Component
public class DailyRegionAvailabilityMessageComposingStrategy implements MessageComposingStrategy{
	
	/**
	 *  Region Chart builder
	 */
	@Autowired
	private AppChart<DailyRegionAvailability> regionChartBuilder;
	
	
	/**
	 *  Excel builder, used for attachment purpose
	 */
	@Autowired
	private AppExcelBuilder<DailyRegionAvailability> excelBuilder;
	
	/**
	 * used for parsing email template
	 * 
	 */
	@Autowired
	TemplateEngine templateEngine;
	
	/**
	 * 
	 * 
	 * 
	 */
	private File parentFolder = new File( new File(".").getAbsolutePath() );
	
	@Override
	public void composeMessage(Map<Object, Object> content, MessagingContext context) {
		
		
		Context thymeleafContext = new Context();
		
		String region2gContentId = Email.getContentId();
		String region3gContentId = Email.getContentId();
		
		thymeleafContext.setVariable("region2gImageResource", region2gContentId);
		thymeleafContext.setVariable("region3gImageResource", region3gContentId);
		thymeleafContext.setVariable("emailDate", new Date());
		
		@SuppressWarnings("unchecked")
		List<DailyRegionAvailability> list = (List<DailyRegionAvailability>) content.get("nav");
		List<DailyRegionAvailability> dataNav = list ;
		
		
		Map<String, File> imageAttachments = prepareRegionImageAttachements(dataNav);
		
        
        try {
        	MimeBodyPart avail2gregionImagePart = new MimeBodyPart();
        	avail2gregionImagePart.attachFile( imageAttachments.get("2G_REGION") );
            avail2gregionImagePart.setContentID("<" + region2gContentId + ">");
            avail2gregionImagePart.setDisposition(MimeBodyPart.INLINE);
            
            MimeBodyPart avail3gregionImagePart = new MimeBodyPart();
            avail3gregionImagePart.attachFile( imageAttachments.get("3G_REGION") );
            avail3gregionImagePart.setContentID("<" + region3gContentId + ">");
            avail3gregionImagePart.setDisposition(MimeBodyPart.INLINE);

            
            //prepare availabilty attachments file
    		//parent folder
    		File parentFolder = new File(new File(".").getAbsolutePath());
    		
            File navAttachment = new File(parentFolder,"DAILY_NAV_ALL_REGION.xlsx");
            Workbook workbook = excelBuilder.createWorkbook( dataNav);
            FileOutputStream outputStream = new FileOutputStream( navAttachment );
            workbook.write(outputStream);
            workbook.close();
            
            MimeBodyPart attachmentExcel = new MimeBodyPart();
            attachmentExcel.setFileName(navAttachment.getName());
            attachmentExcel.attachFile(navAttachment);
            attachmentExcel.setDisposition(MimeBodyPart.ATTACHMENT);
           
    		MimeBodyPart bodyPart = new MimeBodyPart();
    		final String htmlContent = templateEngine.process("mail/dailynav", thymeleafContext);
    		bodyPart.setContent(htmlContent, "text/html");
    		
    		context.getMultipart().addBodyPart(attachmentExcel);
    		context.getMultipart().addBodyPart(avail2gregionImagePart);
    		context.getMultipart().addBodyPart(avail3gregionImagePart);
    		context.getMultipart().addBodyPart(bodyPart);
    		
    		context.getMessage().setContent( context.getMultipart() );
    		
        } catch (IOException | MessagingException e) {
			e.printStackTrace();
		}

	}

	private Map<String,File> prepareRegionImageAttachements(List<DailyRegionAvailability> data){
		
		Map<String, File> maps = new HashMap<>();
		List<DailyRegionAvailability> regionAvailabilityRaw = data;

		//region 2g
		List<DailyRegionAvailability> regionAvailability2g = regionAvailabilityRaw.stream().filter(nav->{
			return ( nav.getTechnology().equalsIgnoreCase("2G") );
		}).collect(Collectors.toList());
		
		//region 3g
		List<DailyRegionAvailability> regionAvailability3g = regionAvailabilityRaw.stream().filter(nav->{
			return ( nav.getTechnology().equalsIgnoreCase("3G") );
		}).collect(Collectors.toList());		
		
		
		JFreeChart regionChart2G = regionChartBuilder.createChart( regionAvailability2g ,"2G REGION AVAILABILITY MONTHLY");
		JFreeChart regionChart3G = regionChartBuilder.createChart( regionAvailability3g , "3G REGION AVAILABILITY MONTHLY");
		
		
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

	
}
