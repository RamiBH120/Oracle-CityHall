package Model;

public class Depense {
    private int iddep;
    private float montant;
    private String forres,type;

    public Depense(int iddep, float montant, String forres, String type) {
        this.iddep = iddep;
        this.montant = montant;
        this.forres = forres;
        this.type = type;
    }

    public int getIddep() {
        return iddep;
    }

    public void setIddep(int iddep) {
        this.iddep = iddep;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getForres() {
        return forres;
    }

    public void setForres(String forres) {
        this.forres = forres;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Depense{" +
                "iddep=" + iddep +
                ", montant=" + montant +
                ", forres='" + forres + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
