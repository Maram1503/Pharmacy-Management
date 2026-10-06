package pharmacie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FenetreRapportStatistique extends JFrame {

    private InterfaceGestionnaire parent;
    private JTextField dateDebutField;
    private JTextField dateFinField;

    public FenetreRapportStatistique(InterfaceGestionnaire parent) {
        this.parent = parent;
        setTitle("Générer un Rapport Statistique");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

   
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Date de début (jj/mm/aaaa) :"), gbc);
        dateDebutField = new JTextField(12);
        gbc.gridx = 1;
        add(dateDebutField, gbc);


        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Date de fin (jj/mm/aaaa) :"), gbc);
        dateFinField = new JTextField(12);
        gbc.gridx = 1;
        add(dateFinField, gbc);

 
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JPanel panel = new JPanel(new FlowLayout());
        JButton btnGenerer = new JButton("Générer");
        JButton btnRetour = new JButton("Retour");

        btnGenerer.addActionListener(this::genererRapport);
        btnRetour.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });

        panel.add(btnGenerer);
        panel.add(btnRetour);
        add(panel, gbc);
    }

    private void genererRapport(ActionEvent e) {
        String debutStr = dateDebutField.getText().trim();
        String finStr = dateFinField.getText().trim();

        if (debutStr.isEmpty() || finStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir les deux dates.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate dateDebut, dateFin;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            dateDebut = LocalDate.parse(debutStr, formatter);
            dateFin = LocalDate.parse(finStr, formatter);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Format de date invalide.\nUtilisez jj/mm/aaaa.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dateDebut.isAfter(dateFin)) {
            JOptionPane.showMessageDialog(this, "La date de début ne peut pas être après la date de fin.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = """
            SELECT 
                COALESCE(SUM(v.quantite), 0) AS total_quantite,
                COALESCE(SUM(v.quantite * m.prix_unitaire), 0) AS chiffre_affaires
            FROM Vente v
            JOIN Medicament m ON v.id_medicament = m.id_medicament
            WHERE v.date_vente BETWEEN ? AND ?
            """;

        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(dateDebut));
            stmt.setDate(2, java.sql.Date.valueOf(dateFin));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int totalQuantite = rs.getInt("total_quantite");
                double chiffreAffaires = rs.getDouble("chiffre_affaires");

                String message = String.format(
                    "Période : %s → %s\n\n" +
                    "Nombre total de médicaments vendus : %d\n" +
                    "Chiffre d'affaires total : %.2f DT",
                    debutStr, finStr, totalQuantite, chiffreAffaires
                );

                JOptionPane.showMessageDialog(this, message, "Rapport Statistique", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la génération du rapport.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}