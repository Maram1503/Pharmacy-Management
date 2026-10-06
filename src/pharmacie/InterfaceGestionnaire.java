package pharmacie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InterfaceGestionnaire extends JFrame {

    private String idGestionnaire;
    private String nom;
    private String prenom;

    public InterfaceGestionnaire(String idGestionnaire) {
        this.idGestionnaire = idGestionnaire;
        chargerInfosUtilisateur();

        setTitle("Espace Gestionnaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

      
        JLabel titre = new JLabel("Gestionnaire : " + nom + " " + prenom, SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(titre, BorderLayout.NORTH);

      
        JPanel panelBoutons = new JPanel(new GridLayout(6, 1, 10, 10));
        panelBoutons.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
        
        JButton btnRapports = new JButton("Générer des rapports statistiques");
        JButton btnGererStock = new JButton("Gérer le stock");
        JButton btnConsulterVentes = new JButton("Consulter les ventes");
        JButton btnGererCommandes = new JButton("Gérer les commandes");
        JButton btnConsulterProfil = new JButton("Consulter le profil");
        JButton btnDeconnexion = new JButton("Déconnexion"); 
        
        panelBoutons.add(btnGererStock);
        panelBoutons.add(btnConsulterVentes);
        panelBoutons.add(btnGererCommandes);
        panelBoutons.add(btnConsulterProfil);
        panelBoutons.add(btnRapports); 
        panelBoutons.add(btnDeconnexion); 

        add(panelBoutons, BorderLayout.CENTER);

      
        btnConsulterProfil.addActionListener(e -> {
            Gestionnaire gestionnaire = getGestionnaireById(idGestionnaire);
            if (gestionnaire != null) {
                this.setVisible(false);
                new FenetreProfilGestionnaire(this,
                    gestionnaire.getIdGestionnaire(),
                    gestionnaire.getNom(),
                    gestionnaire.getPrenom(),
                    gestionnaire.getLogin(),
                    gestionnaire.getPwd()
                ).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur : impossible de charger le profil.");
            }
        });
        btnGererStock.addActionListener(e -> {
            this.setVisible(false); 
            new FenetreGestionStockGestionnaire(this).setVisible(true);
        });
        btnConsulterVentes.addActionListener(e -> {
            this.setVisible(false);
            new FenetreVentesGestionnaire(this).setVisible(true);
        });
        btnGererCommandes.addActionListener(e -> {
            this.setVisible(false);
            new FenetreGererCommandes(this).setVisible(true);
        });

       
        btnDeconnexion.addActionListener(e -> {
            this.dispose(); 
            new InterfaceConnexion().setVisible(true); 
        });
        btnRapports.addActionListener(e -> {
            this.setVisible(false);
            new FenetreRapportStatistique(this).setVisible(true);
        });
    }
 
    private Gestionnaire getGestionnaireById(String id) {
        String sql = "SELECT id_gestionnaire, nom, prenom, login, pwd FROM Gestionnaire WHERE id_gestionnaire = ?";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Gestionnaire(
                    rs.getString("id_gestionnaire"),
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


    private void chargerInfosUtilisateur() {
        String sql = "SELECT nom, prenom FROM Gestionnaire WHERE id_gestionnaire = ?";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idGestionnaire);
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
