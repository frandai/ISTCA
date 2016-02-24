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
@Table(schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personavista.findAll", query = "SELECT p FROM Personavista p"),
    @NamedQuery(name = "Personavista.findByNif", query = "SELECT p FROM Personavista p WHERE p.nif = :nif"),
    @NamedQuery(name = "Personavista.findByNombre", query = "SELECT p FROM Personavista p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Personavista.findByEmpresas", query = "SELECT p FROM Personavista p WHERE p.empresas = :empresas"),
    @NamedQuery(name = "Personavista.findByTelefono", query = "SELECT p FROM Personavista p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Personavista.findByProvinciaNombre", query = "SELECT p FROM Personavista p WHERE p.provinciaNombre = :provinciaNombre"),
    @NamedQuery(name = "Personavista.findByLocalidadNombre", query = "SELECT p FROM Personavista p WHERE p.localidadNombre = :localidadNombre"),
    @NamedQuery(name = "Personavista.findByTipo", query = "SELECT p FROM Personavista p WHERE p.tipo = :tipo"),
    @NamedQuery(name = "Personavista.findByNivelEstudio", query = "SELECT p FROM Personavista p WHERE p.nivelEstudio = :nivelEstudio"),
    @NamedQuery(name = "Personavista.findByGrupoCotizacion", query = "SELECT p FROM Personavista p WHERE p.grupoCotizacion = :grupoCotizacion"),
    @NamedQuery(name = "Personavista.findByAreaFuncional", query = "SELECT p FROM Personavista p WHERE p.areaFuncional = :areaFuncional"),
    @NamedQuery(name = "Personavista.findByCategoriaProfesional", query = "SELECT p FROM Personavista p WHERE p.categoriaProfesional = :categoriaProfesional"),
    @NamedQuery(name = "Personavista.findByEcSuperior", query = "SELECT p FROM Personavista p WHERE p.ecSuperior = :ecSuperior"),
    @NamedQuery(name = "Personavista.findByNivelEc", query = "SELECT p FROM Personavista p WHERE p.nivelEc = :nivelEc")})
public class Personavista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Id
    private String nif;
    @Size(max = 2147483647)
    private String nombre;
    @Size(max = 2147483647)
    private String empresas;
    @Size(max = 2147483647)
    private String telefono;
    @Size(max = 2147483647)
    @Column(name = "provincia_nombre")
    private String provinciaNombre;
    @Size(max = 2147483647)
    @Column(name = "localidad_nombre")
    private String localidadNombre;
    @Size(max = 2147483647)
    private String tipo;
    @Size(max = 2147483647)
    @Column(name = "nivel_estudio")
    private String nivelEstudio;
    @Size(max = 2147483647)
    @Column(name = "grupo_cotizacion")
    private String grupoCotizacion;
    @Size(max = 2147483647)
    @Column(name = "area_funcional")
    private String areaFuncional;
    @Size(max = 2147483647)
    @Column(name = "categoria_profesional")
    private String categoriaProfesional;
    @Size(max = 2147483647)
    @Column(name = "ec_superior")
    private String ecSuperior;
    @Size(max = 2147483647)
    @Column(name = "nivel_ec")
    private String nivelEc;
    @Size(max = 2147483647)
    @Column(name = "nss")
    private String nss;
    @JoinColumn(name = "nif", referencedColumnName = "nif", updatable = false, insertable = false, nullable = false)
    @OneToOne
    private Persona persona;

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Personavista() {
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

    public String getEmpresas() {
        return empresas;
    }

    public void setEmpresas(String empresas) {
        this.empresas = empresas;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getProvinciaNombre() {
        return provinciaNombre;
    }

    public void setProvinciaNombre(String provinciaNombre) {
        this.provinciaNombre = provinciaNombre;
    }

    public String getLocalidadNombre() {
        return localidadNombre;
    }

    public void setLocalidadNombre(String localidadNombre) {
        this.localidadNombre = localidadNombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNivelEstudio() {
        return nivelEstudio;
    }

    public void setNivelEstudio(String nivelEstudio) {
        this.nivelEstudio = nivelEstudio;
    }

    public String getGrupoCotizacion() {
        return grupoCotizacion;
    }

    public void setGrupoCotizacion(String grupoCotizacion) {
        this.grupoCotizacion = grupoCotizacion;
    }

    public String getAreaFuncional() {
        return areaFuncional;
    }

    public void setAreaFuncional(String areaFuncional) {
        this.areaFuncional = areaFuncional;
    }

    public String getCategoriaProfesional() {
        return categoriaProfesional;
    }

    public void setCategoriaProfesional(String categoriaProfesional) {
        this.categoriaProfesional = categoriaProfesional;
    }

    public String getEcSuperior() {
        return ecSuperior;
    }

    public void setEcSuperior(String ecSuperior) {
        this.ecSuperior = ecSuperior;
    }

    public String getNivelEc() {
        return nivelEc;
    }

    public void setNivelEc(String nivelEc) {
        this.nivelEc = nivelEc;
    }
}
