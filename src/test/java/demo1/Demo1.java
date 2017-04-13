/*
 * @(#) Demo1.java 2016-12-1
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */
package demo1;

public class Demo1 {
	public static void main(String[] args) {
		try {
			Thread.sleep(1000);
			System.out.println("睡1秒");
			Thread.sleep(2000);
			System.out.println("睡2秒,结束");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
