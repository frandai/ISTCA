/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion;

import bean.PermisoFacade;
import bean.PersonaFacade;
import bean.UsuarioFacade;
import datos.Permiso;
import datos.Persona;
import datos.Usuario;
import datosVista.Personausuariovista;
import datosVistaFacade.PersonausuariovistaFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import lazyDataModel.LazyPersonaUsuarioVistaDataModel;
import org.primefaces.model.LazyDataModel;
import util.Encriptacion;
import util.Fecha;
import util.Validacion;
import vista.SesionActual;
import vista.UtilidadesVista;

/**
 *
 *dai
 */
@ManagedBean(name = "usuarioGestion")
@ViewScoped
public class UsuarioGestion implements Serializable {

    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private PersonausuariovistaFacade personauFacade;
    @EJB
    private PersonaFacade personaFacade;
    @EJB
    private PermisoFacade permisoFacade;
    @ManagedProperty("#{sesionActual}")
    private SesionActual sesionActual;
    private LazyDataModel<Personausuariovista> personasAsociarUsuario;
    private String nuevaPassword;
    private String nuevaPassword2;
    private List<Permiso> permisos;
    private List<Permiso> permiSel;
    private boolean modoAlta = false;
    private Usuario usuarioSeleccionado;
    private boolean quiereCambiarPassword;
    private boolean borrado;
    private boolean modoCrearAdministrador = false;
    private boolean modoCrearAdministradorGuardado = false;
    @ManagedProperty("#{configuracionSistemaGestion}")
    private ConfiguracionSistemaGestion configSisGest;
    private String Email;

    public String getEmail() {
        getUsuarioSeleccionado();
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public boolean isModoCrearAdministrador() {
        return modoCrearAdministrador;
    }

    public void setModoCrearAdministrador(boolean modoCrearAdministrador) {
        this.modoCrearAdministrador = modoCrearAdministrador;
    }

    public boolean isModoCrearAdministradorGuardado() {
        return modoCrearAdministradorGuardado;
    }

    public void setModoCrearAdministradorGuardado(boolean modoCrearAdministradorGuardado) {
        this.modoCrearAdministradorGuardado = modoCrearAdministradorGuardado;
    }

    public ConfiguracionSistemaGestion getConfigSisGest() {
        return configSisGest;
    }

    public void setConfigSisGest(ConfiguracionSistemaGestion configSisGest) {
        this.configSisGest = configSisGest;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    public void elimina(ActionEvent event) throws IOException {
        usuarioFacade.remove(usuarioSeleccionado);
        System.out.println("[BORRAR][" + Fecha.getFechaAnioMesDiaHoraMinutoSegundo(new Date()) + "]Se ha Borrado el usuario " + this.getUsuarioSeleccionado().getUsuario());
        borrado = true;
        UtilidadesVista.Mensaje("El usuario se ha eliminado", FacesMessage.SEVERITY_INFO);
    }

    public List<Permiso> getPermiSel() {
        return permiSel;
    }

    public void setPermiSel(List<Permiso> permiSel) {
        this.permiSel = permiSel;
    }

    public boolean isQuiereCambiarPassword() {
        return quiereCambiarPassword;
    }

    public void setQuiereCambiarPassword(boolean quiereCambiarPassword) {
        this.quiereCambiarPassword = quiereCambiarPassword;
    }

    public SesionActual getSesionActual() {
        return sesionActual;
    }

    public void setSesionActual(SesionActual sesionActual) {
        this.sesionActual = sesionActual;
    }

    public UsuarioGestion() {
        modoAlta = (UtilidadesVista.getParametroReq("usuario") == null);
        modoCrearAdministrador = (UtilidadesVista.getParametroReq("crearUsuario") != null);
    }

    public Usuario getUsuarioSeleccionado() {

        if (usuarioSeleccionado == null) {
            if (!sesionActual.tienePermiso(16)) {
                usuarioSeleccionado = usuarioFacade.find(sesionActual.getUsuario_conectado().getNif());
                modoAlta = false;
            } else {
                String usuario = UtilidadesVista.getParametroReq("usuario");
                if (usuario != null) {
                    usuarioSeleccionado = usuarioFacade.find(usuario);
                    Email = usuarioSeleccionado.getPersona().getEmail();
                } else {
                    usuarioSeleccionado = new Usuario();
                    usuarioSeleccionado.setAccesible(true);
                    usuarioSeleccionado.setPersona(new Persona());
                }
            }
        }
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public LazyDataModel<Personausuariovista> getPersonasAsociarUsuario() {
        if (personasAsociarUsuario == null) {
            personasAsociarUsuario = new LazyPersonaUsuarioVistaDataModel(personauFacade);
        }
        return personasAsociarUsuario;
    }

    public String getNuevaPassword() {
        return nuevaPassword;
    }

    public void setNuevaPassword(String nuevaPassword) {
        this.nuevaPassword = nuevaPassword;
    }

    public String getNuevaPassword2() {
        return nuevaPassword2;
    }

    public void setNuevaPassword2(String nuevaPassword2) {
        this.nuevaPassword2 = nuevaPassword2;
    }

    public List<Permiso> getPermisos() {
        permisos = permisoFacade.findAll();
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public Permiso getBorraPermiso() {
        return null;
    }

    ;
    public Permiso getAniadePermiso() {
        return null;
    }

    ;
    
    public void setBorraPermiso(Permiso permiso) {
        if (usuarioSeleccionado.getPermisoList() == null) {
            usuarioSeleccionado.setPermisoList(new ArrayList<Permiso>());
        }
        usuarioSeleccionado.getPermisoList().remove(permiso);
    }

    public void setAniadePermiso(Permiso permiso) {
        if (usuarioSeleccionado.getPermisoList() == null) {
            usuarioSeleccionado.setPermisoList(new ArrayList<Permiso>());
        }
        usuarioSeleccionado.getPermisoList().add(permiso);
    }

    public void guarda(ActionEvent event) {
        try {
            boolean valido = true;
            if ((nuevaPassword != null && !nuevaPassword.equals("")) || (nuevaPassword2 != null && !nuevaPassword2.equals(""))) {
                if (nuevaPassword.length() < 4) {
                    UtilidadesVista.Mensaje("La contraseña debe tener al menos 4 caracteres.", FacesMessage.SEVERITY_ERROR);
                    valido = false;
                }
                if (!nuevaPassword.equals(nuevaPassword2)) {
                    UtilidadesVista.Mensaje("Las contraseñas no coinciden.", FacesMessage.SEVERITY_ERROR);
                    valido = false;
                }
                if (valido) {
                    usuarioSeleccionado.setPassword(Encriptacion.getMD5(nuevaPassword));
                }
            } else if (modoAlta) {
                UtilidadesVista.Mensaje("Debe de introducir obligatoriamente una contraseña.", FacesMessage.SEVERITY_ERROR);
                valido = false;
            }
            if (usuarioSeleccionado.getPersona() == null) {
                UtilidadesVista.Mensaje("Debe asociar el usuario a una persona.", FacesMessage.SEVERITY_ERROR);
                valido = false;
            }
            if (getEmail() == null || !Validacion.validaEmail(getEmail())) {
                UtilidadesVista.Mensaje("La persona debe tener un e-mail válido asociado.", FacesMessage.SEVERITY_ERROR);
                valido = false;
            } else {
                usuarioSeleccionado.getPersona().setEmail(getEmail());
            }
            if (valido) {

                if (modoCrearAdministrador) {
                    if (usuarioSeleccionado.getPermisoList() == null) {
                        usuarioSeleccionado.setPermisoList(new ArrayList<Permiso>());
                    }
                    List<Permiso> perm = getPermisos();
                    usuarioSeleccionado.getPermisoList().addAll(perm);
                    for (Permiso permiso : perm) {
                        if (permiso.getId() == 22 || permiso.getId() == 23 || permiso.getId() == 24) {
                            usuarioSeleccionado.getPermisoList().remove(permiso);
                        }
                    }
                }

                usuarioSeleccionado.setUsuario(usuarioSeleccionado.getUsuario().toLowerCase());
                if (modoAlta) {
                    usuarioSeleccionado = usuarioFacade.create(usuarioSeleccionado);
                    modoAlta = false;
                } else {
                    usuarioSeleccionado = usuarioFacade.edit(usuarioSeleccionado);
                    if (sesionActual.getUsuario_conectado().getNif().equals(this.getUsuarioSeleccionado().getNif())) {
                        sesionActual.setUsuario_conectado(usuarioSeleccionado);
                    }
                    personaFacade.edit(usuarioSeleccionado.getPersona());
                }
                UtilidadesVista.Mensaje("El usuario se ha guardado.", FacesMessage.SEVERITY_INFO);
                if (modoCrearAdministrador) {
                    getConfigSisGest().setExisteUsuarioAdministrador(true);
                    getConfigSisGest().setExistePersonaAdministrador(true);
                    modoCrearAdministradorGuardado = true;
                }
            }
        } catch (Exception e) {
            String c = "";
            for (StackTraceElement t : e.getStackTrace()) {
                if (t.getMethodName().contains("guarda")) {
                    c += t;
                }
            }
            UtilidadesVista.Mensaje(e.getMessage() + ": " + e.getCause() + " - " + c, FacesMessage.SEVERITY_FATAL);
        }
    }

    public boolean isModoAlta() {
        return modoAlta;
    }

    public void setModoAlta(boolean modoAlta) {
        this.modoAlta = modoAlta;
    }
}
