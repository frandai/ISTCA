/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datosVista;

import datos.Alumno;
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
@Table(name = "alumnovista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumnovista.findAll", query = "SELECT a FROM Alumnovista a"),
    @NamedQuery(name = "Alumnovista.findByNif", query = "SELECT a FROM Alumnovista a WHERE a.nif = :nif"),
    @NamedQuery(name = "Alumnovista.findByNombre", query = "SELECT a FROM Alumnovista a WHERE a.nombre = :nombre")})
public class Alumnovista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "nif")
    @Id
    private String nif;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "nif", referencedColumnName = "nif", updatable = false, insertable = false, nullable = false)
    @OneToOne
    private Alumno alumno;

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Alumnovista() {
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
