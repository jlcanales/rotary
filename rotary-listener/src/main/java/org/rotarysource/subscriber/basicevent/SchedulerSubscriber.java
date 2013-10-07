package org.rotarysource.subscriber.basicevent;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.bind.DatatypeConverter;

import org.quartz.SchedulerException;
import org.rotarysource.core.sep.SepEngine;
import org.rotarysource.core.sep.job.JobDescription;
import org.rotarysource.events.BasicEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Implements a Scheduler Subscriber taking information from
 * a Basic Events Object.
 * This subscriber is designed to schedule an Event in the
 * Scheduled Events processor taking information from a BasicEvent
 * Object captured by EPL. 
 * 
 *
 * Its specially conceived for for a quick Event scheduling from
 * a complex event.
 * 
 * To use it correctly the EPL query must be based in:
 * select * from BasicEvent     query, in order to receive
 * a BasicEvent object
 * 
 @author J. L. Canales
 * 
 */
public class SchedulerSubscriber
{
	private static Logger log = LoggerFactory.getLogger(SchedulerSubscriber.class);

	protected static final String JOBNAME_PARAM_KEY   = "jobName";
	protected static final String JOBGROUP_PARAM_KEY  = "jobGroup";
	protected static final String TASKTYPE_PARAM_KEY  = "taskType";
	protected static final String STARTDATE_PARAM_KEY = "startDate";
	
	/**
	 * Sep Engine to support scheduled execution
	 */
	private SepEngine sepEngine;
	
	/**
	 * SechedulerSubscriber constructor.
	 * 
	 * @param aiSepEngine SepEngine used by this object
	 */
	public SchedulerSubscriber(SepEngine aiSepEngine) {
		super();
		
		this.sepEngine = aiSepEngine;
	}
	
	
	/**
	 * processEvent method that implements the Scheduling Event routine
	 * 
	 * @param BasicEvent Event received with scheduling information. That event must contain three fields
	 * to schedule event.
	 * <p>compData("jobName") Name of Job to be scheduled.</p>
	 * <p>compData("jobGroup") Name of Job Group to be scheduled.</p>
	 * <p>compData("taskType") Task Type to be executed by the job.</p>
	 * <p>compData("startdate") Scheduling date to be executed by the job.</p>
	 * <p>compData(...) The rest of compData elements will be used as Task Params</p>
	 * @see {@link org.rotarysource.core.sep.SepEngine}
	 */
	public void update(BasicEvent event) {
		Assert.notNull(event, "Received null event to be scheduled!");
		Assert.notNull(event.getCompData().containsKey(JOBNAME_PARAM_KEY), "BasicEvent must contain a compData('jobName') entry to schedule event in SEP!");
		Assert.notNull(event.getCompData().containsKey(JOBGROUP_PARAM_KEY), "BasicEvent must contain a compData('jobGroup') entry to schedule event in SEP!");
		Assert.notNull(event.getCompData().containsKey(TASKTYPE_PARAM_KEY), "BasicEvent must contain a compData('taskType') entry to schedule event in SEP!");
		Assert.notNull(event.getCompData().containsKey(STARTDATE_PARAM_KEY), "BasicEvent must contain a compData('startDate') entry to schedule event in SEP!");

		
       	log.info("Scheduling a new event in SEP");
       	log.debug(event.toString());

       	
		JobDescription jobDesk = new JobDescription(event.getCompData().get(JOBNAME_PARAM_KEY),
													event.getCompData().get(JOBGROUP_PARAM_KEY),
													event.getCompData().get(TASKTYPE_PARAM_KEY));
 
		Calendar scheduleCal = DatatypeConverter.parseDateTime(event.getCompData().get(STARTDATE_PARAM_KEY));
		
		jobDesk.setFireDate(scheduleCal.getTime());
		
		
		//Attach Task Params
		Map<String,Object> taskParams = new HashMap<String, Object>();
		
        Set<Entry<String, String>> set = event.getCompData().entrySet();
        // Get an iterator
        Iterator<Entry<String, String>> it = set.iterator();

        while(it.hasNext()) {
        	Entry<String, String> mapEntry = it.next();
        	taskParams.put(mapEntry.getKey(), mapEntry.getValue());
        }
		
		jobDesk.setTaskParams(taskParams);
		
		//Scheduled job
		try {			
			sepEngine.scheduleJob(jobDesk);
			
		} catch (SchedulerException e) {
			StringBuffer errorText  = new StringBuffer( "Event could not be scheduled. ");
			if(log.isDebugEnabled()){
				errorText.append("Message: " + event.toString());
			}
			log.error(errorText.toString(), e);
		}		
  	
	}
}
