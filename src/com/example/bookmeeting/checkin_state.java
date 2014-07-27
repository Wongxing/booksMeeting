package com.example.bookmeeting;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.content.Intent;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class checkin_state extends Activity {
	public String user;
	static String result = "";
	JSONArray jArray;
	ListView list;
	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_friend_state);

		if (Build.VERSION.SDK_INT >= 11) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
					.penaltyLog().penaltyDeath().build());
		}
		
		Bundle b = getIntent().getExtras();
		user = b.getString("name");

		String url = "http://1.tsthelo.sinaapp.com/getstate.php";
		HttpPost httpRequest = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", user));
		params.add(new BasicNameValuePair("type", "0"));

		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");
			httpRequest.setEntity(httpEntity);

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils
						.toString(httpResponse.getEntity(), "utf-8");
				System.out.println("result   " + result);

				String UserName, State, Date, Time;
				int getlib;

				try {
					jArray = new JSONArray(result);
					JSONObject json_data = null;

					for (int i = 0; i < jArray.length(); i++) {
						json_data = jArray.getJSONObject(i);

						UserName = json_data.getString("username");
						State = json_data.getString("state");
						Date = json_data.getString("date");
						Time = json_data.getString("time");
						getlib = json_data.getInt("getlib");

						HashMap<String, String> map = new HashMap<String, String>();
						map.put("username", UserName);
						map.put("state", State);
						map.put("date", Date);
						map.put("time", Time);
						map.put("getlib", getlib+"");

						mylist.add(map);
					}
					
					SimpleAdapter mSchedule = new SimpleAdapter(this, mylist,
							R.layout.statelistview, new String[] { "username",
									"state", "date", "time" }, new int[] {
									R.id.UserName, R.id.State, R.id.Date,
									R.id.Time });

					list = (ListView) findViewById(R.id.StateListView);
					list.setAdapter(mSchedule);
					
				} catch (JSONException e1) {

					// Toast.makeText(getBaseContext(), "No City Found"

					// ,Toast.LENGTH_LONG).show();

				} catch (ParseException e1) {

					e1.printStackTrace();

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

}
