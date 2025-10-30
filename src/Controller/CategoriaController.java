package Controller;

import DAO.CategoriaDAO;
import entità.Categoria;
import java.sql.SQLException;
import java.util.List;

public class CategoriaController 
{
	private CategoriaDAO categoriaDAO;
	
	public CategoriaController()
	{
		this.categoriaDAO = new CategoriaDAO();
	}
	
	public boolean aggiungiCategoria(String nomeCategoria)
	{
		try
		{
			Categoria esistente = categoriaDAO.findbyNome(nomeCategoria);
			
			if (nomeCategoria == null || nomeCategoria.trim().isEmpty()) 
			{
			    System.out.println("Nome categoria non valido.");
			    return false;
			}

			if(esistente != null)
			{
				System.out.println("La categoria  " + esistente.getNomeCategoria() + " già esiste");
				return false;
			} 
			
			Categoria nuova = new Categoria(nomeCategoria);
			categoriaDAO.insertIntoDB(nuova);
			
			System.out.println("Categoria " + nuova.getNomeCategoria() + " creata con ID= " + nuova.getIdCategoria());
			
			return true;
			
		} catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean eliminaCategoria(String nomeCategoria)
	{
		try
		{
			if (categoriaDAO.countAnnunciByCategoria(nomeCategoria) > 0) 
			{
			    System.out.println("Impossibile eliminare: ci sono annunci attivi.");
			    return false;
			}

			categoriaDAO.deleteFromDB(nomeCategoria);
			return true;

		} catch(SQLException ex)	
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public List<Categoria> listCategorie()
	{
		try
		{
			return categoriaDAO.findAll();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Categoria searchByName(String nome)
	{
		try
		{
			return categoriaDAO.findbyNome(nome);
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Categoria searchByID(int id)
	{
		try
		{
			return categoriaDAO.findByID(id);
		} catch(SQLException ex)
		{
			ex.printStackTrace();
			return null; 
		}
	}
	
	public int contaAnnunci(String nomeC)
	{
		try
		{
			return categoriaDAO.countAnnunciByCategoria(nomeC); 
		} catch(SQLException ex)
		{
			ex.printStackTrace();
			return -1;
		}
	}
}
