package com.crud.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
//import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.Format;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import com.crud.dao.*;
import com.crud.model.Usuario;
//import com.practicaljava.lesson23.MyFrame.MyTableModel;


public class MainWindow extends JFrame {

	UsuarioDAO usuarioDAO;
	private JPanel pForm, pTable;
	private JLabel lName, lCpf, lPhone, lEmail,bDelete;
	private JTextField tfName, tfCpf, tfEmail, lMyDate;
	private JButton bCreate,  bUpdate, bRead, bPrint;
	private JCheckBox cData;
	private JScrollPane spTable;
	private JTable tTable;
	public String s;
	//List<Usuario> usuarios = usuarioDAO.readAll();
	//MyTableModel myTableModel; 
	
	public MainWindow() {
		super("Rekka skra N2");
		usuarioDAO = new UsuarioDAO();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		createForm();
		pack();
	refreshTable();
		//List<Usuario> usuarios = usuarioDAO.readAll();

	}
//	List<Usuario> usuarios = usuarioDAO.readAll();

	public void createForm() {
		pForm = new JPanel();
		pForm.setLayout(new GridLayout(5, 4, 10, 10));

		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		// pForm.setLayout(new java.awt.GridLayout(4, 3, 10, 5));

		lName = new JLabel("Nafn V");
		lName.setHorizontalAlignment(SwingConstants.RIGHT);
		tfName = new JTextField();
	//	tfName.setFocusable(false);
		pForm.add(lName);
		pForm.add(tfName);

		bCreate = new JButton("Skra ");
		bCreate.setBackground(Color.green);
		bCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				create();
			}
		});
		// for the easy tabulation in UI unfocus
		bCreate.setFocusable(false);
		pForm.add(bCreate);

		lCpf = new JLabel("Pontun N + ENTER");
		lCpf.setHorizontalAlignment(SwingConstants.RIGHT);
		tfCpf = new JTextField();
	
	
		  // ADD KEY LISTENER TO FATCH ISPAN DB
		tfCpf.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent w) {
			//&& tfName.getText().isEmpty()
				if (w.getKeyCode() == KeyEvent.VK_ENTER ) {
					  System.out.println("Pontun aCTION");
				FetchIspan ispan = new FetchIspan();
				//ispan.readNameIspan(tfCpf.getText());
				
					tfName.setText(ispan.readNameIspan(tfCpf.getText()));
					// Your job
					
					
				}
				
			}
		});
		

		pForm.add(lCpf);
		pForm.add(tfCpf);

		bRead = new JButton("Clear fields");
		bRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cleanFields();
			}
		});

		bRead.setFocusable(false);
		pForm.add(bRead);

		// cData = new JCheckBox("Dagsetn.");
		// cData.addItemListener( new ItemListener() {
		//
		// @Override
		// public void itemStateChanged(ItemEvent e) {
		// unableData();
		//
		// }
		// });
		// pForm.add(cData);
		//

		lPhone = new JLabel("Dags(Sjalfvirk): ");
		lPhone.setHorizontalAlignment(SwingConstants.RIGHT);
		lMyDate = new JTextField();
		// lMyDate.setFocusable(false);
		pForm.add(lPhone);
		pForm.add(lMyDate);
		lMyDate.setFocusable(false);
		bUpdate = new JButton("Uppfaera");
		bUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				update();
			}
		});

		bUpdate.setFocusable(false);
		pForm.add(bUpdate);

		lEmail = new JLabel("Rekka Numer: ");
		lEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		tfEmail = new JTextField();
		// react on enter to delete "rekka"
		tfEmail.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER ) {
					 System.out.println("aCTION");
			Toolkit.getDefaultToolkit().beep();
					create();
					delete();
					// Your job
				}

			}
		});

		pForm.add(lEmail);
		pForm.add(tfEmail);

		bDelete = new JLabel("Skra rekka N til ad Eyda");
	    bDelete.setBackground(Color.red);
		//bDelete.addActionListener(new ActionListener() {
		//	public void actionPerformed(ActionEvent evt) {
		//		delete();
		//	}
	//	});
		pForm.add(bDelete);

		cData = new JCheckBox("Dagsetn.Ovirkt");
		cData.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					unableData();

				else {

					enableData();
				}
			}
		});
		pForm.add(cData);

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
		pForm.add(bPrint);

		getContentPane().add(pForm);
       //   myTableModel = new MyTableModel();
		pTable = new JPanel();
		tTable = new JTable();
	//	tTable = new JTable(myTableModel);
		//Sorting row and col 
		
	    tTable.setAutoCreateRowSorter(true);
	   // BEGIN
	    pTable = new JPanel();
		tTable = new JTable();
		pTable.setLayout(new BorderLayout());
		tTable.setModel(new DefaultTableModel(new Object[5][5], new String[] {
				"REKKA", "Nafn", "Pontun", "Dagur", "ID" }));
		spTable = new JScrollPane();
		spTable.setViewportView(tTable);
		pTable.add(spTable, BorderLayout.CENTER);
		/*tTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				read();
			}
		});*/
	   // END
	/*    
		pTable.setLayout(new BorderLayout());
	tTable.setModel(new DefaultTableModel(new Object[5][5], new String[] {
				"ID", "Nafn", "Pontun n", "Dagsetning", "Rekka N" }));
		spTable = new JScrollPane();
		spTable.setViewportView(tTable);
		pTable.add(spTable, BorderLayout.CENTER);
		tTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// check
		 tTable.setColumnSelectionAllowed(false);*/
		/*tTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			read();
				// need to unfocus from the table to add a new entry?
				//tTable.setFocusable(false);
				  //translate coordinates 
			    
			
			}
		});*/
		//pTable.add(tTable);
		getContentPane().add(pTable);
		// focus on a Pontun at start of a programm
		this.addWindowListener( new WindowAdapter() {
			   public void windowOpened( WindowEvent e ){
			        tfCpf.requestFocus();
			     }
			   } ); 
	}

	protected void enableData() {
		// TODO Auto-generated method stub
		lMyDate.setFocusable(true);
	}

	protected void unableData() {
		// TODO Auto-generated method stub
		lMyDate.setFocusable(false);
	}

	public void cleanFields() {
		tfName.setText("");
		//tfName.requestFocus(true);
		tfCpf.setText("");
		tfCpf.requestFocus(true);
		lMyDate.setText("");
		tfEmail.setText("");
	}

	public boolean isValidData() {
		// if (tfName.getText().isEmpty() || tfCpf.getText().isEmpty()
		// || tfPhone.getText().isEmpty() || tfEmail.getText().isEmpty())

		if (tfName.getText().isEmpty() || tfCpf.getText().isEmpty()
				|| tfEmail.getText().isEmpty())
			return false;
		else
			return true;
	}

	private boolean isValidDataOption() {

	  List<Usuario> usuarios = usuarioDAO.readAll();
		// usuarios = usuarioDAO.readAll();*/

		for (Usuario ur : usuarios) {

			String rekka = ur.getEmail();
			if (rekka.equals(tfEmail.getText())) {

				int n = JOptionPane.showConfirmDialog(null, "ATH Rekka  "
						+ tfEmail.getText()

						+ " VAR skradur!\n " + "Fjarlaegja tad?", null,
						JOptionPane.YES_NO_OPTION);

				if (n == JOptionPane.YES_OPTION) {
					
// TRY TO USE UPDATE 					
					usuarioDAO.delete(ur.getId());

					Date date = new Date();

					Format formatter = new SimpleDateFormat("dd.MM.yyyy");
					s = formatter.format(date);

					Usuario user = new Usuario(tfName.getText(),
							tfCpf.getText(), s, tfEmail.getText());
					usuarioDAO.create(user);
					refreshTable();
					cleanFields();
					

					return false;

				}

				else if (n == JOptionPane.NO_OPTION) {
					System.out.println("pressed NO ");
					// recursion !!!

					refreshTable();
					cleanFields();

					return false;
				} else {
					return false;
				}

			}
		}
		return true;
	}

	public void refreshTable() {
		DefaultTableModel tableModel = (DefaultTableModel) tTable.getModel();
		tableModel.setNumRows(0);

		List<Usuario> usuarios = usuarioDAO.readAll();

		for (int linha = 0; linha < usuarios.size(); linha++) {
			Usuario user = usuarios.get(linha);

			tableModel.addRow(new Object[] { 1 });

			//tTable.setValueAt(user.getId(), linha, 0);
			tTable.setValueAt(user.getEmail(), linha, 0);
			tTable.setValueAt(user.getNome(), linha, 1);
			tTable.setValueAt(user.getCpf(), linha, 2);
			tTable.setValueAt(user.getTelefone(), linha, 3);
			tTable.setValueAt(user.getId(), linha, 4);
			//tTable.setValueAt(user.getEmail(), linha, 4);

		}
	}

	public int getSelectedId() {
		int linhaSelecionada = tTable.getSelectedRow();
		if (linhaSelecionada >= 0) {
			int idSelecionado = (int) tTable.getValueAt(linhaSelecionada, 0);
			return idSelecionado;
		} else {
			System.err.println("Selected line not existed");
			return -1;
		}
	}

	public void create() {

		Date date = new Date();
		// String dateWithoutTime = d.toString().substring(0, 10);
		// to put a data in a phone column
		Format formatter = new SimpleDateFormat("dd.MM.yyyy");
		s = formatter.format(date);

		// Usuario user = new Usuario(tfName.getText(), tfCpf.getText(),
		// tfPhone.getText(), tfEmail.getText());
		
		// insted tfName.getText() - calling a method which retrieve a name
        // SELECT Name FROM Table name WHERE PONTUN = tfCpf.getText 
		
		
		//FATCHING FROM ISPAN DB 
		// 
		/*FetchIspan fetchispan = new FetchIspan();
		String ispanName = fetchispan.readNameIspan(tfCpf.getText());*/
		
		Usuario user = new Usuario(tfName.getText(), tfCpf.getText(), s,
				tfEmail.getText());
		
		/*Usuario user = new Usuario(tfName.getText(), tfCpf.getText(), s,
				tfEmail.getText());*/

		if (isValidData() && isValidDataOption()) {
			usuarioDAO.create(user);
			refreshTable();
			// JOptionPane.showMessageDialog(null, "Vidstiptavinur  " +
			// tfName.getText()
			// + " skradur!");
			cleanFields();
		}
	}// else {

	// JOptionPane
	// .showMessageDialog(null,
	// "Vidskiptavinur EKKI uppfaerdur .Athugadu reitina og reyndu aftur!");
	// }

	public void read() {
		if (getSelectedId() >= 0) {
			Usuario user = usuarioDAO.read(getSelectedId());
			cleanFields();
			tfName.setText(user.getNome());
			tfCpf.setText(user.getCpf());
			lMyDate.setText(user.getTelefone());
			tfEmail.setText(user.getEmail());
		}
	}

	public void update() {
		if (getSelectedId() >= 0) {
			Usuario user = new Usuario(getSelectedId(), tfName.getText(),
					tfCpf.getText(), lMyDate.getText(), tfEmail.getText());
			usuarioDAO.update(user);
			refreshTable();
			cleanFields();
		}

	}

	public void delete() {
		DefaultTableModel tableModel = (DefaultTableModel) tTable.getModel();
		int linhaSelecionada = tTable.getSelectedRow();
		// try this
		if (linhaSelecionada >= 0 && tfCpf.getText().isEmpty()
				&& tfName.getText().isEmpty() && tfEmail.getText().isEmpty()) {
			// System.out.println("Vidsliptavinur med ID=" + getSelectedId()
			// + " fjarlaegdur.");
			usuarioDAO.delete(getSelectedId());
			tableModel.removeRow(linhaSelecionada);
			refreshTable();
			cleanFields();

		}
		// int i =Integer.parseInt(tfEmail.getText());
		else if (tfName.getText().isEmpty()
				&& tfCpf.getText().isEmpty() != (tfEmail.getText().isEmpty())) {
			System.out.println("Vidsliptavinur med Rekka N ="
					+ tfEmail.getText() + " fjarlaegdur.");
			// JOptionPane.showMessageDialog(null,
			// "Vidsliptavinur med Rekka N =" + tfEmail.getText()
			// + " fjarlaegdur.");
			usuarioDAO.deleteRekka(tfEmail.getText());

			refreshTable();
			cleanFields();

		}
		

		// else if (linhaSelecionada >= 0 ){
		// usuarioDAO.delete(getSelectedId());
		// tableModel.removeRow(linhaSelecionada);
		// refreshTable();
		// cleanFields();

		// }
		
	}
	
	/*class MyTableModel extends AbstractTableModel {
		List<Usuario> usuarios = usuarioDAO.readAll();

		String[] orderColNames = { "ID",   "name", 
                "order", "date"};
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return usuarios.size();
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 4;
		}

		@Override
		public Object getValueAt(int row, int col) {
			// TODO Auto-generated method stub
			switch (col) {
	    	  case 0:   // col 1
	    	    return usuarios.get(row).getId();
	    	  case 1:   // col 2
	    		  return usuarios.get(row).getNome();
	    	  case 2:   // col 3
	    		  return usuarios.get(row).getCpf();
	    	  case 3:   // col 4 
	    		  return usuarios.get(row).getEmail();
	    	  default:
	    	    return "";
	    	}
		}
		 public String getColumnName(int col){
		    	return orderColNames[col];
		    }
		
	}*/

}
