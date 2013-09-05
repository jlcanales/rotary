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
package org.rotarysource.core.sep.task.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.rotarysource.core.sep.task.TaskTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTask extends TaskTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -813422059424661749L;

	/**
	 * Apache commons login logger instance
	 */
	private static Logger log = LoggerFactory.getLogger(LogTask.class);

	public static final String LOGTASK_CEP_EVENT_CODE = "SEP-000";
	public static final String LOGTASK_CEP_EVENT_TYPE = "INFO";

	@Override
	public void setTaskParams(Map<String, Object> taskParams) {
		log.warn("LogTask doesn't admit parameters. Received params will be ignored.");
	}

	@Override
	public void run() {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar now = Calendar.getInstance();
		log.info(
				"LogTask Execution call at: {}. An Info Event will be sended to CEP",
				df.format(now.getTime()));

		HashMap<String, String> compData = new HashMap<String, String>();
		compData.put("ExecutionDate", df.format(now.getTime()));
		compData.put("TaskName", "LogTask");

		sendEvent(LOGTASK_CEP_EVENT_CODE, LOGTASK_CEP_EVENT_TYPE, compData);

	}

}
