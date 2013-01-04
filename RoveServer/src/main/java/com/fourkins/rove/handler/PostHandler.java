package com.fourkins.rove.handler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fourkins.rove.Utils;
import com.fourkins.rove.post.Post;
import com.fourkins.rove.provider.PostProvider;

@Path("/posts")
public class PostHandler extends BaseHandler {

    private static final PostProvider PROVIDER = PostProvider.getInstance();

    private static final Logger LOGGER = Logger.getLogger(PostHandler.class.getName());

    public PostHandler() {
        super();
    }

    @GET
    @Path("/{post-id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPost(@PathParam("post-id") int postId) {

        LOGGER.log(Level.INFO, "getPost() - postId=" + postId);

        Post post = PROVIDER.getPost(postId);

        if (post != null) {
            return buildJsonResponse(Status.OK, post);
        } else {
            return buildResponse(Status.NOT_FOUND);
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPostsByDistance(@QueryParam("lat") double lat, @QueryParam("lng") double lng, @QueryParam("dist") double dist) {

        LOGGER.log(Level.INFO, "getPostsByDistance() - lat=" + lat + ",lng=" + lng + ",dist=" + dist);

        double[] delta = Utils.getLocationDegreeDelta(lat, lng, dist);
        double lat1 = lat - delta[0];
        double lat2 = lat + delta[0];
        double lng1 = lng - delta[1];
        double lng2 = lng + delta[1];

        List<Post> posts = PROVIDER.getPostsByDistance(lat1, lng1, lat2, lng2);

        return buildJsonResponse(Status.OK, posts);
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addPost(String json) {

        LOGGER.log(Level.INFO, "addPost() - json=" + json);

        Post post = gson.fromJson(json, Post.class);

        // return "BAD REQUEST 400" if we can't parse the post
        if (post == null) {
            return buildResponse(Status.BAD_REQUEST);
        }

        boolean success = PROVIDER.addPost(post);

        return buildJsonResponse(Status.OK, success);
    }
}
