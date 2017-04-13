/*
 * @(#) Demo2.java 2016-12-3
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */
package jpushdemo;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class Demo2 {
	public static final String AppKey = "3d256e0a985b145a90d9a86c";
	public static final String MasterSecret = "0502320bae321786a9c65675";
	public static final JPushClient jpushClient = new JPushClient(MasterSecret, AppKey);
	
	public static void main(String[] args) {
		// 构建一个简单的警报通知对象到所有平台和所有的观众的快捷方式
		new Builder()
        .setPlatform(Platform.all())
        .setAudience(Audience.all())
        .setNotification(Notification.alert("阿斯顿发斯蒂芬")).build();
	}
}
