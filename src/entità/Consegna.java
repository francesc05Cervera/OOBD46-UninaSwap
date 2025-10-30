package entità;

import java.time.LocalDate;
import java.time.LocalTime;
import entity.enums.TipoConsegna;

public class Consegna {
    private int idConsegna;
    private String destinatario;
    private String noteConsegna;
    private TipoConsegna tipoConsegna;
    private String sedeUniversitaria;
    private LocalDate data;
    private LocalTime oraInizioPref;
    private LocalTime oraFinePref;
    private String indirizzo;
    private String civico;
    private String corriere;
    private String trackingNumber;
    private int idAnnuncio;

    public Consegna() {}

    public Consegna(int idConsegna, String destinatario, String noteConsegna, TipoConsegna tipoConsegna,
                    String sedeUniversitaria, LocalDate data, LocalTime oraInizioPref, LocalTime oraFinePref,
                    String indirizzo, String civico, String corriere, String trackingNumber, int idAnnuncio) {
        this.idConsegna = idConsegna;
        this.destinatario = destinatario; 
        this.noteConsegna = noteConsegna;
        this.tipoConsegna = tipoConsegna;
        this.sedeUniversitaria = sedeUniversitaria;
        this.data = data;
        this.oraInizioPref = oraInizioPref;
        this.oraFinePref = oraFinePref;
        this.indirizzo = indirizzo;
        this.civico = civico;
        this.corriere = corriere;
        this.trackingNumber = trackingNumber;
        this.idAnnuncio = idAnnuncio;
    }

	public int getIdConsegna() {
		return idConsegna;
	}

	public void setIdConsegna(int idConsegna) {
		this.idConsegna = idConsegna;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getNoteConsegna() {
		return noteConsegna;
	}

	public void setNoteConsegna(String noteConsegna) {
		this.noteConsegna = noteConsegna;
	}

	public TipoConsegna getTipoConsegna() {
		return tipoConsegna;
	}

	public void setTipoConsegna(TipoConsegna tipoConsegna) {
		this.tipoConsegna = tipoConsegna;
	}

	public String getSedeUniversitaria() {
		return sedeUniversitaria;
	}

	public void setSedeUniversitaria(String sedeUniversitaria) {
		this.sedeUniversitaria = sedeUniversitaria;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getOraInizioPref() {
		return oraInizioPref;
	}

	public void setOraInizioPref(LocalTime oraInizioPref) {
		this.oraInizioPref = oraInizioPref;
	}

	public LocalTime getOraFinePref() {
		return oraFinePref;
	}

	public void setOraFinePref(LocalTime oraFinePref) {
		this.oraFinePref = oraFinePref;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCorriere() {
		return corriere;
	}

	public void setCorriere(String corriere) {
		this.corriere = corriere;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public int getIdAnnuncio() {
		return idAnnuncio;
	}

	public void setIdAnnuncio(int idAnnuncio) {
		this.idAnnuncio = idAnnuncio;
	}

	@Override
	public String toString() {
	    return "Consegna{" +
	            "idConsegna=" + idConsegna +
	            ", destinatario='" + destinatario + '\'' +
	            ", tipoConsegna=" + tipoConsegna +
	            ", sedeUniversitaria='" + sedeUniversitaria + '\'' +
	            ", data=" + data +
	            ", oraInizioPref=" + oraInizioPref +
	            ", oraFinePref=" + oraFinePref +
	            ", indirizzo='" + indirizzo + '\'' +
	            ", civico='" + civico + '\'' +
	            ", corriere='" + corriere + '\'' +
	            ", trackingNumber='" + trackingNumber + '\'' +
	            ", idAnnuncio=" + idAnnuncio +
	            '}';
	}

    
    
}

