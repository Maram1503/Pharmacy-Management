package pharmacie;

import java.time.LocalDate;

public class Vente {
    private String idVente;
    private int quantite;
    private LocalDate dateVente;
    private String idMedicament;
    private String idPharmacien;
    private String idClient;

    public Vente(String idVente, int quantite, LocalDate dateVente, String idMedicament, String idPharmacien, String idClient) {
        this.idVente = idVente;
        this.quantite = quantite;
        this.dateVente = dateVente;
        this.idMedicament = idMedicament;
        this.idPharmacien = idPharmacien;
        this.idClient = idClient;
    }

    public String getIdVente() { return idVente; }
    public void setIdVente(String idVente) { this.idVente = idVente; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public LocalDate getDateVente() { return dateVente; }
    public void setDateVente(LocalDate dateVente) { this.dateVente = dateVente; }
    public String getIdMedicament() { return idMedicament; }
    public void setIdMedicament(String idMedicament) { this.idMedicament = idMedicament; }
    public String getIdPharmacien() { return idPharmacien; }
    public void setIdPharmacien(String idPharmacien) { this.idPharmacien = idPharmacien; }
    public String getIdClient() { return idClient; }
    public void setIdClient(String idClient) { this.idClient = idClient; }
}