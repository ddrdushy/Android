package com.example.bluetooth;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class ConnectingThread extends Thread {
	private final static UUID uuid = UUID.fromString("fc5ffc49-00e3-4c8b-9cf1-6b72aad1001a");
	 private final BluetoothSocket bluetoothSocket;
	    private final BluetoothDevice bluetoothDevice;
BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
	    
	public ConnectingThread(BluetoothDevice Device) {
		// TODO Auto-generated constructor stub
	    BluetoothSocket temp = null;
        bluetoothDevice = Device;
        
        try {
            temp = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bluetoothSocket = temp;
	}

	 public void run() {
	        // Cancel any discovery as it will slow down the connection
	        bluetoothAdapter.cancelDiscovery();

	        try {
	            // This will block until it succeeds in connecting to the device
	            // through the bluetoothSocket or throws an exception
	            bluetoothSocket.connect();
	        } catch (IOException connectException) {
	            connectException.printStackTrace();
	            try {
	                bluetoothSocket.close();
	            } catch (IOException closeException) {
	                closeException.printStackTrace();
	            }
	        }

	        // Code to manage the connection in a separate thread
	        /*
	            manageBluetoothConnection(bluetoothSocket);
	        */
	    }

	    // Cancel an open connection and terminate the thread
	    public void cancel() {
	        try {
	            bluetoothSocket.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
}
