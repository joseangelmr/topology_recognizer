package com.isis.wifimanager;

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
import android.os.SystemClock;
import android.support.v4.app.ListFragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint({ "NewApi", "SimpleDateFormat" })
public class MainActivity extends Activity {

	WifiManager mainWifiObj;
	WifiScanReceiver wifiReciever;
	ListView list;
	String wifis[];
	List<String> wifis_list = new ArrayList<String>();
	String str;

	ArrayList<String> list_BSSID = new ArrayList<String>();

	Hashtable<String, Double> contenedor_bssid = new Hashtable<String, Double>();

	Hashtable<String, String> contenedor_ssid = new Hashtable<String, String>();

	Map<String, Integer> ordenada = new HashMap<String, Integer>();

	ArrayList<String> list_mac = new ArrayList<String>();
	ArrayList<String> list_repe = new ArrayList<String>();
	ArrayList<String> list_prob = new ArrayList<String>();

	int count = 1;

	int count2 = 0;

	Long probeRequestTime;
	SimpleDateFormat formatTime;
	String out;

	TextView scan;
	TextView rq;

	private static ListFragment listActivity;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);

		scan = (TextView) findViewById(R.id.textView3);
		rq = (TextView) findViewById(R.id.textView4);

		list = (ListView) findViewById(R.id.listView1);
		mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifiReciever = new WifiScanReceiver();

		formatTime = new SimpleDateFormat("hh:mm:ss.SSSS");
		out = "";

		mainWifiObj.startScan();
		probeRequestTime = System.currentTimeMillis();

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

			List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
			wifis = new String[wifiScanList.size()];

			for (int i = 0; i < wifiScanList.size(); i++) {
				Long probeResponseTime = System.currentTimeMillis()
						- SystemClock.elapsedRealtime()
						+ (wifiScanList.get(i).timestamp / 1000);

				out = wifiScanList.get(i).BSSID;

				wifis[i] = out;

				if (!list_BSSID.contains(wifis[i])) {
					contenedor_bssid.put(out, 1.0);
					contenedor_ssid.put(out, wifiScanList.get(i).SSID);
					System.err.println("entra 2 NUEVOs" + wifis[i]);
					list_BSSID.add(wifis[i]);
				} else {
					System.out.println("entra 2 " + "REPETIDO ES " + wifis[i]);
					contenedor_bssid.put(out, contenedor_bssid.get(out) + 1);
					// contenedor_ssid.put(out, wifiScanList.get(i).SSID);
				}

				SpannableString ss = new SpannableString(out);
				ss.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 5, 0);

			}

			// ordenada.put(4,1);
			// ordenada.put(3,13);
			// ordenada.put(5,45);
			// ordenada.put(8,2);
			// ordenada.put(23,56);
			// ordenada.put(1,3);
			// ordenada.put(5,8);

			Set<String> set = contenedor_bssid.keySet();
			Iterator<String> itr = set.iterator();
			while (itr.hasNext()) {
				str = itr.next();

				System.out.println("fred " + count + " " + str + ": "
						+ contenedor_bssid.get(str));
				System.err.println("www " + count + " " + str + ": "
						+ contenedor_ssid.get(str));

				double prob = 0;
				prob = contenedor_bssid.get(str) / count;

				String prob_s = String.format("%.4f", prob);

				if (contenedor_bssid.get(str) == count)
					prob_s = String.format("%.0f", prob);

			}

			Set<Entry<String,  Double>> sete = contenedor_bssid.entrySet();
			List<Entry<String,  Double>> list2 = new ArrayList<Entry<String,  Double>>(
					sete);
			Collections.sort(list2,
					new Comparator<Map.Entry<String,  Double>>() {

						public int compare(Map.Entry<String,  Double> o1,
								Map.Entry<String,  Double> o2) {
							return (o2.getValue()).compareTo(o1.getValue());
						}
					});
			for (Map.Entry<String,  Double> entry : list2) {
				System.out
						.println(entry.getKey() + " ==== " + entry.getValue());
				
				double prob = 0;
				prob = entry.getValue()/count;
				
				String prob_s = String.format("%.3f", prob);
				
				if (entry.getValue() == count)
					prob_s = String.format("%.0f", prob);
				
				String output = "SSID :" + contenedor_ssid.get(entry.getKey()) + "\n";
				output = output + "Appear :" + entry.getValue().intValue() + "\n";
				output = output + "Prob :" + prob_s + "\n";
				
				
				
//						wifis_list.add(contenedor_ssid.get(entry.getKey()).toString() + " " + entry.getValue().intValue() + " " +  prob_s);
						wifis_list.add(output);
						
						
						
			}

			mainWifiObj.startScan();
			probeRequestTime = System.currentTimeMillis();
			out = "";

			scan.setText("  " + count + "");
			rq.setText("  " + formatTime.format(new Date(probeRequestTime))
					+ "");

			list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.simple_list_item_1, wifis_list));

			wifis_list = new ArrayList<String>();

			count++;
			count2++;

		}

				@SuppressLint("SimpleDateFormat")
		public String getDiffTime(Long start, Long end) {
			Date probeRequestTime = new Date(start);
			Date probeResponseTime = new Date(end);
			long diff = probeResponseTime.getTime()
					- probeRequestTime.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;

			return diffMinutes + "m: " + diffSeconds + "s";
		}

		public String longToString(Long a) {
			return Long.toString(a);
		}

	}

}
