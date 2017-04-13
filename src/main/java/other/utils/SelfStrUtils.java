package other.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 字符串操作类
 * @author Janesen
 *
 */
public class SelfStrUtils {

	 /**
     * 格式化用户手机号码
     * @param userPhone
     * @return
     */
    public static String formatePhone(String userPhone) {
        if (userPhone != null) {
        	if(userPhone.length()<7){
        		return userPhone;
        	}
            userPhone = userPhone.replaceAll(userPhone.substring(3, 7), "****");
            return userPhone;
        }
        return userPhone;
    }

    
    
    /**
     * 格式化日期，
     * @param dateTime
     * @return
     */
	public static String formatDateTime(String dateTime){
        if(dateTime==null||dateTime.equals("null"))return "";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Calendar cd=Calendar.getInstance();
		try {
			cd.setTime(sdf.parse(dateTime));

			long minu=(System.currentTimeMillis()-cd.getTimeInMillis())/1000/60;

			if(minu==0){
				return "刚刚";
			}

			if(minu<60&&minu>0){

				return minu+"分钟前";
			}else{
				if(minu<12*60&&minu>0){//12个小时内
					return minu/60+"小时"+minu%60+"分钟前";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateTime;
	}


	/**
     * 格式化日期，
     * @param dateTime
     * @return
     */
	public static String formatDate(String dateTime){
        if(dateTime==null||dateTime.equals("null"))return "";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Calendar cd=Calendar.getInstance();
			cd.setTime(sdf.parse(dateTime));
			Calendar now=Calendar.getInstance();
			if(cd.get(Calendar.DATE)==now.get(Calendar.DATE)){
				return "今天";
			}else if(cd.get(Calendar.DATE)==now.get(Calendar.DATE)-1){
				return "昨天";
			}else if(cd.get(Calendar.DATE)==now.get(Calendar.DATE)-2){
				return "前天";
			}
			return sdf.format(sdf.parse(dateTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateTime;
	}


	/**
     * 格式化日期，
     * @param dateTime
     * @return
     */
	public static String formatDateMinute(String dateTime){
        if(dateTime==null||dateTime.equals("null"))return "";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			SimpleDateFormat sdf2=new SimpleDateFormat("HH:mm");
			Calendar cd=Calendar.getInstance();
			cd.setTime(sdf.parse(dateTime));
			Calendar now=Calendar.getInstance();
			if(cd.get(Calendar.DATE)==now.get(Calendar.DATE)){
				return "今天"+sdf2.format(cd.getTime());
			}else if(cd.get(Calendar.DATE)==now.get(Calendar.DATE)-1){
				return "昨天"+sdf2.format(cd.getTime());
			}else if(cd.get(Calendar.DATE)==now.get(Calendar.DATE)-2){
				return "前天"+sdf2.format(cd.getTime());
			}
			return sdf.format(sdf.parse(dateTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateTime;
	}

	/**
	 * 插入字符串
	 * @param string
	 * @param position
	 * @param insert
	 * @return
	 */
	public static String insertStr(String string ,int position,String insert){
		if(position<0){
			return string+insert;
		}
		String char1=string.substring(0,position);
		String char2=string.substring(position);
		return char1+insert+char2;
	}
	
	
	/**
	 * 插入字符串
	 * @param string
	 * @param position
	 * @param insert
	 * @return
	 */
	public static String insertStr(String string ,String split,String insert){
		return insertStr(string, string.indexOf(split), insert);
	}
	
}

