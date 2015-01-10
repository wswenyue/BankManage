package cn.sdut.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

public class SecurityCode {

	public static String sendGet(String aim,String text) throws IOException{
//	    String u="http://2.smsfx.sinaapp.com/send.php?";
//		String tel = "your phone number";
//		String pwd = "your password";
//		String num = makeNum();
//		String stext = text + num;
//			
//			stext = URLEncoder.encode(stext,"UTF-8");
//			String param="tel=" + tel + "&pwd=" + pwd + "&aim=" + aim + "&text=" + stext;
//			   
//        	String surl = u + param;
//    		
//    		URL url = new URL(surl);
//    		URLConnection connection = url.openConnection();
//    		
//    		HttpURLConnection httpUrlConnection = (HttpURLConnection) connection;
//
//    		InputStream l_urlStream = httpUrlConnection.getInputStream();
//    		StringBuffer sb = new StringBuffer();
//    		BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "UTF-8"));
//    		String sCurrentLine;
//    		while ((sCurrentLine = l_reader.readLine()) != null) {
//    			sb.append(sCurrentLine);
//    		}
    		//System.out.println(sb);
    		//System.out.println(num);
    		
    	String num = makeNum();
    	System.out.println(num);
		return num; 
    }

	private static String makeNum() {
	
	    Random r = new Random();
	    String num = r.nextInt(9999) + "";
	    StringBuffer sb = new StringBuffer();
	    for (int i=0;i<4-num.length();i++){
	        sb.append("0");        //不够四位的用“0”补充
	    }
	
	    num = sb.toString()+num;    //字符串合并
	
	    return num;
	}
	
}