/*
Copyright (c) 2013 J. L. Canales Gasco
 
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA}]
*/


package org.rotarysource.events;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represent a multi-purpose event.
 * Basic Event has simple event information as header and a variable
 * Map with the body event information.
 * 
 * It is JAXB annotated in such way it can be XML marshall and unmarshall allowing
 * bind it to any transport (such JMS).
 * @author J. L. Canales
 * 
 */

@XmlRootElement
public class BasicEvent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2406681472774601151L;

	/**
	 * Event Identification
	 */
	private String eventId;
	
	/**
	 * Code descriptor for event
	 */	
	private String eventCode;
	
	/**
	 * Event occurrence timestamp
	 */	
	private Date   eventTimestamp;
	
	/**
	 * Event Type. CRITICAL, FATAL, ERROR, DEBUG, INFO
	 */		
	private String eventType;
	
	/**
	 * Sender System Id.
	 */		
	private String systemId;
	
	/**
	 * Additional information Key-Value map
	 */	
	private HashMap<String, String> compData;

	/**
	* Create a new CsBean, for bean-style usage.
	*/
	public BasicEvent() {

	}
	
	/**
	* Return the Event Id for this item.
	* @return String Event Identificator
	*/
	@XmlElement(required=true)
	public String getEventId() {
		return eventId;
	}

	/**
	* Return the Event Code for this item.
	* @return String Event Code Identificator
	*/
	@XmlElement(required=true)
	public String getEventCode() {
		return eventCode;
	}

	/**
	* Return the Event occurrence timestamp for this item.
	* @return Date Event occurrence timestamp
	*/
	@XmlElement(required=true)
	public Date getEventTimestamp() {
		return eventTimestamp;
	}
	
	/**
	* Return the Event Type for this item.
	* Available values are  CRITICAL, FATAL, ERROR, DEBUG, INFO
	* @return String Event Type
	*/
	@XmlElement(required=true)
	public String getEventType() {
		return eventType;
	}

	/**
	* Return the Sender System Id. for this item.
	* @return String Sender System Id
	*/
	public String getSystemId() {
		return systemId;
	}

	/**
	* Return the Additional information Key-Value map for this item.
	* @return HashMap<String, String> Event Additional information Map
	*/
	public HashMap<String, String> getCompData() {
		return compData;
	}

	/**
	* Set the Event Id for this item.
	* @param aiEventId Event Identificator
	*/
	public void setEventId(String aiEventId) {
		eventId = aiEventId;
	}

	/**
	* Set the Event Code for this item.
	* @param aiEventCode Event Code Identificator
	*/
	public void setEventCode(String aiEventCode) {
		eventCode = aiEventCode;
	}

	/**
	* Set the Event occurrence timestamp for this item.
	* @param aiEventTimestamp Event occurrence timestamp
	*/
	public void setEventTimestamp(Date aiEventTimestamp) {
		eventTimestamp = aiEventTimestamp;
	}

	/**
	* Set the Event Type for this item.
	* Available values are  CRITICAL, FATAL, ERROR, DEBUG, INFO
	* @param aiEventType Event Type
	*/
	public void setEventType(String aiEventType) {
		eventType = aiEventType;
	}

	/**
	* Set the Sender System Id. for this item.
	* @param aiSystemId Sender System Id
	*/
	public void setSystemId(String aiSystemId) {
		systemId = aiSystemId;
	}

	/**
	* Set the Additional information Key-Value map for this item.
	* @param hashTypes information Map
	*/
	public void setCompData(HashMap<String, String> hashTypes) {
		compData = hashTypes;
	}

	@Override
	public String toString() {
		return "BasicEvent [eventId=" + eventId + 
				", eventCode=" + eventCode + 
				", eventTimestamp=" + eventTimestamp + 
				", eventType=" + eventType + 
				", systemId=" + systemId + 
				", compData=" + compData + "]";
	}

}
