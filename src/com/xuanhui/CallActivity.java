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
import android.widget.TextView;
import android.widget.Toast;
public class CallActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);
		final TextView txv_feedback1 = (TextView) findViewById(R.id.txv_feedback1);
		Button btn_commit1 = (Button) findViewById(R.id.btn_commit1);
		RelativeLayout rlTitle = (RelativeLayout) findViewById(R.id.rl_title_call);
		rlTitle.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				CallActivity.this.finish();
			}
		});
		btn_commit1.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(CallActivity.this, "提交成功", 1000).show();
				txv_feedback1.setText("");
			}
		});
	}
}
