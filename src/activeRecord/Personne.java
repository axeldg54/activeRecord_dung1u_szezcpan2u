package activeRecord;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Personne {

    protected int id;
    protected String nom;
    protected  String prenom;

    public Personne(String p, String n){
        id = -1;
        nom = n;
        prenom = p;
    }

    public static ArrayList<Personne> findAll() throws SQLException {
        Connection c = DBConnection.getConnection();
        ArrayList<Personne> p = new ArrayList<Personne>();
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Personne");
        while (resultSet.next()){
            p.add(new Personne(resultSet.getString(1), resultSet.getString(2)));
        }
        return p;
    }
}
