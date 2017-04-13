package controller.mobile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.dao.CancelOrder;
import model.dao.Order;
import model.dao.User;
import model.dao.Order.DriverDeleteEnum;
import model.dao.Order.OrderStateEnum;
import model.dao.Order.OrderTypeEnum;
import model.dao.Order.PushStateEnum;
import model.dao.Order.UserDeleteEnum;
import model.dao.User.AcceptStateEnum;

import org.junit.Test;

import other.jpush.JpushFactory;
import other.jpush.JpushUtil;
import other.utils.ToolUtils;

import com.jfinal.aop.Before;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.tx.Tx;

import config.anno.Controller;
import controller.tool.BaseController;

/**
 * 订单类
 * @Description 
 * @author wh
 * @version 1.0
 * @since 2016-12-1
 */
@Controller("/mobile/order")
public class OrderController extends BaseController {
	// 接客距离,超过该距离就开始计费(距离都按公里计算)
	private static double acceptUserDistance;
	// 超过免费距离后的接客费用
	private static double acceptUserMoney;
	// 白天时段开始时间
	private static int dayStartTime;
	// 白天时段结束时间
	private static int dayEndTIme;
	// 夜间时段开始时间
	private static int nightStartTime;
	// 夜间时段结束时间
	private static int nightEndTime;
	// 司机免费等待客人的时间
	private static int waitTime;
	// 超过免费等待时间后每几分钟
	private static int waitChargeTime;
	// 超过免费等待时间后每几分钟所收的钱
	private static int waitChargeMoney;
	// 白天代驾时段的起步距离
	private static double DayDistanceGradeOne;
	// 白天的起步费用
	private static double DayDistanceGradeOneMoney;
	// 白天超过起步距离后每多少公里多少钱
	private static double DayDistanceGradeTwo;
	private static double DayDistanceGradeTwoMoney;
	// 夜间代驾时段的起步距离
	private static double nightDistanceGradeOne;
	// 夜间的起步费用
	private static double nightDistanceGradeOneMoney;
	// 夜间超过起步距离后每多少公里多少钱
	private static double nightDistanceGradeTwo;
	private static double nightDistanceGradeTwoMoney;
	
	private DriverPositionController positionController = new DriverPositionController();
	
	@Test
	public void chargeRule(){
		System.out.println("接客距离,超过该距离就开始计费(acceptUserDistance)" + acceptUserDistance);
		System.out.println("超过免费距离后的接客费用(acceptUserMoney)" + acceptUserMoney);
		System.out.println("白天时段开始时间(dayStartTime)" + dayStartTime);
		System.out.println("白天时段结束时间(dayEndTIme)" + dayEndTIme);
		System.out.println("夜间时段开始时间(nightStartTime)" + nightStartTime);
		System.out.println("夜间时段结束时间(nightEndTime)" + nightEndTime);
		System.out.println("司机免费等待客人的时间(waitTime)" + waitTime);
		System.out.println("超过免费等待时间后每几分钟(waitChargeTime)" + waitChargeTime);
		System.out.println("超过免费等待时间后每几分钟所收的钱(waitChargeMoney)" + waitChargeMoney);
		System.out.println("白天代驾时段的起步距离(DayDistanceGradeOne)" + DayDistanceGradeOne);
		System.out.println("白天的起步费用(DayDistanceGradeOneMoney)" + DayDistanceGradeOneMoney);
		System.out.println("白天超过起步距离后每(DayDistanceGradeTwo)"+ DayDistanceGradeTwo +"公里收费(DayDistanceGradeTwoMoney)" + DayDistanceGradeTwoMoney);
		System.out.println("夜间代驾时段的起步距离(nightDistanceGradeOne)" + nightDistanceGradeOne);
		System.out.println("夜间的起步费用(nightDistanceGradeOneMoney)" + nightDistanceGradeOneMoney);
		System.out.println("夜间超过起步距离后每(nightDistanceGradeTwo)"+ nightDistanceGradeTwo +"公里收费(nightDistanceGradeTwoMoney)" + nightDistanceGradeTwoMoney);
	}
	
	static{
		Prop p = PropKit.use("charge.properties");
		acceptUserDistance = Double.valueOf(p.get("acceptUserDistance"));
		acceptUserMoney = Double.valueOf(p.get("acceptUserMoney"));
		dayStartTime = Integer.valueOf(p.get("dayStartTime"));
		dayEndTIme = Integer.valueOf(p.get("dayEndTIme"));
		nightStartTime = Integer.valueOf(p.get("nightStartTime"));
		nightEndTime = Integer.valueOf(p.get("nightEndTime"));
		waitTime = Integer.valueOf(p.get("waitTime"));
		waitChargeTime = Integer.valueOf(p.get("waitChargeTime"));
		waitChargeMoney = Integer.valueOf(p.get("waitChargeMoney"));
		DayDistanceGradeOne = Double.valueOf(p.get("DayDistanceGradeOne"));
		DayDistanceGradeOneMoney = Double.valueOf(p.get("DayDistanceGradeOneMoney"));
		DayDistanceGradeTwo = Double.valueOf(p.get("DayDistanceGradeTwo"));
		DayDistanceGradeTwoMoney = Double.valueOf(p.get("DayDistanceGradeTwoMoney"));
		nightDistanceGradeOne = Double.valueOf(p.get("nightDistanceGradeOne"));
		nightDistanceGradeOneMoney = Double.valueOf(p.get("nightDistanceGradeOneMoney"));
		nightDistanceGradeTwo = Double.valueOf(p.get("nightDistanceGradeTwo"));
		nightDistanceGradeTwoMoney = Double.valueOf(p.get("nightDistanceGradeTwoMoney"));
	}
	
	/**
	 * 显示计费规则
	 * @author wh
	 * @since 2016-12-29
	 */
	public void showChargeRule(){
		Map<String,Object> ruleMap = new HashMap<String, Object>();
		ruleMap.put("acceptUserDistance", acceptUserDistance);
		ruleMap.put("acceptUserMoney", acceptUserMoney);
		ruleMap.put("dayStartTime", dayStartTime);
		ruleMap.put("dayEndTIme", dayEndTIme);
		ruleMap.put("nightStartTime", nightStartTime);
		ruleMap.put("nightEndTime", nightEndTime);
		ruleMap.put("waitTime", waitTime);
		ruleMap.put("waitChargeTime", waitChargeTime);
		ruleMap.put("waitChargeMoney", waitChargeMoney);
		ruleMap.put("DayDistanceGradeOne", DayDistanceGradeOne);
		ruleMap.put("DayDistanceGradeOneMoney", DayDistanceGradeOneMoney);
		ruleMap.put("DayDistanceGradeTwo", DayDistanceGradeTwo);
		ruleMap.put("DayDistanceGradeTwoMoney", DayDistanceGradeTwoMoney);
		ruleMap.put("nightDistanceGradeOne", nightDistanceGradeOne);
		ruleMap.put("nightDistanceGradeOneMoney", nightDistanceGradeOneMoney);
		ruleMap.put("nightDistanceGradeTwo", nightDistanceGradeTwo);
		ruleMap.put("nightDistanceGradeTwoMoney", nightDistanceGradeTwoMoney);
		setAttr("rule", ruleMap);
		renderJsp("/jsp/chargeRule.jsp");
	}
	
	/**
	 * 用户下单
	 * @author wh
	 * @since 2016-12-1
	 */
	@Before(Tx.class)
	public void addOrder(){
		if (checkPara("userId", "startPlaceLon", "startPlaceLat", "startPlaceName", "startPlaceAddress",
				"driveEndLon", "driveEndlat", "driveEndName", "driveEndAddress", "orderDistance", "driveMoney")){
			try {
				Map<String, Object> paramMap = changeParaMap(getParaMap());
//				String userId = paramMap.get("userId").toString();
				// 检测是该用户否有正在进行的订单
//				if(Order.dao.findUserMarchOrder(userId) != null){
//					renderJsonError(RequestError, "您还有未完成的订单");
//					return;
//				}
				
				Order order = new Order();
				order.setOrderId(null);
				order.put(paramMap);
				order.save();
				
				// 下单后的订单可能被用户取消,被接单,和没有人接单
				// 拿到距离和费用,添加订单状态为下单的订单
				// 得到接单后的订单
				// 给用户响应该条订单的结果
				
				// 查询附近的司机
				String startPlaceLon = (String) paramMap.get("startPlaceLon");
				String startPlaceLat = (String) paramMap.get("startPlaceLat");
				// 根据经纬度查询司机并推送
				pushOrder(startPlaceLon, startPlaceLat, order.getOrderId().toString());
				renderJsonError(RequestNormal, "下单成功", order.getOrderId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 司机报单
	 * @author wh
	 * @since 2016-12-28
	 */
	@Before(Tx.class)
	public void reportOrder(){
		if (checkPara("driverId", "reportOrderUserPhone", "startPlaceLon", "startPlaceLat", "startPlaceName", "startPlaceAddress",
				"driveEndLon", "driveEndlat", "driveEndName", "driveEndAddress", "orderDistance", "driveMoney")){
			Map<String, Object> paramMap = changeParaMap(getParaMap());
			Order order = new Order();
			order.setOrderId(null);
			order.put(paramMap);
			order.setOrderType(OrderTypeEnum.司机报单.ordinal());
			order.save();
			
			renderJsonError(RequestNormal, "报单成功", order);
		}
	}
	
	/**
	 * 预估的代驾费用
	 * @author wh
	 * @since 2016-12-14
	 */
	public void countDriveMoney(){
		Double orderDistance =  Double.valueOf(getPara("orderDistance"));
		Double driveMoney = countDriveMoney((orderDistance), new Date());
		renderJsonError(RequestNormal, driveMoney);
	}
	
	/**
	 * 根据距离和时间计算代驾的费用
	 * @author wh
	 * @since 2016-12-2
	 * @param distance
	 */
	public Double countDriveMoney(Double orderDistance, Date date){
		Integer hour = Integer.valueOf(ToolUtils.MsecToDateString(date.getTime(), "HH"));
		// 拿到下单的时间和距离,计算本次代驾的费用
		if(dayStartTime <= hour && hour < dayEndTIme){//白天时段
			if(orderDistance <= DayDistanceGradeOne){
				return DayDistanceGradeOneMoney;
			}else{
				double times = Math.ceil((orderDistance - DayDistanceGradeOne) / DayDistanceGradeTwo);
				return DayDistanceGradeOneMoney + DayDistanceGradeTwoMoney * times;
			}
			
		}else if(nightStartTime <= hour || hour < nightEndTime){// 夜间时段
			if(orderDistance <= nightDistanceGradeOne){
				return nightDistanceGradeOneMoney;
			}else{
				double times = Math.ceil((orderDistance - nightDistanceGradeOne) / nightDistanceGradeTwo);
				return nightDistanceGradeOneMoney + nightDistanceGradeTwoMoney * times;
			}
		}
		return null;
	}

	/**
	 * 推送订单给司机
	 * @author wh
	 * @since 2016-12-22
	 * @param lon
	 * @param lat
	 * @param orderId
	 * @return true推送成功,false推送失败
	 * @throws Exception 
	 */
	public void pushOrder(String lon, String lat, String orderId) throws Exception{
		/*一、5公里内最近+包括4.6分以上，立即推送；
		二、8公里内最近+包括4.6分以上， 2秒；
		三、15公里内最近+包括4.6分以上，5秒；
		四、15公里内最近+包括4.4分以上，9秒；
		五、15公里内最近+包括4.2分以上，14秒
		一条订单一个司机只接收一条推送信息
		*/
		List<String> pushDrivers = positionController.findBestDriver(lon, lat, 0, 5000, 4.6, 5);
		List<String> pushDrivers2 = positionController.findBestDriver(lon, lat, 5000, 8000, 4.6, 5);
		List<String> pushDrivers3 = positionController.findBestDriver(lon, lat, 8000, 15000, 4.6, 5);
		List<String> pushDrivers4 = positionController.findBestDriver(lon, lat, 0, 15000, 4.4, 4.6);
		List<String> pushDrivers5 = positionController.findBestDriver(lon, lat, 0, 15000, 4.2, 4.4);
		
		Map<String, String> extraMap = new HashMap<String, String>();
		extraMap.put("type", PushStateEnum.推送订单.toString());
		extraMap.put("orderId", orderId);
		PushThread pushThread = new PushThread(pushDrivers, pushDrivers2, pushDrivers3, pushDrivers4, pushDrivers5, extraMap);
		pushThread.start();
	}
	
	/**
	 * 根据订单id查询订单
	 * @author wh
	 * @since 2016-12-13
	 */
	public void findOrderById(){
		if (checkPara("orderId")){
			String orderId = getPara("orderId");
			Order order = Order.dao.findOrderDetail(orderId);
			renderJsonError(RequestNormal, order);
		}
	}
	
	/**
	 * 司机接单
	 * @author wh
	 * @since 2016-12-1
	 */
	@Before(Tx.class)
	public void orderReceiving(){
		if (checkPara("orderId", "driverId", "acceptOrderLon", "acceptOrderLat")){
			String orderId = getPara("orderId");
			String driverId = getPara("driverId");
			String acceptOrderLon = getPara("acceptOrderLon");
			String acceptOrderLat = getPara("acceptOrderLat");
			// 判断该司机是否有未完成的订单
//			if(Order.dao.findDriverMarchOrder(driverId) != null){
//				renderJsonError(RequestError, "您还有未完成的订单");
//				return;
//			}
			
			Order order = Order.dao.findById(orderId);
			// 判断订单是否已被用户取消
			if(order == null){
				renderJsonError(RequestError, "用户已取消订单");
				return;
			}
			// 判断该笔订单是否被接单
			if(order.getOrderState() == OrderStateEnum.已接单.ordinal()){
				renderJsonError(RequestError, "已被接单");
				return;
			}
			
			double endLon = order.getStartPlaceLon();
			double endLat = order.getStartPlaceLat();
			// 得到公里
			double startPlaceDistance = ToolUtils.countDistance(Double.valueOf(acceptOrderLon),
					Double.valueOf(acceptOrderLat), endLon, endLat) / 1000;
			
			// 添加接单地到达起始地所产生的费用
			if(startPlaceDistance > acceptUserDistance){
				order.set("startPlaceMoney", acceptUserMoney);
			}
			// 修改订单的状态和司机列
			order.set("orderState", OrderStateEnum.已接单.ordinal());
			order.set("driverId", driverId);
			order.set("acceptOrderLon", acceptOrderLon);
			order.set("acceptOrderLat", acceptOrderLat);
			order.set("acceptOrderTime", new Date());
			order.set("startPlaceDistance", startPlaceDistance);
			order.update();
			
			// 修改司机的接单状态
			modifyAcceptOrderState(driverId, AcceptStateEnum.关闭接单.ordinal());
			
			// 给用户响应
			Map<String, String> extraMap = new HashMap<String, String>();
			extraMap.put("type", PushStateEnum.司机接单.toString());
			extraMap.put("orderId", orderId);
			JpushUtil.sendNoticeAlias(JpushFactory.getUserJpush(), "已被接单",
					extraMap, order.getUserId().toString());
			// 给司机响应
			renderJsonError(RequestNormal, "接单成功");
		}
	}
	
	/**
	 * 司机到达目的地开始等待用户
	 * @author wh
	 * @since 2016-12-14
	 */
	public void waitStart(){
		if(checkPara("orderId")){
			// 记录开始等待的时间
			Order order = Order.dao.findById(getPara("orderId"));
			order.setWaitStartTime(new Date());
			order.update();
			// 给用户推送信息
			JpushUtil.sendNoticeAlias(JpushFactory.getUserJpush(), "司机已到达目的地", 
					order.getUserId().toString());
			renderJsonError(RequestNormal, "开始计时");
		}
	}
	
	/**
	 * 司机等待结束,开始代驾
	 * @author wh
	 * @since 2016-12-14
	 */
	public void waitEnd(){
		if(checkPara("orderId")){
			// 记录结束等待的时间
			Order order = Order.dao.findById(getPara("orderId"));
			Date startTime = order.getWaitStartTime();
			Date endTime = new Date();
			// 得到分钟,计算金额
			long mins = (endTime.getTime() - startTime.getTime()) / 1000 / 60;
			// 超过规定时间计费
			if(mins > waitTime){
				order.set("waitMoney", mins - waitTime);
			}
			order.setWaitEndTime(new Date());
			order.update();
			// 开始代驾(推送)
			JpushUtil.sendNoticeAlias(JpushFactory.getUserJpush(), "开始代驾", 
					order.getUserId().toString());
			
			renderJsonError(RequestNormal, "开始代驾");
		}
	}
	
	/**
	 * 添加额外的费用
	 * @author wh
	 * @since 2016-12-14
	 */
	@Before(Tx.class)
	public void addExtraMoney(){
		if(checkPara("orderId", "extraMoney")){
			Order order = Order.dao.findById(getPara("orderId"));
			order.setExtraMoney(Double.valueOf(getPara("extraMoney")));
			order.update();
			JpushUtil.sendNoticeAlias(JpushFactory.getUserJpush(), "额外费用添加成功", 
					order.getUserId().toString());
			
			renderJsonError(RequestNormal, "额外费用添加成功");
		}
	}
	
	/**
	 * 司机中途改变目的地
	 * @author wh
	 * @since 2016-12-15
	 */
	@Before(Tx.class)
	public void changeDestination(){
		if(checkPara("orderId", "driveEndLon", "driveEndlat", "driveEndName", "driveEndAddress", "orderDistance")){
			Double orderDistance = Double.valueOf(getPara("orderDistance").toString());
			Order order = Order.dao.findById(getPara("orderId"));
			Date addOrderTime = order.getAddOrderTime();
			Double driverMoney = countDriveMoney(orderDistance, addOrderTime);
			String [] fields = {"driveEndLon", "driveEndlat", "driveEndName", "driveEndAddress", "orderDistance"};
			for(String field : fields){
				order.set(field, getPara(field));
			}
			order.setDriveMoney(driverMoney);
			order.update();
			JpushUtil.sendNoticeAlias(JpushFactory.getUserJpush(), "您已修改目的地", 
					order.getUserId().toString());
			renderJsonError(RequestNormal, "修改目的地成功", driverMoney);
		}
	}
	
	/**
	 * 到达目的地,计算最终金额
	 * @author wh
	 * @since 2016-12-14
	 */
	@Before(Tx.class)
	public void arriveDestination(){
		if(checkPara("orderId", "orderDistance")){
			Order order = Order.dao.findById(getPara("orderId"));
			// 接单地到达起始地所产生的费用
			double startPlaceMoney = order.getStartPlaceMoney();
			// 等待费用
			double waitMoney = order.getWaitMoney();
			// 额外费用
			double extraMoney = order.getExtraMoney();
			// 得到下单时间
			Date addOrderTime = order.getAddOrderTime();
			Double orderDistance =  Double.valueOf(getPara("orderDistance"));
			// 计算代驾金额
			Double driverMoney = countDriveMoney(orderDistance, addOrderTime);
			
			// 保存订单信息
			order.setDriveEndTime(new Date());
			order.setDriveMoney(driverMoney);
			order.setOrderDistance(orderDistance);
			order.setDriveTotalMoney(startPlaceMoney + waitMoney + extraMoney + driverMoney);
			order.update();
			
			JpushUtil.sendNoticeAlias(JpushFactory.getUserJpush(), "您已到达目的地", 
					order.getUserId().toString());
			renderJsonError(RequestNormal, "到达目的地成功");
		}
	}
	
	
	/**
	 * 修改司机的接单状态
	 * @author wh
	 * @since 2016-12-28
	 */
	@Before(Tx.class)
	public void modifyAcceptOrderState(){
		if(checkPara("driverId", "acceptState")){
			String driverId = getPara("driverId");
			Integer acceptState = getParaToInt("acceptState");
			modifyAcceptOrderState(driverId, acceptState);
			if(acceptState == AcceptStateEnum.关闭接单.ordinal()){
				renderJsonError(RequestNormal, "关闭成功");
			}else{
				renderJsonError(RequestNormal, "开启成功");
			}
		}
	}
	
	/**
	 * 修改司机的接单状态
	 * @author wh
	 * @since 2016-12-28
	 * @param driverId
	 * @param isAccept
	 */
	public void modifyAcceptOrderState(String driverId, Integer acceptState){
		User driver = User.dao.findById(driverId);
		driver.setAcceptState(acceptState);
		driver.update();
	}
	
	/**
	 * 司机线下收款
	 * @author wh
	 * @since 2016-12-19
	 */
	@Before(Tx.class)
	public void offlinePayment(){
		if(checkPara("orderId")){
			Order order = Order.dao.findById(getPara("orderId"));
			order.setOrderState(OrderStateEnum.支付完成.ordinal());
			order.update();
			
			// 给司机扣除该笔订单的手续费
			Integer driverId = order.getDriverId();
			User driver = User.dao.findById(driverId);
			double userMoney = driver.getUserMoney();
			double driverMoney = order.getDriveMoney();
			driver.setUserMoney(userMoney - driverMoney * 0.2);
			driver.update();
			
			// 修改司机的的接单状态
			modifyAcceptOrderState(driverId.toString(), AcceptStateEnum.开启接单.ordinal());
			
			Map<String, String> extraMap = new HashMap<String, String>();
			extraMap.put("type", PushStateEnum.线下支付.toString());
			JpushUtil.sendNoticeAlias(JpushFactory.getUserJpush(), "订单完成",
					extraMap, order.getUserId().toString());
			renderJsonError(RequestNormal, "支付成功");
		}
	}
	
	/**
	 * 司机选择线上支付
	 * @author wh
	 * @since 2016-12-21
	 */
	public void PickOnlinePayment(){
		if(checkPara("orderId")){
			String orderId = getPara("orderId");
			Order order = Order.dao.findById(orderId);
			Map<String,String> extraMap = new HashMap<String,String>();
			extraMap.put("orderId", orderId);
			extraMap.put("type", PushStateEnum.线上支付.toString());
			JpushUtil.sendNoticeAlias(JpushFactory.getUserJpush(), "线上支付",
					extraMap, order.getUserId().toString());
			renderJsonError(RequestNormal, "选择支付方式成功");
		}
	}
	
	/**
	 * 用户线上支付
	 * @author wh
	 * @since 2016-12-26
	 */
	@Before(Tx.class)
	public void onlinePayment(){
		if(checkPara("orderId")){
			String orderId = getPara("orderId");
			// ------  支付宝付钱  ------------
			System.out.println("付钱成功");
			// ------  支付宝付钱成功  ----------
			// 修改订单状态
			Order order = Order.dao.findById(orderId);
			order.setOrderState(OrderStateEnum.支付完成.ordinal());
			order.update();
			
			// 修改司机的的接单状态
			modifyAcceptOrderState(order.getDriverId().toString(), AcceptStateEnum.开启接单.ordinal());
			
			Map<String,String> extraMap = new HashMap<String,String>();
			extraMap.put("orderId", orderId);
			extraMap.put("type", PushStateEnum.支付完成.toString());
			JpushUtil.sendNoticeAlias(JpushFactory.getDriverJpush(), "线上支付成功",
					extraMap, order.getDriverId().toString());
			renderJsonError(RequestNormal, "");
		}
		
	}
	
	/**
	 * 查询订单
	 * @author wh
	 * @since 2016-12-16
	 */
	public void findOrders(){
		if(checkPara("userId", "type")){
			String userId = getPara("userId");
			String type = getPara("type");
			List<Order> orders = Order.dao.findUserOrder(userId, type);
			renderJsonError(RequestNormal, orders);
		}
	}
	
	/**
	 * 删除订单,只有完成的订单才可以删除
	 * @author wh
	 * @since 2016-12-15
	 */
	@Before(Tx.class)
	public void deleteOrder(){
		if(checkPara("orderId", "type")){
			String[] orderIds = getParaValues("orderId");
			String type = getPara("type");
			if(Order.dao.delUserOrder(orderIds, type)){
				renderJsonError(RequestNormal, "删除成功");
			}else{
				renderJsonError(RequestError, "删除失败");
			}
		}
	}
	
	/**
	 * 用户取消订单,订单没有完成的时候用户或司机取消订单
	 * @author wh
	 * @since 2016-12-28
	 */
	@Before(Tx.class)
	public void cancelOrder(){
		if(checkPara("orderId", "userId")){
			Integer orderId = getParaToInt("orderId");
			Order order = Order.dao.findById(orderId);
			Integer orderState = order.getOrderState();
			if(orderState == OrderStateEnum.下单.ordinal()){
				order.delete();
				renderJsonError(RequestNormal, "取消订单成功");
				return;
			}
			
			if(orderState == OrderStateEnum.已接单.ordinal()){
				order.setUserDeleteState(UserDeleteEnum.已删除.ordinal());
				order.setDriverDeleteState(DriverDeleteEnum.已删除.ordinal());
				order.update();
				
				// 添加拒绝记录
				CancelOrder cancelOrder = new CancelOrder();
				cancelOrder.setCancelOrderId(null);
				cancelOrder.setOrderId(orderId);
				cancelOrder.setUserId(getParaToInt("userId"));
				cancelOrder.save();
				renderJsonError(RequestNormal, "取消订单成功");
			}
		}
	}
	
	
	
	@Test
	public void test(){
		double a = (28 - DayDistanceGradeOne) / 5;
		System.out.println(Math.ceil(a));
		
		JpushUtil.sendNoticeAlias(JpushFactory.getDriverJpush(), "您已修改目的地", 
				"8");
	}
	
	@Test
	public void test2() throws Exception{
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date startTime = sdf.parse("2016-12-15 10:9:11");
//		long l = new Date().getTime() - startTime.getTime();
//		System.out.println(l / 1000 / 60);
//		System.out.println(1/2);
		
		// long l = ToolUtils.countDistance(117.25498, 117.25498, 117.253914, 31.854084);
		long l = 345;
		double d = Double.valueOf(l);
		System.out.println(d);
		System.out.println(d/1000);
		
	}
	
	
	public void threadTest(){
		System.out.println("线程测试");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("5秒之后");
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
