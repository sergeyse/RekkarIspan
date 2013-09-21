package com.crud.dao.banco;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  

public class ConnectionFactory {
	   public static final int MYSQL = 0;  
	   public  final static int MSSQL = 1;
	   private static final String MySQLDriver = "com.mysql.jdbc.Driver";  
      
	   //Ýעמ טלÿ הנאיגונא הכÿ הנאיגונא Microsoft SQL Server 2000 הכÿ JDBC:
	  private static final String MsSQLDriver ="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	   
	   public static Connection conexao(String url, String nome, String senha,  
	         int banco) throws ClassNotFoundException, SQLException {  
	      switch (banco) {        
	      case MYSQL:           
	         Class.forName(MySQLDriver); 
	         System.out.println("Connecting to MYSQL");
	         break; 
	       case MSSQL:
	        Class.forName(MsSQLDriver);
	        System.out.println("Connecting to MSSQL");
	       break;
	      }  
	      return DriverManager.getConnection(url, nome, senha);  
	   }  
	
}
