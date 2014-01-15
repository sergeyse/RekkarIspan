package com.client;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.test.rmi.Constants;
import com.test.rmi.InterfaceRMI;

public class TestClient  {

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 */
//	public static void main(String[] args) throws RemoteException, NotBoundException {
		// TODO Auto-generated method stub
	public void runRemote() throws RemoteException, NotBoundException{
  Registry registry = LocateRegistry.getRegistry(Constants.RMISERVER_IP,Constants.RMI_PORT);
  InterfaceRMI remote = (InterfaceRMI) registry.lookup(Constants.RMI_ID);
    remote.runMyTask();
  
    
	}

}
