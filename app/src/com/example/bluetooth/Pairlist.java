package com.example.bluetooth;

import java.util.ArrayList;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Pairlist extends Activity {

	ListView lv;
	ArrayList<String> arr;
	ArrayAdapter<String> ad;
	BluetoothAdapter b_ada=BluetoothAdapter.getDefaultAdapter();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pairlist_layout);
		lv=(ListView)findViewById(R.id.lvid);
		Bundle bn=getIntent().getExtras();
		arr=bn.getStringArrayList("key");
		
		ad=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
		lv.setAdapter(ad);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				 String  itemValue = (String) lv.getItemAtPosition(position);
			        String MAC = itemValue.substring(itemValue.length() - 17);
			        BluetoothDevice bluetoothDevice = b_ada.getRemoteDevice(MAC);
			        // Initiate a connection request in a separate thread
			        ConnectingThread t = new ConnectingThread(bluetoothDevice);
			        t.start();
				
			}
		});
		
	}
}
