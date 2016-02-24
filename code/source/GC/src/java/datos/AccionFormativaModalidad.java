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
@Table(name = "ACCION_FORMATIVA_MODALIDAD",  schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccionFormativaModalidad.findAll", query = "SELECT a FROM AccionFormativaModalidad a"),
    @NamedQuery(name = "AccionFormativaModalidad.findById", query = "SELECT a FROM AccionFormativaModalidad a WHERE a.id = :id"),
    @NamedQuery(name = "AccionFormativaModalidad.findByNombre", query = "SELECT a FROM AccionFormativaModalidad a WHERE a.nombre = :nombre")})
public class AccionFormativaModalidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Short id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modalidad")
    private List<AccionFormativa> accionFormativaList;

    public AccionFormativaModalidad() {
    }

    public AccionFormativaModalidad(Short id) {
        this.id = id;
    }

    public AccionFormativaModalidad(Short id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<AccionFormativa> getAccionFormativaList() {
        return accionFormativaList;
    }

    public void setAccionFormativaList(List<AccionFormativa> accionFormativaList) {
        this.accionFormativaList = accionFormativaList;
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
        if (!(object instanceof AccionFormativaModalidad)) {
            return false;
        }
        AccionFormativaModalidad other = (AccionFormativaModalidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.AccionFormativaModalidad[ id=" + id + " ]";
    }
    
}
