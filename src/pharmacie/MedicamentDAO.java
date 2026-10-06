package pharmacie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAO {


	public boolean ajouterMedicament(Medicament m) {
	    String sql = "INSERT INTO Medicament VALUES (?, ?, ?, ?, ?)";
	    try (Connection conn = Connexion.getConnexion();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, m.getIdMedicament());
	        stmt.setString(2, m.getNom());
	        stmt.setDouble(3, m.getDosage());
	        stmt.setInt(4, m.getStock());
	        stmt.setDouble(5, m.getPrixUnitaire());
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean medicamentExiste(String idMedicament) {
	    String sql = "SELECT COUNT(*) FROM Medicament WHERE id_medicament = ?";
	    try (Connection conn = Connexion.getConnexion();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, idMedicament);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public List<Medicament> getStockCritique() {
	    List<Medicament> medicaments = new ArrayList<>();
	    String sql = "SELECT * FROM Medicament WHERE stock <= 10 ORDER BY stock";
	    try (Connection conn = Connexion.getConnexion();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            Medicament m = new Medicament(
	                rs.getString("id_medicament"),
	                rs.getString("nom"),
	                rs.getDouble("dosage"),
	                rs.getInt("stock"),
	                rs.getDouble("prix_unitaire")
	            );
	            medicaments.add(m);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return medicaments;
	}
    public List<Medicament> getAllMedicaments() {
        List<Medicament> medicaments = new ArrayList<>();
        String sql = "SELECT * FROM Medicament ORDER BY nom";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Medicament m = new Medicament(
                    rs.getString("id_medicament"),
                    rs.getString("nom"),
                    rs.getDouble("dosage"),
                    rs.getInt("stock"),
                    rs.getDouble("prix_unitaire")
                );
                medicaments.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicaments;
    }


    public Integer getStock(String idMedicament) {
        String sql = "SELECT stock FROM Medicament WHERE id_medicament = ?";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idMedicament);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

    public boolean updateStock(String idMedicament, int nouveauStock) {
        String sql = "UPDATE Medicament SET stock = ? WHERE id_medicament = ?";
        try (Connection conn = Connexion.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nouveauStock);
            stmt.setString(2, idMedicament);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}