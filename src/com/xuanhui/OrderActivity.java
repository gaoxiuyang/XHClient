package com.xuanhui;

import java.io.File;

import cn.buaa.myweixin.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends Activity{
	
	
	private CheckBox checkBox;
	private TextView tvClause;
	private RelativeLayout rlOrder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		rlOrder = (RelativeLayout) findViewById(R.id.rl_order);
		rlOrder.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OrderActivity.this,MainActivity.class);
				OrderActivity.this.startActivity(intent);
				OrderActivity.this.finish();
			}
		});
		Button btn_pay = (Button) findViewById(R.id.btn_pay);
		 checkBox = (CheckBox) findViewById(R.id.checkBox1);
		 tvClause = (TextView) findViewById(R.id.tv_clause);
		 tvClause.setOnClickListener(new OnClickListener() {
		
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(OrderActivity.this, ClauseActivity.class);
//				OrderActivity.this.startActivity(intent);
			}
		});
		 btn_pay.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ShowPickDialog();
			}
		});
	}
	private void ShowPickDialog() {
		new AlertDialog.Builder(this)
				.setTitle("请确认支付")
				.setNegativeButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (checkBox.isChecked() == false) {
							Toast.makeText(OrderActivity.this, "尚未同意用户服务协议，请重试", 1000).show();
						}else {
							Toast.makeText(OrderActivity.this, "支付成功", 1000).show();
							Intent intent = new Intent(OrderActivity.this, MainActivity.class);
							OrderActivity.this.startActivity(intent);
						}
					}
				})
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				}).show();
	}

}
