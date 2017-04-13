package controller.sys;
import java.util.Map;

import other.utils.ToolUtils;

import config.anno.Controller;
import controller.tool.BaseController;

@Controller("/sys/position")
public class SysDriverPositionController extends BaseController{
	
	/**
	 * 显示数据
	 * @author wh
	 * @since 2017-1-5
	 */
	@SuppressWarnings("unchecked")
	public void showDataList(){
		try {
			if(checkPara("page", "rows")){
				// 获得参数map
				Map<String, Object> paramMap = changeParaMap(getParaMap());
				// 获得className
				String className = (String) paramMap.get("className");
				paramMap.remove("className");
				Map<String, Object> pageData = (Map<String, Object>) ToolUtils.executeMethod(className, "getDataList", paramMap);
				renderJson(pageData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		java.lang.Double a = 1.22;
		System.out.println(String.valueOf(a));
	}
	
}
