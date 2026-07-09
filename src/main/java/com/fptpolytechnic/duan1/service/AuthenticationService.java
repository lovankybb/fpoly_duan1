package com.fptpolytechnic.duan1.service;


import com.fptpolytechnic.duan1.model.InvalidateToken;
import com.fptpolytechnic.duan1.model.Role;
import com.fptpolytechnic.duan1.model.User;
import com.fptpolytechnic.duan1.repository.InvalidateTokenRepository;
import com.fptpolytechnic.duan1.repository.RoleRepository;
import com.fptpolytechnic.duan1.repository.UserRepository;
import com.fptpolytechnic.duan1.utils.AppConfig;
import com.fptpolytechnic.duan1.utils.PasswordEncoder;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    InvalidateTokenRepository invalidateTokenRepository;

    public AuthenticationService(){
        userRepository = new UserRepository();
        passwordEncoder = new PasswordEncoder();
        roleRepository = new RoleRepository();
        invalidateTokenRepository = new InvalidateTokenRepository();
    }

    public User authenticate(String username, String password){

        User user = userRepository.findByUsername(username);
        System.out.println("User found: " + user);
        if(Objects.isNull(user) || !passwordEncoder.matches(password, user.getPassword())) return null;
        return user;
    }


    public String generateToken(User user) throws JOSEException {

        Set<Role> roles = roleRepository.findByUserId(user.getId());

        String roleClaim = this.convertRoles(roles);

        String secreteKey = AppConfig.getInstance().getProperty("app.jwt.secret-key");
        long duration = Long.parseLong(AppConfig.getInstance().getProperty("app.jwt.duration"));

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .subject(user.getUsername())
                .issuer("fpt-polytechnic")
                .claim("roles", roleClaim)
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(duration)))
                .build();

        SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
        MACSigner macSigner = new MACSigner(secreteKey.getBytes());
        signedJWT.sign(macSigner);

        return signedJWT.serialize();
    }


    public void logout(HttpServletRequest request, HttpServletResponse response) throws ParseException {

        Cookie [] cookies = request.getCookies();
        if(!Objects.isNull(cookies)){
            for(Cookie cookie : cookies){
                if("token".equals(cookie.getName())){

                    String token = cookie.getValue();
                    try {
                        SignedJWT signedJWT = SignedJWT.parse(token);
                        InvalidateToken invalidateToken = InvalidateToken.builder()
                                .jwtId(signedJWT.getJWTClaimsSet().getJWTID())
                                .expiredAt(signedJWT.getJWTClaimsSet().getExpirationTime())
                                .build();
                        invalidateTokenRepository.insert(invalidateToken);
                    } catch (Exception e) {
                        System.out.println("Không thể blacklist token: " + e.getMessage());
                    }


//                    Create a new cookie with the same name to override old cookie
                    Cookie deleteCookie = new Cookie("token", "");

                    deleteCookie.setPath("/");
                    deleteCookie.setValue("");
                    deleteCookie.setMaxAge(0);
                    response.addCookie(deleteCookie);

                    break;
                }
            }
        }
    }


    private String convertRoles(Set<Role> roles) {
        return roles.stream().map(Role::getName).reduce((r1, r2) -> r1 + "," + r2).orElse("");
    }
}
