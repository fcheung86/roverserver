package com.fourkins.rove.handler;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fourkins.rove.post.Post;
import com.fourkins.rove.provider.PostProvider;

@Path("/posts")
public class PostHandler extends BaseHandler {

    private static final PostProvider PROVIDER = PostProvider.getInstance();

    public PostHandler() {
        super();
    }

    @GET
    @Path("/{post-id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPost(@PathParam("post-id") int postId) {

        Post post = PROVIDER.getPost(postId);

        if (post != null) {
            return buildJsonResponse(Status.OK, post);
        } else {
            return buildResponse(Status.NOT_FOUND);
        }
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addPost(String json) {
        Post post = gson.fromJson(json, Post.class);

        // return "BAD REQUEST 400" if we can't parse the post
        if (post == null) {
            return buildResponse(Status.BAD_REQUEST);
        }

        boolean success = PROVIDER.addPost(post);

        return buildJsonResponse(Status.OK, success);
    }
}
