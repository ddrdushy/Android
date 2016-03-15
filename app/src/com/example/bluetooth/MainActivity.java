package com.example.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button b1;
	BluetoothAdapter b_ada;
	int BLUETOOTH_RE=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b1=(Button)findViewById(R.id.button1);
		
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
