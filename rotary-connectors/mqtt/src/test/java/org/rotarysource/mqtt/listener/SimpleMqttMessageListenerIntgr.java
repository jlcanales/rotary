package org.rotarysource.mqtt.listener;

import junit.framework.TestCase;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;



@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = { "classpath:SimpleMqttMessageListener-TestConfig.xml" })
public class SimpleMqttMessageListenerIntgr extends TestCase {

	/**
	 * Cep Engine to support listener execution
	 */
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
	 */
	@Test
	public void ConnectionTest() throws MqttException{
		listenerContainer.doInitialize();
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
