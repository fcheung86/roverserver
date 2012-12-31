package com.fourkins.rove.handler;

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

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response verifyUser(@QueryParam("email") String email, @QueryParam("password") String password) {

        // check if email and passwords are empty or null
        if (email == null || email.isEmpty()) {
            return buildResponse(Status.BAD_REQUEST);
        }
        if (password == null || password.isEmpty()) {
            return buildResponse(Status.BAD_REQUEST);
        }

        // check if this email/password is valid
        boolean isValidUser = PROVIDER.isValidUser(email, password);
        if (isValidUser) {
            return buildJsonResponse(Status.OK, true);
        }

        // this email/password is not valid, check to see if there's an existing
        // account
        // with this email
        User user = PROVIDER.getUserByEmail(email);
        if (user != null) {
            // this email exist, so the password must've been wrong
            return buildJsonResponse(Status.OK, ResultCode.USER_INVALID_PASSWORD);
        } else {
            // this email doesn't exist
            return buildJsonResponse(Status.OK, ResultCode.USER_EMAIL_DOES_NOT_EXIST);
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

        // if the user was successfully added, the user object should not be null,
        // something must've went wrong if the user is null, but we don't know the
        // exact reason, so return UNKNOWN_INTERNAL_ERROR as the result code
        if (user != null) {
            return buildJsonResponse(Status.OK, user.getUserId());
        } else {
            return buildJsonResponse(Status.INTERNAL_SERVER_ERROR, ResultCode.UNKNOWN_INTERNAL_ERROR);
        }
    }
}