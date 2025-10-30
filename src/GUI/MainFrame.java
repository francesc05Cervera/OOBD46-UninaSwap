package GUI;

import javax.swing.*;

public class MainFrame {

    public static void main(String[] args) 
    {
        // Avvio programma → parte dal login
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame(); // apre direttamente la schermata di login
            }
        });
    }
}
