/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import bean.UsuarioFacade;
import datos.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 *
 */
@ManagedBean(name = "usuarioVista")
@ViewScoped
public class UsuarioVista implements Serializable {

    @EJB
    private UsuarioFacade usuarioFacade;

    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltrados;
    private boolean muestraUsuario = true;
    private boolean muestraNIF = true;
    private boolean muestraNombre = true;
    private boolean muestraActivo = true;


    public UsuarioVista() {


    }

    public List<Usuario> getUsuarios() {
        if (usuarios == null) {
            usuarios = usuarioFacade.findAll();
        }
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Usuario> getUsuariosFiltrados() {
        return usuariosFiltrados;
    }

    public void setUsuariosFiltrados(List<Usuario> usuariosSeleccionados) {
        this.usuariosFiltrados = usuariosSeleccionados;
    }

    public boolean isMuestraUsuario() {
        return muestraUsuario;
    }

    public void setMuestraUsuario(boolean muestraUsuario) {
        this.muestraUsuario = muestraUsuario;
    }

    public boolean isMuestraNIF() {
        return muestraNIF;
    }

    public void setMuestraNIF(boolean muestraNIF) {
        this.muestraNIF = muestraNIF;
    }

    public boolean isMuestraNombre() {
        return muestraNombre;
    }

    public void setMuestraNombre(boolean muestraNombre) {
        this.muestraNombre = muestraNombre;
    }

    public boolean isMuestraActivo() {
        return muestraActivo;
    }

    public void setMuestraActivo(boolean muestraActivo) {
        this.muestraActivo = muestraActivo;
    }
}
