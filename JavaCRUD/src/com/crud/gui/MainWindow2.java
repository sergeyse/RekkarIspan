package com.crud.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.crud.dao.*;
import com.crud.model.Entrence;
import com.crud.model.Usuario;

//import com.practicaljava.lesson23.MyFrame.MyTableModel;

public class MainWindow2 extends JFrame implements TableModelListener {

	FetchIspan fetchispan;
	FetchGlerskalinn fetchGlerskalinn;
	private JPanel pForm, pTable;
	private JLabel lName, lCpf, lPhone, lEmail, bDelete;
	private JTextField tfName, tfCpf, tfEmail, lMyDate;
	private JButton bCreate, bUpdate, bRead, bPrint;
	private JCheckBox cData;
	private JScrollPane spTable;
	private JTable tTable;
	public String s;

	MyTableModel myTableModel;

	public MainWindow2() {
		super("Rekka skra");
		fetchispan = new FetchIspan();
		fetchGlerskalinn = new FetchGlerskalinn();
		myTableModel = new MyTableModel();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		createForm();
		pack();

	}

	public void createForm() {
		pForm = new JPanel();
		pForm.setLayout(new GridLayout(5, 4, 10, 10));

		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		// pForm.setLayout(new java.awt.GridLayout(4, 3, 10, 5));

		lName = new JLabel("Nafn Vidskiptavins");
		lName.setHorizontalAlignment(SwingConstants.RIGHT);
		tfName = new JTextField();
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

		lCpf = new JLabel("Pontun N ");
		lCpf.setHorizontalAlignment(SwingConstants.RIGHT);
		tfCpf = new JTextField();
		// ADD KEY LISTENER TO FATCH ISPAN DB

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

		lPhone = new JLabel("Dags(Sjalfvirk): ");
		lPhone.setHorizontalAlignment(SwingConstants.RIGHT);
		lMyDate = new JTextField();
		// lMyDate.setFocusable(false);
		pForm.add(lPhone);
		pForm.add(lMyDate);

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
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("aCTION");
					// delete();
					create();
					// Your job
				}

			}
		});

		pForm.add(lEmail);
		pForm.add(tfEmail);

		bDelete = new JLabel("Skra rekka N til ad Eyda");
		bDelete.setBackground(Color.red);

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

		pTable = new JPanel();
		tTable = new JTable(myTableModel);
		// Sorting row and col
		myTableModel.addTableModelListener(this);

		tTable.setAutoCreateRowSorter(true);

		pTable.setLayout(new BorderLayout());

		// custom renderer

		TableColumn column = tTable.getColumnModel().getColumn(3);

		column.setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int col) {

				JLabel label = (JLabel) super.getTableCellRendererComponent(
						table, value, isSelected, hasFocus, row, col);

				// right-align the value
				label.setHorizontalAlignment(JLabel.RIGHT);

				// display that more than $100 in red
				if ((Integer) value <= 99) {
					label.setForeground(Color.RED);
				} else if ((Integer) value >= 100
						&& Integer.parseInt(value.toString()) <= 199) {
					label.setForeground(Color.GREEN);
				} else {

					label.setForeground(Color.BLUE);
				}

				return label;

			} // end of getTableCellRendererComponent
		} // end of new DefaultTableCellRenderer
		); // end of setCellRenderer(...)

		spTable = new JScrollPane(tTable);
		spTable.setViewportView(tTable);
		pTable.add(spTable, BorderLayout.CENTER);

		getContentPane().add(pTable);
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
		tfName.requestFocus(true);
		tfCpf.setText("");
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

	private boolean isDobleEntryNOTPresent() {

		List<Entrence> entr = fetchGlerskalinn.readAll();
       int row = -1;
		for (Entrence e : entr) {
			row=row+1;
			int rekkaUI = Integer.parseInt(tfEmail.getText());
			int rekka = e.getRekkan();
			if (rekka == rekkaUI) {

				int n = JOptionPane.showConfirmDialog(null, "ATH Rekka  "
						+ tfEmail.getText()

						+ " VAR skradur!\n " + "Fjarlaegja tad?", null,
						JOptionPane.YES_NO_OPTION);

				if (n == JOptionPane.YES_OPTION) {

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

		// FATCHING FROM ISPAN DB
		if (isDobleEntryNOTPresent()) {

			int rekka = Integer.parseInt(tfEmail.getText());
			int pontun = Integer.parseInt(tfCpf.getText());
			Entrence tempentralldata = fetchispan.createReadNameIspan(pontun,
					rekka);

			// JOptionPane.showMessageDialog(null, "Vidstiptavinur  " +
			// tfName.getText()
			// + " skradur!");
			myTableModel.refresh(tempentralldata);
			
			cleanFields();
		} else {
			cleanFields();
		}

	}

	// }
	// else {

	// JOptionPane
	// .showMessageDialog(null,
	// "Vidskiptavinur EKKI uppfaerdur .Athugadu reitina og reyndu aftur!");
	// }

	/*
	 * public void read() { if (getSelectedId() >= 0) { Usuario user =
	 * usuarioDAO.read(getSelectedId()); cleanFields();
	 * tfName.setText(user.getNome()); tfCpf.setText(user.getCpf());
	 * lMyDate.setText(user.getTelefone()); tfEmail.setText(user.getEmail()); }
	 * }
	 * 
	 * public void update() { if (getSelectedId() >= 0) { Usuario user = new
	 * Usuario(getSelectedId(), tfName.getText(), tfCpf.getText(),
	 * lMyDate.getText(), tfEmail.getText()); usuarioDAO.update(user); //
	 * refreshTable(); cleanFields(); }
	 * 
	 * }
	 */
	/*
	 * public void delete() { DefaultTableModel tableModel = (DefaultTableModel)
	 * tTable.getModel(); int linhaSelecionada = tTable.getSelectedRow(); // try
	 * this if (linhaSelecionada >= 0 && tfCpf.getText().isEmpty() &&
	 * tfName.getText().isEmpty() && tfEmail.getText().isEmpty()) { //
	 * System.out.println("Vidsliptavinur med ID=" + getSelectedId() // +
	 * " fjarlaegdur."); usuarioDAO.delete(getSelectedId());
	 * tableModel.removeRow(linhaSelecionada); //refreshTable(); cleanFields();
	 * 
	 * } // int i =Integer.parseInt(tfEmail.getText()); else if
	 * (tfName.getText().isEmpty() && tfCpf.getText().isEmpty() !=
	 * (tfEmail.getText().isEmpty())) {
	 * System.out.println("Vidsliptavinur med Rekka N =" + tfEmail.getText() +
	 * " fjarlaegdur."); // JOptionPane.showMessageDialog(null, //
	 * "Vidsliptavinur med Rekka N =" + tfEmail.getText() // + " fjarlaegdur.");
	 * usuarioDAO.deleteRekka(tfEmail.getText());
	 * 
	 * // refreshTable(); cleanFields();
	 * 
	 * }
	 * 
	 * // else if (linhaSelecionada >= 0 ){ //
	 * usuarioDAO.delete(getSelectedId()); //
	 * tableModel.removeRow(linhaSelecionada); // refreshTable(); //
	 * cleanFields();
	 * 
	 * // }
	 * 
	 * }
	 */

	class MyTableModel extends AbstractTableModel {
		FetchGlerskalinn fetchGlerskalinn = new FetchGlerskalinn();
		List<Entrence> entlist = fetchGlerskalinn.readAll();

		String[] orderColNames = { "Nafn", "Pontun", "Dagsetning", "Rekka",
				"Siminn", "Gata", "PostN" };

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return entlist.size();
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 7;
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
			default:
				return "";
			}
		}

		public String getColumnName(int col) {
			return orderColNames[col];
		}

		public boolean isCellEditable(int row, int col) {

			if (col == 1) {
				return true;
			} else {
				return false;
			}
		}

		// Update the model when the use changes the quantity
		public void setValueAt(Object value, int row, int col) {
			String val = value.toString();
			if (col == 1) {
				usuarios.get(row).setNome(val);
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
		public void refreshWhenDelete ( int row){
			entlist.remove(row);
			//fireTableDataChanged();
			fireTableRowsDeleted(row, row);
		}

	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub

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

}
