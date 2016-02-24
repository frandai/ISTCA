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
public class MatriculaComercialPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int comercial;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int matricula;

    public MatriculaComercialPK() {
    }

    public MatriculaComercialPK(int comercial, int matricula) {
        this.comercial = comercial;
        this.matricula = matricula;
    }

    public int getComercial() {
        return comercial;
    }

    public void setComercial(int comercial) {
        this.comercial = comercial;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) comercial;
        hash += (int) matricula;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MatriculaComercialPK)) {
            return false;
        }
        MatriculaComercialPK other = (MatriculaComercialPK) object;
        if (this.comercial != other.comercial) {
            return false;
        }
        if (this.matricula != other.matricula) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.MatriculaComercialPK[ comercial=" + comercial + ", matricula=" + matricula + " ]";
    }
    
}
