package model.dao;

import java.util.HashMap;

import model.dao.inter.BaseModel;
import model.entity.BaseMessage;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Message extends BaseMessage<Message> implements BaseModel {
	public static final Message dao = new Message();
	
	/**
	 * 消息是否被读
	 * @Description 
	 * @author wh
	 * @version 1.0
	 * @since 2016-12-27
	 */
	public enum MsgReadEnum{
		未读,
		已读
	}
	
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
	
	

	

	
}