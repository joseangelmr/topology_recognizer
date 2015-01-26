package com.isis.wifimanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Chart extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.results);
		
		
		final Button btn2 = (Button) findViewById(R.id.button5);

		btn2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(Chart.this,
						StarScanActivity.class);
				Bundle b = new Bundle();

				intent.putExtras(b);
				startActivity(intent);
			}

		});


	}

}
