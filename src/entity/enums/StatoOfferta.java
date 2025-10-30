package entity.enums;

public enum StatoOfferta {
    ACCETTATA("ACCETTATA"),
    IN_SOSPESO("IN SOSPESO"),   
    RIFIUTATA("RIFIUTATA");

    private final String dbValue;

    StatoOfferta(String dbValue) 
    {
        this.dbValue = dbValue;
    }

    @Override
    public String toString() {
        return dbValue;
    }

    public static StatoOfferta fromString(String value) {
        for (StatoOfferta s : values()) {
            if (s.dbValue.equalsIgnoreCase(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Valore non valido: " + value);
    }
}
