package model.dao;

import java.util.Date;
import java.util.HashMap;

import model.dao.inter.BaseModel;
import model.entity.BaseChangePhone;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class ChangePhone extends BaseChangePhone<ChangePhone> implements BaseModel {
	public static final ChangePhone dao = new ChangePhone();
	
	@Override
	public Object addDate(HashMap<String, Object> where) {
		return null;
	}

	@Override
	public Object deleteDate(HashMap<String, Object> where) {
		return null;
	}

	@Override
	public Object updateDate(HashMap<String, Object> where) {
		return null;
	}
	
	@Override
	public Object getDataList(HashMap<String, Object> where) {
		return null;
	}
	
	/**
	 * 添加更换手机号记录
	 * @author wh
	 * @since 2016-12-8
	 * @param userId
	 */
	public void addRecord(Integer userId) {
		this.setChangePhoneId(null);
		this.setChangePhoneTime(new Date());
		this.setUserId(userId);
		this.save();
	}

	/**
	 * 查找该用户最新的更换手机号的记录
	 * @author wh
	 * @since 2016-12-8
	 * @param userId
	 * @return 
	 */
	public ChangePhone findChangePhone(Integer userId) {
		String sql = "SELECT * FROM t_change_phone WHERE userId = ? ORDER BY changePhoneTime DESC";
		return this.findFirst(sql, userId);
	}

	



	
}


