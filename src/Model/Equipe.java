package Model;

public class Equipe {
    private int idequipe;
    private String nomequipe,deskequipe,joindate;

    public Equipe(int idequipe, String nomequipe, String deskequipe, String joindate) {
        this.idequipe = idequipe;
        this.nomequipe = nomequipe;
        this.deskequipe = deskequipe;
        this.joindate = joindate;
    }

    public int getIdequipe() {
        return idequipe;
    }

    public void setIdequipe(int idequipe) {
        this.idequipe = idequipe;
    }

    public String getNomequipe() {
        return nomequipe;
    }

    public void setNomequipe(String nomequipe) {
        this.nomequipe = nomequipe;
    }

    public String getDeskequipe() {
        return deskequipe;
    }

    public void setDeskequipe(String deskequipe) {
        this.deskequipe = deskequipe;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    @Override
    public String toString() {
        return "Equipe{" +
                "idequipe=" + idequipe +
                ", nomequipe='" + nomequipe + '\'' +
                ", deskequipe='" + deskequipe + '\'' +
                ", joindate='" + joindate + '\'' +
                '}';
    }
}
