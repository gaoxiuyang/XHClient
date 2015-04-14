package com.xuanhui;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.xuanhui.util.Constant;
import com.xuanhui.view.ChildViewPager;
import com.xuanhui.view.CircularImage;
import com.xuanhui.view.MyView;
import com.xuanhui.view.PullScrollView;
public class MainActivity extends SlidingFragmentActivity implements PullScrollView.OnTurnListener,OnPageChangeListener {
	public static MainActivity instance = null;
	private Fragment mContent;
	private ViewPager mTabPager;	
	private ImageView mTab1,mTab2,mTab3;
	private int zero = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int one;//����ˮƽ����λ��
	private int two;
	private PopupWindow mPopupWindow;
	private View mPopupAddPhotos;
	private LinearLayout mBtnCamera;
	private LinearLayout mBtnAlbums;
	private LinearLayout mBtnCancel;
	private int three;
	private LinearLayout mClose;
    private LinearLayout mCloseBtn;
    private MyView myView;  
    private RelativeLayout mRlLoginNo,mRlLoginOK;
    private View layout;	
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;
    private PullScrollView mScrollView;
    private ImageView mHeadImg;
    private boolean isFirst = true;
    boolean isExit; 
    /**
	 * ViewPage
	 */
	 private List<ImageView> imageViewList;  
	 private TextView tvDescription;  
	 private LinearLayout llPoints;
	 private String[] imageDescriptions;  
	 private int previousSelectPosition = 0;  
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
		   //sm.setSelectorBitmap(bitmap);
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
//        btn_hot_100_pag,btn_100_pag,btn_50_pag_1,btn_50_pag_2,btn_50_pag_3;
//        btn_gunagmao_pag
//        btn_100_pag
//        btn_100_pag
        
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
        btn_gunagmao_pag.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Constant.login == 0) {
					Toast.makeText(MainActivity.this, "����δ��¼,���¼������", 1000).show();
				}else if (Constant.login == 1) {
					Intent intent = new Intent(MainActivity.this, OrderActivity.class);
					MainActivity.this.startActivity(intent);
				}
			}
		});
        btn_hot_gm_pag.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Constant.login == 0) {
					Toast.makeText(MainActivity.this, "����δ��¼,���¼������", 1000).show();
				}else if (Constant.login == 1) {
					Intent intent = new Intent(MainActivity.this, OrderActivity.class);
					MainActivity.this.startActivity(intent);
				}
			}
		});
        btn_hot_100_pag.setOnClickListener(new OnClickListener() {
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				if (Constant.login == 0) {
    					Toast.makeText(MainActivity.this, "����δ��¼,���¼������", 1000).show();
    				}else if (Constant.login == 1) {
    					Intent intent = new Intent(MainActivity.this, OrderActivity.class);
    					MainActivity.this.startActivity(intent);
    				}
    			}
    		});
        btn_50_pag_1.setOnClickListener(new OnClickListener() {
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				if (Constant.login == 0) {
    					Toast.makeText(MainActivity.this, "����δ��¼,���¼������", 1000).show();
    				}else if (Constant.login == 1) {
    					Intent intent = new Intent(MainActivity.this, OrderActivity.class);
    					MainActivity.this.startActivity(intent);
    				}
    			}
    		});
        btn_50_pag_2.setOnClickListener(new OnClickListener() {
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				if (Constant.login == 0) {
    					Toast.makeText(MainActivity.this, "����δ��¼,���¼������", 1000).show();
    				}else if (Constant.login == 1) {
    					Intent intent = new Intent(MainActivity.this, OrderActivity.class);
    					MainActivity.this.startActivity(intent);
    				}
    			}
    		});
        btn_50_pag_3.setOnClickListener(new OnClickListener() {
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				if (Constant.login == 0) {
    					Toast.makeText(MainActivity.this, "����δ��¼,���¼������", 1000).show();
    				}else if (Constant.login == 1) {
    					Intent intent = new Intent(MainActivity.this, OrderActivity.class);
    					MainActivity.this.startActivity(intent);
    				}
    			}
    		});
        btn_100_pag.setOnClickListener(new OnClickListener() {
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				if (Constant.login == 0) {
    					Toast.makeText(MainActivity.this, "����δ��¼,���¼������", 1000).show();
    				}else if (Constant.login == 1) {
    					Intent intent = new Intent(MainActivity.this, OrderActivity.class);
    					MainActivity.this.startActivity(intent);
    				}
    			}
    		});
        btn_20_pag_1.setOnClickListener(new OnClickListener() {
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				if (Constant.login == 0) {
    					Toast.makeText(MainActivity.this, "����δ��¼,���¼������", 1000).show();
    				}else if (Constant.login == 1) {
    					Intent intent = new Intent(MainActivity.this, OrderActivity.class);
    					MainActivity.this.startActivity(intent);
    				}
    			}
    		});
        btn_20_pag_2.setOnClickListener(new OnClickListener() {
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				if (Constant.login == 0) {
    					Toast.makeText(MainActivity.this, "����δ��¼,���¼������", 1000).show();
    				}else if (Constant.login == 1) {
    					Intent intent = new Intent(MainActivity.this, OrderActivity.class);
    					MainActivity.this.startActivity(intent);
    				}
    			}
    		});
        btn_6_pag_1.setOnClickListener(new OnClickListener() {
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				if (Constant.login == 0) {
    					Toast.makeText(MainActivity.this, "����δ��¼,���¼������", 1000).show();
    				}else if (Constant.login == 1) {
    					Intent intent = new Intent(MainActivity.this, OrderActivity.class);
    					MainActivity.this.startActivity(intent);
    				}
    			}
    		});
        btn_6_pag_2.setOnClickListener(new OnClickListener() {
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				if (Constant.login == 0) {
    					Toast.makeText(MainActivity.this, "����δ��¼,���¼������", 1000).show();
    				}else if (Constant.login == 1) {
    					Intent intent = new Intent(MainActivity.this, OrderActivity.class);
    					MainActivity.this.startActivity(intent);
    				}
    			}
    		});
        btn_6_pag_3.setOnClickListener(new OnClickListener() {
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				if (Constant.login == 0) {
    					Toast.makeText(MainActivity.this, "����δ��¼,���¼������", 1000).show();
    				}else if (Constant.login == 1) {
    					Intent intent = new Intent(MainActivity.this, OrderActivity.class);
    					MainActivity.this.startActivity(intent);
    				}
    			}
    		});
        
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
				String check = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";    
				// String bb = etMail.getText().toString();
				 Boolean b = etMail.getText().toString().matches(check);
				 if (Constant.login == 0) {
					Toast.makeText(MainActivity.this, "�����ȵ�¼,���ύ���޵�", 1000).show();
				}else if (Constant.login == 1) {
					if (TextUtils.isEmpty(etAddress.getText())) {
						Toast.makeText(MainActivity.this, "��ַΪ�գ������������", Toast.LENGTH_SHORT).show();
					}else if (TextUtils.isEmpty(etName.getText())) {
						Toast.makeText(MainActivity.this, "�û���Ϊ�գ������������", Toast.LENGTH_SHORT).show();
					}else if (TextUtils.isEmpty(etTelNum.getText())) {
						Toast.makeText(MainActivity.this, "�绰��Ϊ�գ������������", Toast.LENGTH_SHORT).show();
					}else if (TextUtils.isEmpty(etMail.getText())) {
						Toast.makeText(MainActivity.this, "�����ʼ�Ϊ�գ������������", Toast.LENGTH_SHORT).show();
					}else if (TextUtils.isEmpty(etMisDes.getText())) {
						Toast.makeText(MainActivity.this, "��������Ϊ�գ������������", Toast.LENGTH_SHORT).show();
					}else if (b == false){
						Toast.makeText(MainActivity.this, "�����ַ��������������", Toast.LENGTH_SHORT).show();
					}else {
						Toast.makeText(MainActivity.this, "�ύ�ɹ�", 1000).show();
						etAddress.setText("");
						etName.setText("");
						etTelNum.setText("");
						etMail.setText("");
						etMisDes.setText("");
					}
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
        mUserAvatar = (ImageView) view3.findViewById(R.id.user_avatar);
        mRlLoginNo = (RelativeLayout) view3.findViewById(R.id.rl_login_no);
        mRlLoginOK = (RelativeLayout) view3.findViewById(R.id.rl_login_ok);
        mBuLoginOk = (Button) view3.findViewById(R.id.btn_login_ok);
        mBuLoginOk.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
        if (Constant.login == 0) {
        	mRlLoginNo.setVisibility(view3.VISIBLE);
        	mRlLoginOK.setVisibility(view3.GONE);
		}else if (Constant.login == 1) {
			mRlLoginNo.setVisibility(view3.GONE);
        	mRlLoginOK.setVisibility(view3.VISIBLE);
		}
        myView = (MyView)view3.findViewById(R.id.my_view);  
        myView.setRatio(40f, 60f,"��ʹ��", "δʹ��");  
        mScrollView = (PullScrollView)view3.findViewById(R.id.scroll_view);
       mHeadImg = (ImageView)view3.findViewById(R.id.background_img1);
       
       
       
   
    	    
    	   Bitmap bitmap1=readBitMap(MainActivity.this,R.drawable.scrollview_header);
    	   mHeadImg.setImageBitmap(bitmap1);
       
       
       
     //  mHeadImg.setImageResource(R.drawable.a006);
        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
        showTable();
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
	 /* ҳ���л�����(ԭ����:D.Winter)
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
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_normal));
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
					
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			animation.setDuration(150);
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
	private ViewPager advPager;
	private Button mButCall;
	private SlidingMenu sm;
	private Button mBuLoginOk;
	public CircularImage cover_user_photo1;
	private ImageView mUserAvatar;
	private ImageView ivImageMis;
	private View view2;
	private RelativeLayout mRl100M,mRlGuangmao,mRl50M,mRl20M,mRl6M;
	private View ilG;
	private View il100;
	private View il50;
	private View il20;
	private View il6;
	private View ilH100;
	private View ilguangmao;
	private View view1;
	private Button btn_hot_gm_pag, btn_gunagmao_pag,btn_hot_100_pag,btn_100_pag,btn_50_pag_1,btn_50_pag_2,btn_50_pag_3,btn_20_pag_1,btn_20_pag_2,btn_6_pag_1,btn_6_pag_2,btn_6_pag_3;
	private View view3;
	private ScrollView contentView;
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
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
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
