/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.utils;

import java.nio.charset.Charset;
import java.util.Random;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Test
 */
public class PasswordUtils {

    public static String encodePassword(String pw) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        pw = passwordEncoder.encode(pw);
        return pw;
    }

    public static String generatePassword() {
// length is bounded by 256 Character
        int n = 10;
        byte[] array = new byte[256];
        new Random().nextBytes(array);
        String randomString = new String(array, Charset.forName("UTF-8"));

        StringBuffer r = new StringBuffer();
        for (int k = 0; k < randomString.length(); k++) {

            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9'))
                    && (n > 0)) {

                r.append(ch);
                n--;
            }
        }
        return r.toString();
    }
}
