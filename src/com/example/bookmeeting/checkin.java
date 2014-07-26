package com.example.bookmeeting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class checkin extends Activity{
	public String user;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin);
        Bundle b = getIntent().getExtras();
        user = b.getString("name");
        showView();
        
        Button getUpBtn = (Button)findViewById(R.id.getUpBtn);
        Button libraryBtn = (Button)findViewById(R.id.libraryBtn);
        Button stateBtn = (Button)findViewById(R.id.stateBtn);
        
        getUpBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent checkin_getupIntent = new Intent(checkin.this, checkin_getUp.class);
				Bundle data = new Bundle();
           	    data.putString("name", user);
           	    checkin_getupIntent.putExtras(data);
           	    startActivity(checkin_getupIntent);
			}
		});
        libraryBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent checkin_libraryIntent = new Intent(checkin.this, checkin_library.class);
				Bundle data = new Bundle();
           	    data.putString("name", user);
           	    checkin_libraryIntent.putExtras(data);
           	    startActivity(checkin_libraryIntent);
			}
		});
        stateBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent checkin_stateIntent = new Intent(checkin.this, checkin_state.class);
				Bundle data = new Bundle();
           	    data.putString("name", user);
           	    checkin_stateIntent.putExtras(data);
           	    startActivity(checkin_stateIntent);
			}
		});
	}
	public void showView(){	 
        TextView tv1 = (TextView)findViewById(R.id.textView1);
        tv1.setText("ÄúºÃ£¡"+user);
    }
}
