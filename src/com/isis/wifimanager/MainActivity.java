package com.isis.wifimanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.isis.wifimanager.R.color;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ListFragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "NewApi", "SimpleDateFormat" })
public class MainActivity extends Activity {

	WifiManager mainWifiObj;
	WifiScanReceiver wifiReciever;
	ListView list;
	String wifis[];
	List<String> wifis_list = new ArrayList<String>();
	String str;
	String outfile = "";

	// Nuevos Cambios ---------------------------------------------------------

	int divisor = 5;

	// ------------------------------------------------------------------------

	ArrayList<String> list_BSSID = new ArrayList<String>();

	Hashtable<String, Double> table_bssid_repe = new Hashtable<String, Double>();
	Hashtable<String, String> table_bssid_ssid = new Hashtable<String, String>();
	Hashtable<String, Integer> table_bssid_level = new Hashtable<String, Integer>();
	Hashtable<String, Integer> table_bssid_channel = new Hashtable<String, Integer>();

	Map<String, Integer> ordenada = new HashMap<String, Integer>();

	ArrayList<String> list_mac = new ArrayList<String>();
	ArrayList<String> list_repe = new ArrayList<String>();
	ArrayList<String> list_ssid = new ArrayList<String>();
	ArrayList<String> list_prob = new ArrayList<String>();
	ArrayList<String> list_pw = new ArrayList<String>();
	ArrayList<String> list_pwAvg = new ArrayList<String>();
	ArrayList<String> list_ch = new ArrayList<String>();

	int count = 1;

	int count2 = 0;

	Long probeRequestTime;
	SimpleDateFormat formatTime;
	String out;

	TextView scan;
	TextView rq;

	TableLayout country_table;

	private static ListFragment listActivity;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_scan);

		setContentView(R.layout.main);
		country_table = (TableLayout) findViewById(R.id.country_table);

		scan = (TextView) findViewById(R.id.TextView02);
		rq = (TextView) findViewById(R.id.TextView03);
		//
		final Button btn1 = (Button) findViewById(R.id.button1);
		// list = (ListView) findViewById(R.id.listView1);
		mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifiReciever = new WifiScanReceiver();

		formatTime = new SimpleDateFormat("hh:mm:ss.SSSS");
		out = "";

		mainWifiObj.startScan();
		probeRequestTime = System.currentTimeMillis();

		btn1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				grabar();
				Intent intent = new Intent(MainActivity.this, Chart.class);
				Bundle b = new Bundle();

				intent.putExtras(b);
				startActivity(intent);
			}

			public void grabar() {
				// TODO Auto-generated method stub
				String nomarchivo = "wifimanager2.txt";
				String contenido = outfile;

				

				try {
					File tarjeta = Environment.getExternalStorageDirectory();
					File file = new File(tarjeta.getAbsolutePath(), nomarchivo);
					OutputStreamWriter osw = new OutputStreamWriter(
							new FileOutputStream(file));
					osw.write(contenido);
					osw.flush();
					osw.close();
				} catch (IOException ioe) {
				}

			}

		});

	}

	protected void onPause() {
		unregisterReceiver(wifiReciever);
		super.onPause();
	}

	protected void onResume() {
		registerReceiver(wifiReciever, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		super.onResume();
	}

	class WifiScanReceiver extends BroadcastReceiver {

		@SuppressLint("UseValueOf")
		public void onReceive(Context c, Intent intent) {

			// Escaneo---------------------------------------------------------------------

			outfile = outfile + "\n"+ "Scan #: "+count+ "\n";
			
			System.out.println("Final :" + "Escaneo-----------------------"
					+ count);

			if (count < 200) // -----------Numeros de Escaneos
			{
				List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
				wifis = new String[wifiScanList.size()];

				for (int i = 0; i < wifiScanList.size(); i++) {
					Long probeResponseTime = System.currentTimeMillis()
							- SystemClock.elapsedRealtime()
							+ (wifiScanList.get(i).timestamp / 1000);

					out = wifiScanList.get(i).BSSID;

					wifis[i] = out;

					if (!list_BSSID.contains(wifis[i])) {
						table_bssid_repe.put(out, 1.0);
						table_bssid_ssid.put(out, wifiScanList.get(i).SSID);
						table_bssid_level.put(out, wifiScanList.get(i).level);

						if (wifiScanList.get(i).frequency == 2412)
							table_bssid_channel.put(out, 1);
						if (wifiScanList.get(i).frequency == 2417)
							table_bssid_channel.put(out, 2);
						if (wifiScanList.get(i).frequency == 2422)
							table_bssid_channel.put(out, 3);
						if (wifiScanList.get(i).frequency == 2427)
							table_bssid_channel.put(out, 4);
						if (wifiScanList.get(i).frequency == 2432)
							table_bssid_channel.put(out, 5);
						if (wifiScanList.get(i).frequency == 2437)
							table_bssid_channel.put(out, 6);
						if (wifiScanList.get(i).frequency == 2442)
							table_bssid_channel.put(out, 7);
						if (wifiScanList.get(i).frequency == 2447)
							table_bssid_channel.put(out, 8);
						if (wifiScanList.get(i).frequency == 2452)
							table_bssid_channel.put(out, 9);
						if (wifiScanList.get(i).frequency == 2457)
							table_bssid_channel.put(out, 10);
						if (wifiScanList.get(i).frequency == 2462)
							table_bssid_channel.put(out, 11);
						if (wifiScanList.get(i).frequency == 2467)
							table_bssid_channel.put(out, 12);
						if (wifiScanList.get(i).frequency == 2472)
							table_bssid_channel.put(out, 13);
						if (wifiScanList.get(i).frequency == 2484)
							table_bssid_channel.put(out, 14);

						list_BSSID.add(wifis[i]);
					} else {
						table_bssid_repe
								.put(out, table_bssid_repe.get(out) + 1);
					}

				}

				Set<String> set = table_bssid_repe.keySet();
				Iterator<String> itr = set.iterator();
				while (itr.hasNext()) {
					str = itr.next();

					double prob = 0;
					prob = table_bssid_repe.get(str) / count;

					String prob_s = String.format("%.4f", prob);

					if (table_bssid_repe.get(str) == count)
						prob_s = String.format("%.0f", prob);

				}

				Set<Entry<String, Double>> sete = table_bssid_repe.entrySet();
				List<Entry<String, Double>> list2 = new ArrayList<Entry<String, Double>>(
						sete);
				Collections.sort(list2,
						new Comparator<Map.Entry<String, Double>>() {

							public int compare(Map.Entry<String, Double> o1,
									Map.Entry<String, Double> o2) {
								return (o2.getValue()).compareTo(o1.getValue());
							}
						});
				for (Map.Entry<String, Double> entry : list2) {

					double prob = 0;
					prob = entry.getValue() / count;

					String prob_s = String.format("%.3f", prob);

					if (entry.getValue() == count)
						prob_s = String.format("%.0f", prob);

					String output = "SSID: "
							+ table_bssid_ssid.get(entry.getKey()) + "\n";
					output = output + "Appear: " + entry.getValue().intValue()
							+ "\n";
					output = output + "Prob: " + prob_s + "\n";

					output = output + "Power: "
							+ table_bssid_level.get(entry.getKey()) + "\n";

					output = output + "Channel: "
							+ table_bssid_channel.get(entry.getKey()) + "\n";

					list_ssid.add(table_bssid_ssid.get(entry.getKey()));
					list_repe.add((entry.getValue().intValue()) + "");
					list_pw.add(table_bssid_level.get(entry.getKey()) + "");
					list_prob.add(prob_s);
					list_pw.add(table_bssid_level.get(entry.getKey()) + "");
					list_pwAvg.add("34");
					list_ch.add(table_bssid_channel.get(entry.getKey()) + "");

					fillCountryTable();

					// Salida por Consola de Escaneo
					System.err.println("Final: "
							+ table_bssid_ssid.get(entry.getKey()) + " "
							+ "Appear: " + entry.getValue().intValue() + " "
							+ "Prob: " + prob_s + " " + "Power: "
							+ table_bssid_level.get(entry.getKey()) + " "
							+ "Channel: "
							+ table_bssid_channel.get(entry.getKey()) + " ");

					wifis_list.add(output);

				}

				scan.setText(count + "");
				rq.setText(formatTime.format(new Date(probeRequestTime)) + "");

				mainWifiObj.startScan();
				probeRequestTime = System.currentTimeMillis();
				out = "";

				wifis_list = new ArrayList<String>();

				list_ssid = new ArrayList<String>();
				list_repe = new ArrayList<String>();
				list_pw = new ArrayList<String>();
				list_prob = new ArrayList<String>();
				list_pw = new ArrayList<String>();
				list_pwAvg = new ArrayList<String>();
				list_ch = new ArrayList<String>();

				count++;
				count2++;

			}// -----------------------------

			// Escaneo---------------------------------------------------------------------

		}

		public void grabar() {
			// TODO Auto-generated method stub
			String nomarchivo = "wifimanager.txt";
			String contenido = "";

			for (int i = 0; i < list_ssid.size(); i++) {
				contenido = list_ssid.get(i) + "\t" + list_repe.get(i) + "\t"
						+ list_prob.get(i) + "\t" + list_pw.get(i) + "\t"
						+ list_pwAvg.get(i) + "\t" + list_ch.get(i) + "\n";

			}

			try {
				File tarjeta = Environment.getExternalStorageDirectory();
				File file = new File(tarjeta.getAbsolutePath(), nomarchivo);
				OutputStreamWriter osw = new OutputStreamWriter(
						new FileOutputStream(file));
				osw.write(contenido);
				osw.flush();
				osw.close();
			} catch (IOException ioe) {
			}

		}

		private void deleteRow() {
			// TODO Auto-generated method stub

			country_table.removeAllViews();

		}

	}

	void fillCountryTable() {

		country_table.removeAllViews();

		TableRow row;
		TextView t1, t2, t3, t4, t5, t6;

		// Converting to dip unit
		int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				(float) 1, getResources().getDisplayMetrics());

		  
		
		for (int current = 0; current < list_ssid.size(); current++) {
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

			// ----

			t1.setText(list_ssid.get(current));
			t2.setText(list_repe.get(current));
			t3.setText(list_prob.get(current));

			if (Integer.parseInt(list_pw.get(current)) > -40)
				t4.setTextColor(Color.BLUE);

			if (Integer.parseInt(list_pw.get(current)) > -53
					&& Integer.parseInt(list_pw.get(current)) <= -40)
				t4.setTextColor(Color.CYAN);

			if (Integer.parseInt(list_pw.get(current)) > -68
					&& Integer.parseInt(list_pw.get(current)) <= -53)
				t4.setTextColor(Color.rgb(40, 251, 46));

			if (Integer.parseInt(list_pw.get(current)) > -83
					&& Integer.parseInt(list_pw.get(current)) <= -68)
				t4.setTextColor(Color.rgb(255, 111, 0));

			if (Integer.parseInt(list_pw.get(current)) > -96
					&& Integer.parseInt(list_pw.get(current)) <= -83)
				t4.setTextColor(Color.RED);

			t4.setText(list_pw.get(current));
			t5.setText(list_pwAvg.get(current));
			t6.setText(list_ch.get(current));

			outfile = outfile + list_ssid.get(current) + "\t"+ list_repe.get(current) + "\t"
					+ list_prob.get(current) + "\t"+ list_pw.get(current) + "\t" + list_pwAvg.get(current) + "\t" +list_ch.get(current)+"\n";

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
				row.setBackgroundColor(Color.rgb(202, 202, 202));

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
