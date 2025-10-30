package DAO;

import entità.Annuncio;
import entity.enums.TipoAnnuncio;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnuncioDAO {

    public int insertIntoDB(Annuncio a) throws SQLException {
        String sql = "INSERT INTO Annuncio (Titolo, Descrizione, TipoAnnuncio, PrezzoOffertaMinima, PrezzoVendita, " +
                "OggettiDaScambiare, Disponibile, Username, ID_Categoria) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING ID_Annuncio";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getTitolo());
            ps.setString(2, a.getDescrizione());
            ps.setString(3, a.getTipoAnnuncio().toString());

            // PrezzoOffertaMinima e PrezzoVendita  possono essere NULL in caso di scambio o regalo
            if (a.getPrezzoOffertaMinima() != 0)
                ps.setDouble(4, a.getPrezzoOffertaMinima()); //setta il prezzo offerta minima
            else
                ps.setNull(4, Types.NUMERIC); //altrimenti lo mette a NULL e con Types.NUMERIC dice al DB di trattarlo come un NUMERIC
 
            if (a.getPrezzoVendita() != 0)
                ps.setDouble(5, a.getPrezzoVendita()); 
            else
                ps.setNull(5, Types.NUMERIC);

            // OggettiDaScambiare può essere NULL se è un regalo o una vendita
            if (a.getOggettiDaScambiare() != null && !a.getOggettiDaScambiare().isEmpty()) //controlla se la stringa sia vuota o NULL
                ps.setString(6, a.getOggettiDaScambiare()); //se non è vuota la setta
            else
                ps.setNull(6, Types.VARCHAR); //altrimenti la mette a NULL e gli dice di trattarlo come un VARCHAR

            ps.setBoolean(7, a.isDisponibile()); //setta il boolean
            ps.setString(8, a.getUsername()); //setta l'username dell'utente loggato
            ps.setInt(9, a.getIdCategoria()); //setta l'ID della categoria selezionata

            ResultSet rs = ps.executeQuery(); //avvia la query che si trova nella stringa sql
            
            if (rs.next()) 
            {
                int newId = rs.getInt("ID_Annuncio"); //recupera l'output della query, ovvero IDAnnuncio
                a.setIdAnnuncio(newId); //e lo imposta come ID dell'annuncio appena creato
                
                return newId;
            }
        }
        return -1;
    }

    public List<Annuncio> findAll() throws SQLException {
        String sql = "SELECT * FROM Annuncio ORDER BY ID_Annuncio";
        List<Annuncio> lista = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Annuncio a = new Annuncio();
                a.setIdAnnuncio(rs.getInt("ID_Annuncio"));
                a.setTitolo(rs.getString("Titolo"));
                a.setDescrizione(rs.getString("Descrizione"));
                a.setTipoAnnuncio(TipoAnnuncio.valueOf(rs.getString("TipoAnnuncio")));
                a.setPrezzoOffertaMinima(rs.getObject("PrezzoOffertaMinima") != null ? rs.getDouble("PrezzoOffertaMinima") : 0);
                a.setPrezzoVendita(rs.getObject("PrezzoVendita") != null ? rs.getDouble("PrezzoVendita") : 0);
                a.setOggettiDaScambiare(rs.getString("OggettiDaScambiare"));
                a.setDisponibile(rs.getBoolean("Disponibile"));
                a.setUsername(rs.getString("Username"));
                a.setIdCategoria(rs.getInt("ID_Categoria"));

                lista.add(a);
            }
        }
        return lista;
    }

    public Annuncio findByID(int id) throws SQLException {
        String sql = "SELECT * FROM Annuncio WHERE ID_Annuncio = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Annuncio a = new Annuncio();
                a.setIdAnnuncio(rs.getInt("ID_Annuncio"));
                a.setTitolo(rs.getString("Titolo"));
                a.setDescrizione(rs.getString("Descrizione"));
                a.setTipoAnnuncio(TipoAnnuncio.valueOf(rs.getString("TipoAnnuncio")));
                a.setPrezzoOffertaMinima(rs.getObject("PrezzoOffertaMinima") != null ? rs.getDouble("PrezzoOffertaMinima") : 0);
                a.setPrezzoVendita(rs.getObject("PrezzoVendita") != null ? rs.getDouble("PrezzoVendita") : 0);
                a.setOggettiDaScambiare(rs.getString("OggettiDaScambiare"));
                a.setDisponibile(rs.getBoolean("Disponibile"));
                a.setUsername(rs.getString("Username"));
                a.setIdCategoria(rs.getInt("ID_Categoria"));
                return a;
            }
        }
        return null;
    }
    
    public boolean update(Annuncio a) throws SQLException {
        String sql = "UPDATE Annuncio SET Titolo=?, Descrizione=?, TipoAnnuncio=?, PrezzoOffertaMinima=?, PrezzoVendita=?, " +
                "OggettiDaScambiare=?, Disponibile=?, Username=?, ID_Categoria=? WHERE ID_Annuncio=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getTitolo());
            ps.setString(2, a.getDescrizione());
            ps.setString(3, a.getTipoAnnuncio().toString());

            if (a.getPrezzoOffertaMinima() != 0)
                ps.setDouble(4, a.getPrezzoOffertaMinima());
            else
                ps.setNull(4, Types.NUMERIC);

            if (a.getPrezzoVendita() != 0)
                ps.setDouble(5, a.getPrezzoVendita());
            else
                ps.setNull(5, Types.NUMERIC);

            if (a.getOggettiDaScambiare() != null && !a.getOggettiDaScambiare().isEmpty())
                ps.setString(6, a.getOggettiDaScambiare());
            else
                ps.setNull(6, Types.VARCHAR);

            ps.setBoolean(7, a.isDisponibile());
            ps.setString(8, a.getUsername());
            ps.setInt(9, a.getIdCategoria());
            ps.setInt(10, a.getIdAnnuncio());

            return ps.executeUpdate() > 0;
        }
    }

    public List<Annuncio> cercaAnnunciPerUtente(String use) throws SQLException {
        String sql = "SELECT * FROM Annuncio WHERE Username = ?";
        List<Annuncio> result = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, use);
            ResultSet rs = ps.executeQuery(); 
            
            // CAMBIATO: if -> while per recuperare TUTTI gli annunci
            while(rs.next()) {
                Annuncio a = new Annuncio();
                a.setIdAnnuncio(rs.getInt("ID_Annuncio"));
                a.setTitolo(rs.getString("Titolo"));
                a.setDescrizione(rs.getString("Descrizione"));
                a.setTipoAnnuncio(TipoAnnuncio.valueOf(rs.getString("TipoAnnuncio")));
                a.setPrezzoOffertaMinima(rs.getObject("PrezzoOffertaMinima") != null ? rs.getDouble("PrezzoOffertaMinima") : 0);
                a.setPrezzoVendita(rs.getObject("PrezzoVendita") != null ? rs.getDouble("PrezzoVendita") : 0);
                a.setOggettiDaScambiare(rs.getString("OggettiDaScambiare"));
                a.setDisponibile(rs.getBoolean("Disponibile"));
                a.setUsername(rs.getString("Username"));
                a.setIdCategoria(rs.getInt("ID_Categoria"));
                
                result.add(a);
            }
            
            return result;
        }
    }

    
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Annuncio WHERE ID_Annuncio=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
