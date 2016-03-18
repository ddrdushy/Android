package com.example.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Pairlist extends Activity {

	ListView lv;
	ArrayList<String> arr;
	ArrayAdapter<String> ad;
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
				Intent i=new Intent("send_Data");
				i.putExtra("selval", ((TextView)view).getText());
				startActivity(i);
			}
		});
		
	}
}
