package be.heb.esi.alg3ir.dames.db;

import java.sql.SQLException;

/**
 *
 * @author Parmentier Bruno - Wyckmans Jonathan
 */
public class DerbyUtils {

    public static boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        exists = e.getSQLState().equals("X0Y32");
        return exists;
    }
}
