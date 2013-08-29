package org.rotarysource.core.sep.task;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskMock extends TaskTemplate {

    /** 
     * Apache commons login logger instance
     */	
	private static Logger log = LoggerFactory.getLogger(TaskMock.class);
	
	@Override
	public void setTaskParams(Map<String, Object> taskParams) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
        log.info("Run execution called");

	}

}
