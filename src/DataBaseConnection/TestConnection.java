package DataBaseConnection;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static DataBaseConnection.OracleConnection.getOracleConnection;


public class TestConnection {


    public static void main(String[] args)throws SQLException {

        String selectTableSQL = "SELECT * FROM employ";

        Statement statement = null;

        try{
            Connection connection= getOracleConnection();

            statement = connection.createStatement();


            //get data from db

            ResultSet rs = statement.executeQuery(selectTableSQL);



            //fetch data

            while(rs.next()){
                String field = rs.getString("name");
                String field1 = rs.getString("id");

                System.out.println("name : "+field+"    id : "+field1);

            }
            rs.close();




        }
        catch (SQLException e){
            System.out.println("1000000 dawa7");
        }

    }

}