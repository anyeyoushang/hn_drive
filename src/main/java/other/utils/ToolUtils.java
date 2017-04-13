package other.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;

public class ToolUtils {
	private static double pi = 3.1415926535898;

	/**
	 * 通过反射获得该类的指定方法
	 * 
	 * @author wh
	 * @since 2016-10-25
	 * @param className 类名
	 * @param methodName 方法名
	 * @param paraType 参数类型实例
	 * @param param 参数
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object executeMethod(String className, String methodName, Object param) throws Exception{
			Class c = Class.forName(className);
			Object obj = c.getConstructor().newInstance();
			Method m1 = c.getMethod(methodName, param.getClass());
			Object o =  m1.invoke(obj, param);
			return o;
	}
	
	/**
	 * 上传文件
	 * @author wh
	 * @since 2016-10-25
	 * @return
	 * @throws Exception 
	 */
	public static String upload(UploadFile file) throws Exception {
		return upload(file, null, null);
	}
	
	/**
	 * 指定文件大小的上传
	 * @author wh
	 * @since 2016-11-23
	 * @param file
	 * @param object
	 * @throws Exception 
	 */
	public static String upload(UploadFile file, String path, Long fileLength) throws Exception {
		if(path == null){
			path = "headImg";
		}
		String saveName = null;
		String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		// 获得上传文件的名称
		String fileName = file.getFileName();
		
		// 处理文件名的局对路径问题
		int index = fileName.lastIndexOf("\\");
		if(index != -1){
			fileName = fileName.substring(index + 1);
		}
		// 处理文件名
		saveName = uuid + "_" + new Date().getTime() + fileName.substring(fileName.lastIndexOf("."));
		
		String contextPath = PropKit.use("config.properties").get("contextPath");
		String uploadPath = PropKit.use("config.properties").get("uploadPath");
		String root = PathKit.getWebRootPath().replaceFirst(contextPath, uploadPath + path);
		
		// 生成文件存放的文件夹
		File dirFile = new File(root);
		if(!dirFile.exists()){
			dirFile.mkdir();
		}
		// 数据库中保存图片的访问路径
		// 创建目录文件
		File destFile = new File(dirFile, saveName);
		
		// 写入该文件
		File f = file.getFile();
		// 判断文件大小是否符合要求
		// 文件大小没有限制; 有限制,在限制大小内
		if(fileLength == null || (fileLength != null && f.length() <= fileLength)){
			FileUtils.copyFile(f, destFile);
			return uploadPath + path + "/" + saveName;
		}else{// 有限制,在限制大小外
			return null;
		}
	}

	/**
	 * 判断是不是ajax请求
	 * @author wh
	 * @since 2016-11-8
	 * @param request
	 * @return true是,false不是
	 */
	public static boolean isAjax(HttpServletRequest request){
		String str = request.getHeader("X-Requested-With");
		return (str != null) ? true : false;
	}
	
	/**
	 * 生成UUID
	 * @author wh
	 * @since 2016-12-2
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 得到天数后的毫秒数
	 * @author wh
	 * @since 2016-11-8
	 * @param dayNum
	 * @return
	 */
	public static Long getMsecNum(int dayNum){
		return new Date().getTime() + 86400000 * dayNum;
	}
	
	public static String MsecToDateString(Long Msec){
		return MsecToDateString(Msec, null);
	}
	
	/**
	 * 把毫秒数转为date字符串
	 * @author wh
	 * @since 2016-11-9
	 * @param Msec
	 */
	public static String MsecToDateString(Long Msec, String format){
		if(format == null){
			format = "yyyy-MM-dd HH:mm:ss";
		}
		Date date = new Date(Msec);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 计算表中的经纬度的位置到实际经纬度位置的距离
	 * @author wh
	 * @since 2016-11-22
	 * @param longitude
	 * @param latitude
	 * @param longitudeName
	 * @param latitudeName
	 * @param resultName
	 * @return
	 */
	public static String yxPointSql(String longitude, String latitude,
			String longitudeName, String latitudeName, String resultName) {
		String sql = " round(6378.138*2*asin(sqrt(pow(sin( (" + latitude
				+ "*pi()/180-" + latitudeName + "*pi()/180)/2),2)+cos(" + latitude
				+ "*pi()/180)*cos(" + latitudeName + "*pi()/180)* pow(sin( ("
				+ longitude + "*pi()/180-" + longitudeName
				+ "*pi()/180)/2),2)))*1000) as " + resultName + " ";
		return sql;
	}
	
	/**
	 * 计算两个经纬度之间的距离
	 * @author wh
	 * @since 2016-12-2
	 * @param startLon
	 * @param startLat
	 * @param endLon
	 * @param endLat
	 * @return 距离(米)
	 */
	public static Double countDistance(double startLon, double startLat, double endLon, double endLat){
		long distance =  Math.round(6378.138*2*Math.asin(Math.sqrt(Math.pow(Math.sin((endLat*pi/180-startLat
				*pi/180)/2),2)+Math.cos(endLat*pi/180)*Math.cos(startLat*pi/180)*Math.pow(Math.sin
				((endLon*pi/180-startLon*pi/180)/2),2)))*1000);
		System.out.println("两地之间的距离" + distance);
		// 返回double类型
		return Double.valueOf(distance);
	}
	
	/**
	 * 生成随机数
	 * @author wh
	 * @since 2016年11月28日
	 * @param num
	 * @return
	 */
	public static String randomNumber(Integer num){
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < num; i++){
			sb.append(random.nextInt(10));
		}
		return sb.toString();
		
	}
}










