package id.franspratama.geol.core.mail;

import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;

public interface MessagingContext {
	public MimeMessage getMessage();
	public Multipart getMultipart();
}
