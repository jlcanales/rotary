package org.rotarysource.listener.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.rotarysource.listener.MapBaseListener;
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
public class LogListener extends MapBaseListener
{
	private static Logger log = LoggerFactory.getLogger(LogListener.class);

	/**
	 * processEvent method implements a routine that go over generated Map
	 * and log all its entries.
	 * 
	 * @param Map<String, String> Map received form MapBaseListener parent class
	 */
	@Override
	public void processEvent(Map<String, String> eventMap) {
		
		Set<Entry<String, String>> entries = eventMap.entrySet();
		
		Iterator<Entry<String, String>> index = entries.iterator();
		StringBuffer logText = new StringBuffer();
		logText.append("Received event: ");
		while(index.hasNext()){
			Entry<String, String> nextEntry = index.next();
    		logText.append( nextEntry.getKey())
    						.append("=")
    						.append(nextEntry.getValue())
    						.append(";");		
		}
       	log.info(logText.toString());
	}
}
