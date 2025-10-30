package Controller;
import DAO.OffertaDAO;
import DAO.AnnuncioDAO;
import entità.Offerta;
import entità.Annuncio;
import entity.enums.StatoOfferta;
import entity.enums.TipoAnnuncio;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class OffertaController {
    private OffertaDAO offertaDAO;
    private AnnuncioDAO annuncioDAO;

    public OffertaController() {
        this.offertaDAO = new OffertaDAO();
        this.annuncioDAO = new AnnuncioDAO();
    }

    public boolean creaOfferta(LocalDate data, TipoAnnuncio tipo, double prezzoProposto, String oggetti, String messaggio, String username, int idAnnuncio, String comeConsegnare) {
        try {
            Annuncio annuncio = annuncioDAO.findByID(idAnnuncio);
            if (annuncio == null || !annuncio.isDisponibile()) {
                System.out.println("Annuncio non valido o non disponibile.");
                return false;
            }

            Offerta offerta = new Offerta();
            offerta.setDataOff(data);
            offerta.setStatoOfferta(StatoOfferta.IN_SOSPESO);
            offerta.setTipoOfferta(tipo);
            offerta.setPrezzoProposto(prezzoProposto);
            offerta.setOggetti(oggetti);
            offerta.setMessaggio(messaggio);
            offerta.setUsername(username);
            offerta.setIdAnnuncio(idAnnuncio);
            offerta.setComeConsegnare(comeConsegnare);

            int newID = offertaDAO.insertIntoDB(offerta);
            if (newID > 0) {
                System.out.println("Offerta creata con ID: " + newID);
                return true;
            }
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean validaECreaOfferta(LocalDate data, TipoAnnuncio tipo, double prezzoProposto, 
                                       String oggetti, String messaggio, String username, 
                                       int idAnnuncio, String comeConsegnare) throws IllegalArgumentException {
        try {
            Annuncio annuncio = annuncioDAO.findByID(idAnnuncio);
            if (annuncio == null) {
                throw new IllegalArgumentException("Annuncio non trovato");
            }

            if (annuncio.getUsername() != null && annuncio.getUsername().equals(username)) {
                throw new IllegalArgumentException("Non puoi offrire sui tuoi annunci");
            }

            if (!annuncio.isDisponibile()) {
                throw new IllegalArgumentException("Annuncio non disponibile");
            }

            List<Offerta> offerteEsistenti = offertaDAO.getAllByAnnuncio(idAnnuncio);
            for (Offerta o : offerteEsistenti) {
                if (o.getUsername().equals(username)) {
                    throw new IllegalArgumentException("Hai già fatto un'offerta");
                }
            }

            if (tipo == TipoAnnuncio.VENDITA) {
                if (prezzoProposto < 0) {
                    throw new IllegalArgumentException("Prezzo negativo");
                }
                if (prezzoProposto < annuncio.getPrezzoOffertaMinima()) {
                    throw new IllegalArgumentException("Prezzo minimo: €" + annuncio.getPrezzoOffertaMinima());
                }
            } else if (tipo == TipoAnnuncio.SCAMBIO) {
                if (oggetti == null || oggetti.trim().isEmpty()) {
                    throw new IllegalArgumentException("Inserisci oggetti");
                }
            }

            if (comeConsegnare == null || comeConsegnare.trim().isEmpty()) {
                throw new IllegalArgumentException("Inserisci modalità e orario consegna");
            }

            return creaOfferta(data, tipo, prezzoProposto, oggetti, messaggio, username, idAnnuncio, comeConsegnare);

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean accettaOfferta(int idOfferta) {
        try {
            Offerta offerta = offertaDAO.getByID(idOfferta);
            if (offerta == null) {
                System.out.println("Offerta non trovata.");
                return false;
            }

            boolean ok = offertaDAO.updateStato(idOfferta, StatoOfferta.ACCETTATA);
            if (!ok) {
                System.out.println("Errore aggiornando stato offerta accettata.");
                return false;
            }

            List<Offerta> tutte = offertaDAO.getAllByAnnuncio(offerta.getIdAnnuncio());
            for (Offerta o : tutte) {
                if (o.getIdOfferta() != idOfferta) {
                    boolean rifiutato = offertaDAO.updateStato(o.getIdOfferta(), StatoOfferta.RIFIUTATA);
                    if (!rifiutato) {
                        System.out.println("Errore aggiornando stato offerta rifiutata ID: " + o.getIdOfferta());
                        return false;
                    }
                }
            }

            Annuncio annuncio = annuncioDAO.findByID(offerta.getIdAnnuncio());
            if (annuncio == null) {
                System.out.println("Annuncio non trovato durante aggiornamento.");
                return false;
            }
            annuncio.setDisponibile(false);
            boolean updated = annuncioDAO.update(annuncio);
            if (!updated) {
                System.out.println("Errore aggiornando annuncio.");
                return false;
            }

            System.out.println("Offerta accettata e annuncio chiuso.");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean rifiutaOfferta(int idOfferta) {
        try {
            return offertaDAO.updateStato(idOfferta, StatoOfferta.RIFIUTATA);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean isOffertaGestibile(int idOfferta) {
        try {
            Offerta offerta = offertaDAO.getByID(idOfferta);
            return offerta != null && offerta.getStatoOfferta() == StatoOfferta.IN_SOSPESO;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Offerta> getOfferteRicevuteDaUtente(String username) {
        List<Offerta> risultato = new ArrayList<>();
        try {
            List<Annuncio> annunciUtente = annuncioDAO.cercaAnnunciPerUtente(username);
            
            for (Annuncio annuncio : annunciUtente) {
                List<Offerta> offerteAnnuncio = offertaDAO.getAllByAnnuncio(annuncio.getIdAnnuncio());
                
                for (Offerta offerta : offerteAnnuncio) {
                    if (!offerta.getUsername().equals(username)) {
                        risultato.add(offerta);
                    }
                }
            }
            
            return risultato;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Annuncio> getAnnunciDisponibiliPerOfferte(String username) {
        List<Annuncio> risultato = new ArrayList<>();
        try {
            List<Annuncio> tuttiAnnunci = annuncioDAO.findAll();
            
            for (Annuncio annuncio : tuttiAnnunci) {
                if (annuncio.isDisponibile() && 
                    annuncio.getUsername() != null && 
                    !annuncio.getUsername().equals(username)) {
                    risultato.add(annuncio);
                }
            }
            
            return risultato;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean haGiaOfferto(String username, int idAnnuncio) {
        try {
            List<Offerta> offerte = offertaDAO.getAllByAnnuncio(idAnnuncio);
            for (Offerta o : offerte) {
                if (o.getUsername().equals(username)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Offerta> getOffertePerAnnuncio(int idAnnuncio) {
        try {
            return offertaDAO.getAllByAnnuncio(idAnnuncio);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Offerta> getOffertePerUtente(String username) {
        try {
            return offertaDAO.getAllByUsername(username);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
