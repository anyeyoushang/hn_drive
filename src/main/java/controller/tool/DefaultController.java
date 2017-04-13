package controller.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import other.utils.ToolUtils;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;

import config.anno.Controller;

/**
 * 后台的controller,封装了后台的增删改查
 * @Description 
 * @author wh
 * @version 1.0
 * @since 2016-10-10
 */
@Controller("/")
public class DefaultController extends BaseController {
	
	public static void main(String[] args) {
		String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		System.out.println(uuid);
	}
	
	/**
	 * 后台的登陆
	 * @author wh
	 * @since 2016年11月29日
	 */
	public void index(){
		renderJsp("/background/backLogin.jsp");
	}
	
	/**
	 * 添加数据
	 * @author wh
	 * @since 2017-1-6
	 */
	@Before(Tx.class)
	public void addData() {
		try {
			Map<String, Object> paramMap = changeParaMap(getParaMap());
			// 获得className
			String className = (String) paramMap.get("className");
			paramMap.remove("className");
			ToolUtils.executeMethod(className, "addDate", paramMap);
			
			renderJsonError(RequestNormal, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加数据
	 * @author wh
	 * @since 2017-1-6
	 */
	@Before(Tx.class)
	public void deleteData() {
		try {
			Map<String, Object> paramMap = changeParaMap(getParaMap());
			// 获得className
			String className = (String) paramMap.get("className");
			paramMap.remove("className");
			ToolUtils.executeMethod(className, "getDataList", paramMap);
			
			renderJsonError(RequestNormal, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 后台修改数据
	 * 
	 * @author wh
	 * @since 2016-10-25
	 */
	@Before(Tx.class)
	public void updateData(){
		try {
			Map<String, Object> paramMap = changeParaMap(getParaMap());
			// 获得className
			String className = (String) paramMap.get("className");
			paramMap.remove("className");
			ToolUtils.executeMethod(className, "getDataList", paramMap);
			
			renderJsonError(RequestNormal, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询填充datagrid的数据 
	 * @author wh
	 * @return 
	 * @since 2016-10-7
	 */
	@SuppressWarnings("rawtypes")
	public void showDataList(){
		try {
			if(checkPara("page", "rows")){
				// 获得参数map
				Map<String, Object> paramMap = changeParaMap(getParaMap());
				// 获得className
				String className = (String) paramMap.get("className");
				paramMap.remove("className");
				Page pageList = (Page) ToolUtils.executeMethod(className, "getDataList", paramMap);
				int total = pageList.getTotalRow();
				List rows = pageList.getList();
				Map<String, Object> pageData = new HashMap<String, Object>();
				pageData.put("total", total);
				pageData.put("rows", rows);
				renderJson(pageData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void addInfo(UploadFile file) throws Exception {
//		if(checkPara("name", "age", "type")){
//			// 获得参数map
//			Map<String, Object> paramMap = changeParaMap(getParaMap());
//			// 获得className
//			String className = pack + (String) paramMap.get("className");
//			paramMap.remove("className");
//			
//			// 文件不为空就上传
//			if(file != null){
//				String saveName = ToolUtils.upload(file);
//				paramMap.put("markPic", saveName);
//			}
//			// 调用该service中的getDataList方法
//			ToolUtils.executeMethod(className, 
//					backExecuteEnum.addData.toString(), paramMap);
//			renderJsonError(RequestNormal, "添加成功!");
//		}else{
//			throw new RuntimeException();
//		}
	}
	
	
	
	

	
	
	
	

	
	
	
	
}
