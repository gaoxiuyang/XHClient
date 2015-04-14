package com.xuanhui.view;

import cn.buaa.myweixin.R;
import android.view.View;


	import android.annotation.SuppressLint;
	import android.content.Context;
	import android.graphics.Canvas;
	import android.graphics.Paint;
	import android.graphics.RectF;
	import android.util.AttributeSet;
	import android.util.Log;
import android.view.View;

	@SuppressLint("DrawAllocation")
	public class MyView extends View {

		/**
		 * �����ΰ뾶
		 */
		private static final int BR = 230;
		/**
		 * �е����ΰ뾶
		 */
		private static final int MR = 230;
		/**
		 * С���ΰ뾶
		 */
		//private static final int SR = 160;

		/**
		 * ��ʼ���Ƶ���ʼ�Ƕ�
		 */
		private static final int START = 90;
		/**
		 * ����Բ��x����
		 */
		private static int CX;
		/**
		 * ����Բ��y����
		 */
		private static int CY;
		/**
		 * �������ڲ�Բ��x����
		 */
		private static int BX;
		/**
		 * �������ڲ�Բ��y����
		 */
		private static int BY;
		/**
		 * �е������ڲ�Բ��x����
		 */
		private static int MX;
		/**
		 * �е������ڲ�Բ��y����
		 */
		private static int MY;
		/**
		 * С�����ڲ�Բ��x����
		 */
		//private static int SX;

		/**
		 * С�����ڲ�Բ��y����
		 */
		//private static int SY;

		private int big;
		private int middle;
		//private int small;
		private Float bigRO;
		private Float middleRO;
		//private Float smallRO;
		private String moneyBig;
		private String moneyMiddle;
		//private String moneySmall;

		/**
		 * �����νǶȵ�һ�������ֵ
		 */
		private double sin2big;

		/**
		 * �����νǶȵ�һ�������ֵ
		 */
		private double cos2big;
		private double sin2middle;
		private double cos2middle;
		//private double sin2small;
		//private double cos2small;

		public MyView(Context context) {
			super(context);
		}

		public MyView(Context context, AttributeSet attrs) {
			super(context, attrs);
			Log.d("ee", "MyView-----------");
		}

		  public MyView(Context context, AttributeSet attrs, int defStyle) {
		        super(context, attrs, defStyle);
		    
		    }
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			// TODO Auto-generated method stub
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			Log.d("ee", "onMeasure-----------");
		}

		public void setRatio(Float big, Float middle, String moneyBig, String moneyMiddle) {
			this.bigRO = big;
			this.middleRO = middle;
			//this.smallRO = small;
			this.big = (int) (big * 0.01 * 360);
			this.middle = (int) (middle * 0.01 * 360);
			//this.small = (int) (small * 0.01 * 360);
			this.moneyBig = moneyBig;
			this.moneyMiddle = moneyMiddle;
			//this.moneySmall = moneySmall;
		}
		@SuppressLint("DrawAllocation")
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);
			Log.d("ee", "onDraw-----------");
			CX = this.getWidth() / 2;
			CY = this.getHeight() / 2;
			sin2big = Math.sin(Math.toRadians(big / 2));
			cos2big = Math.cos(Math.toRadians(big / 2));
			BX = (int) (CX - BR / 2 * sin2big);
			BY = (int) (CY + BR / 2 * cos2big);
			sin2middle = Math.sin(Math.toRadians(middle / 2));
			cos2middle = Math.cos(Math.toRadians(middle / 2));
			MX = (int) (CX + MR / 2 * sin2middle);
			MY = (int) (CY + MR / 2 * cos2middle);
//			if (middle + small / 2 >= 90) {
//				sin2small = Math.sin(Math.toRadians(small / 2 - (180 - big)));
//				cos2small = Math.cos(Math.toRadians(small / 2 - (180 - big)));
//				SX = (int) (CX + SR / 2 * sin2small);
//				SY = (int) (CY - SR / 2 * cos2small);
//			} else {
//				sin2small = Math.sin(Math.toRadians(middle + small / 2));
//				cos2small = -Math.cos(Math.toRadians(middle + small / 2));
//				SX = (int) (CX + SR / 2 * sin2small);
//				SY = (int) (CY - SR / 2 * cos2small);
//			}

			/*
			 * ���� ˵�� drawRect ���ƾ��� drawCircle ����Բ�� drawOval ������Բ drawPath ������������
			 * drawLine ����ֱ�� drawPoin ���Ƶ�
			 */
			// ��������
			Paint p = new Paint();
			p.setAntiAlias(true);// ���û��ʵľ��Ч���� true��ȥ�������һ��Ч����������

			// �����������
			p.setColor(getResources().getColor(R.color.lan));// ����ɫ
			Log.d("ee", (CX - BR) + "  " + (CY - BR) + "  " + (CX + BR) + "  " + (CY + BR));
			RectF oval2 = new RectF(CX - BR, CY - BR, CX + BR, CY + BR);// ���ø��µĳ����Σ�ɨ�����
			canvas.drawArc(oval2, START, big, true, p);

			// �����м�����
			p.setColor(getResources().getColor(R.color.huang));// ����ɫ
			oval2 = new RectF(CX - MR, CY - MR, CX + MR, CY + MR);// ���ø��µĳ����Σ�ɨ�����
			canvas.drawArc(oval2, 360 - middle + START, middle, true, p);

			// ������С����
			p.setColor(getResources().getColor(R.color.lv));// ����ɫ
			//oval2 = new RectF(CX - SR, CY - SR, CX + SR, CY + SR);// ���ø��µĳ����Σ�ɨ�����
			//canvas.drawArc(oval2, 360 + (START - middle - small), small, true, p);

			// �����м��ɫԲ
			p.setColor(getResources().getColor(R.color.bai));// ����BAIɫ
			canvas.drawCircle(CX, CY, 20, p);// ����Բ

			Log.d("ee", "  CX:" + CX + "  CY:" + CY + "  BX:" + BX + "  BY:" + BY + "  big�Ƕ�:" + big / 2 + "  �뾶BR:" + BR + "    ����ֵ��" + Math.sin(Math.toRadians(big / 2)) + "    " + BR * Math.sin(Math.toRadians(big / 2)) + "    " + Math.cos(Math.toRadians(big / 2)) + "    " + "    ����ֵ��" + BR * Math.cos(Math.toRadians(big / 2)));
			// �������ڲ��İ�ɫб����
			p.setColor(getResources().getColor(R.color.bai));// ����BAIɫ
			canvas.drawLine(BX, BY, (int) (CX - BR * sin2big), (int) (CY + BR * cos2big), p);
			// �����������ɫб����
			p.setColor(getResources().getColor(R.color.lan));
			canvas.drawLine((int) (CX - BR * sin2big), (int) (CY + BR * cos2big), (int) (CX - (BR + 20) * sin2big), (int) (CY + (BR + 20) * cos2big), p);
			// �����������ɫֱ����
			canvas.drawLine((int) (CX - (BR + 20) * sin2big), (int) (CY + (BR + 20) * cos2big), (int) (CX - (BR + 20) * sin2big - 30), (int) (CY + (BR + 20) * cos2big), p);
			// �����������ɫֱ�����ϵİٷֱ�����
			canvas.drawText(bigRO + "%", (int) (CX - (BR + 20) * sin2big - 30 + 5), (int) (CY + (BR + 20) * cos2big - 2), p);

			// �������ڲ�����������Բ
			p.setColor(getResources().getColor(R.color.bai));// ���ú�ɫ
			canvas.drawCircle(BX, BY, 8, p);// ��Բ
			p.setColor(getResources().getColor(R.color.lan));// ���ú�ɫ
			canvas.drawCircle(BX, BY, 6, p);// ��Բ
			p.setColor(getResources().getColor(R.color.bai));// ���ú�ɫ
			canvas.drawCircle(BX, BY, 4, p);// ��Բ
			// �������ڲ����ʽ�����
			canvas.drawText(moneyBig, BX - 20, BY + 20, p);

			// �е������ڲ��İ�ɫб����
			canvas.drawLine(MX, MY, (float) (CX + MR * sin2middle), (float) (CY + MR * cos2middle), p);
			// �е�������Ļ�ɫб����
			p.setColor(getResources().getColor(R.color.huang));
			canvas.drawLine((float) (CX + MR * sin2middle), (float) (CY + MR * cos2middle), (float) (CX + (MR + 20) * sin2middle), (float) (CY + (MR + 20) * cos2middle), p);
			// �е�������Ļ�ɫֱ����
			canvas.drawLine((float) (CX + (MR + 20) * sin2middle), (float) (CY + (MR + 20) * cos2middle), (float) (CX + (MR + 20) * sin2middle + 50), (float) (CY + (MR + 20) * cos2middle), p);
			// �е�������Ļ�ɫֱ�����ϵİٷֱ�����
			canvas.drawText(middleRO + "%", (float) (CX + (MR + 20) * sin2middle + 50 - 40), (float) (CY + (MR + 20) * cos2middle - 2), p);

			// �е������ڲ�����������Բ
			p.setColor(getResources().getColor(R.color.bai));// ����ɫ
			Log.d("ee", middle / 2 + "  " + MR + "    " + MR / 2 * Math.sin(middle / 2) + "    " + CX + "    " + MR / 2 * Math.cos(middle / 2) + "    " + CY);
			canvas.drawCircle(MX, MY, 8, p);// ��Բ
			p.setColor(getResources().getColor(R.color.huang));// ����ɫ
			canvas.drawCircle(MX, MY, 6, p);// ��Բ
			p.setColor(getResources().getColor(R.color.bai));// ����ɫ
			canvas.drawCircle(MX, MY, 4, p);// ��Բ
			// С�����ڲ����ʽ�����
			canvas.drawText(moneyMiddle, MX - 20, MY + 20, p);

			// С�����ڲ�����������Բ
			//Log.d("ee", "  CX:" + CX + "  CY:" + CY + "  SX:" + SX + "  SY:" + SY + "  small�Ƕ�:" + small / 2 + "  �뾶SR:" + SR + "    ����ֵ��" + Math.sin(Math.toRadians(small / 2)) + "    " + SR * Math.sin(Math.toRadians(small / 2)) + "    " + Math.cos(Math.toRadians(small / 2)) + "    " + "    ����ֵ��" + SR * Math.cos(Math.toRadians(small / 2)));
			//p.setColor(getResources().getColor(R.color.bai));// ����ɫ
			//canvas.drawCircle(SX, SY, 8, p);// ��Բ
			//p.setColor(getResources().getColor(R.color.lv));// ����ɫ
			//canvas.drawCircle(SX, SY, 6, p);// ��Բ
			//p.setColor(getResources().getColor(R.color.bai));// ����ɫ
			//canvas.drawCircle(SX, SY, 4, p);// ��Բ
			// С�����ڲ����ʽ�����
			//canvas.drawText(moneySmall, SX, SY + 20, p);

			// С�����ڲ��İ�ɫб����
			//canvas.drawLine(SX, SY, (float) (CX + SR * sin2small), (float) (CY - SR * cos2small), p);
			// С���������ɫб����
			//p.setColor(getResources().getColor(R.color.lv));
			//canvas.drawLine((float) (CX + SR * sin2small), (float) (CY - SR * cos2small), (float) (CX + (SR + 20) * sin2small), (float) (CY - (SR + 20) * cos2small), p);
			// С���������ɫֱ����
			//canvas.drawLine((float) (CX + (SR + 20) * sin2small), (float) (CY - (SR + 20) * cos2small), (float) (CX + (SR + 20) * sin2small + 50), (float) (CY - (SR + 20) * cos2small), p);
			// С���������ɫֱ�����ϵİٷֱ�����
			//canvas.drawText(smallRO + "%", (float) (CX + (SR + 20) * sin2small + 50 - 40), (float) (CY - (SR + 20) * cos2small - 2), p);

			// canvas.drawText("��Բ��", 10, 20, p);// ���ı�
			// canvas.drawCircle(60, 20, 10, p);// СԲ
			// p.setAntiAlias(true);// ���û��ʵľ��Ч���� true��ȥ�������һ��Ч����������
			// canvas.drawCircle(120, 20, 20, p);// ��Բ
			// cx��Բ�ĵ�x���ꡣ
			// cy��Բ�ĵ�y���ꡣ
			// radius��Բ�İ뾶��
			// paint������ʱ��ʹ�õĻ��ʡ�
			//
			// canvas.drawText("���߼����ߣ�", 10, 60, p);
			// p.setColor(Color.GREEN);// ������ɫ
			// canvas.drawLine(60, 40, 100, 40, p);// ����
			// canvas.drawLine(110, 40, 190, 80, p);// б��
			// // ��Ц������
			// p.setStyle(Paint.Style.STROKE);// ���ÿ���
			// RectF oval1 = new RectF(150, 20, 180, 40);
			// canvas.drawArc(oval1, 180, 180, false, p);// С����
			// oval1.set(190, 20, 220, 40);
			// canvas.drawArc(oval1, 180, 180, false, p);// С����
			// oval1.set(160, 30, 210, 60);
			// canvas.drawArc(oval1, 0, 180, false, p);// С����
			//
			// canvas.drawText("�����Σ�", 10, 80, p);
			// p.setColor(Color.GRAY);// ���û�ɫ
			// p.setStyle(Paint.Style.FILL);// ��������
			// canvas.drawRect(60, 60, 80, 80, p);// ������
			// canvas.drawRect(60, 90, 160, 100, p);// ������

			// canvas.drawText("�����κ���Բ:", 10, 120, p);
			// /* ���ý���ɫ ��������ε���ɫ�Ǹı�� */
			// Shader mShader = new LinearGradient(0, 0, 100, 100, new int[] {
			// Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.LTGRAY },
			// null, Shader.TileMode.REPEAT); // һ������,�����һ�������ݶ�����һ���ߡ�
			// p.setShader(mShader);
			// // p.setColor(Color.BLUE);
			// RectF oval2 = new RectF(60, 100, 200, 240);// ���ø��µĳ����Σ�ɨ�����
			// canvas.drawArc(oval2, 200, 130, true, p);
			// oval :ָ��Բ������������������
			// startAngle: Բ����ʼ�Ƕȣ���λΪ�ȡ�
			// sweepAngle: Բ��ɨ���ĽǶȣ�˳ʱ�뷽�򣬵�λΪ�ȡ�
			// useCenter: ���ΪTrueʱ���ڻ���Բ��ʱ��Բ�İ������ڣ�ͨ�������������Ρ�
			// paint: ����Բ���Ļ������ԣ�����ɫ���Ƿ����ȡ�
			// // ��������һ��������RectF�������ǵڶ��������ǽǶȵĿ�ʼ�������������Ƕ��ٶȣ����ĸ����������ʱ�����Σ��Ǽٵ�ʱ�򻭻���
			// // ����Բ����oval��һ��
			// oval2.set(210, 100, 250, 130);
			// canvas.drawOval(oval2, p);

			// canvas.drawText("�������Σ�", 10, 200, p);
			// // �������������,����Ի�����������
			// Path path = new Path();
			// path.moveTo(80, 200);// �˵�Ϊ����ε����
			// path.lineTo(120, 250);
			// path.lineTo(80, 250);
			// path.close(); // ʹ��Щ�㹹�ɷ�յĶ����
			// canvas.drawPath(path, p);
			//
			// // ����Ի��ƺܶ��������Σ��������滭������
			// p.reset();// ����
			// p.setColor(Color.LTGRAY);
			// p.setStyle(Paint.Style.STROKE);// ���ÿ���
			// Path path1 = new Path();
			// path1.moveTo(180, 200);
			// path1.lineTo(200, 200);
			// path1.lineTo(210, 210);
			// path1.lineTo(200, 220);
			// path1.lineTo(180, 220);
			// path1.lineTo(170, 210);
			// path1.close();// ���
			// canvas.drawPath(path1, p);
			// /*
			// * Path���װ����(����������ͼ�ε�·��
			// * ��ֱ�߶�*����������,�����η����ߣ�Ҳ�ɻ����ͻ���drawPath(·��������),Ҫô�����Ļ���
			// * (��������ķ��),���߿������ڼ��ϻ򻭻����ı���·����
			// */
			//
			// // ��Բ�Ǿ���
			// p.setStyle(Paint.Style.FILL);// ����
			// p.setColor(Color.LTGRAY);
			// p.setAntiAlias(true);// ���û��ʵľ��Ч��
			// canvas.drawText("��Բ�Ǿ���:", 10, 260, p);
			// RectF oval3 = new RectF(80, 260, 200, 300);// ���ø��µĳ�����
			// canvas.drawRoundRect(oval3, 20, 15, p);// �ڶ���������x�뾶��������������y�뾶
			//
			// // ������������
			// canvas.drawText("������������:", 10, 310, p);
			// p.reset();
			// p.setStyle(Paint.Style.STROKE);
			// p.setColor(Color.GREEN);
			// Path path2 = new Path();
			// path2.moveTo(100, 320);// ����Path�����
			// path2.quadTo(150, 310, 170, 400); // ���ñ��������ߵĿ��Ƶ�������յ�����
			// canvas.drawPath(path2, p);// ��������������
			//
			// // ����
			// p.setStyle(Paint.Style.FILL);
			// canvas.drawText("���㣺", 10, 390, p);
			// canvas.drawPoint(60, 390, p);// ��һ����
			// canvas.drawPoints(new float[] { 60, 400, 65, 400, 70, 400 }, p);//
			// �������
			//
			// // ��ͼƬ��������ͼ
			// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
			// R.drawable.ic_launcher);
			// canvas.drawBitmap(bitmap, 250, 360, p);
		}

	

	
	
	
	
}
