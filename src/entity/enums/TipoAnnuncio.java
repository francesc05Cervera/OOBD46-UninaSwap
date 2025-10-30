package entity.enums;

public enum TipoAnnuncio 
{
	VENDITA,
	REGALO,
	SCAMBIO;
	
	@Override
    public String toString() {
        return name();
    }

    public static TipoAnnuncio fromString(String value) {
        if (value == null) 
        	return null;
        
        return TipoAnnuncio.valueOf(value.toUpperCase());
    }

}
