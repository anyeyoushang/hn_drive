/*
 * @(#) Test.java 2016-12-13
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */
package other.jpush;

import java.util.HashMap;

public class Test {
	public static void main(String[] args) {
		JpushUtil.sendNoticeAlias(JpushFactory.getUserJpush(), "第三方第三方",
				new HashMap<String, String>(), "18715083418");
	}
}
