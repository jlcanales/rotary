package org.rotarysource.core.sep;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.rotarysource.core.sep.job.JobDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.util.Assert;

/**
 * Scheduled Event Processor engine. This class Manage the job scheduling
 * 
 */
public class SepEngineQuartzImpl implements SepEngine {

	/**
	 * Apache commons login logger instance
	 */
	private static Logger log = LoggerFactory.getLogger(SepEngineQuartzImpl.class);

	/**
	 * Quartz scheduler wrapper
	 */
	private transient final Scheduler scheduler;
	/**
	 * Quartz triggers factory
	 */
	private transient final ObjectFactory<SimpleTrigger> jobTriggerFactory;
	/**
	 * Quartz jobDetail factory Map This Map include the different object
	 * factories to create all job types available
	 */
	private transient final HashMap<String, ObjectFactory<JobDetail>> jobDetailFactoryMap;

	/**
	 * Constructor of the <code>PEMScheduler</code> object.
	 * 
	 * This object is created by spring framework using this constructor.
	 * 
	 * @param scheduler
	 *            The Quartz scheduler
	 * @param jobDetailFactory
	 *            Job detail factory.
	 * @param jobTriggerFactory
	 *            Trigger factory.
	 * @throws SchedulerException Returned exception when a Quartz initialization error happens
	 */
	public SepEngineQuartzImpl(
			final Scheduler aiScheduler,
			final HashMap<String, ObjectFactory<JobDetail>> aiJobDetailFactoryMap,
			final ObjectFactory<SimpleTrigger> aiJobTriggerFactory)
			throws SchedulerException {

		Assert.notNull(aiScheduler,
				"An scheduler is needed to create SepEngine");
		Assert.notNull(aiJobDetailFactoryMap, "Job Factory Map cannot be null!");
		Assert.notNull(aiJobTriggerFactory,
				"Trigger Factory to scheduler cannot be null!");

		this.scheduler = aiScheduler;
		this.jobDetailFactoryMap = aiJobDetailFactoryMap;
		this.jobTriggerFactory = aiJobTriggerFactory;

		if (log.isInfoEnabled()) {
			log.info("==============================================");
			log.info("Initializing Scheduled Events Processor Engine");
			log.info("==============================================");

			Set<String>    jobKeys = this.jobDetailFactoryMap.keySet();
			Iterator<String> keyIt = jobKeys.iterator();
			while (keyIt.hasNext()) {
				String key = (String) keyIt.next();
				log.info("Registered jobDetailFactory for job type: {}", key);
			}
		}

		// Start Scheduler
		log.info("Starting scheduler Quarth Engine");
		if (!this.scheduler.isStarted()) {
			this.scheduler.start();
			log.info("Quarth Engine Started;");
		} else {
			log.info("Quarth Engine Already Started; ");
		}

	}

    /* (non-Javadoc)
	 * @see org.rotarysource.core.sep.SepEngineIntrf#scheduleJob(org.rotarysource.core.sep.job.JobDescription)
	 */	
	@Override
	public final void scheduleJob(final JobDescription job)
			throws SchedulerException {
		Assert.notNull(job, "Job to scheduler cannot be null!");

		if (jobDetailFactoryMap.containsKey(job.getJobFactoryId()) == false) {
			// Manage exception
			StringBuffer errorTxt = new StringBuffer("Job Type not found for: ");
			errorTxt.append(job.toString());

			log.error(errorTxt.toString());
			throw (new IllegalArgumentException(errorTxt.toString()));

		}

		final JobDetail jobDetail = jobDetailFactoryMap.get(
				job.getJobFactoryId()).getObject();
		jobDetail.setName(job.getName());
		jobDetail.setGroup(job.getGroup());
		if (job.getTaskParams() != null)
			jobDetail.getJobDataMap().put("taskParams", job.getTaskParams());

		final SimpleTrigger trigger = jobTriggerFactory.getObject();
		trigger.setRepeatCount(0);
		trigger.setStartTime(job.getFireDate());
		trigger.setName(job.getName());
		trigger.setGroup(job.getGroup());

		innerScheduleJob(jobDetail, trigger);
	}

	/**
	 * Schedule or reschedule a job based on its jobDetail and Trigger If the
	 * asked job doesnt exists it will be scheduled with trigger. If the asked
	 * job exists in the scheduler, its definition will be updated with the new
	 * params and it will be rescheduled with the new trigger definition.
	 * 
	 * @param jobDetail
	 *            Job to be scheduled
	 * @param trigger
	 *            Trigger definition for the job
	 * @throws SchedulerException
	 */
	private void innerScheduleJob(final JobDetail jobDetail,
			final Trigger trigger) throws SchedulerException {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		scheduler.addJob(jobDetail, true);

		trigger.setJobName(jobDetail.getName());
		trigger.setJobGroup(jobDetail.getGroup());

		if (null == scheduler.getTrigger(trigger.getName(), trigger.getGroup())) {
			log.info("Scheduling New Job {}  at date: {}",
					jobDetail.getFullName(), df.format(trigger.getStartTime()));

			scheduler.scheduleJob(trigger);
		} else {
			log.info("Rescheduling Existing Job {}  at date: {}",
					jobDetail.getFullName(), df.format(trigger.getStartTime()));

			scheduler.rescheduleJob(trigger.getName(), trigger.getGroup(),
					trigger);
		}
	}
}