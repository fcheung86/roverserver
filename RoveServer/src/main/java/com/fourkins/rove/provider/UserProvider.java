package com.fourkins.rove.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fourkins.rove.user.User;

public class UserProvider extends BaseProvider {

    private static final UserProvider INSTANCE = new UserProvider();

    //@formatter:off
    private static final String GET_USER_BY_ID = 
            "SELECT user_id, name, email " + 
            "  FROM users " + 
            " WHERE user_id = ?";
    //@formatter:on

    private UserProvider() {
        super();
    }

    public static UserProvider getInstance() {
        return INSTANCE;
    }

    public User getUser(int userId) {
        User user = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = acquireConnection();

            ps = conn.prepareStatement(GET_USER_BY_ID);
            ps.setInt(1, userId);

            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt(1));
                user.setName(rs.getString(1));
                user.setEmail(rs.getString(3));
            }

        } catch (SQLException e) {
            Logger logger = Logger.getLogger(UserProvider.class.getName());
            logger.log(Level.SEVERE, e.getMessage(), e);

        } finally {
            try {
                releaseConnection(conn, ps, rs);
            } catch (SQLException e) {
                Logger logger = Logger.getLogger(UserProvider.class.getName());
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        return user;
    }
}
