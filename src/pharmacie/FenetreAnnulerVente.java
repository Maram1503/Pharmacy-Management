package pharmacie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FenetreAnnulerVente extends JFrame {

    private InterfacePharmacien parent;
    private JTextField idVenteField;

    public FenetreAnnulerVente(InterfacePharmacien parent) {
        this.parent = parent;
        setTitle("Annuler une Vente");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 220);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("ID Vente :"), gbc);
        idVenteField = new JTextField(20);
        gbc.gridx = 1;
        add(idVenteField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        JButton btnAnnuler = new JButton("Annuler Vente");
        JButton btnRetour = new JButton("Retour");

        btnAnnuler.addActionListener(this::executeAnnulation);
        btnRetour.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });

        panelBoutons.add(btnAnnuler);
        panelBoutons.add(btnRetour);
        add(panelBoutons, gbc);
    }

    private void executeAnnulation(ActionEvent e) {
        String idVente = idVenteField.getText().trim();
        if (idVente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir l'ID de la vente à annuler.", "Champ vide", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            conn = Connexion.getConnexion();
            conn.setAutoCommit(false);

            String selectSql = "SELECT id_medicament, quantite FROM Vente WHERE id_vente = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setString(1, idVente);
            ResultSet rs = selectStmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Aucune vente trouvée avec cet ID.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String idMedicament = rs.getString("id_medicament");
            int quantite = rs.getInt("quantite");

            String updateStockSql = "UPDATE Medicament SET stock = stock + ? WHERE id_medicament = ?";
            PreparedStatement updateStockStmt = conn.prepareStatement(updateStockSql);
            updateStockStmt.setInt(1, quantite);
            updateStockStmt.setString(2, idMedicament);
            updateStockStmt.executeUpdate();

            String insertHistSql = "INSERT INTO stock_historique (id_medicament, quantite, date_stock) " +
                                   "VALUES (?, (SELECT stock FROM Medicament WHERE id_medicament = ?), ?)";
            PreparedStatement insertHistStmt = conn.prepareStatement(insertHistSql);
            insertHistStmt.setString(1, idMedicament);
            insertHistStmt.setString(2, idMedicament);
            insertHistStmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            insertHistStmt.executeUpdate();

            String deleteSql = "DELETE FROM Vente WHERE id_vente = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setString(1, idVente);
            deleteStmt.executeUpdate();

            conn.commit();
            JOptionPane.showMessageDialog(this, 
                "Vente annulée avec succès !\n" +
                "• " + quantite + " unité(s) restaurée(s) pour le médicament " + idMedicament + "\n" +
                "• Stock mis à jour et historisé.",
                "Annulation réussie", JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
            parent.setVisible(true);

        } catch (SQLException ex) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Échec de l'annulation.\nVérifiez l'ID ou la connexion à la base.",
                "Erreur", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
}