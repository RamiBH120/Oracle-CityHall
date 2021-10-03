package DAO;

import Model.User;

import java.sql.*;

import static DataBaseConnection.OracleConnection.getOracleConnection;

public class UserDAO {

    public String userLog(String usern, String mdp){

        String field2 = "";
        PreparedStatement statement;

        try {
            Connection connection = getOracleConnection();
            statement = connection.prepareStatement("select compte.usern,compte.mdp,employ.droit,employ.id from employ join compte on(employ.id=compte.idemp) where compte.usern = '"+usern+"' and compte.mdp = '"+mdp+"'");

            //get data from db

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                    String field = rs.getString("usern");
                    String field1 = rs.getString("mdp");
                    field2 = rs.getString("droit");
                    String field3 = rs.getString("id");

                    User.setUsern(field);
                    User.setMdp(field1);
                    User.setId(Integer.parseInt(field3));
                    User.setDro(field2);
                    System.out.println(User.getUsern()+User.getMdp());

                }

            rs.close();
            connection.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return field2;
    }
}
