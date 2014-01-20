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

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.rotarysource.mqtt.listener.MessageListener;
import org.rotarysource.mqtt.listener.SimpleMqttMessageListenerContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jose Luis Canales Gasco
 * @since 1.0
 * @see 
 */
public class MqttInputAdapter implements MessageListener {

	private static Logger  log = LoggerFactory.getLogger(MqttInputAdapter.class);
	
	@Override
	public void onMessage(String topic, MqttMessage message) {
		// TODO Auto-generated method stub
		log.debug("Received message from topic : {}", topic);

	}

}
