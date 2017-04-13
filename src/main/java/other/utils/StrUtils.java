package other.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class StrUtils {
	

	

	
	
	public static String dateFormate="yyyy-MM-dd";
	
	

	
	
	
	
	/**
	 * 格式化时间
	 * @param formate
	 * @param date
	 * @return
	 */
	public static String getDate(String formate,Date date){
		
		SimpleDateFormat sp=new SimpleDateFormat(formate);
		return sp.format(date);
		
		
	}
	
	/**
	 * 判断一个字符串是否为空
	 * 
	 * @author mn.qiao
	 * @since 2015年7月27日
	 * @param str
	 * @return
	 */
	public static boolean isNull(Object str) {
		try {
			if (str == null)
				return true;
			else if ("".equals(str.toString().trim())||"null".equals(str.toString().trim()))
				return true;
			else
				return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return true;
		}
	}
	
	
	
	/**
	 * 判断一个数组里的字符串是否为空
	 * 
	 * @author mn.qiao
	 * @since 2015年7月27日
	 * @param strs
	 * @return
	 */
	public static boolean isNull(Object[] strs) {
		try {
			if (strs == null || strs.length <= 0)
				return true;
			else {
				boolean isRight = false;
				for (Object str : strs) {
					if (str == null) {
						isRight = true;
						break;
					} else if ("".equals(str.toString().trim())) {
						isRight = true;
						break;
					}

				}
				return isRight;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return true;
		}
	}
	
	

	/**
	 * 返回经纬度的sql字符串 单位米
	 * 
	 * @author mn.qiao
	 * @since 2015年11月29日
	 * @param ypoint
	 *            经度
	 * @param xpoint
	 *            纬度
	 * @param yPointName
	 *            经度列名
	 * @param xPointName
	 *            纬度列名
	 * @param resultName
	 *            结果名
	 * @return
	 */
	public static String yxPointSql(String ypoint, String xpoint,
			String yPointName, String xPointName, String resultName) {
		String sql = " round(6378.138*2*asin(sqrt(pow(sin( (" + xpoint
				+ "*pi()/180-" + xPointName + "*pi()/180)/2),2)+cos(" + xpoint
				+ "*pi()/180)*cos(" + xPointName + "*pi()/180)* pow(sin( ("
				+ ypoint + "*pi()/180-" + yPointName
				+ "*pi()/180)/2),2)))*1000) as " + resultName + " ";
		return sql;
	}
	
	public static String yxPointSql(String ypoint, String xpoint,
			String yPointName, String xPointName) {
		String sql = " round(6378.138*2*asin(sqrt(pow(sin( (" + xpoint
				+ "*pi()/180-" + xPointName + "*pi()/180)/2),2)+cos(" + xpoint
				+ "*pi()/180)*cos(" + xPointName + "*pi()/180)* pow(sin( ("
				+ ypoint + "*pi()/180-" + yPointName
				+ "*pi()/180)/2),2)))*1000)  ";
		return sql;
	}
	
	
	
	
	public static String sqlInVal(Object[] array) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = 0; i < array.length; i++) {
			sb.append("'" + array[i] + "',");
		}
		sb.deleteCharAt(sb.length() - 1);//这句是去掉sb最后的那个("，")逗号
		sb.append(")");
		return sb.toString();
	}
	
	
	
	
}
