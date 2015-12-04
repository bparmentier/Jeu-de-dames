/*
 * Copyright (C) 2015 gbps2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package be.heb.esi.alg3ir.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to manage the creation of the tables and the access to those tables in
 * a data base.
 *
 * @author Parmentier Bruno - Wyckmans Jonathan
 */
public class BDManager {

    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String bdd = "jdbc:derby:Dames;create=true";

    private Connection co = null;
    private ResultSet result;
    private Statement statement;

    private int numSequence;
    private int numMove;

    public BDManager() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        try {
            /* ------- Load Driver ----------- */
            loadDriver();
            /* ------- Start DB ----------- */
            co = connectBDD();
            statement = co.createStatement();

            createTables();
        } catch (Exception ex) {
            Logger.getLogger(BDManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void shutdownBD() throws SQLException {
        /*  ------ Shut Down DB ------- */
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
            statement.close();
        } catch (SQLException se) {
            if (((se.getErrorCode() == 50000)
                    && ("XJ015".equals(se.getSQLState())))) {
                System.out.println("Derby shut down normally");
            } else {
                System.err.println("Derby did not shut down normally");
                throw se;
            }
        }
    }

    private void loadDriver() throws Exception {
        try {
            System.out.println("Loading the  driver...");
            Class.forName(driver).newInstance();
            System.out.println("=====    Driver Loaded    =====");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new Exception("Driver missing");
        }
    }

    private Connection connectBDD() throws Exception {
        Connection cotemp = null;
        try {
            cotemp = DriverManager.getConnection(bdd);
            System.out.println("=====    Started/Connected DB    =====");
        } catch (Exception e) {
            throw new Exception("Problem with the connexion to the data base");
        }
        return cotemp;
    }

    private void createTables() {
        try {
            /* ----- Create 'games' table  ----- */
            String createSql = "CREATE TABLE games ( id INT NOT NULL, started TIMESTAMP NOT NULL,"
                    + "PRIMARY KEY (id) )";
            statement.execute(createSql);
            System.out.println("=====    Created Table 'games'   =====");

            /* ----- Create 'moves' table  ----- */
            createSql = "CREATE TABLE moves ( idGame INT NOT NULL, numMove INT NOT NULL, "
                    + "fromLine INT NOT NULL, fromColumn INT NOT NULL, "
                    + "toLine INT NOT NULL, toColumn INT NOT NULL,"
                    + "PRIMARY KEY (idGame, numMove) )";
            statement.execute(createSql);
            System.out.println("=====    Created Table 'moves'   =====");

            String fkSql = "ALTER TABLE moves ADD CONSTRAINT fkIdGameRefGameId FOREIGN KEY (idGame) "
                    + "REFERENCES games(id)";
            statement.execute(fkSql);
            System.out.println("=====    Created FOREIGN KEY (idGame) REFERENCES games(id)   =====");

        } catch (SQLException ex) {
            if (!DerbyUtils.tableAlreadyExists((SQLException) ex)) { //check if the exception is because of pre-existing table.
                Logger.getLogger(BDManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void insertNewGame() {
        try {
            String query = "SELECT MAX(id) FROM games";
            PreparedStatement stmt = co.prepareStatement(query);
            result = stmt.executeQuery();

            if (result.next()) {
                numSequence = result.getInt(1) + 1;
            }

            System.out.println("Sequence number = " + numSequence);
            Timestamp sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            query = "INSERT INTO games(id, started) VALUES (?,?)";

            stmt = co.prepareStatement(query);

            stmt.setString(1, Integer.toString(numSequence));
            stmt.setTimestamp(2, sqlDate);

            stmt.executeUpdate();

            result.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void insertNewMove(int fromLine, int fromColumn, int toLine, int toColumn) {
        try {
            String query = "SELECT MAX(numMove) FROM moves WHERE idGame = " + numSequence;
            PreparedStatement stmt = co.prepareStatement(query);
            result = stmt.executeQuery();

            if (result.next()) {
                numMove = result.getInt(1) + 1;
            }

            System.out.println("Move number = " + numMove);
            query = "INSERT INTO moves (idGame, numMove, fromLine, fromColumn, toLine, toColumn) VALUES (?,?,?,?,?,?)";

            stmt = co.prepareStatement(query);

            stmt.setString(1, Integer.toString(numSequence));
            stmt.setString(2, Integer.toString(numMove));
            stmt.setString(3, Integer.toString(fromLine));
            stmt.setString(4, Integer.toString(fromColumn));
            stmt.setString(5, Integer.toString(toLine));
            stmt.setString(6, Integer.toString(toColumn));

            stmt.executeUpdate();

            System.out.print("Move from : (" + Integer.toString(fromLine) + "," + Integer.toString(fromColumn) + ") to ("
                    + Integer.toString(toLine) + "," + Integer.toString(toColumn) + ") - ");
            System.out.print(" from idGame : " + Integer.toString(numSequence));
            System.out.println(" has been added to the database");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public List<Timestamp> getTimeStampGame() {

        List<Timestamp> allTimeStamps = new ArrayList<>();

        try {
            String query = "SELECT started FROM games";
            PreparedStatement stmt = co.prepareStatement(query);
            result = stmt.executeQuery();

            while (result.next()) {
                allTimeStamps.add(result.getTimestamp(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return allTimeStamps;
    }

    public List<Move> getMovesOfGame(int idGame) {
        List<Move> moves = new ArrayList<>();

        try {
            String query = "SELECT fromLine, fromColumn, toLine, toColumn FROM moves WHERE idGame = " + Integer.toString(idGame);
            PreparedStatement stmt = co.prepareStatement(query);
            result = stmt.executeQuery();

            while (result.next()) {
                moves.add(new Move(result.getInt("fromLine"), result.getInt("fromColumn"),
                        result.getInt("toLine"), result.getInt("toColumn")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return moves;
    }
}
