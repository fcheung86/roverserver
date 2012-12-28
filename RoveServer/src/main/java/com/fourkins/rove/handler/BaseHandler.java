package com.fourkins.rove.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseHandler {

    protected static Gson gson = new GsonBuilder().create();

    public BaseHandler() {

    }

    protected Response buildNotFoundResponse() {
        return Response.status(Status.NOT_FOUND).build();
    }

    protected Response buildJsonOKResponse(Object object) {
        return Response.status(Status.OK).entity(gson.toJson(object)).build();
    }

}
