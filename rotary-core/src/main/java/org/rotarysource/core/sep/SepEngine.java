package org.rotarysource.core.sep;

import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.rotarysource.core.sep.job.JobDescription;

public interface SepEngine {

	/**
	 * <p>
	 * Add the given <code>{@link org.rotarysource.core.sep.job.JobDescription}</code> to the
	 * Scheduler, and use its data to create internally a <code>JobDetail</code>,
	 * a <code>{@link Trigger}</code> associated with it and schedule the job.
	 * </p>
	 * 
	 * @param JobDescription Job information needed to schedule a new job in scheduler engine.
	 * @throws SchedulerException
	 *           if the <code>JobDescription<code> or Trigger cannot be added to 
	 *           the Scheduler, or there is an internal Scheduler error.
	 *     
	 * @see org.quartz.Scheduler           
	 * @see org.quartz.JobDetail
	 * @see org.quartz.Trigger
	 */
	public abstract void scheduleJob(final JobDescription job)
			throws SchedulerException;

}