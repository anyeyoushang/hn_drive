/*
 * @(#) JpushFactory.java 2016-12-13
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */
package other.jpush;

import cn.jpush.api.JPushClient;

public class JpushFactory {
	private static final String userKey = "2b500dbf9ae445fd06185dd0";
	private static final String userSecret = "c777341dafb20dcb39c31377";
	
	private static final String driverKey = "3d256e0a985b145a90d9a86c";
	private static final String driverSecret = "0502320bae321786a9c65675";
	
	
	public static JPushClient getUserJpush(){
		return new JPushClient(userSecret, userKey);
	}
	
	public static JPushClient getDriverJpush(){
		return new JPushClient(driverSecret, driverKey);
	}
}
