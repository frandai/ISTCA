/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 *
 */
@Entity
@Table(name = "PROVEEDOR_FACTURA",  schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProveedorFactura.findAll", query = "SELECT p FROM ProveedorFactura p"),
    @NamedQuery(name = "ProveedorFactura.findById", query = "SELECT p FROM ProveedorFactura p WHERE p.proveedorFacturaPK.id = :id"),
    @NamedQuery(name = "ProveedorFactura.findByProveedor", query = "SELECT p FROM ProveedorFactura p WHERE p.proveedorFacturaPK.proveedor = :proveedor"),
    @NamedQuery(name = "ProveedorFactura.findByFecha", query = "SELECT p FROM ProveedorFactura p WHERE p.fecha = :fecha")})
public class ProveedorFactura implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProveedorFacturaPK proveedorFacturaPK;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "PROVEEDOR", referencedColumnName = "NIF", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proveedor proveedor1;

    public ProveedorFactura() {
    }

    public ProveedorFactura(ProveedorFacturaPK proveedorFacturaPK) {
        this.proveedorFacturaPK = proveedorFacturaPK;
    }

    public ProveedorFactura(String id, String proveedor) {
        this.proveedorFacturaPK = new ProveedorFacturaPK(id, proveedor);
    }

    public ProveedorFacturaPK getProveedorFacturaPK() {
        return proveedorFacturaPK;
    }

    public void setProveedorFacturaPK(ProveedorFacturaPK proveedorFacturaPK) {
        this.proveedorFacturaPK = proveedorFacturaPK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
 

    public Proveedor getProveedor1() {
        return proveedor1;
    }

    public void setProveedor1(Proveedor proveedor1) {
        this.proveedor1 = proveedor1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proveedorFacturaPK != null ? proveedorFacturaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProveedorFactura)) {
            return false;
        }
        ProveedorFactura other = (ProveedorFactura) object;
        if ((this.proveedorFacturaPK == null && other.proveedorFacturaPK != null) || (this.proveedorFacturaPK != null && !this.proveedorFacturaPK.equals(other.proveedorFacturaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.ProveedorFactura[ proveedorFacturaPK=" + proveedorFacturaPK + " ]";
    }
    
}
