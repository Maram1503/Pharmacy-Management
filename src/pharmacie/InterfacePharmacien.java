package pharmacie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InterfacePharmacien extends JFrame {
	
	private String idPharmacien;
    private String nom;
    private String prenom;
    private Pharmacien getPharmacienById(String id) {
        String sql = "SELECT id_pharmacien, nom, prenom, login, pwd FROM Pharmacien WHERE id_pharmacien = ?";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Pharmacien(
                    rs.getString("id_pharmacien"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("login"),
                    rs.getString("pwd")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public InterfacePharmacien(String idPharmacien) {
        this.idPharmacien = idPharmacien;
        chargerInfosUtilisateur();

        setTitle("Espace Pharmacien");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Pharmacien : " + nom + " " + prenom, SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titre, BorderLayout.NORTH);


        JPanel panelBoutons = new JPanel(new GridLayout(5, 1, 10, 10)); 
        panelBoutons.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnProfil = new JButton("Consulter le profil");
        JButton btnStock = new JButton("Gérer le stock");
        JButton btnVentes = new JButton("Consulter les ventes");
        JButton btnAnnulerVente = new JButton("Annuler une vente"); 
        JButton btnDeconnexion = new JButton("Déconnexion");

        panelBoutons.add(btnProfil);
        panelBoutons.add(btnStock);
        panelBoutons.add(btnVentes);
        panelBoutons.add(btnAnnulerVente);
        panelBoutons.add(btnDeconnexion);

        add(panelBoutons, BorderLayout.CENTER);
        btnProfil.addActionListener(e -> {
            Pharmacien pharmacien = getPharmacienById(idPharmacien);
            if (pharmacien != null) {
                this.setVisible(false);
                new FenetreProfilPharmacien(this, 
                    pharmacien.getIdPharmacien(),
                    pharmacien.getNom(),
                    pharmacien.getPrenom(),
                    pharmacien.getLogin(),
                    pharmacien.getPwd()
                ).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur : impossible de charger le profil.");
            }
        });
        btnStock.addActionListener(e -> {
            this.setVisible(false);
            new FenetreGestionStockPharmacien(this).setVisible(true);
        });
        btnVentes.addActionListener(e -> {
            this.setVisible(false); 
            new FenetreListeVentes(this).setVisible(true);
        });
        
        btnDeconnexion.addActionListener(e -> {
            this.dispose();
            new InterfaceConnexion().setVisible(true);
        });
        btnAnnulerVente.addActionListener(e -> {
            this.setVisible(false);
            new FenetreAnnulerVente(this).setVisible(true);
        });
    }
    public String getIdPharmacien() {
        return this.idPharmacien;
    }
    private void chargerInfosUtilisateur() {
        String sql = "SELECT nom, prenom FROM Pharmacien WHERE id_pharmacien = ?";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idPharmacien);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                this.nom = rs.getString("nom");
                this.prenom = rs.getString("prenom");
            } else {
                this.nom = "Inconnu";
                this.prenom = "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.nom = "Erreur";
            this.prenom = "Chargement";
        }
    }
}
