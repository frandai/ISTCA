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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table( schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p"),
    @NamedQuery(name = "Proveedor.findByNif", query = "SELECT p FROM Proveedor p WHERE p.nif = :nif")})
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String nif;
    @ManyToMany(mappedBy = "proveedorList")
    private List<AccionFormativa> accionFormativaList;
    @JoinColumn(name = "NIF", referencedColumnName = "NIF", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Empresa empresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedor")
    private List<Factura> facturaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedor1")
    private List<ProveedorFactura> proveedorFacturaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedor1")
    private List<Grupo> grupoList;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GENERA_SEGUIMIENTO_TUTORIAL", nullable = false)
    private boolean generaSeguimientoTutorial;

    public boolean isGeneraSeguimientoTutorial() {
        return generaSeguimientoTutorial;
    }

    public void setGeneraSeguimientoTutorial(boolean generaSeguimientoTutorial) {
        this.generaSeguimientoTutorial = generaSeguimientoTutorial;
    }

    public Proveedor() {
    }

    public Proveedor(String nif) {
        this.nif = nif;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    @XmlTransient
    public List<AccionFormativa> getAccionFormativaList() {
        return accionFormativaList;
    }

    public void setAccionFormativaList(List<AccionFormativa> accionFormativaList) {
        this.accionFormativaList = accionFormativaList;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @XmlTransient
    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
    }

    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    @XmlTransient
    public List<ProveedorFactura> getProveedorFacturaList() {
        return proveedorFacturaList;
    }

    public void setProveedorFacturaList(List<ProveedorFactura> proveedorFacturaList) {
        this.proveedorFacturaList = proveedorFacturaList;
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
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.nif == null && other.nif != null) || (this.nif != null && !this.nif.equals(other.nif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.Proveedor[ nif=" + nif + " ]";
    }
}
