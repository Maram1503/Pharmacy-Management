package pharmacie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VenteM {

    public boolean ajouterVente(Vente vente) {
        String sql = "INSERT INTO Vente VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vente.getIdVente());
            stmt.setInt(2, vente.getQuantite());
            stmt.setDate(3, Date.valueOf(vente.getDateVente()));
            stmt.setString(4, vente.getIdMedicament());
            stmt.setString(5, vente.getIdPharmacien());
            stmt.setString(6, vente.getIdClient());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

  
    public List<Vente> getToutesLesVentes() {
        List<Vente> ventes = new ArrayList<>();
        String sql = "SELECT * FROM Vente ORDER BY date_vente DESC";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Vente v = new Vente(
                    rs.getString("id_vente"),
                    rs.getInt("quantite"),
                    rs.getDate("date_vente").toLocalDate(),
                    rs.getString("id_medicament"),
                    rs.getString("id_pharmacien"),
                    rs.getString("id_client")
                );
                ventes.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventes;
    }

    
    public boolean annulerVente(String idVente) {
        String sql = "DELETE FROM Vente WHERE id_vente = ?";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idVente);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}