package Model;

public class Materiel {
    private int idmat,stock;
    private String nom,cat,etat,joindate;

    public Materiel(int idmat, String nom, String cat, int stock,  String etat, String joindate) {
        this.idmat = idmat;
        this.stock = stock;
        this.nom = nom;
        this.cat = cat;
        this.etat = etat;
        this.joindate = joindate;
    }

    public int getIdmat() {
        return idmat;
    }

    public void setIdmat(int idmat) {
        this.idmat = idmat;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    @Override
    public String toString() {
        return "Materiel{" +
                "idmat=" + idmat +
                ", stock=" + stock +
                ", nom='" + nom + '\'' +
                ", cat='" + cat + '\'' +
                ", etat='" + etat + '\'' +
                ", joindate='" + joindate + '\'' +
                '}';
    }
}
