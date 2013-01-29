package com.fourkins.rove.handler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fourkins.rove.comment.Comment;
import com.fourkins.rove.provider.CommentProvider;

@Path("/comments")
public class CommentHandler extends BaseHandler {

    private static final CommentProvider PROVIDER = CommentProvider.getInstance();

    private static final Logger LOGGER = Logger.getLogger(CommentHandler.class.getName());

    public CommentHandler() {
        super();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getComments(@QueryParam("post-id") int postId) {
        LOGGER.log(Level.INFO, "getComments() - postId=" + postId);

        List<Comment> comments = PROVIDER.getComments(postId);

        return buildJsonResponse(Status.OK, comments);
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addComment(String json) {
        LOGGER.log(Level.INFO, "addComment() - json=" + json);

        Comment comment = gson.fromJson(json, Comment.class);

        // return "BAD REQUEST 400" if we can't parse the comment
        if (comment == null) {
            return buildResponse(Status.BAD_REQUEST);
        }

        boolean success = PROVIDER.addComment(comment);

        return buildJsonResponse(Status.OK, success);
    }
}
