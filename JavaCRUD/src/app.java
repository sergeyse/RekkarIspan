import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;

import rmi.RmiIntfServer;


import com.crud.gui.MainWindow3;


public class app {

	public static void main(String[] args) {
		 if (args.length == 0) {
		   /*  System.out.println("\nUsage: prog.jar  reciver'sEmail");
		   //  System.exit(0);
		 	MainWindow3 mainWindow3 = new MainWindow3("solveig");
		     mainWindow3.setVisible(true);
		     mainWindow3.setSize(700, 700);
			mainWindow3.setResizable(true);
			mainWindow3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		     */
			   System.out.println("\nUsage: prog.jar  reciver'sEmail");
			   //  System.exit(0);
			 	MainWindow3 mainWindow3 = new MainWindow3("sergiy");
			     mainWindow3.setVisible(true);
			     mainWindow3.setSize(700, 700);
				mainWindow3.setResizable(true);
				mainWindow3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
			     try {
					RmiIntfServer stub =  (RmiIntfServer)UnicastRemoteObject.exportObject(mainWindow3,0);
					Registry reg = LocateRegistry.getRegistry(1099);
			
					reg.rebind("MsgServise", stub);
					System.out.println("RMI run");
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			     
			     
		     
		   }else{
		MainWindow3 mainWindow3 = new MainWindow3(args[0]);
	     mainWindow3.setVisible(true);
	     mainWindow3.setSize(700, 700);
		mainWindow3.setResizable(true);
		mainWindow3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   }
	}

}
