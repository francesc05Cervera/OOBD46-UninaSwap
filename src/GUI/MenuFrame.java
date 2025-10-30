package GUI;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class MenuFrame extends JFrame {
    private JButton btnAnnuncio, btnOfferte, btnConsegne, btnLogout, btnAccount, btnCategorie, btnStatistiche;
    
    public MenuFrame(String usernameUtente) {
        setTitle("Menu Principale - Unina Swap");
        setSize(750, 600);
        setMinimumSize(new Dimension(700, 550));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Panel principale con sfondo colorato
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));
        
        // Header con titolo
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        
        JLabel lblTitle = new JLabel("Menu Principale");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblTitle);
        
        headerPanel.add(Box.createVerticalStrut(10));
        
        JLabel lblUser = new JLabel("Benvenuto, " + usernameUtente);
        lblUser.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblUser.setForeground(new Color(100, 100, 100));
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblUser);
        
        // Panel centrale con i bottoni
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 242, 245));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Prima riga
        btnAnnuncio = createStyledButton("📢 Gestisci Annunci", new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(btnAnnuncio, gbc);
        
        btnOfferte = createStyledButton("💰 Gestisci Offerte", new Color(40, 167, 69));
        gbc.gridx = 1;
        centerPanel.add(btnOfferte, gbc);
        
        btnConsegne = createStyledButton("🚚 Gestisci Consegne", new Color(255, 193, 7));
        gbc.gridx = 2;
        centerPanel.add(btnConsegne, gbc);
        
        // Seconda riga
        btnCategorie = createStyledButton("📁 Gestisci Categorie", new Color(111, 66, 193));
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(btnCategorie, gbc);
        
        btnStatistiche = createStyledButton("📈 Statistiche", new Color(220, 53, 69));
        gbc.gridx = 1;
        centerPanel.add(btnStatistiche, gbc);
        
        btnAccount = createStyledButton("👤 Il mio account", new Color(23, 162, 184));
        gbc.gridx = 2;
        centerPanel.add(btnAccount, gbc);
        
        // Terza riga - Logout centrato
        btnLogout = createStyledButton("🚪 Logout", new Color(108, 117, 125));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 150, 0, 150);
        centerPanel.add(btnLogout, gbc);
        
        // Aggiungi tutto al mainPanel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        // Azioni dei pulsanti
        btnAnnuncio.addActionListener(e -> {
            dispose();
            new AnnuncioFrame(usernameUtente);
        });
        
        btnOfferte.addActionListener(e -> {
            dispose();
            new OffertaFrame(usernameUtente);
        });
        
        btnConsegne.addActionListener(e -> {
            dispose();
            new ConsegnaFrame(usernameUtente);
        });
        
        btnCategorie.addActionListener(e -> {
            dispose();
            new CategoriaFrame(usernameUtente);
        });
        
        btnAccount.addActionListener(e -> {
            new AccountFrame(usernameUtente);
        });
        
        btnStatistiche.addActionListener(e -> { 
            dispose();
            new StatisticheFrame(usernameUtente); 
        });    
        
        btnLogout.addActionListener(e -> {
            int scelta = JOptionPane.showConfirmDialog(
                this,
                "Sei sicuro di voler uscire?",
                "Conferma Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (scelta == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame();
            }
        });
        
        setVisible(true);
    }
    
    // Metodo per creare bottoni stilizzati
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(color.darker(), 1, true),
            BorderFactory.createEmptyBorder(18, 12, 18, 12)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 70));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
}