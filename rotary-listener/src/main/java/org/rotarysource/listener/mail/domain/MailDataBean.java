package org.rotarysource.listener.mail.domain;

/**
 * This bean represents  needed data to setup a custormer 
 * proofing online Mail
 * @author J. L. Canales
 * 
 */
public class MailDataBean {

	/**
	 * To address to send customer email
	 */
	private String to;

	/**
	 * To address to send customer email
	 */
	private String[] cc;
		
	
	/**
	 * Mail From email Address
	 */
	private String	fromAddress;
	
	/**
	 * Mail From Name text
	 */
	private String	fromName;

	/**
	 * Mail Subject Template
	 */
	private String subjectTemplate;
	
	/**
	 * Mail Subject
	 */
	private String subject;

	/**
	 * Sending date
	 */
	private String sendDate;
	
	
	/**
	* Create a new MailSetupBean, for bean-style usage.
	*/
	public MailDataBean(){
		to 			= "";
		fromAddress 	= "";
		fromName	= "";
		subjectTemplate = "";
		subject 	= "";
		sendDate    = "";
	}

	/**
	* Return the To address for this item.
	* @return String To Address
	*/
	public String getTo() {
		return to;
	}
	
	/**
	* Set the  To address for this item.
	* @param aiTo To address
	*/
	public void setTo(String aiTo) {
		this.to = aiTo;
	}
	
	public String getFromAddress() {
		return fromAddress;
	}

	/**
	* Set the  From Mail address for this item.
	* @param aiFromAddress From Mail address
	*/
	public void setFromAddress(String aiFromAddress) {
		this.fromAddress = aiFromAddress;
	}

	public String getFromName() {
		return fromName;
	}

	/**
	* Return the send date for this item.
	* @return String Send date
	*/
	public String getSendDate() {
		return sendDate;
	}

	/**
	* Set the  From Name for this item.
	* @param aiFromName From Name
	*/
	public void setFromName(String aiFromName) {
		this.fromName = aiFromName;
	}

	/**
	 * Get Subject Template.
	 * @return template as String
	 */
	public String getSubjectTemplate() {
		return subjectTemplate;
	}

	/**
	* Set the Subject Template for this item.
	* @param aiSubjectTemplate From Name
	*/
	public void setSubjectTemplate(String aiSubjectTemplate) {
		this.subjectTemplate = aiSubjectTemplate;
	}

	/**
	 * Get Subject.
	 * @return Subject as String
	 */
	public String getSubject() {
		return subject;
	}

	/**
	* Set the  Subject for this item.
	* @param aiSubject From Name
	*/
	public void setSubject(String aiSubject) {
		this.subject = aiSubject;
	}

	/**
	* get the  CC list for this item.
	* @return CC List
	*/
	public String[] getCc() {
		return cc;
	}
	
	/**
	* Set the  CC list for this item.
	* @param aiCC CC List
	*/
	public void setCc(String[] aiCC) {
		this.cc = aiCC;
	}

	/**
	* Set the send date for this item.
	* @param aiSendDate Send date
	*/
	public void setSendDate(String aiSendDate) {
		this.sendDate = aiSendDate;
	}
	
	
	/**
	* Return a formatted string with this item information
	* @return String item information string
	*/
	public String toString(){
		StringBuffer result = new StringBuffer();
		result.append("To=").append( to).append("; ");
		result.append("fromAddress=").append( fromAddress).append("; ");
		result.append("fromName=").append( fromName).append("; ");
		result.append("subject=").append( subject).append("; ");
		
		return result.toString();
	}


}
