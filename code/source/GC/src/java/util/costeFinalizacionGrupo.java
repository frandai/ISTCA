/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 *
 */
public class costeFinalizacionGrupo {

    private double directos = 0;
    private double asociados = 0;
    private double salariales = 0;
    private int mes;
    private double importe_mes = 0;

    public double getDirectos() {
        return directos;
    }

    public void setDirectos(double directos) {
        this.directos = directos;
    }

    public double getAsociados() {
        return asociados;
    }

    public void setAsociados(double asociados) {
        this.asociados = asociados;
    }

    public double getSalariales() {
        return salariales;
    }

    public void setSalariales(double salariales) {
        this.salariales = salariales;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public double getImporte_mes() {
        return importe_mes;
    }

    public void setImporte_mes(double importe_mes) {
        this.importe_mes = importe_mes;
    }
}
