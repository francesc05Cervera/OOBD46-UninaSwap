package DAO;

import entità.Categoria;
import util.DBConnection;
import java.sql.*;
import java.util.*; 

public class CategoriaDAO 
{
	public int insertIntoDB (Categoria categoria) throws SQLException
	{
		String sql = "INSERT INTO Categoria (NomeCategoria) VALUES (?) RETURNING ID_Categoria";
		
		try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setString(1, categoria.getNomeCategoria());
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				int id = rs.getInt("ID_Categoria"); 
				categoria.setIdCategoria(id);
				return id; 
			}
		}
		
		return -1; 
	}
	
	public Categoria findbyNome(String nomeCategoria) throws SQLException
	{
		String sql = "SELECT * FROM Categoria WHERE NomeCategoria = ?"; 
		
		try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setString(1, nomeCategoria);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				Categoria categoriaTrovata = new Categoria();
				categoriaTrovata.setIdCategoria(rs.getInt("ID_Categoria"));
				categoriaTrovata.setNomeCategoria(rs.getString("NomeCategoria"));
				
				return categoriaTrovata;
			}
		}
		
		return null; 
	}
	
	public Categoria findByID(int id) throws SQLException
	{
		String sql = "SELECT * FROM Categoria WHERE ID_Categoria = ?"; 
		
		try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				Categoria categoriaTrovata = new Categoria();
				categoriaTrovata.setIdCategoria(rs.getInt("ID_Categoria"));
				categoriaTrovata.setNomeCategoria(rs.getString("NomeCategoria"));
				
				return categoriaTrovata;
			}
		}
		
		return null;
		
	}
	public String findByIDAndRetName(int id) throws SQLException
	{
		String sql = "SELECT * FROM Categoria WHERE ID_Categoria = ?"; 
		
		try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				Categoria categoriaTrovata = new Categoria();
				categoriaTrovata.setIdCategoria(rs.getInt("ID_Categoria"));
				categoriaTrovata.setNomeCategoria(rs.getString("NomeCategoria"));
				
				return categoriaTrovata.getNomeCategoria();
			}
		}
		
		return null;
		
	}
	public int findbyNomeAndRetId(String nomeCategoria) throws SQLException
	{
		String sql = "SELECT * FROM Categoria WHERE NomeCategoria = ?"; 
		
		try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setString(1, nomeCategoria);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
			Categoria categoriaTrovata = new Categoria();
				categoriaTrovata.setIdCategoria(rs.getInt("ID_Categoria"));
				categoriaTrovata.setNomeCategoria(rs.getString("NomeCategoria"));
				
				return categoriaTrovata.getIdCategoria();
			}
		}
		
		return -1; 
	}
	
	public List<Categoria> findAll() throws SQLException 
	{
		List<Categoria> risultato = new ArrayList<Categoria>();
		String sql = "SELECT * FROM Categoria ORDER BY ID_Categoria";
		
		try(Connection conn = DBConnection.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql))
		{
			while(rs.next())
			{
				Categoria categoria = new Categoria(); 
				categoria.setIdCategoria(rs.getInt("ID_Categoria"));
				categoria.setNomeCategoria(rs.getString("NomeCategoria"));
				risultato.add(categoria);
			}
		}
		
		return risultato; 
	}
		
	public boolean deleteFromDB(String nomeCategoria) throws SQLException
	{
		String sql = "DELETE FROM Categoria WHERE NomeCategoria = ?"; 
		
		try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setString(1, nomeCategoria);
			
			if(ps.executeUpdate() > 0)
				return true; 
		}
		
		return false; 
	}
	
	public int countAnnunciByCategoria(String nomeCategoria) throws SQLException {
	    String sql = "SELECT COUNT(*) AS totale FROM Annuncio WHERE ID_Categoria = ?";

	    try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) 
	    {

	        ps.setInt(1, findbyNomeAndRetId(nomeCategoria));

	        ResultSet rs = ps.executeQuery();
	        		
	            if (rs.next()) 
	            {
	                return rs.getInt("totale");
	            }
	     }
	    
	    return 0; // Nessun annuncio trovato o categoria inesistente
	}

}
