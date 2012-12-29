package com.fourkins.rove.provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseProvider {

    private final String url = "jdbc:mysql://localhost:3306/rove";
    private final String user = "rove";
    private final String password = "passw0rd";

    public BaseProvider() {

    }

    protected Connection acquireConnection() throws SQLException {
        Connection conn = null;

        conn = DriverManager.getConnection(url, user, password);

        return conn;
    }

    protected void releaseConnection(Connection conn, Statement st, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (st != null) {
            st.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
