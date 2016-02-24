/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 *
 */
@Entity
@Table(name = "EMPRESA_TELEFONO",  schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpresaTelefono.findAll", query = "SELECT e FROM EmpresaTelefono e"),
    @NamedQuery(name = "EmpresaTelefono.findByNumero", query = "SELECT e FROM EmpresaTelefono e WHERE e.empresaTelefonoPK.numero = :numero"),
    @NamedQuery(name = "EmpresaTelefono.findByEmpresa", query = "SELECT e FROM EmpresaTelefono e WHERE e.empresaTelefonoPK.empresa = :empresa")})
public class EmpresaTelefono implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmpresaTelefonoPK empresaTelefonoPK;
    @JoinColumn(name = "TIPO", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private TelefonoTipo tipo;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "NIF", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empresa empresa1;

    public EmpresaTelefono() {
    }

    public EmpresaTelefono(EmpresaTelefonoPK empresaTelefonoPK) {
        this.empresaTelefonoPK = empresaTelefonoPK;
    }

    public EmpresaTelefono(String numero, String empresa) {
        this.empresaTelefonoPK = new EmpresaTelefonoPK(numero, empresa);
    }

    public EmpresaTelefonoPK getEmpresaTelefonoPK() {
        return empresaTelefonoPK;
    }

    public void setEmpresaTelefonoPK(EmpresaTelefonoPK empresaTelefonoPK) {
        this.empresaTelefonoPK = empresaTelefonoPK;
    }

    public TelefonoTipo getTipo() {
        return tipo;
    }

    public void setTipo(TelefonoTipo tipo) {
        this.tipo = tipo;
    }

    public Empresa getEmpresa1() {
        return empresa1;
    }

    public void setEmpresa1(Empresa empresa1) {
        this.empresa1 = empresa1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empresaTelefonoPK != null ? empresaTelefonoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresaTelefono)) {
            return false;
        }
        EmpresaTelefono other = (EmpresaTelefono) object;
        if ((this.empresaTelefonoPK == null && other.empresaTelefonoPK != null) || (this.empresaTelefonoPK != null && !this.empresaTelefonoPK.equals(other.empresaTelefonoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.EmpresaTelefono[ empresaTelefonoPK=" + empresaTelefonoPK + " ]";
    }
    
}