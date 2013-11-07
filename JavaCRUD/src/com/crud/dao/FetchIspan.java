package com.crud.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.crud.dao.banco.ConnectionFactory;
import com.crud.model.Entrence;

public class FetchIspan {

	// refactor for MS Sql
	private final String URL = "jdbc:sqlserver://ispansql:1433;databaseName=PPM_ISPAN";
	private final String NAME = "sergey";
	private final String PASS = "sql.sergey";
	/*
	 * private final String URL = "jdbc:mysql://localhost:3306/homedb"; private
	 * final String NAME = "root"; // private final String PASS = "123"; private
	 * final String PASS = "";
	 */

	private Connection con;
	private Statement comando;

	public void connect() {
		try {
			con = ConnectionFactory.conexao(URL, NAME, PASS,
					ConnectionFactory.MYSQL);
			comando = con.createStatement();
			System.out.println("Connected");
		} catch (ClassNotFoundException e) {
			System.out.println("Error loading the  driver: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Error connecting : " + e.getMessage());
		}
	}

	public void fechar() {
		try {
			comando.close();
			con.close();
			System.out.println("Disconectet Ispan SQL");
		} catch (SQLException e) {
			System.err.println("Error connecting : " + e.getMessage());
		}
	}

	// for numeric values SELECT * FROM Persons WHERE Year=1965

	public Entrence createReadNameIspan(int pontunN, int rekkan) {
		connect();
		ResultSet rs;
		Entrence temp = null;
        Entrence tempNullPontun= null;
		Date date = new Date();

		Format formatter = new SimpleDateFormat("dd.MM.yyyy");
		String s = formatter.format(date);

		// if pontunn.length < 6 show message
		try {
			// remember to use tabordre not taborder this if for a local testing
			// rs =
			// comando.executeQuery("SELECT Navn,Gateadresse,Poststed,Telefon  FROM taborder  WHERE Ordrenummer = "+
			// pontunN + ";" );

			// changes for selecting correct address of a delivery to the
			// customer
			int length = String.valueOf(pontunN).length();
			if (length == 6) {

				rs = comando
						.executeQuery("SELECT Navn,Leveranseadresse,LeveransePostnummer,TelefonLevering  FROM dbo.tabOrdre  WHERE Ordrenummer = "
								+ pontunN + ";");
				while (rs.next()) {
					temp = new Entrence(rs.getString("Navn"), pontunN, s,
							rekkan, rs.getString("TelefonLevering"),
							rs.getString("Leveranseadresse"),
							rs.getString("LeveransePostnummer"));
				}
				FetchGlerskalinn fg = new FetchGlerskalinn();
				fg.create(temp);

				return temp;
			}

		} catch (SQLException e) {
			System.err.println("Error in searching entry: " + e.getMessage());
		} finally {
			fechar();
		}
		// return msg when order is not existed
		//return null;
		tempNullPontun = new Entrence("Not Found in DB", 01, s, rekkan, "5555555", "gata", "postn");
		FetchGlerskalinn f = new FetchGlerskalinn();
		f.create(tempNullPontun);
		
		return tempNullPontun = new Entrence("Not Found in DB", 01, s, rekkan, "5555555", "gata", "postn");
		
		
	}

}
