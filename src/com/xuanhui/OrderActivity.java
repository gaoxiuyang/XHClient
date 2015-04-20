package com.xuanhui;

import java.io.File;
import java.nio.channels.SelectableChannel;


import com.xuanhui.sqlite.DatabaseHelper;

import cn.buaa.myweixin.R;
import android.R.id;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
	private EditText etOrderName;
	private EditText et_currentuser_name;
	private EditText et_currentuser_tele;
	private TextView tv_currentuser_orderid;
	private TextView tv_currentuser_ywname;
	private TextView tv_currentuser_money;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		tv_currentuser_orderid = (TextView) findViewById(R.id.tv_currentuser_orderid);
		tv_currentuser_ywname = (TextView) findViewById(R.id.tv_currentuser_ywname);
		tv_currentuser_money = (TextView) findViewById(R.id.tv_currentuser_money);
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
			}
		});
		 btn_pay.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String check = "(\\d{11})$|^(\\d{3,5}[-]?\\d{6,8})$"; 
				//^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
				 Boolean b = et_currentuser_tele.getText().toString().matches(check);
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
							Insert();
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
	private void Insert() {
		// TODO Auto-generated method stub
		 ContentValues values = new ContentValues();  
         // 向该对象中插入键值对，其中键是列名，值是希望插入到这一列的值，值必须和数据库当中的数据类型一致  
		 values.put("id", tv_currentuser_orderid.getText().toString());  
         values.put("name", etOrderName.getText().toString()); 
         values.put("xhnum", et_currentuser_name.getText().toString()); 
         values.put("tele", et_currentuser_tele.getText().toString()); 
         values.put("type", tv_currentuser_ywname.getText().toString()); 
         values.put("pay", tv_currentuser_money.getText().toString()); 
         // 创建DatabaseHelper对象  
         DatabaseHelper dbHelper = new DatabaseHelper(OrderActivity.this,  
                 "xh_order_db", 2);  
         // 得到一个可写的SQLiteDatabase对象  
         SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();  
         // 调用insert方法，就可以将数据插入到数据库当中  
         // 第一个参数:表名称  
         // 第二个参数：SQl不允许一个空列，如果ContentValues是空的，那么这一列被明确的指明为NULL值  
         // 第三个参数：ContentValues对象  
         sqliteDatabase.insert("orders", null, values);
        System.out.println("-----------------------------------");
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

	
}
