package pharmacie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FenetreAjouterMedicament extends JFrame {

	private InterfaceGestionnaire parent;
    private JTextField idField;
    private JTextField nomField;
    private JTextField dosageField;
    private JTextField stockField;
    private JTextField prixField;

    public FenetreAjouterMedicament(InterfaceGestionnaire parent) {
        this.parent = parent;
        setTitle("Ajouter un Médicament");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 500);
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
        add(new JLabel("Nom :"), gbc);
        nomField = new JTextField(15);
        gbc.gridx = 1;
        add(nomField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Dosage :"), gbc);
        dosageField = new JTextField(15);
        gbc.gridx = 1;
        add(dosageField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Stock :"), gbc);
        stockField = new JTextField(15);
        gbc.gridx = 1;
        add(stockField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Prix unitaire :"), gbc);
        prixField = new JTextField(15);
        gbc.gridx = 1;
        add(prixField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        JPanel panelBoutons = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnRetour = new JButton("Retour");

        btnAjouter.addActionListener(e -> ajouterMedicament());
        btnRetour.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });

        panelBoutons.add(btnAjouter);
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
    private void ajouterMedicament() {
        String id = idField.getText().trim();
        String nom = nomField.getText().trim();
        String dosageStr = dosageField.getText().trim();
        String stockStr = stockField.getText().trim();
        String prixStr = prixField.getText().trim();

        if (id.isEmpty() || nom.isEmpty() || dosageStr.isEmpty() || stockStr.isEmpty() || prixStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double dosage;
        int stock;
        double prix;

        try {
            dosage = Double.parseDouble(dosageStr);
            stock = Integer.parseInt(stockStr);
            prix = Double.parseDouble(prixStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Dosage, stock et prix doivent être des nombres.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Medicament m = new Medicament(id, nom, dosage, stock, prix);

        MedicamentDAO dao = new MedicamentDAO();
        if (dao.ajouterMedicament(m)) {
            enregistrerDansHistorique(id, stock);
            JOptionPane.showMessageDialog(this, "Médicament ajouté !");
        }else {
            JOptionPane.showMessageDialog(this, "Échec de l'ajout du médicament.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
