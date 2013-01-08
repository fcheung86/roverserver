package com.fourkins.rove.handler;

import javax.ws.rs.core.MultivaluedMap;
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

    protected Integer getInt(MultivaluedMap<String, String> map, String key, int defaultValue) {
        Integer value = getInt(map, key);

        return value != null ? value : defaultValue;
    }

    protected Integer getInt(MultivaluedMap<String, String> map, String key) {
        String value = map.getFirst(key);

        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return null;
            }

        } else {
            return null;

        }
    }

    protected Long getLong(MultivaluedMap<String, String> map, String key, long defaultValue) {
        Long value = getLong(map, key);

        return value != null ? value : defaultValue;
    }

    protected Long getLong(MultivaluedMap<String, String> map, String key) {
        String value = map.getFirst(key);

        if (value != null) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                return null;
            }

        } else {
            return null;

        }
    }

    protected Float getFloat(MultivaluedMap<String, String> map, String key, float defaultValue) {
        Float value = getFloat(map, key);

        return value != null ? value : defaultValue;
    }

    protected Float getFloat(MultivaluedMap<String, String> map, String key) {
        String value = map.getFirst(key);

        if (value != null) {
            try {
                return Float.parseFloat(value);
            } catch (NumberFormatException e) {
                return null;
            }

        } else {
            return null;

        }
    }

    protected Double getDouble(MultivaluedMap<String, String> map, String key, double defaultValue) {
        Double value = getDouble(map, key);

        return value != null ? value : defaultValue;
    }

    protected Double getDouble(MultivaluedMap<String, String> map, String key) {
        String value = map.getFirst(key);

        if (value != null) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return null;
            }

        } else {
            return null;

        }
    }

    protected String getString(MultivaluedMap<String, String> map, String key, String defaultValue) {
        String value = getString(map, key);

        return value != null ? value : defaultValue;
    }

    protected String getString(MultivaluedMap<String, String> map, String key) {
        return map.getFirst(key);
    }
}