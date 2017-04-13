package controller.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import other.utils.ToolUtils;

import model.dao.Comment;
import model.dao.DriverPosition;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import config.anno.Controller;
import controller.tool.BaseController;



/**
 * 司机位置
 * @Description 
 * @author wh
 * @version 1.0
 * @since 2016-11-23
 */
@Controller("/mobile/position")
public class DriverPositionController extends BaseController {
	// 所查询的附近的司机的距离限制(米)
	private final Integer distance = 80000;
	/**
	 * 司机没有接单实时保存位置
	 * @author wh
	 * @since 2016-12-2
	 */
	public void savePosition(){
		// 接单前的保存位置,只保存一条
		if(checkPara("driverId", "lon", "lat")){
			Map<String, Object> params = changeParaMap(getParaMap());
			String driverId = getPara("driverId");
			// 判断位置表是否有该司机的记录
			DriverPosition driverPosition = DriverPosition.dao.findPositionRecord(driverId);
			if(driverPosition == null){// 添加记录
				DriverPosition.dao.addDriverPosition(params);
			}else{// 修改记录
				DriverPosition.dao.updateDriverPosition(params);
			}
			System.out.println("=======添加位置成功=======" + new Date());
			renderJsonError(RequestNormal, "添加位置成功!");
		}
	}
	
	/**
	 * 查询附近的司机 
	 * @author wh
	 * @since 2016-11-23
	 */
	public void findNearDriver(){
		if (checkPara("lon", "lat")) {
			String lon = getPara("lon");// 经度
			String lat = getPara("lat");// 纬度
			List<Record> list = this.findNearDriver(lon, lat);
			renderJsonError(RequestNormal, list);
		}
	}
	
	/**
	 * 查询指定距离的附近的司机
	 * @author wh
	 * @since 2016-12-13
	 * @param lon
	 * @param lat
	 * @return
	 */
	public List<Record> findNearDriver(String lon, String lat){
		return DriverPosition.dao.findNearDriver(lon, lat, distance);
	}

	/**
	 * 查询符合距离评分要求的司机
	 * @author wh
	 * @since 2016-12-22
	 * @param lon 经度
	 * @param lat 纬度
	 * @param distance 距离
	 * @param grade 评分
	 * @return
	 */
	public List<String> findBestDriver(String lon, String lat, int startDistance, int endDistance, double startGrade, double endGrade) {
		StringBuffer sql = new StringBuffer();
		sql.append("select");
		sql.append(ToolUtils.yxPointSql(lon, lat, "lon", "lat", "distance"));
		sql.append(",tu.userId as driverId from t_driver_position AS t LEFT JOIN t_user AS tu ON t.driverId = tu.userId where tu.AcceptState = 0 and tu.userMoney >= 100");
		sql.append(" HAVING distance >= ? AND distance <= ?");
		// 查询符合距离要求的司机
		List<Record> records = Db.find(sql.toString(), startDistance, endDistance);
		List<String> pushDrivers = new ArrayList<String>();
		Double score = null;
		for(int i = 0; i < records.size(); i++){
			// 该司机的评分
			score = Comment.dao.findDriverGrade(records.get(i).get("driverId").toString());
			// 评分符合要求
			if(score >= startGrade && score <= endGrade){
				pushDrivers.add(records.get(i).get("driverId").toString());
			}
		}
		return pushDrivers;
	}
	
	
	public static void main(String[] args) throws Exception {
//		Date date1 = new Date();
//		long time1 = date1.getTime() / 1000;// 开始计时
//		long time3 = date1.getTime() / 1000;// 开始计时
//		// 循环计时
//		Integer batch = 1;// 批次
//		while(true){
//			Date date2 = new Date();
//			long time2 = date2.getTime() / 1000;
//			long time4 = date2.getTime() / 1000;
//			if((time2 - time1) >= 1){
//				System.out.println("1秒执行一次");
//				time1 = new Date().getTime() / 1000;
//			}
//			
//			if((time4-time3) >= 3){
//				System.out.println("3秒循环一次");
//				time3 = new Date().getTime() / 1000;
//			}
//			
//			//Thread.sleep(2000);
//		}
		
		List<String> pushDrivers = null;
		pushDrivers.add("1");
	}
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
}
