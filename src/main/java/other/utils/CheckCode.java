package other.utils;
/*
 * @(#) CommonUtil.java 2016年4月19日
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class CheckCode {
	private static final String textMessageTemplate="【佳伟代驾】验证码为：@,有效时间30分钟。";
	private static final String textMessaeName="daijia2016";
	private static final String textMessagePassWord="1C8E70B4C8EB3E3700FC70ED43E7";
	
	public static String sendTextMessage(String code,String phone){
		try {	
			// 创建StringBuffer对象用来操作字符串
			StringBuffer sb = new StringBuffer("http://web.cr6868.com/asmx/smsservice.aspx?");

			// 向StringBuffer追加用户名
			sb.append("name="+textMessaeName.trim());

			// 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
			sb.append("&pwd="+textMessagePassWord.trim());

			// 向StringBuffer追加手机号码
			sb.append("&mobile="+phone);

			// 向StringBuffer追加消息内容转URL标准码
			sb.append("&content="+URLEncoder.encode(textMessageTemplate.replaceAll("@",code),"UTF-8"));
			
			//追加发送时间，可为空，为空为及时发送
			sb.append("&stime=");
			
			//加签名
			//sb.append("&sign="+URLEncoder.encode("嗖租","UTF-8"));
			
			//type为固定值pt  extno为扩展码，必须为数字 可为空
			sb.append("&type=pt&extno=");
			// 创建url对象
			//String temp = new String(sb.toString().getBytes("GBK"),"UTF-8");
			System.out.println("sb:"+sb.toString());
			URL url = new URL(sb.toString());

			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");

			// 发送
			InputStream is =url.openStream();

			//转换返回值
			String returnStr = convertStreamToString(is);
			
			// 返回结果为‘0，20140009090990,1，提交成功’ 发送成功   具体见说明文档
			return returnStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
		
	}
	
	/**
	 * 转换返回值类型为UTF-8格式.
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
        StringBuilder sb1 = new StringBuilder();    
        byte[] bytes = new byte[4096];  
        int size = 0;  
        try {    
        	while ((size = is.read(bytes)) > 0) {  
                String str = new String(bytes, 0, size, "UTF-8");  
                sb1.append(str);  
            }  
        } catch (IOException e) {    
            e.printStackTrace();    
        } finally {    
            try {    
                is.close();    
            } catch (IOException e) {    
               e.printStackTrace();    
            }    
        }    
        return sb1.toString();    
    }
	
	
	public static void main(String[] args) {
		String data = sendTextMessage("2345", "15755175415");
		int is = Integer.valueOf(data.split(",")[0]);
		if (is==0) {
			System.out.println("success");
		}else{
			System.out.println("fail");
		}
	}
	
}
