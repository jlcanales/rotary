package org.rotarysource.core.sep.job;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Class with all necessary data to schedule a job in the <code>Scheduler</code>.
 * 
 * Its data are used by <code>Scheduler</code> to register a 
 * <code>{@link org.quartz.JobDetail}</code> in the wrapped <code>Scheduler</code>.
 * 
 *  
 */
public class JobDescription implements Serializable {
	
	/**
	 * Needed serial version to serialize this object.
	 */
	private static final long serialVersionUID = 9080586584901118951L;
	/** 
	 * Job name 
	 */
	private String name;
	/** 
	 * Group name 
	 */
	private final String group;
	
	/**
	 * In Scheduler configuration is inserted a JobDetail factory Map with all the
	 * Job types that can be managed.
	 * jobFactoryId identify the key value that identify the JobDetail Factory that must
	 * be used to get correct job for this jobDescription
	 */
	private final String jobFactoryId;
	
	/** 
	 * Date when this <code>ScheduledJob</code> should be executed. 
	 */
	private Date fireDate;
	
	/** 
	 * Job data to be used by the associated task. 
	 */
	private Map<String, Object> taskParams;
	
	/**
	 * Create a <code>JobDetail</code> object with a associated task 
	 * and a scheduling group read-only parameters.
	 * 
	 * This object is created by spring framework using this constructor.
	 * 
	 * @param taskBeanName
	 * @param group
	 */
	protected JobDescription(final String aiName, final String aiGroup, final String aiJobFactoryId) {
		this.name = aiName;
		this.group = aiGroup;
		this.jobFactoryId = aiJobFactoryId;
	}
	
	/**
	 * Get the name of this <code>JobDetail</code>.
	 * 
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of this <code>JobDetail</code>
	 * 
	 */
	public void setName(final String name) {
		this.name = name;
	}
	
	/**
	 * Get the group of this <code>JobDetail</code>.
	 * 
	 */
	public String getGroup() {
		return group;
	}

	
	/**
	 * Get the date when job will be fired.
	 * 
	 */
	public Date getFireDate() {
		return (Date)fireDate.clone();
	}

	/**
	 * Set the date when job will be fired.
	 * 
	 */
	public void setFireDate(final Date fireDate) {
		this.fireDate = (Date)fireDate.clone();
	}

	
	/**
	 * Set job data use in execution of this job.
	 * 
	 */
	public void setTaskParams(final Map<String, Object> aiTaskParams) {
		if (null == aiTaskParams)
			this.taskParams = null;
		else {
			this.taskParams = new HashMap<String,Object>();
			this.taskParams.putAll(aiTaskParams);
		}
	}
	
	public Map<String, Object> getTaskParams() {
		
		Map<String, Object> tempTaskParams = null;
		
		if( this.taskParams != null){
			tempTaskParams = new HashMap<String,Object>();
			tempTaskParams.putAll(this.taskParams);			
		}
		
		return taskParams;
	}

	/* (non-Javadoc) */
	@Override
	public String toString() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		final StringBuilder builder = new StringBuilder();
		builder.append("JobDescription [name=").append(name)
				.append(", group=").append(group)
				.append(", jobFactoryId=").append(jobFactoryId)
				.append(", fireDate=[").append(sdf.format(fireDate))
				.append("], taskParams=").append(taskParams).append("]");
		return builder.toString();
	}

	public String getJobFactoryId() {
		return jobFactoryId;
	}


}
