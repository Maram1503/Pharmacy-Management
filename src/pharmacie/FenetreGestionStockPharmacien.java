package pharmacie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FenetreGestionStockPharmacien extends JFrame {

    private InterfacePharmacien parent;

    public FenetreGestionStockPharmacien(InterfacePharmacien parent) {
        this.parent = parent;
        setTitle("Stock - Tous les Médicaments");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Stock actuel de tous les médicaments", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        add(titre, BorderLayout.NORTH);

        String[] colonnes = {"ID", "Nom", "Dosage", "Stock", "Prix unitaire"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        chargerMedicaments(model);

        JPanel panelBoutons = new JPanel(new FlowLayout());
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });
        panelBoutons.add(btnRetour);
        add(panelBoutons, BorderLayout.SOUTH);
    }

    private void chargerMedicaments(DefaultTableModel model) {
        MedicamentDAO dao = new MedicamentDAO();
        List<Medicament> medicaments = dao.getAllMedicaments();
        for (Medicament m : medicaments) {
            Object[] ligne = {
                m.getIdMedicament(),
                m.getNom(),
                m.getDosage(),
                m.getStock(),
                String.format("%.2f", m.getPrixUnitaire()) + " DA"
            };
            model.addRow(ligne);
        }
    }
}
