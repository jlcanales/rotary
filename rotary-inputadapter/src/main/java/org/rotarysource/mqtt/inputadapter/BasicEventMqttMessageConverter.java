/*
Copyright (c) 2014 the original author or authors.
 
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
package org.rotarysource.mqtt.inputadapter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.rotarysource.events.BasicEvent;
import org.rotarysource.mqtt.support.converter.MessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class BasicEventMqttMessageConverter implements MessageConverter {

	private static Logger log = LoggerFactory.getLogger(BasicEventMqttMessageConverter.class);
	
	private static final String DEFAULT_EVENT_CODE = "MQTT-001";
	/**
	* Create a new BasicEventMqttMessageConverter, for bean-style usage.
	*/	 
	 public BasicEventMqttMessageConverter(){
		 
	 }
	 
	/**
	 * Convert a BasicEvent object to a MQTT Message.
	 * 
	 * @param object the BasicEvent object to convert
	 * @return the MQTT Message
	 * @throws MqttException in case of conversion failure
	 * 
	 * @see org.rotarysource.mqtt.support.converter.MessageConverter#toMessage(java.lang.Object)
	 */
	@Override
	public MqttMessage toMessage(Object object) throws MqttException {

		Assert.notNull(object);
		Assert.isInstanceOf(BasicEvent.class, object, "The input object must be a BasicEvent object");
		
		HashMap<String, String> payload = ((BasicEvent) object).getCompData();
		
		Set<Entry<String, String>> entryValues = payload.entrySet();
		Iterator<Entry<String, String>> it = entryValues.iterator();
		StringBuffer stringPayload = new StringBuffer("{");
		
		while(it.hasNext()){
			Entry<String, String> entry = it.next();
			stringPayload.append("\"").append(entry.getKey()).append("\"")
						.append(":")
						.append("\"").append(entry.getValue()).append("\"");
			if(it.hasNext()){
				stringPayload.append(",");
			}
		}
		stringPayload.append("}");
		
		MqttMessage message = new MqttMessage(stringPayload.toString().getBytes());
		return message;
	}

	
	/** 
	 * MessageConverter fromMessage implementation.
	 * This method transform a Mqtt Message in a BasicEvent object following next definition
	 * <p>eventId will be autogenerated</p>
	 * <p>eventCode will be a fix value DEFAULT_EVENT_CODE</p>
	 * <p>eventType will be "INFO"</p>
	 * <p>eventTimeStamp will be current Date</p>
	 * <p>systemId will be the 'topic'</p>
	 * <p>compData will store all the key,value pairs in message payload</p>
	 * 
	 * @see org.rotarysource.mqtt.support.converter.MessageConverter#fromMessage(org.eclipse.paho.client.mqttv3.MqttMessage)
	 *
	 * @param MqttMessage Message in Mqtt format
	 * @return Object BasicEvent Object
	 */
	@Override
	public Object fromMessage(String topic, MqttMessage message) throws MqttException {

		HashMap<String, String> compdata = new HashMap<String, String>();
		BasicEvent 	       		basEvent = new BasicEvent();
		
		String messageString    = message.toString();
		messageString = messageString.substring(1,messageString.length() - 3); // Three characters }\r\n
		String messageEntries[] = messageString.split("\r\n");

		log.debug("Converting message from topic: {}", topic);
		
		// Key and Value extraction from each line
		for( int i = 0; i < messageEntries.length; i++){

			String messageEntry = new String(messageEntries[i]);
			if( messageEntry.charAt( messageEntry.length() - 1) == ','){
				messageEntry = messageEntry.substring(0, messageEntry.length() - 1);
			}
			
			String keyValue[] = messageEntry.split("\":");
			String key = keyValue[0].replaceAll("\"","");
			String value = keyValue[1].replaceAll("\"","");

			log.debug("Adding Entry: key->{}, value->{} ", key, value);
			
			compdata.put(key, value);
		}

		//Autogenerated EventId
		basEvent.setEventId(UUID.randomUUID().toString());
		//Autogenerated Event Code
		basEvent.setEventCode(DEFAULT_EVENT_CODE);
		//Autogenerated Event Type
		basEvent.setEventType("INFO");
		basEvent.setSystemId(topic);
		
		Calendar cal = Calendar.getInstance();
		basEvent.setEventTimestamp(cal.getTime());   
		basEvent.setCompData(compdata);
		
		return basEvent;
	}

}
