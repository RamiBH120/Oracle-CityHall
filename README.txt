mvn install:install-file -Dfile=C:\\Users\\Rami\\Documents\\Rami\\IdeaProjs\\FXScLog\\ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar

<dependencies>
        <dependency>
            <groupId>com.oracle</groupId>
            <artifactId>ojdbc6</artifactId>
            <version>11.2.0</version>
        </dependency>
</dependencies>

package DataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class OracleConnection {


    public static Connection getOracleConnection() throws SQLException{
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        }
        catch (ClassNotFoundException ex){
            System.out.println("error : unable to load driver class!");
        }
        catch (IllegalAccessError ex){
            System.out.println("error : access problem while loading!");
        } catch (IllegalAccessException e) {
            System.out.println("error 10");
        } catch (InstantiationException e) {
            System.out.println("error 100");
        }

        String URL = "jdbc:oracle:thin:@localhost:1521/XE";
        String Username = "MAHDI";
        String Password = "SYSTEM";

        Connection connection = null ;

        try{
            connection = DriverManager.getConnection(URL,Username,Password);
        }
        catch (SQLException e){
            System.out.println("error 1000");
            System.out.println(e.getErrorCode());
        }

        return connection;
    }

}

package DataBaseConnection;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static DataBaseConnection.OracleConnection.getOracleConnection;


public class TestConnection {


    public static void main(String[] args)throws SQLException {

        String selectTableSQL = "SELECT NOMCLI FROM CLI";

        Statement statement = null;

        try{
            Connection connection= getOracleConnection();

            statement = connection.createStatement();


            //get data from db

            ResultSet rs = statement.executeQuery(selectTableSQL);



            //fetch data

            while(rs.next()){
                String field = rs.getString("NOMCLI");

                System.out.println("field : "+field);
            }
            rs.close();




        }
        catch (SQLException e){
            System.out.println("1000000 dawa7");
        }

    }

}