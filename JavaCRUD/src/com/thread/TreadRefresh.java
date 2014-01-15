package com.thread;

//abandoned feature for refreshing a table data remotely , when new record made.Insted added a  bottom "UPDATE" which changes  the color when the data changes.

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.client.TestClient;
import com.crud.dao.FetchGlerskalinn;
import com.crud.gui.MainWindow3;


public class TreadRefresh extends Thread  {
	
	
	

	public void run(){
		TestClient tc = new TestClient();
		
try {
	tc.runRemote();
} catch (RemoteException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (NotBoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		
		
	}

}
