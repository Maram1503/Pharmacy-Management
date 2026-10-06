package pharmacie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FenetreModifierStock extends JFrame {

    private InterfaceGestionnaire parent;

    private JTextField idField;
    private JTextField nouveauStockField;

    public FenetreModifierStock(InterfaceGestionnaire parent) {
        this.parent = parent;
        setTitle("Modifier le Stock");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

  
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("ID Médicament :"), gbc);
        idField = new JTextField(15);
        gbc.gridx = 1;
        add(idField, gbc);

   
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Nouveau Stock :"), gbc);
        nouveauStockField = new JTextField(15);
        gbc.gridx = 1;
        add(nouveauStockField, gbc);

      
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JPanel panelBoutons = new JPanel(new FlowLayout());
        JButton btnModifier = new JButton("Modifier");
        JButton btnRetour = new JButton("Retour");

        btnModifier.addActionListener(e -> modifierStock());
        btnRetour.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });

        panelBoutons.add(btnModifier);
        panelBoutons.add(btnRetour);
        add(panelBoutons, gbc);
    }
    private void enregistrerDansHistorique(String idMedicament, int nouveauStock) {
        String sql = "INSERT INTO stock_historique (id_medicament, quantite, date_stock) VALUES (?, ?, ?)";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idMedicament);
            stmt.setInt(2, nouveauStock);
            stmt.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void modifierStock() {
        String id = idField.getText().trim();
        String stockStr = nouveauStockField.getText().trim();

        if (id.isEmpty() || stockStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int nouveauStock;
        try {
            nouveauStock = Integer.parseInt(stockStr);
            if (nouveauStock < 0) {
                JOptionPane.showMessageDialog(this, "Le stock ne peut pas être négatif.", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Le stock doit être un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MedicamentDAO dao = new MedicamentDAO();
        if (!dao.medicamentExiste(id)) {
            JOptionPane.showMessageDialog(this, "Médicament introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dao.updateStock(id, nouveauStock)) {
            enregistrerDansHistorique(id, nouveauStock);
            JOptionPane.showMessageDialog(this, "Stock mis à jour !");
            this.dispose();
            parent.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Échec de la mise à jour.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}