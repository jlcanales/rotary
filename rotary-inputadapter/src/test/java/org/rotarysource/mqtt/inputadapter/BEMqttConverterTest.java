package org.rotarysource.mqtt.inputadapter;

import java.util.Date;
import java.util.HashMap;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.*;
import org.rotarysource.events.BasicEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;

import junit.framework.Assert;
import junit.framework.TestCase;


@RunWith(BlockJUnit4ClassRunner.class)
public class BEMqttConverterTest extends TestCase{

	private static Logger log = LoggerFactory.getLogger(BEMqttConverterTest.class);
	
	@Test
	public void fromMessageNormalCaseTest(){

		log.info("==============================");
		log.info("fromMessage Normal Case Test ");
		log.info("==============================");
		
		
		//Give
		BasicEventMqttMessageConverter converter = new BasicEventMqttMessageConverter();
		StringBuffer sBuffer = new StringBuffer();
		
		sBuffer.append("\"date\"").append(":").append("\"18:26\"").append(",")
		.append("\"temp\"").append(":").append("\"4.9\"").append(",")
		.append("\"tempTL\"").append(":").append("\"-0.8\"").append(",")
		.append("\"tempTH\"").append(":").append("\"8.9\"").append(",")
		.append("\"intemp\"").append(":").append("\"19.5\"");
	/*
		"date":"18:26",
		"temp":"4.9",
		"tempTL":"-0.8",
		"tempTH":"8.9",
		"intemp":"19.5",
		"dew":"3.0",
		"dewpointTL":"-0.9",
		"dewpointTH":"6.4",
		"apptemp":"3.4",
		"apptempTL":"-2.9",
		"apptempTH":"8.1",
		"wchill":"4.9",
		"wchillTL":"-0.8",
		"heatindex":"4.9",
		"heatindexTH":"8.9",
		"humidex":"4.9",
		"wlatest":"0.0",
		"wspeed":"0.0",
		"wgust":"0.0",
		"wgustTM":"4.0",
		"bearing":"321",
		"avgbearing":"0",
		"press":"1011.62",
		"pressTL":"1002.21",
		"pressTH":"1011.62",
		"pressL":"983.61",
		"pressH":"1024.83",
		"rfall":"0.0",
		"rrate":"0.0",
		"rrateTM":"0.0",
		"hum":"87",
		"humTL":"82",
		"humTH":"99",
		"inhum":"57",
		"SensorContactLost":"0",
		"forecast":" Partly cloudy and cooler. ",
		"tempunit":"C",
		"windunit":"mph",
		"pressunit":"mb",
		"rainunit":"mm",
		"temptrend":"-0.7",
		"TtempTL":"08:01",
		"TtempTH":"14:01",
		"TdewpointTL":"07:33",
		"TdewpointTH":"11:58",
		"TapptempTL":"08:01",
		"TapptempTH":"14:01",
		"TwchillTL":"08:01",
		"TheatindexTH":"14:01",
		"TrrateTM":"00:00",
		"ThourlyrainTH":"00:00",
		"LastRainTipISO":"2014-01-19 02:40",
		"hourlyrainTH":"0.0",
		"ThumTL":"14:19",
		"ThumTH":"01:25",
		"TpressTL":"00:00",
		"TpressTH":"18:11",
		"presstrendval":"0.52",
		"Tbeaufort":"F1",
		"TwgustTM":"12:34",
		"windTM":"1.0",
		"bearingTM":"342",
		"timeUTC":"2014,1,20,18,26,19",
		"BearingRangeFrom10":"000",
		"BearingRangeTo10":"000",
		"UV":"0.0",
		"UVTH":"0.0",
		"SolarRad":"0",
		"SolarTM":"0",
		"CurrentSolarMax":"0",
		"domwinddir":"NW",
		"WindRoseData":[55.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,103.0,9.0,55.0,9.0],
		"windrun":"0.6",
		"version":"1.9.4",
		"build":"1086",
		"ver":"11"
*/
		
		MqttMessage message = new MqttMessage(sBuffer.toString().getBytes());
		BasicEvent basEvent = null;
		//When
		try {
			basEvent = (BasicEvent) converter.fromMessage("testTopic", message);
		} catch (MqttException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		//Then
		Assert.assertEquals("testTopic", basEvent.getSystemId());
		Assert.assertEquals("4.9", basEvent.getCompData().get("temp"));
	}
	
	@Test
	public void toMessageNormalCaseTest(){

		log.info("==============================");
		log.info("toMessage Normal Case Test ");
		log.info("==============================");
		
		
		//Given
		BasicEventMqttMessageConverter converter = new BasicEventMqttMessageConverter();
		HashMap<String,String> compData = new HashMap<String, String>();
		MqttMessage message = null;

		compData.put("tempunit", "C");
		compData.put("windunit","mph");
		compData.put("pressunit","mb");
		compData.put("rainunit","mm");
		compData.put("temptrend","-0.7");
		compData.put("TtempTL","08:01");
		compData.put("TtempTH","14:01");
		compData.put("TdewpointTL","07:33");
		compData.put("TdewpointTH","11:58");
		
		BasicEvent event = new BasicEvent();
		event.setCompData(compData);
		
		//When
		try {
			 message = converter.toMessage(event);
		} catch (MqttException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		
		//Then
		log.info(message.toString());
	}
}
