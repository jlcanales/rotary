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

package org.rotarysource.mqtt.listener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.Assert;

/**
 * Message listener container that uses the plain Eclipse Paho Mqtt client API's
 * <code>MessageConsumer.setMessageListener()</code> method to
 * create concurrent MessageConsumers for the specified listeners.
 *
 * <p>This is the simplest form of a message listener container.
 * It creates a fixed number of Mqtt Sessions to invoke the listener,
 * not allowing for dynamic adaptation to runtime demands. Its main
 * advantage is its low level of complexity.
 *
 * @author Jose Luis Canales Gasco
 * @since 1.0
 * @see 
 */
public class SimpleMqttMessageListenerContainer implements MqttCallback{

	private static Logger  log = LoggerFactory.getLogger(SimpleMqttMessageListenerContainer.class);
	
	private String brokerUrl;
	
	private String destination;

	private int concurrentConsumers = 1;

	private TaskExecutor taskExecutor;
	
	private MessageListener messageListener;

	private Set<MqttClient> consumers;

	private final Object consumersMonitor = new Object();
	

	
	
	
	/**
	 * Simple Listener dont send any message to Mqtt server. This method is not
	 * implemented.
	 * @param arg0
	 */
	public void deliveryComplete(IMqttDeliveryToken arg0) {
	}
	
	/**
	 * Creates the specified number of concurrent consumers.
	 */
	public void doInitialize() throws MqttException {
		if(consumers != null){
			doShutdown();
			consumers = null;
		}
		initializeConsumers();
	}

	/**
	 * Re-initializes this container's Mqtt message consumers,
	 * if not initialized already.
	 */
	protected void doStart() throws MqttException {
		initializeConsumers();
	}
	/**
	 * This method is called when the connection to the server is lost.
	 * 
	 * @param cause the reason behind the loss of connection.
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback
	 */
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		cause.printStackTrace();
	}
	
	/**
	 * This method is called when a message arrives from the server.
     * This method is invoked synchronously by the MQTT client. An acknowledgment is not sent back to the server until this method returns cleanly.
	 * It process the incoming message to be sended to the MqttMessageListener
	 * set up for this object.
	 * @param topic name of the topic on the message was published to
	 * @param message Mqtt message received.
	 * @throws Exception  if a terminal error has occurred, and the client should be shut down.
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		log.debug("Received menssage form {}",topic);		
		log.debug("Payload: {}", message.toString());
		
		if(messageListener != null){
			messageListener.onMessage(topic, message);
		}
		
	}
	
	/**
	 * Create a MessageConsumer for the given Mqtt configuration,
	 * registering a MessageListener for the specified listener.
	 * @return the MqttClient
	 * @throws MqttException if thrown by Paho methods
	 */
	protected MqttClient createListenerConsumer() throws MqttException {
		if (brokerUrl != null){
			String consumerName;
			consumerName = Long.toString(UUID.randomUUID().getLeastSignificantBits());
			
			return new MqttClient(brokerUrl, consumerName);

		}
		else{
			return null;
		}

	}
	
	/**
	 * Destroy the registered Mqtt Sessions and associated MessageConsumers.
	 */
	protected void doShutdown() throws MqttException {
		
		log.debug("Closing Mqtt consumers");
		
		Iterator<MqttClient> consumerIt = consumers.iterator();
		
		while(consumerIt.hasNext()){
			MqttClient consumer = consumerIt.next();
			consumer.disconnect();
		}
	}
	
	/**
	 * Initialize the MqttConsumers for this container.
	 * @throws MqttException in case of setup failure
	 */
	protected void initializeConsumers() throws MqttException {
		log.debug("Initializing {} Mqtt Consumers", concurrentConsumers);
		
		synchronized (this.consumersMonitor) {
			if (this.consumers == null) {
				consumers = new HashSet<MqttClient>();
			
				for(int i = 0; i < concurrentConsumers; i++){
					MqttClient consumer = createListenerConsumer();
					consumer.setCallback(this);
					consumers.add(consumer);
				}
			}			
		}
		
		Iterator<MqttClient> consumerIt = consumers.iterator();
		
		while(consumerIt.hasNext()){
			MqttClient consumer = consumerIt.next();			
			
			log.debug("Connecting Consumer - {} ", consumer.getClientId());
			
			if(consumer.isConnected()) consumer.disconnect();
			consumer.connect();
			consumer.subscribe(destination);
		}
	}

	
	/**
	 * Specify the number of concurrent consumers to create. Default is 1.
	 * <p>Raising the number of concurrent consumers is recommendable in order
	 * to scale the consumption of messages coming in from a queue. However,
	 * note that any ordering guarantees are lost once multiple consumers are
	 * registered. In general, stick with 1 consumer for low-volume queues.
	 * <p><b>Do not raise the number of concurrent consumers for a topic.</b>
	 * This would lead to concurrent consumption of the same message,
	 * which is hardly ever desirable.
	 */
	public void setConcurrentConsumers(int concurrentConsumers) {
		Assert.isTrue(concurrentConsumers > 0, "'concurrentConsumers' value must be at least 1 (one)");
		this.concurrentConsumers = concurrentConsumers;
	}
	
	/**
	 * Set the Spring TaskExecutor to use for executing the listener once
	 * a message has been received by the provider.
	 * <p>Default is none, that is, to run in the Mqtt provider's own receive thread,
	 * blocking the provider's receive endpoint while executing the listener.
	 * <p>Specify a TaskExecutor for executing the listener in a different thread,
	 * rather than blocking the Mqtt provider, usually integrating with an existing
	 * thread pool. This allows to keep the number of concurrent consumers low (1)
	 * while still processing messages concurrently (decoupled from receiving!).
	 * <p><b>NOTE: Specifying a TaskExecutor for listener execution affects
	 * acknowledgement semantics.</b> Messages will then always get acknowledged
	 * before listener execution, with the underlying Session immediately reused
	 * for receiving the next message. Using this in combination with a transacted
	 * session or with client acknowledgement will lead to unspecified results!
	 * @see http://www.infoq.com/articles/practical-mqtt-with-paho
	 */
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * Gets the Mqtt broker Url configured for this object
	 * @return brokerUrl
	 */
	public String getBrokerUrl() {
		return brokerUrl;
	}

	/**
	 * Sets the broker Url where all consumers will be connected
	 * @param brokerUrl Mqtt access Url
	 */
	public void setBrokerUrl(String aiBrokerUrl) {
		this.brokerUrl = aiBrokerUrl;
	}

	/**
	 * Gets the topic name configured for this object
	 * @return destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Sets the topic name where all consumers will listen
	 * @param aiDestination Mqtt topic to listen
	 */
	public void setDestination(String aiDestination) {
		this.destination = aiDestination;
	}
	
	/**
	 * @return the messageListener
	 */
	public MessageListener getMessageListener() {
		return messageListener;
	}

	/**
	 * @param messageListener the messageListener to set
	 */
	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}
	
	

}
