package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Personne {

    protected int id;
    protected String nom;
    protected String prenom;

    public Personne(String n, String p) {
        id = -1;
        nom = n;
        prenom = p;
    }

    public static ArrayList<Personne> findAll() throws SQLException {
        Connection c = DBConnection.getConnection();
        ArrayList<Personne> pers = new ArrayList<Personne>();
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from personne");
        while (resultSet.next()) {
            Personne p = new Personne(resultSet.getString("nom"), resultSet.getString("prenom"));
            p.id = resultSet.getInt("id");
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
            p = new Personne(resultSet.getString("nom"), resultSet.getString("prenom"));
            p.id = resultSet.getInt("id");
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
            Personne p = new Personne(resultSet.getString("nom"), resultSet.getString("prenom"));
            p.id = resultSet.getInt("id");
            pers.add(p);
        }
        return pers;
    }

    public static void createTable() throws SQLException {
        Connection c = DBConnection.getConnection();
        Statement statement = c.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Personne (\n" +
                "    `id` INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    `nom` VARCHAR(40) NOT NULL,\n" +
                "    `prenom` VARCHAR(40) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n");
    }

    public static void deleteTable() throws SQLException {
        Connection c = DBConnection.getConnection();
        Statement statement = c.createStatement();
        statement.executeUpdate("drop table personne");
    }

    public void saveNew() throws SQLException {
        Connection c = DBConnection.getConnection();
        String query = "insert into Personne (nom, prenom) values (?, ?)";
        PreparedStatement preparedStatement = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, nom);
        preparedStatement.setString(2, prenom);
        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            id = generatedKeys.getInt(1);
        }
    }


    public void update() throws SQLException {
        Connection c = DBConnection.getConnection();
        String query = "update Personne set nom = ?, prenom = ? where id = ?";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setString(1, nom);
        preparedStatement.setString(2, prenom);
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();
    }

    public void delete() throws SQLException {
        Connection c = DBConnection.getConnection();
        String query = "delete from Personne where id = ?";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
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

    public void setNom(String nom) throws SQLException {
        this.nom = nom;
        update();
    }

    public void setPrenom(String p) throws SQLException {
        this.prenom = p;
        update();
    }
}
