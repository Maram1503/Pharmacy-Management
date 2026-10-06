package pharmacie;

public class Gestionnaire {
    private String idGestionnaire;
    private String nom;
    private String prenom;
    private String login;
    private String pwd;
    
    public Gestionnaire(String idGestionnaire, String nom, String prenom, String login, String pwd) {
        this.idGestionnaire = idGestionnaire;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.pwd = pwd;
    }

    public String getIdGestionnaire() { return idGestionnaire; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getLogin() { return login; }
    public String getPwd() { return pwd; }
}