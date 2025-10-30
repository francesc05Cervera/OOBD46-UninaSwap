package Controller;

import DAO.ConsegnaDAO;
import entità.Consegna;
import entità.Annuncio;
import entity.enums.TipoConsegna;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class ConsegnaController {

    private ConsegnaDAO consegnaDAO;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ConsegnaController() {
        this.consegnaDAO = new ConsegnaDAO();
    }

    public int creaConsegna(String destinatario, String note, TipoConsegna tipo, String sedeUni, 
                           LocalDate data, LocalTime oraInizio, LocalTime oraFine, String indirizzo, 
                           String civico, String corriere, String tracking, int idAnnuncio) {
        try {
            if (destinatario == null || destinatario.trim().isEmpty()) {
                System.out.println("Destinatario non valido!");
                return -1;
            }

            Consegna nuovaConsegna = new Consegna();
            nuovaConsegna.setDestinatario(destinatario);
            nuovaConsegna.setNoteConsegna(note);
            nuovaConsegna.setTipoConsegna(tipo);
            nuovaConsegna.setSedeUniversitaria(sedeUni);
            nuovaConsegna.setData(data);
            nuovaConsegna.setOraInizioPref(oraInizio);
            nuovaConsegna.setOraFinePref(oraFine);
            nuovaConsegna.setIndirizzo(indirizzo);
            nuovaConsegna.setCivico(civico);
            nuovaConsegna.setCorriere(corriere);
            nuovaConsegna.setTrackingNumber(tracking);
            nuovaConsegna.setIdAnnuncio(idAnnuncio);

            int newId = consegnaDAO.insertIntoDB(nuovaConsegna);
            
            if (newId > 0) {
                System.out.println("Consegna creata con ID: " + newId);
                return newId;
            }
            return -1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public String validaEModificaConsegna(int idConsegna, String destinatario, String dataStr, 
                                         String oraInizioStr, String oraFineStr, TipoConsegna tipo,
                                         String sedeUni, String indirizzo, String civico, 
                                         String corriere, String tracking, String note) {
        try {
            if (destinatario == null || destinatario.trim().isEmpty()) {
                return "Il destinatario è obbligatorio";
            }

            LocalDate dataConsegna;
            try {
                dataConsegna = LocalDate.parse(dataStr.trim(), DATE_FORMATTER);
            } catch (DateTimeParseException ex) {
                return "Formato data non valido. Usa yyyy-MM-dd";
            }

            if (dataConsegna.isBefore(LocalDate.now())) {
                return "La data non può essere nel passato";
            }

            LocalTime oraInizio = null;
            LocalTime oraFine = null;
            
            if (oraInizioStr != null && !oraInizioStr.trim().isEmpty() && 
                oraFineStr != null && !oraFineStr.trim().isEmpty()) {
                try {
                    oraInizio = LocalTime.parse(oraInizioStr.trim(), TIME_FORMATTER);
                    oraFine = LocalTime.parse(oraFineStr.trim(), TIME_FORMATTER);
                    
                    if (!oraInizio.isBefore(oraFine)) {
                        return "Ora inizio deve essere prima di ora fine";
                    }
                } catch (DateTimeParseException ex) {
                    return "Formato ora non valido. Usa HH:mm:ss";
                }
            } else if ((oraInizioStr != null && !oraInizioStr.trim().isEmpty()) || 
                       (oraFineStr != null && !oraFineStr.trim().isEmpty())) {
                return "Inserisci sia ora inizio che fine";
            }

            String sedeUniversitaria = null;
            String indirizzoConsegna = null;
            String civicoConsegna = null;
            String corriereConsegna = null;
            String trackingConsegna = null;

            if (tipo == TipoConsegna.A_MANO) {
                if (sedeUni == null || sedeUni.trim().isEmpty()) {
                    return "Sede universitaria obbligatoria per consegna a mano";
                }
                sedeUniversitaria = sedeUni.trim();
            } else if (tipo == TipoConsegna.SPEDIZIONE) {
                if (indirizzo == null || indirizzo.trim().isEmpty() || civico == null || civico.trim().isEmpty()) {
                    return "Indirizzo e civico obbligatori per spedizione";
                }
                
                indirizzoConsegna = indirizzo.trim();
                civicoConsegna = civico.trim();
                corriereConsegna = (corriere != null && !corriere.trim().isEmpty()) ? corriere.trim() : null;
                trackingConsegna = (tracking != null && !tracking.trim().isEmpty()) ? tracking.trim() : null;
            }

            List<Consegna> tutte = listaTutteConsegne();
            Consegna consegna = tutte.stream()
                                     .filter(c -> c.getIdConsegna() == idConsegna)
                                     .findFirst()
                                     .orElse(null);

            if (consegna == null) {
                return "Consegna non trovata nel sistema";
            }

            consegna.setDestinatario(destinatario.trim());
            consegna.setTipoConsegna(tipo);
            consegna.setData(dataConsegna);
            consegna.setOraInizioPref(oraInizio);
            consegna.setOraFinePref(oraFine);
            consegna.setSedeUniversitaria(sedeUniversitaria);
            consegna.setIndirizzo(indirizzoConsegna);
            consegna.setCivico(civicoConsegna);
            consegna.setCorriere(corriereConsegna);
            consegna.setTrackingNumber(trackingConsegna);
            consegna.setNoteConsegna((note != null) ? note.trim() : null);

            if (!modificaConsegna(consegna)) {
                return "Errore durante il salvataggio della consegna";
            }

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Errore imprevisto: " + ex.getMessage();
        }
    }

    private boolean modificaConsegna(Consegna consDaMod) {
        try {
            return consegnaDAO.update(consDaMod);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminaConsegna(int idConsegna) {
        try {
            return consegnaDAO.delete(idConsegna);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Consegna> listaTutteConsegne() {
        try {
            return consegnaDAO.findAll();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Consegna> listaConsegnePerAnnuncio(int idAnnuncio) {
        try {
            return consegnaDAO.findByAnnuncio(idAnnuncio);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Consegna> getConsegneDaRicevere(String username) {
        List<Consegna> tutte = listaTutteConsegne();
        if (tutte == null) {
            return null;
        }
        return tutte.stream()
                    .filter(c -> c.getDestinatario().equals(username))
                    .collect(Collectors.toList());
    }

    public List<Consegna> getConsegneDaInviare(String username, AnnuncioController annuncioController) {
        List<Consegna> tutte = listaTutteConsegne();
        if (tutte == null) {
            return null;
        }
        return tutte.stream()
                    .filter(c -> {
                        try {
                            Annuncio ann = annuncioController.cercaPerId(c.getIdAnnuncio());
                            return ann != null && ann.getUsername().equals(username);
                        } catch (Exception ex) {
                            return false;
                        }
                    })
                    .collect(Collectors.toList());
    }

    public Consegna getConsegnaPerId(int idConsegna) {
        List<Consegna> tutte = listaTutteConsegne();
        if (tutte == null) {
            return null;
        }
        return tutte.stream()
                    .filter(c -> c.getIdConsegna() == idConsegna)
                    .findFirst()
                    .orElse(null);
    }
}
