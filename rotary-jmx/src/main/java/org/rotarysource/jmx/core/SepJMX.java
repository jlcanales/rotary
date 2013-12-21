package org.rotarysource.jmx.core;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;
import org.rotarysource.core.sep.SepEngine;
import org.rotarysource.core.sep.job.JobDescription;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.util.Assert;

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
	 * System Stop
	 */
    @ManagedOperation(description="Stop system")
	public void stop() {
    	
		logger.info("Received JMX SEP Stop Call");

		sepEngine.stop();

	}  

    @ManagedOperation(description="schedule a simple event")
    public void scheduleJob(String jobName, String jobGroup, String taskType, String dateTimeString){

		Assert.notNull(jobName, "Call must contain a 'jobName' entry to schedule event in SEP!");
		Assert.notNull(jobGroup, "Call must contain a 'jobGroup' entry to schedule event in SEP!");
		Assert.notNull(taskType, "Call must contain a 'taskType' entry to schedule event in SEP!");
		Assert.notNull(dateTimeString, "Call must contain a 'dateTimeString' entry to schedule event in SEP!");
    	
    	
       	logger.info("Scheduling a new event in SEP using JMX interface");

       	
		JobDescription jobDesk = new JobDescription(jobName, jobGroup,taskType);
 
		Calendar scheduleCal = DatatypeConverter.parseDateTime(dateTimeString);
		
		jobDesk.setFireDate(scheduleCal.getTime());
		
		
		//Attach Task Params

		
		//Scheduled job
		try {			
			sepEngine.scheduleJob(jobDesk);
			
		} catch (SchedulerException e) {
			StringBuffer errorText  = new StringBuffer( "Event could not be scheduled. ");

			logger.error(errorText.toString(), e);
		}		
  	}
    
}
