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

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * A MessageListener object is used to receive asynchronously delivered messages.
 * Each session must insure that it passes messages serially to the listener. 
 * This means that a listener assigned to one or more consumers of the same session 
 * can assume that the onMessage method is not called with the next message until the session 
 * has completed the last call.
 *
 * @author Jose Luis Canales Gasco
 * @since 1.0
 * @see org.eclipse.paho.client.mqttv3.MqttMessage
 */
public interface MessageListener {

	/**
	 * Passes a message to the listener.
	 * @param message - the message passed to the listener
	 */
	public void onMessage(MqttMessage message);
}
