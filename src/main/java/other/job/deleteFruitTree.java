/*
 * @(#) ClearRich.java 2016-11-11
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */
package other.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class deleteFruitTree implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("15秒执行一次!");
	}

}
