/*
 * @(#) GetLocation.java 2017-1-5
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */
package other.utils;
import java.net.URL;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetLocation {
	public static void main(String[] args) {  
        String add = getAddress("117.20881", "31.74848");
        System.out.println(add);
    }
      
    public static String getAddress(String lon, String lat ){
        //lat 小  lon  大
        //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)  
        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="+lat+","+lon+"&type=010";
        String res = "";     
        try {     
            URL url = new URL(urlString);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();    
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));    
            String line;
           while ((line = in.readLine()) != null) {
               res += line+"\n";
         }
           in.close();
        } catch (Exception e) {
            System.out.println("error in wapaction,and e is " + e.getMessage());    
        }
        JSONObject jsonObject = JSONObject.fromObject(res);
        JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("addrList"));  
        JSONObject j_2 = JSONObject.fromObject(jsonArray.get(0));
        String address = j_2.getString("admName") + j_2.getString("name");
        return address; 
    } 
}
