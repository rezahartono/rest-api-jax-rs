/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.restapi.controller;

import bo.UserBO;
import com.restapi.common.RestCommon;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import vo.UserVO;
import vo.custom.rest.AuthenticationVO;
import vo.custom.rest.JWTUserData;
import vo.custom.rest.response.ResponseFormatter;

/**
 *
 * @author rezah
 */
public class Authentication extends RestCommon {

    private static Authentication authController;

    public static Authentication getInstance() {
        if (authController == null) {
            authController = new Authentication();
        }

        return authController;
    }

    public Response signIn(UriInfo uri, UserVO userParam) {
        Response result = null;
        UserVO user = new UserVO();

        try {
            if (userParam.getUsername() != null && userParam.getPassword() != null) {
                user = UserBO.getInstance().signIn(userParam);

                if (user != null) {
                    AuthenticationVO token = generateToken(user, "restapi_v1");

                    result = response.OK(ResponseFormatter.getInstance().OK(uri.getPath(), token, "Authentication Success", 1, 1, 1, 1));
                }
            } else {
                result = response.OK(ResponseFormatter.getInstance().OK(uri.getPath(), null, "Authentication Error", 1, 1, 1, 1));
            }
        } catch (Exception e) {
            result = response.OK(ResponseFormatter.getInstance().OK(uri.getPath(), e, "Authentication Error", 1, 1, 1, 1));
            System.out.println("er ===> " + e);
        }

        return result;
    }

    public Response sessionCheck(UriInfo uri, String authorization) {
        Response result = null;
        UserVO user = new UserVO();
        JWTUserData userData = new JWTUserData();
        boolean isVerified = false;

        try {
            if (authorization != null) {
                userData = decodeToken(authorization);
                isVerified = true;
            } else {
                isVerified = false;
            }

            if (userData != null & isVerified) {
                isVerified = UserBO.getInstance().sessionCheck(userData);
            }

            if (isVerified) {
                result = response.OK(ResponseFormatter.getInstance().OK(uri.getPath(), null, "Session was Active", 1, 1, 1, 1));
            } else {
                result = response.OK(ResponseFormatter.getInstance().OK(uri.getPath(), null, "Session Has been ended", 1, 1, 1, 1));
            }

        } catch (Exception e) {
        }

        return result;
    }

    public Response signOut(UriInfo uri, String authorization) {
        Response result = null;
        UserVO user = new UserVO();
        JWTUserData userData = new JWTUserData();
        boolean isVerified = false;

        try {
            if (authorization != null) {
                userData = decodeToken(authorization);
                isVerified = true;
            } else {
                isVerified = false;
            }

            if (userData != null & isVerified) {
                isVerified = UserBO.getInstance().signOut(userData);
            }

            if (isVerified) {
                result = response.OK(ResponseFormatter.getInstance().OK(uri.getPath(), null, "You already sign out", 1, 1, 1, 1));
            } else {
                result = response.OK(ResponseFormatter.getInstance().OK(uri.getPath(), null, "Session Has been ended", 1, 1, 1, 1));
            }

        } catch (Exception e) {
        }

        return result;
    }

}
