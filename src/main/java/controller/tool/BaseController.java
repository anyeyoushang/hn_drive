package controller.tool;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.render.JsonRender;
/**
 * 基Controller，写了自定义的方法，以便共用
 * @author Janesen
 */
public class BaseController extends Controller {
	
	/**
	 * 请求正常 
	 */
	public final static int RequestNormal = 0;
	
	/**
	 * 请求错误 
	 */
	public final static int RequestError = -1;
	
	/**
	 * 系统错误 
	 */
	public final static int SystemError = -2;
	
	/**
	 * 会话失效
	 */
	public final static int LoginInvalid = -3;
	
	/**
	 * json响应的属性枚举
	 * @author Janesen
	 */
	public enum AttrEnum {
		error, message, data, id, text, success
	}
	
	/**
	 * 检查是否含有参数,如果不存在将自动返回
	 * @param name
	 */
	public final boolean checkPara(String...name){
		for (String string : name) {
			// isParaExists(string)是否包含该参数,isParaBlank(string)该参数的值为null或者去除前后空格后长度为0
			if(!isParaExists(string)||isParaBlank(string)){
				renderJsonError(RequestError, "参数"+string+"不可为空！");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 检查是否含有参数,如果不存在将自动返回
	 * @param name
	 */
	public final boolean checkParaNot(String [] name,String...notName){
		for (String string : name) {
			if(!isParaExists(string)||isParaBlank(string)){
				for(String not:notName){
					if(string.equals(not))
						return true;
				}
				renderJsonError(RequestError, "参数"+string+"不可为空！");
				return false;
			}
		}
		return true;
	}

	/**
	 * 响应错误信息 json格式
	 * @param errorCode 错误码 参看 @see com.self.entity.ResponeConfig
	 * @param data 将要转成json字符串的对象
	 */
	public final void renderJsonError(int errorCode,String message,Object data,boolean forIE){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(AttrEnum.error.name(), errorCode);
		if(errorCode==0){
			map.put(AttrEnum.success.name(), true);
		}else{
			map.put(AttrEnum.success.name(), false);
		}
		map.put(AttrEnum.message.name(), message);
		map.put(AttrEnum.data.name(), data);
		
		if(forIE){
			render(new JsonRender(map).forIE());
		}else{
			renderJson(map);
		}
	}
	
	/**
	 * 返回对应错误码和信息的json
	 * @param errorCode 错误码
	 * @param message 消息
	 */
	public final void renderJsonError(int errorCode,String message){
		renderJsonError(errorCode, message,null,false);
	}

	/**
	 * 响应错误码和对象的json
	 * @param errorCode 错误码
	 * @param data 将要转成json字符串的对象
	 */
	public final void renderJsonError(int errorCode,Object data){
		renderJsonError(errorCode, "请求正常", data, false);
	}
	
	/**
	 * 响应错误信息 json格式
	 * @param errorCode 错误码 参看 @see com.self.entity.ResponeConfig
	 * @param data 将要转成json字符串的对象
	 */
	public final void renderJsonError(int errorCode,Object data,boolean forIE){
		renderJsonError(errorCode, "请求正常",data,forIE);
	}

	/**
	 * 响应错误信息 json格式
	 * @param errorCode 错误码 参看 @see com.self.entity.ResponeConfig
	 * @param data 将要转成json字符串的对象
	 */
	public final void renderJsonError(int errorCode,String message,Object data){
		renderJsonError(errorCode, message,data,false);
	}
	
	/**
	 * 获得新的参数map
	 * @author wh
	 * @since 2016-10-25
	 * @param map
	 * @return
	 */
	public final Map<String, Object> changeParaMap(Map<String, String[]> map) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for(String str : map.keySet()){
			// 如果值的数组长度大于1
			if(map.get(str).length > 1){
				// 直接put该数组
				paramMap.put(str, map.get(str));
			}else{
				paramMap.put(str, map.get(str)[0]);
			}
		}
		return paramMap;
	}
	
	/**
	 * 获得新的参数map
	 * @author wh
	 * @since 2016-10-25
	 * @param map
	 * @return
	 */
	public final Map<String, String> changeParaMapToString(Map<String, String[]> map) {
		Map<String, String> paramMap = new HashMap<String, String>();
		for(String str : map.keySet()){
			paramMap.put(str, map.get(str)[0]);
		}
		return paramMap;
	}

	/**
	 * 将参数转成指定类的对象
	 * @param prefix 参数的前缀 例如参数 user.name 的前缀是user
	 * @param targetClass 目标类型
	 * @return 返回目标类型的对象
	 */
	public final <T> T getObjectPara(String prefix,Class<T> targetClass){
		try {
			T target=targetClass.newInstance();
			String regStr="(\\w+)(\\.(\\w+))?";
			Enumeration<String> names=getParaNames();
			while(names.hasMoreElements()){
				String element=names.nextElement();
				if(getPara(element).isEmpty())continue;

				if(element.startsWith(prefix)){
					Matcher matcher=Pattern.compile(regStr).matcher(element);
					String key=null;
					if(matcher.find()){
						key=matcher.group(3);
					}

					if(key.startsWith("is"))key=key.replaceFirst("is", "");
					if(!StringUtils.isEmpty(key)){
						MethodUtils.invokeMethod(target, "set" + StrKit.firstCharToUpperCase(key), getPara(element));
					}
				}
			}
			return target;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	/**
	 *	将参数转成指定类的对象
	 * @param targetClass 目标类型
	 * @return 返回目标类型的对象
	 */
	public final <T> T getObjectPara(Class<T> targetClass){
		try {
			T target=targetClass.newInstance();
			Enumeration<String> names=getParaNames();
			while(names.hasMoreElements()){
				String key=names.nextElement();
				if(getPara(key).isEmpty())continue;
				if(key.startsWith("is"))key=key.replaceFirst("is", "");
				if(!StringUtils.isEmpty(key)){
					MethodUtils.invokeMethod(target, "set" + StrKit.firstCharToUpperCase(key), getPara(key));
				}
			}
			return target;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	/**
	 * 判断表单是否是附件类型的表单 multipart/form-data
	 * @return
	 */
	public final boolean checkIsMultipartRequest(){
		//System.out.println("ContentType:"+getRequest().getContentType());
		return getRequest().getContentType().indexOf("multipart/form-data")>=0;
	}
	
	/**
	 * 获取浏览器版本信息
	 * @return
	 */
	public String getBrowserName() {
		String agent=getRequest().getHeader("User-Agent").toLowerCase();
		if(agent.indexOf("msie 7")>0){
			return "ie7";
		}else if(agent.indexOf("msie 8")>0){
			return "ie8";
		}else if(agent.indexOf("msie 9")>0){
			return "ie9";
		}else if(agent.indexOf("msie 10")>0){
			return "ie10";
		}else if(agent.indexOf("msie")>0){
			return "ie";
		}else if(agent.indexOf("opera")>0){
			return "opera";
		}else if(agent.indexOf("opera")>0){
			return "opera";
		}else if(agent.indexOf("firefox")>0){
			return "firefox";
		}else if(agent.indexOf("webkit")>0){
			return "webkit";
		}else if(agent.indexOf("gecko")>0 && agent.indexOf("rv:11")>0){
			return "ie11";
		}else{
			return "Others";
		}
	}
	
	
	

}
