package com.lanting.ScrollControl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * ����ScrollView���ⲿControlView(���������ͻ)
 * 
 * ʹ�÷�ʽ
 * 
 * <pre>
 * 
 * &lt;com.lanting.demo01.MyScrollControlView
 *      android:layout_width="match_parent"
 *      android:layout_height="match_parent"
 *      <font color='red'>android:layout_marginTop="600px"</font> >
 *      &lt;ScrollView
 *       android:id="@+id/scrollView"
 *       android:layout_width="match_parent"
 *       android:layout_height="wrap_content" >
 *       ...
 *      &lt;/ScrollView>
 * &lt;/com.lanting.demo01.MyScrollControlView>
 * </pre>
 * 
 * ����android:layout_marginTop="600px"���õ�ֵ��Ҫͨ��MyScrollControlView��setOffset(int
 * offset)������֪�ؼ���
 * 
 * @author ��ͤ����
 * 
 */
@SuppressLint("ClickableViewAccessibility")
public class MyScrollControlView extends LinearLayout {

	/**
	 * ��¼MotionEvent.DOWN������
	 */
	private float firstY_touchEvent = 0;

	/**
	 * marginƫ��ֵ
	 */
	public static int OFFSET = 600;

	/**
	 * ���ֲ�������
	 */
	FrameLayout.LayoutParams params;
	/**
	 * �������ƶ���
	 */
	Scroller scroller;

	/**
	 * ����Ƿ����¹���
	 */
	private boolean isDown = false;

	/**
	 * ����Ƿ���һ����Ч����ָ����
	 */
	private boolean isMove = false;

	public MyScrollControlView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		scroller = new Scroller(context);

	}

	public MyScrollControlView(Context context, AttributeSet attrs) {
		super(context, attrs);
		scroller = new Scroller(context);
	}

	public MyScrollControlView(Context context) {
		super(context);
		scroller = new Scroller(context);
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			int y = scroller.getCurrY();
			params = (FrameLayout.LayoutParams) getLayoutParams();
			params.topMargin = y;
			setLayoutParams(params);
			postInvalidate();
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean intercepted = false;
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			intercepted = false;
			firstY_touchEvent = ev.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:
			float y = ev.getRawY();
			float offset = y - firstY_touchEvent;
			// ��ȡScrollView��ScrollYֵ
			int scrollY = getChildAt(0).getScrollY();
			// ���û�л����������������¼�
			if (getTop() > 0
					&& Math.abs(offset) >= ViewConfiguration.get(getContext())
							.getScaledTouchSlop()) {
				intercepted = true;
			}// ���ScrollView��YֵΪ0��������������ʱ��Ӧ�������¼�
			else if (scrollY <= 0
					&& Math.abs(offset) >= ViewConfiguration.get(getContext())
							.getScaledTouchSlop()) {
				intercepted = true;
			}// �������¼�
			else {
				intercepted = false;
			}

			break;

		case MotionEvent.ACTION_UP:
			intercepted = false;
			break;

		default:
			break;
		}

		return intercepted;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {

		case MotionEvent.ACTION_MOVE:
			float y = event.getRawY();
			float offset = y - firstY_touchEvent;
			if (offset < 0) {
				isDown = false;
			} else {
				isDown = true;
			}
			if (Math.abs(offset) >= ViewConfiguration.get(getContext())
					.getScaledTouchSlop()) {
				isMove = true;
			}
			// ����û�ȷʵ�����û���
			if (isMove) {
				params = (FrameLayout.LayoutParams) getLayoutParams();
				float top = params.topMargin + offset;
				top = top > 0 ? (top > OFFSET ? OFFSET : top) : 0;
				params.topMargin = (int) top;
				setLayoutParams(params);
				firstY_touchEvent = y;
			}

			break;

		case MotionEvent.ACTION_UP:
			isMove = false;
			params = (FrameLayout.LayoutParams) getLayoutParams();
			// ��������
			if (isDown) {
				// topMargin����һ��,�˻�ȥ
				if (params.topMargin < (OFFSET / 4)) {
					scroller.startScroll(getLeft(), getTop(), 0,
							(-params.topMargin));
				}// topMargin����һ��,����ȥ
				else {
					scroller.startScroll(getLeft(), getTop(), 0,
							(OFFSET - params.topMargin));
				}

			} else {
				// topMargin����һ��,�˻�ȥ
				if (params.topMargin < (OFFSET * 3 / 4)) {
					scroller.startScroll(getLeft(), getTop(), 0,
							(-params.topMargin));
				}// topMargin����һ��,����ȥ
				else {
					scroller.startScroll(getLeft(), getTop(), 0,
							(OFFSET - params.topMargin));
				}
			}
			postInvalidate();
			break;

		}
		return true;
	}

	/**
	 * ����marginTop��ֵ
	 * 
	 * @param offset
	 *            �����ܵ�ֵΪpxΪ��λ��
	 */
	public void setOffset(int offset) {
		OFFSET = offset;
	}

}
