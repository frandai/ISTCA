/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 *
 */
@Entity
@Table(name = "PERSONA_TELEFONO",  schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonaTelefono.findAll", query = "SELECT p FROM PersonaTelefono p"),
    @NamedQuery(name = "PersonaTelefono.findByNumero", query = "SELECT p FROM PersonaTelefono p WHERE p.personaTelefonoPK.numero = :numero"),
    @NamedQuery(name = "PersonaTelefono.findByPersona", query = "SELECT p FROM PersonaTelefono p WHERE p.personaTelefonoPK.persona = :persona")})
public class PersonaTelefono implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PersonaTelefonoPK personaTelefonoPK;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personaTelefono")
    private List<Grupo> grupoList;
    @JoinColumn(name = "TIPO", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private TelefonoTipo tipo;
    @JoinColumn(name = "PERSONA", referencedColumnName = "NIF", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Persona persona1;

    public PersonaTelefono() {
    }

    public PersonaTelefono(PersonaTelefonoPK personaTelefonoPK) {
        this.personaTelefonoPK = personaTelefonoPK;
    }

    public PersonaTelefono(String numero, String persona) {
        this.personaTelefonoPK = new PersonaTelefonoPK(numero, persona);
    }

    public PersonaTelefonoPK getPersonaTelefonoPK() {
        return personaTelefonoPK;
    }

    public void setPersonaTelefonoPK(PersonaTelefonoPK personaTelefonoPK) {
        this.personaTelefonoPK = personaTelefonoPK;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    public TelefonoTipo getTipo() {
        return tipo;
    }

    public void setTipo(TelefonoTipo tipo) {
        this.tipo = tipo;
    }

    public Persona getPersona1() {
        return persona1;
    }

    public void setPersona1(Persona persona1) {
        this.persona1 = persona1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personaTelefonoPK != null ? personaTelefonoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaTelefono)) {
            return false;
        }
        PersonaTelefono other = (PersonaTelefono) object;
        if ((this.personaTelefonoPK == null && other.personaTelefonoPK != null) || (this.personaTelefonoPK != null && !this.personaTelefonoPK.equals(other.personaTelefonoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.PersonaTelefono[ personaTelefonoPK=" + personaTelefonoPK + " ]";
    }
    
}
