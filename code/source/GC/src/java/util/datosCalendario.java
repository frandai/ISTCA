/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import datos.EventoTipo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class datosCalendario implements Serializable {
    public List<ParStringBoolean> tiposEvento;
    public boolean estado_pendiente = true;
    public boolean estado_realizado = false;
    public boolean estado_vencido = false;
    public boolean estado_rv = false;
    public ArrayList<ParStringBoolean> numEstados;
    public boolean agenda;
    public int eventos_fecha;
}
