/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumno.findAll", query = "SELECT a FROM Alumno a"),
    @NamedQuery(name = "Alumno.findByNif", query = "SELECT a FROM Alumno a WHERE a.nif = :nif"),
    @NamedQuery(name = "Alumno.findByAutonomo", query = "SELECT a FROM Alumno a WHERE a.autonomo = :autonomo"),
    @NamedQuery(name = "Alumno.findByDiscapacitado", query = "SELECT a FROM Alumno a WHERE a.discapacitado = :discapacitado"),
    @NamedQuery(name = "Alumno.findByObservaciones", query = "SELECT a FROM Alumno a WHERE a.observaciones = :observaciones"),
    @NamedQuery(name = "Alumno.findByOtrasTitulaciones", query = "SELECT a FROM Alumno a WHERE a.otrasTitulaciones = :otrasTitulaciones"),
    @NamedQuery(name = "Alumno.findByVictimaTerrorismo", query = "SELECT a FROM Alumno a WHERE a.victimaTerrorismo = :victimaTerrorismo"),
    @NamedQuery(name = "Alumno.findByVictimaVG", query = "SELECT a FROM Alumno a WHERE a.victimaVG = :victimaVG")})
public class Alumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String nif;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean autonomo;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean discapacitado;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String observaciones;
    @Size(max = 2147483647)
    @Column(name = "OTRAS_TITULACIONES", length = 2147483647)
    private String otrasTitulaciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VICTIMA_TERRORISMO", nullable = false)
    private boolean victimaTerrorismo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VICTIMA_V_G", nullable = false)
    private boolean victimaVG;
    @JoinTable(name = "ALUMNO_C_ERR", joinColumns = {
        @JoinColumn(name = "ALUMNO", referencedColumnName = "NIF", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "COMPROBACION", referencedColumnName = "ID", nullable = false)})
    @ManyToMany
    private List<AlumnoComprobacion> alumnoComprobacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alumno")
    private List<Matricula> matriculaList;
    @JoinColumn(name = "NIF", referencedColumnName = "NIF", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;
    @JoinColumn(name = "NIVEL_ESTUDIOS", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private AlumnoNivelEstudio nivelEstudios;
    @JoinColumn(name = "GRUPO_COTIZACION", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private AlumnoGrupoCotizacion grupoCotizacion;
    @JoinColumn(name = "CATEGORIA_PROFESIONAL", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private AlumnoCategoriaProfesional categoriaProfesional;
    @JoinColumn(name = "AREA_FUNCIONAL", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private AlumnoAreaFuncional areaFuncional;
    @Transient //Listado de CCC de Empresas a las cual el Alumno tiene asociada una Matrícula
    private List<EmpresaMatriculaCcc> emccc;

    public List<EmpresaMatriculaCcc> getEmccc() {
        if(emccc == null){
            emccc = new ArrayList<EmpresaMatriculaCcc>();
            for (Empresa e : getPersona().getEmpresaList()) {
                if(e.getEmpresaMatricula()!=null && e.getEmpresaMatricula().getEmpresaMatriculaCccList() != null){
                    for (EmpresaMatriculaCcc ex :  e.getEmpresaMatricula().getEmpresaMatriculaCccList()) {
                        emccc.add(ex);
                    }
                }
            }
        }
        return emccc;
    }

    public void setEmccc(List<EmpresaMatriculaCcc> emccc) {
        this.emccc = emccc;
    }

    public Alumno() {
    }

    public Alumno(String nif) {
        this.nif = nif;
    }

    public Alumno(String nif, boolean autonomo, boolean discapacitado, boolean victimaTerrorismo, boolean victimaVG) {
        this.nif = nif;
        this.autonomo = autonomo;
        this.discapacitado = discapacitado;
        this.victimaTerrorismo = victimaTerrorismo;
        this.victimaVG = victimaVG;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public boolean getAutonomo() {
        return autonomo;
    }

    public void setAutonomo(boolean autonomo) {
        this.autonomo = autonomo;
    }

    public boolean getDiscapacitado() {
        return discapacitado;
    }

    public void setDiscapacitado(boolean discapacitado) {
        this.discapacitado = discapacitado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getOtrasTitulaciones() {
        return otrasTitulaciones;
    }

    public void setOtrasTitulaciones(String otrasTitulaciones) {
        this.otrasTitulaciones = otrasTitulaciones;
    }

    public boolean getVictimaTerrorismo() {
        return victimaTerrorismo;
    }

    public void setVictimaTerrorismo(boolean victimaTerrorismo) {
        this.victimaTerrorismo = victimaTerrorismo;
    }

    public boolean getVictimaVG() {
        return victimaVG;
    }

    public void setVictimaVG(boolean victimaVG) {
        this.victimaVG = victimaVG;
    }

    @XmlTransient
    public List<AlumnoComprobacion> getAlumnoComprobacionList() {
        return alumnoComprobacionList;
    }

    public void setAlumnoComprobacionList(List<AlumnoComprobacion> alumnoComprobacionList) {
        this.alumnoComprobacionList = alumnoComprobacionList;
    }

    @XmlTransient
    public List<Matricula> getMatriculaList() {
        return matriculaList;
    }

    public void setMatriculaList(List<Matricula> matriculaList) {
        this.matriculaList = matriculaList;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public AlumnoNivelEstudio getNivelEstudios() {
        return nivelEstudios;
    }

    public void setNivelEstudios(AlumnoNivelEstudio nivelEstudios) {
        this.nivelEstudios = nivelEstudios;
    }

    public AlumnoGrupoCotizacion getGrupoCotizacion() {
        return grupoCotizacion;
    }

    public void setGrupoCotizacion(AlumnoGrupoCotizacion grupoCotizacion) {
        this.grupoCotizacion = grupoCotizacion;
    }

    public AlumnoCategoriaProfesional getCategoriaProfesional() {
        return categoriaProfesional;
    }

    public void setCategoriaProfesional(AlumnoCategoriaProfesional categoriaProfesional) {
        this.categoriaProfesional = categoriaProfesional;
    }

    public AlumnoAreaFuncional getAreaFuncional() {
        return areaFuncional;
    }

    public void setAreaFuncional(AlumnoAreaFuncional areaFuncional) {
        this.areaFuncional = areaFuncional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nif != null ? nif.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alumno)) {
            return false;
        }
        Alumno other = (Alumno) object;
        if ((this.nif == null && other.nif != null) || (this.nif != null && !this.nif.equals(other.nif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.Alumno[ nif=" + nif + " ]";
    }
    //Comprobaciones datos inventados
    //Como los datos inventados están controlados por una tabla libre,
    //en el siguiente código se convierten las coincidencias a valores booleanos,
    //más fáciles de gestionar.
    @Transient
    private Boolean fnac_inventada = null;
    @Transient
    private Boolean nivel_estidios_inventado = null;
    @Transient
    private Boolean grupo_cotizacion_inventado = null;
    @Transient
    private Boolean area_funcional_inventada = null;
    @Transient
    private Boolean categoria_profesional_inventada = null;

    public Boolean getFnac_inventada() {
        try {
            if (fnac_inventada == null) {
                fnac_inventada = false;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(7)) {
                        fnac_inventada = true;
                    }
                }
            }
        } catch (Exception e) {
            fnac_inventada = false;
        }
        return fnac_inventada;
    }

    public void setFnac_inventada(Boolean fnac_inventada) {
        try {

            if (fnac_inventada) {
                boolean crear = true;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(7)) {
                        crear = false;
                    }
                }
                if (crear) {
                    AlumnoComprobacion al = new AlumnoComprobacion(7);
                    alumnoComprobacionList.add(al);
                }
            } else {
                AlumnoComprobacion al = null;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(7)) {
                        al = alumnoComprobacion;
                    }
                }
                if (al != null) {
                    al.getAlumnoList().remove(this);
                    alumnoComprobacionList.remove(al);
                }
            }

        } catch (Exception e) {
        }
        this.fnac_inventada = fnac_inventada;
    }

    public Boolean getNivel_estidios_inventado() {
        try {
            if (nivel_estidios_inventado == null) {
                nivel_estidios_inventado = false;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(10)) {
                        nivel_estidios_inventado = true;
                    }
                }
            }
        } catch (Exception e) {
            nivel_estidios_inventado = false;
        }
        return nivel_estidios_inventado;
    }

    public void setNivel_estidios_inventado(Boolean nivel_estidios_inventado) {
        try {
            if (nivel_estidios_inventado) {
                boolean crear = true;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(10)) {
                        crear = false;
                    }
                }
                if (crear) {
                    AlumnoComprobacion al = new AlumnoComprobacion(10);
                    alumnoComprobacionList.add(al);
                }
            } else {
                AlumnoComprobacion al = null;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(10)) {
                        al = alumnoComprobacion;
                    }
                }
                if (al != null) {
                    al.getAlumnoList().remove(this);
                    alumnoComprobacionList.remove(al);
                }
            }
        } catch (Exception e) {
        }
        this.nivel_estidios_inventado = nivel_estidios_inventado;

    }

    public Boolean getGrupo_cotizacion_inventado() {
        try {
            if (grupo_cotizacion_inventado == null) {
                grupo_cotizacion_inventado = false;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(11)) {
                        grupo_cotizacion_inventado = true;
                    }
                }
            }
        } catch (Exception e) {
            grupo_cotizacion_inventado = false;
        }
        return grupo_cotizacion_inventado;
    }

    public void setGrupo_cotizacion_inventado(Boolean grupo_cotizacion_inventado) {
        try {
            if (grupo_cotizacion_inventado) {
                boolean crear = true;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(11)) {
                        crear = false;
                    }
                }
                if (crear) {
                    AlumnoComprobacion al = new AlumnoComprobacion(11);
                    alumnoComprobacionList.add(al);
                }
            } else {
                AlumnoComprobacion al = null;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(11)) {
                        al = alumnoComprobacion;
                    }
                }
                if (al != null) {
                    al.getAlumnoList().remove(this);
                    alumnoComprobacionList.remove(al);
                }
            }
        } catch (Exception e) {
        }
        this.grupo_cotizacion_inventado = grupo_cotizacion_inventado;
    }

    public Boolean getArea_funcional_inventada() {
        try {
            if (area_funcional_inventada == null) {
                area_funcional_inventada = false;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(12)) {
                        area_funcional_inventada = true;
                    }
                }
            }
        } catch (Exception e) {
            area_funcional_inventada = false;
        }
        return area_funcional_inventada;
    }

    public void setArea_funcional_inventada(Boolean area_funcional_inventada) {
        try {
            if (area_funcional_inventada) {
                boolean crear = true;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(12)) {
                        crear = false;
                    }
                }
                if (crear) {
                    AlumnoComprobacion al = new AlumnoComprobacion(12);
                    alumnoComprobacionList.add(al);
                }
            } else {
                AlumnoComprobacion al = null;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(12)) {
                        al = alumnoComprobacion;
                    }
                }
                if (al != null) {
                    al.getAlumnoList().remove(this);
                    alumnoComprobacionList.remove(al);
                }
            }
        } catch (Exception e) {
        }
        this.area_funcional_inventada = area_funcional_inventada;
    }

    public Boolean getCategoria_profesional_inventada() {
        try {
            if (categoria_profesional_inventada == null) {
                categoria_profesional_inventada = false;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(15)) {
                        categoria_profesional_inventada = true;
                    }
                }
            }
        } catch (Exception e) {
            categoria_profesional_inventada = false;
        }
        return categoria_profesional_inventada;
    }

    public void setCategoria_profesional_inventada(Boolean categoria_profesional_inventada) {
        try {
            if (categoria_profesional_inventada) {
                boolean crear = true;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(15)) {
                        crear = false;
                    }
                }
                if (crear) {
                    AlumnoComprobacion al = new AlumnoComprobacion(15);
                    alumnoComprobacionList.add(al);
                }
            } else {
                AlumnoComprobacion al = null;
                for (AlumnoComprobacion alumnoComprobacion : alumnoComprobacionList) {
                    if (alumnoComprobacion.getId().equals(15)) {
                        al = alumnoComprobacion;
                    }
                }
                if (al != null) {
                    al.getAlumnoList().remove(this);
                    alumnoComprobacionList.remove(al);
                }
            }
        } catch (Exception e) {
        }
        this.categoria_profesional_inventada = categoria_profesional_inventada;
    }
}
