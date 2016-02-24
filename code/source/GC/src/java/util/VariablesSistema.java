/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import bean.MatriculaFacade;
import datos.Proveedor;
import java.io.File;
import java.util.Random;

/**
 *
 *
 */
public class VariablesSistema {

    public static String ruta_absoluta_proyecto = null;
    public static String NIF_empresa_principal = null;
    public static Proveedor proveedor_principal = null;
    public static String email_enviar = null;
    public static String dominioEmail = null;
    public static String passwordEmail = null;
    public static String firmaEmail = null;

    public static Random r = new Random();

    /**
     * Devuelve los datos (si están guardados) del email pedido. Datos
     * guardados: pruebas@grupoiwi.com
     *
     * @param email email pedido
     * @return
     */
    public static DatosEmail getDatosEmail(String email) {
        DatosEmail dat = new DatosEmail();
        dat.dominio = dominioEmail;
        dat.firma = "<br/><br/>"+firmaEmail;
        dat.password = passwordEmail;

        return dat;
    }
}
