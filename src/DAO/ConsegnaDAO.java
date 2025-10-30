package DAO;

import entità.Consegna;
import entity.enums.TipoConsegna;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsegnaDAO 
{
	
	public int insertIntoDB(Consegna c) throws SQLException {
        String sql = "INSERT INTO Consegna (Destinatario, NoteConsegna, TipoConsegna, SedeUniversitaria, Data, " +
                     "Ora_Inizio_Pref, Ora_Fine_Pref, Indirizzo, Civico, Corriere, TrackingNumber, ID_Annuncio) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING ID_Consegna";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) 
        {

            ps.setString(1, c.getDestinatario());

            if (c.getNoteConsegna() == null || c.getNoteConsegna().isEmpty())
                ps.setNull(2, Types.VARCHAR);
            else
                ps.setString(2, c.getNoteConsegna());
 
            ps.setString(3, c.getTipoConsegna().toString());

            if (c.getSedeUniversitaria() == null || c.getSedeUniversitaria().isEmpty())
                ps.setNull(4, Types.VARCHAR);
            else
                ps.setString(4, c.getSedeUniversitaria());

            ps.setDate(5, java.sql.Date.valueOf(c.getData()));
            ps.setTime(6, java.sql.Time.valueOf(c.getOraInizioPref()));
            ps.setTime(7, java.sql.Time.valueOf(c.getOraFinePref()));

            if (c.getIndirizzo() == null || c.getIndirizzo().isEmpty())
                ps.setNull(8, Types.VARCHAR);
            else
                ps.setString(8, c.getIndirizzo());

            if (c.getCivico() == null || c.getCivico().isEmpty())
                ps.setNull(9, Types.VARCHAR);
            else
                ps.setString(9, c.getCivico());

            if (c.getCorriere() == null || c.getCorriere().isEmpty())
                ps.setNull(10, Types.VARCHAR);
            else
                ps.setString(10, c.getCorriere());

            if (c.getTrackingNumber() == null || c.getTrackingNumber().isEmpty())
                ps.setNull(11, Types.VARCHAR);
            else
                ps.setString(11, c.getTrackingNumber());

            ps.setInt(12, c.getIdAnnuncio());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) 
            {
                int newID = rs.getInt("ID_Consegna");
                c.setIdConsegna(newID);
                return newID;
            }
        }
        return -1;
    }

	
	 public Consegna findById(int id) throws SQLException 
	 {
	        String sql = "SELECT * FROM Consegna WHERE ID_Consegna = ?";

	        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) 
	        {

	            ps.setInt(1, id);
	            
	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) 
	            {
	                Consegna c = new Consegna();
	                c.setIdConsegna(rs.getInt("ID_Consegna"));
	                c.setDestinatario(rs.getString("Destinatario"));
	                c.setNoteConsegna(rs.getString("NoteConsegna"));
	                c.setTipoConsegna(TipoConsegna.valueOf(rs.getString("TipoConsegna").replace(" ", "_")));
	                c.setSedeUniversitaria(rs.getString("SedeUniversitaria"));
	                c.setData(rs.getDate("Data").toLocalDate());
	                c.setOraInizioPref(rs.getTime("Ora_Inizio_Pref").toLocalTime());
	                c.setOraFinePref(rs.getTime("Ora_Fine_Pref").toLocalTime());
	                c.setIndirizzo(rs.getString("Indirizzo"));
	                c.setCivico(rs.getString("Civico"));
	                c.setCorriere(rs.getString("Corriere"));
	                c.setTrackingNumber(rs.getString("TrackingNumber"));
	                c.setIdAnnuncio(rs.getInt("ID_Annuncio"));
	                return c;
	            }
	        }
	        return null;
	 }
	
	 
	 public List<Consegna> findAll() throws SQLException 
	 {
		 
	        String sql = "SELECT * FROM Consegna ORDER BY ID_Consegna";
	        List<Consegna> risultato = new ArrayList<>();

	        try (Connection conn = DBConnection.getConnection();
	             Statement st = conn.createStatement();
	             ResultSet rs = st.executeQuery(sql)) {

	            while (rs.next()) 
	            {
	                Consegna c = new Consegna();
	                c.setIdConsegna(rs.getInt("ID_Consegna"));
	                c.setDestinatario(rs.getString("Destinatario"));
	                c.setNoteConsegna(rs.getString("NoteConsegna"));
	                c.setTipoConsegna(TipoConsegna.valueOf(rs.getString("TipoConsegna").replace(" ", "_")));
	                c.setSedeUniversitaria(rs.getString("SedeUniversitaria"));
	                c.setData(rs.getDate("Data").toLocalDate());
	                c.setOraInizioPref(rs.getTime("Ora_Inizio_Pref").toLocalTime());
	                c.setOraFinePref(rs.getTime("Ora_Fine_Pref").toLocalTime());
	                c.setIndirizzo(rs.getString("Indirizzo"));
	                c.setCivico(rs.getString("Civico"));
	                c.setCorriere(rs.getString("Corriere"));
	                c.setTrackingNumber(rs.getString("TrackingNumber"));
	                c.setIdAnnuncio(rs.getInt("ID_Annuncio"));
	                risultato.add(c);
	            }
	        }
	        return risultato;
	    }

	    // GET per Annuncio
	    public List<Consegna> findByAnnuncio(int idAnnuncio) throws SQLException 
	    {
	        String sql = "SELECT * FROM Consegna WHERE ID_Annuncio = ?";
	        List<Consegna> risultato = new ArrayList<>();

	        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) 
	        {

	            ps.setInt(1, idAnnuncio);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) 
	            {
	                Consegna c = new Consegna();
	                c.setIdConsegna(rs.getInt("ID_Consegna"));
	                c.setDestinatario(rs.getString("Destinatario"));
	                c.setNoteConsegna(rs.getString("NoteConsegna"));
	                c.setTipoConsegna(TipoConsegna.valueOf(rs.getString("TipoConsegna").replace(" ", "_")));
	                c.setSedeUniversitaria(rs.getString("SedeUniversitaria"));
	                c.setData(rs.getDate("Data").toLocalDate());
	                c.setOraInizioPref(rs.getTime("Ora_Inizio_Pref").toLocalTime());
	                c.setOraFinePref(rs.getTime("Ora_Fine_Pref").toLocalTime());
	                c.setIndirizzo(rs.getString("Indirizzo"));
	                c.setCivico(rs.getString("Civico"));
	                c.setCorriere(rs.getString("Corriere"));
	                c.setTrackingNumber(rs.getString("TrackingNumber"));
	                c.setIdAnnuncio(rs.getInt("ID_Annuncio"));
	                risultato.add(c);
	            }
	        }
	        return risultato;
	    }

	    // UPDATE
	    public boolean update(Consegna c) throws SQLException 
	    {
	        String sql = "UPDATE Consegna SET Destinatario=?, NoteConsegna=?, TipoConsegna=?, SedeUniversitaria=?, Data=?, " +
	                     "Ora_Inizio_Pref=?, Ora_Fine_Pref=?, Indirizzo=?, Civico=?, Corriere=?, TrackingNumber=?, ID_Annuncio=? " +
	                     "WHERE ID_Consegna=?";

	        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) 
	        {

	            ps.setString(1, c.getDestinatario());

	            if (c.getNoteConsegna() == null || c.getNoteConsegna().isEmpty())
	                ps.setNull(2, Types.VARCHAR);
	            else
	                ps.setString(2, c.getNoteConsegna());

	            ps.setString(3, c.getTipoConsegna().toString());

	            if (c.getSedeUniversitaria() == null || c.getSedeUniversitaria().isEmpty())
	                ps.setNull(4, Types.VARCHAR);
	            else
	                ps.setString(4, c.getSedeUniversitaria());

	            ps.setDate(5, java.sql.Date.valueOf(c.getData()));
	            ps.setTime(6, java.sql.Time.valueOf(c.getOraInizioPref()));
	            ps.setTime(7, java.sql.Time.valueOf(c.getOraFinePref()));

	            if (c.getIndirizzo() == null || c.getIndirizzo().isEmpty())
	                ps.setNull(8, Types.VARCHAR);
	            else
	                ps.setString(8, c.getIndirizzo());

	            if (c.getCivico() == null || c.getCivico().isEmpty())
	                ps.setNull(9, Types.VARCHAR);
	            else
	                ps.setString(9, c.getCivico());

	            if (c.getCorriere() == null || c.getCorriere().isEmpty())
	                ps.setNull(10, Types.VARCHAR);
	            else
	                ps.setString(10, c.getCorriere());

	            if (c.getTrackingNumber() == null || c.getTrackingNumber().isEmpty())
	                ps.setNull(11, Types.VARCHAR);
	            else
	                ps.setString(11, c.getTrackingNumber());

	            ps.setInt(12, c.getIdAnnuncio());
	            ps.setInt(13, c.getIdConsegna());

	            return ps.executeUpdate() > 0;
	        }
	    }

	    // DELETE
	    public boolean delete(int idConsegna) throws SQLException 
	    {
	        String sql = "DELETE FROM Consegna WHERE ID_Consegna=?";

	        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) 
	        {
	            ps.setInt(1, idConsegna);
	            return ps.executeUpdate() > 0;
	        }
	    }
	}

