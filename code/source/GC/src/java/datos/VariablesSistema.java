/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 *
 */
@Entity
@Table(name = "variables_sistema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VariablesSistema.findAll", query = "SELECT v FROM VariablesSistema v"),
    @NamedQuery(name = "VariablesSistema.findByNIFempresaprincipal", query = "SELECT v FROM VariablesSistema v WHERE v.nIFempresaprincipal = :nIFempresaprincipal"),
    @NamedQuery(name = "VariablesSistema.findByEmailEnviar", query = "SELECT v FROM VariablesSistema v WHERE v.emailEnviar = :emailEnviar"),
    @NamedQuery(name = "VariablesSistema.findByDominioEmail", query = "SELECT v FROM VariablesSistema v WHERE v.dominioEmail = :dominioEmail"),
    @NamedQuery(name = "VariablesSistema.findByFirmaEmail", query = "SELECT v FROM VariablesSistema v WHERE v.firmaEmail = :firmaEmail"),
    @NamedQuery(name = "VariablesSistema.findByPasswordEmail", query = "SELECT v FROM VariablesSistema v WHERE v.passwordEmail = :passwordEmail"),
    @NamedQuery(name = "VariablesSistema.findByRutaProyecto", query = "SELECT v FROM VariablesSistema v WHERE v.rutaProyecto = :rutaProyecto")})
public class VariablesSistema implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "NIF_empresa_principal")
    private String nIFempresaprincipal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "email_enviar")
    private String emailEnviar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "dominio_email")
    private String dominioEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "firma_email")
    private String firmaEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password_email")
    private String passwordEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "ruta_proyecto")
    private String rutaProyecto;

    public VariablesSistema() {
    }

    public VariablesSistema(String nIFempresaprincipal) {
        this.nIFempresaprincipal = nIFempresaprincipal;
    }

    public VariablesSistema(String nIFempresaprincipal, String emailEnviar, String dominioEmail, String firmaEmail, String passwordEmail, String rutaProyecto) {
        this.nIFempresaprincipal = nIFempresaprincipal;
        this.emailEnviar = emailEnviar;
        this.dominioEmail = dominioEmail;
        this.firmaEmail = firmaEmail;
        this.passwordEmail = passwordEmail;
        this.rutaProyecto = rutaProyecto;
    }

    public String getNIFempresaprincipal() {
        return nIFempresaprincipal;
    }

    public void setNIFempresaprincipal(String nIFempresaprincipal) {
        this.nIFempresaprincipal = nIFempresaprincipal;
    }

    public String getEmailEnviar() {
        return emailEnviar;
    }

    public void setEmailEnviar(String emailEnviar) {
        this.emailEnviar = emailEnviar;
    }

    public String getDominioEmail() {
        return dominioEmail;
    }

    public void setDominioEmail(String dominioEmail) {
        this.dominioEmail = dominioEmail;
    }

    public String getFirmaEmail() {
        return firmaEmail;
    }

    public void setFirmaEmail(String firmaEmail) {
        this.firmaEmail = firmaEmail;
    }

    public String getPasswordEmail() {
        return passwordEmail;
    }

    public void setPasswordEmail(String passwordEmail) {
        this.passwordEmail = passwordEmail;
    }

    public String getRutaProyecto() {
        return rutaProyecto;
    }

    public void setRutaProyecto(String rutaProyecto) {
        this.rutaProyecto = rutaProyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nIFempresaprincipal != null ? nIFempresaprincipal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VariablesSistema)) {
            return false;
        }
        VariablesSistema other = (VariablesSistema) object;
        if ((this.nIFempresaprincipal == null && other.nIFempresaprincipal != null) || (this.nIFempresaprincipal != null && !this.nIFempresaprincipal.equals(other.nIFempresaprincipal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.VariablesSistema[ nIFempresaprincipal=" + nIFempresaprincipal + " ]";
    }
    
}
