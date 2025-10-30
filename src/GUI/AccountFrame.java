package GUI;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import Controller.UtenteController;
import entità.Utente;

public class AccountFrame extends JFrame 
{
    public AccountFrame(String usernameUtente) 
    {
        setTitle("Il mio Account - Unina Swap");
        setSize(550, 500);
        setMinimumSize(new Dimension(550, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(23, 162, 184));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JPanel headerContent = new JPanel();
        headerContent.setLayout(new BoxLayout(headerContent, BoxLayout.Y_AXIS));
        headerContent.setOpaque(false);
        
        JLabel lblIcon = new JLabel("👤");
        lblIcon.setFont(new Font("SansSerif", Font.PLAIN, 36));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerContent.add(lblIcon);
        
        headerContent.add(Box.createVerticalStrut(8));
        
        JLabel lblTitle = new JLabel("Il mio Account");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerContent.add(lblTitle);
        
        headerPanel.add(headerContent, BorderLayout.CENTER);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        UtenteController ctrlU = new UtenteController();
        Utente ut = ctrlU.cercaUtente(usernameUtente);
        
        contentPanel.add(createInfoRow("Username", usernameUtente, new Color(0, 102, 204)));
        contentPanel.add(Box.createVerticalStrut(15));
        
        contentPanel.add(createInfoRow("Nome", ut.getNome(), new Color(40, 167, 69)));
        contentPanel.add(Box.createVerticalStrut(15));
        
        contentPanel.add(createInfoRow("Cognome", ut.getCognome(), new Color(111, 66, 193)));
        contentPanel.add(Box.createVerticalStrut(15));
        
        contentPanel.add(createInfoRow("Email", ut.getEmail(), new Color(220, 53, 69)));
        contentPanel.add(Box.createVerticalGlue());
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(new LineBorder(new Color(220, 220, 220), 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Color.WHITE);
        
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(new Color(240, 242, 245));
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentWrapper.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentWrapper, BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createInfoRow(String label, String value, Color accentColor) {
        JPanel row = new JPanel(new BorderLayout(12, 0));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 230, 230), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        JPanel colorBar = new JPanel();
        colorBar.setBackground(accentColor);
        colorBar.setPreferredSize(new Dimension(4, 40));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        textPanel.setOpaque(false);
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblLabel.setForeground(new Color(108, 117, 125));
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblValue.setForeground(new Color(33, 37, 41));
        
        textPanel.add(lblLabel);
        textPanel.add(lblValue);
        
        row.add(colorBar, BorderLayout.WEST);
        row.add(textPanel, BorderLayout.CENTER);
        
        return row;
    }
}
