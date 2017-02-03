package org.rotarysource.listener.mail.html;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rotarysource.listener.mail.OAMailSender;
import org.rotarysource.listener.mail.domain.MailDataBean;
import org.rotarysource.signals.AppContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class HTMLBaseMailSender implements OAMailSender {

	private static Log logger = LogFactory.getLog(HTMLBaseMailSender.class);

	/**
	 * Mail Sender needed to connect with SMTP server
	 */
	private JavaMailSender   mailSender;
	
	/**
	 * Mail text charset
	 */
	private Charset			 charset;
	
	/**
	 * Create a new ProofingHTMLMailSendSMTPImpl object
	 */
	public HTMLBaseMailSender(){
		charset = Charset.forName("UTF-8");
		
	}
	
	@Override
	public void sendMail(MailDataBean setupParams, String mailText,
			String[] attachments, String[] resources) throws  RuntimeException {
		//Input data validation
		if( setupParams.getFromAddress().isEmpty()   ||
			setupParams.getFromName().isEmpty() ||
			setupParams.getSubject().isEmpty()  ||
			setupParams.getTo().isEmpty()){
			
			String error ="setup Params not correctly defined. Empty fields detected";
            logger.error(error);
			throw new IllegalArgumentException(error);
		}

		// create a mime message using the mail sender implementation
		MimeMessage message = mailSender.createMimeMessage();
		// create the message using the specified template
		MimeMessageHelper helper;
        try {
        	helper = new MimeMessageHelper(message, true, this.charset.name());
			helper.setFrom(setupParams.getFromAddress(), setupParams.getFromName());
	        helper.addTo(setupParams.getTo());
			
			message.setSubject(setupParams.getSubject()); 
			helper.setSubject(setupParams.getSubject());
			
			helper.setText(mailText,true);
			
			if (logger.isDebugEnabled())
				logger.debug("Envio de email " + mailText);
			
			if (attachments!=null) {
				for (String attach: attachments ){
					if (attach != null) {
						addAttachments( helper, attach);
					}
				}
			}

			if (resources != null) {
				for (String resource: resources ){
					if (resource != null) {
						addInlineResource( helper, resource);
					}
				}
			}
			
			mailSender.send(helper.getMimeMessage());


        } catch (UnsupportedEncodingException e) {
 			String error="Unable to setup the mime message helper (setFrom, addTo) for: ";
 			error += setupParams.getTo() + " Email Address." + "\n";
 			if( logger.isDebugEnabled()) error += e.getMessage();
 			
 			logger.error(error);
 			throw new RuntimeException(error);
        } catch (MessagingException e) {
			String error="Unable to setup the message template for: ";
			error += setupParams.getTo() + " Email Address." + "\n";
			if( logger.isDebugEnabled()) error += e.getMessage();
			
			logger.error(error);
			throw new RuntimeException(error);
		}catch(MailException e){
			String error ="Error sending email for "  +
					setupParams.getTo() + " Email Address." + "\n" + e.getMessage();
            logger.error(error);
			throw e;
		}
	}
	
    /**
     * Add Attachments configured in this Item to the message.
     * If there isnt any configured attachment or a file access expeption
     * happens, no attachment will be added
     * @param helper 
     *		MimeMessageHelper to manage the mail message.
     * @param resource 
     *		File name to attach
     */
    private void addAttachments( MimeMessageHelper helper, String attachment){
    	 	
        	ApplicationContext  ctx 	   = AppContext.getApplicationContext(); 
            Resource 			fileAttach = ctx.getResource(attachment);

            try {
    			FileSystemResource file = new FileSystemResource(fileAttach.getFile());
    			helper.addAttachment(file.getFilename(), file);
    		} catch (Exception e1) {
    			String error="Unable to attach file to the Email: ";
    			error += e1.getMessage();
    			logger.warn(error);
    		} 
    }


    /**
     * Add inline resource configured in this Item to the message.
     * If there isnt any configured inline element or a file access exception
     * happens, no attachment will be added
     * @param helper 
     *		MimeMessageHelper to manage the mail message.
     * @param resource 
     *		File name to attach
     */
    private void addInlineResource( MimeMessageHelper helper, String resource){
    	 	
        	ApplicationContext  ctx 	   = AppContext.getApplicationContext(); 
            Resource 			fileAttach = ctx.getResource(resource);

            try {
    			FileSystemResource file = new FileSystemResource(fileAttach.getFile());
    			helper.addInline(file.getFilename(), file);
    		} catch (Exception e1) {
    			String error="Unable to embed file to the Email: ";
    			error += e1.getMessage();
    			logger.warn(error);
    		} 
    }
    
	/**
	* Return the JavaMailSender used to send emails
	* @return JavaMailSender Mail Sender.
	*/		
	public JavaMailSender getMailSender() {
		return mailSender;
	}

	/**
	* Set the JavaMailSender used to send emails. Mail sender must be setted up
	* @param aiMailSender Java Mail Sender.
	*/
	public void setMailSender(JavaMailSender aiMailSender) {
		this.mailSender = aiMailSender;
	}

}
