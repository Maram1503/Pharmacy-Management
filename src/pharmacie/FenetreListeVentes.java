package pharmacie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class FenetreListeVentes extends JFrame {

    private InterfacePharmacien parent; // Pour revenir

    public FenetreListeVentes(InterfacePharmacien parent) {
        this.parent = parent;
        setTitle("Toutes les Ventes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

      
        JLabel titre = new JLabel("Contenu complet de la table Vente", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        add(titre, BorderLayout.NORTH);

   
        String[] colonnes = {"ID Vente", "Quantité", "Date", "ID Médicament", "ID Pharmacien", "ID Client"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        chargerVentes(model);

  
        JPanel panelBoutons = new JPanel(new FlowLayout());
        JButton btnRetour = new JButton("Retour");
        JButton btnEnregistrer = new JButton("Enregistrer une vente");

        btnRetour.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true); 
        });

        btnEnregistrer.addActionListener(e -> {
            this.dispose();
            new FenetreEnregistrerVente(parent, parent.getIdPharmacien()).setVisible(true);
        });

        panelBoutons.add(btnRetour);
        panelBoutons.add(btnEnregistrer);
        add(panelBoutons, BorderLayout.SOUTH);
    }

    private void chargerVentes(DefaultTableModel model) {
        String sql = "SELECT * FROM Vente ORDER BY date_vente DESC";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            while (rs.next()) {
                String idVente = rs.getString("id_vente");
                int quantite = rs.getInt("quantite");
                String date = rs.getDate("date_vente").toLocalDate().format(formatter);
                String idMed = rs.getString("id_medicament");
                String idPharm = rs.getString("id_pharmacien");
                String idClient = rs.getString("id_client");

                model.addRow(new Object[]{idVente, quantite, date, idMed, idPharm, idClient});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des ventes.");
        }
    }
}