package id.franspratama.geol.core.mail;

import java.util.Properties;

import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * 
 * Base Email Messaging Context
 * 
 * @author fransfilastap
 *
 */
public class DefaultMessagingContext implements MessagingContext{

	private String host;
	private String port;
	
	private Session session;
    private MimeMessage message;
    private MimeMultipart multipart;
	
	
	public DefaultMessagingContext(String host, String port){
		
		this.host = host;
		this.port = port;
		
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", this.host);
		props.setProperty("mail.smtp.port", this.port);

		session = Session.getDefaultInstance(props);
		message = new MimeMessage(session);
		
		multipart = new MimeMultipart();
	}



	@Override
	public final MimeMessage getMessage() {
		return message;
	}



	@Override
	public final Multipart getMultipart() {
		return multipart;
	}
	
	
}
