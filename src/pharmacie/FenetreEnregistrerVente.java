package pharmacie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

public class FenetreEnregistrerVente extends JFrame {

    private InterfacePharmacien parent;
    private String idPharmacienConnecte; // L'ID du pharmacien connecté

    private JTextField idVenteField;
    private JTextField quantiteField;
    private JTextField idClientField;
    private JTextField idMedicamentField;

    public FenetreEnregistrerVente(InterfacePharmacien parent, String idPharmacien) {
        this.parent = parent;
        this.idPharmacienConnecte = idPharmacien;

        setTitle("Enregistrer une Vente");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("ID Vente :"), gbc);
        idVenteField = new JTextField(15);
        gbc.gridx = 1;
        add(idVenteField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Quantité :"), gbc);
        quantiteField = new JTextField(15);
        gbc.gridx = 1;
        add(quantiteField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("ID Client :"), gbc);
        idClientField = new JTextField(15);
        gbc.gridx = 1;
        add(idClientField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("ID Médicament :"), gbc);
        idMedicamentField = new JTextField(15);
        gbc.gridx = 1;
        add(idMedicamentField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JPanel panelBoutons = new JPanel(new FlowLayout());
        JButton btnEnregistrer = new JButton("Enregistrer");
        JButton btnAnnuler = new JButton("Annuler");

        btnEnregistrer.addActionListener(this::enregistrerVente);
        btnAnnuler.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });

        panelBoutons.add(btnEnregistrer);
        panelBoutons.add(btnAnnuler);
        add(panelBoutons, gbc);
    }
    private void enregistrerVente(ActionEvent e) {
        String idVente = idVenteField.getText().trim();
        String quantiteStr = quantiteField.getText().trim();
        String idClient = idClientField.getText().trim();
        String idMedicament = idMedicamentField.getText().trim();

        if (idVente.isEmpty() || quantiteStr.isEmpty() || idClient.isEmpty() || idMedicament.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int quantite;
        try {
            quantite = Integer.parseInt(quantiteStr);
            if (quantite <= 0) {
                JOptionPane.showMessageDialog(this, "La quantité doit être un nombre positif.", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La quantité doit être un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!clientExiste(idClient)) {
            JOptionPane.showMessageDialog(this, "Le client avec ID '" + idClient + "' n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!medicamentExiste(idMedicament)) {
            JOptionPane.showMessageDialog(this, "Le médicament avec ID '" + idMedicament + "' n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            conn = Connexion.getConnexion();
            conn.setAutoCommit(false);

            MedicamentDAO medDao = new MedicamentDAO();
            Integer stockActuel = medDao.getStock(idMedicament);
            if (stockActuel == null || stockActuel < quantite) {
                JOptionPane.showMessageDialog(this, 
                    "Stock insuffisant !\nStock actuel : " + (stockActuel != null ? stockActuel : "inconnu"),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int nouveauStock = stockActuel - quantite;
            if (!medDao.updateStock(idMedicament, nouveauStock)) {
                throw new SQLException("Échec de la mise à jour du stock");
            }

            String sqlHist = "INSERT INTO stock_historique VALUES (?, ?, ?)";
            try (PreparedStatement stmtHist = conn.prepareStatement(sqlHist)) {
                stmtHist.setString(1, idMedicament);
                stmtHist.setInt(2, nouveauStock); // stock après vente
                stmtHist.setDate(3, Date.valueOf(LocalDate.now()));
                stmtHist.executeUpdate();
            }

            Vente vente = new Vente(idVente, quantite, LocalDate.now(), idMedicament, idPharmacienConnecte, idClient);
            VenteDAO venteDAO = new VenteDAO();
            if (!venteDAO.ajouterVente(vente)) {
                throw new SQLException("Échec de l'enregistrement de la vente");
            }

            conn.commit();
            JOptionPane.showMessageDialog(this, "Vente enregistrée avec succès !");
            this.dispose();
            parent.setVisible(true);

        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException exep) {
                    exep.printStackTrace();
                }
            }
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement de la vente.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException exe) {
                    exe.printStackTrace();
                }
            }
        }
    }

    private boolean clientExiste(String idClient) {
        String sql = "SELECT COUNT(*) FROM Client WHERE id_client = ?";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idClient);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean medicamentExiste(String idMedicament) {
        String sql = "SELECT COUNT(*) FROM Medicament WHERE id_medicament = ?";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idMedicament);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}