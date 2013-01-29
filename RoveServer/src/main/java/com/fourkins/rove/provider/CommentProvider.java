package com.fourkins.rove.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.fourkins.rove.comment.Comment;

public class CommentProvider extends BaseProvider {

    private static final CommentProvider INSTANCE = new CommentProvider();

    //@formatter:off
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

        // TODO get comments from DB

        return comments;
    }

    public boolean addComment(Comment comment) {
        boolean success = false;

        // TODO add comment to DB

        return success;
    }
}
