/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.Fecha;
import vista.UtilidadesVista;

/**
 *
 *
 */
@Embeddable
public class FacturaHistoricoEstadoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String factura;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public FacturaHistoricoEstadoPK() {
    }

    public FacturaHistoricoEstadoPK(String factura, Date fecha) {
        this.factura = factura;
        this.fecha = fecha;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public Date getFecha() {
        return fecha;
    }
    
    public String getFechaString() {
        return Fecha.getFechaDiaMesAnio(fecha);
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (factura != null ? factura.hashCode() : 0);
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaHistoricoEstadoPK)) {
            return false;
        }
        FacturaHistoricoEstadoPK other = (FacturaHistoricoEstadoPK) object;
        if ((this.factura == null && other.factura != null) || (this.factura != null && !this.factura.equals(other.factura))) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.FacturaHistoricoEstadoPK[ factura=" + factura + ", fecha=" + fecha + " ]";
    }
    
}
