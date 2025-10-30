package entity.enums;

public enum TipoConsegna {
    A_MANO("A MANO"),  //siccome le enumerazioni non possono avere spazi, associo il tipo A_MANO alla stringa A MANO (con lo spazio) come viene salvato nel DB per evitare situazioni in cui non viene riconosciuto il tipo
    SPEDIZIONE("SPEDIZIONE"); //stessa cosa per spedizione

    private final String value; //per ogni istanza salvo il tipo di valore (A MANO O SPEDIZIONE)

    private TipoConsegna(String value) //privato per non consentire istanze di TipoConsegna
    {
        this.value = value;
    } 

    @Override
    public String toString() //dice il tipo di valore  
    {
        return value;
    }

    public static TipoConsegna fromString(String value) throws IllegalArgumentException
    {
        if (value == null) return null;
        for (TipoConsegna t : TipoConsegna.values()) {
            if (t.value.equalsIgnoreCase(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("TipoConsegna non valido: " + value);
    }
}
