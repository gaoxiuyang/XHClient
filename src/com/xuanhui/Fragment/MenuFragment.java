package com.xuanhui.Fragment;
import java.io.File;

import com.xuanhui.AboutActivity;
import com.xuanhui.CallActivity;
import com.xuanhui.LoginActivity;
import com.xuanhui.MainActivity;
import com.xuanhui.MineActivity;
import com.xuanhui.RelationActivity;
import com.xuanhui.util.Constant;
import com.xuanhui.view.CircularImage;

import cn.buaa.myweixin.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
public class MenuFragment extends Fragment {
	private RelativeLayout nLogin,nAbout,nCallBack,nCallMine;
	private RelativeLayout nRlMine,nRlOut;
	private LinearLayout nLLLine;
	private TextView nIvTouXiang;
	private CircularImage cover_user_photo;
	private CircularImage cover_user_photo1;
	//private ImageView user_avatar;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_menu, container, false);
		nLogin = (RelativeLayout) view.findViewById(R.id.ll_login);
		
		nAbout = (RelativeLayout) view.findViewById(R.id.rl_baout);
		nAbout.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), AboutActivity.class);
				getActivity().startActivity(intent);
			}
		});
		nCallBack = (RelativeLayout) view.findViewById(R.id.rl_call_back);
		nCallBack.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), CallActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		nCallMine = (RelativeLayout) view.findViewById(R.id.rl_tel);
		nCallMine.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), RelationActivity.class);
				getActivity().startActivity(intent);
			}
		});
		nRlMine = (RelativeLayout) view.findViewById(R.id.rl_mine);
		nIvTouXiang = (TextView) view.findViewById(R.id.tv_login);
		nRlMine.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), MineActivity.class);
				getActivity().startActivity(intent);
			}
		});
		nLLLine = (LinearLayout) view.findViewById(R.id.ll_line1);
		nRlOut = (RelativeLayout) view.findViewById(R.id.rl_out);
		nRlOut.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Constant.login = 0;
				Intent intent = new Intent(getActivity(), MainActivity.class);
				getActivity().startActivity(intent);
			}
		});
		  cover_user_photo = (CircularImage)view.findViewById(R.id.cover_user_photo1);  
		    if (Constant.login == 0) {
	        	cover_user_photo.setImageResource(R.drawable.icon_avatar_user); 
	        	nRlMine.setVisibility(view.GONE);
	        	nLogin.setOnClickListener(new OnClickListener() {
	    			public void onClick(View arg0) {
	    				// TODO Auto-generated method stub
	    				Intent intent = new Intent(getActivity(), LoginActivity.class);
	    				getActivity().startActivity(intent);
	    			}
	    		});
			}else if (Constant.login == 1) {
				cover_user_photo.setImageResource(R.drawable.ic_callpage_avatar_stud_boy); 
				cover_user_photo.setOnClickListener(new OnClickListener() {
					
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ShowPickDialog();
					}
				});
				nRlMine.setVisibility(view.VISIBLE);
				nLLLine.setVisibility(view.VISIBLE);
				nRlOut.setVisibility(view.VISIBLE);
				nIvTouXiang.setText("����");
				
			}
		return view;
	}
	private void ShowPickDialog() {
		new AlertDialog.Builder(getActivity())
				.setTitle("����ͷ��...")
				.setNegativeButton("���", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						/**
						 * �տ�ʼ�����Լ�Ҳ��֪��ACTION_PICK�Ǹ���ģ�����ֱ�ӿ�IntentԴ�룬
						 * ���Է�������ܶණ����Intent�Ǹ���ǿ��Ķ��������һ����ϸ�Ķ���
						 */
						Intent intent = new Intent(Intent.ACTION_PICK, null);
						
						/**
						 * ������仰����������ʽд��һ����Ч���������
						 * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						 * intent.setType(""image/*");������������
						 * ���������Ҫ�����ϴ�����������ͼƬ����ʱ����ֱ��д�磺"image/jpeg �� image/png�ȵ�����"
						 * ����ط�С���и����ʣ�ϣ�����ֽ���£������������URI������ΪʲôҪ��������ʽ��дѽ����ʲô����
						 */
						intent.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								"image/*");
						startActivityForResult(intent, 1);

					}
				})
				.setPositiveButton("����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
						/**
						 * ������仹�������ӣ����ÿ������չ��ܣ�����Ϊʲô�п������գ���ҿ��Բο����¹ٷ�
						 * �ĵ���you_sdk_path/docs/guide/topics/media/camera.html
						 * �Ҹտ���ʱ����Ϊ̫�������濴����ʵ�Ǵ�ģ�����������õ�̫���ˣ����Դ�Ҳ�Ҫ��Ϊ
						 * �ٷ��ĵ�̫���˾Ͳ����ˣ���ʵ�Ǵ�ģ�����ط�С��Ҳ���ˣ��������
						 */
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
			/**
			 * �ǿ��жϴ��һ��Ҫ��֤���������֤�Ļ���
			 * �ڼ���֮��������ֲ����⣬Ҫ���²ü�������
			 * ��ǰ����ʱ���ᱨNullException��С��ֻ
			 * ������ط����£���ҿ��Ը��ݲ�ͬ����ں��ʵ�
			 * �ط����жϴ����������
			 * 
			 */
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
		/*
		 * �����������Intent��ACTION����ô֪���ģ���ҿ��Կ����Լ�·���µ�������ҳ
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * ֱ��������Ctrl+F�ѣ�CROP ��֮ǰС��û��ϸ��������ʵ��׿ϵͳ���Ѿ����Դ�ͼƬ�ü�����,
		 * ��ֱ�ӵ����ؿ�ģ�С����C C++  ���������ϸ�˽�ȥ�ˣ������Ӿ������ӣ������о���������ô
		 * ��������...���
		 */
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
			
			/**
			 * ����ע�͵ķ����ǽ��ü�֮���ͼƬ��Base64Coder���ַ���ʽ��
			 * ������������QQͷ���ϴ����õķ������������
			 */
			
			/*ByteArrayOutputStream stream = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);
			byte[] b = stream.toByteArray();
			// ��ͼƬ�����ַ�����ʽ�洢����
			
			tp = new String(Base64Coder.encodeLines(b));
			����ط���ҿ���д�¸��������ϴ�ͼƬ��ʵ�֣�ֱ�Ӱ�tpֱ���ϴ��Ϳ����ˣ�
			����������ķ����Ƿ������Ǳߵ����ˣ����
			
			������ص��ķ����������ݻ�����Base64Coder����ʽ�Ļ������������·�ʽת��
			Ϊ���ǿ����õ�ͼƬ���;�OK��...���
			Bitmap dBitmap = BitmapFactory.decodeFile(tp);
			Drawable drawable = new BitmapDrawable(dBitmap);
			*/
			cover_user_photo.setImageBitmap(photo);
			cover_user_photo1 = (CircularImage) getActivity().findViewById(R.id.cover_user_photo);
//			LayoutInflater mLi = LayoutInflater.from(getActivity());
//			View view3 = mLi.inflate(R.layout.main_tab_address, null);
			View view2 = (View) getActivity().getLayoutInflater().inflate(R.layout.main_tab_friends, null);
			//user_avatar = (ImageView) view2.findViewById(R.id.user_avatar);
			cover_user_photo1.setImageBitmap(photo);
			//user_avatar.setImageBitmap(photo);
		}
	}
}
