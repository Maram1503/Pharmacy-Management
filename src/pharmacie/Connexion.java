package pharmacie;
import java.sql.*;

public class Connexion {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "pharmacie";
    private static final String PASSWORD = "votre_mot_de_passe";

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Pilote Oracle non trouvé !", e);
        }
    }

    public static Connection getConnexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}