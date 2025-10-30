package DAO;

import entità.Utente;
import util.DBConnection;
import java.sql.*;
import java.util.*;

public class UtenteDAO 
{
	
	public boolean InsertInDB(Utente utente) throws SQLException
	{
		String sql = "INSERT INTO Utente (Username, Email, Nome, Cognome, PasswordUtente) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setString(1, utente.getUsername());
			ps.setString(2, utente.getEmail());
			ps.setString(3, utente.getNome());
			ps.setString(4, utente.getCognome());
			ps.setString(5, utente.getPassword());
			
			if (ps.executeUpdate() > 0)
				return true;
		}
		
		return false;
	}
 
	public Utente findByMail(String email) throws SQLException
	{
		String sql = "SELECT * FROM Utente WHERE email = ?"; 
		
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				Utente u = new Utente(); 
				u.setUsername(rs.getString("Username"));
				u.setEmail(rs.getString("Email"));
				u.setNome(rs.getString("Nome"));
				u.setCognome(rs.getString("Cognome"));
				u.setPassword(rs.getString("PasswordUtente"));
				return u;
			}
			return null; 
		}
		
	}
	
	public Utente findByUser(String username) throws SQLException
	{
		String sql = "SELECT * FROM Utente WHERE Username = ?";
		
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				Utente u = new Utente();
				 
				u.setUsername(rs.getString("Username"));
				u.setEmail(rs.getString("Email"));
				u.setNome(rs.getString("Nome"));
				u.setCognome(rs.getString("Cognome"));
				u.setPassword(rs.getString("PasswordUtente"));
				
				return u;
			}
		}
		
		return null;
	}
	
	public List<Utente> findAll() throws SQLException
	{
		List<Utente> risultato = new ArrayList<Utente>();
		String sql = "SELECT * FROM Utente ORDER BY Username"; 
		
		try(Connection conn = DBConnection.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql))
		{
			while(rs.next())
			{
				Utente u = new Utente (); 
				u.setUsername(rs.getString("Username"));
				u.setUsername(rs.getString("Email"));
				u.setNome(rs.getString("Nome"));
				u.setCognome(rs.getString("Cognome"));
				u.setPassword(rs.getString("PasswordUtente"));
				
				risultato.add(u);
			}
		}
		
		return risultato;
	}
	
	public boolean delete(String username) throws SQLException
	{
		String sql = "DELETE FROM Utente WHERE Username = ?"; 
		try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setString(1, username);
			
			if(ps.executeUpdate() > 0)
				return true;
		}
		
		return false;
	}
}
