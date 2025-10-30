package GUI;

import Controller.CategoriaController;
import entità.Categoria;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class CategoriaFrame extends JFrame 
{
    private CategoriaController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAggiungi, btnElimina, btnModifica, btnAggiorna, btnIndietro;
    private String usernameUtente;

    public CategoriaFrame(String usernameUtente) 
    {
        this.usernameUtente = usernameUtente;
        this.controller = new CategoriaController();

        setTitle("Gestione Categorie - Unina Swap");
        setSize(800, 600);
        setMinimumSize(new Dimension(750, 550));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(240, 242, 245));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        JLabel lblTitle = new JLabel("Gestione Categorie");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitle.setForeground(new Color(111, 66, 193));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblTitle);

        headerPanel.add(Box.createVerticalStrut(5));

        JLabel lblSubtitle = new JLabel("Visualizza e gestisci le categorie degli annunci");
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(100, 100, 100));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblSubtitle);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(240, 242, 245));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        String[] colonne = {"ID", "Nome Categoria"};
        tableModel = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(111, 66, 193, 50));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(230, 230, 230));
        table.setShowGrid(true);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(400);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(111, 66, 193));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setBorder(BorderFactory.createEmptyBorder());

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(220, 220, 220)));

        btnAggiungi = createStyledButton("+ Aggiungi", new Color(40, 167, 69));
        btnModifica = createStyledButton("✏ Modifica", new Color(0, 102, 204));
        btnElimina = createStyledButton("🗑 Elimina", new Color(220, 53, 69));
        btnAggiorna = createStyledButton("↻ Aggiorna", new Color(108, 117, 125));
        btnIndietro = createStyledButton("← Indietro", new Color(108, 117, 125));

        buttonPanel.add(btnAggiungi);
        buttonPanel.add(btnModifica);
        buttonPanel.add(btnElimina);
        buttonPanel.add(btnAggiorna);
        buttonPanel.add(btnIndietro);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        caricaCategorie();

        btnAggiungi.addActionListener(e -> aggiungiCategoria());
        btnModifica.addActionListener(e -> modificaCategoria());
        btnElimina.addActionListener(e -> eliminaCategoria());
        btnAggiorna.addActionListener(e -> caricaCategorie());
        btnIndietro.addActionListener(e -> {
            dispose();
            new MenuFrame(usernameUtente);
        });

        setVisible(true);
    }

    private JButton createStyledButton(String text, Color color) 
    {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) 
            {
                button.setBackground(color.darker());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) 
            {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void caricaCategorie() 
    {
        tableModel.setRowCount(0);
        List<Categoria> categorie = controller.listCategorie();

        if (categorie != null && !categorie.isEmpty()) 
        { 
            for (Categoria cat : categorie) 
            {
                tableModel.addRow(new Object[]{
                    cat.getIdCategoria(),
                    cat.getNomeCategoria()
                });
            }
        } else {
            showStyledMessage(
                "Non ci sono categorie nel database",
                "Nessuna categoria",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void aggiungiCategoria() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Nuova Categoria");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(new Color(111, 66, 193));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(titleLabel);

        inputPanel.add(Box.createVerticalStrut(10));

        JLabel descLabel = new JLabel("Inserisci il nome della categoria:");
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        descLabel.setForeground(new Color(60, 60, 60));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(descLabel);

        inputPanel.add(Box.createVerticalStrut(8));

        JTextField textField = new JTextField(20);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        textField.setMaximumSize(new Dimension(400, 40));
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(textField);
        
        int result = JOptionPane.showConfirmDialog(this, inputPanel,
            "Aggiungi Categoria", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) 
        {
            String nomeCategoria = textField.getText();

            if (nomeCategoria != null && !nomeCategoria.trim().isEmpty()) {
                List<Categoria> categorieEsistenti = controller.listCategorie();
                boolean esisteGia = false;

                if (categorieEsistenti != null) {
                    for (Categoria cat : categorieEsistenti) {
                        if (cat.getNomeCategoria().equalsIgnoreCase(nomeCategoria.trim())) {
                            esisteGia = true;
                            break;
                        }
                    }
                }

                if (esisteGia) {
                    showStyledMessage(
                        "La categoria '" + nomeCategoria.trim() + "' esiste già",
                        "Categoria duplicata",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                boolean risultato = controller.aggiungiCategoria(nomeCategoria.trim());

                if (risultato) {
                    showStyledMessage(
                        "La categoria è stata aggiunta con successo",
                        "Categoria aggiunta",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    caricaCategorie();
                } else {
                    showStyledMessage(
                        "Si è verificato un errore durante l'aggiunta",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }

    private void modificaCategoria() 
    {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            showStyledMessage(
                "Seleziona una categoria dalla tabella",
                "Nessuna selezione",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String vecchioNome = (String) tableModel.getValueAt(selectedRow, 1);

        try {
            if (controller.contaAnnunci(vecchioNome) > 0) {
                showStyledMessage(
                    "Ci sono annunci attivi in questa categoria",
                    "Impossibile modificare",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        } catch (Exception ex) {
            showStyledMessage(
                "Errore durante il controllo degli annunci",
                "Errore",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Modifica Categoria");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(titleLabel);

        inputPanel.add(Box.createVerticalStrut(10));

        JLabel descLabel = new JLabel("Inserisci il nuovo nome per la categoria:");
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        descLabel.setForeground(new Color(60, 60, 60));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(descLabel);

        inputPanel.add(Box.createVerticalStrut(8));

        JTextField textField = new JTextField(vecchioNome, 20);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        textField.setMaximumSize(new Dimension(400, 40));
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(textField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel, 
            "Modifica Categoria", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) 
        {
            String nuovoNome = textField.getText();

            if (nuovoNome != null && !nuovoNome.trim().isEmpty()) {
                List<Categoria> categorieEsistenti = controller.listCategorie();
                boolean esisteGia = false;

                if (categorieEsistenti != null) {
                    for (Categoria cat : categorieEsistenti) {
                        if (cat.getNomeCategoria().equalsIgnoreCase(nuovoNome.trim()) && 
                            !cat.getNomeCategoria().equalsIgnoreCase(vecchioNome)) {
                            esisteGia = true;
                            break;
                        }
                    }
                }

                if (esisteGia) 
                {
                    showStyledMessage(
                        "La categoria '" + nuovoNome.trim() + "' esiste già",
                        "Categoria duplicata",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                boolean eliminato = controller.eliminaCategoria(vecchioNome);
                if (eliminato) 
                {
                    boolean aggiunto = controller.aggiungiCategoria(nuovoNome.trim());
                    if (aggiunto) {
                        showStyledMessage(
                            "La categoria è stata modificata con successo",
                            "Categoria modificata",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        caricaCategorie();
                    } else {
                        controller.aggiungiCategoria(vecchioNome);
                        showStyledMessage(
                            "Errore durante la modifica. Categoria ripristinata",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        }
    }

    private void eliminaCategoria() 
    {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            showStyledMessage(
                "Seleziona una categoria dalla tabella",
                "Nessuna selezione",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String nomeCategoria = (String) tableModel.getValueAt(selectedRow, 1);

        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.Y_AXIS));
        confirmPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel iconLabel = new JLabel("⚠");
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 50));
        iconLabel.setForeground(new Color(255, 193, 7));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPanel.add(iconLabel);

        confirmPanel.add(Box.createVerticalStrut(15));

        JLabel titleLabel = new JLabel("Conferma Eliminazione");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(new Color(220, 53, 69));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPanel.add(titleLabel);
        
        confirmPanel.add(Box.createVerticalStrut(10));

        JLabel messageLabel = new JLabel("Vuoi eliminare la categoria:");
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(60, 60, 60));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPanel.add(messageLabel);

        confirmPanel.add(Box.createVerticalStrut(5));

        JLabel categoryLabel = new JLabel("\"" + nomeCategoria + "\"");
        categoryLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        categoryLabel.setForeground(new Color(220, 53, 69));
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPanel.add(categoryLabel);

        confirmPanel.add(Box.createVerticalStrut(10));

        JLabel warningLabel = new JLabel("Questa operazione non può essere annullata!");
        warningLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        warningLabel.setForeground(new Color(100, 100, 100));
        warningLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPanel.add(warningLabel);

        int conferma = JOptionPane.showConfirmDialog(this, confirmPanel,
            "Elimina Categoria", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (conferma == JOptionPane.YES_OPTION) 
        {
            boolean risultato = controller.eliminaCategoria(nomeCategoria);

            if (risultato) 
            {
                showStyledMessage(
                    "La categoria è stata eliminata con successo",
                    "Categoria eliminata",
                    JOptionPane.INFORMATION_MESSAGE
                );
                caricaCategorie();
            } else {
                showStyledMessage(
                    "Ci sono annunci attivi in questa categoria",
                    "Impossibile eliminare",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
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

        JOptionPane.showMessageDialog(this, panel, title, JOptionPane.PLAIN_MESSAGE);
    }
}
