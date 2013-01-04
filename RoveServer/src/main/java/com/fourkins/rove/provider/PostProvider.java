package com.fourkins.rove.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fourkins.rove.post.Post;

public class PostProvider extends BaseProvider {

    private static final PostProvider INSTANCE = new PostProvider();

    //@formatter:off
    private static final String GET_POST_BY_ID = 
            "SELECT post_id, user_id, latitude, longitude, message, timestamp " + 
            "  FROM posts " + 
            " WHERE post_id = ?";
    
    private static final String GET_POSTS_BY_DISTANCE = 
            "SELECT post_id, user_id, latitude, longitude, message, timestamp " + 
            "  FROM posts " + 
            " WHERE latitude > ? " +
            "   AND latitude < ? " +
            "   AND longitude > ? " +
            "   AND longitude < ? ";
    
    private static final String ADD_POST = 
            "INSERT INTO posts (user_id, latitude, longitude, message) " +
            "VALUES (?, ?, ?, ?) ";
    //@formatter:on

    private static final Logger LOGGER = Logger.getLogger(PostProvider.class.getName());

    private PostProvider() {
        super();
    }

    public static PostProvider getInstance() {
        return INSTANCE;
    }

    public Post getPost(int postId) {
        Post post = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = acquireConnection();

            ps = conn.prepareStatement(GET_POST_BY_ID);
            ps.setInt(1, postId);

            rs = ps.executeQuery();
            if (rs.next()) {
                post = new Post();
                post.setPostId(rs.getInt(1));
                post.setUserId(rs.getInt(2));
                post.setLatitude(rs.getDouble(3));
                post.setLongitude(rs.getDouble(4));
                post.setMessage(rs.getString(5));
                post.setTimestamp(rs.getTimestamp(6).getTime());
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

        return post;
    }

    public List<Post> getPostsByDistance(double lat1, double lng1, double lat2, double lng2) {
        List<Post> posts = new ArrayList<Post>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = acquireConnection();

            ps = conn.prepareStatement(GET_POSTS_BY_DISTANCE);
            ps.setDouble(1, lat1);
            ps.setDouble(2, lat2);
            ps.setDouble(3, lng1);
            ps.setDouble(4, lng2);

            rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post();

                post.setPostId(rs.getInt(1));
                post.setUserId(rs.getInt(2));
                post.setLatitude(rs.getDouble(3));
                post.setLongitude(rs.getDouble(4));
                post.setMessage(rs.getString(5));
                post.setTimestamp(rs.getTimestamp(6).getTime());

                posts.add(post);
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

        return posts;
    }

    public boolean addPost(Post post) {
        boolean success = false;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = acquireConnection();

            ps = conn.prepareStatement(ADD_POST);
            ps.setInt(1, post.getUserId());
            ps.setDouble(2, post.getLatitude());
            ps.setDouble(3, post.getLongitude());
            ps.setString(4, post.getMessage());

            int rowCount = ps.executeUpdate();
            if (rowCount == 1) {
                success = true;
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

        return success;
    }

}
