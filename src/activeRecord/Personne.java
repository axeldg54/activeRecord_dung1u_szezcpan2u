package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Personne {

    protected int id;
    protected String nom;
    protected  String prenom;

    public Personne(int i, String p, String n) throws SQLException {
        if (i > 0) id = i;
        else id = -1;
        nom = n;
        prenom = p;
    }

    public static ArrayList<Personne> findAll() throws SQLException {
        Connection c = DBConnection.getConnection();
        ArrayList<Personne> p = new ArrayList<Personne>();
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Personne");
        while (resultSet.next()){
            p.add(new Personne(resultSet.getInt(0),resultSet.getString(1), resultSet.getString(2)));
        }
        return p;
    }

    public static Personne findById(int id) throws SQLException {
        Connection c = DBConnection.getConnection();
        PreparedStatement preparedStatement = c.prepareStatement("select * from Personne where id = ?");
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Personne p = null;
        while (resultSet.next()){
            p = new Personne(resultSet.getInt(0),resultSet.getString(1), resultSet.getString(2));
        }
        return p;
    }

    public static ArrayList<Personne> findByName(String nom) throws SQLException {
        Connection c = DBConnection.getConnection();
        PreparedStatement preparedStatement = c.prepareStatement("select * from Personne where nom = ?");
        preparedStatement.setString(1,nom);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Personne> p = new ArrayList<Personne>();
        while (resultSet.next()){
            p.add(new Personne(resultSet.getInt(0),resultSet.getString(1), resultSet.getString(2)));
        }
        return p;
    }
}
