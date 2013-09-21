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
	private final String URL = "jdbc:sqlserver://ispansql:1433;databaseName=PPM_GLERSKALINN";
	private final String NOME = "sergey";
	private final String PAS = "sql.sergey";

	private Connection con;
	private Statement comando;

	public void connect() {
		try {
			con = ConnectionFactory.conexao(URL, NOME, PAS,
					ConnectionFactory.MSSQL);
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
			System.out.println("Disconectet");
		} catch (SQLException e) {
			System.err.println("Error connecting : " + e.getMessage());
		}
	}

	// for numeric values SELECT * FROM Persons WHERE Year=1965

	public Entrence createReadNameIspan(int pontunN ,int rekkan) {
		connect();
		ResultSet rs;
		Entrence temp = null;
	
		Date date = new Date();

		Format formatter = new SimpleDateFormat("dd.MM.yyyy");
		String s = formatter.format(date);

		
		// if pontunn.length < 6 show message
		try {
			rs = comando.executeQuery("SELECT Navn,Gateadresse,Poststed,Telefon  FROM dbo.tabOrdre  WHERE Ordrenummer = "+ pontunN + ";" );
	
			while (rs.next()) {
				temp =  new Entrence(rs.getString("Navn"), pontunN, s, rekkan, rs.getString("Telefon"), rs.getString("Gateadresse"), rs.getString("Poststed"));
			}
			  FetchGlerskalinn fg = new FetchGlerskalinn();
			  fg.create(temp);
			  
			 
			return temp ;

		} catch (SQLException e) {
			System.err.println("Error in searching entry: " + e.getMessage());
		} finally {
			fechar();
		}
		return null;
	}

}
