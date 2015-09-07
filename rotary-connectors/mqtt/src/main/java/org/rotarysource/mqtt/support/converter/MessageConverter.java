/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.rotarysource.mqtt.support.converter;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


/**
 * Strategy interface that specifies a converter between Java objects and MQTT messages.
 *
 * @author J. L. Canales
 * @since 1.1..1
 */
public interface MessageConverter {

	/**
	 * Convert a Java object to a MQTT Message using the supplied session
	 * to create the message object.
	 * @param object the object to convert
	 * @return the MQTT Message
	 * @throws MqttException in case of conversion failure
	 */
	MqttMessage toMessage(Object object) throws MqttException;

	/**
	 * Convert from a MQTT Message to a Java object.
	 * @param topic the topic where the message was captured
	 * @param message the message to convert
	 * @return the converted Java object
	 * @throws MqttException in case of conversion failure
	 */
	Object fromMessage(String topic, MqttMessage message) throws MqttException;

}
