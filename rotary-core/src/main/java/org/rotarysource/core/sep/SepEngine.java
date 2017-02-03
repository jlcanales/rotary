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
package org.rotarysource.core.sep;

import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.rotarysource.core.sep.job.JobDescription;
import org.springframework.context.Lifecycle;

public interface SepEngine extends Lifecycle{

	/**
	 * <p>
	 * Add the given <code>{@link org.rotarysource.core.sep.job.JobDescription}</code> to the
	 * Scheduler, and use its data to create internally a <code>JobDetail</code>,
	 * a <code>{@link Trigger}</code> associated with it and schedule the job.
	 * </p>
	 * 
	 * @param job Job information needed to schedule a new job in scheduler engine.
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