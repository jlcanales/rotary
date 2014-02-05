package org.rotarysource.listener.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.rotarysource.listener.MapBaseListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements base Trace Listener.
 * This listener is designed to log traces with a trace scope
 * (INFO, DEBUG, ERROR, etc.) and all the event data in
 * a log file or console specified in log4j.xml
 * 
 * Its specially conceived for EPL queries testing logging 
 * results of the events that trigger the query.
 @author J. L. Canales
 * 
 */
public class TraceListener extends MapBaseListener
{
	private static Logger log = LoggerFactory.getLogger(TraceListener.class);

	/*
	 * Trace scopes
	 */
	/**
	 * The TRACE Level designates finer-grained informational events than the DEBUG
	 */
	public static final int TRACE = 0;
	/**
	The DEBUG Level designates fine-grained informational events that are most useful to debug an application.
	*/
	public static final int DEBUG = 1;
	/**
    The ERROR level designates error events that might still allow the application to continue running.
	*/
	public static final int ERROR = 2;
	/**
	 * The INFO level designates informational messages that highlight the progress of the application at coarse-grained level.
	 */
	public static final int	INFO = 3;
	/**
	 * The WARN level designates possible error events that will presumably lead the application to abort.
	 */
	public static final int WARN = 4;
	
	
	private String traceText = "";
	
	private int traceScope = 3;
	
	
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
		logText.append(traceText).append(" ");
		while(index.hasNext()){
			Entry<String, String> nextEntry = index.next();
    		logText.append( nextEntry.getKey())
    						.append("=")
    						.append(nextEntry.getValue())
    						.append(";");		
		}
		
		switch(traceScope){
			case 0:
				log.trace(logText.toString());
				break;
			case 1:
				log.debug(logText.toString());
				break;
			case 2:
				log.error(logText.toString());
				break;
			case 3:
				log.info(logText.toString());
				break;
			case 4:
				log.warn(logText.toString());
				break;
			
			default:
				log.info(logText.toString());		
		}
	}

	public String getTraceText() {
		return traceText;
	}


	public void setTraceText(String aiTraceText) {
		this.traceText = aiTraceText;
	}


	public int getTraceScope() {
		return traceScope;
	}


	public void setTraceScope(int aiTraceScope) {
		this.traceScope = aiTraceScope;
	}
	
}
