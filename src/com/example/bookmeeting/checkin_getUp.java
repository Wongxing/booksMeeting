package com.example.bookmeeting;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.content.DialogInterface;

public class checkin_getUp extends Activity {
	public String user;
	static String result = "";

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin_getup);
        
        Bundle b = getIntent().getExtras();
        user = b.getString("name");
        
        if (Build.VERSION.SDK_INT >= 11) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
					.penaltyLog().penaltyDeath().build());
		}
        
        final EditText stateEditText = (EditText)findViewById(R.id.stateEditText);
        Button cancelBtn = (Button)findViewById(R.id.getupCancelBtn);
        Button submitBtn = (Button)findViewById(R.id.getupSubmitBtn);
        Button checkRankBtn = (Button)findViewById(R.id.getUpCheckRankBtn);
        
        cancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(checkin_getUp.this, checkin.class);
				Bundle data = new Bundle();
           	    data.putString("name", user);
           	    intent.putExtras(data);
				startActivity(intent);
			}
		});
        
        submitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = user;
				String state = stateEditText.getText().toString();
				
				String url = "http://1.tsthelo.sinaapp.com/checkin.php";
				
				HttpPost httpRequest = new HttpPost(url);

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("name", username));
				params.add(new BasicNameValuePair("state", state));
				params.add(new BasicNameValuePair("type", "getup"));
				
				try {
					HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");
					httpRequest.setEntity(httpEntity);
					
					HttpClient httpClient = new DefaultHttpClient();
					HttpResponse httpResponse = httpClient.execute(httpRequest);
					
					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						System.out.println("I AM HERE!");
						result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
						System.out.println("result  "+result);
						String error1 = "一天只能签一次到哦！";
						String error2 = "签到失败！";
						String info = "签到成功！";
						if (result.contains(error1)) {
							showDialog(error1);
							Intent intent = new Intent(checkin_getUp.this, checkin.class);
							Bundle data = new Bundle();
			           	    data.putString("name", user);
			           	    intent.putExtras(data);
							startActivity(intent);
						}
						else if (result.contains(error2)){
							showDialog(error2);
							Intent intent = new Intent(checkin_getUp.this, checkin.class);
							Bundle data = new Bundle();
			           	    data.putString("name", user);
			           	    intent.putExtras(data);
							startActivity(intent);
						}
						else if (result.contains(info)) {
							showDialog(info);
							Intent intent = new Intent(checkin_getUp.this, checkin.class);
							Bundle data = new Bundle();
			           	    data.putString("name", user);
			           	    intent.putExtras(data);
							startActivity(intent);
						}
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
        
        checkRankBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(checkin_getUp.this, checkin_getUpRank.class);
				Bundle data = new Bundle();
           	    data.putString("name", user);
           	    intent.putExtras(data);
				startActivity(intent);
			}
		});
        
        showView();
	}
	
	private void showDialog(String msg) {
		if (msg == null) {
			msg = "123";
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(msg).setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						// TODO Auto-generated method stub

					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void showView() {
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		tv1.setText("您好！" + user);
	}
}
