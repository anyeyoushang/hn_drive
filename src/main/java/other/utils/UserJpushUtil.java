/*
 * @(#) JpushUtil.java 2016年1月23日
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */
package other.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import other.utils.StrUtils;


import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Message.Builder;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;


/**
 * 
 * @Description 
 * 
 * @author mn.qiao
 * @version 1.0
 * @since 2016年1月23日
 */
public class UserJpushUtil {
	protected static final Logger LOG = LoggerFactory.getLogger(UserJpushUtil.class);
	public static final String AppKey = "2b500dbf9ae445fd06185dd0";
	public static final String MasterSecret = "c777341dafb20dcb39c31377";
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
	 * 测试方法
	 */
	public static void main(String[] args) {
		sendAlias("流量奖励", "恭喜你获得5M流量奖励", PushPlatform.所有平台, "18715083418");
		// PushPayload payload = PushPayload.alertAll("adsfads");
		// sendNoticeAlias("内容", PushPlatform.安卓和苹果, "18715083418");
	}
	
	/**
	 * 给所有人发送消息
	 * @author mn.qiao
	 * @since 2016年1月23日
	 * @param message
	 */
	public static void sendAll(String message){
		try {
			PushResult result=jpushClient.sendPush(PushPayload.alertAll(message));
			System.out.println(result);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过别名来推送消息
	 * @author mn.qiao
	 * @since 2016年1月23日
	 * @param alias别名 message消息
	 * type类型 0 所有 1 ios 2 android
	 */
	public static void sendNoticeAlias(String notification, PushPlatform platform, String ... alias){
		try {
			// 创建ios的通知对象
			IosNotification.Builder iosBuilder = IosNotification.newBuilder().autoBadge().setSound("default");
			
			iosBuilder.setAlert(notification);// 设置通知的内容
			// 创建推送对象
			PushPayload pushPayload =PushPayload.newBuilder()
					.setPlatform(Platform.all())// 设置平台
					.setAudience(Audience.alias(alias))// 设置观众(可多个)
					// 设置通知
					.setNotification(Notification.newBuilder()
						.addPlatformNotification(iosBuilder.build()).build())// 给推送对象添加IOS的通知
						.setOptions(Options.newBuilder().setApnsProduction(true).build())
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
	
	/**
	 * 通过别名推送(包含通知和消息)
	 * @author wh
	 * @since 2016-10-8
	 * @param notification 通知内容
	 * @param platfrom 所属平台
	 * @param alias 别名,可以有多个
	 */
	public static void sendAlias(String messageTitle, String notification,PushPlatform platfrom, String ...alias){
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
			PushPayload pushPayload = PushPayload.newBuilder()
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
	
	/**
	 * 根据tag来发送消息
	 * @author mn.qiao
	 * @since 2016年1月23日
	 * @param tag
	 * @param message
	 * @param type 0 all 1 ios 2android
	 */
	public static void sendTag(String notification,String message,PushPlatform platfrom,String...tag){
		PushPayload pushPayload=PushPayload.newBuilder()
				.setPlatform(platfrom.push())
				.setAudience(Audience.tag(tag))
				.setNotification(Notification.alert(notification))
				.setMessage(Message.content(message))
				.build();
		PushResult result;
		try {
			result = jpushClient.sendPush(pushPayload);
			System.out.println(result);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		
	}
	
	public static final Map<String, Integer> map = new HashMap<String, Integer>();

	/**
	 * 
	 * @author dd.cui
	 * @since 2016年1月9日
	 * @param alert
	 * @param tags
	 * @param loginName
	 * @param device
	 */
	public static void jSend_notification(String alert, String[] tags,
			String[] loginName, String device) {
		JPushClient jpushClient = UserJpushUtil.jpushClient;
		PushPayload payload = send_N(alert, tags, loginName, device);
		try {
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result);

		} catch (APIConnectionException e) {
			System.out.println(e);
		} catch (APIRequestException e) {
			System.out.println(e);
			System.out
					.println("Error response from JPush server. Should review and fix it. "
							+ e);
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Msg ID: " + e.getMsgId());
		}
	}

	/**
	 * 
	 * 
	 * @author dd.cui
	 * @since 2016年1月9日
	 * @param title
	 *            标题
	 * @param msg
	 *            内容
	 * @param tags
	 *            标签
	 * @param loginName
	 *            别名
	 * @param device
	 *            设备
	 * @param extra
	 * 
	 */
	public static boolean JSend_Message(String title, String msg,
			String[] tags, String[] loginName, String device,
			final Map<String, String> extra) {
		boolean flag = false;
		Audience at = pushPeople(tags, loginName);
		try {
			Builder msgBd = Message.newBuilder().setTitle(title)
					.setMsgContent(msg);
			Platform p = pushDevice(device);// 选择推送设备

			if (extra != null) {
				for (String key : extra.keySet()) {
					msgBd.addExtra(key, extra.get(key));
				}
			}
			cn.jpush.api.push.model.notification.IosNotification.Builder iosBd = IosNotification
					.newBuilder().setAlert(msg).setBadge(+1)
					.setSound("default");

			PushPayload pushPayload = PushPayload
					.newBuilder()
					.setPlatform(p)
					.setNotification(
							Notification.newBuilder()
									.addPlatformNotification(iosBd.build())
									.build())
					.setOptions(
							Options.newBuilder().setApnsProduction(true)
									.build())
					// 推送iPhone 通知
					.setAudience(at)
					// Audience.newBuilder().addAudienceTarget(at).build())
					.setMessage(msgBd.build()).build();
			PushResult result = jpushClient.sendPush(pushPayload);
			flag = result.isResultOK();
			if (result.isResultOK()) {
				System.out.println("推送结果：" + result.toString());
			} else {
				if (result.isResultOK()) {
					System.out.println("推送失败：" + result.getOriginalContent());
				} else {
					System.out.println(result.getOriginalContent());
				}
			}
		} catch (APIConnectionException e) {
			System.out.println(e);
		} catch (APIRequestException e) {
			System.out.println(e);
		}
		return flag;
	}

	/**
	 * 有声音的
	 * @author dd.cui
	 * @since 2016年4月1日
	 * @param title
	 * @param msg
	 * @param tags
	 * @param loginName
	 * @param device
	 * @param extra
	 * @return
	 */
	public static boolean JSend_MessageHasSound(String title, String msg,
			String[] tags, String[] loginName, String device,
			final Map<String, String> extra) {
		boolean flag = false;
		Audience at = pushPeople(tags, loginName);
		try {
			Builder msgBd = Message.newBuilder().setTitle(title)
					.setMsgContent(msg);
			Platform p = pushDevice(device);// 选择推送设备

			if (extra != null) {
				for (String key : extra.keySet()) {
					msgBd.addExtra(key, extra.get(key));
				}
			}
			cn.jpush.api.push.model.notification.IosNotification.Builder iosBd = IosNotification
					.newBuilder().setAlert(msg).setBadge(+1).addExtras(extra)
					.setSound("default");

			PushPayload pushPayload = PushPayload
					.newBuilder()
					.setPlatform(p)
					.setNotification(
							Notification.newBuilder()
									.addPlatformNotification(iosBd.build())
									.build())
					.setOptions(
							Options.newBuilder().setApnsProduction(true)
									.build())
					// 推送iPhone 通知
					.setAudience(at)
					.setAudience(Audience.tag("JPushSound_Default"))
					// Audience.newBuilder().addAudienceTarget(at).build())
					.setMessage(msgBd.build()).build();
			PushResult result = jpushClient.sendPush(pushPayload);
			flag = result.isResultOK();
			if (result.isResultOK()) {
				System.out.println("推送结果：" + result.toString());
			} else {
				if (result.isResultOK()) {
					System.out.println("推送失败：" + result.getOriginalContent());
				} else {
					System.out.println(result.getOriginalContent());
				}
			}
		} catch (APIConnectionException e) {
			System.out.println(e);
		} catch (APIRequestException e) {
			System.out.println(e);
		}
		return flag;
	}

	/**
	 * 没声音的
	 * 
	 * @author dd.cui
	 * @since 2016年4月1日
	 * @param title
	 * @param msg
	 * @param tags
	 * @param loginName
	 * @param device
	 * @param extra
	 * @return
	 */
	public static boolean JSend_MessageNotSound(String title, String msg,
			String[] tags, String[] loginName, String device,
			final Map<String, String> extra) {
		boolean flag = false;
		Audience at = pushPeople(tags, loginName);
		try {
			Builder msgBd = Message.newBuilder().setTitle(title)
					.setMsgContent(msg);
			Platform p = pushDevice(device);// 选择推送设备

			if (extra != null) {
				for (String key : extra.keySet()) {
					msgBd.addExtra(key, extra.get(key));
				}
			}
			cn.jpush.api.push.model.notification.IosNotification.Builder iosBd = IosNotification
					.newBuilder().setAlert(msg).setBadge(+1).disableSound()
					.addExtras(extra);

			@SuppressWarnings("static-access")
			PushPayload pushPayload = PushPayload
					.newBuilder()
					.setPlatform(p)
					.setNotification(
							Notification.newBuilder()
									.addPlatformNotification(iosBd.build())
									.build())
					.setOptions(
							Options.newBuilder().setApnsProduction(true)
									.build())
					// 推送iPhone 通知
					.setAudience(at.tag_and("JPushSound_Mute"))					
					.setMessage(msgBd.build()).build();
			PushResult result = jpushClient.sendPush(pushPayload);
			flag = result.isResultOK();
			if (result.isResultOK()) {
				System.out.println("推送结果：" + result.toString());
			} else {
				if (result.isResultOK()) {
					System.out.println("推送失败：" + result.getOriginalContent());
				} else {
					System.out.println(result.getOriginalContent());
				}
			}
		} catch (APIConnectionException e) {
			System.out.println(e);
		} catch (APIRequestException e) {
			System.out.println(e);
		}
		return flag;
	}

	/**
	 * 
	 * 
	 * @author dd.cui
	 * @since 2016年1月8日
	 * @param alert
	 * @param tags
	 * @param loginName
	 * @return
	 */
	public static PushPayload send_N(String alert, String[] tags,
			String[] loginName, String device) {
		Platform p = pushDevice(device);// 选择推送设备
		Audience a = pushPeople(tags, loginName);// 选择推送标签或者别名
		cn.jpush.api.push.model.notification.IosNotification.Builder iosBd = IosNotification
				.newBuilder().setAlert(alert).setBadge(1).setSound("default");

		return PushPayload
				.newBuilder()
				.setPlatform(p)
				.setAudience(a)
				.setNotification(
						Notification.newBuilder()
								.addPlatformNotification(iosBd.build()).build())
				.setOptions(
						Options.newBuilder().setApnsProduction(true).build())
				.build();
	}

	/**
	 * 根据需要推送设备
	 * 
	 * @author dd.cui
	 * @since 2016年1月8日
	 * @param device
	 * @return
	 */
	@Deprecated
	public static Platform pushDevice(String device) {
		if ((Constants.IOS).equals(device)) {
			return Platform.ios();
		} else if ((Constants.android).equals(device)) {
			return Platform.android();
		} else {
			return Platform.all();
		}
	}

	/**
	 * 选择推送标签还是别名
	 * 
	 * @author dd.cui
	 * @since 2016年1月8日
	 * @param tags
	 * @param loginName
	 * @return
	 */

	public static Audience pushPeople(String tags, String loginName) {
		if (StrUtils.isNull(tags) && StrUtils.isNull(loginName)) {
			// 标签和别名都是空的情况下
			return Audience.all();
		} else {
			if (!StrUtils.isNull(tags)) {
				// 标签不是空
				return Audience.tag(tags);
			} else {
				// 别名不是空
				return Audience.alias(loginName);
			}
		}
	}

	public static Audience pushPeople(String[] tags, String[] loginName) {
		if (StrUtils.isNull(tags) && StrUtils.isNull(loginName)) {
			// 标签和别名都是空的情况下
			return Audience.all();
		} else {
			if (!StrUtils.isNull(tags)) {
				// 标签有数据
				return Audience.tag_and(tags);
			} else {
				return Audience.alias(loginName);
			}
		}
	}
	
	
	
}
