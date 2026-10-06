package pharmacie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreGestionStockGestionnaire extends JFrame {

    private InterfaceGestionnaire parent;

    public FenetreGestionStockGestionnaire(InterfaceGestionnaire parent) {
        this.parent = parent;
        setTitle("Gestion du Stock");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

      
        JLabel titre = new JLabel("Gestion du Stock", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titre, BorderLayout.NORTH);

    
        JPanel panelBoutons = new JPanel(new GridLayout(5, 1, 15, 12));
        panelBoutons.setBorder(BorderFactory.createEmptyBorder(25, 50, 25, 50));

        JButton btnAjouter = new JButton("Ajouter médicament");
        JButton btnModifier = new JButton("Modifier le stock");
        JButton btnCritique = new JButton("Consulter stock critique");
        JButton btnHistorique = new JButton("Consulter le stock historique");
        JButton btnRetour = new JButton("Retour");

  
        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnCritique);
        panelBoutons.add(btnHistorique);
        panelBoutons.add(btnRetour);

        add(panelBoutons, BorderLayout.CENTER);

  
        btnAjouter.addActionListener(e -> {
            this.dispose();
            new FenetreAjouterMedicament(parent).setVisible(true);
        });

        btnModifier.addActionListener(e -> {
            this.dispose();
            new FenetreModifierStock(parent).setVisible(true);
        });

        btnCritique.addActionListener(e -> {
            this.dispose();
            new FenetreStockCritique(parent).setVisible(true);
        });

        btnHistorique.addActionListener(e -> {
            this.dispose();
            new FenetreStockHistorique(parent).setVisible(true);
        });

        btnRetour.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });
    }
}
