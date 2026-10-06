package pharmacie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreProfilGestionnaire extends JFrame {

    private InterfaceGestionnaire parent;

    public FenetreProfilGestionnaire(InterfaceGestionnaire parent, String id, String nom, String prenom, String login, String pwd) {
        this.parent = parent;

        setTitle("Profil du Gestionnaire");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 320);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

       
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("ID Gestionnaire :"), gbc);
        gbc.gridx = 1;
        add(new JLabel(id), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        add(new JLabel(nom), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1;
        add(new JLabel(prenom), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Login :"), gbc);
        gbc.gridx = 1;
        add(new JLabel(login), gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1;
        add(new JLabel(pwd), gbc);

       
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });
        add(btnRetour, gbc);
    }
}
