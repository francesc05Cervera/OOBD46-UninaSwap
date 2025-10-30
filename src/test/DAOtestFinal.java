package test;

import DAO.*;
import entità.*;
import entity.enums.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DAOtestFinal {

    public static void main(String[] args) {
        try 
        {
            // ==============
            // 1. Creazione DAO
            // ==============
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            UtenteDAO utenteDAO = new UtenteDAO();
            AnnuncioDAO annuncioDAO = new AnnuncioDAO();
            OffertaDAO offertaDAO = new OffertaDAO();
            ConsegnaDAO consegnaDAO = new ConsegnaDAO();

            // Pulizia preventiva (se già esistono dati)
            categoriaDAO.deleteFromDB("Libri");
            categoriaDAO.deleteFromDB("Elettronica");
            utenteDAO.delete("mrossi");
            utenteDAO.delete("lbianchi");

            // ==============
            // 2. Inserimento categorie e utenti
            // ==============
            Categoria catLibri = new Categoria();
            catLibri.setNomeCategoria("Libri");
            categoriaDAO.insertIntoDB(catLibri);

            Categoria catElettronica = new Categoria();
            catElettronica.setNomeCategoria("Elettronica");
            categoriaDAO.insertIntoDB(catElettronica);

            Utente u1 = new Utente("mrossi", "mario.rossi@mail.com", "Mario", "Rossi", "pwd1");
            utenteDAO.InsertInDB(u1);

            Utente u2 = new Utente("lbianchi", "luigi.bianchi@mail.com", "Luigi", "Bianchi", "pwd2");
            utenteDAO.InsertInDB(u2);

            System.out.println("Categorie e utenti creati.");

            // ==============
            // 3. Creazione annuncio
            // ==============
            Annuncio ann = new Annuncio();
            ann.setTitolo("Manuale Java");
            ann.setDescrizione("Libro universitario usato");
            ann.setTipoAnnuncio(TipoAnnuncio.VENDITA);
            ann.setPrezzoOffertaMinima(10.0);
            ann.setPrezzoVendita(20.0);
            ann.setOggettiDaScambiare(null);
            ann.setUsername(u1.getUsername());
            ann.setIdCategoria(catLibri.getIdCategoria());
            ann.setDisponibile(true);

            annuncioDAO.insertIntoDB(ann);
            System.out.println("Annuncio creato: " + ann.getIdAnnuncio());

            // ==============
            // 4. Creazione offerta valida
            // ==============
            Offerta off = new Offerta();
            off.setDataOff(LocalDate.now());
            off.setStatoOfferta(StatoOfferta.IN_SOSPESO);
            off.setTipoOfferta(TipoAnnuncio.VENDITA);
            off.setPrezzoProposto(15.0);
            off.setOggetti(null);
            off.setMessaggio("Posso pagare subito");
            off.setUsername(u2.getUsername());
            off.setIdAnnuncio(ann.getIdAnnuncio());

            offertaDAO.insertIntoDB(off);
            System.out.println("Offerta creata: " + off.getIdOfferta());

            // ==============
            // 5. Simula accettazione offerta (semplificata qui)
            // ==============
            offertaDAO.updateStato(off.getIdOfferta(), StatoOfferta.ACCETTATA);
            ann.setDisponibile(false);
            annuncioDAO.update(ann);
            System.out.println("Offerta accettata e annuncio chiuso.");

            // ==============
            // 6. Trigger dovrebbe aver creato una consegna automatica
            // ==============
            List<Consegna> consegne = consegnaDAO.findByAnnuncio(ann.getIdAnnuncio());
            if (!consegne.isEmpty()) {
                Consegna c = consegne.get(0);
                System.out.println("Consegna automatica trovata con ID: " + c.getIdConsegna());

                // Aggiorno dettagli consegna tramite DAO
                c.setDestinatario("Mario Rossi");
                c.setTipoConsegna(TipoConsegna.SPEDIZIONE);
                c.setData(LocalDate.now().plusDays(3));
                c.setOraInizioPref(LocalTime.of(9, 0));
                c.setOraFinePref(LocalTime.of(12, 0));
                c.setIndirizzo("Via Roma");
                c.setCivico("10");
                c.setCorriere("DHL");
                c.setTrackingNumber("TRACK123");

                consegnaDAO.update(c);
                System.out.println("Consegna aggiornata con i dettagli utente.");
            }

            // ==============
            // 7. Recupero e stampa finale
            // ==============
            System.out.println("===== Annunci =====");
            for (Annuncio a : annuncioDAO.findAll()) {
                System.out.println(a);
            }

            System.out.println("===== Offerte =====");
            for (Offerta o : offertaDAO.getAllByAnnuncio(ann.getIdAnnuncio())) {
                System.out.println(o);
            }

            System.out.println("===== Consegne =====");
            for (Consegna c : consegnaDAO.findAll()) {
                System.out.println(c);
            }
            
            //=========
            // conteggio categorie
            // ========
            
            int n = categoriaDAO.countAnnunciByCategoria("Libri");
            System.out.println(n + "annunci");

            // ==============
            // 8. Cleanup finale
            // ==============
            consegnaDAO.delete(consegne.get(0).getIdConsegna());
            offertaDAO.delete(off.getIdOfferta());
            annuncioDAO.delete(ann.getIdAnnuncio());
            utenteDAO.delete(u1.getUsername());
            utenteDAO.delete(u2.getUsername());
            categoriaDAO.deleteFromDB("Libri");
            categoriaDAO.deleteFromDB("Elettronica");

            System.out.println("Dati di test rimossi. Test completatato senza problemi segnalati.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
