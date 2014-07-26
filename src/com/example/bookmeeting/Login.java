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
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import android.content.DialogInterface;
import android.content.Intent;

public class Login extends Activity {

	JSONArray jArray2;
	/** Called when the activity is first created. */
	static String result2 = "";

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		if (Build.VERSION.SDK_INT >= 11) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
					.penaltyLog().penaltyDeath().build());
		}

		final EditText loginUsertv = (EditText) findViewById(R.id.userEditText);
		final EditText loginPwdtv = (EditText) findViewById(R.id.pwdEditText);

		Button LoginRegBtn = (Button) findViewById(R.id.regButton);
		Button LoginBtn = (Button) findViewById(R.id.loginButton);
		LoginRegBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent i = new Intent(Login.this, MainActivity.class);

				startActivity(i);
			}

		});
		LoginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = loginUsertv.getText().toString();
				String password = loginPwdtv.getText().toString();

				String url = "http://1.tsthelo.sinaapp.com/login.php";
				HttpPost httpRequest = new HttpPost(url);

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("name", username));
				params.add(new BasicNameValuePair("pwd", password));

				try {
					HttpEntity httpEntity = new UrlEncodedFormEntity(params,
							"utf-8");
					httpRequest.setEntity(httpEntity);

					HttpClient httpClient = new DefaultHttpClient();
					HttpResponse httpResponse = httpClient.execute(httpRequest);

					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						result2 = EntityUtils.toString(
								httpResponse.getEntity(), "utf-8");
						String error4 = "用户名或密码不能为空！";
						String error5 = "没有该用户名！";
						if (result2.contains(error4)
								|| result2.contains(error5)) {
							showDialog(result2);
						} else {
							try {

								jArray2 = new JSONArray(result2);

								JSONObject json_data = null;

								if (jArray2.length() == 1) {
									json_data = jArray2.getJSONObject(0);

									String password2 = json_data
											.getString("password");
									if (password2.equals(password)) {
										showDialog("登陆成功！");
										Intent firstIntent = new Intent(
												Login.this, main_page.class);
										Bundle data = new Bundle();
										data.putString("name", username);
										firstIntent.putExtras(data);
										startActivity(firstIntent);
									} else {
										showDialog("密码错误！");
									}
								}

							} catch (JSONException e1) {

							} catch (ParseException e1) {

								e1.printStackTrace();
							}
						}
					} else {
						System.out.print("request error");
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