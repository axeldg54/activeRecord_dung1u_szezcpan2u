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
        statement.executeUpdate("create table IF NOT EXISTS Personne(`id` int(11) NOT NULL, `nom` varchar(40) NOT NULL, `prenom` varchar(40) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
    }

    public static void deleteTable() throws SQLException {
        Connection c = DBConnection.getConnection();
        Statement statement = c.createStatement();
        statement.executeUpdate("drop table Personne");
    }

    public void saveNew() throws SQLException {
        Connection c = DBConnection.getConnection();
        String query = "insert into Personne (nom, prenom) values (?, ?)";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setString(1, nom);
        preparedStatement.setString(2, prenom);
    }


    public void update() throws SQLException {
        Connection c = DBConnection.getConnection();
        String query = "update Personne set nom = ? and prenom = ? where id = ?";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setString(1, nom);
        preparedStatement.setString(2, prenom);
        preparedStatement.setInt(3, id);
    }

    public void delete() throws SQLException {
        Connection c = DBConnection.getConnection();
        String query = "delete from Personne where id = ?";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setInt(1, id);
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
