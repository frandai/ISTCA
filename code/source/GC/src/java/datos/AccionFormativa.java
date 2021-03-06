/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 *
 */
@Entity
@Table(name = "ACCION_FORMATIVA", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccionFormativa.findAll", query = "SELECT a FROM AccionFormativa a"),
    @NamedQuery(name = "AccionFormativa.findById", query = "SELECT a FROM AccionFormativa a WHERE a.id = :id"),
    @NamedQuery(name = "AccionFormativa.findByNombre", query = "SELECT a FROM AccionFormativa a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AccionFormativa.findByHoras", query = "SELECT a FROM AccionFormativa a WHERE a.horas = :horas"),
    @NamedQuery(name = "AccionFormativa.findByTipoAccionEspecifica", query = "SELECT a FROM AccionFormativa a WHERE a.tipoAccionEspecifica = :tipoAccionEspecifica"),
    @NamedQuery(name = "AccionFormativa.findByNivelFormacionSuperior", query = "SELECT a FROM AccionFormativa a WHERE a.nivelFormacionSuperior = :nivelFormacionSuperior"),
    @NamedQuery(name = "AccionFormativa.findByObjetivos", query = "SELECT a FROM AccionFormativa a WHERE a.objetivos = :objetivos"),
    @NamedQuery(name = "AccionFormativa.findByContenidos", query = "SELECT a FROM AccionFormativa a WHERE a.contenidos = :contenidos"),
    @NamedQuery(name = "AccionFormativa.findByObservaciones", query = "SELECT a FROM AccionFormativa a WHERE a.observaciones = :observaciones"),
    @NamedQuery(name = "AccionFormativa.findByPrecio", query = "SELECT a FROM AccionFormativa a WHERE a.precio = :precio")})
public class AccionFormativa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short horas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_ACCION_ESPECIFICA", nullable = false)
    private boolean tipoAccionEspecifica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NIVEL_FORMACION_SUPERIOR", nullable = false)
    private boolean nivelFormacionSuperior;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String objetivos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String contenidos;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String observaciones;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Double precio;
    @JoinTable(name = "proveedor_accion_formativa", joinColumns = {
        @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "nif", referencedColumnName = "nif", nullable = false)})
    @ManyToMany
    private List<Proveedor> proveedorList;
    @JoinColumn(name = "MODALIDAD", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private AccionFormativaModalidad modalidad;
    @JoinColumn(name = "ACCION_FORMATIVA_GRUPO", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private AccionFormativaGrupo accionFormativaGrupo;
    @JoinColumn(name = "ACCION_FORMATIVA_GRUPO_FUNDACION", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private AccionFormativaGrupoFundacion accionFormativaGrupoFundacion;
    @JoinColumn(name = "CNCP", referencedColumnName = "ID")
    @ManyToOne
    private AccionFormativaCncp cncp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accionFormativa1")
    private List<Grupo> grupoList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "accionFormativa")
    private AccionFormativaExtra accionFormativaExtra;
    @Transient //Parámetro que muestra los tutores disponibles para la AF
    //(tutores del grupo Pendiente de Gestionar de la Acción Formativa)
    private List<Tutor> tutorGestionList;
    @Transient //Parámetro booleano para identifivar si se ha seleccionado ésta AF
    //Para gestionarla en una función de impresión
    private boolean seleccionarPDF;

    public boolean isSeleccionarPDF() {
        return seleccionarPDF;
    }

    public void setSeleccionarPDF(boolean seleccionarPDF) {
        this.seleccionarPDF = seleccionarPDF;
    }

    public AccionFormativaGrupoFundacion getAccionFormativaGrupoFundacion() {
        return accionFormativaGrupoFundacion;
    }

    public void setAccionFormativaGrupoFundacion(AccionFormativaGrupoFundacion accionFormativaGrupoFundacion) {
        this.accionFormativaGrupoFundacion = accionFormativaGrupoFundacion;
    }

    public List<Tutor> getTutorGestionList() {
        if (tutorGestionList == null) {
            for (Grupo g : grupoList) {
                if (g.getPendienteDeGestionar() == true) {
                    tutorGestionList = g.getTutorList();
                    break;
                }
            }
        }
        return tutorGestionList;
    }

    public AccionFormativa() {
    }

    public AccionFormativa(Integer id) {
        this.id = id;
    }

    public AccionFormativa(Integer id, String nombre, short horas, boolean tipoAccionEspecifica, boolean nivelFormacionSuperior, String objetivos, String contenidos, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.horas = horas;
        this.tipoAccionEspecifica = tipoAccionEspecifica;
        this.nivelFormacionSuperior = nivelFormacionSuperior;
        this.objetivos = objetivos;
        this.contenidos = contenidos;
        this.precio = precio;
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

    public short getHoras() {
        return horas;
    }

    public void setHoras(short horas) {
        this.horas = horas;
    }

    public boolean getTipoAccionEspecifica() {
        return tipoAccionEspecifica;
    }

    public void setTipoAccionEspecifica(boolean tipoAccionEspecifica) {
        this.tipoAccionEspecifica = tipoAccionEspecifica;
    }

    public boolean getNivelFormacionSuperior() {
        return nivelFormacionSuperior;
    }

    public void setNivelFormacionSuperior(boolean nivelFormacionSuperior) {
        this.nivelFormacionSuperior = nivelFormacionSuperior;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getContenidos() {
        return contenidos;
    }

    public void setContenidos(String contenidos) {
        this.contenidos = contenidos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public List<Proveedor> getProveedor() {
        return proveedorList;
    }

    public void setProveedor(List<Proveedor> proveedor) {
        this.proveedorList = proveedor;
    }

    public AccionFormativaModalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(AccionFormativaModalidad modalidad) {
        this.modalidad = modalidad;
    }

    public AccionFormativaGrupo getAccionFormativaGrupo() {
        return accionFormativaGrupo;
    }

    public void setAccionFormativaGrupo(AccionFormativaGrupo accionFormativaGrupo) {
        this.accionFormativaGrupo = accionFormativaGrupo;
    }

    public AccionFormativaCncp getCncp() {
        return cncp;
    }

    public void setCncp(AccionFormativaCncp cncp) {
        this.cncp = cncp;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    public List<Proveedor> getProveedorList() {
        return proveedorList;
    }

    public void setProveedorList(List<Proveedor> proveedorList) {
        this.proveedorList = proveedorList;
    }

    public AccionFormativaExtra getAccionFormativaExtra() {
        return accionFormativaExtra;
    }

    public void setAccionFormativaExtra(AccionFormativaExtra accionFormativaExtra) {
        this.accionFormativaExtra = accionFormativaExtra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccionFormativa)) {
            return false;
        }
        AccionFormativa other = (AccionFormativa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.AccionFormativa[ id=" + id + " ]";
    }

    public String getProveedores() {
        String proveedores = "";
        for (Proveedor p : proveedorList) {
            proveedores += p.getEmpresa().getRazonSocial() + "( " + p.getEmpresa().getNif() + " )" + " ";
        }
        return proveedores;
    }
}
