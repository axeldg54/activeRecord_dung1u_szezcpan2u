package activeRecord;

import java.sql.*;

public class Film {
    private String titre;
    private int id;
    private int id_real;

    public Film(String titre, Personne personne) {
        this.titre = titre;
        this.id_real = personne.getId();
        this.id = -1;
    }

    private Film(int id1, int id2, String titre) {
        this.id = id1;
        this.id_real = id2;
        this.titre = titre;
    }

    public static void createTable() throws SQLException {
        Connection c = DBConnection.getConnection();
        Statement statement = c.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS `Film` (\n" +
                "  `id` INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "  `titre` varchar(40) NOT NULL,\n" +
                "  `id_rea` int(11) DEFAULT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n");
    }

    public static void deleteTable() throws SQLException {
        Connection c = DBConnection.getConnection();
        Statement statement = c.createStatement();
        statement.executeUpdate("drop table film");
    }

    public void save() throws SQLException, RealisateurAbsentException {
        if (id > 0) update();
        else if (id == -1) saveNew();
    }

    public void saveNew() throws SQLException, RealisateurAbsentException {
        if (id_real == -1) throw new RealisateurAbsentException("Pas de réalisateur");
        Connection c = DBConnection.getConnection();
        String query = "insert into Film (titre, id_rea) values (?, ?)";
        PreparedStatement preparedStatement = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, titre);
        preparedStatement.setInt(2, id_real);
        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            id = generatedKeys.getInt(1);
        }
    }


    public void update() throws SQLException, RealisateurAbsentException {
        if (id_real == -1) throw new RealisateurAbsentException("Pas de réalisateur");
        Connection c = DBConnection.getConnection();
        String query = "update Film set titre = ?, id_rea = ? where id = ?";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setString(1, titre);
        preparedStatement.setInt(2, id_real);
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();
    }

    public static Film findById(int id) throws SQLException {
        Connection c = DBConnection.getConnection();
        PreparedStatement preparedStatement = c.prepareStatement("select * from Film where id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Film f = null;
        while (resultSet.next()) {
            f = new Film(resultSet.getString("titre"), Personne.findById(resultSet.getInt("id_rea")));
            f.id = resultSet.getInt("id");
        }
        return f;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public Personne getRealisateur() throws SQLException {
        return Personne.findById(id_real);
    }

    public void setTitre(String t) throws SQLException, RealisateurAbsentException {
        this.titre = t;
        update();
    }
}
