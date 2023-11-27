package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Personne {

    protected int id;
    protected String nom;
    protected String prenom;

    public Personne(String p, String n) {
        id = -1;
        nom = n;
        prenom = p;
    }

    public static ArrayList<Personne> findAll() throws SQLException {
        Connection c = DBConnection.getConnection();
        ArrayList<Personne> pers = new ArrayList<Personne>();
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Personne");
        while (resultSet.next()) {
            Personne p = new Personne(resultSet.getString(1), resultSet.getString(2));
            p.id = resultSet.getInt(0);
            pers.add(p);
        }
        return pers;
    }

    public static Personne findById(int id) throws SQLException {
        Connection c = DBConnection.getConnection();
        PreparedStatement preparedStatement = c.prepareStatement("select * from Personne where id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Personne p = null;
        while (resultSet.next()) {
            p = new Personne(resultSet.getString(1), resultSet.getString(2));
            p.id = resultSet.getInt(0);
        }
        return p;
    }

    public static ArrayList<Personne> findByName(String nom) throws SQLException {
        Connection c = DBConnection.getConnection();
        PreparedStatement preparedStatement = c.prepareStatement("select * from Personne where nom = ?");
        preparedStatement.setString(1, nom);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Personne> pers = new ArrayList<Personne>();
        while (resultSet.next()) {
            Personne p = new Personne(resultSet.getString(1), resultSet.getString(2));
            p.id = resultSet.getInt(0);
            pers.add(p);
        }
        return pers;
    }

    public static void createTable() throws SQLException {
        Connection c = DBConnection.getConnection();
        Statement statement = c.createStatement();
        statement.executeQuery("create table Personne(`id` int(11) NOT NULL, `nom` varchar(40) NOT NULL, `prenom` varchar(40) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
    }

    public static void deleteTable() throws SQLException {
        Connection c = DBConnection.getConnection();
        Statement statement = c.createStatement();
        statement.executeQuery("drop table Personne");
    }

    public void saveNew() throws SQLException {
        Connection c = DBConnection.getConnection();
        Statement statement = c.createStatement();
        statement.executeQuery("insert into Personne values (nom, prenom)");
    }

    public void update() throws SQLException {
        Connection c = DBConnection.getConnection();
        Statement statement = c.createStatement();
        statement.executeUpdate("update Personne set nom = nom and prenom = prenom where id = id");
    }

    public void delete() throws SQLException {
        Connection c = DBConnection.getConnection();
        Statement statement = c.createStatement();
        statement.executeQuery("delete from Personne where id = id");
        this.id = -1;
    }

    public void save() throws SQLException {
        if (id > 0) update();
        else if (id == -1) saveNew();
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
