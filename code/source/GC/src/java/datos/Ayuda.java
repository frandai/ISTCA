/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
@Table(name = "ayuda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ayuda.findAll", query = "SELECT a FROM Ayuda a"),
    @NamedQuery(name = "Ayuda.findByPagina", query = "SELECT a FROM Ayuda a WHERE a.pagina = :pagina"),
    @NamedQuery(name = "Ayuda.findByAyuda", query = "SELECT a FROM Ayuda a WHERE a.ayuda = :ayuda")})
public class Ayuda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "pagina")
    private String pagina;
    @Size(max = 2147483647)
    @Column(name = "ayuda")
    private String ayuda;

    public Ayuda() {
    }

    public Ayuda(String pagina) {
        this.pagina = pagina;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public String getAyuda() {
        return ayuda;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagina != null ? pagina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ayuda)) {
            return false;
        }
        Ayuda other = (Ayuda) object;
        if ((this.pagina == null && other.pagina != null) || (this.pagina != null && !this.pagina.equals(other.pagina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.Ayuda[ pagina=" + pagina + " ]";
    }
    
}
