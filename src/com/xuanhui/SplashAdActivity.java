package com.xuanhui;

import java.io.Serializable;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import cn.buaa.myweixin.R;

import com.xuanhui.adapter.ADPagerAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class SplashAdActivity extends Activity implements OnTouchListener{
	private static final String SHAREDPREFERENCES_NAME = "first_pref";
	private ViewPager pager_splash_ad;
	private ADPagerAdapter adapter;
	private int flaggingWidth;
	private int size = 0;
	private int lastX = 0;
	private int currentIndex = 0;
	private CirclePageIndicator indicator;
	private boolean locker = true;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_ad);
	
		
        DisplayMetrics dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        flaggingWidth = dm.widthPixels / 2;

        List<String> splash_ad = (List<String>) getIntent()
				.getSerializableExtra("splash_ad");
		pager_splash_ad = (ViewPager) findViewById(R.id.pager_splash_ad);
		
		List<View> views = new ArrayList<View>();
		for (String str : splash_ad) {
			View view = LayoutInflater.from(this).inflate(
					R.layout.view_splash_ad, null);
			ImageView iv_ad = (ImageView) view.findViewById(R.id.iv_ad);
			iv_ad.setImageResource(R.drawable.tt);
			views.add(view);
			View view1 = LayoutInflater.from(this).inflate(
					R.layout.view_splash_ad, null);
			ImageView iv_ad1 = (ImageView) view1.findViewById(R.id.iv_ad);
			iv_ad1.setImageResource(R.drawable.ww);
			views.add(view1);
			View view2 = LayoutInflater.from(this).inflate(
					R.layout.view_splash_ad, null);
			ImageView iv_ad2 = (ImageView) view2.findViewById(R.id.iv_ad);
			iv_ad2.setImageResource(R.drawable.jj);
			views.add(view2);
		}
		size = views.size();

		adapter = new ADPagerAdapter(this, views);
		pager_splash_ad.setAdapter(adapter);
		indicator = (CirclePageIndicator) findViewById(R.id.viewflowindic);
		indicator.setmListener(new MypageChangeListener());
		indicator.setViewPager(pager_splash_ad);
		if (views.size() == 1) {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

			
				public void run() {
					gotoMain();
				}
			}, 1000);
		} else {
//			pager_splash_ad.setOnPageChangeListener(new MypageChangeListener());
			pager_splash_ad.setOnTouchListener(this);
		}
	}

	
	private class MypageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int position) {
//			System.err.println("------position---"+position);
//			currentItem = position;
			
		}

	
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		
		public void onPageSelected(int arg0) {
			currentIndex = arg0;
		}

	}
	
	private void gotoMain(){
		Intent intent = new Intent(SplashAdActivity.this,
				MainActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.alpha_in_anim,
				R.anim.alpha_out_anim);
	}


	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int)event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			if((lastX - event.getX()) > flaggingWidth && (currentIndex == size -1) && locker){
				locker = false;
				System.err.println("-------1111-------");
				gotoMain();
				setGuided();
				
				
			}
			break;
		default:
			break;
		}
		return false;
	}
	private void setGuided() {
		SharedPreferences preferences = this.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		// 存入数据
		editor.putBoolean("isFirstIn", false);
		// 提交修改
		editor.commit();
	}

}
