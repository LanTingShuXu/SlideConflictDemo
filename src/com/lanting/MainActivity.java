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
	 * ����˶�����ͼƬ
	 * 
	 * @param v
	 */
	public void clk_img(View v) {
		Toast.makeText(this, "\n������ͼƬ\n", Toast.LENGTH_SHORT).show();
	}

	/**
	 * ���������
	 * 
	 * @param v
	 */
	public void clk_tv(View v) {
		Toast.makeText(this, "\n����������\n", Toast.LENGTH_SHORT).show();
	}

	/**
	 * �����ViewDragHelperDemo��ť
	 * 
	 * @param v
	 */
	public void clk_viewDragHelperDemo(View v) {
		startActivity(new Intent(this, ViewDragHelperActivity.class));
	}
}
