package com.isis.wifimanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StarScanActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		final Button btn2 = (Button) findViewById(R.id.button2);

//		btn2.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {

				Intent intent = new Intent(StarScanActivity.this,
						MainActivity.class);
				Bundle b = new Bundle();

				intent.putExtras(b);
				startActivity(intent);
//			}

//		});

	}

}
