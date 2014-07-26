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
import android.app.AlertDialog;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.content.DialogInterface;
import android.content.Intent;

public class myshuhui extends Activity {
	private static final String TAG = "MainActivity";
	JSONArray jArray;
	/** Called when the activity is first created. */
	static String result = "";
	ListView list;
	public String user;
	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> mylist2 = new ArrayList<HashMap<String, String>>();

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.shuhui);

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

		String url = "http://1.tsthelo.sinaapp.com/myshuhui.php";
		HttpPost httpRequest = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", user));

		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");
			httpRequest.setEntity(httpEntity);

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils
						.toString(httpResponse.getEntity(), "utf-8");
				String bookname;
				String time;
				String add;
				String other;
				String username;
				int num;
				int meetingId;

				try {

					jArray = new JSONArray(result);

					JSONObject json_data = null;

					for (int i = 0; i < jArray.length(); i++) {

						json_data = jArray.getJSONObject(i);
						meetingId = json_data.getInt("meetingId");
						bookname = json_data.getString("bookname");
						time = json_data.getString("time");
						add = json_data.getString("add");
						other = json_data.getString("other");
						username = json_data.getString("username");
						num = json_data.getInt("num");
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("Time", time);
						map.put("Pos", add);
						map.put("Topic", bookname);
						map.put("us", username);
						map.put("note", other);
						map.put("Num", num + "");
						map.put("meetingId", meetingId + "");
						mylist.add(map);
					}

					SimpleAdapter mSchedule = new SimpleAdapter(this, mylist,
							R.layout.shuhuilistview, new String[] { "Time",
									"Pos", "Topic" }, new int[] { R.id.Time,
									R.id.Pos, R.id.Topic });

					list = (ListView) findViewById(R.id.ShuhuiListView);
					list.setAdapter(mSchedule);

				} catch (JSONException e1) {

					// Toast.makeText(getBaseContext(), "No City Found"

					// ,Toast.LENGTH_LONG).show();

				} catch (ParseException e1) {

					e1.printStackTrace();

				}
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

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 获得选中项的HashMap对象
				HashMap<String, String> map = (HashMap<String, String>) list
						.getItemAtPosition(arg2);
				String time = map.get("Time");
				String pos = map.get("Pos");

				final String topic = map.get("Topic");
				String us = map.get("us");
				String num = map.get("Num");
				String note = map.get("note");
				final String meetingId = map.get("meetingId");
				// Toast.makeText(getApplicationContext(),
				// "你选择了第"+arg2+"个Item，Time的值是："+time+"Pos的值是:"+pos+"，主题是："+topic,
				// Toast.LENGTH_SHORT).show();
				setContentView(R.layout.myxiangqing);
				TextView SpecificTime = (TextView) findViewById(R.id.mySpecificTime);
				TextView SpecificPos = (TextView) findViewById(R.id.mySpecificPos);
				TextView SpecificTopic = (TextView) findViewById(R.id.mySpecificTopic);
				TextView Faqiren = (TextView) findViewById(R.id.myFaqiren);
				TextView PeopleNum = (TextView) findViewById(R.id.myPeopleNum);
				TextView Note = (TextView) findViewById(R.id.myNote);
				Button AttendPeople = (Button) findViewById(R.id.AttendPeople);
				SpecificTime.setText(time);
				SpecificPos.setText(pos);
				SpecificTopic.setText(topic);
				Faqiren.setText(us);
				Note.setText(note);
				PeopleNum.setText(num);

				AttendPeople.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent Intent = new Intent(myshuhui.this,
								attendPeople.class);
						Bundle data = new Bundle();
						data.putString("name", user);
						data.putString("meetingId", meetingId);
						Intent.putExtras(data);
						startActivity(Intent);

					}
				});

			}

		});

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

}