package GUI;

import Controller.ConsegnaController;
import Controller.AnnuncioController;
import entità.Consegna;
import entità.Annuncio;
import entity.enums.TipoConsegna;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ConsegnaFrame extends JFrame {
    private ConsegnaController controller;
    private AnnuncioController annuncioController;
    private JTable tableRicevute, tableInviate;
    private DefaultTableModel modelRicevute, modelInviate;
    private JButton btnIndietro;
    private String usernameUtente;

    public ConsegnaFrame(String usernameUtente) {
        this.usernameUtente = usernameUtente;
        this.controller = new ConsegnaController();
        this.annuncioController = new AnnuncioController();

        setTitle("Gestione Consegne - Unina Swap");
        setSize(1300, 750);
        setMinimumSize(new Dimension(1200, 700));
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

        JLabel lblTitle = new JLabel("Gestione Consegne");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitle.setForeground(new Color(255, 193, 7));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblTitle);

        headerPanel.add(Box.createVerticalStrut(5));

        JLabel lblSubtitle = new JLabel("Gestisci le tue consegne in entrata e in uscita");
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(100, 100, 100));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblSubtitle);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(new Color(240, 242, 245));
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(640);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);

        splitPane.setLeftComponent(creaPanelConsegne("Consegne da Ricevere", "Annunci che hai acquistato", new Color(40, 167, 69), true));
        splitPane.setRightComponent(creaPanelConsegne("Consegne da Inviare", "Annunci che ti hanno acquistato", new Color(0, 102, 204), false));

        centerWrapper.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(220, 220, 220)));
        
        btnIndietro = createStyledButton("Indietro", new Color(108, 117, 125));
        btnIndietro.addActionListener(e -> {
            dispose();
            new MenuFrame(usernameUtente);
        });
        bottomPanel.add(btnIndietro);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        caricaConsegne();
        setVisible(true);
    }

    private JPanel creaPanelConsegne(String titolo, String sottotitolo, Color themeColor, boolean isRicevute) {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(themeColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblTitolo = new JLabel(titolo);
        lblTitolo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitolo.setForeground(Color.WHITE);
        lblTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblTitolo);

        headerPanel.add(Box.createVerticalStrut(5));

        JLabel lblSottotitolo = new JLabel(sottotitolo);
        lblSottotitolo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSottotitolo.setForeground(new Color(255, 255, 255, 200));
        lblSottotitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(lblSottotitolo);

        panel.add(headerPanel, BorderLayout.NORTH);

        String[] colonne = {"ID", "Destinatario", "Tipo", "Data", "Titolo Annuncio"};
        DefaultTableModel model = new DefaultTableModel(colonne, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setRowHeight(32);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(themeColor.getRed(), themeColor.getGreen(), themeColor.getBlue(), 50));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(230, 230, 230));

        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(90);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.setBackground(themeColor);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 5; i++) {
            if (i != 1) table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        if (isRicevute) {
            tableRicevute = table;
            modelRicevute = model;
        } else {
            tableInviate = table;
            modelInviate = model;
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 12));
        buttonPanel.setBackground(new Color(250, 250, 250));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        JButton btnDettagli = createSmallButton("Dettagli", new Color(108, 117, 125));
        JButton btnAggiorna = createSmallButton("Aggiorna", new Color(108, 117, 125));

        btnDettagli.addActionListener(e -> visualizzaDettagli(isRicevute));
        btnAggiorna.addActionListener(e -> caricaConsegne());

        if (!isRicevute) {
            JButton btnModifica = createSmallButton("Modifica", themeColor);
            btnModifica.addActionListener(e -> modificaConsegna());
            buttonPanel.add(btnModifica);
        }

        buttonPanel.add(btnDettagli);
        buttonPanel.add(btnAggiorna);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { button.setBackground(color.darker()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { button.setBackground(color); }
        });
        return button;
    }

    private JButton createSmallButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { button.setBackground(color.darker()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { button.setBackground(color); }
        });
        return button;
    }

    private void caricaConsegne() {
        modelRicevute.setRowCount(0);
        modelInviate.setRowCount(0);
        
        List<Consegna> ricevute = controller.getConsegneDaRicevere(usernameUtente);
        List<Consegna> inviate = controller.getConsegneDaInviare(usernameUtente, annuncioController);
        
        if (ricevute != null) {
            for (Consegna c : ricevute) {
                try {
                    Annuncio ann = annuncioController.cercaPerId(c.getIdAnnuncio());
                    if (ann != null) {
                        Object[] row = {c.getIdConsegna(), c.getDestinatario(), c.getTipoConsegna(), 
                                      c.getData(), ann.getTitolo()};
                        modelRicevute.addRow(row);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        if (inviate != null) {
            for (Consegna c : inviate) {
                try {
                    Annuncio ann = annuncioController.cercaPerId(c.getIdAnnuncio());
                    if (ann != null) {
                        Object[] row = {c.getIdConsegna(), c.getDestinatario(), c.getTipoConsegna(), 
                                      c.getData(), ann.getTitolo()};
                        modelInviate.addRow(row);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void modificaConsegna() {
        int selectedRow = tableInviate.getSelectedRow();
        
        if (selectedRow == -1) {
            showMsg("Seleziona una consegna dalla tabella", "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idConsegna = (int) modelInviate.getValueAt(selectedRow, 0);
        Consegna consegna = controller.getConsegnaPerId(idConsegna);

        if (consegna == null) {
            showMsg("Consegna non trovata nel sistema", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Modifica Consegna", true);
        dialog.setSize(550, 750);
        dialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 193, 7));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel headerLabel = new JLabel("Modifica Dettagli Consegna");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        JTextField txtDestinatario = createTextField(consegna.getDestinatario());
        JTextArea txtNote = new JTextArea(consegna.getNoteConsegna(), 3, 20);
        txtNote.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtNote.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        JScrollPane scrollNote = new JScrollPane(txtNote);

        JComboBox<TipoConsegna> cmbTipo = new JComboBox<>(TipoConsegna.values());
        cmbTipo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cmbTipo.setSelectedItem(consegna.getTipoConsegna());
        
        JTextField txtData = createTextField(consegna.getData().toString());
        JTextField txtOraInizio = createTextField(consegna.getOraInizioPref() != null ? consegna.getOraInizioPref().toString() : "");
        JTextField txtOraFine = createTextField(consegna.getOraFinePref() != null ? consegna.getOraFinePref().toString() : "");
        JTextField txtSedeUni = createTextField(consegna.getSedeUniversitaria() != null ? consegna.getSedeUniversitaria() : "");
        JTextField txtIndirizzo = createTextField(consegna.getIndirizzo() != null ? consegna.getIndirizzo() : "");
        JTextField txtCivico = createTextField(consegna.getCivico() != null ? consegna.getCivico() : "");
        JTextField txtCorriere = createTextField(consegna.getCorriere() != null ? consegna.getCorriere() : "");
        JTextField txtTracking = createTextField(consegna.getTrackingNumber() != null ? consegna.getTrackingNumber() : "");

        JPanel campiDinamici = new JPanel(new GridBagLayout());
        campiDinamici.setBackground(Color.WHITE);
        
        cmbTipo.addActionListener(e -> {
            campiDinamici.removeAll();
            GridBagConstraints gbcDin = new GridBagConstraints();
            gbcDin.fill = GridBagConstraints.HORIZONTAL;
            gbcDin.insets = new Insets(5, 5, 5, 5);
            gbcDin.gridx = 0; gbcDin.gridy = 0;

            if (cmbTipo.getSelectedItem() == TipoConsegna.A_MANO) {
                campiDinamici.add(createLabel("Sede Universitaria*:"), gbcDin);
                gbcDin.gridx = 1;
                campiDinamici.add(txtSedeUni, gbcDin);
            } else if (cmbTipo.getSelectedItem() == TipoConsegna.SPEDIZIONE) {
                campiDinamici.add(createLabel("Indirizzo*:"), gbcDin);
                gbcDin.gridx = 1;
                campiDinamici.add(txtIndirizzo, gbcDin);
                
                gbcDin.gridx = 0; gbcDin.gridy = 1;
                campiDinamici.add(createLabel("Civico*:"), gbcDin);
                gbcDin.gridx = 1;
                campiDinamici.add(txtCivico, gbcDin);
                
                gbcDin.gridx = 0; gbcDin.gridy = 2;
                campiDinamici.add(createLabel("Corriere:"), gbcDin);
                gbcDin.gridx = 1;
                campiDinamici.add(txtCorriere, gbcDin);
                
                gbcDin.gridx = 0; gbcDin.gridy = 3;
                campiDinamici.add(createLabel("Tracking:"), gbcDin);
                gbcDin.gridx = 1;
                campiDinamici.add(txtTracking, gbcDin);
            }
            campiDinamici.revalidate();
            campiDinamici.repaint();
        });

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(createLabel("Destinatario*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDestinatario, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(createLabel("Tipo Consegna*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cmbTipo, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        formPanel.add(campiDinamici, gbc);
        gbc.gridwidth = 1;

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(createLabel("Data (yyyy-MM-dd)*:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtData, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(createLabel("Ora Inizio (HH:mm):"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtOraInizio, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(createLabel("Ora Fine (HH:mm):"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtOraFine, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(createLabel("Note:"), gbc);
        gbc.gridx = 1;
        formPanel.add(scrollNote, gbc);

        cmbTipo.setSelectedItem(consegna.getTipoConsegna());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));
        
        JButton btnSalva = createStyledButton("Salva", new Color(40, 167, 69));
        JButton btnAnnulla = createStyledButton("Annulla", new Color(108, 117, 125));
        
        buttonPanel.add(btnSalva);
        buttonPanel.add(btnAnnulla);

        btnSalva.addActionListener(e -> {
            String errore = controller.validaEModificaConsegna(
                idConsegna,
                txtDestinatario.getText(),
                txtData.getText(),
                txtOraInizio.getText(),
                txtOraFine.getText(),
                (TipoConsegna) cmbTipo.getSelectedItem(),
                txtSedeUni.getText(),
                txtIndirizzo.getText(),
                txtCivico.getText(),
                txtCorriere.getText(),
                txtTracking.getText(),
                txtNote.getText()
            );
            
            if (errore == null) {
                showMsg("Consegna modificata con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                caricaConsegne();
            } else {
                showMsg(errore, "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAnnulla.addActionListener(e -> dialog.dispose());

        mainPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private JTextField createTextField(String text) {
        JTextField field = new JTextField(text, 20);
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        return field;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }

    private void visualizzaDettagli(boolean isRicevute) {
        JTable table = isRicevute ? tableRicevute : tableInviate;
        DefaultTableModel model = isRicevute ? modelRicevute : modelInviate;
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            showMsg("Seleziona una consegna dalla tabella", "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idConsegna = (int) model.getValueAt(selectedRow, 0);
        Consegna consegna = controller.getConsegnaPerId(idConsegna);

        if (consegna == null) {
            showMsg("Consegna non trovata", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder dettagli = new StringBuilder();
        dettagli.append("ID Consegna: ").append(consegna.getIdConsegna()).append("\n");
        dettagli.append("Destinatario: ").append(consegna.getDestinatario()).append("\n");
        dettagli.append("Tipo: ").append(consegna.getTipoConsegna()).append("\n");
        dettagli.append("Data: ").append(consegna.getData()).append("\n");
        
        if (consegna.getOraInizioPref() != null) {
            dettagli.append("Ora Inizio: ").append(consegna.getOraInizioPref()).append("\n");
        }
        if (consegna.getOraFinePref() != null) {
            dettagli.append("Ora Fine: ").append(consegna.getOraFinePref()).append("\n");
        }
        if (consegna.getTipoConsegna() == TipoConsegna.A_MANO && consegna.getSedeUniversitaria() != null) {
            dettagli.append("Sede: ").append(consegna.getSedeUniversitaria()).append("\n");
        }
        if (consegna.getTipoConsegna() == TipoConsegna.SPEDIZIONE) {
            if (consegna.getIndirizzo() != null) {
                dettagli.append("Indirizzo: ").append(consegna.getIndirizzo()).append(" ").append(consegna.getCivico()).append("\n");
            }
            if (consegna.getCorriere() != null) {
                dettagli.append("Corriere: ").append(consegna.getCorriere()).append("\n");
            }
        }
        if (consegna.getNoteConsegna() != null && !consegna.getNoteConsegna().isEmpty()) {
            dettagli.append("\nNote:\n").append(consegna.getNoteConsegna());
        }

        JTextArea textArea = new JTextArea(dettagli.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Dettagli Consegna", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMsg(String msg, String title, int type) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel icon = new JLabel(type == JOptionPane.ERROR_MESSAGE ? "X" : type == JOptionPane.WARNING_MESSAGE ? "!" : "✓");
        icon.setFont(new Font("SansSerif", Font.BOLD, 40));
        icon.setForeground(type == JOptionPane.ERROR_MESSAGE ? new Color(220, 53, 69) : type == JOptionPane.WARNING_MESSAGE ? new Color(255, 193, 7) : new Color(40, 167, 69));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(icon);
        p.add(Box.createVerticalStrut(10));
        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 16));
        t.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(t);
        p.add(Box.createVerticalStrut(5));
        JLabel m = new JLabel(msg);
        m.setFont(new Font("SansSerif", Font.PLAIN, 13));
        m.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(m);
        JOptionPane.showMessageDialog(this, p, title, JOptionPane.PLAIN_MESSAGE);
    }
}
