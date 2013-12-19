package org.rotarysource.jmx.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rotarysource.core.sep.SepEngine;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * MBean responsible for the general system management. It controls the
 * main signals that can be sent to the system to manage it.
 *  
 * @author J.L. Canales
 *
 */
@ManagedResource(objectName="org.rotarysource.mbean.core:name=SEP", description="Scheduled Events Processor Management", log=true,
logFile="jmx.log", currencyTimeLimit=15, persistPolicy="OnUpdate", persistPeriod=200,
persistLocation="foo", persistName="bar")
public class SepJMX {


	private static Log        logger = LogFactory.getLog(SepJMX.class);

	/**
	 * Reference to Managed SEP container
	 */
	private SepEngine sepEngine;
	
	
	/**
	 * Default constructor
	 * @param sepEngine SEP Engine managed by this Mbean
	 */
	public SepJMX(SepEngine aiSepEngine) {
		super();
		this.sepEngine = aiSepEngine;
	}	
	
	/**
	 * System Start
	 */
    @ManagedOperation(description="Start system")
	public void start() {
    	
		logger.info("Received JMX SEP Start Call");

		sepEngine.start();

	}
    
	/**
	 * System Start
	 */
    @ManagedOperation(description="Stop system")
	public void stop() {
    	
		logger.info("Received JMX SEP Stop Call");

		sepEngine.stop();

	}    
    
}
