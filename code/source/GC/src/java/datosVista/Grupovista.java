/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datosVista;

import datos.Grupo;
import datos.Matricula;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Grupovista.findAll", query = "SELECT g FROM Grupovista g"),
    @NamedQuery(name = "Grupovista.findByNombreAf", query = "SELECT g FROM Grupovista g WHERE g.nombreAf = :nombreAf"),
    @NamedQuery(name = "Grupovista.findByNombreGrupo", query = "SELECT g FROM Grupovista g WHERE g.nombreGrupo = :nombreGrupo"),
    @NamedQuery(name = "Grupovista.findByHorario", query = "SELECT g FROM Grupovista g WHERE g.horario = :horario"),
    @NamedQuery(name = "Grupovista.findByArrayToString", query = "SELECT g FROM Grupovista g WHERE g.arrayToString = :arrayToString"),
    @NamedQuery(name = "Grupovista.findByFechaInicio", query = "SELECT g FROM Grupovista g WHERE g.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Grupovista.findByFechaFin", query = "SELECT g FROM Grupovista g WHERE g.fechaFin = :fechaFin"),
    @NamedQuery(name = "Grupovista.findByModalidad", query = "SELECT g FROM Grupovista g WHERE g.modalidad = :modalidad"),
    @NamedQuery(name = "Grupovista.findByFInicio", query = "SELECT g FROM Grupovista g WHERE g.f_inicio = :f_inicio"),
    @NamedQuery(name = "Grupovista.findByFFin", query = "SELECT g FROM Grupovista g WHERE g.f_fin = :f_fin")})
public class Grupovista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "nombre_af")
    @Id
    private String nombreAf;
    @Size(max = 2147483647)
    @Column(name = "nombre_grupo")
    @Id
    private String nombreGrupo;
    @Size(max = 2147483647)
    private String horario;
    @Size(max = 2147483647)
    @Column(name = "array_to_string")
    private String arrayToString;
    @Size(max = 2147483647)
    @Column(name = "fecha_inicio")
    private String fechaInicio;
    @Size(max = 2147483647)
    @Column(name = "fecha_fin")
    private String fechaFin;
    @Size(max = 2147483647)
    private String modalidad;
    @Column(name = "f_inicio")
    private String f_inicio;
    @Column(name = "f_fin")
    private String f_fin;
    @Column(name = "id_accion_formativa")
    private Integer id_accion_formativa;
    @Column(name = "id_grupo")
    private Integer id_grupo;
    @Size(max = 2147483647)
    @Column(name = "proveedor")
    private String proveedor;
    @JoinColumns({
        @JoinColumn(name = "id_grupo", referencedColumnName = "id", updatable = false, insertable = false, nullable = false),
        @JoinColumn(name = "id_accion_formativa", referencedColumnName = "accion_formativa", updatable = false, insertable = false, nullable = false)
    })
    @OneToOne
    private Grupo grupo;

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String getF_inicio() {
        return f_inicio;
    }

    public void setF_inicio(String f_inicio) {
        this.f_inicio = f_inicio;
    }

    public String getF_fin() {
        return f_fin;
    }

    public void setF_fin(String f_fin) {
        this.f_fin = f_fin;
    }

    public Integer getId_accion_formativa() {
        return id_accion_formativa;
    }

    public void setId_accion_formativa(Integer id_accion_formativa) {
        this.id_accion_formativa = id_accion_formativa;
    }

    public Integer getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(Integer id_grupo) {
        this.id_grupo = id_grupo;
    }

    public Grupovista() {
    }

    public String getNombreAf() {
        return nombreAf;
    }

    public void setNombreAf(String nombreAf) {
        this.nombreAf = nombreAf;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getArrayToString() {
        return arrayToString;
    }

    public void setArrayToString(String arrayToString) {
        this.arrayToString = arrayToString;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
}
