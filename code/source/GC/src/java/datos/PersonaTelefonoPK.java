/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 *
 */
@Embeddable
public class PersonaTelefonoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String numero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String persona;

    public PersonaTelefonoPK() {
    }

    public PersonaTelefonoPK(String numero, String persona) {
        this.numero = numero;
        this.persona = persona;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numero != null ? numero.hashCode() : 0);
        hash += (persona != null ? persona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaTelefonoPK)) {
            return false;
        }
        PersonaTelefonoPK other = (PersonaTelefonoPK) object;
        if ((this.numero == null && other.numero != null) || (this.numero != null && !this.numero.equals(other.numero))) {
            return false;
        }
        if ((this.persona == null && other.persona != null) || (this.persona != null && !this.persona.equals(other.persona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.PersonaTelefonoPK[ numero=" + numero + ", persona=" + persona + " ]";
    }
    
}
