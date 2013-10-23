package org.rotarysource.listener.mail.plaintext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rotarysource.listener.mail.OAMailSender;
import org.rotarysource.listener.mail.domain.MailDataBean;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SimpleBaseMailSender implements OAMailSender {

	private static Log logger = LogFactory.getLog(SimpleBaseMailSender.class);

	/**
	 * Mail Sender needed to connect with SMTP server
	 */

	private MailSender mailSender;
	
	/**
	 * SimpleTemplate Message template for mail content 
	 */
    private SimpleMailMessage templateMessage;
	
	/**
	 * Create a new PlainMailSendSMTPImpl object
	 */
	public SimpleBaseMailSender(){
		
	}
	
	@Override
	public void sendMail(MailDataBean setupParams, String mailText,
			String[] attachments, String[] resources) throws IllegalArgumentException,
			MailException, RuntimeException {
		//Input data validation
		if( this.templateMessage == null &&
			(setupParams.getFromAddress().isEmpty()   ||
			 setupParams.getSubject().isEmpty()  ||
			 setupParams.getTo().isEmpty())){
			
			String error ="setup Params not correctly defined. Empty fields detected";
            logger.error(error);
			throw new IllegalArgumentException(error);
		}

        // Create a thread safe "copy" of the template message and customize it
		SimpleMailMessage msg = null;
		if(this.templateMessage != null){
			msg = new SimpleMailMessage(this.templateMessage);
		}
		else{
			msg = new SimpleMailMessage();
        	msg.setTo(setupParams.getTo());	
        	msg.setCc(setupParams.getCc());
        	msg.setSubject(setupParams.getSubject());
        	msg.setFrom(setupParams.getFromAddress());			
		}
        
        msg.setText(mailText);
        try{
            this.mailSender.send(msg);
        }
        catch(MailException ex) {
			String error ="Error sending email for Email Address." + msg.getTo() + "\n" + ex.getMessage();
			logger.error(error);
			throw ex;       
        }

	}



	
    
	/**
	* Set the JavaMailSender used to send emails. Mail sender must be setted up
	* @param aiMailSender Java Mail Sender.
	*/	

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	* Set the templateMessage used to built emails. Mail template must be setted up
	* @param aiMailSender Java Mail Sender.
	*/
	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
	}


}
