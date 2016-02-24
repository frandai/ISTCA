/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 *
 */
@Entity
@Table(name = "EMPRESA_MATRICULA_CREDITO", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpresaMatriculaCredito.findAll", query = "SELECT e FROM EmpresaMatriculaCredito e"),
    @NamedQuery(name = "EmpresaMatriculaCredito.findByNif", query = "SELECT e FROM EmpresaMatriculaCredito e WHERE e.empresaMatriculaCreditoPK.nif = :nif"),
    @NamedQuery(name = "EmpresaMatriculaCredito.findByFecha", query = "SELECT e FROM EmpresaMatriculaCredito e WHERE e.empresaMatriculaCreditoPK.fecha = :fecha"),
    @NamedQuery(name = "EmpresaMatriculaCredito.findByCreditoDisponible", query = "SELECT e FROM EmpresaMatriculaCredito e WHERE e.creditoDisponible = :creditoDisponible")})
public class EmpresaMatriculaCredito implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmpresaMatriculaCreditoPK empresaMatriculaCreditoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "credito_disponible")
    private Double creditoDisponible;
    @JoinColumn(name = "NIF", referencedColumnName = "NIF", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EmpresaMatricula empresaMatricula;

    public EmpresaMatricula getEmpresaMatricula() {
        return empresaMatricula;
    }

    public void setEmpresaMatricula(EmpresaMatricula empresaMatricula) {
        this.empresaMatricula = empresaMatricula;
    }

    public EmpresaMatriculaCredito() {
    }

    public EmpresaMatriculaCredito(EmpresaMatriculaCreditoPK empresaMatriculaCreditoPK) {
        this.empresaMatriculaCreditoPK = empresaMatriculaCreditoPK;
    }

    public EmpresaMatriculaCredito(EmpresaMatriculaCreditoPK empresaMatriculaCreditoPK, Double creditoDisponible) {
        this.empresaMatriculaCreditoPK = empresaMatriculaCreditoPK;
        this.creditoDisponible = creditoDisponible;
    }

    public EmpresaMatriculaCredito(String nif, Date fecha) {
        this.empresaMatriculaCreditoPK = new EmpresaMatriculaCreditoPK(nif, fecha);
    }

    public EmpresaMatriculaCreditoPK getEmpresaMatriculaCreditoPK() {
        return empresaMatriculaCreditoPK;
    }

    public void setEmpresaMatriculaCreditoPK(EmpresaMatriculaCreditoPK empresaMatriculaCreditoPK) {
        this.empresaMatriculaCreditoPK = empresaMatriculaCreditoPK;
    }

    public Double getCreditoDisponible() {
        return creditoDisponible;
    }

    public void setCreditoDisponible(Double creditoDisponible) {
        this.creditoDisponible = creditoDisponible;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empresaMatriculaCreditoPK != null ? empresaMatriculaCreditoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresaMatriculaCredito)) {
            return false;
        }
        EmpresaMatriculaCredito other = (EmpresaMatriculaCredito) object;
        if ((this.empresaMatriculaCreditoPK == null && other.empresaMatriculaCreditoPK != null) || (this.empresaMatriculaCreditoPK != null && !this.empresaMatriculaCreditoPK.equals(other.empresaMatriculaCreditoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.EmpresaMatriculaCredito[ empresaMatriculaCreditoPK=" + empresaMatriculaCreditoPK + " ]";
    }
}
