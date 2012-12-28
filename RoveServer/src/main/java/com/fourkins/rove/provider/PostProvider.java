package com.fourkins.rove.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        //@formatter:on

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
            Logger logger = Logger.getLogger(PostProvider.class.getName());
            logger.log(Level.SEVERE, e.getMessage(), e);

        } finally {
            try {
                releaseConnection(conn, ps, rs);
            } catch (SQLException e) {
                Logger logger = Logger.getLogger(PostProvider.class.getName());
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        return post;
    }

}
