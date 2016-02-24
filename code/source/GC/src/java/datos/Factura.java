/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import util.Fecha;
import vista.UtilidadesVista;

/**
 *
 *
 */
@Entity
@Table(schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findById", query = "SELECT f FROM Factura f WHERE f.id = :id"),
    @NamedQuery(name = "Factura.findByFecha", query = "SELECT f FROM Factura f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "Factura.findByObservaciones", query = "SELECT f FROM Factura f WHERE f.observaciones = :observaciones")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String observaciones;
    @JoinTable(name = "FACTURA_REMESA", joinColumns = {
        @JoinColumn(name = "FACTURA", referencedColumnName = "ID", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "REMESA", referencedColumnName = "ID", nullable = false)})
    @ManyToMany
    private List<Remesa> remesaList;
    @JoinColumn(name = "PROVEEDOR", referencedColumnName = "NIF", nullable = false)
    @ManyToOne(optional = false)
    private Proveedor proveedor;
    @JoinColumn(name = "FORMA_PAGO", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private FormaPago formaPago;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura1")
    private List<MatriculaFactura> matriculaFacturaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura1", orphanRemoval = true)
    private List<FacturaHistoricoEstado> facturaHistoricoEstadoList;
    @Transient //Variable que guarda el importe de la factura, sumando sus importes de la tabla MatriculaFactura
    private Double importe = null;
    @Transient //Variable que guarda el último estado de la factura, que es el estado actual de la factura
    private FacturaHistoricoEstado ultimoEstado = null;
    @Transient //Porcentaje de la factura en costes de ORGANIZACIÓN/IMPARTICIÓN
    private int porcentaje = -1;
    @Transient //Parámetro booleano para identifivar si se ha seleccionado ésta Factura
    //Para gestionarla en una función de impresión o gestión
    private boolean seleccionado = false;

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public Factura() {
    }

    public Factura(String id) {
        this.id = id;
    }

    public Factura(String id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }
    
    public String getIdR() {
        return id.replaceAll("###", "2013");
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechaString() {
        return Fecha.getFechaDiaMesAnio(fecha);
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @XmlTransient
    public List<Remesa> getRemesaList() {
        return remesaList;
    }

    public void setRemesaList(List<Remesa> remesaList) {
        this.remesaList = remesaList;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    @XmlTransient
    public List<MatriculaFactura> getMatriculaFacturaList() {
        return matriculaFacturaList;
    }

    public void setMatriculaFacturaList(List<MatriculaFactura> matriculaFacturaList) {
        this.matriculaFacturaList = matriculaFacturaList;
    }

    @XmlTransient
    public List<FacturaHistoricoEstado> getFacturaHistoricoEstadoList() {
        return facturaHistoricoEstadoList;
    }

    public void setFacturaHistoricoEstadoList(List<FacturaHistoricoEstado> facturaHistoricoEstadoList) {
        this.facturaHistoricoEstadoList = facturaHistoricoEstadoList;
    }

    public Double getImporte() {
        if (importe == null) {
            importe = 0.0;
            for (MatriculaFactura mf : matriculaFacturaList) {
                importe += mf.getPrecio();
            }
            importe = util.Validacion.Redondear(importe);
        }
        return importe;
    }

    public void setImporte(Double importe) {
        if (matriculaFacturaList.size() == 2) {
            for (MatriculaFactura mf : matriculaFacturaList) {
                if (mf.getObservaciones().contains("IMPARTICIÓN")) {
                    mf.setPrecio(util.Validacion.Redondear(importe * ((100 - getPorcentaje()) / 100.0)));
                } else if (mf.getObservaciones().contains("ORGANIZACIÓN")) {
                    mf.setPrecio(util.Validacion.Redondear(importe * (getPorcentaje() / 100.0)));
                }
            }
            this.porcentaje = -1;
            this.importe = null;
        }
    }

    public FacturaHistoricoEstado getUltimoEstado() {
        if (ultimoEstado == null) {
            for (FacturaHistoricoEstado estado : facturaHistoricoEstadoList) {
                if (ultimoEstado == null || estado.getFacturaHistoricoEstadoPK().getFecha().after(ultimoEstado.getFacturaHistoricoEstadoPK().getFecha())) {
                    ultimoEstado = estado;
                }
            }
        }
        return ultimoEstado;
    }

    public int getPorcentaje() {
        if (porcentaje == -1) {
            if (matriculaFacturaList.size() == 2) {
                for (MatriculaFactura mf : matriculaFacturaList) {
                    if (mf.getObservaciones().contains("ORGANIZACIÓN")) {
                        porcentaje = (int) (util.Validacion.Redondear(mf.getPrecio() / this.getImporte()) * 100.0);
                    }
                }
            }
        }
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        if (porcentaje > 100) {
            porcentaje = 100;
        }
        if (porcentaje < 0) {
            porcentaje = 0;
        }
        this.porcentaje = porcentaje;
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
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.Factura[ id=" + id + " ]";
    }
}
