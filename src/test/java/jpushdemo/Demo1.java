/*
 * @(#) Demo1.java 2016-12-3
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */
package jpushdemo;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.Message.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class Demo1 {
	
	public static void main(String[] args) {
		// sendAlias("流量奖励", "恭喜你获得5M流量奖励", PushPlatform.所有平台, "18715083418");
		// System.out.println(PushPlatform.安卓和苹果.toString());
		// sendAll("发送消息");
		
	}
	
	public static final String AppKey = "3d256e0a985b145a90d9a86c";
	public static final String MasterSecret = "0502320bae321786a9c65675";
	public static final JPushClient jpushClient = new JPushClient(MasterSecret, AppKey);
	
	/**
	 * 推送平台
	 * @Description 
	 * 
	 * @author Chuck Don
	 * @version 1.0
	 * @since 2016年8月1日
	 */
	public enum PushPlatform{
		/**
		 * 安卓设备
		 */
		安卓(Platform.android()),
		/**
		 * 苹果设备
		 */
		苹果(Platform.ios()),
		/**
		 * 所有平台
		 */
		所有平台(Platform.all()),
		/**
		 * 只有安卓和苹果
		 */
		安卓和苹果(Platform.android_ios());
		
		private final Platform platform;
		PushPlatform(Platform platform){
			this.platform=platform;
		}
		public Platform push(){
			return platform;
		}
	}
	
	/**
	 * 给所有人发送消息
	 * @author mn.qiao
	 * @since 2016年1月23日
	 * @param message
	 */
	public static void sendAll(String message){
		try {
			PushResult result = jpushClient.sendPush(PushPayload.alertAll(message));
			System.out.println(result);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 通过别名推送(包含通知和消息)
	 * @author wh
	 * @since 2016-10-8
	 * @param notification 通知内容
	 * @param platfrom 所属平台
	 * @param alias 别名,可以有多个
	 */
	public static void sendAlias(String messageTitle, String notification,PushPlatform platfrom,String ...alias){
		try {
			Builder builder = Message.newBuilder();
			// 创建ios的通知对象
			IosNotification.Builder iosBuilder = IosNotification.newBuilder().autoBadge().setSound("default");
			// 创建android的通知对象
			AndroidNotification.Builder andBuilder = AndroidNotification.newBuilder();
			
			builder.setTitle(messageTitle);// 设置消息的标题
			builder.setMsgContent(notification);// 设置消息的内容
			iosBuilder.setAlert(notification);// 设置通知的内容
			andBuilder.setAlert(notification);
			// 创建推送对象
			PushPayload pushPayload =PushPayload.newBuilder()
					.setPlatform(Platform.all())// 设置平台
					.setAudience(Audience.alias(alias))// 设置观众(可多个)
					// 设置通知
					.setNotification(Notification.newBuilder()
						// .addPlatformNotification(andBuilder.build())// 给推送对象添加安卓的通知
						.addPlatformNotification(iosBuilder.build()).build())// 给推送对象添加IOS的通知
						.setOptions(Options.newBuilder().setApnsProduction(true).build())
					// 设置消息
					.setMessage(builder.build())
					.build();
				PushResult pushResult = jpushClient.sendPush(pushPayload);
				if (pushResult.isResultOK()) {
					System.out.println("推送成功:"+pushResult.toString());
				}else {
					System.out.println("推送失败:"+pushResult.toString());
				}
			} catch (APIConnectionException e) {
				e.printStackTrace();
			} catch (APIRequestException e) {
				System.out.println("Error response from JPush server. Should review and fix it. "+e);
				System.out.println("HTTP Status:"+e.getStatus());
				System.out.println("Error Code:"+e.getErrorCode());
				System.out.println("Error Message: "+e.getErrorMessage());
				System.out.println("Msg ID:"+e.getMsgId());
			}
	}
}
