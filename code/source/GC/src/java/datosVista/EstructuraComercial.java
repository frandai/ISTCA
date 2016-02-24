/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datosVista;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 *
 */
@Entity
@Table(name = "estructura_comercial", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstructuraComercial.findAll", query = "SELECT e FROM EstructuraComercial e"),
    @NamedQuery(name = "EstructuraComercial.findById", query = "SELECT e FROM EstructuraComercial e WHERE e.id = :id"),
    @NamedQuery(name = "EstructuraComercial.findByNombre", query = "SELECT e FROM EstructuraComercial e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "EstructuraComercial.findBySuperior", query = "SELECT e FROM EstructuraComercial e WHERE e.superior = :superior"),
    @NamedQuery(name = "EstructuraComercial.findByTipo", query = "SELECT e FROM EstructuraComercial e WHERE e.tipo = :tipo")})
public class EstructuraComercial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "id")
    @Id
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "superior")
    private String superior;
    @Size(max = 2147483647)
    @Column(name = "tipo")
    private String tipo;

    public EstructuraComercial() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(String superior) {
        this.superior = superior;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
