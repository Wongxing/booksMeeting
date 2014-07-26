
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
import android.widget.TextView;

import android.widget.EditText;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;



public class finding extends Activity {
	private static final String TAG = "MainActivity";
	JSONArray jArray;
	public String user;
    /** Called when the activity is first created. */
  static String result = "";
  @SuppressLint("NewApi")
  @Override
  public void onCreate(Bundle savedInstanceState) {

      super.onCreate(savedInstanceState);     
        
      setContentView(R.layout.finding);

      if (Build.VERSION.SDK_INT >= 11) {
          StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads     ().detectDiskWrites().detectNetwork().penaltyLog().build());
       StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
      }
      Bundle b = getIntent().getExtras();
      user = b.getString("name");
      showView();
    	final EditText booktv = (EditText) findViewById(R.id.findingBookEditText);
    	final EditText timetv = (EditText) findViewById(R.id.findingTimeEditText);
    	final EditText addtv = (EditText) findViewById(R.id.findingAddEditText);
    	final EditText othertv = (EditText) findViewById(R.id.findingElseEditText);
    	Button findingCanBtn = (Button)findViewById(R.id.findingCancelButton);
    	Button FindingBtn = (Button)findViewById(R.id.FindingNewButton);
    	Button ShareBtn = (Button) findViewById(R.id.ShareButton);
    	
    	findingCanBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent back1=new Intent(finding.this,main_page.class);
				Bundle data = new Bundle();
           	    data.putString("name", user);
           	    back1.putExtras(data);
				startActivity(back1);
			}
			
				
		});
    	FindingBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String bookname = booktv.getText().toString();
		    	String time = timetv.getText().toString();
				String add = addtv.getText().toString();
				String other = othertv.getText().toString();
		    	
		    	
		    	String url = "http://1.tsthelo.sinaapp.com/meeting.php";
		        HttpPost httpRequest = new HttpPost(url);
		        
		        List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("bookname", bookname));
		        params.add(new BasicNameValuePair("time", time));
		        params.add(new BasicNameValuePair("add", add));
		        params.add(new BasicNameValuePair("other", other));
		        params.add(new BasicNameValuePair("user", user));
		        try {
					HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");
					httpRequest.setEntity(httpEntity);
					
					HttpClient httpClient = new DefaultHttpClient();
					HttpResponse httpResponse = httpClient.execute(httpRequest);
					
					if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
						result = EntityUtils.toString(httpResponse.getEntity(),"utf-8");
						String username2;
						
							showDialog("创建成功！");
							
						
						
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
        ShareBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String bookname = booktv.getText().toString();
		    	String time = timetv.getText().toString();
				String add = addtv.getText().toString();
				String other = othertv.getText().toString();
				Intent intent=new Intent(Intent.ACTION_SEND);			      
			    intent.setType("text/plain");
			    intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
			    intent.putExtra(Intent.EXTRA_TEXT, "在"+time+"，"+user+"与君相约在"+add+"，共同在《"+bookname+"》的海洋里畅游，不见不散！PS："+other+"（发自 中大书友 软件）");
			    startActivity(Intent.createChooser(intent, getTitle()));
			}
		});
        
    }
  private void showDialog(String msg){
  	if(msg == null)
  	{
  		msg = "123";
  	}
  	AlertDialog.Builder builder = new AlertDialog.Builder(this);
  	
  	builder.setMessage(msg)
  	.setCancelable(false)
  	.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				
			}
		});
  	AlertDialog alert = builder.create();
  	alert.show();
  }
  public void showView(){	
      
      
      TextView tv2 = (TextView)findViewById(R.id.textView2);
      tv2.setText("您好！"+user);
  }
}