import javax.swing.JFrame;


import com.crud.gui.MainWindow3;


public class app {

	public static void main(String[] args) {
		
		MainWindow3 mainWindow3 = new MainWindow3();
	     mainWindow3.setVisible(true);
	     mainWindow3.setSize(700, 800);
		mainWindow3.setResizable(true);
		mainWindow3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   
		/* if (args.length == 0) {
		     System.out.println("\nUsage: prog.jar  reciver'sEmail");
		   //  System.exit(0);
		 	MainWindow3 mainWindow3 = new MainWindow3("solveig");
		     mainWindow3.setVisible(true);
		     mainWindow3.setSize(700, 700);
			mainWindow3.setResizable(true);
			mainWindow3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		     
		     
		   }else{
		MainWindow3 mainWindow3 = new MainWindow3(args[0]);
	     mainWindow3.setVisible(true);
	     mainWindow3.setSize(700, 700);
		mainWindow3.setResizable(true);
		mainWindow3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   }*/
	}

}
