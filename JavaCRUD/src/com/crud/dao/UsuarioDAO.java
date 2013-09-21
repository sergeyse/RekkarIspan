package com.crud.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crud.dao.banco.ConnectionFactory;
import com.crud.model.Usuario;

public class UsuarioDAO {
	private final String URL = "jdbc:mysql://localhost:3306/CRUD";
	private final String NOME = "root";
	private final String SENHA = "123";

	private Connection con;
	private Statement comando;

	public void conectar() {
		try {
			con = ConnectionFactory.conexao(URL, NOME, SENHA,
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
			System.out.println("Disconectet");
		} catch (SQLException e) {
			System.err.println("Error connecting : " + e.getMessage());
		}
	}

	public void create(Usuario user) {
		conectar();
		try {
			comando.execute("INSERT INTO JavaCRUD(nome,cpf,telefone,email) VALUES('"
					+ user.getNome()
					+ "', '"
					+ user.getCpf()
					+ "', '"
					+ user.getTelefone() + "', '" + user.getEmail() + "')");
		} catch (SQLException e) {
			System.err.println("Error entring user : " + e.getMessage());
		} finally {
			fechar();
		}
	}
	// SELECT Name FROM Table name WHERE PONTUN = tfCpf.getText
	//return String 
	// write connection for jdbc mssql

	
	public Usuario read(int id) {
		conectar();
		ResultSet rs;
		Usuario temp = null;
		try {
			rs = comando.executeQuery("SELECT * FROM JavaCRUD WHERE id LIKE '"
					+ id + "%';");
			while (rs.next()) {
				temp = new Usuario(rs.getInt("id"), rs.getString("nome"),
						rs.getString("cpf"), rs.getString("telefone"),
						rs.getString("email"));
			}
			return temp;

		} catch (SQLException e) {
			System.err.println("Error in searching entry: " + e.getMessage());
		} finally {
			fechar();
		}
		return null;
	}

	public List<Usuario> readAll() {
		conectar();
		List<Usuario> lista = new ArrayList<Usuario>();
		ResultSet rs;
		Usuario temp = null;
		try {
			rs = comando.executeQuery("SELECT * FROM JavaCRUD");
			while (rs.next()) {
				temp = new Usuario(rs.getInt("id"), rs.getString("nome"),
						rs.getString("cpf"), rs.getString("telefone"),
						rs.getString("email"));
				lista.add(temp);
			}
			return lista;
		} catch (SQLException e) {
			System.err.println("Error selecting entry: " + e.getMessage());
			return null;
		} finally {
			fechar();
		}
	}

	public void update(Usuario user) {
		conectar();
		try {
			comando.executeUpdate("UPDATE JavaCRUD SET nome = '"
					+ user.getNome() + "', cpf ='" + user.getCpf()
					+ "', telefone = '" + user.getTelefone() + "', email ='"
					+ user.getEmail() + "' WHERE  id = " + user.getId() + ";");
		} catch (SQLException e) {
			System.err.println("Error updating entry : " + e.getMessage());
		} finally {
			fechar();
		}

	}

	public void delete(int id) {
		conectar();
		try {
			comando.execute("DELETE FROM JavaCRUD WHERE id = '" + id + "';");
		} catch (SQLException e) {
			System.err.println("Error deleting entry: " + e.getMessage());
		} finally {
			fechar();
		}
	}

	
		


	public void deleteRekka(String rekkanumer) {
		conectar();
		try {
			comando.execute("DELETE FROM JavaCRUD WHERE email = '" + rekkanumer + "';");
		} catch (SQLException e) {
			System.err.println("Error deleting entry: " + e.getMessage());
		} finally {
			fechar();
		}
	}

}
