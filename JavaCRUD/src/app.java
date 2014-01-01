import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;


import com.crud.gui.MainWindow3;
import com.test.rmi.InterfaceRMI;


public class app   extends UnicastRemoteObject implements InterfaceRMI{
	
        public 	static MainWindow3 mw ;

	protected app() throws RemoteException {
		//super();
		mw = new MainWindow3();
		  mw.setVisible(true);
		     mw.setSize(700, 800);
			mw.setResizable(true);
			mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// TODO Auto-generated constructor stub
		
		System.out.println("fire constructor app class");
	}  

/*	public static void main(String[] args) {
		mw = new MainWindow3();
	    mw.setVisible(true);
	     mw.setSize(700, 800);
		mw.setResizable(true);
		mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}*/
		   
	/*	 if (args.length == 0) {
		     System.out.println("\nUsage: prog.jar  reciver'sEmail");
		   //  System.exit(0);
		 	MainWindow3 mainWindow3 = new MainWindow3("solveig");
		     mainWindow3.setVisible(true);
		     mainWindow3.setSize(700, 700);
			mainWindow3.setResizable(true);
			mainWindow3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		     
		     
		   }else{
		MainWindow3 mainWindow3 = new MainWindow3();
	     mainWindow3.setVisible(true);
	     mainWindow3.setSize(700, 700);
		mainWindow3.setResizable(true);
		mainWindow3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   }
	}
*/
	@Override
	public void runMyTask() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Task exec");
	
           mw.buttonSkraINN.setForeground(Color.RED);
           mw.buttonSkraINN.setBackground(Color.RED);
      //    mw.showRefreshMSG();
           
		
	}

}
