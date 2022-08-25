package com.restapi.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import bo.UserBO;
import common.SettingConstant;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import vo.UserVO;

@Path("/")
public class RestController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "res ==> " + UserBO.getInstance().getUsers().get(0).toString();
    }

    @Path("/sign-in")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signIn(@Context UriInfo uri, UserVO user) {
        return Authentication.getInstance().signIn(uri, user);
    }

    @Path("/session-check")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sessionCheck(@Context UriInfo uri, @Context HttpHeaders headers) {
        String authorization = headers.getRequestHeader(SettingConstant.JWTConstant.AUTHORIZATION).get(0);
        return Authentication.getInstance().sessionCheck(uri, authorization);
    }

    @Path("/sign-out")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signOut(@Context UriInfo uri, @Context HttpHeaders headers) {
        String authorization = headers.getRequestHeader(SettingConstant.JWTConstant.AUTHORIZATION).get(0);
        return Authentication.getInstance().signOut(uri, authorization);
    }
}
