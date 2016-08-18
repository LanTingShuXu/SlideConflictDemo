package com.lanting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lanting.demo01.R;
import com.lanting.viewDragHelper.ViewDragHelperActivity;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * 点击了顶部的图片
	 * 
	 * @param v
	 */
	public void clk_img(View v) {
		Toast.makeText(this, "\n你点击了图片\n", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 点击了文字
	 * 
	 * @param v
	 */
	public void clk_tv(View v) {
		Toast.makeText(this, "\n你点击了文字\n", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 点击了ViewDragHelperDemo按钮
	 * 
	 * @param v
	 */
	public void clk_viewDragHelperDemo(View v) {
		startActivity(new Intent(this, ViewDragHelperActivity.class));
	}
}
