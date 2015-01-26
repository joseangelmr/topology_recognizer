package com.isis.wifimanager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MyTable extends Activity {
	/** Called when the activity is first created. */
	TableLayout country_table;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		country_table = (TableLayout) findViewById(R.id.country_table);
		fillCountryTable();
	}

	void fillCountryTable() {

		TableRow row;
		TextView t1, t2, t3, t4, t5, t6;
	
		
		
		// Converting to dip unit
		int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				(float) 1, getResources().getDisplayMetrics());

		for (int current = 0; current < CountriesList.abbreviations.length; current++) {
			row = new TableRow(this);

			t1 = new TextView(this);
			t2 = new TextView(this);
			t3 = new TextView(this);
			t4 = new TextView(this);
			t5 = new TextView(this);
			t6 = new TextView(this);

			if (current % 2 == 1) {
				t1.setTextColor(Color.BLACK);
				t2.setTextColor(Color.BLACK);
				t3.setTextColor(Color.BLACK);
				t5.setTextColor(Color.BLACK);
				t6.setTextColor(Color.BLACK);
			} else {
				t1.setTextColor(Color.BLACK);
				t2.setTextColor(Color.BLACK);
				t3.setTextColor(Color.BLACK);
				t5.setTextColor(Color.BLACK);
				t6.setTextColor(Color.BLACK);
			}

			t4.setTextColor(Color.RED);

			//----

			t1.setTypeface(null, 1);
			t2.setTypeface(null, 1);
			t3.setTypeface(null, 1);
			t4.setTypeface(null, 1);
			t5.setTypeface(null, 1);
			t6.setTypeface(null, 1);

			t1.setTextSize(10);
			t2.setTextSize(10);
			t3.setTextSize(10);
			t4.setTextSize(10);
			t5.setTextSize(10);
			t6.setTextSize(10);

			t1.setWidth(190 * dip);
			t2.setWidth(85 * dip);
			t3.setWidth(50 * dip);
			t4.setWidth(50 * dip);
			t5.setWidth(70 * dip);
			t6.setWidth(50 * dip);

			t1.setPadding(10 * dip, 0, 0, 0);

			if (current % 2 == 1)
				row.setBackgroundColor(Color.GRAY);
			else
				row.setBackgroundColor(Color.WHITE);

			row.addView(t1);
			row.addView(t2);
			row.addView(t3);
			row.addView(t4);
			row.addView(t5);
			row.addView(t6);

			country_table.addView(row, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		}
	}
}