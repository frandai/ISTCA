/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datosVista;

import datos.CpLocalidad;
import datos.Empresa;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "cp_localidad_provincia", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CpLocalidadProvincia.findAll", query = "SELECT c FROM CpLocalidadProvincia c"),
    @NamedQuery(name = "CpLocalidadProvincia.findByCp", query = "SELECT c FROM CpLocalidadProvincia c WHERE c.cp = :cp"),
    @NamedQuery(name = "CpLocalidadProvincia.findByLocalidadNombre", query = "SELECT c FROM CpLocalidadProvincia c WHERE c.localidadNombre = :localidadNombre"),
    @NamedQuery(name = "CpLocalidadProvincia.findByProvinciaNombre", query = "SELECT c FROM CpLocalidadProvincia c WHERE c.provinciaNombre = :provinciaNombre"),
    @NamedQuery(name = "CpLocalidadProvincia.findByLocalidadId", query = "SELECT c FROM CpLocalidadProvincia c WHERE c.localidadId = :localidadId"),
    @NamedQuery(name = "CpLocalidadProvincia.findByProvinciaId", query = "SELECT c FROM CpLocalidadProvincia c WHERE c.provinciaId = :provinciaId")})
public class CpLocalidadProvincia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Integer cp;
    @Size(max = 2147483647)
    @Id
    @Column(name = "localidad_nombre")
    private String localidadNombre;
    @Size(max = 2147483647)
    @Column(name = "provincia_nombre")
    private String provinciaNombre;
    @Column(name = "localidad_id")
    private Integer localidadId;
    @Column(name = "provincia_id")
    private Integer provinciaId;
    @JoinColumns({
        @JoinColumn(name = "cp", referencedColumnName = "cp", updatable = false, insertable = false, nullable = false),
        @JoinColumn(name = "localidad_id", referencedColumnName = "localidad", updatable = false, insertable = false, nullable = false)
    })
    @OneToOne
    private CpLocalidad cpLoc;

    public CpLocalidad getCpLoc() {
        return cpLoc;
    }

    public void setCpLoc(CpLocalidad cpLoc) {
        this.cpLoc = cpLoc;
    }

    public CpLocalidadProvincia() {
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getLocalidadNombre() {
        return localidadNombre;
    }

    public void setLocalidadNombre(String localidadNombre) {
        this.localidadNombre = localidadNombre;
    }

    public String getProvinciaNombre() {
        return provinciaNombre;
    }

    public void setProvinciaNombre(String provinciaNombre) {
        this.provinciaNombre = provinciaNombre;
    }

    public Integer getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(Integer localidadId) {
        this.localidadId = localidadId;
    }

    public Integer getProvinciaId() {
        return provinciaId;
    }

    public void setProvinciaId(Integer provinciaId) {
        this.provinciaId = provinciaId;
    }
}
