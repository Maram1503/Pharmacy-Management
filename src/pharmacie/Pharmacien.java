package pharmacie;

public class Pharmacien {
    private String idPharmacien;
    private String nom;
    private String prenom;
    private String login;
    private String pwd;

    public Pharmacien(String idPharmacien, String nom, String prenom, String login, String pwd) {
        this.idPharmacien = idPharmacien;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.pwd = pwd;
    }

    public String getIdPharmacien() { return idPharmacien; }
    public void setIdPharmacien(String idPharmacien) { this.idPharmacien = idPharmacien; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPwd() { return pwd; }
    public void setPwd(String pwd) { this.pwd = pwd; }
}