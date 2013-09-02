package org.rotarysource.jmx.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rotarysource.signals.AppContext;
import org.rotarysource.signals.shutdown.ShutdownEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Async;

/**
 * MBean responsible for the general system management. It controls the
 * main signals that can be sent to the system to manage it.
 *  
 * @author J.L. Canales
 *
 */
@ManagedResource(objectName="org.rotarysource.mbean.core:name=System", description="System global signals Management", log=true,
logFile="jmx.log", currencyTimeLimit=15, persistPolicy="OnUpdate", persistPeriod=200,
persistLocation="foo", persistName="bar")
public class SystemSignalsJMX {
	
	private static Log        logger = LogFactory.getLog(SystemSignalsJMX.class);

	/**
	 * System Shutdown
	 */
    @ManagedOperation(description="Shutdown system")
    @Async
	public void shutdown() {
    	
		logger.warn("Received JMX Shutdown Signal");

		ApplicationContext ctx = AppContext.getApplicationContext();

		ShutdownEvent event = new ShutdownEvent(this, "JMX Shutdown Signal");
		ctx.publishEvent( event);

	}
}
