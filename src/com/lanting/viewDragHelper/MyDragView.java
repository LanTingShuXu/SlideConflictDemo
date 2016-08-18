package com.lanting.viewDragHelper;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * ����DragViewHelper���Զ�����
 * 
 * @author LANTINGSHUXU
 * 
 */
public class MyDragView extends FrameLayout {

	private ViewDragHelper dragHelper;
	// ��������Ͳ˵�����
	private View content, menu;
	// ���չ����
	private int OFFSET = 200;

	private float contentHeigh;
	private float contentWidth;

	private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			// ��ʱ����ȡ�ؼ��Ŀ�ߣ�������ܵ��»�ȡ���ĸ߶�Ϊ0��
			contentHeigh = content.getHeight();
			contentWidth = content.getWidth();
			return child == content;
		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// ����ڿɹ������������
			if (left < 0) {
				left = 0;
			}
			if (left > OFFSET) {
				left = OFFSET;
			}
			return left;
		}

		@Override
		public int clampViewPositionVertical(View child, int top, int dy) {
			return 0;
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			// // ʵ������QQ�໬�˵���Ч���������Ĺ����У���content������Ч����
			// content.setScaleX((OFFSET - left * 0.5f) / OFFSET);
			// content.setScaleY((OFFSET - left * 0.5f) / OFFSET);

			// ʵ�������ڿṷ���ֵķ���Ч��
			float offset = left * 1.0f / OFFSET;
			content.setPivotX(contentWidth / 2);
			content.setPivotY(contentHeigh * 1.5f);
			// 30�ȵ���ת
			content.setRotation(45 * offset);
		}

		// ��ָ�뿪����
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			// �������������һ�룬����ȥ�������˻���
			if (xvel > OFFSET / 2) {
				dragHelper.smoothSlideViewTo(releasedChild, OFFSET, 0);
			} else {
				dragHelper.smoothSlideViewTo(releasedChild, 0, 0);
			}
			ViewCompat.postInvalidateOnAnimation(MyDragView.this);
		}

	};

	public MyDragView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public MyDragView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyDragView(Context context) {
		super(context);
		init();
	}

	// ���XML������
	@Override
	protected void onFinishInflate() {
		menu = getChildAt(0);
		content = getChildAt(1);
	}

	private void init() {
		dragHelper = ViewDragHelper.create(this, callback);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// ViewDragHelper�ӹ�event�¼�
		dragHelper.processTouchEvent(event);
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// ViewDragHelper�ӹ��¼����ػ���
		return dragHelper.shouldInterceptTouchEvent(ev);
	}

	// ��������
	@Override
	public void computeScroll() {
		if (dragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

}
