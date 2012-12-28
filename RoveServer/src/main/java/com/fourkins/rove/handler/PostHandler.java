package com.fourkins.rove.handler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.fourkins.rove.post.Post;
import com.fourkins.rove.provider.PostProvider;

@Path("/posts")
public class PostHandler extends BaseHandler {

    public PostHandler() {
        super();
    }

    @GET
    @Path("/{post-id}")
    public Response getUser(@PathParam("post-id") int postId) {

        Post post = PostProvider.getInstance().getPost(postId);

        if (post != null) {
            return buildJsonOKResponse(post);
        } else {
            return buildNotFoundResponse();
        }
    }
}
