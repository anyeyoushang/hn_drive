package controller.sys;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import other.utils.ToolUtils;

import model.dao.Admin;
import model.dao.User;
import model.dao.User.UserRoleEnum;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.tx.Tx;

import config.anno.Controller;
import controller.tool.BaseController;

@Controller("/sys/user")
public class SysUserController extends BaseController{
	
	/**
	 * 后台登陆 
	 * @author wh
	 * @since 2016-11-17
	 */
	@Clear
	public void backLogin(){
		Map<String, String> paramsMap = changeParaMapToString(getParaMap());
		String userName = paramsMap.get("userName");
		String passWord = paramsMap.get("password");
		Admin admin = Admin.dao.findById("1");
		if(userName.equals(admin.getUserName()) && passWord.equals(admin.getPassword())){
			getRequest().getSession().setAttribute("adminInfo", admin);
			renderJsonError(RequestNormal, "");
		}else{
			renderJsonError(RequestError, "");
		}
	}
	
	/**
	 * 修改管理员信息
	 * @author wh
	 * @since 2017-1-3
	 */
	@Before(Tx.class)
	public void modifyAdminInfo(){
		Admin admin = Admin.dao.findById("1");
		admin.setUserName(getPara("userName"));
		admin.setPassword(getPara("password"));
		if(admin.update()){
			renderJsonError(RequestNormal, "修改成功");
		}else{
			renderJsonError(RequestError, "修改失败");
		}
	}
	
	/**
	 * 后台管理员退出登录 
	 * @author wh
	 * @since 2016-11-18
	 */
	public void quitLogin(){
		getSession().removeAttribute("adminInfo");
		renderJsonError(RequestNormal, "");
	}
	
	
	/**
	 * 删除用户
	 * @author wh
	 * @since 2016-11-10
	 */
	@Before(Tx.class)
	public void deleteUser(){
		Map<String, String> parasMap = changeParaMapToString(getParaMap());
		String ids = parasMap.get("ids");
		// 1,4,
		System.out.println(ids);
		String[] userIds = ids.split(",");
		
		for(String userId : userIds){
			User.dao.deleteById(userId);
		}
		renderJsonError(RequestNormal, "删除成功!");
	}
	
	
	public static void main(String[] args) {
		String str = "1,4,";
		String[] s = str.split(",");
		for(String a : s){
			System.out.println(a);
		}
	}
	
	
	/**
	 * 判断是否是ajax请求
	 * @author wh
	 * @since 2016年11月29日
	 * @param request
	 * @return
	 */
	public boolean isAjax(HttpServletRequest request){
		String requestType = request.getHeader("X-Requested-With"); 
		if (requestType == null) {
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 修改司机的数据 
	 * @author wh
	 * @since 2016-11-16
	 */
	@Before(Tx.class)
	public void updateUser(){
		try {
			Map<String, Object> paramsMap = changeParaMap(getParaMap());
//			{headImg=hn_driveFile/headImg/E504EE3FD6984674854BEB19D67DFB09_1481603194599.jpg,
//			userId=9, userName=司机0310, userPhone=18655450310, userRole=2, AcceptState=0,
//			IDcard=342423199209051234, userMoney=200, addTime=2016-12-08 12:30:42}
			User user = User.dao.findById(paramsMap.get("userId"));
			for(String field : paramsMap.keySet()){
				user.set(field, paramsMap.get(field));
			}
			user.update();
			renderJsonError(RequestNormal, "修改数据成功!");
		} catch (Exception e) {
			e.printStackTrace();
			renderJsonError(RequestError, "修改数据失败!");
		}
	}
	
	
	/**
	 * 
	 * @author wh
	 * @since 2016-11-17
	 */
	public void jumpBack(){
		redirect("/background/index.jsp");
	}
	
	/**
	 * 后台添加司机
	 * @author wh
	 * @since 2017-1-6
	 */
	@Before(Tx.class)
	public void addDrive(){
		if(checkPara("userName", "userPhone", "IDcard")){
			Map<String, Object> paramMap = changeParaMap(getParaMap());
			String userPhone = paramMap.get("userPhone").toString();
			String IDcard = paramMap.get("IDcard").toString();
			
			if(!IDcard.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$")){
				renderJsonError(RequestError, "身份证格式不正确!");
				return;
			}
			
			// 判断该司机是否已注册
			User user = User.dao.findDriveByUserPhone(userPhone);
			if(user == null){// 没有注册
				User newDrive = new User();
				newDrive.setUserId(null);
				newDrive.put(paramMap);
				newDrive.setUserRole(UserRoleEnum.司机.ordinal());
				newDrive.save();
				renderJsonError(RequestNormal, "注册成功!");
			}else{
				renderJsonError(RequestError, "该司机已注册!");
			}
		}
		
		
	}
	
	
	
}
