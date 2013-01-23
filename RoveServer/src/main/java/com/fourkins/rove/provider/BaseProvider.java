package com.fourkins.rove.provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseProvider {

    private final String url = "jdbc:mysql://fourkinsdb.cmwdmnbjpopo.us-east-1.rds.amazonaws.com:3306/rove";
    private final String user = "fourkins";
    private final String password = "1234skin";

    public BaseProvider() {

    }

    protected Connection acquireConnection() throws SQLException {
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
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
