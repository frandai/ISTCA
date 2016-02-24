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
public class BooleanGenerarDoc implements Serializable {

    private boolean generar_datos_curso_alumno, generar_ficha_registro_alumno, generar_factura, generar_informe_fin_curso, generar_diploma, generar_recibi_diploma, generar_carta_rep_legal, generar_acreditacion, generar_recibi_material, generar_RLT;
    private boolean generar_control_asistencia;
    private boolean generar_cartel_FEFE;
    private boolean generar_autorizacion;
    private int generar_matricula = 0;

    public boolean isTodosFalse() {
        if (!generar_datos_curso_alumno
                && !generar_ficha_registro_alumno
                && !generar_factura
                && !generar_informe_fin_curso
                && !generar_diploma
                && !generar_recibi_diploma
                && !generar_carta_rep_legal
                && !generar_acreditacion
                && !generar_recibi_material
                && !generar_control_asistencia
                && !generar_cartel_FEFE
                && !generar_autorizacion
                && !generar_RLT
                && (generar_matricula==0)) {
            return true;
        }
        return false;
    }

    public int getGenerar_matricula() {
        return generar_matricula;
    }

    public void setGenerar_matricula(int generar_matricula) {
        this.generar_matricula = generar_matricula;
    }

    public boolean isGenerar_RLT() {
        return generar_RLT;
    }

    public void setGenerar_RLT(boolean generar_RLT) {
        this.generar_RLT = generar_RLT;
    }

    public boolean isGenerar_autorizacion() {
        return generar_autorizacion;
    }

    public void setGenerar_autorizacion(boolean generar_autorizacion) {
        this.generar_autorizacion = generar_autorizacion;
    }

    public boolean isGenerar_control_asistencia() {
        return generar_control_asistencia;
    }

    public void setGenerar_control_asistencia(boolean generar_control_asistencia) {
        this.generar_control_asistencia = generar_control_asistencia;
    }

    public boolean isGenerar_cartel_FEFE() {
        return generar_cartel_FEFE;
    }

    public void setGenerar_cartel_FEFE(boolean generar_cartel_FEFE) {
        this.generar_cartel_FEFE = generar_cartel_FEFE;
    }

    public boolean isGenerar_datos_curso_alumno() {
        return generar_datos_curso_alumno;
    }

    public void setGenerar_datos_curso_alumno(boolean generar_datos_curso_alumno) {
        this.generar_datos_curso_alumno = generar_datos_curso_alumno;
    }

    public boolean isGenerar_ficha_registro_alumno() {
        return generar_ficha_registro_alumno;
    }

    public void setGenerar_ficha_registro_alumno(boolean generar_ficha_registro_alumno) {
        this.generar_ficha_registro_alumno = generar_ficha_registro_alumno;
    }

    public boolean isGenerar_factura() {
        return generar_factura;
    }

    public void setGenerar_factura(boolean generar_factura) {
        this.generar_factura = generar_factura;
    }

    public boolean isGenerar_informe_fin_curso() {
        return generar_informe_fin_curso;
    }

    public void setGenerar_informe_fin_curso(boolean generar_informe_fin_curso) {
        this.generar_informe_fin_curso = generar_informe_fin_curso;
    }

    public boolean isGenerar_diploma() {
        return generar_diploma;
    }

    public void setGenerar_diploma(boolean generar_diploma) {
        this.generar_diploma = generar_diploma;
    }

    public boolean isGenerar_recibi_diploma() {
        return generar_recibi_diploma;
    }

    public void setGenerar_recibi_diploma(boolean generar_recibi_diploma) {
        this.generar_recibi_diploma = generar_recibi_diploma;
    }

    public boolean isGenerar_carta_rep_legal() {
        return generar_carta_rep_legal;
    }

    public void setGenerar_carta_rep_legal(boolean generar_carta_rep_legal) {
        this.generar_carta_rep_legal = generar_carta_rep_legal;
    }

    public boolean isGenerar_acreditacion() {
        return generar_acreditacion;
    }

    public void setGenerar_acreditacion(boolean generar_acreditacion) {
        this.generar_acreditacion = generar_acreditacion;
    }

    public boolean isGenerar_recibi_material() {
        return generar_recibi_material;
    }

    public void setGenerar_recibi_material(boolean generar_recibi_material) {
        this.generar_recibi_material = generar_recibi_material;
    }
}
