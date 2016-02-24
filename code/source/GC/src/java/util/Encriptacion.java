/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
public class Encriptacion {

    public static String getMD5(String cadena) {
        try {
            MessageDigest md;

            md = MessageDigest.getInstance("MD5");

            byte[] b = md.digest(cadena.getBytes());

            int size = b.length;
            StringBuilder h = new StringBuilder(size);
            for (int i = 0; i < size; i++) {

                int u = b[i] & 255;

                if (u < 16) {
                    h.append("0").append(Integer.toHexString(u));
                } else {
                    h.append(Integer.toHexString(u));
                }
            }
            return h.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encriptacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
