import javax.swing.JFrame;


import com.crud.gui.MainWindow3;


public class app {

	public static void main(String[] args) {
		 if (args.length == 0) {
		     System.out.println("\nUsage: java reciver'sEmail");
		     System.exit(0);
		   }
		MainWindow3 mainWindow3 = new MainWindow3();
	     mainWindow3.setVisible(true);
	     mainWindow3.setSize(700, 700);
		mainWindow3.setResizable(true);
		mainWindow3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
