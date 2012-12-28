package com.fourkins.rove.handler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.fourkins.rove.provider.UserProvider;
import com.fourkins.rove.user.User;

@Path("/users")
public class UserHandler extends BaseHandler {

    public UserHandler() {
        super();
    }

    @GET
    @Path("/{user-id}")
    public Response getUser(@PathParam("user-id") int userId) {

        User user = UserProvider.getInstance().getUser(userId);

        if (user != null) {
            return buildJsonOKResponse(user);
        } else {
            return buildNotFoundResponse();
        }
    }
}