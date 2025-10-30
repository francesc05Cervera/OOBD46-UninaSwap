package test;

import DAO.CategoriaDAO;
import DAO.UtenteDAO;
import entità.Categoria;
import entità.Utente;
import java.util.*;

public class DAOTest //This class have only test's to CategoriaDAO and UtenteDAO
{
	
    public static void main(String[] args) 
    {
        try {
        	
        	
            CategoriaDAO cdao = new CategoriaDAO(); //I've created the instance of CategoriaDAO to use the function in his class
            Categoria cat = new Categoria(); //I've created the instance  of Categoria to save the items picked from Database
            UtenteDAO udao = new UtenteDAO(); 
            List<Utente> f = new ArrayList<Utente>(); 

            cdao.deleteFromDB("Libri");
            cdao.deleteFromDB("Elettronica");
            cdao.deleteFromDB("Appunti");
            cdao.deleteFromDB("NoteDiStudenti");
            cdao.deleteFromDB("NoteDiStudenti2");
            cdao.deleteFromDB("NoteDiStudenti55");
            
            
            udao.delete("mrossi");
            udao.delete("lbianchi");
            udao.delete("averdini");
            udao.delete("mrossi2");
            udao.delete("mrossi34");
            udao.delete("mrossi354");
            
            cat.setNomeCategoria("NoteDiStudenti55");
            cdao.insertIntoDB(cat);
            System.out.println("Categoria inserita con ID: " + cat.getIdCategoria());

            Utente u = new Utente("mrossi354", "mrossi.rossi.po5@example.com", "Mario", "Rossi", "pwd123");
            udao.InsertInDB(u);
            System.out.println("Utente inserito: " + u.getUsername());
            
            f = udao.findAll(); 
            
            for(Utente ut : f)
            	System.out.println(ut.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
