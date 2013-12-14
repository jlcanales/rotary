/*
Copyright (c) 2013 J. L. Canales Gasco
 
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA}]
*/
package org.rotarysource.core.sep.job;


import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.rotarysource.core.sep.task.SepTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * <code>{@link org.springframework.scheduling.quartz.QuartzJobBean}</code> interface
 * implementation for Scheduled Event Processing.
 * 
 *   ScheduledJob allow a <code>SEPTask</code> to be executed in a specific 
 * scheduled date. 
 *   Optionally, if taskExecutorPool is set-up, task execution will be delegate
 *   in a separate executor pool.
 * 
 * @see org.springframework.scheduling.quartz.QuartzJobBean.
 */
public class ScheduledJob extends QuartzJobBean {

    /** 
     * Apache commons login logger instance
     */	
	private static Logger log = LoggerFactory.getLogger(ScheduledJob.class);	
	
	/** 
	 * Job params to be passed to the task execution. 
	 * */
	private  Map<String, Object> taskParams;
	
	/**
	 * Task to be executed reference
	 */
	private SepTask task;

	/**
	 * Task executor used to execute SEPTask in a separate thread executor pool.
	 */
	private TaskExecutor taskExecutorPool;
	
	/**
	 * Retrieve associated 
	 * <code>{@link SEPTask}</code> and execute it.
	 * 
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#execute(JobExecutionContext)
	 */
	@Override
	protected void executeInternal(final JobExecutionContext context) throws JobExecutionException {

		log.info("Received call to execute job {}.{}", context.getTrigger().getJobKey().getGroup(), context.getTrigger().getJobKey().getName());
		if( taskParams != null)
			task.setTaskParams(taskParams);
		
		if( taskExecutorPool != null)
			taskExecutorPool.execute(task);
		else
			task.run();
	}

	/**
	 * Sets the task params to customize execution of run method
	 * @param taskParams key, value task params.
	 */
	public void setTaskParams(Map<String, Object> taskParams) {
		this.taskParams = taskParams;
	}

	/**
	 * Sets the task to be executed when job will be triggered
	 * @param task
	 */
	public void setTask(SepTask task) {
		this.task = task;
	}

	/**
	 * Sets the TaskExecutor for a delegated execution
	 * @param aiTaskExecutorPool Reference to a TaskExecutor Object
	 */
	public void setTaskExecutorPool(TaskExecutor aiTaskExecutorPool) {
		this.taskExecutorPool = aiTaskExecutorPool;
	}
}
