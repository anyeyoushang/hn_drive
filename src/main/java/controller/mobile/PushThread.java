package controller.mobile;

import java.util.List;
import java.util.Map;

import model.dao.Order;
import model.dao.Order.DriverDeleteEnum;
import model.dao.Order.UserDeleteEnum;

import other.jpush.JpushFactory;
import other.jpush.JpushUtil;

public class PushThread extends Thread {
	private List<String> pushDrivers;
	private List<String> pushDrivers2;
	private List<String> pushDrivers3;
	private List<String> pushDrivers4;
	private List<String> pushDrivers5;
	private Map<String, String> extraMap;
	
	public PushThread(List<String> pushDrivers, List<String> pushDrivers2,
			List<String> pushDrivers3, List<String> pushDrivers4,
			List<String> pushDrivers5, Map<String, String> extraMap) {
		super();
		this.pushDrivers = pushDrivers;
		this.pushDrivers2 = pushDrivers2;
		this.pushDrivers3 = pushDrivers3;
		this.pushDrivers4 = pushDrivers4;
		this.pushDrivers5 = pushDrivers5;
		this.extraMap = extraMap;
	}

	@Override
	public void run() {
		try {
			for(String driverId : pushDrivers){
				System.out.println("推送"+ driverId + "号司机");
				JpushUtil.sendNoticeAlias(JpushFactory.getDriverJpush(), "附近有新订单",
						extraMap, driverId);
			}
			
			if(pushDrivers2.size() > 0){
				Thread.sleep(2000);
				if(orderIsCancel()){
					return;
				}
				for(String driverId : pushDrivers2){
					System.out.println("推送"+ driverId + "号司机");
					JpushUtil.sendNoticeAlias(JpushFactory.getDriverJpush(), "附近有新订单",
							extraMap, driverId);
				}
			}
			
			if(pushDrivers3.size() > 0){
				Thread.sleep(3000);
				if(orderIsCancel()){
					return;
				}
				for(String driverId : pushDrivers3){
					System.out.println("推送"+ driverId + "号司机");
					JpushUtil.sendNoticeAlias(JpushFactory.getDriverJpush(), "附近有新订单",
							extraMap, driverId);
				}
			}
			
			if(pushDrivers4.size() > 0){
				Thread.sleep(4000);
				if(orderIsCancel()){
					return;
				}
				for(String driverId : pushDrivers2){
					System.out.println("推送"+ driverId + "号司机");
					JpushUtil.sendNoticeAlias(JpushFactory.getDriverJpush(), "附近有新订单",
							extraMap, driverId);
				}
			}
			
			if(pushDrivers5.size() > 0){
				Thread.sleep(5000);
				for(String driverId : pushDrivers2){
					System.out.println("推送"+ driverId + "号司机");
					JpushUtil.sendNoticeAlias(JpushFactory.getDriverJpush(), "附近有新订单",
							extraMap, driverId);
				}
			}
			Thread.sleep(600000);
			if(orderIsCancel()){
				return;
			}
			// 没有被接单
			Order order = Order.dao.findById(extraMap.get("orderId"));
			if(order.getOrderState() == 0){
				order.setUserDeleteState(UserDeleteEnum.已删除.ordinal());
				order.setDriverDeleteState(DriverDeleteEnum.已删除.ordinal());
				order.update();
				JpushUtil.sendNoticeAlias(JpushFactory.getUserJpush(), "您的订单已超时",
						order.getUserId().toString());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("线程被终止了");
		}
	}
	
	
	
	public boolean orderIsCancel(){
		String orderId = extraMap.get("orderId");
		if(Order.dao.findById(orderId) == null){
			// 订单已被取消
			return true;
		}
		// 订单没有被取消
		return false;
	}
	
}





