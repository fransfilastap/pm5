package id.franspratama.geol.core.mail;

import java.io.IOException;

import javax.mail.MessagingException;

/**
 * 
 * An interface to generalizing implementation of composing message (email)
 * 
 * @author fransfilastap
 *
 */

public interface AppMessaging {
	/**
	 * 
	 * Send email/message with composing strategy as it's parameter
	 * 
	 * @param strategy
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void send(MessageComposingStrategy strategy) throws MessagingException,IOException;
}
