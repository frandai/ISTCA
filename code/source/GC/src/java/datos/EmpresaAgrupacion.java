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
@Table(name = "EMPRESA_AGRUPACION",  schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpresaAgrupacion.findAll", query = "SELECT e FROM EmpresaAgrupacion e"),
    @NamedQuery(name = "EmpresaAgrupacion.findById", query = "SELECT e FROM EmpresaAgrupacion e WHERE e.id = :id"),
    @NamedQuery(name = "EmpresaAgrupacion.findByNombre", query = "SELECT e FROM EmpresaAgrupacion e WHERE e.nombre = :nombre")})
public class EmpresaAgrupacion implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agrupacion")
    private List<EmpresaMatricula> empresaMatriculaList;

    public EmpresaAgrupacion() {
    }

    public EmpresaAgrupacion(Integer id) {
        this.id = id;
    }

    public EmpresaAgrupacion(Integer id, String nombre) {
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
    public List<EmpresaMatricula> getEmpresaMatriculaList() {
        return empresaMatriculaList;
    }

    public void setEmpresaMatriculaList(List<EmpresaMatricula> empresaMatriculaList) {
        this.empresaMatriculaList = empresaMatriculaList;
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
        if (!(object instanceof EmpresaAgrupacion)) {
            return false;
        }
        EmpresaAgrupacion other = (EmpresaAgrupacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.EmpresaAgrupacion[ id=" + id + " ]";
    }
    
}