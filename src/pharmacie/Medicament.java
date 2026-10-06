package pharmacie;

public class Medicament {
    private String idMedicament;
    private String nom;
    private double dosage;
    private int stock;
    private double prixUnitaire;

    public Medicament(String idMedicament, String nom, double dosage, int stock, double prixUnitaire) {
        this.idMedicament = idMedicament;
        this.nom = nom;
        this.dosage = dosage;
        this.stock = stock;
        this.prixUnitaire = prixUnitaire;
    }

    public String getIdMedicament() { return idMedicament; }
    public void setIdMedicament(String idMedicament) { this.idMedicament = idMedicament; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public double getDosage() { return dosage; }
    public void setDosage(double dosage) { this.dosage = dosage; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }
}