package com.crud.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.crud.dao.banco.ConnectionFactory;
import com.crud.model.Entrence;

public class FetchGlerskalinn {
	//private final String URL = "jdbc:mysql://localhost:3306/CRUD";
//	private final String URL = "jdbc:mysql://192.168.81.205:3306/crud2";
	//private final String NAME = "javauser";
  //  private final String PASS = "123";
//	private final String PASS = "1234";
	private final String URL = "jdbc:mysql://localhost:3306/homedb";
	//private final String URL = "jdbc:mysql://192.168.81.205:3306/crud2";
	//private final String NAME = "javauser";
	private final String NAME = "root";
	//private final String PASS = "123";
	//private final String PASS = "1234";
	private final String PASS = "";

	private Connection con;
	private Statement comand;


	public void conectar() {
		try {
		/*	con = ConnectionFactory.conexao(URL, NAME, PASS,
					ConnectionFactory.MYSQL);*/
			con = ConnectionFactory.conexao(URL, NAME, PASS,
					ConnectionFactory.MYSQL);
			comand = con.createStatement();
			System.out.println("Connected");
		} catch (ClassNotFoundException e) {
			System.out.println("Error loading the  driver: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Error connecting : " + e.getMessage());
		}
	}

	public void fechar() {
		try {
			comand.close();
			con.close();
			System.out.println("Disconectet");
		} catch (SQLException e) {
			System.err.println("Error connecting : " + e.getMessage());
		}
	}

	public void create(Entrence entrfromispan) {
		conectar();
		try {
			comand.execute("INSERT INTO JavaCRUD2(nafn,pontun,dagsetning,rekka ,siminn, gata, postn) VALUES('"
					+ entrfromispan.getNafn()
					+ "', '"
					+ entrfromispan.getPontun()
					+ "', '"
					+ entrfromispan.getDagsetning()
					+ "', '"
					+ entrfromispan.getRekkan()
					+ "', '"
					+ entrfromispan.getSiminn()
					+ "', '"
					+ entrfromispan.getGata()
					+ "', '"
					+ entrfromispan.getPostn() + "')");
		} catch (SQLException e) {
			System.err.println("Error entring user : " + e.getMessage());
		}
		catch ( Exception e){
			System.out.println("No such entry in db ");
		}
				
				finally {
			fechar();
		}
	}

	public List<Entrence> readAll() {
		conectar();
		List<Entrence> list = new ArrayList<Entrence>();
		ResultSet rs;
		Entrence temp = null;
		try {
			rs = comand.executeQuery("SELECT * FROM JavaCRUD2");
			while (rs.next()) {
				temp = new Entrence(rs.getString("nafn"), rs.getInt("pontun"),
						rs.getString("dagsetning"), rs.getInt("rekka"),
						rs.getString("siminn"), rs.getString("gata"),
						rs.getString("postn"));
				list.add(temp);
			}
			return list;
		} catch (SQLException e) {
			System.err.println("Error selecting entry: " + e.getMessage());
			return null;
		} finally {
			fechar();
		}
	}

	public void delete(int rekka) {
		conectar();
	

		try {
			comand.execute("DELETE FROM javacrud2 WHERE rekka = '" + rekka + "';");
		} catch (SQLException e) {
			System.err.println("Error deleting entry: " + e.getMessage());
			
		} finally {
			fechar();
		}
	

	}
	public void update (String date, int rekkaToSQL){
		conectar();
		try {
			comand.executeUpdate("UPDATE JavaCRUD2 SET dagsetning = '"
					+ date + "'WHERE  rekka = " + rekkaToSQL + ";");
		} catch (SQLException e) {
			System.err.println("Error updating entry : " + e.getMessage());
		} finally {
			fechar();
		}
		
		
		
	}
	public void createMSG(Entrence entrfromispan ,int rekka) {
		conectar();
		try {
			comand.execute("UPDATE JavaCRUD2  SET  ath ="
					+ entrfromispan.getNafn()+ "WHERE  rekka = "+ rekka ";");
		} catch (SQLException e) {
			System.err.println("Error entring user : " + e.getMessage());
		}
		catch ( Exception e){
			System.out.println("No such entry in db ");
		}
				
				finally {
			fechar();
		}
	}

	
}
