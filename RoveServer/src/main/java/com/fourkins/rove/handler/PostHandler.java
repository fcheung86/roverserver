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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.fourkins.rove.Constants;
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
    public Response getPosts(@Context UriInfo uriInfo) {

        MultivaluedMap<String, String> params = uriInfo.getQueryParameters();

        // get the latitude and longitude parameters
        Double lat = getDouble(params, "lat");
        Double lng = getDouble(params, "lng");

        if (lat == null || lng == null) {
            LOGGER.severe("getPosts() require latitude and longitude, one of them is missing. lat=" + lat + ",lng=" + lng);
            return buildResponse(Status.BAD_REQUEST);
        }

        // get the distance parameter, if not set, use the default of 25km
        Double dist = getDouble(params, "dist", Constants.DEFAULT_GET_POSTS_DISTANCE_KM);
        // calculate the delta lat and delta lng
        double[] delta = Utils.getLocationDegreeDelta(lat, lng, dist);
        double lat1 = lat - delta[0];
        double lat2 = lat + delta[0];
        double lng1 = lng - delta[1];
        double lng2 = lng + delta[1];

        // get the time parameter, if not set, use the current time
        Long time = getLong(params, "time", System.currentTimeMillis());

        // get the size parameter, if not set, use the default result size of 25
        Integer size = getInt(params, "size", Constants.DEFAULT_GET_POSTS_RESULT_SIZE);
        // limit the result size in case the parameter set too high
        size = Math.min(size, Constants.MAX_GET_POSTS_RESULT_SIZE);

        // get the sort parameter, if not set, use the default sort by time
        String sort = getString(params, "sort", Constants.DEFAULT_GET_POST_SORT_ORDER);

        LOGGER.log(Level.INFO, "getPosts() - lat=" + lat + ",lng=" + lng + ",dist=" + dist + ",time=" + time + ",size=" + size + ",sort=" + sort);

        // retrieve the list of posts
        List<Post> posts = PROVIDER.getPosts(lat1, lng1, lat2, lng2, time, size, sort);

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
