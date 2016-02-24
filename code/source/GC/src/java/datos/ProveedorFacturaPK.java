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
public class ProveedorFacturaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String proveedor;

    public ProveedorFacturaPK() {
    }

    public ProveedorFacturaPK(String id, String proveedor) {
        this.id = id;
        this.proveedor = proveedor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (proveedor != null ? proveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProveedorFacturaPK)) {
            return false;
        }
        ProveedorFacturaPK other = (ProveedorFacturaPK) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.proveedor == null && other.proveedor != null) || (this.proveedor != null && !this.proveedor.equals(other.proveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.ProveedorFacturaPK[ id=" + id + ", proveedor=" + proveedor + " ]";
    }
    
}
