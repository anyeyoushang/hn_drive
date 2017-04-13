package controller.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.dao.Tree;

import org.apache.commons.beanutils.BeanUtils;

import config.anno.Controller;
import controller.tool.BaseController;
import easyui.controller.ParentMenu;
import easyui.controller.SonMenu;

@Controller("/sys/tree")
public class SysTreeController extends BaseController{
	
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
}
