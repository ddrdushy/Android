package com.example.bluetooth;

import java.io.IOException;
import java.io.OutputStream;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendData extends Activity{

	Button b1;
	TextView tv1;
	EditText ed1,ed2;
	String val;
	private BluetoothAdapter btAdapter = null;
	BluetoothSocket socket;
	private Set<BluetoothDevice>pairedDevices;
	OutputStream outStream = null;
	String address = "00:00:00:00:00:00";
	ParcelUuid[] uuids ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input);
		
		b1=(Button)findViewById(R.id.b1);
		tv1=(TextView)findViewById(R.id.textView1);
		ed1=(EditText)findViewById(R.id.editText1);
		ed2=(EditText)findViewById(R.id.editText2);
		
		btAdapter=BluetoothAdapter.getDefaultAdapter();
		
		Intent i=getIntent();
		val=i.getStringExtra("selval");
		tv1.setText(val);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					if(!ed1.getText().equals(""))
						sendData(ed1.getText().toString());
					else
						Toast.makeText(getBaseContext(), "Type Aything to send", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		 
	    Log.d("TAG", "...In onResume - Attempting client connect...");
	    pairedDevices = btAdapter.getBondedDevices();
	    BluetoothDevice device = null;
	    for(BluetoothDevice bt : pairedDevices)
	    {
	    	if(bt.getName().toString().equals(val))
	    	{
	    		address=bt.getAddress();
                uuids = bt.getUuids();
                device=bt;
	    	}
	    }
	    // Set up a pointer to the remote node using it's address.
	    
	    try {
			socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
			Toast.makeText(getApplicationContext(), uuids[0].getUuid().toString(), Toast.LENGTH_SHORT).show();
			socket.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    try {
	        outStream = socket.getOutputStream();
	      } catch (IOException e) {
	        Log.d("Fatal Error", "In onResume() and output stream creation failed:" + e.getMessage() + ".");
	      }
	}
	
	@Override
	  public void onPause() {
	    super.onPause();
	 
	    Log.d("tag", "...In onPause()...");
	 
	    if (outStream != null) {
	      try {
	        outStream.flush();
	      } catch (IOException e) {
	    	  Log.d("Fatal Error", "In onPause() and failed to flush output stream: " + e.getMessage() + ".");
	      }
	    }
	 
	    try     {
	    	socket.close();
	    } catch (IOException e2) {
	    	Log.d("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
	    }
	  }
	
	 private void sendData(String message) {
		    byte[] msgBuffer = message.getBytes();
		 
		    Log.d("TAG", "...Sending data: " + message + "...");
		 
		    try {
		      outStream.write(msgBuffer);
		    } catch (IOException e) {
		      String msg = "In onResume() and an exception occurred during write: " + e.getMessage();
		      if (address.equals("00:00:00:00:00:00")) 
		        msg = msg + ".\n\nUpdate your server address from 00:00:00:00:00:00 to the correct address on line 37 in the java code";
		     // msg = msg +  ".\n\nCheck that the SPP UUID: " + MY_UUID.toString() + " exists on server.\n\n";
		       
		      //errorExit("Fatal Error", msg);       
		    }
		  }

}



