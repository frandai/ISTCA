/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datosVista;

import datos.Empresa;
import datos.Persona;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 *
 */
@Entity
@Table(name = "personausuariovista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personausuariovista.findAll", query = "SELECT p FROM Personausuariovista p"),
    @NamedQuery(name = "Personausuariovista.findByNombre", query = "SELECT p FROM Personausuariovista p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Personausuariovista.findByNif", query = "SELECT p FROM Personausuariovista p WHERE p.nif = :nif")})
public class Personausuariovista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "nif")
    @Id
    private String nif;
    @JoinColumn(name = "nif", referencedColumnName = "nif", updatable = false, insertable = false, nullable = false)
    @OneToOne
    private Persona persona;

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Personausuariovista() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}
