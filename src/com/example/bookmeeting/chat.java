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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.EditText;
import android.content.DialogInterface;
import android.content.Intent;

public class chat extends Activity {
	private static final String TAG = "MainActivity";
	JSONArray jArray;
	/** Called when the activity is first created. */
	static String result = "";
	ListView list;
	public String user;
	public String meetingId;
	public String user2;
	public String msg;
	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

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
		meetingId = b.getString("meetingId");
		user2 = b.getString("name2");
		msg = b.getString("msg");

		setContentView(R.layout.hui);
		String url = "http://1.tsthelo.sinaapp.com/chat2.php";
		HttpPost httpRequest = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user1", user));
		params.add(new BasicNameValuePair("user2", user2));
		params.add(new BasicNameValuePair("msg", msg));
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
				String uname;
				String us1;
				String message_back;
				String date;
				try {

					jArray = new JSONArray(result);

					JSONObject json_data = null;

					for (int i = jArray.length() - 1; i >= 0; i--) {

						json_data = jArray.getJSONObject(i);
						us1 = json_data.getString("user1");
						message_back = json_data.getString("msg");
						time = json_data.getString("time");

						HashMap<String, String> map = new HashMap<String, String>();
						map.put("Time", us1);
						map.put("Pos", message_back);
						map.put("Topic", time);

						mylist.add(map);
					}

					SimpleAdapter mSchedule = new SimpleAdapter(this, mylist,
							R.layout.huilistview, new String[] { "Time", "Pos",
									"Topic" }, new int[] { R.id.Time, R.id.Pos,
									R.id.Topic });

					list = (ListView) findViewById(R.id.huiListView);
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

		Button sendBtn = (Button) findViewById(R.id.sendBtn);
		final EditText message = (EditText) findViewById(R.id.message);
		Button backmainBtn = (Button) findViewById(R.id.backmainBtn);

		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String msg = message.getText().toString();

				Intent findIntent = new Intent(chat.this, chating.class);
				Bundle data = new Bundle();
				data.putString("name", user);
				data.putString("name2", user2);
				data.putString("msg", msg);
				findIntent.putExtras(data);
				startActivity(findIntent);

			}
		});
		backmainBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String msg = message.getText().toString();

				Intent findIntent = new Intent(chat.this, main_page.class);
				Bundle data = new Bundle();
				data.putString("name", user);
				data.putString("name2", user2);
				data.putString("msg", msg);
				findIntent.putExtras(data);
				startActivity(findIntent);

			}
		});

	}

	private void showDialog(String msg) {
		if (msg == null) {
			msg = "123";
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(msg).setCancelable(false)
				.setPositiveButton("È·¶¨", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						// TODO Auto-generated method stub

					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

}