package GUI;

import Controller.OffertaController;
import Controller.AnnuncioController;
import Controller.CategoriaController;
import entità.Categoria;
import entità.Offerta;
import entità.Annuncio;
import entity.enums.TipoAnnuncio;
import entity.enums.StatoOfferta;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class StatisticheFrame extends JFrame 
{
    private OffertaController offertaController;
    private AnnuncioController annuncioController;
    private JComboBox<String> comboFiltroCategorie;
    private List<Categoria> listaCategorie;

    public StatisticheFrame(String username) 
    {
        this.offertaController = new OffertaController();
        this.annuncioController = new AnnuncioController();
        
        setTitle("Report Statistiche - Unina Swap");
        setSize(1100, 700);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(240, 242, 245));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));

        JLabel lblTitle = new JLabel("Report Statistiche");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(new Color(111, 66, 193));

        JButton btnIndietro = createStyledButton("Indietro", new Color(108, 117, 125));
        btnIndietro.addActionListener(e -> { dispose(); new MenuFrame(username); });

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnIndietro, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Split panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(380);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(null);

        splitPane.setLeftComponent(creaReportPanel());
        splitPane.setRightComponent(creaGraficoPanel());

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(new Color(240, 242, 245));
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        centerWrapper.add(splitPane, BorderLayout.CENTER);

        mainPanel.add(centerWrapper, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    private JPanel creaReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(111, 66, 193));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        JLabel lblHeader = new JLabel("📊 Report Dettagliato");
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblHeader.setForeground(Color.WHITE);
        
        JLabel lblSubtitle = new JLabel("Analisi completa delle offerte");
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSubtitle.setForeground(new Color(230, 230, 255));
        
        JPanel headerContent = new JPanel(new GridLayout(2, 1, 0, 5));
        headerContent.setOpaque(false);
        headerContent.add(lblHeader);
        headerContent.add(lblSubtitle);
        
        headerPanel.add(headerContent, BorderLayout.WEST);
        panel.add(headerPanel, BorderLayout.NORTH);

        Map<TipoAnnuncio, Integer> offertePerTipo = new HashMap<>();
        Map<TipoAnnuncio, Integer> accettatePerTipo = new HashMap<>();
        Map<TipoAnnuncio, Double> sommaVendite = new HashMap<>();
        Map<TipoAnnuncio, Integer> contaVendite = new HashMap<>();
        Map<TipoAnnuncio, Double> minVendite = new HashMap<>();
        Map<TipoAnnuncio, Double> maxVendite = new HashMap<>();

        for (TipoAnnuncio tipo : TipoAnnuncio.values()) {
            offertePerTipo.put(tipo, 0);
            accettatePerTipo.put(tipo, 0);
            sommaVendite.put(tipo, 0.0);
            contaVendite.put(tipo, 0);
            minVendite.put(tipo, Double.MAX_VALUE);
            maxVendite.put(tipo, 0.0);
        }

        try {
            List<Annuncio> tuttiAnnunci = annuncioController.mostraTuttiAnnunci();
            for (Annuncio ann : tuttiAnnunci) {
                List<Offerta> offerte = offertaController.getOffertePerAnnuncio(ann.getIdAnnuncio());
                for (Offerta off : offerte) {
                    TipoAnnuncio tipo = off.getTipoOfferta();
                    offertePerTipo.put(tipo, offertePerTipo.get(tipo) + 1);

                    if (off.getStatoOfferta() == StatoOfferta.ACCETTATA) {
                        accettatePerTipo.put(tipo, accettatePerTipo.get(tipo) + 1);
                        
                        if (tipo == TipoAnnuncio.VENDITA && off.getPrezzoProposto() > 0) {
                            double prezzo = off.getPrezzoProposto();
                            sommaVendite.put(tipo, sommaVendite.get(tipo) + prezzo);
                            contaVendite.put(tipo, contaVendite.get(tipo) + 1);
                            minVendite.put(tipo, Math.min(minVendite.get(tipo), prezzo));
                            maxVendite.put(tipo, Math.max(maxVendite.get(tipo), prezzo));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(248, 249, 250));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (TipoAnnuncio tipo : TipoAnnuncio.values()) {
            JPanel cardPanel = createStatCard(tipo, offertePerTipo, accettatePerTipo, 
                                               sommaVendite, contaVendite, minVendite, maxVendite);
            contentPanel.add(cardPanel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(new Color(248, 249, 250));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatCard(TipoAnnuncio tipo, Map<TipoAnnuncio, Integer> offertePerTipo,
                                   Map<TipoAnnuncio, Integer> accettatePerTipo,
                                   Map<TipoAnnuncio, Double> sommaVendite,
                                   Map<TipoAnnuncio, Integer> contaVendite,
                                   Map<TipoAnnuncio, Double> minVendite,
                                   Map<TipoAnnuncio, Double> maxVendite) {
        
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Icona e titolo
        Color iconColor;
        String emoji;
        if (tipo == TipoAnnuncio.VENDITA) {
            iconColor = new Color(220, 53, 69);
            emoji = "💰";
        } else if (tipo == TipoAnnuncio.REGALO) {
            iconColor = new Color(40, 167, 69);
            emoji = "🎁";
        } else {
            iconColor = new Color(0, 123, 255);
            emoji = "🔄";
        }

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        
        JLabel iconLabel = new JLabel(emoji);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 32));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
        
        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 5));
        titlePanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(tipo.toString());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(new Color(33, 37, 41));
        
        int totali = offertePerTipo.get(tipo);
        int accettate = accettatePerTipo.get(tipo);
        double percentuale = totali > 0 ? (accettate * 100.0 / totali) : 0;
        
        JLabel subtitleLabel = new JLabel(String.format("Tasso accettazione: %.1f%%", percentuale));
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(108, 117, 125));
        
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);
        
        leftPanel.add(iconLabel, BorderLayout.WEST);
        leftPanel.add(titlePanel, BorderLayout.CENTER);

        // Statistiche
        JPanel statsPanel = new JPanel(new GridLayout(0, 2, 20, 12));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        addStatItem(statsPanel, "Offerte totali", String.valueOf(totali), new Color(111, 66, 193));
        addStatItem(statsPanel, "Offerte accettate", String.valueOf(accettate), new Color(40, 167, 69));

        if (tipo == TipoAnnuncio.VENDITA && contaVendite.get(tipo) > 0) {
            double media = sommaVendite.get(tipo) / contaVendite.get(tipo);
            addStatItem(statsPanel, "Prezzo medio", String.format("€%.2f", media), new Color(0, 123, 255));
            addStatItem(statsPanel, "Prezzo minimo", String.format("€%.2f", minVendite.get(tipo)), new Color(253, 126, 20));
            addStatItem(statsPanel, "Prezzo massimo", String.format("€%.2f", maxVendite.get(tipo)), new Color(220, 53, 69));
        }

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.add(leftPanel, BorderLayout.NORTH);
        mainPanel.add(statsPanel, BorderLayout.CENTER);

        card.add(mainPanel, BorderLayout.CENTER);
        return card;
    }

    private void addStatItem(JPanel parent, String label, String value, Color color) {
        JPanel item = new JPanel(new BorderLayout(8, 0));
        item.setOpaque(false);

        JPanel colorBar = new JPanel();
        colorBar.setBackground(color);
        colorBar.setPreferredSize(new Dimension(4, 40));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textPanel.setOpaque(false);
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblValue.setForeground(new Color(33, 37, 41));
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblLabel.setForeground(new Color(108, 117, 125));
        
        textPanel.add(lblValue);
        textPanel.add(lblLabel);
        
        item.add(colorBar, BorderLayout.WEST);
        item.add(textPanel, BorderLayout.CENTER);
        
        parent.add(item);
    }

    private JPanel creaGraficoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(111, 66, 193));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        JLabel lblHeader = new JLabel("📈 Grafico Offerte");
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblHeader.setForeground(Color.WHITE);
        
        JLabel lblSubtitle = new JLabel("Visualizzazione comparativa");
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSubtitle.setForeground(new Color(230, 230, 255));
        
        JPanel headerContent = new JPanel(new GridLayout(2, 1, 0, 5));
        headerContent.setOpaque(false);
        headerContent.add(lblHeader);
        headerContent.add(lblSubtitle);
        
        headerPanel.add(headerContent, BorderLayout.WEST);
        panel.add(headerPanel, BorderLayout.NORTH);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            Map<TipoAnnuncio, Integer> offerteInviate = new HashMap<>();
            Map<TipoAnnuncio, Integer> offerteAccettate = new HashMap<>();

            for (TipoAnnuncio tipo : TipoAnnuncio.values()) {
                offerteInviate.put(tipo, 0);
                offerteAccettate.put(tipo, 0);
            }

            List<Annuncio> tuttiAnnunci = annuncioController.mostraTuttiAnnunci();
            for (Annuncio ann : tuttiAnnunci) {
                List<Offerta> offerte = offertaController.getOffertePerAnnuncio(ann.getIdAnnuncio());
                for (Offerta off : offerte) {
                    TipoAnnuncio tipo = off.getTipoOfferta();
                    offerteInviate.put(tipo, offerteInviate.get(tipo) + 1);
                    if (off.getStatoOfferta() == StatoOfferta.ACCETTATA) {
                        offerteAccettate.put(tipo, offerteAccettate.get(tipo) + 1);
                    }
                }
            }

            for (TipoAnnuncio tipo : TipoAnnuncio.values()) {
                dataset.addValue(offerteInviate.get(tipo), "Offerte Inviate", tipo.toString());
                dataset.addValue(offerteAccettate.get(tipo), "Offerte Accettate", tipo.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Creazione e personalizzazione grafico con antialiasing
        JFreeChart chart = ChartFactory.createBarChart(
            "Offerte per Tipologia",
            "Tipo Annuncio",
            "Numero Offerte",
            dataset,
            PlotOrientation.VERTICAL,
            true,  // mostra legenda
            false,
            false
        );
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 18));
        chart.setPadding(new RectangleInsets(10, 10, 10, 10));
        chart.addSubtitle(new TextTitle("Dati aggiornati", new Font("SansSerif", Font.PLAIN, 12)));
        chart.setAntiAlias(true);
        chart.setTextAntiAlias(true);

        // Area centrale del grafico
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(200, 200, 200));
        plot.setOutlineVisible(false);

        // Stile barre moderne
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(188, 106, 213, 230)); // viola Offerte Inviate
        renderer.setSeriesPaint(1, new Color(66, 135, 245, 210));  // azzurro Offerte Accettate
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.15);
        renderer.setMaximumBarWidth(0.13);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBaseItemLabelFont(new Font("SansSerif", Font.BOLD, 14));


        // Etichette e assi
        plot.getDomainAxis().setLabelFont(new Font("SansSerif", Font.BOLD, 14));
        plot.getDomainAxis().setTickLabelFont(new Font("SansSerif", Font.PLAIN, 13));
        plot.getRangeAxis().setLabelFont(new Font("SansSerif", Font.BOLD, 14));
        plot.getRangeAxis().setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));

        // Legenda
        if (chart.getLegend() != null) {
            chart.getLegend().setPosition(RectangleEdge.TOP);
            chart.getLegend().setItemFont(new Font("SansSerif", Font.BOLD, 14));
        }

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chartPanel.setPreferredSize(new Dimension(600, 500));
        chartPanel.setMinimumSize(new Dimension(400, 300));
        chartPanel.setMaximumDrawWidth(2000);
        chartPanel.setMaximumDrawHeight(2000);
        chartPanel.setMinimumDrawWidth(400);
        chartPanel.setMinimumDrawHeight(300);
        panel.add(chartPanel, BorderLayout.CENTER);

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
}