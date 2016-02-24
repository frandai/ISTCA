/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import datos.Horario;

/**
 *
 *
 */
public class horarioAuxiliarTutor {

    boolean seleccionado;
    String hora;
    Horario hor;

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Horario getHor() {
        return hor;
    }

    public void setHor(Horario hor) {
        this.hor = hor;
    }

    public horarioAuxiliarTutor(boolean seleccionado, String hora, Horario hor) {
        this.seleccionado = seleccionado;
        this.hora = hora;
        this.hor = hor;
    }
}
