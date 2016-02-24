/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;

/**
 *
 *
 */
public class ParStringBoolean implements Serializable {
    private String s;
    private boolean b;

    public ParStringBoolean(String s, boolean b) {
        this.s = s;
        this.b = b;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }
    
}
