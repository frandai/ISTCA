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
@Table(name = "ALUMNO_CATEGORIA_PROFESIONAL",  schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlumnoCategoriaProfesional.findAll", query = "SELECT a FROM AlumnoCategoriaProfesional a"),
    @NamedQuery(name = "AlumnoCategoriaProfesional.findById", query = "SELECT a FROM AlumnoCategoriaProfesional a WHERE a.id = :id"),
    @NamedQuery(name = "AlumnoCategoriaProfesional.findByNombre", query = "SELECT a FROM AlumnoCategoriaProfesional a WHERE a.nombre = :nombre")})
public class AlumnoCategoriaProfesional implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaProfesional")
    private List<Alumno> alumnoList;

    public AlumnoCategoriaProfesional() {
    }

    public AlumnoCategoriaProfesional(Short id) {
        this.id = id;
    }

    public AlumnoCategoriaProfesional(Short id, String nombre) {
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
    public List<Alumno> getAlumnoList() {
        return alumnoList;
    }

    public void setAlumnoList(List<Alumno> alumnoList) {
        this.alumnoList = alumnoList;
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
        if (!(object instanceof AlumnoCategoriaProfesional)) {
            return false;
        }
        AlumnoCategoriaProfesional other = (AlumnoCategoriaProfesional) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.AlumnoCategoriaProfesional[ id=" + id + " ]";
    }
    
}
