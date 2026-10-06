package pharmacie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FenetreStockCritique extends JFrame {

    private InterfaceGestionnaire parent;

    public FenetreStockCritique(InterfaceGestionnaire parent) {
        this.parent = parent;
        setTitle("Stock Critique (≤ 10)");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Médicaments en stock critique (stock ≤ 10)", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        add(titre, BorderLayout.NORTH);

     
        String[] colonnes = {"ID", "Nom", "Dosage", "Stock", "Prix unitaire"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        chargerStockCritique(model);

       
        JPanel panelBoutons = new JPanel(new FlowLayout());
        JButton btnRetour = new JButton("Retour");
        JButton btnNotifier = new JButton("Envoyer notification");

        btnRetour.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });

        btnNotifier.addActionListener(e -> {
            this.dispose();
            new FenetreEnvoyerNotification(parent).setVisible(true);
        });

        panelBoutons.add(btnRetour);
        panelBoutons.add(btnNotifier);
        add(panelBoutons, BorderLayout.SOUTH);
    }

    private void chargerStockCritique(DefaultTableModel model) {
        String sql = "SELECT * FROM Medicament WHERE stock <= 10 ORDER BY stock";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Object[] ligne = {
                    rs.getString("id_medicament"),
                    rs.getString("nom"),
                    rs.getDouble("dosage"),
                    rs.getInt("stock"),
                    String.format("%.2f DA", rs.getDouble("prix_unitaire"))
                };
                model.addRow(ligne);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement du stock critique.");
        }
    }
}