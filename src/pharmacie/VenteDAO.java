package pharmacie;

import java.sql.*;
import java.time.LocalDate;

public class VenteDAO {

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
}
