package com.example.bookmeeting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class checkin_state extends Activity{
	public String user;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_friend_state);
        Bundle b = getIntent().getExtras();
        user = b.getString("name");
        showView();
	}
	public void showView(){	 
        TextView tv1 = (TextView)findViewById(R.id.textView1);
        tv1.setText("ฤ๚บรฃก"+user);
    }
}
