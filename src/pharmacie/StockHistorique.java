package pharmacie;

import java.time.LocalDate;

public class StockHistorique {
    private String idMedicament;
    private int quantite;
    private LocalDate date;

    public StockHistorique(String idMedicament, int quantite, LocalDate date) {
        this.idMedicament = idMedicament;
        this.quantite = quantite;
        this.date = date;
    }

    public String getIdMedicament() { return idMedicament; }
    public void setIdMedicament(String idMedicament) { this.idMedicament = idMedicament; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}