package org.rotarysource.subscriber.basicevent;

import java.util.Calendar;

import javax.xml.bind.DatatypeConverter;

import org.rotarysource.core.annotations.Subscriber;
import org.rotarysource.core.sep.SepEngine;
import org.rotarysource.events.BasicEvent;
import org.springframework.util.Assert;

@Subscriber(eplStatement = "@Name('delayedExecutionStatement') select istream * from BasicEvent  where compData('IdOperation')='Delay' and eventType='INFO'")
public class DelayScheduledSubscriber extends SchedulerSubscriber {

	protected static final String DELAY_PARAM_KEY = "delay";	
	
	public DelayScheduledSubscriber(SepEngine aiSepEngine) {
		super(aiSepEngine);
	}

	/**
	 * processEvent method that implements the Scheduling Event routine
	 * 
	 * @param event Event received with scheduling information. That event must contain three fields
	 * to schedule event.
	 * <p>compData("jobName") Name of Job to be scheduled.</p>
	 * <p>compData("jobGroup") Name of Job Group to be scheduled.</p>
	 * <p>compData("taskType") Task Type to be executed by the job.</p>
	 * <p>compData("delay") Scheduling delay to be executed by the job.</p>
	 * <p>compData(...) The rest of compData elements will be used as Task Params</p>
	 * @see {@link org.rotarysource.core.sep.SepEngine}
	 */
	public void update(BasicEvent event) {
		Assert.notNull(event.getCompData().containsKey(DELAY_PARAM_KEY), "BasicEvent must contain a compData('delay') entry to schedule event in SEP!");

        // Ignore date. Add delay to current time.
		Calendar nextLaunch = Calendar.getInstance();
		nextLaunch.add(Calendar.SECOND, Integer.parseInt(event.getCompData().get(DELAY_PARAM_KEY)));	
		
		event.getCompData().put(SchedulerSubscriber.STARTDATE_PARAM_KEY, DatatypeConverter.printDateTime(nextLaunch));
		super.update(event);
		
	}
}
