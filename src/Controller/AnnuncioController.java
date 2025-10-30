package Controller;

import DAO.AnnuncioDAO;
import DAO.CategoriaDAO;
import DAO.UtenteDAO;
import entità.Annuncio;
import entità.Utente;
import entità.Categoria;
import entità.Offerta;
import entity.enums.TipoAnnuncio;
import entity.enums.StatoOfferta;

import java.sql.SQLException;
import java.util.List;

public class AnnuncioController {

    private AnnuncioDAO annuncioDAO;
    private UtenteDAO utenteDAO;
    private CategoriaDAO categoriaDAO;
    private OffertaController offertaController;
    private ConsegnaController consegnaController;

    public AnnuncioController() {
        this.annuncioDAO = new AnnuncioDAO();
        this.utenteDAO = new UtenteDAO();
        this.categoriaDAO = new CategoriaDAO();
        this.offertaController = new OffertaController();
        this.consegnaController = new ConsegnaController();
    }

    public boolean creaNuovoAnnuncio(String titolo, String descrizione, TipoAnnuncio tipo,
                                     String oggettiDaScambiare, double prezzoOffertaMinima,
                                     double prezzoVendita, String username, String nomeCategoria) throws Exception {
        
        if (titolo == null || titolo.trim().isEmpty()) {
            throw new Exception("Il titolo è obbligatorio");
        }
        if (titolo.trim().length() < 3) {
            throw new Exception("Il titolo deve contenere almeno 3 caratteri");
        }
        if (descrizione == null || descrizione.trim().isEmpty()) {
            throw new Exception("La descrizione è obbligatoria");
        }
        if (descrizione.trim().length() < 10) {
            throw new Exception("La descrizione deve contenere almeno 10 caratteri");
        }

        try {
            Utente utente = utenteDAO.findByUser(username);
            Categoria categoria = categoriaDAO.findbyNome(nomeCategoria);

            if (utente == null) {
                throw new Exception("Utente non trovato");
            }
            if (categoria == null) {
                throw new Exception("Categoria non trovata");
            }

            Annuncio nuovo = null;

            switch (tipo) {
                case VENDITA:
                    if (prezzoOffertaMinima < 0) {
                        throw new Exception("Il prezzo minimo non può essere negativo");
                    }
                    if (prezzoVendita <= 0) {
                        throw new Exception("Il prezzo di vendita deve essere maggiore di zero");
                    }
                    if (prezzoOffertaMinima > prezzoVendita) {
                        throw new Exception("Il prezzo minimo non può essere maggiore del prezzo di vendita");
                    }
                    nuovo = new Annuncio(titolo, descrizione, tipo, null,
                            prezzoOffertaMinima, prezzoVendita, true,
                            utente.getUsername(), categoria.getIdCategoria());
                    break;

                case SCAMBIO:
                    if (oggettiDaScambiare == null || oggettiDaScambiare.trim().isEmpty()) {
                        throw new Exception("Devi indicare gli oggetti da scambiare");
                    }
                    nuovo = new Annuncio(titolo, descrizione, tipo, oggettiDaScambiare,
                            0.0, 0.0, true,
                            utente.getUsername(), categoria.getIdCategoria());
                    break;

                case REGALO:
                    nuovo = new Annuncio(titolo, descrizione, tipo, null,
                            0.0, 0.0, true,
                            utente.getUsername(), categoria.getIdCategoria());
                    break;
            }

            annuncioDAO.insertIntoDB(nuovo);
            System.out.println("Annuncio creato con ID: " + nuovo.getIdAnnuncio());
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Errore durante la creazione dell'annuncio: " + e.getMessage());
        }
    }

    public List<Annuncio> mostraTuttiAnnunci() {
        try {
            return annuncioDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Annuncio cercaPerId(int id) {
        try {
            return annuncioDAO.findByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean modificaAnnuncio(Annuncio annuncio, String titolo, String descrizione, 
                                   TipoAnnuncio tipo, String oggettiDaScambiare,
                                   double prezzoOffertaMinima, double prezzoVendita, 
                                   boolean disponibile) throws Exception {
        
        if (titolo == null || titolo.trim().isEmpty()) {
            throw new Exception("Il titolo è obbligatorio");
        }
        if (titolo.trim().length() < 3) {
            throw new Exception("Il titolo deve contenere almeno 3 caratteri");
        }
        if (descrizione == null || descrizione.trim().isEmpty()) {
            throw new Exception("La descrizione è obbligatoria");
        }
        if (descrizione.trim().length() < 10) {
            throw new Exception("La descrizione deve contenere almeno 10 caratteri");
        }

        try {
            annuncio.setTitolo(titolo);
            annuncio.setDescrizione(descrizione);
            annuncio.setTipoAnnuncio(tipo);
            annuncio.setDisponibile(disponibile);

            switch (tipo) {
                case VENDITA:
                    if (prezzoOffertaMinima < 0) {
                        throw new Exception("Il prezzo minimo non può essere negativo");
                    }
                    if (prezzoVendita <= 0) {
                        throw new Exception("Il prezzo di vendita deve essere maggiore di zero");
                    }
                    if (prezzoOffertaMinima > prezzoVendita) {
                        throw new Exception("Il prezzo minimo non può essere maggiore del prezzo di vendita");
                    }
                    annuncio.setPrezzoOffertaMinima(prezzoOffertaMinima);
                    annuncio.setPrezzoVendita(prezzoVendita);
                    annuncio.setOggettiDaScambiare(null);
                    break;

                case SCAMBIO:
                    if (oggettiDaScambiare == null || oggettiDaScambiare.trim().isEmpty()) {
                        throw new Exception("Devi indicare gli oggetti da scambiare");
                    }
                    annuncio.setOggettiDaScambiare(oggettiDaScambiare);
                    annuncio.setPrezzoOffertaMinima(0.0);
                    annuncio.setPrezzoVendita(0.0);
                    break;

                case REGALO:
                    annuncio.setOggettiDaScambiare(null);
                    annuncio.setPrezzoOffertaMinima(0.0);
                    annuncio.setPrezzoVendita(0.0);
                    break;
            }

            return annuncioDAO.update(annuncio);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Errore durante la modifica dell'annuncio: " + e.getMessage());
        }
    }

    public boolean eliminaAnnuncio(int idAnnuncio) throws Exception {
        try {
            List<Offerta> offerte = offertaController.getOffertePerAnnuncio(idAnnuncio);
            if (offerte != null) {
                for (Offerta offerta : offerte) {
                    if (offerta.getStatoOfferta() == StatoOfferta.ACCETTATA) {
                        throw new Exception("Impossibile eliminare l'annuncio: ci sono offerte accettate associate");
                    }
                }
            }

            if (!consegnaController.listaConsegnePerAnnuncio(idAnnuncio).isEmpty()) {
                throw new Exception("Impossibile eliminare l'annuncio: ci sono consegne attive associate");
            }

            boolean eliminato = annuncioDAO.delete(idAnnuncio);
            
            if (!eliminato) {
                throw new Exception("Errore durante l'eliminazione dell'annuncio");
            }
            
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Errore durante l'eliminazione dell'annuncio: " + e.getMessage());
        }
    }
    
    public List<Annuncio> cercaPerUser(String username) {
        try {
            return annuncioDAO.cercaAnnunciPerUtente(username);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null; 
        }
    }
}
