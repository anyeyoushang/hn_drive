/*
 * @(#) BaseModel.java 2017-1-6
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */
package model.dao.inter;

import java.util.HashMap;

public interface BaseModel {
	public Object addDate(HashMap<String, Object> where);
	public Object deleteDate(HashMap<String, Object> where);
	public Object updateDate(HashMap<String, Object> where);
	public Object getDataList(HashMap<String, Object> where);
}
