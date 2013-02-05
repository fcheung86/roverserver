package com.fourkins.rove.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fourkins.rove.comment.Comment;

public class CommentProvider extends BaseProvider {

    private static final CommentProvider INSTANCE = new CommentProvider();

    //@formatter:off
    private static final String GET_COMMENTS =
            " SELECT c.comment_id, c.post_id, u.user_id, u.username, c.comment, c.timestamp " +
            "   FROM comments c, users u " +
            "  WHERE c.user_id = u.user_id ";
    
    private static final String ORDER_BY_TIMESTAMP = 
            " ORDER BY c.timestamp ASC ";
    
    private static final String GET_COMMENTS_BY_POST_ID = 
            GET_COMMENTS + 
            " AND post_id = ? " +
            ORDER_BY_TIMESTAMP;
    
    private static final String ADD_COMMENT = 
            " INSERT INTO comments(post_id, user_id, comment) " +
            " VALUES(?, ?, ?)";
    //@formatter:on

    private static final Logger LOGGER = Logger.getLogger(CommentProvider.class.getName());

    private CommentProvider() {
        super();
    }

    public static CommentProvider getInstance() {
        return INSTANCE;
    }

    public List<Comment> getComments(int postId) {
        List<Comment> comments = new ArrayList<Comment>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = acquireConnection();

            ps = conn.prepareStatement(GET_COMMENTS_BY_POST_ID);
            ps.setInt(1, postId);

            rs = ps.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();

                comment.setCommentId(rs.getInt(1));
                comment.setPostId(rs.getInt(2));
                comment.setUserId(rs.getInt(3));
                comment.setUsername(rs.getString(4));
                comment.setComment(rs.getString(5));
                comment.setTimestamp(rs.getTimestamp(6).getTime());

                comments.add(comment);
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

        return comments;
    }

    public boolean addComment(Comment comment) {
        boolean success = false;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = acquireConnection();

            ps = conn.prepareStatement(ADD_COMMENT);
            ps.setInt(1, comment.getPostId());
            ps.setInt(2, comment.getUserId());
            ps.setString(3, comment.getComment());

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
