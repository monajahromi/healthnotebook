package com.notebook;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class HttpClientExample {

	
	public static void main(String[] args) throws IOException {
	
		
		 URL url = new URL("http://46.34.96.61:2020/card/person/0493224890");
		    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		    connection.setRequestMethod("GET");
		    Map<String, String> params = new HashMap<String,String>();
		    params.put("v", "dQw4w9WgXcQ");

		    StringBuilder postData = new StringBuilder();
		    for (Map.Entry<String, String> param : params.entrySet()) {
		        if (postData.length() != 0) {
		            postData.append('&');
		        }
		        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
		        postData.append('=');
		        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		    }

		    byte[] postDataBytes = postData.toString().getBytes("UTF-8");
		    connection.setDoOutput(true);
		    try  {
		    	DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
		        writer.write(postDataBytes);

		        StringBuilder content;

		        try {
		        	 BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
		        String line;
		        content = new StringBuilder();
		           while ((line = in.readLine()) != null) {
		                content.append(line);
		                content.append(System.lineSeparator());
		            }
		           
		        System.out.println("content : "+ content.toString());   
		        }
		        catch(Exception e){
			    	
			    }
		        
		    }
		    catch(Exception e){
		    	
		    }
		    finally {
		        connection.disconnect();
		    }
		    
		    
		    
	}
}
