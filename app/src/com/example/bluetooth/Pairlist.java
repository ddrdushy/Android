package com.example.bluetooth;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Pairlist extends Activity {

	ListView lv;
	String[] arr;
	ArrayAdapter<String> ad;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pairlist_layout);
		lv=(ListView)findViewById(R.id.lvid);
		Bundle bn=getIntent().getExtras();
		arr=bn.getStringArray("key");
		
		ad=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
		lv.setAdapter(ad);
		
	}
}
