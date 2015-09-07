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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.rotarysource.core.CepEngine;
import org.rotarysource.events.BasicEvent;
import org.rotarysource.mqtt.listener.MessageListener;
import org.rotarysource.mqtt.support.converter.MessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * @author Jose Luis Canales Gasco
 * @since 1.0
 * @see 
 */
@ManagedResource(objectName="org.rotarysource.mbean.inputadapters:name=MqttInputAdapter", description="Mqtt Input Adapter managed counters", log=true,
logFile="jmx.log", currencyTimeLimit=15, persistPolicy="OnUpdate", persistPeriod=200,
persistLocation="foo", persistName="bar")
public class MqttInputAdapter implements MessageListener {

    /** 
     * Slf4j logger instance
     */	
	private static Logger  log = LoggerFactory.getLogger(MqttInputAdapter.class);
    
    /** 
     * Reference to Cep Engine that events will be sent
     */ 
    private CepEngine cepEngine;
    /** 
     * MessageConverter to convert JMS Messages in ProdEvent objects
     */
    private MessageConverter    msgConverter;

    /** 
     * For JMX management. Number of processed Messages 
     */
    private int        			countMessages;
    /** 
     * For JMX management. Number of bad formed Messages 
     */
    private int        			countBadFormed;
    
 
    /** 
     * Create a new MqttInputAdapter, given a CepEngine
     * @param aiCepEngine Statement processor class to get the esper instance that process events.
     */
	public MqttInputAdapter(CepEngine aiCepEngine)
    {
        this.cepEngine      = aiCepEngine;
        this.countMessages  = 0;
        this.countBadFormed = 0;
    }    
    
    
	@Override
	public void onMessage(String topic, MqttMessage message) {
		// TODO Auto-generated method stub
		log.debug("Received message from topic : {}", topic);

    	BasicEvent 	basEvent = new BasicEvent();
    	
    	try {
    		if(msgConverter != null){
	    		basEvent = (BasicEvent) msgConverter.fromMessage(topic, message);
	
	            cepEngine.getCepEngine().getEPRuntime().sendEvent(basEvent);
    		}
            else{
            	log.error("There are not message converter defined. Received message will not be sent to CEP Engine");           	           	
            }
            countMessages++;
            
		} catch (MqttException e) {
			String 	  errorText  = "Msg Converter error. Bad Formed message";
			BasicEvent errorEvent = new BasicEvent();
            Date      date       = new Date();  
			log.error(errorText, e);
			
			errorEvent.setEventId(Integer.toString((int)date.getTime()));
			errorEvent.setEventCode("EX001");
			errorEvent.setEventTimestamp(date);
			errorEvent.setEventType("ERROR");
			errorEvent.setSystemId("cepInputAdapter");
            
            Map<String, String> compDataMap = new HashMap<String, String>();
            compDataMap.put("ERRTEXT", errorText);
            
            errorEvent.setCompData( (HashMap<String, String>) compDataMap);
			
	        cepEngine.getCepEngine().getEPRuntime().sendEvent(errorEvent);
	        countBadFormed++;
	        log.error(errorText);
	        log.debug("Bad formed message {}", message.toString());
		}
		

	}

	/**
	 * Return Menssage Converter Object set up for this item
	 * @return MessageConverter Message Converter
	 */
    public MessageConverter getMsgConverter() {
		return msgConverter;
	}

    /**
     * Set the Message Converter needed to convert from/to EventProd xml events
     * into ProdEvent objects
     * @param aiMsgConverter Message Converter for this item
     */
	public void setMsgConverter(MessageConverter aiMsgConverter) {
		this.msgConverter = aiMsgConverter;
	}
	
	/**
	 * Return the number of message processed by MqttInputAdapter instance
	 * @return int Message number
	 */
    @ManagedAttribute(description="Processed Messages counter",persistPeriod=300)
	public int getCountMessages() {
        return countMessages;
	}
    
    /**
     * Return the Message Number Failed while they was been processed 
     * by MqttInputAdapter
     * @return int Bad Formed Messages Counter
     */
    @ManagedAttribute(description="Bad Formed Messages counter",persistPeriod=300)
	public int getCountBadFormed() {
        return countBadFormed;
	}
    
    /**
     * Reset Statistical counters for This item
     */
    @ManagedOperation(description="Reset all Message counters")
	public void resetCounters(){
        this.countMessages  = 0;
        this.countBadFormed = 0;	
	}
	
	
}
