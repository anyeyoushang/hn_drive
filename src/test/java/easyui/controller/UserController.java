package easyui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;



import model.dao.Tree;
import model.dao.User;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import config.anno.Controller;
import controller.tool.BaseController;

/**
 * 用户
 * @Description 
 * 
 * @author wh
 * @version 1.0
 * @since 2016-11-23
 */
@Controller("/test/user")
public class UserController extends BaseController {
	
	public void login(){
		System.out.println("登陆");
		Record record = new Record();
		record.set("loginName", "白痴");
		renderJsonError(RequestNormal, "登陆成功", record);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void combobox(){
		System.out.println("aaa");
		Map<String, String> map0 = new HashMap<String, String>();
		map0.put("id", "0");
		map0.put("text", "嘿嘿");
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "1");
		map.put("text", "哈哈啊");
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("id", "2");
		map2.put("text", "呵呵啊");
		List list = new ArrayList();
		list.add(map0);
		list.add(map);
		list.add(map2);
		renderJson(list);
	}
	
	public void treeDate() throws Exception {
		List<ParentMenu> nl = new ArrayList<ParentMenu>();
		List<Tree> ts1 = Tree.dao.findByLevel(0);
		for(Tree ts : ts1){
			// 等级为0的菜单
			ParentMenu ptm1 = new ParentMenu();
			BeanUtils.copyProperties(ptm1, ts);
			
			// 查询该等级下的子菜单
			List<Tree> ts2 = Tree.dao.findLow(ts.getId());
			// 子菜单集合
			List<SonMenu> list = new ArrayList<SonMenu>();
			// 遍历子菜单
			for(Tree t : ts2){
				// 子菜单对象
				SonMenu tm2 = new SonMenu();
				BeanUtils.copyProperties(tm2, t);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("url", t.getUrl());
				tm2.setAttributes(map);
				
				list.add(tm2);
			}
			
			// 父菜单添加子菜单集合
			ptm1.setChildren(list);
			
			nl.add(ptm1);
		}
		renderJson(nl);
	}
	
	/*public void yhglDate(){
		Map<String, String> paramMap = changeParaMapToString(getParaMap());
		Integer pageNumber = Integer.valueOf(paramMap.get("page"));
		Integer pageSize = Integer.valueOf(paramMap.get("rows"));
		String sql = "select * from t_user";
		Page pageList = User.dao.getDataList(pageNumber, pageSize, sql);
		int total = pageList.getTotalRow();
		List rows = pageList.getList();
		Map<String, Object> pageData = new HashMap<String, Object>();
		pageData.put("total", total);
		pageData.put("rows", rows);
		renderJson(pageData);
	}*/
	
	
	
	
	
	
}
