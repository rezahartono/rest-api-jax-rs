/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.restapi.common;

import bo.UserBO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import common.SettingConstant;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import javax.ws.rs.core.Response;
import vo.UserVO;
import vo.custom.rest.AuthenticationVO;
import vo.custom.rest.JWTUserData;

/**
 *
 * @author rezah
 */
public class RestCommon {

    public ResponseTemplate response = new ResponseTemplate();

    public String generateId() {
        return UUID.randomUUID().toString();
    }

    public class ResponseTemplate {

        public Response OK(Object entity) {
            return Response.ok().entity(entity).build();
        }
    }

    public AuthenticationVO generateToken(UserVO user, String schemaName) {
        AuthenticationVO token = new AuthenticationVO();
        String authToken = null;

        try {
            if (user.getSession_id() == null || user.getSession_id().isEmpty()) {
                user.setSession_id(generateId());
                System.out.println("newSession ==> "+user.getSession_id());
                UserBO.getInstance().updateSession(user, schemaName);
            }

            Algorithm algorithm = Algorithm.HMAC512(SettingConstant.JWTConstant.JWT_ALGORITHM);

            authToken = JWT.create()
                    .withIssuer(SettingConstant.JWTConstant.JWT_ISSUER)
                    .withIssuedAt(Date.from(Instant.now()))
                    .withClaim(SettingConstant.JWTInformationData.USER_ID, user.getId())
                    .withClaim(SettingConstant.JWTInformationData.SESSION_ID, user.getSession_id())
                    .withClaim(SettingConstant.JWTInformationData.SCHEMA_NAME, schemaName)
                    .sign(algorithm);

            if (authToken != null) {
                token.setToken_type(SettingConstant.JWTConstant.JWT_TOKEN_TYPE);
                token.setAuth_token(authToken);
            }

        } catch (JWTCreationException e) {
            System.out.println(e);
        }
        return token;
    }
    
    public JWTUserData decodeToken(String token){
        JWTUserData jwtData = new JWTUserData();
        AuthenticationVO tokenDecrypt = new AuthenticationVO();
        
        if(token != null){
            String[] tokenData = token.split(" ");
            tokenDecrypt.setToken_type(tokenData[0]);
            tokenDecrypt.setAuth_token(tokenData[1]);
            
            if(tokenDecrypt.getToken_type().equals(SettingConstant.JWTConstant.JWT_TOKEN_TYPE)){
                try {
                    Algorithm algorithm = Algorithm.HMAC512(SettingConstant.JWTConstant.JWT_ALGORITHM);
                    
                    JWTVerifier verifier = JWT.require(algorithm)
                            .withIssuer(SettingConstant.JWTConstant.JWT_ISSUER)
                            .build();
                    
                    DecodedJWT jwt = verifier.verify(tokenDecrypt.getAuth_token());
                    
                    if(jwt != null){
                        jwtData.setUser_id(jwt.getClaim(SettingConstant.JWTInformationData.USER_ID).toString().replaceAll("\"", ""));
                        jwtData.setSession_id(jwt.getClaim(SettingConstant.JWTInformationData.SESSION_ID).toString().replaceAll("\"", ""));
                        jwtData.setSchema_name(jwt.getClaim(SettingConstant.JWTInformationData.SCHEMA_NAME).toString().replaceAll("\"", ""));
                    }
                } catch (JWTVerificationException e) {
                    System.out.println("error ==> "+e);
                }
            }
        }
        
        return jwtData;
    }
}
