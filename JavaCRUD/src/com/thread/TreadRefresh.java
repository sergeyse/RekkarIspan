package com.thread;

//abandoned feature for refreshing a table data remotely , when new record made.Insted added a  bottom "UPDATE" which changes  the color when the data changes.

import com.crud.dao.FetchGlerskalinn;
import com.crud.gui.MainWindow3;


public class TreadRefresh extends Thread  {
	
	MainWindow3 mw3;
	
	
	public TreadRefresh(MainWindow3 mw3){ 
		
		
		this.mw3 = mw3;
	}
	public void run(){
		FetchGlerskalinn fg = new FetchGlerskalinn();
		while (true ){
			System.out.println("start inf loop");
//		mw3.setFreshList(fg.readAll());
		 try {
			sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
		
	}

}
