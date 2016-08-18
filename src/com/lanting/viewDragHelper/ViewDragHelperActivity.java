package com.lanting.viewDragHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lanting.demo01.R;

public class ViewDragHelperActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_drag_helper);
	}

	public void clk_content(View v) {
		Toast.makeText(this, "ÄãºÃ¡£", Toast.LENGTH_SHORT).show();
	}

	public void clk_menu(View v) {
		Toast.makeText(this, "ÄãºÃ", Toast.LENGTH_SHORT).show();
	}

}
