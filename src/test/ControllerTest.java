package test;

import Controller.*;
import entità.*;
import entity.enums.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ControllerTest {

    public static void main(String[] args) {

        try {
            // ====== INIZIALIZZAZIONE CONTROLLER ======
            UtenteController utenteCtrl = new UtenteController();
            CategoriaController catCtrl = new CategoriaController();
            AnnuncioController annCtrl = new AnnuncioController();
            OffertaController offCtrl = new OffertaController();
            ConsegnaController consCtrl = new ConsegnaController();

            // ====== 1. TEST UTENTE ======
            System.out.println("=== TEST UTENTE ===");
            utenteCtrl.registrazioneNuovoUtente("mrossi", "mario.rossi@example.com", "Mario", "Rossi", "pwd123");
            utenteCtrl.registrazioneNuovoUtente("lbianchi", "luigi.bianchi@example.com", "Luigi", "Bianchi", "pwd456");

            System.out.println("Lista utenti:");
            for (Utente u : utenteCtrl.listaUtentiRegistrati()) {
                System.out.println(u);
            }

            // ====== 2. TEST CATEGORIA ======
            System.out.println("\n=== TEST CATEGORIA ===");
            catCtrl.aggiungiCategoria("Libri");
            catCtrl.aggiungiCategoria("Elettronica");

            List<Categoria> categorie = catCtrl.listCategorie();
            for (Categoria c : categorie) {
                System.out.println("Categoria: " + c.getNomeCategoria() + " ID=" + c.getIdCategoria());
            }

            Categoria catLibri = catCtrl.searchByName("Libri");

            // ====== 3. TEST ANNUNCIO ======
            System.out.println("\n=== TEST ANNUNCIO ===");
            Utente user = utenteCtrl.cercaUtente("mrossi");

            // crea annuncio vendita
            annCtrl.creaNuovoAnnuncio("Libro Java", "Ottimo stato", TipoAnnuncio.VENDITA,
                    null, 10.0, 20.0, user.getUsername(), catLibri.getNomeCategoria());

            // crea annuncio scambio
            annCtrl.creaNuovoAnnuncio("Tablet Samsung", "Perfetto per studenti", TipoAnnuncio.SCAMBIO,
                    "Cuffie Bluetooth", 0.0, 0.0, user.getUsername(), catLibri.getNomeCategoria());

            // lista annunci
            for (Annuncio a : annCtrl.mostraTuttiAnnunci()) {
                System.out.println("Annuncio: " + a.getTitolo() + " - " + a.getTipoAnnuncio());
            }

            // ====== 4. TEST OFFERTA ======
            System.out.println("\n=== TEST OFFERTA ===");
            List<Annuncio> annunci = annCtrl.mostraTuttiAnnunci();
            Annuncio annVendita = annunci.get(0);

            offCtrl.creaOfferta(LocalDate.now(), TipoAnnuncio.VENDITA, 15.00 , null, null, user.getUsername(), annVendita.getIdAnnuncio(),  "A MONTE SANT'ANGELO" );
            

            List<Offerta> offerte = offCtrl.getOffertePerAnnuncio(annVendita.getIdAnnuncio());
            for (Offerta o : offerte) {
                System.out.println("Offerta: " + o.getIdOfferta() + " da " + o.getUsername() + " stato=" + o.getStatoOfferta());
            }

            // ====== 5. TEST CONSEGNA ======
            System.out.println("\n=== TEST CONSEGNA ===");           

            int idCons = consCtrl.creaConsegna("Mario Rossi", "Consegna mattina", TipoConsegna.fromString("A MANO"), "Sede Napoli", LocalDate.now().plusDays(3), LocalTime.of(9, 0), LocalTime.of(12,0), null, null, null, null, annVendita.getIdAnnuncio()); 
            if(idCons == -1)
            	System.out.println("Problema nella creazione della consegna!");
            else System.out.println("Consegna creata con ID: " + idCons);

            // lista consegne per annuncio
            for (Consegna cx : consCtrl.listaConsegnePerAnnuncio(annVendita.getIdAnnuncio())) {
                System.out.println(cx);
            }

            System.out.println("I test sono andati tutti a buon fine!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
