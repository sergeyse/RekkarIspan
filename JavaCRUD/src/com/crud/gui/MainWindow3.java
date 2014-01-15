 package com.crud.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import printing.OutputPrinter;

import mailer.MailSender;

import com.crud.dao.FetchGlerskalinn;
import com.crud.dao.FetchIspan;
import com.crud.gui.MainWindow3.MyTableModel;
import com.crud.model.Entrence;
import com.sun.java.swing.plaf.windows.resources.windows;
import com.thread.TreadRefresh;

import csv.DTOPontunVasar;
import csv.StackReader;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.text.Format;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

//public class MainWindow3 extends JFrame implements TableModelListener {
public class MainWindow3 extends JFrame  implements TableModelListener {

	public JButton buttonSkraINN, buttonSkraUT, bPrint, bScan, bTestPr;// change to a private variables back and make public API 
	private JButton buttonSave;
	private JButton buttonCancel;
	private JTextField tfRekkaNumer;
	private JTextField tfpontunarNumerField;
	private JDialog pontunDialog;
	private JPanel pForm, pTable;
	private JScrollPane spTable;
	private JTable tTable;
	private JLabel rekkaNumerLable, logoLabel, pontunarNumerLabel;
	// private String email;
	FetchIspan fetchispan;
	FetchGlerskalinn fetchGlerskalinn;
	MyTableModel myTableModel;
	private final String orderGeymslaIspan = "391000";

	
  //  private  List<Entrence> entlist;
	// Constructor
	// public MainWindow3(String email) {
	public MainWindow3()  {
		super("Rekkar IspanHF Afgr 0.3 RMI");

		// this.email = email;

		try {
			UIManager
					.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	/*	TreadRefresh tr =new TreadRefresh(this);
		tr.start();*/
		fetchispan = new FetchIspan();
		fetchGlerskalinn = new FetchGlerskalinn();
		
	  // updateModel();
	
	
	myTableModel = new MyTableModel();
      
	
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
     
		
  
		createForm();
		pack();
 
	
	}
/*	public void setFreshList(List<Entrence> elist){ 
		//this.entlist= elist;
	//	MyTableModel  mtm = new MyTableModel();
		
	  myTableModel.refreshWithNewList(elist);
	  myTableModel = new MyTableModel();
	   
	   tTable.setModel(myTableModel);
		
	}*/



	private void updateModel() {
		// TODO Auto-generated method stub
		myTableModel = new MyTableModel();
		
		//do not foget a listener for a model after updates 
		myTableModel.addTableModelListener(this);
		
		tTable.setModel(myTableModel);
		
		System.out.println("updateJTAble");
		customRenderer();
		
		
	}



	public void createForm() {
		pForm = new JPanel();
		pForm.setLayout(new GridLayout(3, 2, 10, 10));

		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		rekkaNumerLable = new JLabel("  Skra Rekka Numer her :");
		rekkaNumerLable.setHorizontalAlignment(SwingConstants.CENTER);
		tfRekkaNumer = new JTextField();
		pForm.add(rekkaNumerLable);
		pForm.add(tfRekkaNumer);
   // Abandoned functionality button removed from UI 
		buttonSkraINN = new JButton("Update");//was "eyda ur keerfinu"
		buttonSkraINN.setForeground(Color.green);
		buttonSkraINN.setBackground(Color.green);

		buttonSkraINN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				updateModel();
				buttonSkraINN.setForeground(Color.green);
				buttonSkraINN.setBackground(Color.green);
				
				//delete();

			}

			private void delete() {
				if (rekkaEnterIsRight()) {
					List<Entrence> entr = fetchGlerskalinn.readAll();
					int row = -1;
					for (Entrence e : entr) {
						row = row + 1;
						int rekkaUI = Integer.parseInt(tfRekkaNumer.getText());
						int rekka = e.getRekkan();
						if (rekka == rekkaUI) {
							// this dialog is slowing us down :-)

							int n = JOptionPane.showConfirmDialog(null,
									"ATH Rekka  " + tfRekkaNumer.getText()

									+ " Verdur\n " + "Fjarlaegdur !", null,
									JOptionPane.YES_NO_OPTION);

							if (n == JOptionPane.YES_OPTION) {

								// TRY TO USE UPDATE
								fetchGlerskalinn.delete(rekkaUI);

								myTableModel.refreshWhenDelete(row);

							}

							else if (n == JOptionPane.NO_OPTION) {
								System.out.println("pressed NO "); // recursion

								// refreshTable();
								cleanFields();

							}

						}
					}

				}
			}

		});

		//pForm.add(buttonSkraINN);

		buttonSkraUT = new JButton("Skrá lánað Út eða geymsla");

		buttonSkraUT.setForeground(Color.GREEN);

		// JDialog settings
		pontunDialog = new JDialog(this, "Dialog", true);
		pontunDialog.setLayout(new BorderLayout());
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(2, 2));
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));

		buttonSave = new JButton("Save");
		buttonCancel = new JButton("Cancel");

		pontunarNumerLabel = new JLabel("Pontunar N: ( eða 11-GEYMSLA)");
		tfpontunarNumerField = new JTextField();
		
		tfpontunarNumerField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode()== KeyEvent.VK_ENTER){
					int pontunUI = Integer.parseInt(tfpontunarNumerField.getText());
					if (pontunUI == 11) {
						createGeymsla();
						pontunDialog.setVisible(false);
						
					} else {
						create();
						
						pontunDialog.setVisible(false);
					}
					cleanFields();
					
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		JLabel emptyLable = new JLabel("");
		p1.add(pontunarNumerLabel);
		p1.add(tfpontunarNumerField);

		p2.add(buttonCancel);
		p2.add(buttonSave);
		pontunDialog.add(BorderLayout.NORTH, emptyLable);
		pontunDialog.add(BorderLayout.CENTER, p1);
		pontunDialog.add(BorderLayout.SOUTH, p2);
		pontunDialog.setSize(300, 150);
		tfpontunarNumerField.requestFocus();
		
		// pontunDialog.pack();

		buttonSkraUT.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent skraUT) {
				// TODO Auto-generated method stub

				pontunDialog.setVisible(true);

			}
		});
		
		tfRekkaNumer.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode()== KeyEvent.VK_ENTER){
					
					pontunDialog.setVisible(true);
					
					
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		buttonCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent eCancel) {
				// TODO Auto-generated method stub
				pontunDialog.setVisible(false);
			}
		});

		buttonSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent eSave) {
				
				// TODO Auto-generated method stub
				
				// Integer.parseInt(tfpontunarNumerField.getText())!=11
				int pontunUI = Integer.parseInt(tfpontunarNumerField.getText());
				if (pontunUI == 11) {
					createGeymsla();
					pontunDialog.setVisible(false);
					
				} else {
					create();
					
					pontunDialog.setVisible(false);
				}
			
		

				
			}


		});
	
        /// change location of a button in UI

		bPrint = new JButton("Print");
		bPrint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent ignore) {
				MessageFormat header = new MessageFormat(
						"Page {0,number,integer}");
				try {
					tTable.print(JTable.PrintMode.FIT_WIDTH, header, null);
				} catch (java.awt.print.PrinterException e) {
					System.err.format("Cannot print %s%n", e.getMessage());
				}
			}
		});
		// change location of a button in UI
		pForm.add(bPrint);
	
		
		
		
	pForm.add(buttonSkraUT);
		
		
		
		
		//pForm.add(buttonSkraINN);

		/*bTestPr = new JButton("Test");
		bTestPr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				printToPrinter("Sergey");
			}
		});
		pForm.add(bTestPr);*/

		bScan = new JButton("Scan");
		bScan.addActionListener(new ActionListener() {
			// ATH pontun 11 geymsla ISPAN

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				StackReader sr = new StackReader();
				Set<DTOPontunVasar> rekkarPairsFromCsv = sr.allarPontunarRecksOnly;

				// TODO CHECK size of set ,IF >0 CALL deletAndCreate mehod OR
				// if (rekkaEnterIsRight().)
				deleteAndCreateExistedRekkarInLokalSQL(rekkarPairsFromCsv);

			}

			private void deleteAndCreateExistedRekkarInLokalSQL(
					Set<DTOPontunVasar> allRecks) {

				// TO DO careful here with loops flip it around order dose
				// matter ?
				DTOPontunVasar temp = null;
				List<Entrence> entr = fetchGlerskalinn.readAll();
				int rekkaFromCSV = 0;
				int pontunarNumerCSV = 0;
				int row = -1;
				for (Entrence e : entr) {
					row = row + 1;

					for (DTOPontunVasar r : allRecks) {
						rekkaFromCSV = Integer.parseInt(r.getVasaN());
						pontunarNumerCSV = Integer.parseInt(r.getPontunarN());
						int rekka = e.getRekkan();
						if (rekka == rekkaFromCSV) {

							// TRY TO USE UPDATE
							fetchGlerskalinn.delete(rekkaFromCSV);

							myTableModel.refreshWhenDelete(row);
							// we removed a row and need to update table . now
							// is one row less in a table so out of bound
							// exception not happen
							row = row - 1;
							/*
							 * createSqlOnScanPressFromCSV(pontunarNumerCSV,
							 * rekkaFromCSV);
							 */

						}
					}

					// TO DO create method for a sql query creation and call it
					// when
					// action event is triggered
				}
				// recording in a loop to a local db (mysql)
				for (DTOPontunVasar re : allRecks) {
					System.out
							.println("Starting loop for recording from hashset ");
					int rekkaFromCSV2 = Integer.parseInt(re.getVasaN());
					int pontunarNumerCSV2 = Integer.parseInt(re.getPontunarN());
					createSqlOnScanPressFromCSV(pontunarNumerCSV2,
							rekkaFromCSV2);
				}

				if (allRecks.size() > 0) {
					Set<DTOPontunVasar> recksToEmailWithoutGeymsla = new HashSet<DTOPontunVasar>();

					for (DTOPontunVasar rk : allRecks) {

						if (orderGeymslaIspan.equals(rk.getPontunarN())) {

						} else {
							temp = new DTOPontunVasar(rk.getPontunarN(), rk
									.getVasaN());

							recksToEmailWithoutGeymsla.add(temp);
						}

					}
					if (recksToEmailWithoutGeymsla.size() > 0) {// we check if
																// any of
																// orders, which
																// suppose to go
																// out, are left
																// in
																// collection/

						/*
						 * MailSender ms = new
						 * MailSender(recksToEmailWithoutGeymsla, email);
						 * ms.sendMailConfirmed();
						 */
					}
				}

			}

			private void createSqlOnScanPressFromCSV(int csvPontun, int csvRekka) {

				// attention empty values give a null point exception

				if (csvPontun > 0 && csvRekka > 0) {
					Entrence tempentralldata = fetchispan.createReadNameIspan(
							csvPontun, csvRekka);
					// from fetchispan is called to fetchglerskalinn and doing
					// writing to a local db with data from remote sql server
					myTableModel.refresh(tempentralldata);

					cleanFields();
				} else
					System.out.println("Dialog here");
				// JOptionPane.showMessageDialog(pForm, "Enkin Rekki");
			}
		});

		// remove possibility to get entrance from CSV
		// pForm.add(bScan);

		/*
		 * BufferedImage myPicture = null; try { myPicture = ImageIO.read(new
		 * File("logo2.JPG")); } catch (IOException e1) { // TODO Auto-generated
		 * catch block e1.printStackTrace(); } logoLabel = new JLabel(new
		 * ImageIcon( myPicture ));
		 */

		logoLabel = new JLabel("                    ");
		// logoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(logoLabel);

		getContentPane().add(pForm);

		pTable = new JPanel();
		tTable = new JTable(myTableModel);
			myTableModel.addTableModelListener(this);
		

		// Sorting row and col
 
		tTable.setAutoCreateRowSorter(true);

		pTable.setLayout(new BorderLayout());

		// custom renderer
		
		customRenderer();
	
/*
		TableColumn column3 = tTable.getColumnModel().getColumn(3);
		
		
         
		column3.setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int col) {

				JLabel label = (JLabel) super.getTableCellRendererComponent(
						table, value, isSelected, hasFocus, row, col);

				// right-align the value
				label.setHorizontalAlignment(JLabel.RIGHT);

				// display that more than $100 in red
				if ((Integer) value <= 999) {
					label.setForeground(Color.RED);
				} else if ((Integer) value >= 1000
						&& Integer.parseInt(value.toString()) <= 1999) {
					label.setForeground(Color.GREEN);
				} else {

					label.setForeground(Color.BLUE);
				}

				return label;

			} // end of getTableCellRendererComponent
		} // end of new DefaultTableCellRenderer
		); // end of setCellRenderer(...)
		
		TableColumn tcDate = tTable.getColumnModel().getColumn(2);
		tcDate.setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			private Date dateValue;
			private SimpleDateFormat sdfNewValue = new SimpleDateFormat("dd.MM.yyyy");
			private String valueToString = "";
			
			public void setValue(Object value){
				  if ((value != null)) {
				       String stringFormat = value.toString();
				        try {
				            dateValue = sdfNewValue.parse(stringFormat);
				        } catch (ParseException e) {
				            e.printStackTrace();
				        }
				 valueToString = sdfNewValue.format(dateValue);
				      value = valueToString;
			     //value = dateValue;
				    }
				super.setValue(value);
				
				}
				
				
				
				
			}
			
			// end of getTableCellRendererComponent
		 // end of new DefaultTableCellRenderer
		); // end of setCellRenderer(...)

		// column size

		TableColumn columnSize = null;
		for (int i = 0; i < 8; i++) {
			columnSize = tTable.getColumnModel().getColumn(i);
			if (i == 0) {
				columnSize.setPreferredWidth(150); // third column is bigger
			} else if (i == 1) {
				columnSize.setPreferredWidth(60);
			} else if (i == 3) {
				columnSize.setPreferredWidth(50);
			} else if (i == 7) {
				columnSize.setPreferredWidth(150);
			} else {
				columnSize.setPreferredWidth(70);
			}
		}*/

		spTable = new JScrollPane(tTable);
		spTable.setViewportView(tTable);
		pTable.add(spTable, BorderLayout.CENTER);

		getContentPane().add(pTable);
	}

	



	private void customRenderer() {
		TableColumn column3 = tTable.getColumnModel().getColumn(3);
		
		
        
		column3.setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int col) {

				JLabel label = (JLabel) super.getTableCellRendererComponent(
						table, value, isSelected, hasFocus, row, col);

				// right-align the value
				label.setHorizontalAlignment(JLabel.RIGHT);

				// display that more than $100 in red
				if ((Integer) value <= 999) {
					label.setForeground(Color.RED);
				} else if ((Integer) value >= 1000
						&& Integer.parseInt(value.toString()) <= 1999) {
					label.setForeground(Color.GREEN);
				} else {

					label.setForeground(Color.BLUE);
				}

				return label;

			} // end of getTableCellRendererComponent
		} // end of new DefaultTableCellRenderer
		); // end of setCellRenderer(...)
		
/*		TableColumn tcDate = tTable.getColumnModel().getColumn(2);
		tcDate.setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			private Date dateValue;
			private SimpleDateFormat sdfNewValue = new SimpleDateFormat("dd.MM.yyyy");
			private String valueToString = "";
			
			public void setValue(Object value){
				  if ((value != null)) {
				       String stringFormat = value.toString();
				        try {
				            dateValue = sdfNewValue.parse(stringFormat);
				        } catch (ParseException e) {
				            e.printStackTrace();
				        }
				 valueToString = sdfNewValue.format(dateValue);
				      value = valueToString;
			     //value = dateValue;
				    }
				super.setValue(value);
				
				}
				
				
				
				
			}
			
			// end of getTableCellRendererComponent
		 // end of new DefaultTableCellRenderer
		)*/; // end of setCellRenderer(...)

		// column size

		TableColumn columnSize = null;
		for (int i = 0; i < 8; i++) {
			columnSize = tTable.getColumnModel().getColumn(i);
			if (i == 0) {
				columnSize.setPreferredWidth(150); // third column is bigger
			} else if (i == 1) {
				columnSize.setPreferredWidth(60);
			} else if (i == 3) {
				columnSize.setPreferredWidth(50);
			} else if (i == 7) {
				columnSize.setPreferredWidth(150);
			} else {
				columnSize.setPreferredWidth(70);
			}
		}
		
	}
	private boolean isDobleEntryNOTPresent() {

		List<Entrence> entr = fetchGlerskalinn.readAll();
		int row = -1;
		for (Entrence e : entr) {
			row = row + 1;
			int rekkaUI = Integer.parseInt(tfRekkaNumer.getText());
			int rekka = e.getRekkan();
			if (rekka == rekkaUI) {

				int n = JOptionPane.showConfirmDialog(null, "ATH Rekka  "
						+ tfRekkaNumer.getText()

						+ " VAR skradur!\n " + "Fjarlaegja tad?", null,
						JOptionPane.YES_NO_OPTION);

				if (n == JOptionPane.YES_OPTION) {
					// another if for creation of a GEYSLU in Ispan
					if (rekkaUI == 11) {
						Date date = new Date();

						Format formatter = new SimpleDateFormat("dd.MM.yyyy");
						String s = formatter.format(date);

						Entrence entrenseGeymsla = new Entrence(
								"ISPAN GEYMSLA", 11, date, rekka, "5454300",
								"Smidjuvegur", "Kopavogur");
						fetchGlerskalinn.delete(rekkaUI);
						myTableModel.refreshWhenDelete(row);
						fetchGlerskalinn.create(entrenseGeymsla);
						myTableModel.refresh(entrenseGeymsla);
						return false;
					} else

						// TRY TO USE UPDATE
						fetchGlerskalinn.delete(rekkaUI);

					myTableModel.refreshWhenDelete(row);

					create();

					cleanFields();

					return false;

				}

				else if (n == JOptionPane.NO_OPTION) {
					System.out.println("pressed NO ");
					// recursion !!!

					// refreshTable();
					cleanFields();

					return false;
				} else {
					return false;
				}

			}
		}
		return true;
	}

	private void cleanFields() {
		// TODO Auto-generated method stub
		tfpontunarNumerField.setText("");
		tfRekkaNumer.setText("");

	}

	public void create() {

		// FATCHING FROM ISPAN DB
		if (isDobleEntryNOTPresent()&& rekkaEnterIsRight()) {

			int rekka = Integer.parseInt(tfRekkaNumer.getText());
			int pontun = Integer.parseInt(tfpontunarNumerField.getText());
			Entrence tempentralldata = fetchispan.createReadNameIspan(pontun,
					rekka);

			// JOptionPane.showMessageDialog(null, "Vidstiptavinur  " +
			// tfName.getText()
			// + " skradur!");
			myTableModel.refresh(tempentralldata);
          //   printToPrinter(tempentralldata.getNafn());
			cleanFields();
		}

		else {
			cleanFields();
		}

	}

	public boolean isNoDoubleForGeymsla() {
		List<Entrence> entr = fetchGlerskalinn.readAll();
		int row = -1;
		for (Entrence e : entr) {
			row = row + 1;
			int rekkaUI = Integer.parseInt(tfRekkaNumer.getText());
			int rekka = e.getRekkan();
			if (rekka == rekkaUI) {

				int n = JOptionPane.showConfirmDialog(null, "ATH Rekka  "
						+ tfRekkaNumer.getText()

						+ " VAR skradur!\n " + "Fjarlaegja tad?", null,
						JOptionPane.YES_NO_OPTION);

				if (n == JOptionPane.YES_OPTION) {

					// TRY TO USE UPDATE
					fetchGlerskalinn.delete(rekkaUI);

					myTableModel.refreshWhenDelete(row);

					createGeymsla();

					cleanFields();

					return false;

				}

				else if (n == JOptionPane.NO_OPTION) {
					System.out.println("pressed NO ");
					// recursion !!!

					// refreshTable();
					cleanFields();

					return false;
				} else {
					return false;
				}

			}
		}
		return true;
	}

	public void createGeymsla() {
		if (isNoDoubleForGeymsla()) {
			int rekkaUI = Integer.parseInt(tfRekkaNumer.getText());
			Date date = new Date();

			Format formatter = new SimpleDateFormat("dd.MM.yyyy");
			String s = formatter.format(date);

			Entrence entrenseGeymsla = new Entrence("ISPAN GEYMSLA", 11, date,
					rekkaUI, "5454300", "Smidjuvegur", "Kopavogur");

			fetchGlerskalinn.create(entrenseGeymsla);
			myTableModel.refresh(entrenseGeymsla);
		} else {
			cleanFields();
		}

	}

	class MyTableModel extends AbstractTableModel  {
		

		FetchGlerskalinn fetchGlerskalinn = new FetchGlerskalinn();
		
		List<Entrence> entlist = fetchGlerskalinn.readAll();
		
	
		
		
		public  void refreshWithNewList( List<Entrence> entrlist) {
			this.entlist = entrlist;
			// TODO Auto-generated method stub
			
			
			fireTableDataChanged();
			
		}

		String[] orderColNames = { "Nafn", "Pontun", "Dagsetning", "Rekka",
				"Siminn", "Gata", "PostN", "ATH ! ","Buinn ad hringja" };

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return entlist.size();
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 8;
		}

		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return String.class;
			case 1:
				return Integer.class;
			case 2:
				return Date.class;
			case 3:
				return Integer.class;
			case 4:
				return String.class;
			case 5:
				return String.class;
			case 6:
				return String.class;
			case 7:
				return String.class;
			case 8 : 
				return Boolean.class;

			default:
				return String.class;
			}
		}

		@Override
		public Object getValueAt(int row, int col) {
			// TODO Auto-generated method stub
			switch (col) {
			case 0: // col 1
				return entlist.get(row).getNafn();
			case 1: // col 2
				return entlist.get(row).getPontun();
			case 2: // col 3
				return entlist.get(row).getDagsetning();
			case 3: // col 4
				return entlist.get(row).getRekkan();
			case 4:
				return entlist.get(row).getSiminn();
			case 5:
				return entlist.get(row).getGata();
			case 6:
				return entlist.get(row).getPostn();
			case 7:
				return entlist.get(row).getMsgATH();
			case 8:
				return entlist.get(row).getCallChecked();
			default:
				return "";
			}
		}

		public String getColumnName(int col) {
			return orderColNames[col];
		}

		public boolean isCellEditable(int row, int col) {

			if (col == 7 || col== 8) {
				return true;
			} else {
				return false;
			}
		}

		// Update the model when the use changes the quantity
		public void setValueAt(Object writtenMsg, int row, int col) {
			
			if (col == 7) {
				String msgFromtable = writtenMsg.toString();
				entlist.get(row).setMsgATH(msgFromtable);
				
				
			}else if (col == 8){
				Boolean chechedcall= (Boolean)writtenMsg;
				entlist.get(row).setCallChecked(chechedcall);
			}

			// Notify the world about the change
			// fireTableDataChanged();
		

			TableModelEvent event = new TableModelEvent(this, row, row, col);
			fireTableChanged(event);
			
		}

		public void refresh(Entrence tempentralldata) {
			entlist.add(tempentralldata);
			fireTableRowsInserted(entlist.size() - 1, entlist.size() - 1);
		}

		public void refreshWhenDelete(int row) {
			entlist.remove(row);// fetchGlerskalinn read all
			// fireTableDataChanged();
			fireTableRowsDeleted(row, row);
		} 
		
	
		
		

		//if we remove tabelModelListeners and implement all functionality in setValueAt 
		
	}
	public void tableChanged(TableModelEvent eventDataChanged) {
		
		// TODO Auto-generated method stub
		
		int row = eventDataChanged.getFirstRow();
		//int lrow = eventDataChanged.getLastRow();
		int column = eventDataChanged.getColumn();
		if (column==7){
			MyTableModel model = (MyTableModel) eventDataChanged.getSource();
			// String columnName = model.getColumnName(column);// hardcoded in our
			// case - "ath" name
			Object dataChanged = model.getValueAt(row, column);
			Object rekkaN = model.getValueAt(row, 3);
			
			
			// int rekkaNoSendToSQL =Integer.parseInt(rekkaN.toString());
			int rekkaNoSendToSQL = (int) rekkaN;
			String data = dataChanged.toString();
			
			fetchGlerskalinn.createMSG(data, rekkaNoSendToSQL);
			System.out.println("MSG creation "+rekkaNoSendToSQL);
		}else if(column ==8){
			MyTableModel model = (MyTableModel) eventDataChanged.getSource();
			Object rekkaN = model.getValueAt(row, 3);
			boolean dataChanged = (boolean) model.getValueAt(row, column);
			int rekkaNoSendToSQL = (int) rekkaN;
			fetchGlerskalinn.createCheck(dataChanged,rekkaNoSendToSQL);
			System.out.println("added boolean ");
			
			
		}
				
		
		
	}


	/*
	 * @Override public void tableChanged(TableModelEvent e) { // TODO
	 * Auto-generated method stub int row = e.getFirstRow(); int column =
	 * e.getColumn(); MyTableModel model = (MyTableModel) e.getSource(); //
	 * String columnName = model.getColumnName(column); Object data =
	 * model.getValueAt(row, column); String testname = data.toString(); Object
	 * id = model.getValueAt(row, 0); String idtosql = id.toString();
	 * usuarioDAO.update(idtosql, testname); }
	 */

	public boolean rekkaEnterIsRight() {

		int rekka = Integer.parseInt(tfRekkaNumer.getText());

		if (rekka > 0 && rekka < 2999) {
			return true;
		} else {
			JOptionPane.showMessageDialog(this, " ATH!Athuga Rekka numer !",
					" ", JOptionPane.ERROR_MESSAGE);
			return false;
		}

	}

	private void printToPrinter( String nameToPrint) {
		Date date = new Date();
		Format formatter = new SimpleDateFormat("dd.MM.yyyy");
		String s = formatter.format(date);

		/*BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("logo2.JPG"));
		} catch (IOException e1) { // TODO Auto-generated
			e1.printStackTrace();
		}
		logoLabel = new JLabel(new ImageIcon(myPicture));*/
		// String printData = CalculationTextArea.getText() + "\n" +
		// SpecificTextArea.getText();
	
	   
		
		String printData = "ISPAN EHF\nSMIÐJUVEGUR 7\n200KÓPAVOGUR\nSIM.5454 300\nFAX.545 4300\n"
			
				+ 

				"Dags : "
				+ s
				+ " "
				+ " \n"
			
				
				+ "Undirritaður, hefur móttekið flutningsrekka, í eigu Íspan ehf, og með undirskrift sinni\nsamþykkt skilmála Íspan varðandi lán og leigu á rekkum, en rekkar eru lánaðir\nviðskiptavinum Íspan ehf., þeim að kostnaðarlausu, allt að 7 daga, en að þeim tíma liðnum\nreiknast leigugjald samkvæmt gjaldskrá Íspan, hverju sinni, fyrir hvern byrjaðan\nsólarhring. Á sama hátt tekur viðkomandi, fulla ábyrgð á þeim rekkum sem hann hefur í\nsinni umsjá. Íspan ehf., sækir rekka á höfuðborgarsvæðinu, en nauðsynlegt er að hafa\nsamband í síma 54-54-300, til þess. Viрskiptavinir á landsbyggðinni verða að senda \nrekkana, á sinn kostnað  til: Íspan ehf. Smiрjuvegi 7, 200 Kópavogi. (Íspan sækir rekka á \nflutningastöðvar í Reykjavík, án aukagjalds)\n"+"\n"+
				"Rekki N:  "+tfRekkaNumer.getText() +"\n"
			    +"Pöntun N: "+ tfpontunarNumerField.getText()
			    +""
				+"\n" +
			    		"Viðskiptav:" +nameToPrint+
			    		"\n" +
			    		"___________________________________________________________________________________________\n" +
			    		"SAMÞYKKI VIVÐSKIPTAVINAR\n" +
			    		"___________________________________________________________________________________________\n" +
			    		"\n" +
			    		"    Rekka/ rekkumm skilað , dags._______________________________________________________\n" +
			    		"";
				
			
				
				
				//;
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new OutputPrinter(printData));
		boolean doPrint = job.printDialog();
		if (doPrint) {
			try {
				job.print();
			} catch (PrinterException e) {
				// Print job did not complete.
			}
		}
	}



	public void showRefreshMSG() {
		// TODO Auto-generated method stub
		System.out.println("refresh msg");
		buttonSkraINN.setForeground(Color.RED);
		
	}


}
