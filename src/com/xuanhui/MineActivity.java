package com.xuanhui;

import com.xuanhui.view.MyView;
import com.xuanhui.view.PullScrollView;

import cn.buaa.myweixin.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MineActivity extends Activity implements PullScrollView.OnTurnListener {
	
	private MyView myView;
	private PullScrollView mScrollView;
	 private ImageView mHeadImg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine);
		  myView = (MyView)findViewById(R.id.my_view);  
	        myView.setRatio(40f, 60f,"已使用", "未使用");
	        mScrollView = (PullScrollView)findViewById(R.id.scroll_view);
	        mHeadImg = (ImageView)findViewById(R.id.background_img);
	        mScrollView.setHeader(mHeadImg);
	        mScrollView.setOnTurnListener(MineActivity.this);
	        showTable();
		RelativeLayout rlMineBack = (RelativeLayout) findViewById(R.id.rl_mine_back);
		rlMineBack.setOnClickListener(new OnClickListener() {
		
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MineActivity.this.finish();
			}
		});
		
	}
	  public void showTable() {
	        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
	                TableRow.LayoutParams.MATCH_PARENT,
	                TableRow.LayoutParams.WRAP_CONTENT);
	        layoutParams.gravity = Gravity.CENTER;
	        layoutParams.leftMargin = 30;
	        layoutParams.bottomMargin = 10;
	        layoutParams.topMargin = 10;
	        for (int i = 0; i < 30; i++) {
	            TableRow tableRow = new TableRow(this);
	            TextView textView = new TextView(this);
	            textView.setText("Test pull down scroll view " + i);
	            textView.setTextSize(20);
	            textView.setPadding(15, 15, 15, 15);
	            tableRow.addView(textView, layoutParams);
	            if (i % 2 != 0) {
	                tableRow.setBackgroundColor(Color.LTGRAY);
	            } else {
	                tableRow.setBackgroundColor(Color.WHITE);
	            }
	            final int n = i;
	            tableRow.setOnClickListener(new View.OnClickListener() {
	                public void onClick(View v) {
	                   // Toast.makeText(MainActivity.this, "Click item " + n, Toast.LENGTH_SHORT).show();
	                }
	            });
	        }
	    }
	public void onTurn() {
		// TODO Auto-generated method stub
		
	}
}
