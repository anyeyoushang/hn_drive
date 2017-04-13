/*
 * @(#) Demo2.java 2016-12-2
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */
package demo1;

import java.util.UUID;

public class Demo2 {
	static double pi = 3.1415926535898;
	
	public static void main(String[] args) {
//		long length = getDistance(117, 36.5, 117.1, 36.6);
//		System.out.println(length);
		
		test2();
	}

	private static void test2() {
		System.out.println(UUID.randomUUID().toString().replace("-", "").toUpperCase());
	}

	private static long getDistance(double lon1, double lat1, double lon2, double lat2) {
		long length = Math.round(6378.138*2*Math.asin(Math.sqrt(Math.pow(Math.sin( 
				(lat2*pi/180-lat1*pi/180)/2),2)+Math.cos(lat2*pi/180)*Math.cos(lat1*pi/180)* 
				Math.pow(Math.sin( (lon2*pi/180-lon1*pi/180)/2),2)))*1000);
		return length;
	}
}
