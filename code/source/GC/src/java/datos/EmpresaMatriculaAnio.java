/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import util.Fecha;

/**
 *
 *
 */
@Entity
@Table(name = "EMPRESA_MATRICULA_ANIO", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpresaMatriculaAnio.findAll", query = "SELECT e FROM EmpresaMatriculaAnio e"),
    @NamedQuery(name = "EmpresaMatriculaAnio.findByNif", query = "SELECT e FROM EmpresaMatriculaAnio e WHERE e.empresaMatriculaAnioPK.nif = :nif"),
    @NamedQuery(name = "EmpresaMatriculaAnio.findByAnio", query = "SELECT e FROM EmpresaMatriculaAnio e WHERE e.empresaMatriculaAnioPK.anio = :anio"),
    @NamedQuery(name = "EmpresaMatriculaAnio.findByPlantilla", query = "SELECT e FROM EmpresaMatriculaAnio e WHERE e.plantilla = :plantilla"),
    @NamedQuery(name = "EmpresaMatriculaAnio.findByCreadoCentro", query = "SELECT e FROM EmpresaMatriculaAnio e WHERE e.creadoCentro = :creadoCentro"),
    @NamedQuery(name = "EmpresaMatriculaAnio.findByRecibeFormacion", query = "SELECT e FROM EmpresaMatriculaAnio e WHERE e.recibeFormacion = :recibeFormacion")})
public class EmpresaMatriculaAnio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmpresaMatriculaAnioPK empresaMatriculaAnioPK;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer plantilla;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREADO_CENTRO", nullable = false)
    private boolean creadoCentro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECIBE_FORMACION", nullable = false)
    private boolean recibeFormacion;
    @JoinColumn(name = "NIF", referencedColumnName = "NIF", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EmpresaMatricula empresaMatricula;
    @Basic
    @Column(name = "CREDITO_ASIGNADO")
    private Double creditoAsignado;
    @Transient //Se muestra el Crédito disponible de la empresa
    Double creditoDisponible;
    @Transient //Variable para cargar los créditos disponibles de la emrpesa en el año de EmpresaMatrículaAnio
    List<EmpresaMatriculaCredito> cargaCreditoAnio;

    public Double getCreditoDisponible() {
        if (creditoDisponible == null) {
            creditoDisponible = 0.0;
            try {
                if (getCreditoAsignado() != null) {
                    creditoDisponible = getCreditoAsignado();
                }
                EmpresaMatriculaCredito emcSel = null;
                for (EmpresaMatriculaCredito emcr : getCargaCreditoAnio()) {
                    if (emcSel == null || emcSel.getEmpresaMatriculaCreditoPK().getFecha().before(emcr.getEmpresaMatriculaCreditoPK().getFecha())) {
                        emcSel = emcr;
                    }
                }
                if (emcSel != null) {
                    creditoDisponible = emcSel.getCreditoDisponible();
                }
                for (EmpresaMatriculaCcc p : this.getEmpresaMatricula().getEmpresaMatriculaCccList()) {
                    if (p.getMatriculaList() != null) {
                        for (Matricula m : p.getMatriculaList()) {
                            if (!m.getBaja()
                                    && (m.getImporteBonificar() == null
                                    || (emcSel == null
                                    && Fecha.getAnio(m.getImporteBonificarFecha() != null ? m.getImporteBonificarFecha() : m.getFechaCreacion()) == this.getEmpresaMatriculaAnioPK().getAnio())
                                    || (emcSel != null && m.getImporteBonificar() != null && m.getImporteBonificarFecha() != null
                                    && emcSel.getEmpresaMatriculaCreditoPK().getFecha().before(m.getImporteBonificarFecha())))) {
                                creditoDisponible -= m.getPrecio();
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
            creditoDisponible = util.Validacion.Redondear(creditoDisponible);
        }
        return creditoDisponible;
    }

    public List<EmpresaMatriculaCredito> getCargaCreditoAnio() {
        if (cargaCreditoAnio == null) {
            List<EmpresaMatriculaCredito> credito = new ArrayList<EmpresaMatriculaCredito>();
            try {
                if (getEmpresaMatricula().getEmpresaMatriculaCreditoList() != null) {
                    for (EmpresaMatriculaCredito empresaMatriculaCredito : getEmpresaMatricula().getEmpresaMatriculaCreditoList()) {
                        if (Fecha.getAnio(empresaMatriculaCredito.getEmpresaMatriculaCreditoPK().getFecha()) == this.getEmpresaMatriculaAnioPK().getAnio()) {
                            credito.add(empresaMatriculaCredito);
                        }
                    }
                }
            } catch (Exception e) {
            }
            cargaCreditoAnio = credito;
        }
        return cargaCreditoAnio;
    }

    public Double getCreditoAsignado() {
        return creditoAsignado;
    }

    public void setCreditoAsignado(Double creditoAsignado) {
        creditoDisponible = null;
        this.creditoAsignado = creditoAsignado;
    }

    public EmpresaMatriculaAnio() {
    }

    public EmpresaMatriculaAnio(EmpresaMatriculaAnioPK empresaMatriculaAnioPK) {
        this.empresaMatriculaAnioPK = empresaMatriculaAnioPK;
    }

    public EmpresaMatriculaAnio(EmpresaMatriculaAnioPK empresaMatriculaAnioPK, Integer plantilla, boolean creadoCentro, boolean recibeFormacion) {
        this.empresaMatriculaAnioPK = empresaMatriculaAnioPK;
        this.plantilla = plantilla;
        this.creadoCentro = creadoCentro;
        this.recibeFormacion = recibeFormacion;
    }

    public EmpresaMatriculaAnio(String nif, int anio) {
        this.empresaMatriculaAnioPK = new EmpresaMatriculaAnioPK(nif, anio);
    }

    public EmpresaMatriculaAnioPK getEmpresaMatriculaAnioPK() {
        return empresaMatriculaAnioPK;
    }

    public void setEmpresaMatriculaAnioPK(EmpresaMatriculaAnioPK empresaMatriculaAnioPK) {
        this.empresaMatriculaAnioPK = empresaMatriculaAnioPK;
    }

    public Integer getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(Integer plantilla) {
        this.plantilla = plantilla;
    }

    public boolean getCreadoCentro() {
        return creadoCentro;
    }

    public void setCreadoCentro(boolean creadoCentro) {
        this.creadoCentro = creadoCentro;
    }

    public boolean getRecibeFormacion() {
        return recibeFormacion;
    }

    public void setRecibeFormacion(boolean recibeFormacion) {
        this.recibeFormacion = recibeFormacion;
    }

    public EmpresaMatricula getEmpresaMatricula() {
        return empresaMatricula;
    }

    public void setEmpresaMatricula(EmpresaMatricula empresaMatricula) {
        this.empresaMatricula = empresaMatricula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empresaMatriculaAnioPK != null ? empresaMatriculaAnioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresaMatriculaAnio)) {
            return false;
        }
        EmpresaMatriculaAnio other = (EmpresaMatriculaAnio) object;
        if ((this.empresaMatriculaAnioPK == null && other.empresaMatriculaAnioPK != null) || (this.empresaMatriculaAnioPK != null && !this.empresaMatriculaAnioPK.equals(other.empresaMatriculaAnioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.EmpresaMatriculaAnio[ empresaMatriculaAnioPK=" + empresaMatriculaAnioPK + " ]";
    }
}
