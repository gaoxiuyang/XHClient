package com.xuanhui;

import cn.buaa.myweixin.R;

import com.xuanhui.util.Constant;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity{

	private EditText mEtAccount = null;
	private ImageView mIvAccountClear = null;
	private EditText mEtPwd = null;
	private ImageView mIvPwdClear = null;
	private Button mBtnLogin = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mEtAccount = (EditText) findViewById(R.id.et_account1);
		mEtPwd = (EditText) findViewById(R.id.et_password1);
		mBtnLogin = (Button) findViewById(R.id.btn_login1);
		mBtnLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(mEtAccount.getText())) {
					Toast.makeText(LoginActivity.this, "用户名为空，请输入后重试", Toast.LENGTH_SHORT).show();
				}else if (TextUtils.isEmpty(mEtPwd.getText())) {
					Toast.makeText(LoginActivity.this, "密码为空，请输入后重?", Toast.LENGTH_SHORT).show();
				}else {
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					LoginActivity.this.startActivity(intent);
					Toast.makeText(LoginActivity.this, "登录成功", 1000).show();
					Constant.login = 1;
				}
			}
		});
	}

}
