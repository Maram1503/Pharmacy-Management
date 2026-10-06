package pharmacie;

import java.sql.*;

public class Authentification {
    public static String getTypeAvecId(String login, String pwd) {
        try (Connection conn = Connexion.getConnexion()) {
            PreparedStatement p1 = conn.prepareStatement(
                "SELECT id_pharmacien FROM Pharmacien WHERE LOGIN = ? AND PWD = ?");
            p1.setString(1, login);
            p1.setString(2, pwd);
            ResultSet r1 = p1.executeQuery();
            
            PreparedStatement p2 = conn.prepareStatement(
                "SELECT id_gestionnaire FROM Gestionnaire WHERE LOGIN = ? AND PWD = ?");
            p2.setString(1, login);
            p2.setString(2, pwd);
            ResultSet r2 = p2.executeQuery();
            if (r1.next()) {
                return "pharmacien:" + r1.getString("ID_PHARMACIEN");
            }

            if (r2.next()) {
                return "gestionnaire:" + r2.getString("ID_GESTIONNAIRE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}