package org.rotarysource.listener.common;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements base Log Listener.
 * This listener is designed for Log all the event data in
 * a log file or console specified in log4j.xml
 * 
 * Its specially conceived for EPL queries testing logging 
 * results of the events that trigger the query.
 @author J. L. Canales
 * 
 */
public class LogListener implements UpdateListener
{
	private static Logger log = LoggerFactory.getLogger(LogListener.class);
	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if (newEvents == null)
        {
            return;
        }
       
        for (int i = 0; i < newEvents.length; i++)
        {
            if (log.isInfoEnabled())
            {
            	String propertyNames[] = newEvents[i].getEventType().getPropertyNames();
            	StringBuffer logText = new StringBuffer();
            	for (int j = 0; j<propertyNames.length; j++){
            		logText.append(propertyNames[j]).append("=").append( newEvents[i].get(propertyNames[j]));
            		logText.append("; ");
            	}
            	log.info(logText.toString());
            }
        }
	}
}
