package GUI;

import Controller.AnnuncioController;
import Controller.CategoriaController;
import entità.Annuncio;
import entità.Categoria;
import entity.enums.TipoAnnuncio;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AnnuncioFrame extends JFrame 
{
    private String nomeUtente;
    private AnnuncioController controllerAnnuncio;
    private CategoriaController controllerCategoria;
    private JTable tabella;
    private DefaultTableModel modelloTabella;
    private JComboBox<Annuncio> comboModifica, comboElimina;
    private JComboBox<String> comboFiltroCategorie;
    private List<Categoria> listaCategorie;

    public AnnuncioFrame(String nomeUtente) {
        this.nomeUtente = nomeUtente;
        this.controllerAnnuncio = new AnnuncioController();
        this.controllerCategoria = new CategoriaController();

        setTitle("Gestione Annunci - Unina Swap");
        setSize(1000, 700);
        setMinimumSize(new Dimension(950, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(240, 242, 245));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));

        JLabel lblTitle = new JLabel("Gestione Annunci");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));

        JButton btnHome = createStyledButton("Indietro", new Color(108, 117, 125));
        btnHome.addActionListener(e -> {
            dispose();
            new MenuFrame(nomeUtente);
        });

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnHome, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabs.setBackground(Color.WHITE);
        tabs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tabs.addTab("Visualizza", creaTabVisualizza());
        tabs.addTab("Nuovo", creaTabNuovo());
        tabs.addTab("Modifica", creaTabModifica());
        tabs.addTab("Elimina", creaTabElimina());

        mainPanel.add(tabs, BorderLayout.CENTER);
        add(mainPanel);
        caricaAnnunci();
        setVisible(true);
    }

    private JPanel creaTabVisualizza() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroPanel.setBackground(Color.WHITE);

        comboFiltroCategorie = new JComboBox<>();
        comboFiltroCategorie.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboFiltroCategorie.addItem("Tutti Gli Annunci");

        try {
            listaCategorie = controllerCategoria.listCategorie();
            for (Categoria c : listaCategorie) {
                comboFiltroCategorie.addItem(c.getNomeCategoria());
            }
        } catch (Exception e) {
            listaCategorie = new ArrayList<>();
        }

        comboFiltroCategorie.addActionListener(e -> filtraAnnunciPerCategoria());
        filtroPanel.add(new JLabel("Categoria:"));
        filtroPanel.add(comboFiltroCategorie);
        panel.add(filtroPanel, BorderLayout.NORTH);

        modelloTabella = new DefaultTableModel(new String[]{"Titolo", "Descrizione", "Tipo", "Prezzo Min", "Prezzo Vend", "Oggetti", "Stato", "Categoria"}, 0) 
        {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabella = new JTable(modelloTabella);
        tabella.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabella.setRowHeight(30);
        tabella.setSelectionBackground(new Color(0, 102, 204, 50));
        tabella.setGridColor(new Color(230, 230, 230));
        tabella.getColumnModel().getColumn(0).setPreferredWidth(200);
        tabella.getColumnModel().getColumn(1).setPreferredWidth(300);
        tabella.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabella.getColumnModel().getColumn(3).setPreferredWidth(70);
        tabella.getColumnModel().getColumn(4).setPreferredWidth(70);
        tabella.getColumnModel().getColumn(5).setPreferredWidth(150);
        tabella.getColumnModel().getColumn(6).setPreferredWidth(100);
        tabella.getColumnModel().getColumn(7).setPreferredWidth(100);

        JTableHeader header = tabella.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        refreshTabella();

        JScrollPane scrollPane = new JScrollPane(tabella);
        scrollPane.setBorder(new LineBorder(new Color(220, 220, 220), 1));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel creaTabNuovo() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);

        JTextField campoTitolo = createTextField("");
        JTextArea campoDescrizione = new JTextArea(4, 20);
        campoDescrizione.setFont(new Font("SansSerif", Font.PLAIN, 13));
        campoDescrizione.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        JScrollPane scrollDesc = new JScrollPane(campoDescrizione);

        JComboBox<TipoAnnuncio> comboTipo = new JComboBox<>(TipoAnnuncio.values());
        comboTipo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JTextField campoPrezzoMin = createTextField("");
        JTextField campoPrezzoVendita = createTextField("");
        JTextField campoOggetti = createTextField("");
        JComboBox<Categoria> comboCategoria = new JComboBox<>();
        comboCategoria.setFont(new Font("SansSerif", Font.PLAIN, 13));
        
        try {
            for (Categoria c : controllerCategoria.listCategorie()) 
            	comboCategoria.addItem(c);
        } catch (Exception e) {}

        comboTipo.addActionListener(e -> aggiornaAbilitazioneCampi(comboTipo, campoPrezzoMin, campoPrezzoVendita, campoOggetti));
        campoPrezzoMin.setEnabled(true);
        campoPrezzoVendita.setEnabled(true);
        campoOggetti.setEnabled(false);

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(createLabel("Titolo*:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(campoTitolo, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(createLabel("Descrizione*:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(scrollDesc, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(createLabel("Tipo*:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(comboTipo, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(createLabel("Prezzo Min:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(campoPrezzoMin, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(createLabel("Prezzo Vendita:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(campoPrezzoVendita, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(createLabel("Oggetti:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(campoOggetti, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(createLabel("Categoria*:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(comboCategoria, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 3;
        JButton btnCrea = createStyledButton("Crea Annuncio", new Color(40, 167, 69));
        btnCrea.setPreferredSize(new Dimension(200, 40));
        formPanel.add(btnCrea, gbc);

        btnCrea.addActionListener(e -> creaAnnuncio(campoTitolo, campoDescrizione, comboTipo, campoPrezzoMin, campoPrezzoVendita, campoOggetti, comboCategoria));

        mainPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel creaTabModifica() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);

        comboModifica = new JComboBox<>();
        comboModifica.setFont(new Font("SansSerif", Font.PLAIN, 13));
        comboModifica.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Annuncio) {
                    Annuncio a = (Annuncio) value;
                    setText(a.getTitolo() + " (ID: " + a.getIdAnnuncio() + ")");
                }
                return this;
            }
        });
        JTextField campoTitolo = createTextField("");
        JTextArea campoDescrizione = new JTextArea(4, 20);
        campoDescrizione.setFont(new Font("SansSerif", Font.PLAIN, 13));
        campoDescrizione.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        JScrollPane scrollDesc = new JScrollPane(campoDescrizione);
        JComboBox<TipoAnnuncio> comboTipo = new JComboBox<>(TipoAnnuncio.values());
        comboTipo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JTextField campoPrezzoMin = createTextField("");
        JTextField campoPrezzoVendita = createTextField("");
        JTextField campoOggetti = createTextField("");
        JCheckBox checkDisponibile = new JCheckBox("Disponibile");
        checkDisponibile.setFont(new Font("SansSerif", Font.PLAIN, 13));

        comboModifica.addActionListener(e -> caricaDatiModifica(comboModifica, campoTitolo, campoDescrizione, comboTipo, campoPrezzoMin, campoPrezzoVendita, campoOggetti, checkDisponibile));
        comboTipo.addActionListener(e -> aggiornaAbilitazioneCampi(comboTipo, campoPrezzoMin, campoPrezzoVendita, campoOggetti));

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(createLabel("Annuncio:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(comboModifica, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(createLabel("Titolo*:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(campoTitolo, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(createLabel("Descrizione*:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(scrollDesc, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(createLabel("Tipo*:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(comboTipo, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(createLabel("Prezzo Min:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(campoPrezzoMin, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(createLabel("Prezzo Vend:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(campoPrezzoVendita, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(createLabel("Oggetti:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(campoOggetti, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 3;
        formPanel.add(checkDisponibile, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 3;
        JButton btnSalva = createStyledButton("Salva", new Color(0, 102, 204));
        btnSalva.setPreferredSize(new Dimension(200, 40));
        formPanel.add(btnSalva, gbc);

        btnSalva.addActionListener(e -> salvaModifica(comboModifica, campoTitolo, campoDescrizione, comboTipo, campoPrezzoMin, campoPrezzoVendita, campoOggetti, checkDisponibile));

        mainPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel creaTabElimina() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        comboElimina = new JComboBox<>();
        comboElimina.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboElimina.setPreferredSize(new Dimension(400, 35));
        comboElimina.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Annuncio) setText(((Annuncio) value).getTitolo() + " (ID: " + ((Annuncio) value).getIdAnnuncio() + ")");
                return this;
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createLabel("Annuncio:"), gbc);
        gbc.gridy = 1;
        panel.add(comboElimina, gbc);
        gbc.gridy = 2;
        JButton btnElimina = createStyledButton("Elimina", new Color(220, 53, 69));
        btnElimina.setPreferredSize(new Dimension(200, 40));
        btnElimina.addActionListener(e -> eliminaAnnuncio());
        panel.add(btnElimina, gbc);
        return panel;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(color.darker()); }
            public void mouseExited(java.awt.event.MouseEvent e) { btn.setBackground(color); }
        });
        return btn;
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

    private void aggiornaAbilitazioneCampi(JComboBox<TipoAnnuncio> tipo, JTextField pMin, JTextField pVend, JTextField ogg) {
        TipoAnnuncio t = (TipoAnnuncio) tipo.getSelectedItem();
        if (t == TipoAnnuncio.VENDITA) {
            pMin.setEnabled(true); pVend.setEnabled(true); ogg.setEnabled(false); ogg.setText("");
        } else if (t == TipoAnnuncio.SCAMBIO) {
            pMin.setEnabled(false); pVend.setEnabled(false); ogg.setEnabled(true); pMin.setText(""); pVend.setText("");
        } else {
            pMin.setEnabled(false); pVend.setEnabled(false); ogg.setEnabled(false);
            pMin.setText(""); pVend.setText(""); ogg.setText("");
        }
    }

    private void caricaDatiModifica(JComboBox<Annuncio> combo, JTextField tit, JTextArea desc, JComboBox<TipoAnnuncio> tipo, JTextField pMin, JTextField pVend, JTextField ogg, JCheckBox disp) {
        Annuncio a = (Annuncio) combo.getSelectedItem();
        if (a != null) {
            tit.setText(a.getTitolo());
            desc.setText(a.getDescrizione());
            tipo.setSelectedItem(a.getTipoAnnuncio());
            pMin.setText(String.valueOf(a.getPrezzoOffertaMinima()));
            pVend.setText(String.valueOf(a.getPrezzoVendita()));
            ogg.setText(a.getOggettiDaScambiare());
            disp.setSelected(a.isDisponibile());
            aggiornaAbilitazioneCampi(tipo, pMin, pVend, ogg);
        }
    }

    private void creaAnnuncio(JTextField tit, JTextArea desc, JComboBox<TipoAnnuncio> tipo, JTextField pMin, JTextField pVend, JTextField ogg, JComboBox<Categoria> cat) {
        try {
            String titolo = tit.getText().trim();
            String descrizione = desc.getText().trim();
            if (titolo.isEmpty() || descrizione.isEmpty()) { showMsg("Compila tutti i campi", "Errore", 0); return; }
            Categoria categoria = (Categoria) cat.getSelectedItem();
            if (categoria == null) { showMsg("Seleziona categoria", "Errore", 0); return; }
            
            TipoAnnuncio tipoAnn = (TipoAnnuncio) tipo.getSelectedItem();
            double pm = 0, pv = 0;
            String oggetti = "";
            
            if (tipoAnn == TipoAnnuncio.VENDITA) {
                if (pMin.getText().trim().isEmpty() || pVend.getText().trim().isEmpty()) {
                    showMsg("Inserisci prezzi", "Errore", 0); return;
                }
                try {
                    pm = Double.parseDouble(pMin.getText().trim());
                    pv = Double.parseDouble(pVend.getText().trim());
                } catch (NumberFormatException ex) { showMsg("Prezzi non validi", "Errore", 0); return; }
            } else if (tipoAnn == TipoAnnuncio.SCAMBIO) {
                oggetti = ogg.getText().trim();
                if (oggetti.isEmpty()) { showMsg("Inserisci oggetti", "Errore", 0); return; }
            }
            
            if (controllerAnnuncio.creaNuovoAnnuncio(titolo, descrizione, tipoAnn, oggetti, pm, pv, nomeUtente, categoria.getNomeCategoria())) {
                showMsg("Creato!", "OK", 1);
                refreshTabella(); caricaAnnunci();
                tit.setText(""); desc.setText(""); pMin.setText(""); pVend.setText(""); ogg.setText("");
            }
        } catch (Exception ex) { showMsg(ex.getMessage(), "Errore", 0); }
    }

    private void salvaModifica(JComboBox<Annuncio> combo, JTextField tit, JTextArea desc, JComboBox<TipoAnnuncio> tipo, JTextField pMin, JTextField pVend, JTextField ogg, JCheckBox disp) {
        Annuncio a = (Annuncio) combo.getSelectedItem();
        if (a == null) { showMsg("Seleziona", "Errore", 2); return; }
        
        try {
            String titolo = tit.getText().trim();
            String descrizione = desc.getText().trim();
            if (titolo.isEmpty() || descrizione.isEmpty()) { showMsg("Compila campi", "Errore", 0); return; }
            
            TipoAnnuncio tipoAnn = (TipoAnnuncio) tipo.getSelectedItem();
            boolean disponibile = disp.isSelected();
            double pm = 0, pv = 0;
            String oggetti = "";
            
            if (tipoAnn == TipoAnnuncio.VENDITA) {
                if (pMin.getText().trim().isEmpty() || pVend.getText().trim().isEmpty()) {
                    showMsg("Inserisci prezzi", "Errore", 0); return;
                }
                try {
                    pm = Double.parseDouble(pMin.getText().trim());
                    pv = Double.parseDouble(pVend.getText().trim());
                } catch (NumberFormatException ex) { showMsg("Prezzi non validi", "Errore", 0); return; }
            } else if (tipoAnn == TipoAnnuncio.SCAMBIO) {
                oggetti = ogg.getText().trim();
                if (oggetti.isEmpty()) { showMsg("Inserisci oggetti", "Errore", 0); return; }
            }
            
            if (controllerAnnuncio.modificaAnnuncio(a, titolo, descrizione, tipoAnn, oggetti, pm, pv, disponibile)) {
                showMsg("Modificato!", "OK", 1);
                refreshTabella(); caricaAnnunci();
            }
        } catch (Exception ex) { showMsg(ex.getMessage(), "Errore", 0); }
    }

    private void eliminaAnnuncio() {
        Annuncio a = (Annuncio) comboElimina.getSelectedItem();
        if (a == null) { showMsg("Seleziona", "Errore", 2); return; }
        
        int conferma = JOptionPane.showConfirmDialog(this, "Eliminare '" + a.getTitolo() + "'?", "Conferma", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (conferma != JOptionPane.YES_OPTION) return;
        
        try {
            if (controllerAnnuncio.eliminaAnnuncio(a.getIdAnnuncio())) {
                showMsg("Eliminato!", "OK", 1);
                refreshTabella(); caricaAnnunci();
            }
        } catch (Exception ex) { showMsg(ex.getMessage(), "Errore", 0); }
    }

    private void refreshTabella() {
        if (comboFiltroCategorie != null) { 
            filtraAnnunciPerCategoria(); 
            return; 
        }
        modelloTabella.setRowCount(0);
        try {
            for (Annuncio a : controllerAnnuncio.mostraTuttiAnnunci()) {
                Categoria c = controllerCategoria.searchByID(a.getIdCategoria());
                modelloTabella.addRow(new Object[]{
                    a.getTitolo(), 
                    a.getDescrizione(), 
                    a.getTipoAnnuncio(), 
                    a.getPrezzoOffertaMinima(), 
                    a.getPrezzoVendita(), 
                    a.getOggettiDaScambiare(), 
                    a.isDisponibile() ? "DISPONIBILE" : "NON DISPONIBILE", 
                    c != null ? c.getNomeCategoria() : "?"
                });
            }
        } catch (Exception e) {}
    }

    private void caricaAnnunci() {
        if (comboModifica != null) comboModifica.removeAllItems();
        if (comboElimina != null) comboElimina.removeAllItems();
        try {
            List<Annuncio> tutti = controllerAnnuncio.mostraTuttiAnnunci();
            if (tutti != null) {
                for (Annuncio a : tutti) {
                    if (nomeUtente.equals(a.getUsername())) {
                        if (comboModifica != null) comboModifica.addItem(a);
                        if (comboElimina != null) comboElimina.addItem(a);
                    }
                }
            }
        } catch (Exception e) {}
    }

    private void filtraAnnunciPerCategoria() {
        String selezionata = (String) comboFiltroCategorie.getSelectedItem();
        modelloTabella.setRowCount(0);
        try {
            List<Annuncio> tutti = controllerAnnuncio.mostraTuttiAnnunci();
            for (Annuncio a : tutti) {
                Categoria c = controllerCategoria.searchByID(a.getIdCategoria());
                String nomeCat = (c != null) ? c.getNomeCategoria() : "?";
                if ("Tutti Gli Annunci".equals(selezionata) || nomeCat.equals(selezionata)) {
                    modelloTabella.addRow(new Object[]{
                        a.getTitolo(), 
                        a.getDescrizione(), 
                        a.getTipoAnnuncio(),
                        a.getPrezzoOffertaMinima(), 
                        a.getPrezzoVendita(), 
                        a.getOggettiDaScambiare(),
                        a.isDisponibile() ? "DISPONIBILE" : "NON DISPONIBILE",
                        nomeCat
                    });
                }
            }
        } catch (Exception e) {}
    }

    private void showMsg(String msg, String title, int type) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel icon = new JLabel(type == 0 ? "✖" : type == 2 ? "⚠" : "✓");
        icon.setFont(new Font("SansSerif", Font.BOLD, 40));
        icon.setForeground(type == 0 ? new Color(220, 53, 69) : type == 2 ? new Color(255, 193, 7) : new Color(40, 167, 69));
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
