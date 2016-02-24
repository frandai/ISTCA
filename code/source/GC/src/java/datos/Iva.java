/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "Iva.findAll", query = "SELECT i FROM Iva i"),
    @NamedQuery(name = "Iva.findById", query = "SELECT i FROM Iva i WHERE i.id = :id"),
    @NamedQuery(name = "Iva.findByPorcentaje", query = "SELECT i FROM Iva i WHERE i.porcentaje = :porcentaje"),
    @NamedQuery(name = "Iva.findByFechaVigor", query = "SELECT i FROM Iva i WHERE i.fechaVigor = :fechaVigor")})
public class Iva implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Double porcentaje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_VIGOR", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaVigor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iva")
    private List<MatriculaFactura> matriculaFacturaList;

    public Iva() {
    }

    public Iva(Integer id) {
        this.id = id;
    }

    public Iva(Integer id, Double porcentaje, Date fechaVigor) {
        this.id = id;
        this.porcentaje = porcentaje;
        this.fechaVigor = fechaVigor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Date getFechaVigor() {
        return fechaVigor;
    }

    public void setFechaVigor(Date fechaVigor) {
        this.fechaVigor = fechaVigor;
    }

    @XmlTransient
    public List<MatriculaFactura> getMatriculaFacturaList() {
        return matriculaFacturaList;
    }

    public void setMatriculaFacturaList(List<MatriculaFactura> matriculaFacturaList) {
        this.matriculaFacturaList = matriculaFacturaList;
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
        if (!(object instanceof Iva)) {
            return false;
        }
        Iva other = (Iva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.Iva[ id=" + id + " ]";
    }
    
}
