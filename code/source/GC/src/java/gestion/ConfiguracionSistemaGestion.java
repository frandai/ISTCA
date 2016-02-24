/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion;

import bean.PersonaFacade;
import bean.UsuarioFacade;
import bean.VariablesSistemaFacade;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import datos.VariablesSistema;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import vista.UtilidadesVista;

/**
 *
 *
 */
@ManagedBean(name = "configuracionSistemaGestion")
@SessionScoped
public class ConfiguracionSistemaGestion implements Serializable {

    private Boolean existePersonaAdministrador;
    private Boolean existeUsuarioAdministrador;
    private Boolean existeProveedorPrincipal;
    private Boolean guardado;
    @EJB
    private VariablesSistemaFacade variablesSistemaFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    private VariablesSistema sistema;

    public void elimina(ActionEvent event) throws IOException {
        Process p = Runtime.getRuntime().exec("/RESTAURAR_BD/restaurar");
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((stdInput.readLine()) != null) {
            //System.out.println(s);
        }
    }

    public boolean isExistePersonaAdministrador() {
        if (existePersonaAdministrador == null) {
            try {
                existePersonaAdministrador = (usuarioFacade.findAll().get(0) != null && usuarioFacade.findAll().get(0).getPersona() != null);

            } catch (Exception e) {
                existePersonaAdministrador = false;
            }
        }
        return existePersonaAdministrador;
    }

    public void setExistePersonaAdministrador(boolean existePersonaAdministrador) {
        this.existePersonaAdministrador = existePersonaAdministrador;
    }

    public Boolean isGuardado() {
        if (guardado == null) {
            guardado = false;
        }
        return guardado;
    }

    public void setGuardado(Boolean guardado) {
        this.guardado = guardado;
    }

    public boolean isExisteUsuarioAdministrador() {
        if (existeUsuarioAdministrador == null) {
            try {
                existeUsuarioAdministrador = (usuarioFacade.findAll().get(0) != null);

            } catch (Exception e) {
                existeUsuarioAdministrador = false;
            }
        }
        return existeUsuarioAdministrador;
    }

    public void setExisteUsuarioAdministrador(boolean existeUsuarioAdministrador) {
        this.existeUsuarioAdministrador = existeUsuarioAdministrador;
    }

    public boolean isExisteProveedorPrincipal() {
        if (existeProveedorPrincipal == null) {
            try {
                existeProveedorPrincipal = getSistema().getNIFempresaprincipal() != null && !getSistema().getNIFempresaprincipal().equals("") && util.VariablesSistema.proveedor_principal != null;

            } catch (Exception e) {
                existeProveedorPrincipal = false;
            }
        }
        return existeProveedorPrincipal;
    }

    public void setExisteProveedorPrincipal(boolean existeProveedorPrincipal) {
        this.existeProveedorPrincipal = existeProveedorPrincipal;
    }

    public VariablesSistema getSistema() {
        if (sistema == null) {
            try {
                sistema = variablesSistemaFacade.findAll().get(0);
            } catch (Exception e) {
                sistema = new VariablesSistema();
            }
        }
        return sistema;
    }

    public void setSistema(VariablesSistema sistema) {
        this.sistema = sistema;
    }

    public void guarda() {
        boolean guarda = true;
        existePersonaAdministrador = null;
        existeProveedorPrincipal = null;
        existeUsuarioAdministrador = null;

        if (!isExistePersonaAdministrador()) {
            UtilidadesVista.Mensaje("No existe Persona Administradora del Sistema.", FacesMessage.SEVERITY_ERROR);
            guarda = false;
        }
        if (!isExisteUsuarioAdministrador()) {
            UtilidadesVista.Mensaje("No existe Usuario Administrador del Sistema.", FacesMessage.SEVERITY_ERROR);
            guarda = false;
        }
        if (!isExisteProveedorPrincipal()) {
            UtilidadesVista.Mensaje("No existe Proveedor Principal.", FacesMessage.SEVERITY_ERROR);
            guarda = false;
        }
        if (guarda) {
            variablesSistemaFacade.remove(variablesSistemaFacade.findAll().get(0));
            variablesSistemaFacade.create(sistema);
            util.VariablesSistema.NIF_empresa_principal = null;
            util.VariablesSistema.proveedor_principal = null;
            util.VariablesSistema.ruta_absoluta_proyecto = null;
            util.VariablesSistema.email_enviar = null;
            util.VariablesSistema.dominioEmail = null;
            util.VariablesSistema.firmaEmail = null;
            util.VariablesSistema.passwordEmail = null;
            UtilidadesVista.Mensaje("Se han guardado los cambios en el Sistema. Por favor, cierre sesión y conéctese a la plataforma.", FacesMessage.SEVERITY_INFO);
            guardado = true;
        }

    }

}
