/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datosVista;

import datos.Matricula;
import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import util.VariablesSistema;

/**
 *
 *
 */
@Entity
@Table(schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Matriculavista.findAll", query = "SELECT m FROM Matriculavista m"),
    @NamedQuery(name = "Matriculavista.findByRazonSocial", query = "SELECT m FROM Matriculavista m WHERE m.razonSocial = :razonSocial"),
    @NamedQuery(name = "Matriculavista.findByAlumno", query = "SELECT m FROM Matriculavista m WHERE m.alumno = :alumno"),
    @NamedQuery(name = "Matriculavista.findByGrupo", query = "SELECT m FROM Matriculavista m WHERE m.grupo = :grupo"),
    @NamedQuery(name = "Matriculavista.findByPrecio", query = "SELECT m FROM Matriculavista m WHERE m.precio = :precio"),
    @NamedQuery(name = "Matriculavista.findByFechaInicio", query = "SELECT m FROM Matriculavista m WHERE m.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Matriculavista.findByFInicio", query = "SELECT m FROM Matriculavista m WHERE m.fInicio = :fInicio"),
    @NamedQuery(name = "Matriculavista.findByFechaFin", query = "SELECT m FROM Matriculavista m WHERE m.fechaFin = :fechaFin"),
    @NamedQuery(name = "Matriculavista.findByFFin", query = "SELECT m FROM Matriculavista m WHERE m.fFin = :fFin"),
    @NamedQuery(name = "Matriculavista.findByCobroPendiente", query = "SELECT m FROM Matriculavista m WHERE m.cobroPendiente = :cobroPendiente"),
    @NamedQuery(name = "Matriculavista.findByConfirmada", query = "SELECT m FROM Matriculavista m WHERE m.confirmada = :confirmada"),
    @NamedQuery(name = "Matriculavista.findByAFinalizar", query = "SELECT m FROM Matriculavista m WHERE m.a_finalizar = :a_finalizar"),
    @NamedQuery(name = "Matriculavista.findByFinalizada", query = "SELECT m FROM Matriculavista m WHERE m.finalizada = :finalizada"),
    @NamedQuery(name = "Matriculavista.findByEvRecibida", query = "SELECT m FROM Matriculavista m WHERE m.evRecibida = :evRecibida"),
    @NamedQuery(name = "Matriculavista.findByIfcEnviada", query = "SELECT m FROM Matriculavista m WHERE m.ifcEnviada = :ifcEnviada"),
    @NamedQuery(name = "Matriculavista.findById", query = "SELECT m FROM Matriculavista m WHERE m.id = :id"),
    @NamedQuery(name = "Matriculavista.findByFormaPago", query = "SELECT m FROM Matriculavista m WHERE m.formaPago = :formaPago"),
    @NamedQuery(name = "Matriculavista.findByAsbaja", query = "SELECT m FROM Matriculavista m WHERE m.asbaja = :asbaja"),
    @NamedQuery(name = "Matriculavista.findByDiplomaEnviado", query = "SELECT m FROM Matriculavista m WHERE m.diplomaEnviado = :diplomaEnviado"),
    @NamedQuery(name = "Matriculavista.findByPagoPosterior", query = "SELECT m FROM Matriculavista m WHERE m.pagoPosterior = :pagoPosterior"),
    @NamedQuery(name = "Matriculavista.findByCcc", query = "SELECT m FROM Matriculavista m WHERE m.ccc = :ccc"),
    @NamedQuery(name = "Matriculavista.findByFactura", query = "SELECT m FROM Matriculavista m WHERE m.factura = :factura"),
    @NamedQuery(name = "Matriculavista.findByModalidad", query = "SELECT m FROM Matriculavista m WHERE m.modalidad = :modalidad"),
    @NamedQuery(name = "Matriculavista.findByComercial", query = "SELECT m FROM Matriculavista m WHERE m.comercial = :comercial")})
public class Matriculavista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "razon_social")
    private String razonSocial;
    @Size(max = 2147483647)
    @Column(name = "alumno")
    private String alumno;
    @Size(max = 2147483647)
    @Column(name = "grupo")
    private String grupo;
    @Column(name = "precio")
    private Double precio;
    @Size(max = 2147483647)
    @Column(name = "fecha_inicio")
    private String fechaInicio;
    @Column(name = "f_inicio")
    @Temporal(TemporalType.DATE)
    private Date fInicio;
    @Size(max = 2147483647)
    @Column(name = "fecha_fin")
    private String fechaFin;
    @Column(name = "f_fin")
    @Temporal(TemporalType.DATE)
    private Date fFin;
    @Size(max = 2147483647)
    @Column(name = "cobro_pendiente")
    private String cobroPendiente;
    @Size(max = 2147483647)
    @Column(name = "confirmada")
    private String confirmada;
    @Size(max = 2147483647)
    @Column(name = "a_finalizar")
    private String a_finalizar;
    @Size(max = 2147483647)
    @Column(name = "finalizada")
    private String finalizada;
    @Size(max = 2147483647)
    @Column(name = "ev_recibida")
    private String evRecibida;
    @Size(max = 2147483647)
    @Column(name = "ifc_enviada")
    private String ifcEnviada;
    @Column(name = "id")
    @Id
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "forma_pago")
    private String formaPago;
    @Size(max = 2147483647)
    @Column(name = "asbaja")
    private String asbaja;
    @Size(max = 2147483647)
    @Column(name = "diploma_enviado")
    private String diplomaEnviado;
    @Size(max = 2147483647)
    @Column(name = "pago_posterior")
    private String pagoPosterior;
    @Size(max = 2147483647)
    @Column(name = "ccc")
    private String ccc;
    @Size(max = 2147483647)
    @Column(name = "factura")
    private String factura;
    @Size(max = 2147483647)
    @Column(name = "modalidad")
    private String modalidad;
    @Size(max = 2147483647)
    @Column(name = "comercial")
    private String comercial;
    @Size(max = 2147483647)
    @Column(name = "asesoria")
    private String asesoria;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 2147483647)
    @Column(name = "proveedor")
    private String proveedor;
    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;
    @JoinColumn(name = "id", referencedColumnName = "id", updatable = false, insertable = false, nullable = false)
    @OneToOne
    private Matricula matricula;
    @Size(max = 2147483647)
    @Column(name = "pago_aplazado")
    private String pagoAplazado;
    @Size(max = 2147483647)
    @Column(name = "creado_xml_inicio")
    private String creadoXmlInicio;
    @Size(max = 2147483647)
    @Column(name = "diplomas_adicc")
    private String diplomasAdicc;
    @Size(max = 2147483647)
    @Column(name = "fecha_matricula")
    private String fechaMatricula;
    @Size(max = 2147483647)
    @Column(name = "email_alumno")
    private String emailAlumno;
    @Size(max = 2147483647)
    @Column(name = "telefono_empresa")
    private String telefonoEmpresa;
    @Size(max = 2147483647)
    @Column(name = "telefono_alumno")
    private String telefonoAlumno;
    @Size(max = 2147483647)
    @Column(name = "com_inicio_curso")
    private String comInicioCursio;
    @Size(max = 2147483647)
    @Column(name = "com_seg_curso")
    private String comSegCurso;
    @Size(max = 2147483647)
    @Column(name = "com_fin_curso")
    private String comFinCurso;

    public String getEmailAlumno() {
        return emailAlumno;
    }

    public void setEmailAlumno(String emailAlumno) {
        this.emailAlumno = emailAlumno;
    }

    public String getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public void setTelefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public String getTelefonoAlumno() {
        return telefonoAlumno;
    }

    public void setTelefonoAlumno(String telefonoAlumno) {
        this.telefonoAlumno = telefonoAlumno;
    }

    public String getComInicioCursio() {
        return comInicioCursio;
    }

    public void setComInicioCursio(String comInicioCursio) {
        this.comInicioCursio = comInicioCursio;
    }

    public String getComSegCurso() {
        return comSegCurso;
    }

    public void setComSegCurso(String comSegCurso) {
        this.comSegCurso = comSegCurso;
    }

    public String getComFinCurso() {
        return comFinCurso;
    }

    public void setComFinCurso(String comFinCurso) {
        this.comFinCurso = comFinCurso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getAsesoria() {
        return asesoria;
    }

    public void setAsesoria(String asesoria) {
        this.asesoria = asesoria;
    }

    public String getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(String fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getDiplomasAdicc() {
        return diplomasAdicc;
    }

    public void setDiplomasAdicc(String diplomasAdicc) {
        this.diplomasAdicc = diplomasAdicc;
    }

    public String getCreadoXmlInicio() {
        return creadoXmlInicio;
    }

    public void setCreadoXmlInicio(String creadoXmlInicio) {
        this.creadoXmlInicio = creadoXmlInicio;
    }

    public String getPagoAplazado() {
        return pagoAplazado;
    }

    public void setPagoAplazado(String pagoAplazado) {
        this.pagoAplazado = pagoAplazado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Matriculavista() {
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

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFInicio() {
        return fInicio;
    }

    public void setFInicio(Date fInicio) {
        this.fInicio = fInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFFin() {
        return fFin;
    }

    public void setFFin(Date fFin) {
        this.fFin = fFin;
    }

    public String getCobroPendiente() {
        return cobroPendiente;
    }

    public void setCobroPendiente(String cobroPendiente) {
        this.cobroPendiente = cobroPendiente;
    }

    public String getConfirmada() {
        return confirmada;
    }

    public void setConfirmada(String confirmada) {
        this.confirmada = confirmada;
    }

    public String getA_finalizar() {
        return a_finalizar;
    }

    public void setA_finalizar(String aFinalizar) {
        this.a_finalizar = aFinalizar;
    }

    public String getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(String finalizada) {
        this.finalizada = finalizada;
    }

    public String getEvRecibida() {
        return evRecibida;
    }

    public void setEvRecibida(String evRecibida) {
        this.evRecibida = evRecibida;
    }

    public String getIfcEnviada() {
        return ifcEnviada;
    }

    public void setIfcEnviada(String ifcEnviada) {
        this.ifcEnviada = ifcEnviada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getAsbaja() {
        return asbaja;
    }

    public void setAsbaja(String asbaja) {
        this.asbaja = asbaja;
    }

    public String getDiplomaEnviado() {
        return diplomaEnviado;
    }

    public void setDiplomaEnviado(String diplomaEnviado) {
        this.diplomaEnviado = diplomaEnviado;
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

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getComercial() {
        return comercial;
    }

    public void setComercial(String comercial) {
        this.comercial = comercial;
    }
}
