/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datosVista;

import datos.AccionFormativa;
import datos.Empresa;
import java.io.Serializable;
import java.math.BigInteger;
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
@Table(schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accionformativavista.findAll", query = "SELECT a FROM Accionformativavista a"),
    @NamedQuery(name = "Accionformativavista.findById", query = "SELECT a FROM Accionformativavista a WHERE a.id = :id"),
    @NamedQuery(name = "Accionformativavista.findByNombre", query = "SELECT a FROM Accionformativavista a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Accionformativavista.findByModalidad", query = "SELECT a FROM Accionformativavista a WHERE a.modalidad = :modalidad"),
    @NamedQuery(name = "Accionformativavista.findByPrecio", query = "SELECT a FROM Accionformativavista a WHERE a.precio = :precio"),
    @NamedQuery(name = "Accionformativavista.findByHoras", query = "SELECT a FROM Accionformativavista a WHERE a.horas = :horas"),
    @NamedQuery(name = "Accionformativavista.findByProveedor", query = "SELECT a FROM Accionformativavista a WHERE a.proveedor = :proveedor"),
    @NamedQuery(name = "Accionformativavista.findByFamilia", query = "SELECT a FROM Accionformativavista a WHERE a.familia = :familia")})
public class Accionformativavista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    @Size(max = 2147483647)
    private String nombre;
    @Size(max = 2147483647)
    private String modalidad;
    private Double precio;
    private Short horas;
    @Size(max = 2147483647)
    private String proveedor;
    @Size(max = 2147483647)
    private String familia;
    @JoinColumn(name = "id", referencedColumnName = "id", updatable = false, insertable = false, nullable = false)
    @OneToOne
    private AccionFormativa accionFormativa;

    public AccionFormativa getAccionFormativa() {
        return accionFormativa;
    }

    public void setAccionFormativa(AccionFormativa accionFormativa) {
        this.accionFormativa = accionFormativa;
    }

    public Accionformativavista() {
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

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Short getHoras() {
        return horas;
    }

    public void setHoras(Short horas) {
        this.horas = horas;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }
}
