package entità;

import entity.enums.StatoOfferta;
import entity.enums.TipoAnnuncio;
import java.time.LocalDate;

public class Offerta {
    private int idOfferta;
    private LocalDate dataOff;
    private StatoOfferta statoOfferta;
    private TipoAnnuncio tipoOfferta; //per comodità utilizziamo TipoAnnuncio come tipo di dato visto che tipoOfferta può assumere gli stessi valori
    private double prezzoProposto;
    private String oggetti;
    private String messaggio;
    private String username;
    private int idAnnuncio;
    private String comeConsegnare;

    public Offerta() {}

    public Offerta(LocalDate dataOff, StatoOfferta statoOfferta, TipoAnnuncio tipoOfferta, double prezzoProposto, String oggetti, String messaggio, String username, int idAnnuncio) {
        
        this.dataOff = dataOff;
        this.statoOfferta = statoOfferta;
        this.tipoOfferta = tipoOfferta;
        this.prezzoProposto = prezzoProposto;
        this.oggetti = oggetti;
        this.messaggio = messaggio;
        this.username = username;
        this.idAnnuncio = idAnnuncio;
    }

    public String getComeConsegnare()
    {
    	return comeConsegnare;
    }
    
    public void setComeConsegnare(String c)
    {
    	this.comeConsegnare = c; 
    }
    public int getIdOfferta() {
        return idOfferta;
    }

    public void setIdOfferta(int idOfferta) {
        this.idOfferta = idOfferta;
    }

    public LocalDate getDataOff() {
        return dataOff;
    }

    public void setDataOff(LocalDate dataOff) {
        this.dataOff = dataOff;
    }

    public StatoOfferta getStatoOfferta() {
        return statoOfferta;
    }

    public void setStatoOfferta(StatoOfferta statoOfferta) {
        this.statoOfferta = statoOfferta;
    }

    public TipoAnnuncio getTipoOfferta() {
        return tipoOfferta;
    }

    public void setTipoOfferta(TipoAnnuncio tipoOfferta) {
        this.tipoOfferta = tipoOfferta;
    }

    public double getPrezzoProposto() {
        return prezzoProposto;
    }

    public void setPrezzoProposto(double prezzoProposto) {
        this.prezzoProposto = prezzoProposto;
    }

    public String getOggetti() {
        return oggetti;
    }

    public void setOggetti(String oggetti) {
        this.oggetti = oggetti;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdAnnuncio() {
        return idAnnuncio;
    }

    public void setIdAnnuncio(int idAnnuncio) {
        this.idAnnuncio = idAnnuncio;
    }

    @Override
    public String toString() {
        return "L'offerta numero " + idOfferta + " è stata inviata il " + dataOff + " da " + username + ". " +
               "Lo stato attuale dell'offerta è " + statoOfferta + " e il tipo di annuncio è " + tipoOfferta + ". " +
               "Il prezzo proposto è di euro " + ((int)(prezzoProposto * 100) % 100 == 0 ? 
               ((int) prezzoProposto) + ".00" : prezzoProposto) + ". " +
               "Gli oggetti inclusi nell'offerta sono: " + oggetti + ". " +
               "Il messaggio dell'utente è il seguente: \"" + messaggio + "\". " +
               "Questa offerta è collegata all'annuncio numero " + idAnnuncio + ".";
    }

}
