package pharmacie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreEnvoyerNotification extends JFrame {

    private InterfaceGestionnaire parent;
    private JTextField idMedicamentField;
    private JTextField stockField;

    public FenetreEnvoyerNotification(InterfaceGestionnaire parent) {
        this.parent = parent;
        setTitle("Envoyer une Notification");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("ID Médicament :"), gbc);
        idMedicamentField = new JTextField(15);
        gbc.gridx = 1;
        add(idMedicamentField, gbc);


        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Stock :"), gbc);
        stockField = new JTextField(15);
        gbc.gridx = 1;
        add(stockField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JPanel panel = new JPanel(new FlowLayout());
        JButton btnEnvoyer = new JButton("Envoyer");
        JButton btnRetour = new JButton("Retour");

        btnEnvoyer.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Envoyé avec succès", "Notification", JOptionPane.INFORMATION_MESSAGE);
        });

        btnRetour.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });

        panel.add(btnEnvoyer);
        panel.add(btnRetour);
        add(panel, gbc);
    }
}