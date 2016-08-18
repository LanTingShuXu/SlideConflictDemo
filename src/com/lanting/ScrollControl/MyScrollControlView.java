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
 * 控制ScrollView的外部ControlView(解决滑动冲突)
 * 
 * 使用方式
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
 * 其中android:layout_marginTop="600px"设置的值需要通过MyScrollControlView的setOffset(int
 * offset)方法告知控件。
 * 
 * @author 蓝亭书序
 * 
 */
@SuppressLint("ClickableViewAccessibility")
public class MyScrollControlView extends LinearLayout {

	/**
	 * 记录MotionEvent.DOWN的坐标
	 */
	private float firstY_touchEvent = 0;

	/**
	 * margin偏移值
	 */
	public static int OFFSET = 600;

	/**
	 * 布局参数对象
	 */
	FrameLayout.LayoutParams params;
	/**
	 * 滚动控制对象
	 */
	Scroller scroller;

	/**
	 * 标记是否向下滚动
	 */
	private boolean isDown = false;

	/**
	 * 标记是否是一次有效的手指滑动
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
			// 获取ScrollView的ScrollY值
			int scrollY = getChildAt(0).getScrollY();
			// 如果没有滑动到顶部，拦截事件
			if (getTop() > 0
					&& Math.abs(offset) >= ViewConfiguration.get(getContext())
							.getScaledTouchSlop()) {
				intercepted = true;
			}// 如果ScrollView的Y值为0，并且向下拉的时候，应该拦截事件
			else if (scrollY <= 0
					&& Math.abs(offset) >= ViewConfiguration.get(getContext())
							.getScaledTouchSlop()) {
				intercepted = true;
			}// 不拦截事件
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
			// 如果用户确实是在用滑动
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
			// 向下拉动
			if (isDown) {
				// topMargin不到一半,退回去
				if (params.topMargin < (OFFSET / 4)) {
					scroller.startScroll(getLeft(), getTop(), 0,
							(-params.topMargin));
				}// topMargin超过一半,补回去
				else {
					scroller.startScroll(getLeft(), getTop(), 0,
							(OFFSET - params.topMargin));
				}

			} else {
				// topMargin不到一半,退回去
				if (params.topMargin < (OFFSET * 3 / 4)) {
					scroller.startScroll(getLeft(), getTop(), 0,
							(-params.topMargin));
				}// topMargin超过一半,补回去
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
	 * 设置marginTop的值
	 * 
	 * @param offset
	 *            （接受的值为px为单位）
	 */
	public void setOffset(int offset) {
		OFFSET = offset;
	}

}
