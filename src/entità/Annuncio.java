package entità;

import entity.enums.TipoAnnuncio;

public class Annuncio {

    private int idAnnuncio; // nel Database è la PK
    private String titolo;
    private String descrizione;
    private TipoAnnuncio tipoAnnuncio;
    private String oggettiDaScambiare;
    private double prezzoOffertaMinima;
    private double prezzoVendita;
    private boolean disponibile;
    private String Username; // username dell'utente. FK in Annuncio verso Utente
    private int idCategoria; // idCategoria della categoria in cui è pubblicato. FK in Annuncio verso categoria

    public Annuncio() {}

    public Annuncio(String titolo, String descrizione, TipoAnnuncio tipoAnnuncio, String oggettiDaScambiare, double prezzoOffertaMinima, double prezzoVendita, boolean disponibile, String username, int idCategoria) 
    {
       
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.tipoAnnuncio = tipoAnnuncio;
        this.oggettiDaScambiare = oggettiDaScambiare;
        this.prezzoOffertaMinima = prezzoOffertaMinima;
        this.prezzoVendita = prezzoVendita;
        this.disponibile = disponibile;
        this.Username = username;
        this.idCategoria = idCategoria;
    }

    public int getIdAnnuncio() {
        return idAnnuncio;
    }

    public void setIdAnnuncio(int idAnnuncio) {
        this.idAnnuncio = idAnnuncio;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public TipoAnnuncio getTipoAnnuncio() {
        return tipoAnnuncio;
    }

    public void setTipoAnnuncio(TipoAnnuncio tipoAnnuncio) {
        this.tipoAnnuncio = tipoAnnuncio;
    }

    public String getOggettiDaScambiare() {
        return oggettiDaScambiare;
    }

    public void setOggettiDaScambiare(String oggettiDaScambiare) {
        this.oggettiDaScambiare = oggettiDaScambiare;
    }

    public double getPrezzoOffertaMinima() {
        return prezzoOffertaMinima;
    }

    public void setPrezzoOffertaMinima(double prezzoOffertaMinima) {
        this.prezzoOffertaMinima = prezzoOffertaMinima;
    }

    public double getPrezzoVendita() {
        return prezzoVendita;
    }

    public void setPrezzoVendita(double prezzoVendita) {
        this.prezzoVendita = prezzoVendita;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return getTitolo() + " (ID: " + getIdAnnuncio() + ")";
    }

    }

