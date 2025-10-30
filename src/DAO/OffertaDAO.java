package DAO;

import entità.Offerta;
import util.DBConnection;
import entity.enums.*; 

import java.util.*;
import java.sql.*;


public class OffertaDAO 
{
		
	public int insertIntoDB(Offerta offerta) throws SQLException
	{
		String sql = "INSERT INTO Offerta (DataOff, StatoOfferta, TipoOfferta, PrezzoProposto, Oggetti, Messaggio, Username, ID_Annuncio, NotePerLaConsegna) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING ID_Offerta";
		
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			
			ps.setDate(1, java.sql.Date.valueOf(offerta.getDataOff()));
			ps.setString(2, offerta.getStatoOfferta().name().replace("_", " "));
			ps.setString(3, offerta.getTipoOfferta().name()); //sempre
 			
 			if(offerta.getPrezzoProposto() <= 0)
 			{
 				ps.setNull(4, Types.NUMERIC);
 			}
 			else ps.setDouble(4, offerta.getPrezzoProposto()); //solo se > 0

 			
 			if(offerta.getOggetti() == null || offerta.getOggetti().isEmpty())
 			{
 				ps.setNull(5, Types.VARCHAR);
 			}
 			else ps.setString(5, offerta.getOggetti()); //solo se non vuoto 
 			
 			if(offerta.getMessaggio() == null || offerta.getMessaggio().isEmpty())
 			{
 				ps.setNull(6, Types.VARCHAR);
 			}
 			else ps.setString(6, offerta.getMessaggio()); //solo se non vuoto

 			
 			ps.setString(7, offerta.getUsername()); //sempre 
			ps.setInt(8, offerta.getIdAnnuncio()); //sempre
			ps.setString(9, offerta.getComeConsegnare());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				int newID = rs.getInt("ID_Offerta");
				offerta.setIdOfferta(newID);
				return newID;
			}
			
			return -1;
		}
	}
	
	public Offerta getByID(int id) throws SQLException
	{
		String sql = "SELECT * FROM Offerta WHERE ID_Offerta = ?";
		
		try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			
			if(rs.next())
			{
				Offerta offerta = new Offerta(); 
				
				offerta.setUsername(rs.getString("Username"));
				StatoOfferta stato; 
				stato = StatoOfferta.fromString(rs.getString("StatoOfferta"));
				offerta.setStatoOfferta(stato);
				
				TipoAnnuncio tipoOfferta; 
				tipoOfferta = TipoAnnuncio.fromString(rs.getString("TipoOfferta"));
				offerta.setTipoOfferta(tipoOfferta);
				
				offerta.setPrezzoProposto(rs.getDouble("PrezzoProposto"));
				offerta.setOggetti(rs.getString("Oggetti"));
				offerta.setMessaggio(rs.getString("Messaggio"));
				offerta.setIdOfferta(rs.getInt("ID_Offerta"));
				offerta.setIdAnnuncio(rs.getInt("ID_Annuncio"));
				offerta.setComeConsegnare(rs.getString("NotePerLaConsegna"));
				offerta.setDataOff(rs.getDate("DataOff").toLocalDate());
				
				return offerta;
			}
			
			return null;
		}
		
	}
	
	public List<Offerta> getAllByAnnuncio(int idAnnuncio) throws SQLException
	{
		String sql = "SELECT * FROM Offerta WHERE ID_Annuncio = ?";
		List<Offerta> risultato = new ArrayList<Offerta>();
		
		try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setInt(1, idAnnuncio);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				Offerta offerta = new Offerta();
				
				offerta.setUsername(rs.getString("Username"));
				StatoOfferta stato; 
				stato = StatoOfferta.fromString(rs.getString("StatoOfferta"));
				offerta.setStatoOfferta(stato);
				
				TipoAnnuncio tipoOfferta; 
				tipoOfferta = TipoAnnuncio.fromString(rs.getString("TipoOfferta"));
				offerta.setTipoOfferta(tipoOfferta);
				
				offerta.setPrezzoProposto(rs.getDouble("PrezzoProposto"));
				offerta.setOggetti(rs.getString("Oggetti"));
				offerta.setMessaggio(rs.getString("Messaggio"));
				offerta.setIdOfferta(rs.getInt("ID_Offerta"));
				offerta.setIdAnnuncio(rs.getInt("ID_Annuncio"));
				offerta.setDataOff(rs.getDate("DataOff").toLocalDate());
				offerta.setComeConsegnare(rs.getString("NotePerLaConsegna"));

				risultato.add(offerta);
			}
			
			return risultato;
		
		}
	}
	
	public List<Offerta> getAllByUsername(String username) throws SQLException
	{
		String sql = "SELECT * FROM Offerta WHERE Username = ?";
		List<Offerta> risultato = new ArrayList<Offerta>();
		
		try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				Offerta offerta = new Offerta();
				
				offerta.setUsername(rs.getString("Username"));
				StatoOfferta stato; 
				stato = StatoOfferta.fromString(rs.getString("StatoOfferta"));
				offerta.setStatoOfferta(stato);
				
				TipoAnnuncio tipoOfferta; 
				tipoOfferta = TipoAnnuncio.fromString(rs.getString("TipoOfferta"));
				offerta.setTipoOfferta(tipoOfferta);
				
				offerta.setPrezzoProposto(rs.getDouble("PrezzoProposto"));
				offerta.setOggetti(rs.getString("Oggetti"));
				offerta.setMessaggio(rs.getString("Messaggio"));
				offerta.setIdOfferta(rs.getInt("ID_Offerta"));
				offerta.setIdAnnuncio(rs.getInt("ID_Annuncio"));
				offerta.setDataOff(rs.getDate("DataOff").toLocalDate());
				offerta.setComeConsegnare(rs.getString("NotePerLaConsegna"));

				risultato.add(offerta);
			}
			
			return risultato;
		}
	}
	
	public boolean updateStato(int idOfferta, StatoOfferta newState) throws SQLException
	{
		String sql = "UPDATE Offerta SET StatoOfferta = ? WHERE ID_Offerta = ?";
		
		try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setString(1, newState.toString());
			ps.setInt(2, idOfferta);
			
			int righeModificate = ps.executeUpdate();
			return righeModificate > 0; 
		}
	}
	
	
	public boolean delete(int idOfferta) throws SQLException {
	    String sql = "DELETE FROM Offerta WHERE ID_Offerta = ?";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, idOfferta);

	        int righeEliminate = ps.executeUpdate();
	        return righeEliminate > 0;
	    }
	}

    public boolean updateOfferta(Offerta offerta) throws SQLException {
        String sql = "UPDATE Offerta SET PrezzoProposto = ?, Oggetti = ?, Messaggio = ? WHERE ID_Offerta = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // PrezzoProposto
            if (offerta.getPrezzoProposto() <= 0) {
                ps.setNull(1, Types.NUMERIC);
            } else {
                ps.setDouble(1, offerta.getPrezzoProposto());
            }

            // Oggetti
            if (offerta.getOggetti() == null || offerta.getOggetti().isEmpty()) {
                ps.setNull(2, Types.VARCHAR);
            } else {
                ps.setString(2, offerta.getOggetti());
            }

            // Messaggio
            if (offerta.getMessaggio() == null || offerta.getMessaggio().isEmpty()) {
                ps.setNull(3, Types.VARCHAR);
            } else {
                ps.setString(3, offerta.getMessaggio());
            }

            ps.setInt(4, offerta.getIdOfferta());

            int righeModificate = ps.executeUpdate();
            return righeModificate > 0; // TRUE se l'update è andato a buon fine
        }
    }

}
