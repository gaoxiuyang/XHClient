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
import android.view.View.OnFocusChangeListener;
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
		etOrderName = (EditText) findViewById(R.id.et_order_name);
		et_currentuser_name = (EditText) findViewById(R.id.et_currentuser_name);
		et_currentuser_tele = (EditText) findViewById(R.id.et_currentuser_tele);
		etOrderName.setOnFocusChangeListener(this.onFocusAutoClearHintListener);
		et_currentuser_name.setOnFocusChangeListener(this.onFocusAutoClearHintListener);
		et_currentuser_tele.setOnFocusChangeListener(this.onFocusAutoClearHintListener);
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
				String check = "(\\d{11})$|^(\\d{3,5}[-]?\\d{6,8})$"; 
				//^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
				
				// String bb = etMail.getText().toString();
				 Boolean b = et_currentuser_tele.getText().toString().matches(check);
//				 if (Constant.login == 0) {
//					Toast.makeText(MainActivity.this, "请您先登录,再提交报修单", 1000).show();
//				}else if (Constant.login == 1) {
					if (TextUtils.isEmpty(etOrderName.getText())) {
						Toast.makeText(OrderActivity.this, "用户名为空，请输入后重试", Toast.LENGTH_SHORT).show();
					}else if (TextUtils.isEmpty(et_currentuser_name.getText())) {
						Toast.makeText(OrderActivity.this, "轩慧账户为空，请输入后重试", Toast.LENGTH_SHORT).show();
					}else if (TextUtils.isEmpty(et_currentuser_tele.getText())) {
						Toast.makeText(OrderActivity.this, "电话号为空，请输入后重试", Toast.LENGTH_SHORT).show();
					}else if (b == false){
						Toast.makeText(OrderActivity.this, "电话号码有误，请重新输入", Toast.LENGTH_SHORT).show();
					}else {
						ShowPickDialog();
					}
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
							Toast.makeText(OrderActivity.this, "进入支付宝界面", 1000).show();
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

	/**
	 * EditText的hint属性自动消失
	 */
	public static OnFocusChangeListener onFocusAutoClearHintListener = new OnFocusChangeListener() {
		public void onFocusChange(View v, boolean hasFocus) {
		EditText textView = (EditText) v;
		String hint;
		if (hasFocus) {
		hint = textView.getHint().toString();
		textView.setTag(hint);
		textView.setHint("");
		} else {
		hint = textView.getTag().toString();
		textView.setHint(hint);
		}
		}
		};
	private EditText etOrderName;
	private EditText et_currentuser_name;
	private EditText et_currentuser_tele;
	
}
