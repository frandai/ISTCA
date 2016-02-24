/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import datos.Alumno;
import datos.EmpresaMatriculaCcc;

/**
 *
 *
 */
public class TripleSelectorAlumnoCCC {

    private boolean selec;
    private Alumno alumno;
    private EmpresaMatriculaCcc emcc;

    public boolean isSelec() {
        return selec;
    }

    public void setSelec(boolean selec) {
        this.selec = selec;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public EmpresaMatriculaCcc getEmcc() {
        return emcc;
    }

    public void setEmcc(EmpresaMatriculaCcc emcc) {
        this.emcc = emcc;
    }

    public TripleSelectorAlumnoCCC(boolean selec, Alumno alumno, EmpresaMatriculaCcc emcc) {
        this.selec = selec;
        this.alumno = alumno;
        this.emcc = emcc;
    }
}
