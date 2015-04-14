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
		 * 大扇形半径
		 */
		private static final int BR = 230;
		/**
		 * 中等扇形半径
		 */
		private static final int MR = 230;
		/**
		 * 小扇形半径
		 */
		//private static final int SR = 160;

		/**
		 * 开始绘制的起始角度
		 */
		private static final int START = 90;
		/**
		 * 扇形圆心x坐标
		 */
		private static int CX;
		/**
		 * 扇形圆心y坐标
		 */
		private static int CY;
		/**
		 * 大扇形内部圆心x坐标
		 */
		private static int BX;
		/**
		 * 大扇形内部圆心y坐标
		 */
		private static int BY;
		/**
		 * 中等扇形内部圆心x坐标
		 */
		private static int MX;
		/**
		 * 中等扇形内部圆心y坐标
		 */
		private static int MY;
		/**
		 * 小扇形内部圆心x坐标
		 */
		//private static int SX;

		/**
		 * 小扇形内部圆心y坐标
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
		 * 大扇形角度的一半的正玄值
		 */
		private double sin2big;

		/**
		 * 大扇形角度的一半的余玄值
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
			 * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
			 * drawLine 绘制直线 drawPoin 绘制点
			 */
			// 创建画笔
			Paint p = new Paint();
			p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了

			// 绘制最大扇形
			p.setColor(getResources().getColor(R.color.lan));// 设置色
			Log.d("ee", (CX - BR) + "  " + (CY - BR) + "  " + (CX + BR) + "  " + (CY + BR));
			RectF oval2 = new RectF(CX - BR, CY - BR, CX + BR, CY + BR);// 设置个新的长方形，扫描测量
			canvas.drawArc(oval2, START, big, true, p);

			// 绘制中间扇形
			p.setColor(getResources().getColor(R.color.huang));// 设置色
			oval2 = new RectF(CX - MR, CY - MR, CX + MR, CY + MR);// 设置个新的长方形，扫描测量
			canvas.drawArc(oval2, 360 - middle + START, middle, true, p);

			// 绘制最小扇形
			p.setColor(getResources().getColor(R.color.lv));// 设置色
			//oval2 = new RectF(CX - SR, CY - SR, CX + SR, CY + SR);// 设置个新的长方形，扫描测量
			//canvas.drawArc(oval2, 360 + (START - middle - small), small, true, p);

			// 绘制中间白色圆
			p.setColor(getResources().getColor(R.color.bai));// 设置BAI色
			canvas.drawCircle(CX, CY, 20, p);// 中心圆

			Log.d("ee", "  CX:" + CX + "  CY:" + CY + "  BX:" + BX + "  BY:" + BY + "  big角度:" + big / 2 + "  半径BR:" + BR + "    正玄值：" + Math.sin(Math.toRadians(big / 2)) + "    " + BR * Math.sin(Math.toRadians(big / 2)) + "    " + Math.cos(Math.toRadians(big / 2)) + "    " + "    余弦值：" + BR * Math.cos(Math.toRadians(big / 2)));
			// 大扇形内部的白色斜线条
			p.setColor(getResources().getColor(R.color.bai));// 设置BAI色
			canvas.drawLine(BX, BY, (int) (CX - BR * sin2big), (int) (CY + BR * cos2big), p);
			// 大扇形外的蓝色斜线条
			p.setColor(getResources().getColor(R.color.lan));
			canvas.drawLine((int) (CX - BR * sin2big), (int) (CY + BR * cos2big), (int) (CX - (BR + 20) * sin2big), (int) (CY + (BR + 20) * cos2big), p);
			// 大扇形外的蓝色直线条
			canvas.drawLine((int) (CX - (BR + 20) * sin2big), (int) (CY + (BR + 20) * cos2big), (int) (CX - (BR + 20) * sin2big - 30), (int) (CY + (BR + 20) * cos2big), p);
			// 大扇形外的蓝色直线条上的百分比字体
			canvas.drawText(bigRO + "%", (int) (CX - (BR + 20) * sin2big - 30 + 5), (int) (CY + (BR + 20) * cos2big - 2), p);

			// 大扇形内部的三个叠加圆
			p.setColor(getResources().getColor(R.color.bai));// 设置红色
			canvas.drawCircle(BX, BY, 8, p);// 白圆
			p.setColor(getResources().getColor(R.color.lan));// 设置红色
			canvas.drawCircle(BX, BY, 6, p);// 蓝圆
			p.setColor(getResources().getColor(R.color.bai));// 设置红色
			canvas.drawCircle(BX, BY, 4, p);// 白圆
			// 大扇形内部的资金字体
			canvas.drawText(moneyBig, BX - 20, BY + 20, p);

			// 中等扇形内部的白色斜线条
			canvas.drawLine(MX, MY, (float) (CX + MR * sin2middle), (float) (CY + MR * cos2middle), p);
			// 中等扇形外的黄色斜线条
			p.setColor(getResources().getColor(R.color.huang));
			canvas.drawLine((float) (CX + MR * sin2middle), (float) (CY + MR * cos2middle), (float) (CX + (MR + 20) * sin2middle), (float) (CY + (MR + 20) * cos2middle), p);
			// 中等扇形外的黄色直线条
			canvas.drawLine((float) (CX + (MR + 20) * sin2middle), (float) (CY + (MR + 20) * cos2middle), (float) (CX + (MR + 20) * sin2middle + 50), (float) (CY + (MR + 20) * cos2middle), p);
			// 中等扇形外的黄色直线条上的百分比字体
			canvas.drawText(middleRO + "%", (float) (CX + (MR + 20) * sin2middle + 50 - 40), (float) (CY + (MR + 20) * cos2middle - 2), p);

			// 中等扇形内部的三个叠加圆
			p.setColor(getResources().getColor(R.color.bai));// 设置色
			Log.d("ee", middle / 2 + "  " + MR + "    " + MR / 2 * Math.sin(middle / 2) + "    " + CX + "    " + MR / 2 * Math.cos(middle / 2) + "    " + CY);
			canvas.drawCircle(MX, MY, 8, p);// 白圆
			p.setColor(getResources().getColor(R.color.huang));// 设置色
			canvas.drawCircle(MX, MY, 6, p);// 黄圆
			p.setColor(getResources().getColor(R.color.bai));// 设置色
			canvas.drawCircle(MX, MY, 4, p);// 白圆
			// 小扇形内部的资金字体
			canvas.drawText(moneyMiddle, MX - 20, MY + 20, p);

			// 小扇形内部的三个叠加圆
			//Log.d("ee", "  CX:" + CX + "  CY:" + CY + "  SX:" + SX + "  SY:" + SY + "  small角度:" + small / 2 + "  半径SR:" + SR + "    正玄值：" + Math.sin(Math.toRadians(small / 2)) + "    " + SR * Math.sin(Math.toRadians(small / 2)) + "    " + Math.cos(Math.toRadians(small / 2)) + "    " + "    余弦值：" + SR * Math.cos(Math.toRadians(small / 2)));
			//p.setColor(getResources().getColor(R.color.bai));// 设置色
			//canvas.drawCircle(SX, SY, 8, p);// 白圆
			//p.setColor(getResources().getColor(R.color.lv));// 设置色
			//canvas.drawCircle(SX, SY, 6, p);// 黄圆
			//p.setColor(getResources().getColor(R.color.bai));// 设置色
			//canvas.drawCircle(SX, SY, 4, p);// 白圆
			// 小扇形内部的资金字体
			//canvas.drawText(moneySmall, SX, SY + 20, p);

			// 小扇形内部的白色斜线条
			//canvas.drawLine(SX, SY, (float) (CX + SR * sin2small), (float) (CY - SR * cos2small), p);
			// 小扇形外的蓝色斜线条
			//p.setColor(getResources().getColor(R.color.lv));
			//canvas.drawLine((float) (CX + SR * sin2small), (float) (CY - SR * cos2small), (float) (CX + (SR + 20) * sin2small), (float) (CY - (SR + 20) * cos2small), p);
			// 小扇形外的蓝色直线条
			//canvas.drawLine((float) (CX + (SR + 20) * sin2small), (float) (CY - (SR + 20) * cos2small), (float) (CX + (SR + 20) * sin2small + 50), (float) (CY - (SR + 20) * cos2small), p);
			// 小扇形外的蓝色直线条上的百分比字体
			//canvas.drawText(smallRO + "%", (float) (CX + (SR + 20) * sin2small + 50 - 40), (float) (CY - (SR + 20) * cos2small - 2), p);

			// canvas.drawText("画圆：", 10, 20, p);// 画文本
			// canvas.drawCircle(60, 20, 10, p);// 小圆
			// p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
			// canvas.drawCircle(120, 20, 20, p);// 大圆
			// cx：圆心的x坐标。
			// cy：圆心的y坐标。
			// radius：圆的半径。
			// paint：绘制时所使用的画笔。
			//
			// canvas.drawText("画线及弧线：", 10, 60, p);
			// p.setColor(Color.GREEN);// 设置绿色
			// canvas.drawLine(60, 40, 100, 40, p);// 画线
			// canvas.drawLine(110, 40, 190, 80, p);// 斜线
			// // 画笑脸弧线
			// p.setStyle(Paint.Style.STROKE);// 设置空心
			// RectF oval1 = new RectF(150, 20, 180, 40);
			// canvas.drawArc(oval1, 180, 180, false, p);// 小弧形
			// oval1.set(190, 20, 220, 40);
			// canvas.drawArc(oval1, 180, 180, false, p);// 小弧形
			// oval1.set(160, 30, 210, 60);
			// canvas.drawArc(oval1, 0, 180, false, p);// 小弧形
			//
			// canvas.drawText("画矩形：", 10, 80, p);
			// p.setColor(Color.GRAY);// 设置灰色
			// p.setStyle(Paint.Style.FILL);// 设置填满
			// canvas.drawRect(60, 60, 80, 80, p);// 正方形
			// canvas.drawRect(60, 90, 160, 100, p);// 长方形

			// canvas.drawText("画扇形和椭圆:", 10, 120, p);
			// /* 设置渐变色 这个正方形的颜色是改变的 */
			// Shader mShader = new LinearGradient(0, 0, 100, 100, new int[] {
			// Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.LTGRAY },
			// null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
			// p.setShader(mShader);
			// // p.setColor(Color.BLUE);
			// RectF oval2 = new RectF(60, 100, 200, 240);// 设置个新的长方形，扫描测量
			// canvas.drawArc(oval2, 200, 130, true, p);
			// oval :指定圆弧的外轮廓矩形区域。
			// startAngle: 圆弧起始角度，单位为度。
			// sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。
			// useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。
			// paint: 绘制圆弧的画板属性，如颜色，是否填充等。
			// // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
			// // 画椭圆，把oval改一下
			// oval2.set(210, 100, 250, 130);
			// canvas.drawOval(oval2, p);

			// canvas.drawText("画三角形：", 10, 200, p);
			// // 绘制这个三角形,你可以绘制任意多边形
			// Path path = new Path();
			// path.moveTo(80, 200);// 此点为多边形的起点
			// path.lineTo(120, 250);
			// path.lineTo(80, 250);
			// path.close(); // 使这些点构成封闭的多边形
			// canvas.drawPath(path, p);
			//
			// // 你可以绘制很多任意多边形，比如下面画六连形
			// p.reset();// 重置
			// p.setColor(Color.LTGRAY);
			// p.setStyle(Paint.Style.STROKE);// 设置空心
			// Path path1 = new Path();
			// path1.moveTo(180, 200);
			// path1.lineTo(200, 200);
			// path1.lineTo(210, 210);
			// path1.lineTo(200, 220);
			// path1.lineTo(180, 220);
			// path1.lineTo(170, 210);
			// path1.close();// 封闭
			// canvas.drawPath(path1, p);
			// /*
			// * Path类封装复合(多轮廓几何图形的路径
			// * 由直线段*、二次曲线,和三次方曲线，也可画以油画。drawPath(路径、油漆),要么已填充的或抚摸
			// * (基于油漆的风格),或者可以用于剪断或画画的文本在路径。
			// */
			//
			// // 画圆角矩形
			// p.setStyle(Paint.Style.FILL);// 充满
			// p.setColor(Color.LTGRAY);
			// p.setAntiAlias(true);// 设置画笔的锯齿效果
			// canvas.drawText("画圆角矩形:", 10, 260, p);
			// RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
			// canvas.drawRoundRect(oval3, 20, 15, p);// 第二个参数是x半径，第三个参数是y半径
			//
			// // 画贝塞尔曲线
			// canvas.drawText("画贝塞尔曲线:", 10, 310, p);
			// p.reset();
			// p.setStyle(Paint.Style.STROKE);
			// p.setColor(Color.GREEN);
			// Path path2 = new Path();
			// path2.moveTo(100, 320);// 设置Path的起点
			// path2.quadTo(150, 310, 170, 400); // 设置贝塞尔曲线的控制点坐标和终点坐标
			// canvas.drawPath(path2, p);// 画出贝塞尔曲线
			//
			// // 画点
			// p.setStyle(Paint.Style.FILL);
			// canvas.drawText("画点：", 10, 390, p);
			// canvas.drawPoint(60, 390, p);// 画一个点
			// canvas.drawPoints(new float[] { 60, 400, 65, 400, 70, 400 }, p);//
			// 画多个点
			//
			// // 画图片，就是贴图
			// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
			// R.drawable.ic_launcher);
			// canvas.drawBitmap(bitmap, 250, 360, p);
		}

	

	
	
	
	
}
