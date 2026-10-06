package pharmacie;

import java.time.LocalDate;

public class Commande {
    private String idCommande;
    private String idMedicament;
    private String idGestionnaire;
    private int quantite;
    private LocalDate date;

    public Commande(String idCommande, String idMedicament, String idGestionnaire, int quantite, LocalDate date) {
        this.idCommande = idCommande;
        this.idMedicament = idMedicament;
        this.idGestionnaire = idGestionnaire;
        this.quantite = quantite;
        this.date = date;
    }

    public String getIdCommande() { return idCommande; }
    public void setIdCommande(String idCommande) { this.idCommande = idCommande; }
    public String getIdMedicament() { return idMedicament; }
    public void setIdMedicament(String idMedicament) { this.idMedicament = idMedicament; }
    public String getIdGestionnaire() { return idGestionnaire; }
    public void setIdGestionnaire(String idGestionnaire) { this.idGestionnaire = idGestionnaire; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}