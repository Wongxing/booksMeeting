
package com.example.bookmeeting;




import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;


import java.io.InputStream;

import java.io.InputStreamReader;

import java.util.ArrayList;

 

import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;

import org.apache.http.NameValuePair;

import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;

import org.json.JSONException;

import org.json.JSONObject;

 

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;

import android.net.ParseException;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;

import android.widget.EditText;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;



public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	JSONArray jArray;
    /** Called when the activity is first created. */
  static String result = "";
  @SuppressLint("NewApi")
  @Override
  public void onCreate(Bundle savedInstanceState) {

      super.onCreate(savedInstanceState);     
        
      setContentView(R.layout.reg);

      if (Build.VERSION.SDK_INT >= 11) {
          StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads     ().detectDiskWrites().detectNetwork().penaltyLog().build());
       StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
      }
        
    	final EditText usertv = (EditText) findViewById(R.id.reg_userEditText);
    	final EditText pwdtv = (EditText) findViewById(R.id.reg_pwdEditText);
    	final EditText checktv = (EditText) findViewById(R.id.reg_check_pwdEditText);
    	Button regBtn = (Button)findViewById(R.id.new_reg_Button);
    	Button canBtn = (Button)findViewById(R.id.cancelButton);
    	canBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(MainActivity.this,Login.class);
				
				startActivity(i);
			}
			
				
		});
    	regBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = usertv.getText().toString();
		    	String password = pwdtv.getText().toString();
				String checkPassword = checktv.getText().toString();
		    	
		    	
		    	String url = "http://1.tsthelo.sinaapp.com/login_handle_new.php";
		        HttpPost httpRequest = new HttpPost(url);
		        
		        List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("name", username));
		        params.add(new BasicNameValuePair("pwd", password));
		        params.add(new BasicNameValuePair("check", checkPassword));
		        
		        try {
					HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");
					httpRequest.setEntity(httpEntity);
					
					HttpClient httpClient = new DefaultHttpClient();
					HttpResponse httpResponse = httpClient.execute(httpRequest);
					
					if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
						result = EntityUtils.toString(httpResponse.getEntity(),"utf-8");
						String username2;
						String error1 = "用户名或密码不能为空！";
						String error2 = "此用户名已被人注册！";
						String error3 = "两次输入的密码不一致！";
						if(result.contains(error1) || result.contains(error2) || result.contains(error3))
						{
							showDialog(result);
						}
						else
						{
				              try {
	
					                  jArray = new JSONArray(result);
		
					                  JSONObject json_data = null;
		
					                  for (int i = 0; i < jArray.length(); i++) {
		
					                     json_data = jArray.getJSONObject(i);
		
					                     username2 = json_data.getString("username");
		
					                     
		
					                     showDialog(username2+"注册成功！");
					                     Intent firstIntent=new Intent(MainActivity.this,Login.class);
	                                	 
	                     				startActivity(firstIntent);
		
					                  }
	
				              } catch (JSONException e1) {
	
				                  // Toast.makeText(getBaseContext(), "No City Found"
	
				                  // ,Toast.LENGTH_LONG).show();
	
				              } catch (ParseException e1) {
	
				                  e1.printStackTrace();
	
				              }
						}
						
					}else{
						System.out.print("request error");
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	
			}
		});
        
    }
  private void showDialog(String msg){
  	if(msg == null)
  	{
  		msg = "123";
  	}
  	AlertDialog.Builder builder = new AlertDialog.Builder(this);
  	
  	builder.setMessage(msg).setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				
			}
		});
  	AlertDialog alert = builder.create();
  	alert.show();
  }
}