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
package org.rotarysource.core.sep.task;

import java.io.Serializable;
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
public interface SepTask extends Runnable,Serializable {
	
	/**
	 * Set the task params
	 * @param HashMap<String, Object> Key, value params to be used in task customization
	 */
	void setTaskParams(Map<String, Object> taskParams);
	
}