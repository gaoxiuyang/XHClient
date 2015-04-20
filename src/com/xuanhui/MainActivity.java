package com.xuanhui;
import java.io.File;

import java.io.InputStream;
import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import cn.buaa.myweixin.R;
import cn.buaa.myweixin.R.color;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.xuanhui.Fragment.MenuFragment;
import com.xuanhui.helper.Utility;
import com.xuanhui.sqlite.DatabaseHelper;
import com.xuanhui.util.Constant;
import com.xuanhui.view.ChildViewPager;
import com.xuanhui.view.CircularImage;
import com.xuanhui.view.MyView;
import com.xuanhui.view.PullScrollView;
public class MainActivity extends SlidingFragmentActivity implements PullScrollView.OnTurnListener,OnPageChangeListener {
	public static MainActivity instance = null;
	private LinearLayout llPoints,llWeFuKuan,llWeiAnZhuang,llYiWanCheng,llPayOk,llAnZhuangOk,llWanChengOk,mBtnCamera,mBtnAlbums,mBtnCancel,mClose,mCloseBtn;
	private TextView tvDescription,tv_all_order,tv_qufukuan1,tv_qufukuan2,tv_tele_cuian;
	private Fragment mContent;
	private ViewPager mTabPager,advPager;	
	private ImageView mTab1,mTab2,mTab3,ivImageMis;
	private int zero = 0,currIndex = 0,one,two,three,previousSelectPosition = 0;// ����ͼƬƫ����
	private PopupWindow mPopupWindow,menuWindow;
	private boolean menu_display = false;
	private LayoutInflater inflater;
	private SlidingMenu sm;
	public CircularImage cover_user_photo1;
	private RelativeLayout mRl100M,mRlGuangmao,mRl50M,mRl20M,mRl6M,rl_kuandai,rl_huodong,rl_youxain,rl_jifen;
	private View ilG,il100,il50,il20,il6,ilH100,ilguangmao,view1,view3,mPopupAddPhotos,view2,layout,ilWeiFuKuan,ilWeiAnHuang,ilYiWanCheng;
	private Button btn_hot_gm_pag, btn_gunagmao_pag,btn_hot_100_pag,btn_100_pag,btn_50_pag_1,btn_50_pag_2,btn_50_pag_3,btn_20_pag_1,btn_20_pag_2,btn_6_pag_1,btn_6_pag_2,btn_6_pag_3,mButCall;
	private ScrollView contentView;
    private boolean isFirst = true;
    boolean isExit; 
	private List<ImageView> imageViewList;  
	private String[] imageDescriptions;  
	private ChildViewPager mViewPager;  
	private boolean isLoop = true; 
	private Handler handler = new Handler() {  
	        @Override  
	        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
	            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);  
	        }  
	 };  
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_weixin);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
         //����activityʱ���Զ����������
        instance = this;
        RelativeLayout rlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        cover_user_photo1 = (CircularImage) findViewById(R.id.cover_user_photo);  
        if (Constant.login == 0) {
        	cover_user_photo1.setImageResource(R.drawable.icon_avatar_user); 
		}else if (Constant.login == 1) {
			cover_user_photo1.setImageResource(R.drawable.ic_callpage_avatar_stud_boy); 
		}
        if (findViewById(R.id.menu_frame) == null) {
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			mTabPager = (ViewPager)findViewById(R.id.tabpager);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			View v = new View(this);
			setBehindContentView(v);
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuFragment()).commit();
		sm = getSlidingMenu();
			cover_user_photo1.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sm.showMenu();
			}
		});
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeEnabled(false);
		sm.setBehindScrollScale(0.25f);
		sm.setFadeDegree(0.25f);
		   Bitmap bitmap=readBitMap(MainActivity.this,R.drawable.img_frame_background);
		   Drawable drawable =new BitmapDrawable(bitmap);
		sm.setBackground(drawable);
		sm.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.25 + 0.75);
				canvas.scale(scale, scale, -canvas.getWidth() / 2,
						canvas.getHeight() / 2);
			}
		});
		sm.setAboveCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (1 - percentOpen * 0.25);
				canvas.scale(scale, scale, 0, canvas.getHeight() / 2);
			}
		});
        mTabPager = (ViewPager)findViewById(R.id.tabpager);
        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mTab1 = (ImageView) findViewById(R.id.img_weixin);
        mTab2 = (ImageView) findViewById(R.id.img_address);
        mTab3 = (ImageView) findViewById(R.id.img_friends);
        mTab1.setOnClickListener(new MyOnClickListener(0));
        mTab2.setOnClickListener(new MyOnClickListener(1));
        mTab3.setOnClickListener(new MyOnClickListener(2));
        Display currDisplay = getWindowManager().getDefaultDisplay();//��ȡ��Ļ��ǰ�ֱ���
        int displayWidth = currDisplay.getWidth();
        int displayHeight = currDisplay.getHeight();
        one = displayWidth/4; //����ˮƽ����ƽ�ƴ�С
        two = one*2;
        three = one*3;
        LayoutInflater mLi = LayoutInflater.from(this);
        view1 = mLi.inflate(R.layout.main_tab_weixin, null);
        mRl100M = (RelativeLayout) view1.findViewById(R.id.rl_100m1);
        mRlGuangmao = (RelativeLayout) view1.findViewById(R.id.rl_guangmao);
        mRl50M = (RelativeLayout) view1.findViewById(R.id.rl_50m);
        mRl20M = (RelativeLayout) view1.findViewById(R.id.rl_20m);
        mRl6M = (RelativeLayout) view1.findViewById(R.id.rl_6m);
        ilG = view1.findViewById(R.id.il_guangmao);
        il100 = view1.findViewById(R.id.il_100m);
        il50 = view1.findViewById(R.id.il_50m);
        il20 = view1.findViewById(R.id.il_20m);
        il6 = view1.findViewById(R.id.il_6m);
        ilH100 = view1.findViewById(R.id.hot_100m);
        ilguangmao = view1.findViewById(R.id.hot_gangmao);
        btn_gunagmao_pag = (Button) ilG.findViewById(R.id.btn_gunagmao_pag);
        btn_hot_100_pag = (Button) ilH100.findViewById(R.id.btn_hot_100_pag);
        btn_100_pag = (Button) il100.findViewById(R.id.btn_100_pag);
        btn_50_pag_1 = (Button) il50.findViewById(R.id.btn_50_pag_1);
        btn_50_pag_2 = (Button) il50.findViewById(R.id.btn_50_pag_2);
        btn_50_pag_3 = (Button) il50.findViewById(R.id.btn_50_pag_3);
        btn_20_pag_1 = (Button) il20.findViewById(R.id.btn_20_pag_1);
        btn_20_pag_2 = (Button) il20.findViewById(R.id.btn_20_pag_2);
        btn_6_pag_1 = (Button) il6.findViewById(R.id.btn_6_pag_1);
        btn_6_pag_2 = (Button) il6.findViewById(R.id.btn_6_pag_2);
        btn_6_pag_3 = (Button) il6.findViewById(R.id.btn_6_pag_3);
        btn_hot_gm_pag = (Button) ilguangmao.findViewById(R.id.btn_hot_gm_pag);
        //���붩������
        OnClickListener onClick = new OnClickListener(){
            public void onClick(View v) {
                switch (v.getId()) {
                  case R.id.btn_gunagmao_pag: 
                	  Intent intent = new Intent(MainActivity.this, OrderActivity.class);
  					  MainActivity.this.startActivity(intent);
                  break;
                  case R.id.btn_hot_100_pag: 
                	  Intent intent1 = new Intent(MainActivity.this, OrderActivity.class);
  					  MainActivity.this.startActivity(intent1);
                  break;
                  case R.id.btn_100_pag: 
                	  Intent intent2 = new Intent(MainActivity.this, OrderActivity.class);
  					  MainActivity.this.startActivity(intent2);
                	                  break;
                  case R.id.btn_50_pag_1: 
                	  Intent intent3 = new Intent(MainActivity.this, OrderActivity.class);
  					  MainActivity.this.startActivity(intent3);
                	                  break;
                  case R.id.btn_50_pag_2: 
                	  Intent intent4 = new Intent(MainActivity.this, OrderActivity.class);
  					  MainActivity.this.startActivity(intent4);
                	                  break;
                  case R.id.btn_50_pag_3: 
                	  Intent intent5 = new Intent(MainActivity.this, OrderActivity.class);
  					  MainActivity.this.startActivity(intent5);
                	                  break;
                  case R.id.btn_20_pag_1: 
                	  Intent intent6 = new Intent(MainActivity.this, OrderActivity.class);
  					  MainActivity.this.startActivity(intent6);
                	                  break;
                  case R.id.btn_20_pag_2: 
                	  Intent intent7 = new Intent(MainActivity.this, OrderActivity.class);
  					  MainActivity.this.startActivity(intent7);
                	                  break;
                  case R.id.btn_6_pag_1: 
                	  Intent intent8 = new Intent(MainActivity.this, OrderActivity.class);
  					  MainActivity.this.startActivity(intent8);
                	                  break;
                  case R.id.btn_6_pag_2: 
                	  Intent intent9 = new Intent(MainActivity.this, OrderActivity.class);
  					  MainActivity.this.startActivity(intent9);
                	                  break;
                  case R.id.btn_6_pag_3: 
                	  Intent intent0 = new Intent(MainActivity.this, OrderActivity.class);
  					  MainActivity.this.startActivity(intent0);
                	                  break;
                  case R.id.btn_hot_gm_pag: 
                	  Intent intent11 = new Intent(MainActivity.this, OrderActivity.class);
  					  MainActivity.this.startActivity(intent11);
                	                  break;
               }
            }
        };
        btn_gunagmao_pag.setOnClickListener(onClick);
        btn_hot_100_pag.setOnClickListener(onClick);
        btn_100_pag.setOnClickListener(onClick);
        btn_50_pag_1.setOnClickListener(onClick);
        btn_50_pag_2.setOnClickListener(onClick);
        btn_50_pag_3.setOnClickListener(onClick);
        btn_20_pag_1.setOnClickListener(onClick);
        btn_20_pag_2.setOnClickListener(onClick);
        btn_6_pag_1.setOnClickListener(onClick);
        btn_6_pag_2.setOnClickListener(onClick);
        btn_6_pag_3.setOnClickListener(onClick);
        btn_hot_gm_pag.setOnClickListener(onClick);
        mRl100M.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ilH100.setVisibility(view1.GONE);
				ilguangmao.setVisibility(view1.GONE);
				il100.setVisibility(view1.VISIBLE);
				il50.setVisibility(view1.GONE);
				il20.setVisibility(view1.GONE);
				il6.setVisibility(view1.GONE);
				ilG.setVisibility(view1.GONE);
			}
		});
        mRlGuangmao.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ilH100.setVisibility(view1.GONE);
				ilguangmao.setVisibility(view1.GONE);
				ilG.setVisibility(view1.VISIBLE);
				il50.setVisibility(view1.GONE);
				il20.setVisibility(view1.GONE);
				il6.setVisibility(view1.GONE);
				il100.setVisibility(view1.GONE);
			}
		});
        mRl50M.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ilH100.setVisibility(view1.GONE);
				ilguangmao.setVisibility(view1.GONE);
				il50.setVisibility(view1.VISIBLE);
				ilG.setVisibility(view1.GONE);
				il20.setVisibility(view1.GONE);
				il6.setVisibility(view1.GONE);
				il100.setVisibility(view1.GONE);
			}
		});
        mRl20M.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ilH100.setVisibility(view1.GONE);
				ilguangmao.setVisibility(view1.GONE);
				il20.setVisibility(view1.VISIBLE);
				il50.setVisibility(view1.GONE);
				il100.setVisibility(view1.GONE);
				il6.setVisibility(view1.GONE);
				ilG.setVisibility(view1.GONE);
			}
		});
        mRl6M.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ilH100.setVisibility(view1.GONE);
				ilguangmao.setVisibility(view1.GONE);
				il6.setVisibility(view1.VISIBLE);
				il50.setVisibility(view1.GONE);
				il20.setVisibility(view1.GONE);
				il100.setVisibility(view1.GONE);
				ilG.setVisibility(view1.GONE);
			}
		});
        View ilTele = view1.findViewById(R.id.il_tele);
        mViewPager = (ChildViewPager)view1.findViewById(R.id.adv_pager);
	      tvDescription = (TextView)view1.findViewById(R.id.tv_image_description);  
	      llPoints = (LinearLayout)view1.findViewById(R.id.ll_points);
       //���ͼ����
        setView(); 
        //���ͼ����
	    initView();
        mButCall = (Button)ilTele.findViewById(R.id.btn_login11);
        mButCall.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		        	   Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "0316-3317606")); 
		               MainActivity.this.startActivity(intent);//�ڲ���
			}
		});
        view2 = mLi.inflate(R.layout.main_tab_address, null);
        View ilMistake = view2.findViewById(R.id.il_mistake);
        contentView = (ScrollView) view2.findViewById(R.id.contentView);
        View ilMis = view2.findViewById(R.id.il_mis);
        final EditText etAddress = (EditText)ilMis.findViewById(R.id.et_address);
        final EditText etName = (EditText)ilMis.findViewById(R.id.et_name);
        final EditText etTelNum = (EditText) ilMis.findViewById(R.id.et_tel_num);
        final EditText etMail = (EditText)ilMis.findViewById(R.id.et_mail);
        final EditText etMisDes = (EditText)ilMis.findViewById(R.id.et_mis_des);
        etAddress.setOnFocusChangeListener(this.onFocusAutoClearHintListener);
        etName.setOnFocusChangeListener(this.onFocusAutoClearHintListener);
        etTelNum.setOnFocusChangeListener(this.onFocusAutoClearHintListener);
        etMail.setOnFocusChangeListener(this.onFocusAutoClearHintListener);
        etMisDes.setOnFocusChangeListener(this.onFocusAutoClearHintListener);
        ivImageMis = (ImageView)ilMis.findViewById(R.id.iv_image_mis);
        ivImageMis.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ShowPickDialog();
			}
		});
        Button buttonOK = (Button)view2.findViewById(R.id.btn_login11);
        buttonOK.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String check = "^(\\d{11})$|^(\\d{3,5}[-]?\\d{6,8})$"; 
				//^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
				 Boolean b = etTelNum.getText().toString().matches(check);
					if (TextUtils.isEmpty(etAddress.getText())) {
						Toast.makeText(MainActivity.this, "��ַΪ�գ������������", Toast.LENGTH_SHORT).show();
					}else if (TextUtils.isEmpty(etName.getText())) {
						Toast.makeText(MainActivity.this, "�û���Ϊ�գ������������", Toast.LENGTH_SHORT).show();
					}else if (TextUtils.isEmpty(etTelNum.getText())) {
						Toast.makeText(MainActivity.this, "�绰��Ϊ�գ������������", Toast.LENGTH_SHORT).show();
					}else if (b == false){
						Toast.makeText(MainActivity.this, "�绰������������������", Toast.LENGTH_SHORT).show();
					}else {
						Toast.makeText(MainActivity.this, "�ύ�ɹ�", 1000).show();
						etAddress.setText("");
						etName.setText("");
						etTelNum.setText("");
						etMail.setText("");
						etMisDes.setText("");
					}
				}
		});
        Button mButLioginView = (Button)ilMistake.findViewById(R.id.btn_login2);
        mButLioginView.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		        	   Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "0316-3317606")); 
		               MainActivity.this.startActivity(intent);//�ڲ���
			}
		});
        view3 = mLi.inflate(R.layout.main_tab_friends, null);
        tv_all_order = (TextView) view3.findViewById(R.id.tv_all_order);
        tv_all_order.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, AllOrderActivity.class);
    	    	MainActivity.this.startActivity(intent);
			}
		});
        rl_kuandai = (RelativeLayout) view3.findViewById(R.id.rl_kuandai);
        rl_youxain = (RelativeLayout) view3.findViewById(R.id.rl_youxain);
        rl_jifen = (RelativeLayout) view3.findViewById(R.id.rl_jifen);
        rl_huodong = (RelativeLayout) view3.findViewById(R.id.rl_huodong);
        llWeFuKuan = (LinearLayout) view3.findViewById(R.id.ll_weifuluan);
        llWeiAnZhuang = (LinearLayout) view3.findViewById(R.id.ll_weiyanzhaung);
        llYiWanCheng = (LinearLayout) view3.findViewById(R.id.ll_yiwangcheng);
        llPayOk = (LinearLayout) view3.findViewById(R.id.ll_pay_OK);
        llAnZhuangOk = (LinearLayout) view3.findViewById(R.id.ll_anzhuang_OK);
        llWanChengOk = (LinearLayout) view3.findViewById(R.id.ll_wancheng_OK);
        ilWeiFuKuan = view3.findViewById(R.id.il_weifukuan);
        ilWeiAnHuang = view3.findViewById(R.id.il_weianzhaung);
        ilYiWanCheng = view3.findViewById(R.id.il_yiwancheng);
        tv_qufukuan1 = (TextView) ilWeiFuKuan.findViewById(R.id.tv_qufukuan1);
        tv_qufukuan2 = (TextView) ilWeiFuKuan.findViewById(R.id.tv_qufukuan2);
        tv_tele_cuian = (TextView) ilYiWanCheng.findViewById(R.id.tv_tele_cuian);
        tv_qufukuan1.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "��ת��֧����", 1000).show();
			}
		});
        tv_qufukuan2.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "��ת��֧����", 1000).show();
			}
		});
        tv_tele_cuian.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				  Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "0316-3317606")); 
	               MainActivity.this.startActivity(intent);//�ڲ���
			}
		});
        rl_youxain.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "�ù�����δ����,�����ڴ�", 1000).show();
			}
		});
        rl_jifen.setOnClickListener(new OnClickListener() {
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				Toast.makeText(MainActivity.this, "�ù�����δ����,�����ڴ�", 1000).show();
    			}
    		});
        rl_huodong.setOnClickListener(new OnClickListener() {
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				Toast.makeText(MainActivity.this, "�ù�����δ����,�����ڴ�", 1000).show();
    			}
    		});
        rl_kuandai.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Constant.login == 0) {
					new AlertDialog.Builder(MainActivity.this)
					.setTitle("����û�е�¼,�Ƿ��¼")
					.setNegativeButton("��¼", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Intent intent = new Intent(MainActivity.this, LoginActivity.class);
							MainActivity.this.startActivity(intent);
						}
					})
					.setPositiveButton("ȡ��", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							dialog.dismiss();
						}
					}).show();
				}else if (Constant.login == 1) {
					Intent intent = new Intent(MainActivity.this, MineActivity.class);
					MainActivity.this.startActivity(intent);
				}
			}
		});
        llWeFuKuan.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				llPayOk.setBackgroundColor(Color.parseColor("#16a0e8"));
				llAnZhuangOk.setBackgroundResource(R.drawable.bg_edittext);
				llWanChengOk.setBackgroundResource(R.drawable.bg_edittext);
				ilWeiFuKuan.setVisibility(view3.VISIBLE);
				ilWeiAnHuang.setVisibility(view3.GONE);
				ilYiWanCheng.setVisibility(view3.GONE);
			}
		});
        llWeiAnZhuang.setOnClickListener(new OnClickListener() {
     			public void onClick(View arg0) {
     				// TODO Auto-generated method stub
     				llAnZhuangOk.setBackgroundColor(Color.parseColor("#16a0e8"));
     				llPayOk.setBackgroundResource(R.drawable.bg_edittext);
    				llWanChengOk.setBackgroundResource(R.drawable.bg_edittext);
    				ilYiWanCheng.setVisibility(view3.VISIBLE);
    				ilWeiFuKuan.setVisibility(view3.GONE);
    				ilWeiAnHuang.setVisibility(view3.GONE);
     			}
     		});
        llYiWanCheng.setOnClickListener(new OnClickListener() {
     			public void onClick(View arg0) {
     				// TODO Auto-generated method stub
     				llWanChengOk.setBackgroundColor(Color.parseColor("#16a0e8"));
    				llAnZhuangOk.setBackgroundResource(R.drawable.bg_edittext);
    				llPayOk.setBackgroundResource(R.drawable.bg_edittext);
    				ilWeiAnHuang.setVisibility(view3.VISIBLE);
    				ilYiWanCheng.setVisibility(view3.GONE);
    				ilWeiFuKuan.setVisibility(view3.GONE);
     			}
     		});
       //ÿ��ҳ���view����
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
      //���ViewPager������������
        PagerAdapter mPagerAdapter = new PagerAdapter() {
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			public int getCount() {
				return views.size();
			}
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};
		mTabPager.setAdapter(mPagerAdapter);
    }
    public static Bitmap readBitMap(Context context, int resId) {
 	   BitmapFactory.Options opt = new BitmapFactory.Options();
 	   opt.inPreferredConfig = Bitmap.Config.RGB_565;
 	   opt.inPurgeable = true;
 	   opt.inInputShareable = true;
 	   // ��ȡ��ԴͼƬ
 	   InputStream is = context.getResources().openRawResource(resId);
 	   return BitmapFactory.decodeStream(is, null, opt);
 	   }
    /**
     * ���ViewPage
     */
    public void setView() {  
	     //  setContentView(R.layout.activity_splash_viewpager);  
	        // �Զ��л�ҳ�湦��   
	        new Thread(new Runnable() {  
	           public void run() {  
               while (isLoop) {  
                   SystemClock.sleep(4000);  
	                    handler.sendEmptyMessage(0);  
	                }  
	            }  
	        }).start();  
	    } 
    public void initView() {  
	      prepareData();  
      	  ViewPagerAdapter adapter = new ViewPagerAdapter(imageViewList);  
	      mViewPager.setAdapter(adapter);  
	      mViewPager.setOnPageChangeListener(this);  
	      tvDescription.setText(imageDescriptions[previousSelectPosition]);  
	      llPoints.getChildAt(previousSelectPosition).setEnabled(true);  
	        /** 
	         * 2147483647 / 2 = 1073741820 - 1  
	         * ����ViewPager�ĵ�ǰ��Ϊһ���Ƚϴ�������Ա�һ��ʼ�Ϳ�������ѭ������ 
	         */  
	        int n = Integer.MAX_VALUE / 2 % imageViewList.size();  
	        int itemPosition = Integer.MAX_VALUE / 2 - n;  
	       mViewPager.setCurrentItem(itemPosition);  
	   }
    private void prepareData() {  
    	 imageViewList = new ArrayList<ImageView>();  
         int[] imageResIDs = getImageResIDs();  
         imageDescriptions = getImageDescription();  
         ImageView iv;  
         View view;  
         for (int i = 0; i < imageResIDs.length; i++) {  
             iv = new ImageView(this);  
             iv.setBackgroundResource(imageResIDs[i]);  
         imageViewList.add(iv);  
             // ��ӵ�view����   
         view = new View(this); 
         //view.setBackgroundResource(R.drawable.point_background);
         view.setBackgroundDrawable(getResources().getDrawable(R.drawable.point_background));  
            LayoutParams lp = new LayoutParams(5, 5);  
            lp.leftMargin = 10;
            view.setLayoutParams(lp);  
            view.setEnabled(false);  
            llPoints.addView(view);  
         }  
     }
    private int[] getImageResIDs() {  
        return new int[]{  
                R.drawable.a002,  
                R.drawable.a003,  
                R.drawable.a004,  
                R.drawable.a005,
                R.drawable.a002
        };  
}  
    private String[] getImageDescription() {  
        return new String[]{  
                "�⣬������������ҫ����",  
                "�ù��˿���������п���",  
                "Ϊ�����ֵ������",  
                "AndroidGps",  
                "�⣬������������ҫ����"  
       };  
   }
    class ViewPagerAdapter extends PagerAdapter {  
	    private List<ImageView> mImageViewList;  
    public ViewPagerAdapter(List<ImageView> imageViewList) {  
	        super();  
        this.mImageViewList = imageViewList;  
	    }  
    /** 
	     * �÷����������������� Item�ܸ�����Ϊ��ʵ��һ��ѭ��������Ч���������˻������͵����ֵ�������ͻᴴ���ܶ��Item, 
	     * ��ʵ�Ⲣ��������������ѭ���� 
	     */  
	    @Override  
	    public int getCount() {  
	        return Integer.MAX_VALUE;  
    }  
	         /** 
	     * �жϳ�ȥ��view�Ƿ���ڽ�����view ���Ϊtrueֱ�Ӹ��� 
	     */  
	    @Override  
	    public boolean isViewFromObject(View arg0, Object arg1) {  
        return arg0 == arg1;  
	    }  
	    /** 
	     * ����Ԥ���������view����, �����Ҫ���ٵĶ��������λ�ô�����������position�� 
	     * ����ʹ��ȡ�����ķ�������ȡÿһ������� 
	    */  
	    @Override  
	    public void destroyItem(ViewGroup container, int position, Object object) {  
	        container.removeView(mImageViewList.get(position % mImageViewList.size()));  
	    }  
	   /** 
	     * ����һ��view�� 
	     */  
	    @Override  
    public Object instantiateItem(ViewGroup container, int position) {  
	       // container.addView(mImageViewList.get(position % mImageViewList.size()));  
	        View view = mImageViewList.get(position % mImageViewList.size());
	        view.setOnClickListener(new OnClickListener() {
        	       public void onClick(View v) {
        	        // TODO Auto-generated method stub
        	    	   Toast.makeText(MainActivity.this, "kusdia", 1000).show();
        	    	Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        	    	MainActivity.this.startActivity(intent);
   					//HomeActivity.this.finish();
        	       }
        	      });
	        container.addView(view);  
	        return mImageViewList.get(position % mImageViewList.size()); 
	    }  
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
                    Toast.makeText(MainActivity.this, "Click item " + n, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    /**
	 * ͷ��������
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;
		public MyOnClickListener(int i) {
			index = i;
		}
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};
	 /**
	  *  ҳ���л�����
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_pressed));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_normal));
					ilH100.setVisibility(view1.VISIBLE);
					ilguangmao.setVisibility(view1.VISIBLE);
					il50.setVisibility(view1.GONE);
					il20.setVisibility(view1.GONE);
					il6.setVisibility(view1.GONE);
					ilG.setVisibility(view1.GONE);
					il100.setVisibility(view1.GONE);
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_normal));
					ilH100.setVisibility(view1.VISIBLE);
					ilguangmao.setVisibility(view1.VISIBLE);
					il50.setVisibility(view1.GONE);
					il20.setVisibility(view1.GONE);
					il6.setVisibility(view1.GONE);
					ilG.setVisibility(view1.GONE);
					il100.setVisibility(view1.GONE);
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_normal));
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
				}
				break;
			case 2:
				mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, two, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
					SelectOrder();
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_normal));
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
					SelectOrder();
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			animation.setDuration(150);
		}
	/**
	 * View3��ȡsqlite�еĶ�������	
	 */
	private void SelectOrder() {
			// TODO Auto-generated method stub
				String id = null;  
		        String name = null;  
		        String xhnum = null;
		        String tele = null;
		        String type = null;
		        String pay = null;
		        //����DatabaseHelper����  
		        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this,  
		                "xh_order_db", 2);  
		        // �õ�һ��ֻ����SQLiteDatabase����  
		        SQLiteDatabase sqliteDatabase = dbHelper.getReadableDatabase();  
		        // ����SQLiteDatabase�����query�������в�ѯ������һ��Cursor���������ݿ��ѯ���صĽ��������  
		        // ��һ������String������  
		        // �ڶ�������String[]:Ҫ��ѯ������  
		        // ����������String����ѯ����  
		        // ���ĸ�����String[]����ѯ�����Ĳ���  
		        // ���������String:�Բ�ѯ�Ľ�����з���  
		        // ����������String���Է���Ľ����������  
		        // ���߸�����String���Բ�ѯ�Ľ����������  
		        Cursor cursor = sqliteDatabase.query("orders", new String[] { "id","name","xhnum","tele","type","pay"}, "id=?", new String[] { "TD1839201123" },null, null, null);  
		        // ������ƶ�����һ�У��Ӷ��жϸý�����Ƿ�����һ�����ݣ�������򷵻�true��û���򷵻�false  
		        while (cursor.moveToNext()) {  
		            id = cursor.getString(cursor.getColumnIndex("id"));  
		            name = cursor.getString(cursor.getColumnIndex("name")); 
		            xhnum = cursor.getString(cursor.getColumnIndex("xhnum")); 
		            tele = cursor.getString(cursor.getColumnIndex("tele")); 
		            type = cursor.getString(cursor.getColumnIndex("type")); 
		            pay = cursor.getString(cursor.getColumnIndex("pay")); 
		            System.out.println("-------------select------------");  
			        System.out.println("id: "+id);  
			        System.out.println("name: "+name);
			        System.out.println("xhnum: "+xhnum);  
			        System.out.println("tele: "+tele);  
			        System.out.println("type: "+type);  
			        System.out.println("pay: "+pay);
		        }  
		}
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			public void onPageScrollStateChanged(int arg0) {
			}
	}
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	 if (keyCode == KeyEvent.KEYCODE_BACK) {  
             exit();  
             return false;  
         } else {  
             return super.onKeyDown(keyCode, event);  
         }  
    }
    /**
     * �˳����������ƾ���onKeyDown�е�exit����
     */
    public void exit(){  
            if (!isExit) {  
                isExit = true;  
                Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();  
                mHandler.sendEmptyMessageDelayed(0, 2000);  
            } else {  
                Intent intent = new Intent(Intent.ACTION_MAIN);  
                intent.addCategory(Intent.CATEGORY_HOME);  
                startActivity(intent);  
                System.exit(0);  
            }  
        } 
    Handler mHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            // TODO Auto-generated method stub   
            super.handleMessage(msg);  
            isExit = false;  
        }  
    };
	public void onTurn() {
		// TODO Auto-generated method stub
	}
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		// �ı�ͼƬ��������Ϣ   
        tvDescription.setText(imageDescriptions[position % imageViewList.size()]);  
       // �л�ѡ�еĵ�,��ǰһ������Ϊnormal״̬   
       llPoints.getChildAt(previousSelectPosition).setEnabled(false);  
       // �ѵ�ǰѡ�е�position��Ӧ�ĵ���Ϊenabled״̬   
        llPoints.getChildAt(position % imageViewList.size()).setEnabled(true);  
        previousSelectPosition = position  % imageViewList.size();  
	}
	/**
	 * ���޽����ϴ�ͼƬ
	 */
	private void ShowPickDialog() {
		new AlertDialog.Builder(this)
				.setTitle("���ϴ�����ͼƬ")
				.setNegativeButton("���", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Intent intent = new Intent(Intent.ACTION_PICK, null);
						intent.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								"image/*");
						startActivityForResult(intent, 1);

					}
				})
				.setPositiveButton("����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
						Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						//�������ָ������������պ����Ƭ�洢��·��
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
								.fromFile(new File(Environment
										.getExternalStorageDirectory(),
										"xiaoma.jpg")));
						startActivityForResult(intent, 2);
					}
				}).show();
	}
	/**
	 * EditText��hint�����Զ���ʧ
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
		/**
		 * ������Ƭ���
		 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// �����ֱ�Ӵ�����ȡ
		case 1:
			startPhotoZoom(data.getData());
			break;
		// ����ǵ����������ʱ
		case 2:
			File temp = new File(Environment.getExternalStorageDirectory()
					+ "/xiaoma.jpg");
			startPhotoZoom(Uri.fromFile(temp));
			break;
		// ȡ�òü����ͼƬ
		case 3:
			if(data != null){
				setPicToView(data);
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	 * �ü�ͼƬ����ʵ��
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		//�������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü�
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			ivImageMis.setImageBitmap(photo);
		}
	}
}