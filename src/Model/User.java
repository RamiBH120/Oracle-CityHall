package Model;

public class User {
    static String usern,mdp,dro;
    static int id;

    public User(String username, String motdp, int idd, String droit) {
        usern = username;
        mdp = motdp;
        id = idd;
        dro = droit;
    }

    public static String getUsern() {
        return usern;
    }

    public static void setUsern(String usern) {
        User.usern = usern;
    }

    public static String getMdp() {
        return mdp;
    }

    public static void setMdp(String mdp) {
        User.mdp = mdp;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        User.id = id;
    }

    public static String getDro() {
        return dro;
    }

    public static void setDro(String dro) {
        User.dro = dro;
    }
}
