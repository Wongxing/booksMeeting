package com.example.bookmeeting;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class main_page extends Activity{
	public String user;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_main);
        Bundle b = getIntent().getExtras();
        user = b.getString("name");
        showView();
        Button newOneBtn = (Button)findViewById(R.id.newOneBtn);
    	Button whtchingAllBtn = (Button)findViewById(R.id.whtchingAllBtn);
    	Button finish = (Button)findViewById(R.id.finish);
    	Button myshuhuiBtn = (Button)findViewById(R.id.myshuhuiBtn);
    	Button myAttenBtn = (Button)findViewById(R.id.myAttenBtn);
    	Button friendBtn = (Button)findViewById(R.id.friendBtn);
    	Button checkBtn = (Button)findViewById(R.id.checkBtn);

    	newOneBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
                Intent findIntent=new Intent(main_page.this,finding.class);
                Bundle data = new Bundle();
           	    data.putString("name", user);
           	    findIntent.putExtras(data);
				startActivity(findIntent);
			}			
		});
    	whtchingAllBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
                Intent watchIntent=new Intent(main_page.this,watching.class);
                Bundle data = new Bundle();
           	    data.putString("name", user);
           	    watchIntent.putExtras(data);
				startActivity(watchIntent);
			}					
		});
    	myshuhuiBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
                Intent findIntent=new Intent(main_page.this,myshuhui.class);
                Bundle data = new Bundle();
           	    data.putString("name", user);
           	    findIntent.putExtras(data);
				 startActivity(findIntent);
			}		
		});
    	myAttenBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
                Intent findIntent=new Intent(main_page.this,Atten.class);
                Bundle data = new Bundle();
           	    data.putString("name", user);
           	    findIntent.putExtras(data);
				 startActivity(findIntent);
			}		
		});
    	friendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
                Intent findIntent=new Intent(main_page.this,friend.class);
                Bundle data = new Bundle();
           	    data.putString("name", user);
           	    findIntent.putExtras(data);
				startActivity(findIntent);
			}				
		});
    	checkBtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent checkIntent = new Intent(main_page.this, checkin.class);
				Bundle data = new Bundle();
           	    data.putString("name", user);
           	    checkIntent.putExtras(data);
				startActivity(checkIntent);
			}
		});
    	finish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				while(true){
				    System.exit(0);
				}
			}				
		});        
    }
    public void showView(){	 
        TextView tv1 = (TextView)findViewById(R.id.textView1);
        tv1.setText("ÄúºÃ£¡"+user);
    }

}

	