/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 *
 */
@Entity
@Table(name = "FACTURA_ESTADO",  schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaEstado.findAll", query = "SELECT f FROM FacturaEstado f"),
    @NamedQuery(name = "FacturaEstado.findById", query = "SELECT f FROM FacturaEstado f WHERE f.id = :id"),
    @NamedQuery(name = "FacturaEstado.findByNombre", query = "SELECT f FROM FacturaEstado f WHERE f.nombre = :nombre")})
public class FacturaEstado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado")
    private List<FacturaHistoricoEstado> facturaHistoricoEstadoList;

    public FacturaEstado() {
    }

    public FacturaEstado(Integer id) {
        this.id = id;
    }

    public FacturaEstado(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<FacturaHistoricoEstado> getFacturaHistoricoEstadoList() {
        return facturaHistoricoEstadoList;
    }

    public void setFacturaHistoricoEstadoList(List<FacturaHistoricoEstado> facturaHistoricoEstadoList) {
        this.facturaHistoricoEstadoList = facturaHistoricoEstadoList;
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
        if (!(object instanceof FacturaEstado)) {
            return false;
        }
        FacturaEstado other = (FacturaEstado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.FacturaEstado[ id=" + id + " ]";
    }
    
}
