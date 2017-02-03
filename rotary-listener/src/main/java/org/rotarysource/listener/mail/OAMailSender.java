package org.rotarysource.listener.mail;

import org.rotarysource.listener.mail.domain.MailDataBean;
import org.springframework.mail.MailException;

/**
 * Define interface for an object that have capabilities to send email
 * @author J.L.Canales
 *
 */
public interface OAMailSender{
	
	/**
	 * Method to send an Email with attachements
	 * @param setupParams Pojo with all needed data to send an email.
	 * @param mailText  Test to be sended.
	 * @param attachments  Attachment list with all the path to files to be attached.
	 * @param resources	   Resource list with all paths to files to be embedded.
	 * 
	 * @throws IllegalArgumentException
	 * @throws MailException Exception throws when a mail send fails. 
	 * @throws RuntimeException
	 */
	void sendMail(MailDataBean setupParams, String mailText, String attachments[],
					     String resources[] )throws RuntimeException;
	
}
