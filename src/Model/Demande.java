package Model;

public class Demande {
    private int idem;
    private String titre,sujet,etat,joindate,type;

    public Demande(int idem, String titre, String sujet, String etat, String joindate, String type) {
        this.idem = idem;
        this.titre = titre;
        this.sujet = sujet;
        this.etat = etat;
        this.joindate = joindate;
        this.type = type;
    }

    public int getIdem() {
        return idem;
    }

    public void setIdem(int idem) {
        this.idem = idem;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Demande{" +
                "idem=" + idem +
                ", titre='" + titre + '\'' +
                ", sujet='" + sujet + '\'' +
                ", etat='" + etat + '\'' +
                ", joindate='" + joindate + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
