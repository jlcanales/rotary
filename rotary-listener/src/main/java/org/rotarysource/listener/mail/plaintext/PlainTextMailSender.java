package org.rotarysource.listener.mail.plaintext;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.rotarysource.listener.mail.domain.MailDataBean;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;


public class PlainTextMailSender extends SimpleBaseMailSender{

	//private static Logger  log = LoggerFactory.getLogger(PlainTextMailSender.class);
	
	/**
	 * Mail text String array index
	 */	
	private static final int MAIL_TEXT = 0;

	/**
	 * Mail Headered text String array index
	 */		
	private static final int MAIL_HEADERED_TEXT = 1;

	/**
	 * Store a template text.
	 */
	private String templateMailText;	
		
	
	
	public String sendMail(MailDataBean setupParams, Map<String, Object> composeParams,
			String[] attachments, String[] resources) throws IllegalArgumentException,
			MailException, RuntimeException {
		
			// Composition
			String[] mailTexts = composeMail(composeParams);

			// Send mail
			super.sendMail(setupParams, mailTexts[MAIL_TEXT], attachments, resources);
	
			return mailTexts[MAIL_TEXT];
		
	}
	
	
	
	private String[] composeMail(Map<String, Object> mailVariablesMap)
			throws IllegalArgumentException, MailPreparationException {
		
		// Copy template to be thread Safe
		String mailText = templateMailText;
		
        Set<Entry<String, Object>> set = mailVariablesMap.entrySet();
        // Get an iterator
        Iterator<Entry<String, Object>> it = set.iterator();
        while(it.hasNext()) {
        	Entry<String, Object> mapEntry = it.next();
        	// Key substitution looking for ${mapEntry.getKey()} string 
        	
        	mailText = mailText.replace("@{"+ mapEntry.getKey() +"}" , mapEntry.getValue().toString());
        }
		
        String[] result;
        result = new String[2];
        
        result[ MAIL_TEXT] 			= mailText;
        result[ MAIL_HEADERED_TEXT] = mailText;
		return result;
	}
	
	/**
	 * Sets Template string for this composer. Variables to be substitude in this template, 
	 * must be declared in the string template as ${variableName}
	 * @param templateMailText String tempate
	 */
	public void setTemplateMailText(String templateMailText) {
		this.templateMailText = templateMailText;
	}

}
