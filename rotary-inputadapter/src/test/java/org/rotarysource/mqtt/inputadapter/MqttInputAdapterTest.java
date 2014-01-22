package org.rotarysource.mqtt.inputadapter;

import javax.annotation.Resource;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rotarysource.mqtt.listener.SimpleMqttMessageListenerContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import junit.framework.TestCase;



@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = { "classpath:MqttInputAdapter-TestConfig.xml" })
public class MqttInputAdapterTest extends TestCase {

	@Resource
	SimpleMqttMessageListenerContainer listenerContainer;
	


	
	/**
	 * Test AppContext Loading to assure all needed beans
	 * are properly configured.
	 */
	@Test
	public void loadAppContextTest(){
		Assert.assertTrue(true);
	}
	
	/**
	 * Test AppContext Loading to assure all needed beans
	 * are properly configured.
	 * @throws MqttException 
	 * @throws InterruptedException 
	 */
	@Test
	public void ListenTest() throws MqttException, InterruptedException{
		listenerContainer.doInitialize();
		  Thread.currentThread().sleep(5000);//sleep for 1000 ms
	}
}
