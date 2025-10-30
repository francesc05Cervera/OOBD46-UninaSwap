package test;

import java.sql.Connection;
import java.sql.SQLException;
import util.DBConnection;

public class testDBC {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connessione riuscita al database unina_swap!");
            } else {
                System.out.println("⚠️ Connessione non valida.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Errore di connessione: " + e.getMessage());
        }
    }
}
