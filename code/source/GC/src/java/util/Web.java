/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 *
 */
public class Web {

    public static String getWebContent(String url) throws MalformedURLException, IOException {
        URL myURL = new URL(url);
        URLConnection myURLConnection = myURL.openConnection();
        myURLConnection.connect();
        InputStreamReader in = new InputStreamReader((InputStream) myURLConnection.getContent());
        BufferedReader buff = new BufferedReader(in);
        String texto = "";
        String linea;
        do {
            linea = buff.readLine() ;
            if (linea != null) {
                texto += linea + "\n";
            }
        } while (linea != null);
        return texto;
    }
}
