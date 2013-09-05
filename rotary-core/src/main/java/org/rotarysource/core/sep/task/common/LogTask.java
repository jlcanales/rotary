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
