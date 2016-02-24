/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import util.Fecha;
import vista.UtilidadesVista;

/**
 *
 *
 */
@Entity
@Table(name = "EMPRESA_MATRICULA", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpresaMatricula.findAll", query = "SELECT e FROM EmpresaMatricula e"),
    @NamedQuery(name = "EmpresaMatricula.findByNif", query = "SELECT e FROM EmpresaMatricula e WHERE e.nif = :nif"),
    @NamedQuery(name = "EmpresaMatricula.findByFechaCreacion", query = "SELECT e FROM EmpresaMatricula e WHERE e.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "EmpresaMatricula.findByRepresentacionLegal", query = "SELECT e FROM EmpresaMatricula e WHERE e.representacionLegal = :representacionLegal"),
    @NamedQuery(name = "EmpresaMatricula.findByCotSs", query = "SELECT e FROM EmpresaMatricula e WHERE e.cotSs = :cotSs")})
public class EmpresaMatricula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String nif;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPRESENTACION_LEGAL", nullable = false)
    private boolean representacionLegal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "COT_SS", nullable = false, length = 2147483647)
    private String cotSs;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresaMatricula")
    private List<EmpresaMatriculaCcc> empresaMatriculaCccList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresaMatricula")
    private List<EmpresaMatriculaAnio> empresaMatriculaAnioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresaMatricula")
    private List<EmpresaMatriculaCredito> empresaMatriculaCreditoList;
    @JoinColumn(name = "AGRUPACION", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private EmpresaAgrupacion agrupacion;
    @JoinColumn(name = "NIF", referencedColumnName = "NIF", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Empresa empresa;
    @JoinColumn(name = "C_COLECTIVO", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private CColectivo cColectivo;

    public List<EmpresaMatriculaCredito> getEmpresaMatriculaCreditoList() {
        return empresaMatriculaCreditoList;
    }

    public void setEmpresaMatriculaCreditoList(List<EmpresaMatriculaCredito> empresaMatriculaCreditoList) {
        this.empresaMatriculaCreditoList = empresaMatriculaCreditoList;
    }

    public EmpresaMatricula() {
    }

    public EmpresaMatricula(String nif) {
        this.nif = nif;
    }

    public EmpresaMatricula(String nif, boolean representacionLegal, String cotSs) {
        this.nif = nif;
        this.representacionLegal = representacionLegal;
        this.cotSs = cotSs;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public String getFechaCreacionString() {
        return Fecha.getFechaDiaMesAnio(fechaCreacion);
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean getRepresentacionLegal() {
        return representacionLegal;
    }

    public void setRepresentacionLegal(boolean representacionLegal) {
        this.representacionLegal = representacionLegal;
    }

    public String getCotSs() {
        return cotSs;
    }

    public void setCotSs(String cotSs) {
        this.cotSs = cotSs;
    }

    public boolean isMuestraCambiaMat() {
        if (empresaMatriculaCccList != null) {
            for (EmpresaMatriculaCcc empresaMatriculaCcc : empresaMatriculaCccList) {
                if (empresaMatriculaCcc.getMatriculaList() != null && !empresaMatriculaCcc.getMatriculaList().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    @XmlTransient
    public List<EmpresaMatriculaCcc> getEmpresaMatriculaCccList() {
        return empresaMatriculaCccList;
    }

    public void setEmpresaMatriculaCccList(List<EmpresaMatriculaCcc> empresaMatriculaCccList) {
        this.empresaMatriculaCccList = empresaMatriculaCccList;
    }

    @XmlTransient
    public List<EmpresaMatriculaAnio> getEmpresaMatriculaAnioList() {
        return empresaMatriculaAnioList;
    }

    public void setEmpresaMatriculaAnioList(List<EmpresaMatriculaAnio> empresaMatriculaAnioList) {
        this.empresaMatriculaAnioList = empresaMatriculaAnioList;
    }

    public EmpresaAgrupacion getAgrupacion() {
        return agrupacion;
    }

    public void setAgrupacion(EmpresaAgrupacion agrupacion) {
        this.agrupacion = agrupacion;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public CColectivo getCColectivo() {
        return cColectivo;
    }

    public void setCColectivo(CColectivo cColectivo) {
        this.cColectivo = cColectivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nif != null ? nif.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresaMatricula)) {
            return false;
        }
        EmpresaMatricula other = (EmpresaMatricula) object;
        if ((this.nif == null && other.nif != null) || (this.nif != null && !this.nif.equals(other.nif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.EmpresaMatricula[ nif=" + nif + " ]";
    }
}
