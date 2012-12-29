package com.fourkins.rove.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseHandler {

    protected static Gson gson = new GsonBuilder().create();

    public BaseHandler() {

    }

    protected Response buildResponse(Status status) {
        return Response.status(status).build();
    }

    protected Response buildJsonResponse(Status status, Object object) {
        return Response.status(status).entity(gson.toJson(object)).build();
    }

}
