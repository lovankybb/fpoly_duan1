package com.fptpolytechnic.duan1.service;


import com.fptpolytechnic.duan1.repository.InvalidateTokenRepository;
import com.fptpolytechnic.duan1.utils.AppConfig;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.Date;

public class IntrospectService {


    private final InvalidateTokenRepository invalidateTokenRepository;

    public IntrospectService(){
        invalidateTokenRepository = new InvalidateTokenRepository();
    }



    public boolean verifyToken(String token) throws ParseException, JOSEException {


        String secretKey = AppConfig.getInstance().getProperty("app.jwt.secret-key");

        SignedJWT signedJWT = SignedJWT.parse(token);

        if(invalidateTokenRepository.isExist(signedJWT.getJWTClaimsSet().getJWTID()))
            return false;

        JWSVerifier verifier = new MACVerifier(secretKey.getBytes());

        if(!signedJWT.verify(verifier)) return false;

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        if(expirationTime == null || expirationTime.before(new Date())) return false;

        return signedJWT.verify(verifier);
    }
}

