package test;

import DAO.AnnuncioDAO;
import entità.Annuncio;
import entity.enums.TipoAnnuncio;

import java.util.*;

public class AnnuncioDAOtest {

    public static void main(String[] args) {
        try {
            AnnuncioDAO adao = new AnnuncioDAO();

            // Inserimento
            Annuncio a = new Annuncio();
            a.setTitolo("Test Annuncio");
            a.setDescrizione("Questo è un annuncio di test");
            a.setTipoAnnuncio(TipoAnnuncio.VENDITA);
            a.setPrezzoOffertaMinima(0); // sarà interpretato come NULL
            a.setPrezzoVendita(49.99);
            a.setOggettiDaScambiare(null);
            a.setDisponibile(true);
            a.setUsername("utente_test");
            a.setIdCategoria(18); // Assicurati che la categoria 1 esista

            int id = adao.insertIntoDB(a);
            System.out.println("✅ Annuncio inserito con ID: " + id);

            // Recupero con ID
            Annuncio trovato = adao.findByID(id);
            if (trovato != null) {
                System.out.println("🔍 Annuncio trovato: " + trovato.getTitolo());

                // Modifica
                trovato.setTitolo("Annuncio Modificato");
                boolean aggiornato = adao.update(trovato);
                System.out.println("✏️ Annuncio aggiornato: " + aggiornato);

                // Verifica modifica
                Annuncio modificato = adao.findByID(id);
                System.out.println("📌 Titolo aggiornato: " + modificato.getTitolo());
            } else {
                System.out.println("⚠️ Annuncio non trovato, impossibile aggiornare.");
            }

            // Lista completa
            List<Annuncio> lista = adao.findAll();
            System.out.println("📋 Lista annunci:");
            for (Annuncio ann : lista) {
                System.out.println(" - " + ann.getIdAnnuncio() + ": " + ann.getTitolo());
            }

            // Cancellazione
            boolean eliminato = adao.delete(id);
            System.out.println("🗑️ Annuncio eliminato: " + eliminato);

        } catch (Exception e) {
            System.err.println("❌ Errore durante l'esecuzione:");
            e.printStackTrace();
        }
    }
}
