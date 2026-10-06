package pharmacie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class FenetreStockHistorique extends JFrame {

    private InterfaceGestionnaire parent;

    public FenetreStockHistorique(InterfaceGestionnaire parent) {
        this.parent = parent;
        setTitle("Historique du Stock");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Toutes les modifications de stock", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        add(titre, BorderLayout.NORTH);

        String[] colonnes = {"ID Médicament", "Stock après mise à jour", "Date"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        chargerHistorique(model);

        JPanel panelBoutons = new JPanel(new FlowLayout());
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });
        panelBoutons.add(btnRetour);
        add(panelBoutons, BorderLayout.SOUTH);
    }

    private void chargerHistorique(DefaultTableModel model) {
        String sql = "SELECT id_medicament, quantite, date_stock FROM stock_historique ORDER BY date_stock DESC";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            while (rs.next()) {
                String idMed = rs.getString("id_medicament");
                int stock = rs.getInt("quantite");
                String date = rs.getDate("date_stock").toLocalDate().format(formatter);

                model.addRow(new Object[]{idMed, stock, date});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement de l'historique.");
        }
    }
}
