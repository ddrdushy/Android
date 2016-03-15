package com.example.bluetooth;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button b1,b2;
	BluetoothAdapter b_ada;
	int BLUETOOTH_RE=1;
	Set<BluetoothDevice> paired_devices;
	String plist[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				b_ada=BluetoothAdapter.getDefaultAdapter();
				
				if(b_ada==null)
				{
					Toast.makeText(getBaseContext(), "No Bluetooth", Toast.LENGTH_LONG).show();
				}
				else
				{
					if(!b_ada.isEnabled())
					{
						Intent i=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
						startActivityForResult(i,BLUETOOTH_RE);
					}
		
				}
			}
		});
		
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				paired_devices=b_ada.getBondedDevices();
				int count=paired_devices.size();
				plist=new String[count];
				int j=0;
				for (BluetoothDevice device : paired_devices) {
					plist[j]=device.getName().toString();
					j++;
				}
				Bundle bn=new Bundle();
				bn.putStringArray("key", plist);
				Intent in=new Intent("pair_filter");
				in.putExtras(bn);
				startActivity(in);
			}
		});
	}
	public void onActivityResult(int req_code,int res_code,Intent data)
	{
		if(req_code==BLUETOOTH_RE)
		{
			if(res_code==RESULT_OK)
			{
				Toast.makeText(getBaseContext(), "Bluetooth On", Toast.LENGTH_LONG).show();
			}
			if(req_code==RESULT_CANCELED)
			{
				Toast.makeText(getBaseContext(), "Bluetooth Failed", Toast.LENGTH_LONG).show();
			}
		}
		}

}
