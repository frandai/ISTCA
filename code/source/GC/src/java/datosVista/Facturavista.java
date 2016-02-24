/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datosVista;

import datos.Empresa;
import datos.Factura;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    @NamedQuery(name = "Facturavista.findAll", query = "SELECT f FROM Facturavista f"),
    @NamedQuery(name = "Facturavista.findByIdfactura", query = "SELECT f FROM Facturavista f WHERE f.idfactura = :idfactura"),
    @NamedQuery(name = "Facturavista.findByRazonSocial", query = "SELECT f FROM Facturavista f WHERE f.razonSocial = :razonSocial"),
    @NamedQuery(name = "Facturavista.findByAlumno", query = "SELECT f FROM Facturavista f WHERE f.alumno = :alumno"),
    @NamedQuery(name = "Facturavista.findByImporte", query = "SELECT f FROM Facturavista f WHERE f.importe = :importe"),
    @NamedQuery(name = "Facturavista.findByGrupo", query = "SELECT f FROM Facturavista f WHERE f.grupo = :grupo"),
    @NamedQuery(name = "Facturavista.findByFechaMatricula", query = "SELECT f FROM Facturavista f WHERE f.fechaMatricula = :fechaMatricula"),
    @NamedQuery(name = "Facturavista.findByAsbaja", query = "SELECT f FROM Facturavista f WHERE f.asbaja = :asbaja"),
    @NamedQuery(name = "Facturavista.findByPagoPosterior", query = "SELECT f FROM Facturavista f WHERE f.pagoPosterior = :pagoPosterior"),
    @NamedQuery(name = "Facturavista.findByCcc", query = "SELECT f FROM Facturavista f WHERE f.ccc = :ccc"),
    @NamedQuery(name = "Facturavista.findByFechaEstado", query = "SELECT f FROM Facturavista f WHERE f.fechaEstado = :fechaEstado"),
    @NamedQuery(name = "Facturavista.findByFechaCreacionFac", query = "SELECT f FROM Facturavista f WHERE f.fechaCreacionFac = :fechaCreacionFac"),
    @NamedQuery(name = "Facturavista.findByIdmatricula", query = "SELECT f FROM Facturavista f WHERE f.idmatricula = :idmatricula"),
    @NamedQuery(name = "Facturavista.findByPagoAplazado", query = "SELECT f FROM Facturavista f WHERE f.pagoAplazado = :pagoAplazado"),
    @NamedQuery(name = "Facturavista.findByFechasPagoAplazado", query = "SELECT f FROM Facturavista f WHERE f.fechasPagoAplazado = :fechasPagoAplazado"),
    @NamedQuery(name = "Facturavista.findByFechasPagoPosterior", query = "SELECT f FROM Facturavista f WHERE f.fechasPagoPosterior = :fechasPagoPosterior"),
    @NamedQuery(name = "Facturavista.findByEstadoFactura", query = "SELECT f FROM Facturavista f WHERE f.estadoFactura = :estadoFactura")})
public class Facturavista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Id
    @Column(name = "idfactura")
    private String idfactura;
    @Size(max = 2147483647)
    @Column(name = "razon_social")
    private String razonSocial;
    @Size(max = 2147483647)
    @Column(name = "alumno")
    private String alumno;
    @Column(name = "importe")
    private Double importe;
    @Size(max = 2147483647)
    @Column(name = "grupo")
    private String grupo;
    @Size(max = 2147483647)
    @Column(name = "fecha_matricula")
    private String fechaMatricula;
    @Size(max = 2147483647)
    @Column(name = "asbaja")
    private String asbaja;
    @Size(max = 2147483647)
    @Column(name = "pago_posterior")
    private String pagoPosterior;
    @Size(max = 2147483647)
    @Column(name = "ccc")
    private String ccc;
    @Size(max = 2147483647)
    @Column(name = "fecha_estado")
    private String fechaEstado;
    @Size(max = 2147483647)
    @Column(name = "fecha_creacion_fac")
    private String fechaCreacionFac;
    @Column(name = "idmatricula")
    private Integer idmatricula;
    @Size(max = 2147483647)
    @Column(name = "pago_aplazado")
    private String pagoAplazado;
    @Size(max = 2147483647)
    @Column(name = "fechas_pago_aplazado")
    private String fechasPagoAplazado;
    @Size(max = 2147483647)
    @Column(name = "fechas_pago_posterior")
    private String fechasPagoPosterior;
    @Size(max = 2147483647)
    @Column(name = "estado_factura")
    private String estadoFactura;
    @Size(max = 2147483647)
    @Column(name = "puede_generar_remesa")
    private String puedeGenerarRemesa;
    @Size(max = 2147483647)
    @Column(name = "razon_social_empresa")
    private String razonSocialEmpresa;
    @Size(max = 2147483647)
    @Column(name = "nif_empresa")
    private String nifEmpresa;
    @Size(max = 2147483647)
    @Column(name = "nombre_alumno")
    private String nombreAlumno;
    @Size(max = 2147483647)
    @Column(name = "nif_alumno")
    private String nifAlumno;
    @Size(max = 2147483647)
    @Column(name = "fecha_inicio")
    private String fechaInicio;
    @Size(max = 2147483647)
    @Column(name = "fecha_fin")
    private String fechaFin;
    @Size(max = 2147483647)
    @Column(name = "remesas")
    private String remesas;
    @Column(name = "sum_importes_facturas")
    private Double sumImportesFacturas;
    @Size(max = 2147483647)
    @Column(name = "razon_social_proveedor")
    private String razon_social_proveedor;
    @Size(max = 2147483647)
    @Column(name = "nif_proveedor")
    private String nifProveedor;
    @Size(max = 2147483647)
    @Column(name = "proveedor")
    private String proveedor;
    @Column(name = "f_creacion_fac")
    @Temporal(TemporalType.DATE)
    private Date fCreacionFac;

    public Date getfCreacionFac() {
        return fCreacionFac;
    }

    public void setfCreacionFac(Date fCreacionFac) {
        this.fCreacionFac = fCreacionFac;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
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

    public String getRemesas() {
        return remesas;
    }

    public void setRemesas(String remesas) {
        this.remesas = remesas;
    }

    public Double getSumImportesFacturas() {
        return sumImportesFacturas;
    }

    public void setSumImportesFacturas(Double sumImportesFacturas) {
        this.sumImportesFacturas = sumImportesFacturas;
    }

    public String getRazon_social_proveedor() {
        return razon_social_proveedor;
    }

    public void setRazon_social_proveedor(String razon_social_proveedor) {
        this.razon_social_proveedor = razon_social_proveedor;
    }

    public String getNifProveedor() {
        return nifProveedor;
    }

    public void setNifProveedor(String nifProveedor) {
        this.nifProveedor = nifProveedor;
    }

    public String getRazonSocialEmpresa() {
        return razonSocialEmpresa;
    }

    public void setRazonSocialEmpresa(String razonSocialEmpresa) {
        this.razonSocialEmpresa = razonSocialEmpresa;
    }

    public String getNifEmpresa() {
        return nifEmpresa;
    }

    public void setNifEmpresa(String nifEmpresa) {
        this.nifEmpresa = nifEmpresa;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getNifAlumno() {
        return nifAlumno;
    }

    public void setNifAlumno(String nifAlumno) {
        this.nifAlumno = nifAlumno;
    }

    public String getPuedeGenerarRemesa() {
        return puedeGenerarRemesa;
    }

    public void setPuedeGenerarRemesa(String puedeGenerarRemesa) {
        this.puedeGenerarRemesa = puedeGenerarRemesa;
    }

    public Facturavista() {
    }

    public String getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(String idfactura) {
        this.idfactura = idfactura;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(String fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getAsbaja() {
        return asbaja;
    }

    public void setAsbaja(String asbaja) {
        this.asbaja = asbaja;
    }

    public String getPagoPosterior() {
        return pagoPosterior;
    }

    public void setPagoPosterior(String pagoPosterior) {
        this.pagoPosterior = pagoPosterior;
    }

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc;
    }

    public String getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(String fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    public String getFechaCreacionFac() {
        return fechaCreacionFac;
    }

    public void setFechaCreacionFac(String fechaCreacionFac) {
        this.fechaCreacionFac = fechaCreacionFac;
    }

    public Integer getIdmatricula() {
        return idmatricula;
    }

    public void setIdmatricula(Integer idmatricula) {
        this.idmatricula = idmatricula;
    }

    public String getPagoAplazado() {
        return pagoAplazado;
    }

    public void setPagoAplazado(String pagoAplazado) {
        this.pagoAplazado = pagoAplazado;
    }

    public String getFechasPagoAplazado() {
        return fechasPagoAplazado;
    }

    public void setFechasPagoAplazado(String fechasPagoAplazado) {
        this.fechasPagoAplazado = fechasPagoAplazado;
    }

    public String getFechasPagoPosterior() {
        return fechasPagoPosterior;
    }

    public void setFechasPagoPosterior(String fechasPagoPosterior) {
        this.fechasPagoPosterior = fechasPagoPosterior;
    }

    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }
}
