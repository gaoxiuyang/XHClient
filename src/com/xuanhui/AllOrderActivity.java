package com.xuanhui;


import cn.buaa.myweixin.R;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AllOrderActivity extends Activity{
	private LinearLayout ll_order1;
	private LinearLayout ll_order2;
	private LinearLayout ll_order3,ll_nothing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_order);
		ll_order1 = (LinearLayout) findViewById(R.id.ll_order1);
		ll_order2 = (LinearLayout) findViewById(R.id.ll_order2);
		ll_order3 = (LinearLayout) findViewById(R.id.ll_order3);
		ll_nothing = (LinearLayout) findViewById(R.id.ll_nothing);
		
		ll_order1.setOnLongClickListener(new OnLongClickListener() {
			
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(AllOrderActivity.this)
				.setTitle("您确定删除该订单吗?")
				.setNegativeButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						ll_order1.setVisibility(View.GONE);
						if (ll_order2.getVisibility()==View.GONE&&ll_order3.getVisibility()==View.GONE ) {
							ll_nothing.setVisibility(View.VISIBLE);
						}
					}
				})
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				}).show();
				return false;
			}
		});
		ll_order2.setOnLongClickListener(new OnLongClickListener() {
			
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(AllOrderActivity.this)
				.setTitle("您确定删除该订单吗?")
				.setNegativeButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						ll_order2.setVisibility(View.GONE);
						if (ll_order1.getVisibility()==View.GONE&&ll_order3.getVisibility()==View.GONE ) {
							ll_nothing.setVisibility(View.VISIBLE);
						}
					}
				})
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				}).show();
				return false;
			}
		});
		ll_order3.setOnLongClickListener(new OnLongClickListener() {
		
		public boolean onLongClick(View arg0) {
			// TODO Auto-generated method stub
			new AlertDialog.Builder(AllOrderActivity.this)
			.setTitle("您确定删除该订单吗?")
			.setNegativeButton("确认", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					ll_order3.setVisibility(View.GONE);
					if (ll_order1.getVisibility()==View.GONE&&ll_order2.getVisibility()==View.GONE) {
						ll_nothing.setVisibility(View.VISIBLE);
					}
				}
			})
			.setPositiveButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
				}
			}).show();
			return false;
			}
		});
		
		RelativeLayout rlTitleAbout = (RelativeLayout) findViewById(R.id.rl_title_all_order);
		rlTitleAbout.setOnClickListener(new OnClickListener() {
		
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AllOrderActivity.this.finish();
			}
		});
	}


}
