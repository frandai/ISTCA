/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 *
 */
@Entity
@Table(schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Remesa.findAll", query = "SELECT r FROM Remesa r"),
    @NamedQuery(name = "Remesa.findById", query = "SELECT r FROM Remesa r WHERE r.id = :id"),
    @NamedQuery(name = "Remesa.findByArchivo", query = "SELECT r FROM Remesa r WHERE r.archivo = :archivo"),
    @NamedQuery(name = "Remesa.findByFecha", query = "SELECT r FROM Remesa r WHERE r.fecha = :fecha"),
    @NamedQuery(name = "Remesa.findByFechaCobro", query = "SELECT r FROM Remesa r WHERE r.fechaCobro = :fechaCobro")})
public class Remesa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String archivo;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_COBRO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaCobro;
    @ManyToMany(mappedBy = "remesaList")
    private List<Factura> facturaList;
    @Column(name = "agrupada")
    private Boolean agrupada;
    @Column(name = "cobrada")
    private Boolean cobrada;
    @Column(name = "num_registros")
    private Integer numRegistros;
    @Column(name = "total_remesa")
    private Double totalRemesa;

    public Integer getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(Integer numRegistros) {
        this.numRegistros = numRegistros;
    }

    public Double getTotalRemesa() {
        return totalRemesa;
    }

    public void setTotalRemesa(Double totalRemesa) {
        this.totalRemesa = totalRemesa;
    }

    public Boolean getCobrada() {
        return cobrada;
    }

    public void setCobrada(Boolean cobrada) {
        this.cobrada = cobrada;
    }

    public Boolean getAgrupada() {
        return agrupada;
    }

    public void setAgrupada(Boolean agrupada) {
        this.agrupada = agrupada;
    }

    public Remesa() {
    }

    public Remesa(String id) {
        this.id = id;
    }

    public Remesa(String id, String archivo, Date fecha, Date fechaCobro) {
        this.id = id;
        this.archivo = archivo;
        this.fecha = fecha;
        this.fechaCobro = fechaCobro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(Date fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    @XmlTransient
    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Remesa)) {
            return false;
        }
        Remesa other = (Remesa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.Remesa[ id=" + id + " ]";
    }
}
