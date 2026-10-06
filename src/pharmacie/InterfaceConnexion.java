package pharmacie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceConnexion extends JFrame {

    private JTextField loginField;
    private JPasswordField pwdField;

    public InterfaceConnexion() {
        setTitle("Connexion - Gestion Pharmacie");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setSize(320, 180);
        
        setLocationRelativeTo(null);
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

      
        add(new JLabel("Login :"), gbc);
        loginField = new JTextField(15);
        gbc.gridx = 1;
        add(loginField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Mot de passe :"), gbc);
        pwdField = new JPasswordField(15);
        gbc.gridx = 1;
        add(pwdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JPanel panelBoutons = new JPanel(new FlowLayout());

        JButton btnAnnuler = new JButton("Annuler");
        JButton btnConnecter = new JButton("Connecter");

        btnAnnuler.addActionListener(e -> System.exit(0));

        btnConnecter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText().trim();
                String pwd = new String(pwdField.getPassword()).trim();

                if (login.isEmpty() || pwd.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        InterfaceConnexion.this,
                        "Veuillez remplir tous les champs.",
                        "Erreur",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                String type = Authentification.getTypeAvecId(login, pwd);
                if (type != null) {
                    InterfaceConnexion.this.dispose();
                    if (type.startsWith("pharmacien:")) {
                        String id = type.split(":")[1];
                        new InterfacePharmacien(id).setVisible(true);
                    } else if (type.startsWith("gestionnaire:")) {
                        String id = type.split(":")[1];
                        new InterfaceGestionnaire(id).setVisible(true);
                    }
                    } else {
                    JOptionPane.showMessageDialog(
                        InterfaceConnexion.this,
                        "Login : "+login+" ou mot de passe : "+pwd+" incorrect.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
       
        panelBoutons.add(btnAnnuler);
        panelBoutons.add(btnConnecter);
        add(panelBoutons, gbc);
    }
   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfaceConnexion().setVisible(true);
        });
    }
}