package GUI;

import Controller.UtenteController;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField txtUsername, txtEmail, txtNome, txtCognome;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JButton btnSubmit, btnBack;
    private UtenteController utenteController;

    public RegisterFrame() {
        setTitle("Registrazione - Unina Swap");
        setSize(600, 650);
        setMinimumSize(new Dimension(550, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        utenteController = new UtenteController();

        // Panel principale con sfondo colorato
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));
        
        // Panel centrale con il form
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(30, 30, 30, 30),
            BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
            )
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titolo
        JLabel lblWelcome = new JLabel("Crea il tuo account", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblWelcome.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblWelcome, gbc);

        // Sottotitolo
        JLabel lblSubtitle = new JLabel("Unisciti a Unina Swap", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(100, 100, 100));
        gbc.gridy++;
        panel.add(lblSubtitle, gbc);
        
        // Spazio
        gbc.gridy++;
        panel.add(Box.createVerticalStrut(10), gbc);

        // Username
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0.3; // Label prende meno spazio
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblUsername.setForeground(new Color(60, 60, 60));
        panel.add(lblUsername, gbc);
        
        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.weightx = 0.7; // Campo di testo prende più spazio
        panel.add(txtUsername, gbc);

        // Email
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblEmail.setForeground(new Color(60, 60, 60));
        panel.add(lblEmail, gbc);
        
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(txtEmail, gbc);

        // Nome
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblNome.setForeground(new Color(60, 60, 60));
        panel.add(lblNome, gbc);
        
        txtNome = new JTextField(20);
        txtNome.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtNome.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(txtNome, gbc);

        // Cognome
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        JLabel lblCognome = new JLabel("Cognome:");
        lblCognome.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblCognome.setForeground(new Color(60, 60, 60));
        panel.add(lblCognome, gbc);
        
        txtCognome = new JTextField(20);
        txtCognome.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtCognome.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(txtCognome, gbc);

        // Password
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblPassword.setForeground(new Color(60, 60, 60));
        panel.add(lblPassword, gbc);
        
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(txtPassword, gbc);

        // Conferma Password
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        JLabel lblConfirm = new JLabel("Conferma Password:");
        lblConfirm.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblConfirm.setForeground(new Color(60, 60, 60));
        panel.add(lblConfirm, gbc);
        
        txtConfirmPassword = new JPasswordField(20);
        txtConfirmPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtConfirmPassword.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(txtConfirmPassword, gbc);

        // Spazio prima dei bottoni
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.weightx = 0; // Reset peso
        panel.add(Box.createVerticalStrut(5), gbc);

        // Panel per i bottoni affiancati
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setMaximumSize(new Dimension(280, 45));
        
        // Bottone Registrati (principale, blu)
        btnSubmit = new JButton("Registrati");
        btnSubmit.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSubmit.setBackground(new Color(40, 167, 69)); // Verde
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(btnSubmit);
        
        // Bottone Indietro (secondario)
        btnBack = new JButton("Indietro");
        btnBack.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(new Color(108, 117, 125)); // Grigio
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(108, 117, 125), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(btnBack);
        
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        // Aggiungi il panel al centro del mainPanel
        mainPanel.add(panel, BorderLayout.CENTER);
        add(mainPanel);

        // Azione bottone Indietro
        btnBack.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        // Azione bottone Registrati
        btnSubmit.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String email = txtEmail.getText().trim();
            String nome = txtNome.getText().trim();
            String cognome = txtCognome.getText().trim();
            String password = new String(txtPassword.getPassword());
            String confirm = new String(txtConfirmPassword.getPassword());

            if (username.isEmpty() || email.isEmpty() || nome.isEmpty() || cognome.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                showStyledMessage(
                    "Tutti i campi sono obbligatori",
                    "Campi mancanti",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (!password.equals(confirm)) {
                showStyledMessage(
                    "Le password inserite non corrispondono",
                    "Password diverse",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            boolean esito = utenteController.registrazioneNuovoUtente(username, email, nome, cognome, password);
            
            if (esito) {
                showStyledMessage(
                    "Il tuo account è stato creato con successo!",
                    "Registrazione completata",
                    JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
                new LoginFrame();
            } else {
                showStyledMessage(
                    "Username o email già esistenti",
                    "Registrazione fallita",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        setVisible(true);
    }
    
    // Metodo per mostrare messaggi stilizzati
    private void showStyledMessage(String message, String title, int messageType) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Icona colorata
        JLabel iconLabel = new JLabel();
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        Color titleColor;
        if (messageType == JOptionPane.ERROR_MESSAGE) {
            iconLabel.setText("✖");
            titleColor = new Color(220, 53, 69);
        } else if (messageType == JOptionPane.WARNING_MESSAGE) {
            iconLabel.setText("⚠");
            titleColor = new Color(255, 193, 7);
        } else {
            iconLabel.setText("✓");
            titleColor = new Color(40, 167, 69);
        }
        iconLabel.setForeground(titleColor);
        panel.add(iconLabel);
        
        panel.add(Box.createVerticalStrut(10));
        
        // Titolo
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(titleColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        
        panel.add(Box.createVerticalStrut(5));
        
        // Messaggio
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        messageLabel.setForeground(new Color(60, 60, 60));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(messageLabel);
        
        JOptionPane.showMessageDialog(this, panel, title, JOptionPane.PLAIN_MESSAGE);
    }
}