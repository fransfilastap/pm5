package id.franspratama.geol.core.alert;

import java.io.IOException;

import javax.mail.MessagingException;

public interface Alert {
	public void send() throws MessagingException,IOException;
}
