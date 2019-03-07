package com.kirash.alex.post_request_json.main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class MainClass {

	private static final int PRETTY_PRINT_INDENT_FACTOR = 4;
	
	 public static void main(String[] args) throws FileNotFoundException, JSONException {
		 
		 		// Read XML file //
		 	  BufferedReader b_reader = new BufferedReader(new FileReader("example.xml"));
		 	  String context = b_reader.lines().collect(Collectors.joining("\n"));
		 	    // Ñonversion data to JSON 
			  JSONObject jsonObject = XML.toJSONObject(context);
			  String json = jsonObject.toString(PRETTY_PRINT_INDENT_FACTOR);
			  System.out.println("JSON Format : " + "\n");
			  System.out.println(json + "\n");
			  
			  
			  String query_url = "http://test.upc/queryBatch.cfm";
			  
			  	// Creating a POST request JSON
			  String urlParameters  = "";
			  byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
			  int    postDataLength = postData.length;
			  			
			  try {		 
		           URL url = new URL(query_url);
		           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		           connection.setConnectTimeout(4000);
		           connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded\\r\\n");
		           connection.setRequestProperty( "charset", "utf-8");
		           connection.setRequestProperty( "Content-Length", Integer.toString( postDataLength ) + "\\r\\n");
		           connection.setDoOutput(true);
		           connection.setDoInput(true);
		           connection.setRequestMethod("POST");

		           OutputStream ostream = connection.getOutputStream();
		           ostream.write(json.getBytes("UTF-8"));
		           ostream.close(); 

		           InputStream istream = new BufferedInputStream(connection.getInputStream());
		           String result = IOUtils.toString(istream, "UTF-8");
		          
		           System.out.println(result);		
		           
		            // Reading response 
		           System.out.println("Reading Response");		           
		           JSONObject response = new JSONObject(result);
		           System.out.println("ID : " + response.getInt("id"));
		           System.out.println("DT request : " + response.getInt("dt_request"));
		           System.out.println("ID Task : " + response.getInt("IDTask"));
		           System.out.println("Type of operation : " + response.getString("typeOper"));
		           System.out.println("Name : " + response.getString("Name"));
		           System.out.println("Password : " + response.getString("Password"));
		           System.out.println("Card number : " + response.getInt("Cardnumber"));
		           		           
		           istream.close();
		           connection.disconnect();	           
			
		           } catch (Exception e) {
		   			System.out.println(e);
		           }
			
	 	}
}
		
