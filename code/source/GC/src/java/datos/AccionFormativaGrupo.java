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
@Table(name = "ACCION_FORMATIVA_GRUPO",  schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccionFormativaGrupo.findAll", query = "SELECT a FROM AccionFormativaGrupo a"),
    @NamedQuery(name = "AccionFormativaGrupo.findById", query = "SELECT a FROM AccionFormativaGrupo a WHERE a.id = :id"),
    @NamedQuery(name = "AccionFormativaGrupo.findByNombre", query = "SELECT a FROM AccionFormativaGrupo a WHERE a.nombre = :nombre")})
public class AccionFormativaGrupo implements Serializable {
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
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accionFormativaGrupo")
    private List<AccionFormativa> accionFormativaList;

    public AccionFormativaGrupo() {
    }

    public AccionFormativaGrupo(String id) {
        this.id = id;
    }

    public AccionFormativaGrupo(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        if (!(object instanceof AccionFormativaGrupo)) {
            return false;
        }
        AccionFormativaGrupo other = (AccionFormativaGrupo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.AccionFormativaGrupo[ id=" + id + " ]";
    }
    
}
