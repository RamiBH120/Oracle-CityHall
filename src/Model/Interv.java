package Model;

public class Interv{
    private int idinterv;
    private String titre,obj,deb,fin,etat,loc,joindate;

    public Interv(int idinterv, String titre, String obj, String deb, String fin, String etat, String loc, String joindate) {
        this.idinterv = idinterv;
        this.titre = titre;
        this.obj = obj;
        this.deb = deb;
        this.fin = fin;
        this.etat = etat;
        this.loc = loc;
        this.joindate = joindate;
    }

    public int getIdinterv() {
        return idinterv;
    }

    public void setIdinterv(int idinterv) {
        this.idinterv = idinterv;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getDeb() {
        return deb;
    }

    public void setDeb(String deb) {
        this.deb = deb;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    @Override
    public String toString() {
        return "Interv{" +
                "idinterv=" + idinterv +
                ", titre='" + titre + '\'' +
                ", obj='" + obj + '\'' +
                ", deb='" + deb + '\'' +
                ", fin='" + fin + '\'' +
                ", etat='" + etat + '\'' +
                ", loc='" + loc + '\'' +
                ", joindate='" + joindate + '\'' +
                '}';
    }
}
