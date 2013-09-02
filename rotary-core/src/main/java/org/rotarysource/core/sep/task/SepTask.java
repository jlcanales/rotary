package org.rotarysource.core.sep.task;

import java.util.Map;


/**
 * <p>
 * This Interface define the task object access methods
 * A Task Object is a customizable Runnable object that will be executed
 * by a <code>ScheduledJob</code>
 * </p>
 * <p>
 *    Defining SEPTasp as Runnable make it flexible enough to be executed
 *    directly by ScheduledJob or to be executed by an executors pool.
 * </p>
 * 
 * @see org.rotarysource.core.sep.job.ScheduledJob
 */
public interface SepTask extends Runnable {
	
	/**
	 * Set the task params
	 * @param HashMap<String, Object> Key, value params to be used in task customization
	 */
	void setTaskParams(Map<String, Object> taskParams);
	
}