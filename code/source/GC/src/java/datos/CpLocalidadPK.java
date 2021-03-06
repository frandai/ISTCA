/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 *
 */
@Embeddable
public class CpLocalidadPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int cp;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int localidad;

    public CpLocalidadPK() {
    }

    public CpLocalidadPK(int cp, int localidad) {
        this.cp = cp;
        this.localidad = localidad;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public int getLocalidad() {
        return localidad;
    }

    public void setLocalidad(int localidad) {
        this.localidad = localidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cp;
        hash += (int) localidad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CpLocalidadPK)) {
            return false;
        }
        CpLocalidadPK other = (CpLocalidadPK) object;
        if (this.cp != other.cp) {
            return false;
        }
        if (this.localidad != other.localidad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.CpLocalidadPK[ cp=" + cp + ", localidad=" + localidad + " ]";
    }
    
}
