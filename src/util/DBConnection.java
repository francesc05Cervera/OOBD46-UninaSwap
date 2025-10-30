package util;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 


public class DBConnection 
{
	
	 static final String URL = "jdbc:postgresql://localhost:5433/unina_swap";
	 static final String USER = "postgres";
	 static final String PASSWORD = "francesco";
	
	
	public static Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
