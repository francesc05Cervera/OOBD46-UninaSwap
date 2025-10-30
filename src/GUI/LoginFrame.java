package GUI;

import Controller.UtenteController;
import entità.Utente;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame
{
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;
    private UtenteController utenteCtrl;
    
    public LoginFrame()
    {
        setTitle("Login - Unina Swap");
        setSize(550, 450);
        setMinimumSize(new Dimension(500, 400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        utenteCtrl = new UtenteController();
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(30, 30, 30, 30),
            BorderFactory.createCompoundBorder(new LineBorder(new Color(220, 220, 220), 1, true), BorderFactory.createEmptyBorder(30, 40, 30, 40))
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblWelcome = new JLabel("Benvenuto su Unina Swap!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblWelcome.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblWelcome, gbc);
        
        JLabel lblSubtitle = new JLabel("Accedi al tuo account", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(100, 100, 100));
        gbc.gridy++;
        panel.add(lblSubtitle, gbc);
        
        gbc.gridy++;
        panel.add(Box.createVerticalStrut(10), gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblUser.setForeground(new Color(60, 60, 60));
        panel.add(lblUser, gbc);
        
        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(200, 200, 200), 1, true), BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);
        
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblPass.setForeground(new Color(60, 60, 60));
        panel.add(lblPass, gbc);
        
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(200, 200, 200), 1, true), BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);
        
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        panel.add(Box.createVerticalStrut(5), gbc);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setMaximumSize(new Dimension(280, 45));
        
        btnLogin = new JButton("Accedi");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(btnLogin);
        
        btnRegister = new JButton("Registrati");
        btnRegister.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnRegister.setBackground(Color.WHITE);
        btnRegister.setForeground(new Color(0, 102, 204));
        btnRegister.setFocusPainted(false);
        btnRegister.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(0, 102, 204), 2, true), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(btnRegister);
        
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);
        
        mainPanel.add(panel, BorderLayout.CENTER);
        add(mainPanel);
        
        btnRegister.addActionListener(e -> {
            dispose();
            new RegisterFrame();
        });
        
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                String user = txtUsername.getText().trim();
                String pass = new String(txtPassword.getPassword());
                
                if (user.isEmpty() || pass.isEmpty()) {
                    showStyledMessage(
                        "Inserisci username e password per continuare",
                        "Campi mancanti",
                        JOptionPane.WARNING_MESSAGE
                    );
                } else 
                {
                    Utente u = utenteCtrl.cercaUtente(user);
                    
                    if (u == null) 
                    {
                        showStyledMessage(
                            "L'username inserito non esiste nel sistema",
                            "Utente non trovato",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    if (!u.getPassword().equals(pass)) {
                        showStyledMessage(
                            "La password inserita non è corretta",
                            "Password errata",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    
                    showStyledMessage(
                        "Benvenuto " + user + "!",
                        "Accesso effettuato",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    dispose();
                    new MenuFrame(user);
                }
            }
        });
        
        setVisible(true);
    }
    
    private void showStyledMessage(String message, String title, int messageType) 
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel iconLabel = new JLabel();
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        Color titleColor;
        
        if (messageType == JOptionPane.ERROR_MESSAGE) 
        {
            iconLabel.setText("✖");
            titleColor = new Color(220, 53, 69);
        } else if (messageType == JOptionPane.WARNING_MESSAGE) 
        {
            iconLabel.setText("⚠");
            titleColor = new Color(255, 193, 7);
        } else 
        {
            iconLabel.setText("✓");
            titleColor = new Color(40, 167, 69);
        }
        
        iconLabel.setForeground(titleColor);
        panel.add(iconLabel);
        
        panel.add(Box.createVerticalStrut(10));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(titleColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        
        panel.add(Box.createVerticalStrut(5));
        
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        messageLabel.setForeground(new Color(60, 60, 60));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(messageLabel);
        
        JOptionPane.showMessageDialog(
            this, 
            panel, 
            title, 
            JOptionPane.PLAIN_MESSAGE
        );
    }
}
