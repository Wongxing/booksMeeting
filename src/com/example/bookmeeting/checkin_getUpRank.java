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

import android.R.bool;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class checkin_getUpRank extends Activity {
	public String user;
	static String result = "";
	ListView list;
	JSONArray jArray;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkin_getuprank);

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

		String url = "http://1.tsthelo.sinaapp.com/checkinrank.php";
		HttpPost httpRequest = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", user));
		params.add(new BasicNameValuePair("type", "getup"));

		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");
			httpRequest.setEntity(httpEntity);

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils
						.toString(httpResponse.getEntity(), "utf-8");
				System.out.println("result   " + result);

				ArrayList<checkInfo> arrayList = new ArrayList<checkInfo>();

				arrayList = getALFromString(result);

				int aLength = arrayList.size();

				for (int i = 0; i < aLength; i++) {
					for (int j = i + 1; j < aLength; j++) {
						if (arrayList.get(i).getDays() < arrayList.get(j)
								.getDays()) {
							checkInfo tempCheckInfo = arrayList.get(i);
							arrayList.set(i, arrayList.get(j));
							arrayList.set(j, tempCheckInfo);
						}
					}
				}


				int userNumber = 0;
				for (int i = 0; i < aLength; i++) {
					if (arrayList.get(i).getUserName() == user) {
						userNumber = i;
						break;
					}
				}
				
				System.out.println("No.  "+userNumber);
				
				if (aLength == 1) {
					System.out.println(1);
					HashMap<String, String> map = new HashMap<String, String>();
					ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
					
					map.put("CheckNumber", "1");
					map.put("checkUserName", arrayList.get(0).getUserName());
					map.put("checkDays",
							String.valueOf(arrayList.get(0).getDays()));
					mylist.add(map);
					
					SimpleAdapter mSchedule = new SimpleAdapter(this, mylist,
							R.layout.getuprankview, new String[] { "CheckNumber",
									"checkUserName", "checkDays" }, new int[] {
									R.id.CheckNumber, R.id.checkUserName,
									R.id.checkDays });
					
					list = (ListView)findViewById(R.id.getUpRankView);
					list.setAdapter(mSchedule);
					
				} else if (aLength == 2) {
					System.out.println(2);
					HashMap<String, String> map1 = new HashMap<String, String>(), map2 = new HashMap<String, String>();
					ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
					
					map1.put("CheckNumber", "1");
					map1.put("checkUserName", arrayList.get(0).getUserName());
					map1.put("checkDays",
							String.valueOf(arrayList.get(0).getDays()));
					mylist.add(map1);
					
					map2.put("CheckNumber", "2");
					map2.put("checkUserName", arrayList.get(1).getUserName());
					map2.put("checkDays",
							String.valueOf(arrayList.get(1).getDays()));
					mylist.add(map2);
					
					
					SimpleAdapter mSchedule = new SimpleAdapter(this, mylist,
							R.layout.getuprankview, new String[] { "CheckNumber",
									"checkUserName", "checkDays" }, new int[] {
									R.id.CheckNumber, R.id.checkUserName,
									R.id.checkDays });
					
					list = (ListView)findViewById(R.id.getUpRankView);
					list.setAdapter(mSchedule);

				} else if (aLength == 3) {
					System.out.println(3);
					HashMap<String, String> map1 = new HashMap<String, String>(), map2 = new HashMap<String, String>(), map3 = new HashMap<String, String>();
					ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
					
					map1.put("CheckNumber", "1");
					map1.put("checkUserName", arrayList.get(0).getUserName());
					map1.put("checkDays",
							String.valueOf(arrayList.get(0).getDays()));
					mylist.add(map1);
					
					map2.put("CheckNumber", "2");
					map2.put("checkUserName", arrayList.get(1).getUserName());
					map2.put("checkDays",
							String.valueOf(arrayList.get(1).getDays()));
					mylist.add(map2);
					
					map3.put("CheckNumber", "3");
					map3.put("checkUserName", arrayList.get(2).getUserName());
					map3.put("checkDays",
							String.valueOf(arrayList.get(2).getDays()));
					mylist.add(map3);
					
					SimpleAdapter mSchedule = new SimpleAdapter(this, mylist,
							R.layout.getuprankview, new String[] { "CheckNumber",
									"checkUserName", "checkDays" }, new int[] {
									R.id.CheckNumber, R.id.checkUserName,
									R.id.checkDays });
					
					System.out.println("nimei");
					list = (ListView)findViewById(R.id.getUpRankView);
					list.setAdapter(mSchedule);
					System.out.println("cuola");
					
				} else {
					if (userNumber > 2) {
						HashMap<String, String> map1 = new HashMap<String, String>(), map2 = new HashMap<String, String>(), map3 = new HashMap<String, String>(), map4 = new HashMap<String, String>();
						ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
						
						map1.put("CheckNumber", "1");
						map1.put("checkUserName", arrayList.get(0).getUserName());
						map1.put("checkDays",
								String.valueOf(arrayList.get(0).getDays()));
						mylist.add(map1);
						
						map2.put("CheckNumber", "2");
						map2.put("checkUserName", arrayList.get(1).getUserName());
						map2.put("checkDays",
								String.valueOf(arrayList.get(1).getDays()));
						mylist.add(map2);
						
						map3.put("CheckNumber", "3");
						map3.put("checkUserName", arrayList.get(2).getUserName());
						map3.put("checkDays",
								String.valueOf(arrayList.get(2).getDays()));
						mylist.add(map3);
						
						map4.put("CheckNumber", String.valueOf(userNumber));
						map4.put("checkUserName", user);
						map4.put("checkDays",
								String.valueOf(arrayList.get(userNumber).getDays()));
						mylist.add(map4);
						
						SimpleAdapter mSchedule = new SimpleAdapter(this, mylist,
								R.layout.getuprankview, new String[] { "CheckNumber",
										"checkUserName", "checkDays" }, new int[] {
										R.id.CheckNumber, R.id.checkUserName,
										R.id.checkDays });
						
						list = (ListView)findViewById(R.id.getUpRankView);
						list.setAdapter(mSchedule);
					}
					else {
						HashMap<String, String> map1 = new HashMap<String, String>(), map2 = new HashMap<String, String>(), map3 = new HashMap<String, String>();
						ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
						
						map1.put("CheckNumber", "1");
						map1.put("checkUserName", arrayList.get(0).getUserName());
						map1.put("checkDays",
								String.valueOf(arrayList.get(0).getDays()));
						mylist.add(map1);
						
						map2.put("CheckNumber", "2");
						map2.put("checkUserName", arrayList.get(1).getUserName());
						map2.put("checkDays",
								String.valueOf(arrayList.get(1).getDays()));
						mylist.add(map2);
						
						map3.put("CheckNumber", "3");
						map3.put("checkUserName", arrayList.get(2).getUserName());
						map3.put("checkDays",
								String.valueOf(arrayList.get(2).getDays()));
						mylist.add(map3);
					
						SimpleAdapter mSchedule = new SimpleAdapter(this, mylist,
								R.layout.getuprankview, new String[] { "CheckNumber",
										"checkUserName", "checkDays" }, new int[] {
										R.id.CheckNumber, R.id.checkUserName,
										R.id.checkDays });
						
						list = (ListView)findViewById(R.id.getUpRankView);
						list.setAdapter(mSchedule);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		showView();
	}

	public void showView() {
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		tv1.setText("ÄúºÃ£¡" + user);
	}

	public ArrayList<checkInfo> getALFromString(String str) {
		ArrayList<checkInfo> ckArrayList = new ArrayList<checkInfo>();

		int slen = str.length();

		Boolean isUserName = true;

		for (int i = 0; i < slen; i++) {
			if (str.charAt(i) == '"') {
				ckArrayList.add(new checkInfo());
				int ckLength = ckArrayList.size();
				if (isUserName) {
					for (int j = i + 1; j < slen; j++) {
						if (str.charAt(j) == '"') {
							ckArrayList.get(ckLength - 1).setUserName(
									str.substring(i + 1, j));
							i = j;
							break;
						}
					}
					i = i + 2;
					for (int k = i + 1; k < slen; k++) {
						if (str.charAt(k) == '"') {
							ckArrayList.get(ckLength - 1).setDays(
									Integer.parseInt(str.substring(i + 1, k)));
							i = k;
							break;
						}
					}
				}
			}
		}

		return ckArrayList;
	}
}
