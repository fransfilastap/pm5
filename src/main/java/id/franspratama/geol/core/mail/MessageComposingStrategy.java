package id.franspratama.geol.core.mail;

import java.util.Map;



/**
 * 
 * An interface for how to compose email
 * 
 * @author franspotter
 *
 */
public interface MessageComposingStrategy {
	
	/**
	 * Pass your content here. you may put image, html/text, file like excel file or word file
	 * and put it in map
	 * 
	 * @param content
	 */
	public void composeMessage(Map<Object,Object> content,MessagingContext context);
}
