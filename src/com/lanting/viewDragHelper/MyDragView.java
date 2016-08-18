package com.lanting.viewDragHelper;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 测试DragViewHelper的自定义类
 * 
 * @author LANTINGSHUXU
 * 
 */
public class MyDragView extends FrameLayout {

	private ViewDragHelper dragHelper;
	// 内容区域和菜单区域
	private View content, menu;
	// 最大展开量
	private int OFFSET = 200;

	private float contentHeigh;
	private float contentWidth;

	private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			// 此时，获取控件的宽高（否则可能导致获取到的高度为0）
			contentHeigh = content.getHeight();
			contentWidth = content.getWidth();
			return child == content;
		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// 如果在可滚动区域，则滚动
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
			// // 实现类似QQ侧滑菜单的效果（滑动的过程中，主content有缩放效果）
			// content.setScaleX((OFFSET - left * 0.5f) / OFFSET);
			// content.setScaleY((OFFSET - left * 0.5f) / OFFSET);

			// 实现类似于酷狗音乐的翻阅效果
			float offset = left * 1.0f / OFFSET;
			content.setPivotX(contentWidth / 2);
			content.setPivotY(contentHeigh * 1.5f);
			// 30度的旋转
			content.setRotation(45 * offset);
		}

		// 手指离开界面
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			// 如果滑动超过了一半，补上去，否则退回来
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

	// 如果XML填充完毕
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
		// ViewDragHelper接管event事件
		dragHelper.processTouchEvent(event);
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// ViewDragHelper接管事件拦截机制
		return dragHelper.shouldInterceptTouchEvent(ev);
	}

	// 滚动动画
	@Override
	public void computeScroll() {
		if (dragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

}
