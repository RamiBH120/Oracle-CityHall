package Model;

public class Employ {
    private int id,horr,tel;
    private String nom,prenom,droit,date_naiss,auto,usern,mdp,email,nomtsk,desktsk,etattsk,joindate;
    private float salaire;

    public Employ(int id, String nom, String prenom, String date_naiss, String droit, int horr, float salaire, String auto, String joindate) {
        this.id = id;
        this.horr = horr;
        this.nom = nom;
        this.prenom = prenom;
        this.droit = droit;
        this.date_naiss = date_naiss;
        this.salaire = salaire;
        this.auto = auto;
        this.joindate = joindate;
    }

    public Employ(int id, String nom, String prenom, String date_naiss, String droit, int horr, float salaire, String auto, String joindate, String usern, String mdp, int tel, String email) {
        this.id = id;
        this.horr = horr;
        this.tel = tel;
        this.nom = nom;
        this.prenom = prenom;
        this.droit = droit;
        this.date_naiss = date_naiss;
        this.auto = auto;
        this.joindate = joindate;
        this.usern = usern;
        this.mdp = mdp;
        this.email = email;
        this.salaire = salaire;
    }

    public Employ(int id, String nom, String prenom, String date_naiss, String droit, int horr, float salaire, String auto, String joindate, String nomtsk, String desktsk,String etattsk) {
        this.id = id;
        this.horr = horr;
        this.nom = nom;
        this.prenom = prenom;
        this.droit = droit;
        this.date_naiss = date_naiss;
        this.auto = auto;
        this.joindate = joindate;
        this.salaire = salaire;
        this.nomtsk = nomtsk;
        this.desktsk = desktsk;
        this.etattsk = etattsk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHorr() {
        return horr;
    }

    public void setHorr(int horr) {
        this.horr = horr;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDroit() {
        return droit;
    }

    public void setDroit(String droit) {
        this.droit = droit;
    }

    public String getDate_naiss() {
        return date_naiss;
    }

    public void setDate_naiss(String date_naiss) {
        this.date_naiss = date_naiss;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getUsern() {
        return usern;
    }

    public void setUsern(String usern) {
        this.usern = usern;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomtsk() {
        return nomtsk;
    }

    public void setNomtsk(String nomtsk) {
        this.nomtsk = nomtsk;
    }

    public String getDesktsk() {
        return desktsk;
    }

    public void setDesktsk(String desktsk) {
        this.desktsk = desktsk;
    }

    public String getEtattsk() {
        return etattsk;
    }

    public void setEtattsk(String etattsk) {
        this.etattsk = etattsk;
    }

    @Override
    public String toString() {
        return "Employ{" +
                "id=" + id +
                ", horr=" + horr +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", droit='" + droit + '\'' +
                ", date_naiss=" + date_naiss +
                ", salaire=" + salaire +
                ", auto=" + auto +
                '}';
    }
}
