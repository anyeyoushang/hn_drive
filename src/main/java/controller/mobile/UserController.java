package controller.mobile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.dao.ChangePhone;
import model.dao.Comment;
import model.dao.User;
import model.dao.User.CodeTypeEnum;
import model.dao.User.UserRoleEnum;
import other.utils.CheckCode;
import other.utils.ToolUtils;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;

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
@Controller("/mobile/user")
public class UserController extends BaseController {
	// 存放该手机对应的验证码
	public static Map<String, Map<Integer, String>> pcode = new HashMap<String, Map<Integer, String>>();

	/**
	 * 发送短信验证码
	 * codeType: 0:注册登陆, 1,更换手机号
	 * @author wh
	 * @since 2016-11-23
	 */
	public void sendTextMessage(){
		if (checkPara("phone", "codeType")) {
			String phone = getPara("phone");
			Integer codeType = getParaToInt("codeType");
			// 查询该手机号的用户
			User user = User.dao.findUser(phone);
			
			// 更换手机号但是用户不存在
			if(user == null && (codeType == CodeTypeEnum.更换手机号.ordinal())){
				renderJsonError(RequestError, "该用户不存在!");
				return;
			}
			
			String code=ToolUtils.randomNumber(4);
			// 发送验证码
			String data = CheckCode.sendTextMessage(code,phone);
			int is = Integer.valueOf(data.split(",")[0]);
			if (is==0) {
				//保存验证码
				Map<Integer, String> map = new HashMap<Integer, String>();
				map.put(codeType, code);
				pcode.put(phone, map);
				System.out.println("验证码为============================"+code);
				renderJsonError(RequestNormal, "验证码发送成功");
			}else{
				renderJsonError(SystemError, "发送验证码失败");
			}
			
			//保存验证码
//			Map<Integer, String> map = new HashMap<Integer, String>();
//			map.put(codeType, code);
//			pcode.put(phone, map);
//			System.out.println("验证码为============================"+code);
//			renderJsonError(RequestNormal, "验证码发送成功");
		}
	}
	
	/**
	 * 用户登陆
	 * @author wh
	 * @since 2016-11-22
	 */
	public void login(){
		if (checkPara("userPhone", "code", "userRole")) {
			// 判断该用户在数据库中是否有记录
			String userPhone = getPara("userPhone");
			// 1用户登陆,2司机登陆
			Integer userRole = getParaToInt("userRole");
			// 查询该用户的登陆的验证吗
			String code = null;
			if(pcode.containsKey(userPhone)){
				Map<Integer, String> map = pcode.get(userPhone);
				if(map.containsKey(CodeTypeEnum.注册登陆.ordinal())){
					code = map.get(CodeTypeEnum.注册登陆.ordinal());
				}
			}
			
			// 验证码不存在或者错误
			if(code == null || !code.equals(getPara("code"))){
				renderJsonError(RequestError, "验证码错误!");
				return;
			}
			
			// 验证码正确,移除验证码
			pcode.remove(userPhone);
			User result = User.dao.findUserByUserPhone(userPhone);
			if(result == null){// 注册
				User user = new User();
				user.setUserId(null);
				user.setUserPhone(userPhone);
				user.setUserRole(userRole);
				if(userRole == UserRoleEnum.用户.ordinal()){
					user.setUserName("用户" + userPhone.substring(userPhone.length() - 4));
				}else{
					user.setUserName("司机" + userPhone.substring(userPhone.length() - 4));
				}
				user.save();
				User responseUser = User.dao.findUserByUserPhone(userPhone);
				setSessionAttr("user", responseUser);
				renderJsonError(RequestNormal, responseUser);
			}else{// 登陆
				if(userRole == result.getInt("userRole")){
					setSessionAttr("user", result);
					renderJsonError(RequestNormal, result);
				}else{
					renderJsonError(RequestError, "请使用正确的用户端登录!");
				}
			}
		}
	}
	
	/**
	 * 更换手机号
	 * @author wh
	 * @since 2016-11-22
	 */
	@Before(Tx.class)
	public void changeUserPhone(){
		if (checkPara("oldUserPhone", "newUserPhone", "code")) {
			String oldUserPhone = getPara("oldUserPhone");
			String newUserPhone = getPara("newUserPhone");
			String code = null;
			if(pcode.containsKey(oldUserPhone)){
				Map<Integer, String> map = pcode.get(oldUserPhone);
				if(map.containsKey(CodeTypeEnum.更换手机号.ordinal())){
					code = map.get(CodeTypeEnum.更换手机号.ordinal());
				}
			}
			if(code == null || !code.equals(getPara("code"))){
				renderJsonError(RequestError, "您输入的验证码有误!");
				return;
			}
			pcode.remove(oldUserPhone);
			Integer userId = User.dao.findUserByUserPhone(oldUserPhone).getUserId();
			if(isChangePhone(userId)){
				// 给该用户更换手机号
				if(User.dao.changeUserPhone(oldUserPhone, newUserPhone)){
					// 添加更换手机号的记录
					ChangePhone.dao.addRecord(userId);
					renderJsonError(RequestNormal, "修改手机号码成功!");
				}else{
					renderJsonError(RequestError, "修改手机号码失败!");
				}
			}else{
				renderJsonError(RequestError, "您已经修改过手机号了!");
			}
			
		}
	}
	
	/**
	 * 判断该用户3个月只能是否更换过手机号
	 * @author wh
	 * @since 2016-12-8
	 * @param oldUserPhone
	 * @return 
	 */
	private boolean isChangePhone(Integer userId) {
		// 查找该用户最新的更换手机号的记录
		ChangePhone changePhone = ChangePhone.dao.findChangePhone(userId);
		if(changePhone != null){
			Date date = changePhone.getChangePhoneTime();
			Long msec = ToolUtils.getMsecNum(90);
			if(date.getTime() + msec <= new Date().getTime()){
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	
	/**
	 * 修改用户的头像
	 * @author wh
	 * @since 2016-11-22
	 */
	public void updateHeadImg() {
		try {
			// getFile()默认只能上传10M的文件,可以通过制定参数改变上传大小
			// 上传大文件:getFile("papers", "hn_rentHeadImg", 81234567);
			UploadFile headFile = getFile();
			if (checkPara("userId")) {
				User user = User.dao.findById(getPara("userId"));
				if(headFile != null){
					String fileType = headFile.getContentType();
					// image/jpeg
					if(fileType.split("/")[0].equals("image")){
						// 没有文件大小限制
						String headImg = ToolUtils.upload(headFile);
						user.set("headImg", headImg);
						user.update();
						renderJsonError(RequestNormal, user);
					}else{
						renderJsonError(RequestError, "上传的文件格式不正确!");
						return;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderJsonError(RequestError, "上传文件过大!");
		}
	}
	
	
	/**
	 * 修改昵称
	 * @author wh
	 * @since 2016-12-7
	 */
	public void updateUserName() {
		if (checkPara("userId", "userName")) {
			User user = User.dao.findById(getPara("userId"));
			// 修改昵称
			String userName = getPara("userName");
			if(userName != null){
				user.set("userName", userName);
			}
			user.update();
			renderJsonError(RequestNormal, user);
		}
	}
	
	/**
	 * 查询用户详情
	 * @author wh
	 * @since 2016-12-21
	 */
	public void findUserDetail(){
		if (checkPara("userId", "type")){
			String userId = getPara("userId");
			String type = getPara("type");
			Record record = Db.findFirst("select * from t_user where userId = ?", userId);
			if(type.equals("driver")){
				double grade = Comment.dao.findDriverGrade(userId);
				record.set("grade", grade);
			}
			renderJsonError(RequestNormal, record);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
