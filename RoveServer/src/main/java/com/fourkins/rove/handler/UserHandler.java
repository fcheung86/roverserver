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

import com.fourkins.rove.ResultCode;
import com.fourkins.rove.provider.UserProvider;
import com.fourkins.rove.user.User;
import com.fourkins.rove.user.UserAlreadyExistException;

@Path("/users")
public class UserHandler extends BaseHandler {

    private static final UserProvider PROVIDER = UserProvider.getInstance();

    public UserHandler() {
        super();
    }

    @GET
    @Path("/{user-id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUser(@PathParam("user-id") int userId) {

        User user = PROVIDER.getUserById(userId);

        if (user != null) {
            return buildJsonResponse(Status.OK, user);
        } else {
            return buildResponse(Status.NOT_FOUND);
        }
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUser(String json) {
        User user = gson.fromJson(json, User.class);

        // return "BAD REQUEST 400" if we can't parse the user
        if (user == null || user.getEmail() == null || user.getName() == null) {
            return buildResponse(Status.BAD_REQUEST);
        }

        try {
            user = PROVIDER.addUser(user);
        } catch (UserAlreadyExistException e) {
            return buildJsonResponse(Status.INTERNAL_SERVER_ERROR, ResultCode.USER_ALREADY_EXIST);
        }

        // if the user was successfully added, the user object should not be null, something must've went
        // wrong if it is, but we don't know the exact reason, so return UNKNOWN_INTERNAL_ERROR as the result code
        if (user != null) {
            return buildJsonResponse(Status.OK, user.getUserId());
        } else {
            return buildJsonResponse(Status.INTERNAL_SERVER_ERROR, ResultCode.UNKNOWN_INTERNAL_ERROR);
        }
    }
}