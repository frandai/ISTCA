/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 *
 */
@Entity
@Table(schema = "public", name = "EVENTO_EMPRESA_TIPO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventoEmpresaTipo.findAll", query = "SELECT e FROM EventoEmpresaTipo e"),
    @NamedQuery(name = "EventoEmpresaTipo.findByNombreEvento", query = "SELECT e FROM EventoEmpresaTipo e WHERE e.nombreEvento = :nombreEvento")})
public class EventoEmpresaTipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_evento")
    private String nombreEvento;

    public EventoEmpresaTipo() {
    }

    public EventoEmpresaTipo(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreEvento != null ? nombreEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventoEmpresaTipo)) {
            return false;
        }
        EventoEmpresaTipo other = (EventoEmpresaTipo) object;
        if ((this.nombreEvento == null && other.nombreEvento != null) || (this.nombreEvento != null && !this.nombreEvento.equals(other.nombreEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.EventoEmpresaTipo[ nombreEvento=" + nombreEvento + " ]";
    }
    
}
