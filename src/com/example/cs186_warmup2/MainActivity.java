package com.example.cs186_warmup2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	public final static String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";
	public final static String EXTRA_MESSAGE3 = "com.example.myfirstapp.MESSAGE3";
	public final static String EXTRA_MESSAGE4 = "com.example.myfirstapp.MESSAGE4";

	private String username, password, count;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
    	
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	EditText editText2 = (EditText) findViewById(R.id.edit_message2);

    	String message = editText.getText().toString();
    	String message2 = editText2.getText().toString();
    	
    	username = message;
    	password = message2;

    	intent.putExtra(EXTRA_MESSAGE, message);
    	intent.putExtra(EXTRA_MESSAGE2, message2);
    	
    	AsyncTaskRunner runner = new AsyncTaskRunner();
    	runner.execute(message, message2, "login");
    	
    }
    
    public void sendMessage2(View view){
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	EditText editText2 = (EditText) findViewById(R.id.edit_message2);

    	String message = editText.getText().toString();
    	String message2 = editText2.getText().toString();
    	
    	username = message;
    	password = message2;
    	
    	AsyncTaskRunner runner = new AsyncTaskRunner();
    	runner.execute(message, message2, "add");
    }
    
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
    	@Override
    	protected String doInBackground(String... params){
    		try{
	    		//String urlParameters = "username="+params[0]+"a&password="+params[1];
    			
	    		String request = "http://fast-brook-9858.herokuapp.com/users/"+params[2];
    			//String request = "http://radiant-temple-1017.herokuapp.com/users/"+params[2];
    			//using a teammate's backend cause mine failed, this proj should only be graded on frontend pls
	    		//System.out.println(params);
	    		//System.out.println(params[0] + " " + params[1] + " " + params[2]);
	    		URL url = new URL(request); 
	    		HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
	    		connection.setDoOutput(true);
	    		connection.setDoInput(true);

	    		connection.setRequestMethod("POST"); 
	    		connection.setRequestProperty("Content-Type", "application/json"); 
	    		connection.setRequestProperty("charset", "utf-8");
	    		connection.connect();
	    		
	    		//System.out.println(connection.getResponseCode());
	    		JSONObject json = new JSONObject();
	    		try{
	    			json.put("user", params[0]);
	    			json.put("password", params[1]);
	    		}
	    		catch(Exception e1){
	    			System.out.println("json add failed" + e1);
	    		}
	    		byte[] outputBytes = json.toString().getBytes("UTF-8");
	    		OutputStream os = connection.getOutputStream();
	    		os.write(outputBytes);
	    		os.flush();
	    		os.close();
	    		
	    		StringBuilder builder = new StringBuilder();
	    		InputStream is = connection.getInputStream();

	    		BufferedReader breader = new BufferedReader( new InputStreamReader(is) );

	    		String line;
	    		while((line = breader.readLine()) != null){
	    			builder.append(line);
	    		}
	    		String result = builder.toString();
	    		//System.out.println(result);
	    		
	    		String userName = params[0];
	    		renderViews(result, userName);
	    		//System.out.println("whathappened");
	    		return result;
	    		/*
	    		DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
	    		wr.writeBytes(urlParameters);
	    		wr.flush();
	    		wr.close();
	    		connection.disconnect();
	    		*/
    		}
    		catch(Exception e1){
    			System.out.println(e1);
    		}
    		return "exception out";
    	}
    }
    
    private void renderViews(String result, String userName){
    	
    	//looking for first comma, }, etc
		int countIndex = result.indexOf("\"count\":");
		int errCodeIndex = result.indexOf("\"errCode\":");
		int commaIndex = result.indexOf(",");
		int lastBracketIndex = result.indexOf("}");
		
		int countIndexLen = ("\"count\":").length();
		int errCodeLen =  ("\"errCode\":").length();
		
		String count = "";
		String errCode = "";
		
		if(commaIndex != -1){
			errCode = result.substring(errCodeIndex+errCodeLen,commaIndex);
		}
		else{
			errCode = result.substring(errCodeIndex+errCodeLen,lastBracketIndex);

		}
		
		//System.out.println("errcode:"+errCode);
		//System.out.println(errCode.equals("1"));
		
		if(errCode.equals("1")){	//success
			//System.out.println("SUUCCESS");
			errCode = result.substring(errCodeIndex+errCodeLen,commaIndex);
			count += result.substring(countIndex+countIndexLen, lastBracketIndex);
    	}

		
		//System.out.println(count + "! " + errCode);
		
		Intent intent = new Intent(this, DisplayMessageActivity.class);
    	intent.putExtra(EXTRA_MESSAGE, userName); 
    	intent.putExtra(EXTRA_MESSAGE3, count); 
    	intent.putExtra(EXTRA_MESSAGE4, errCode); 
    	startActivity(intent);
    	
		if(errCode.equals("1")){
			//System.out.println("Welcome " + userName);
			//System.out.println("You have logged in " + count + "times.");
		}
		//System.out.println("GOT HERE");
    }
    
    
    
    protected void onPostExecute(String result){
    	try{

    	}
    	catch(Exception e1){
    		System.out.println(e1);
    	}
    }

}


