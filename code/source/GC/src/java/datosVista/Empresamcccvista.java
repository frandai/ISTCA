/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datosVista;

import datos.EmpresaMatriculaCcc;
import datos.Grupo;
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
@Table(name = "empresamcccvista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresamcccvista.findAll", query = "SELECT e FROM Empresamcccvista e"),
    @NamedQuery(name = "Empresamcccvista.findByNif", query = "SELECT e FROM Empresamcccvista e WHERE e.nif = :nif"),
    @NamedQuery(name = "Empresamcccvista.findByRazonSocial", query = "SELECT e FROM Empresamcccvista e WHERE e.razonSocial = :razonSocial"),
    @NamedQuery(name = "Empresamcccvista.findByCcc", query = "SELECT e FROM Empresamcccvista e WHERE e.ccc = :ccc")})
public class Empresamcccvista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "nif")
    @Id
    private String nif;
    @Size(max = 2147483647)
    @Column(name = "razon_social")
    private String razonSocial;
    @Size(max = 2147483647)
    @Column(name = "ccc")
    @Id
    private String ccc;
    @JoinColumns({
        @JoinColumn(name = "nif", referencedColumnName = "nif", updatable = false, insertable = false, nullable = false),
        @JoinColumn(name = "ccc", referencedColumnName = "ccc", updatable = false, insertable = false, nullable = false)
    })
    @OneToOne
    private EmpresaMatriculaCcc empresamatriculaccc;

    public EmpresaMatriculaCcc getEmpresamatriculaccc() {
        return empresamatriculaccc;
    }

    public void setEmpresamatriculaccc(EmpresaMatriculaCcc empresamatriculaccc) {
        this.empresamatriculaccc = empresamatriculaccc;
    }

    public Empresamcccvista() {
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc;
    }
}