package com.example.bluetooth;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
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
	ArrayList<String> plist=new ArrayList<String>();
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
			
			private BroadcastReceiver br;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				b_ada.startDiscovery();
				br = new BroadcastReceiver() {
				    public void onReceive(Context context, Intent intent) {
				        String action = intent.getAction();
				        // Whenever a remote Bluetooth device is found
				        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				            // Get the BluetoothDevice object from the Intent
				            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				            Toast.makeText(getBaseContext(), device.getName(), Toast.LENGTH_SHORT).show();
				            // Add the name and address to an array adapter to show in a ListView
				            plist.add(device.getName() + "\n" + device.getAddress());
				        }
				    }
				};
				
				paired_devices=b_ada.getBondedDevices();
				//bint count=paired_devices.size();
				for (BluetoothDevice device : paired_devices) {
					plist.add(device.getName().toString());
				}
				Bundle bn=new Bundle();
				bn.putStringArrayList("key", plist);
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
