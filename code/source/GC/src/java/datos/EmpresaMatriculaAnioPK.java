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
import javax.validation.constraints.Size;

/**
 *
 *
 */
@Embeddable
public class EmpresaMatriculaAnioPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String nif;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int anio;

    public EmpresaMatriculaAnioPK() {
    }

    public EmpresaMatriculaAnioPK(String nif, int anio) {
        this.nif = nif;
        this.anio = anio;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nif != null ? nif.hashCode() : 0);
        hash += (int) anio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresaMatriculaAnioPK)) {
            return false;
        }
        EmpresaMatriculaAnioPK other = (EmpresaMatriculaAnioPK) object;
        if ((this.nif == null && other.nif != null) || (this.nif != null && !this.nif.equals(other.nif))) {
            return false;
        }
        if (this.anio != other.anio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.EmpresaMatriculaAnioPK[ nif=" + nif + ", anio=" + anio + " ]";
    }
    
}
