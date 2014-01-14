
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.test.rmi.Constants;

public class RmiServer {

	/**
	 * @param args 
	 * @throws RemoteException
	 * @throws AlreadyBoundException 
	 */
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		// TODO Auto-generated method stub
		
		app a = new app();
       
		Registry reg = LocateRegistry.createRegistry(Constants.RMI_PORT);
		reg.rebind(Constants.RMI_ID, a);
		System.out.println("SERVER STARTED");

	}

}