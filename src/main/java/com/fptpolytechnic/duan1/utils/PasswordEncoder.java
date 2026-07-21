package com.fptpolytechnic.duan1.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    public String encode(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean matches( String rawPassword, String encodedPassword){
        if (encodedPassword == null) { return false; }
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
