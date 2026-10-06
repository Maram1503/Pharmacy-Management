package pharmacie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class FenetreGererCommandes extends JFrame {

    private InterfaceGestionnaire parent;

    public FenetreGererCommandes(InterfaceGestionnaire parent) {
        this.parent = parent;
        setTitle("Gérer les Commandes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JLabel titre = new JLabel("Historique des commandes", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        add(titre, BorderLayout.NORTH);

      
        String[] colonnes = {"ID Commande", "ID Médicament", "ID Gestionnaire", "Quantité", "Date"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

      
        chargerCommandes(model);


        JPanel panelBoutons = new JPanel(new FlowLayout());
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });
        panelBoutons.add(btnRetour);
        add(panelBoutons, BorderLayout.SOUTH);
    }

    private void chargerCommandes(DefaultTableModel model) {
        String sql = "SELECT * FROM Commande ORDER BY date_commande DESC";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            while (rs.next()) {
                String idCommande = rs.getString("ID_COMMANDE");
                String idMedicament = rs.getString("ID_MEDICAMENT");
                String idGestionnaire = rs.getString("ID_GESTIONNAIRE");
                int quantite = rs.getInt("QUANTITE");
                String date = rs.getDate("DATE_COMMANDE").toLocalDate().format(formatter);

                model.addRow(new Object[]{idCommande, idMedicament, idGestionnaire, quantite, date});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des commandes.");
        }
    }
}
