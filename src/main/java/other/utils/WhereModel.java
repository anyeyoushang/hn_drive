/*
 * @(#) WhereModel.java 2016年11月29日
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */
package other.utils;

import java.util.ArrayList;
import java.util.List;

public class WhereModel {
	private String sqlStr;
	private List<Object> values;

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public List<Object> getValues() {
		if (values == null) {
			values = new ArrayList<Object>();
		}
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}
}
