package entità;

public class Categoria 
{
    private int idCategoria;
    private String nomeCategoria;

    public Categoria() {}
    public Categoria(String nomeCategoria) 
    {
        this.nomeCategoria = nomeCategoria;
    }

    public int getIdCategoria() 
    { 
    	return idCategoria; 
    }
    
    public String getNomeCategoria() 
    { 
    	return nomeCategoria; 
    }
    public void setNomeCategoria(String nomeCategoria) 
    { 
    	this.nomeCategoria = nomeCategoria; 
    }
    
    public void setIdCategoria(int id)
    {
    	this.idCategoria = id; 
    }

    @Override
    public String toString() {
        return nomeCategoria;
    }
}
