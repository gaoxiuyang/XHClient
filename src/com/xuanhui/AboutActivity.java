package com.xuanhui;


import cn.buaa.myweixin.R;
import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AboutActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		RelativeLayout rlTitleAbout = (RelativeLayout) findViewById(R.id.rl_title_about);
		rlTitleAbout.setOnClickListener(new OnClickListener() {
		
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AboutActivity.this.finish();
			}
		});
	}


}
