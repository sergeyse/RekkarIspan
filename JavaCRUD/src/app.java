import javax.swing.JFrame;

import com.crud.gui.MainWindow;
import com.crud.gui.MainWindow2;
import com.crud.gui.MainWindow3;


public class app {

	public static void main(String[] args) {
		MainWindow3 mainWindow3 = new MainWindow3();
	     mainWindow3.setVisible(true);
	     mainWindow3.setSize(700, 700);
		mainWindow3.setResizable(true);
		mainWindow3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
