package com.fourkins.rove.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fourkins.rove.user.User;
import com.fourkins.rove.user.UserAlreadyExistException;
import com.mysql.jdbc.MysqlErrorNumbers;

public class UserProvider extends BaseProvider {

    private static final UserProvider INSTANCE = new UserProvider();

    //@formatter:off
    private static final String GET_USER = 
            "SELECT user_id, username, real_name, email, salt " + 
            "  FROM users ";
    
    private static final String GET_USER_BY_ID = GET_USER + " WHERE user_id = ? ";
    
    private static final String GET_USER_BY_EMAIL = GET_USER + " WHERE email = ? ";
    
    private static final String GET_USER_BY_USERNAME = GET_USER + " WHERE username = ? ";
    
    private static final String IS_VALID_USER = 
            "SELECT COUNT(*) " +
            "  FROM users " +
            " WHERE email = ? " +
            "   AND password = ? ";
    
    private static final String ADD_USER =
            "INSERT INTO users (username, real_name, email, password, salt, created_on, updated_on) " +
            "VALUES (?, ?, ?, ?, ?, now(), now()) "; 
    //@formatter:on

    private static final Logger LOGGER = Logger.getLogger(UserProvider.class.getName());

    private UserProvider() {
        super();
    }

    public static UserProvider getInstance() {
        return INSTANCE;
    }

    public User getUserById(int userId) {
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
                user.setUsername(rs.getString(2));
                user.setRealName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setSalt(rs.getString(5));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);

        } finally {
            try {
                releaseConnection(conn, ps, rs);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        return user;
    }

    public User getUserByEmail(String email) {
        User user = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = acquireConnection();

            ps = conn.prepareStatement(GET_USER_BY_EMAIL);
            ps.setString(1, email);

            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setRealName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setSalt(rs.getString(5));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);

        } finally {
            try {
                releaseConnection(conn, ps, rs);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        return user;
    }

    public User getUserByUsername(String username) {
        User user = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = acquireConnection();

            ps = conn.prepareStatement(GET_USER_BY_USERNAME);
            ps.setString(1, username);

            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setRealName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setSalt(rs.getString(5));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);

        } finally {
            try {
                releaseConnection(conn, ps, rs);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        return user;
    }

    public boolean isValidUser(String email, String password) {
        boolean isValidUser = false;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = acquireConnection();

            ps = conn.prepareStatement(IS_VALID_USER);
            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) == 1) {
                    isValidUser = true;
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);

        } finally {
            try {
                releaseConnection(conn, ps, rs);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        return isValidUser;
    }

    public User addUser(User user) throws UserAlreadyExistException {
        User addedUser = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = acquireConnection();

            ps = conn.prepareStatement(ADD_USER);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getRealName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getSalt());

            int rowCount = ps.executeUpdate();
            if (rowCount == 1) {
                // user was added successfully, try to get the user object again
                // by the email and return it
                addedUser = getUserByEmail((user.getEmail()));
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
                // this user already exist with this email, log this error and
                // throw exception to be handled by the caller
                LOGGER.log(Level.WARNING, "User already exist with email=" + user.getEmail());
                throw new UserAlreadyExistException();
            } else {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }

        } finally {
            try {
                releaseConnection(conn, ps, rs);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        return addedUser;
    }
}
