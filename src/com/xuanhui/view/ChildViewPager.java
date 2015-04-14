package com.xuanhui.view;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
public class ChildViewPager extends ViewPager {
    /** ����ʱ���µĵ� **/
    PointF downP = new PointF();
    /** ����ʱ��ǰ�ĵ� **/
    PointF curP = new PointF();
    OnSingleTouchListener onSingleTouchListener;

  
  

    public ChildViewPager(Context context) {
        super(context);
    }
    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ChildViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
    
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // �����ش����¼������λ�õ�ʱ�򣬷���true��
        // ˵����onTouch�����ڴ˿ؼ�������ִ�д˿ؼ���onTouchEvent
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        int x, y;
        // ÿ�ν���onTouch�¼�����¼��ǰ�İ��µ�����
        curP.x = arg0.getX();
        curP.y = arg0.getY();

        if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
            // ��¼����ʱ�������
            // �мǲ����� downP = curP �������ڸı�curP��ʱ��downPҲ��ı�
            downP.x = arg0.getX();
            downP.y = arg0.getY();
            // �˾������Ϊ��֪ͨ���ĸ�ViewPager���ڽ��е��Ǳ��ؼ��Ĳ�������Ҫ���ҵĲ������и���
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if (arg0.getAction() == MotionEvent.ACTION_MOVE) {
            // �˾������Ϊ��֪ͨ���ĸ�ViewPager���ڽ��е��Ǳ��ؼ��Ĳ�������Ҫ���ҵĲ������и���
            x = (int)(curP.x - downP.x);
            y = (int)(curP.y - downP.y);
            int count = this.getAdapter().getCount()-1;
            if (Math.abs(x) > Math.abs(y)) {
                if (x > 0 && getCurrentItem() == 0) {// ��һҳ�����ƶ�
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else if (x < 0 && getCurrentItem() == count) {// 
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
            }else if(Math.abs(x) < Math.abs(y)) {

            }

        }

        if (arg0.getAction() == MotionEvent.ACTION_UP) {
            // ��upʱ�ж��Ƿ��º����ֵ�����Ϊһ����
            // �����һ���㣬��ִ�е���¼����������Լ�д�ĵ���¼���������onclick
            if (downP.x == curP.x && downP.y == curP.y) {
                onSingleTouch();
                return true;
            }
        }
        if (arg0.getAction() == MotionEvent.ACTION_CANCEL) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }

        return super.onTouchEvent(arg0);
    }

    /**
     * ����
     */
    public void onSingleTouch() {
        if (onSingleTouchListener != null) {
            onSingleTouchListener.onSingleTouch();
        }
    }

    /**
     * ��������¼��ӿ�
     * 
     * @author wanpg
     * 
     */
    public interface OnSingleTouchListener {
        public void onSingleTouch();
    }

    public void setOnSingleTouchListener(
            OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }

}
 


