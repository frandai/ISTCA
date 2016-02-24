/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import util.Fecha;
import util.Validacion;
import vista.UtilidadesVista;

/**
 *
 *
 */
@Entity
@Table(schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findByNif", query = "SELECT p FROM Persona p WHERE p.nif = :nif"),
    @NamedQuery(name = "Persona.findByNombre", query = "SELECT p FROM Persona p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Persona.findByApellido1", query = "SELECT p FROM Persona p WHERE p.apellido1 = :apellido1"),
    @NamedQuery(name = "Persona.findByApellido2", query = "SELECT p FROM Persona p WHERE p.apellido2 = :apellido2"),
    @NamedQuery(name = "Persona.findByNss", query = "SELECT p FROM Persona p WHERE p.nss = :nss"),
    @NamedQuery(name = "Persona.findByEmail", query = "SELECT p FROM Persona p WHERE p.email = :email"),
    @NamedQuery(name = "Persona.findByFechaNacimiento", query = "SELECT p FROM Persona p WHERE p.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Persona.findByMasculino", query = "SELECT p FROM Persona p WHERE p.masculino = :masculino")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String nif;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String nombre;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String apellido1;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String apellido2;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String nss;
    // @Pattern(regexp="[a-z0-9!#$%&"*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&"*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String email;
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean masculino;
    @ManyToMany(mappedBy = "personaList")
    private List<Empresa> empresaList;
    @JoinColumn(name = "DIRECCION", referencedColumnName = "ID")
    @ManyToOne(cascade = {CascadeType.ALL})
    private Direccion direccion;
    @JoinColumn(name = "COMERCIAL", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private Comercial comercial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "represLegal")
    private List<Empresa> empresaList1;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Usuario usuario;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Tutor tutor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona1")
    private List<PersonaTelefono> personaTelefonoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creador")
    private List<Evento> eventoList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Alumno alumno;
    @Transient //Variable que guarda el Tipo de Persona (Contacto / Alumno / Tutor)
    private String tipo;
    @Transient //Valor que convierte la Estructura Comercial de la Persona en una cadena de caracteres,
    //Para mostrársela al usuario.
    private String ecString;
    @Transient //Valor que muestra la EC superior de la Persona
    private List<Comercial> comercialSuperiorList;
    @Transient //Parámetro booleano para identifivar si se ha seleccionado ésta Persona
    //Para gestionarla en una función de impresión
    private boolean seleccionarPDF;

    public boolean isSeleccionarPDF() {
        return seleccionarPDF;
    }

    public void setSeleccionarPDF(boolean seleccionarPDF) {
        this.seleccionarPDF = seleccionarPDF;
    }

    public String getEcString() {
        if (ecString == null) {
            ecString = "";
            for (Comercial c : getComercialSuperiorList()) {
                ecString += "[ " + c.getNombre() + " (" + c.getComercialTipo().getNombre() + ") ]";
            }
        }
        return ecString;
    }

    public void setEcString(String ecString) {
        this.ecString = ecString;
    }

    public List<Comercial> getComercialSuperiorList() {
        if (comercialSuperiorList == null) {
            comercialSuperiorList = new ArrayList<Comercial>();
            try {
                Comercial c = comercial.getComercialSuperior();
                while (c != null) {
                    comercialSuperiorList.add(c);
                    c = c.getComercialSuperior();
                }
            } catch (NullPointerException e) {
            }
        }
        return comercialSuperiorList;
    }

    public void setComercialSuperiorList(List<Comercial> comercialSuperiorList) {
        this.comercialSuperiorList = comercialSuperiorList;
    }

    public String getTipo() {
        if (tipo == null) {
            tipo = (this.alumno != null && this.tutor != null) ? "Tutor + Alumno" : (this.alumno != null ? "Alumno" : (this.tutor != null ? "Tutor" : "Contacto"));
        }
        return tipo;
    }

    public Persona() {
    }

    public Persona(String nif) {
        this.nif = nif;
    }

    public Persona(String nif, String nombre, String email, boolean masculino) {
        this.nif = nif;
        this.nombre = nombre;
        this.email = email;
        this.masculino = masculino;
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

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getFechaNacimientoString() {

        return Fecha.getFechaDiaMesAnio(fechaNacimiento);
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean getMasculino() {
        return masculino;
    }

    public void setMasculino(boolean masculino) {
        this.masculino = masculino;
    }

    @XmlTransient
    public List<Empresa> getEmpresaList() {
        return empresaList;
    }

    public void setEmpresaList(List<Empresa> empresaList) {
        this.empresaList = empresaList;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Comercial getComercial() {
        return comercial;
    }

    public void setComercial(Comercial comercial) {
        this.comercial = comercial;
    }

    @XmlTransient
    public List<Empresa> getEmpresaList1() {
        return empresaList1;
    }

    public void setEmpresaList1(List<Empresa> empresaList1) {
        this.empresaList1 = empresaList1;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @XmlTransient
    public List<PersonaTelefono> getPersonaTelefonoList() {
        return personaTelefonoList;
    }

    public void setPersonaTelefonoList(List<PersonaTelefono> personaTelefonoList) {
        this.personaTelefonoList = personaTelefonoList;
    }

    @XmlTransient
    public List<Evento> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nif != null ? nif.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won"t work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.nif == null && other.nif != null) || (this.nif != null && !this.nif.equals(other.nif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.Persona[ nif=" + nif + " ]";
    }

    public String getTipoNif() {
        try {
            switch (Validacion.getTipoNif(nif)) {
                case Validacion.NIF_CIF:
                    return "CIF";
                case Validacion.NIF_DNI:
                    return "DNI";
                case Validacion.NIF_OTRO:
                    return "NIF";
                case Validacion.NIF_NIE:
                    return "NIE";
                default:
                    return "-";
            }
        } catch (Exception e) {
            return "-";
        }
    }

    public String getTelefonos() {
        String telefonos = "";
        for (PersonaTelefono t : personaTelefonoList) {
            telefonos += t.getPersonaTelefonoPK().getNumero() + " ";
        }
        return telefonos;
    }

    public String getNombreApellidos() {
        String NomAp = "";
        if (nombre != null) {
            NomAp += nombre + " ";
        }
        if (apellido1 != null) {
            NomAp += apellido1 + " ";
        }
        if (apellido2 != null) {
            NomAp += apellido2 + " ";
        }
        return NomAp;
    }
}
