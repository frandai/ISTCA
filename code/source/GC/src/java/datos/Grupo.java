/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
import vista.UtilidadesVista;

/**
 *
 *
 */
@Entity
@Table(schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g"),
    @NamedQuery(name = "Grupo.findByAccionFormativa", query = "SELECT g FROM Grupo g WHERE g.grupoPK.accionFormativa = :accionFormativa"),
    @NamedQuery(name = "Grupo.findById", query = "SELECT g FROM Grupo g WHERE g.grupoPK.id = :id"),
    @NamedQuery(name = "Grupo.findByNombre", query = "SELECT g FROM Grupo g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "Grupo.findByAportPrivada", query = "SELECT g FROM Grupo g WHERE g.aportPrivada = :aportPrivada"),
    @NamedQuery(name = "Grupo.findByFInicio", query = "SELECT g FROM Grupo g WHERE g.fInicio = :fInicio"),
    @NamedQuery(name = "Grupo.findByFFin", query = "SELECT g FROM Grupo g WHERE g.fFin = :fFin"),
    @NamedQuery(name = "Grupo.findByHoraIM", query = "SELECT g FROM Grupo g WHERE g.horaIM = :horaIM"),
    @NamedQuery(name = "Grupo.findByHoraFM", query = "SELECT g FROM Grupo g WHERE g.horaFM = :horaFM"),
    @NamedQuery(name = "Grupo.findByHoraIT", query = "SELECT g FROM Grupo g WHERE g.horaIT = :horaIT"),
    @NamedQuery(name = "Grupo.findByHoraFT", query = "SELECT g FROM Grupo g WHERE g.horaFT = :horaFT"),
    @NamedQuery(name = "Grupo.findByMediosP", query = "SELECT g FROM Grupo g WHERE g.mediosP = :mediosP"),
    @NamedQuery(name = "Grupo.findByMediosEO", query = "SELECT g FROM Grupo g WHERE g.mediosEO = :mediosEO"),
    @NamedQuery(name = "Grupo.findByMediosC", query = "SELECT g FROM Grupo g WHERE g.mediosC = :mediosC"),
    @NamedQuery(name = "Grupo.findByObservaciones", query = "SELECT g FROM Grupo g WHERE g.observaciones = :observaciones")})
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GrupoPK grupoPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "APORT_PRIVADA", nullable = false)
    private boolean aportPrivada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "F_INICIO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "F_FIN", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fFin;
    @Column(name = "HORA_I_M")
    @Temporal(TemporalType.TIME)
    private Date horaIM;
    @Column(name = "HORA_F_M")
    @Temporal(TemporalType.TIME)
    private Date horaFM;
    @Column(name = "HORA_I_T")
    @Temporal(TemporalType.TIME)
    private Date horaIT;
    @Column(name = "HORA_F_T")
    @Temporal(TemporalType.TIME)
    private Date horaFT;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MEDIOS_P", nullable = false)
    private boolean mediosP;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MEDIOS_E_O", nullable = false)
    private boolean mediosEO;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MEDIOS_C", nullable = false)
    private boolean mediosC;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String observaciones;
    @ManyToMany(mappedBy = "grupoList")
    private List<Direccion> direccionList;
    @JoinTable(name = "GRUPO_DIA_SEMANA", joinColumns = {
        @JoinColumn(referencedColumnName = "ACCION_FORMATIVA", name = "ACCION_FORMATIVA", nullable = false),
        @JoinColumn(referencedColumnName = "ID", name = "ID", nullable = false)},
            inverseJoinColumns = {
        @JoinColumn(referencedColumnName = "ID", name = "DIA", nullable = false)})
    @ManyToMany
    private List<DiaSemana> diaSemanaList;
    @JoinTable(name = "TUTORIA", joinColumns = {
        @JoinColumn(name = "GRUPO", referencedColumnName = "ACCION_FORMATIVA", nullable = false),
        @JoinColumn(name = "ACCION_FORMATIVA", referencedColumnName = "ID", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "TUTOR", referencedColumnName = "NIF", nullable = false)})
    @ManyToMany
    private List<Tutor> tutorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private List<Matricula> matriculaList;
    @JoinColumns({
        @JoinColumn(name = "RESPONSABLE", referencedColumnName = "NUMERO", nullable = false),
        @JoinColumn(name = "TLF_RESP", referencedColumnName = "PERSONA", nullable = false)})
    @ManyToOne(optional = false)
    private PersonaTelefono personaTelefono;
    @JoinColumn(name = "ACCION_FORMATIVA", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AccionFormativa accionFormativa1;
    @JoinColumn(name = "PROVEEDOR", referencedColumnName = "NIF")
    @ManyToOne(optional = false)
    private Proveedor proveedor1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo1")
    private List<TutoriaHorario> tutoriaHorarioList;
    @Transient //Variable booleana que guarda si este grupo es de tipo "pendiente de gestionar" (con las fechas de 1900)
    private Boolean pendienteDeGestionar = null;
    @Transient //Variable de tipo cadena de caracteres que guarda el Horario del Grupo (desde/hasta qué horas)
    private String horario;

    public boolean contieneHorario(Horario hor, Tutor t) {
        try {

            for (TutoriaHorario x : t.getTutoriaHorarioList()) {
                if (x.getGrupo1().equals(this) && x.getHorario1().equals(hor)) {
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public Boolean getPendienteDeGestionar() {
        if (pendienteDeGestionar == null) {
            if (this.grupoPK != null && this.grupoPK.getId() == 0) {
                pendienteDeGestionar = true;
            } else {
                pendienteDeGestionar = false;
            }
        }
        return pendienteDeGestionar;
    }

    public void setPendienteDeGestionar(Boolean pendienteDeGestionar) {
        this.pendienteDeGestionar = pendienteDeGestionar;
    }

    public Grupo() {
    }

    public Grupo(GrupoPK grupoPK) {
        this.grupoPK = grupoPK;
    }

    public Grupo(GrupoPK grupoPK, String nombre, boolean aportPrivada, Date fInicio, Date fFin, boolean mediosP, boolean mediosEO, boolean mediosC) {
        this.grupoPK = grupoPK;
        this.nombre = nombre;
        this.aportPrivada = aportPrivada;
        this.fInicio = fInicio;
        this.fFin = fFin;
        this.mediosP = mediosP;
        this.mediosEO = mediosEO;
        this.mediosC = mediosC;
    }

    public Grupo(int accionFormativa, int id) {
        this.grupoPK = new GrupoPK(accionFormativa, id);
    }

    public GrupoPK getGrupoPK() {
        return grupoPK;
    }

    public void setGrupoPK(GrupoPK grupoPK) {
        this.grupoPK = grupoPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getAportPrivada() {
        return aportPrivada;
    }

    public Date getfInicio() {
        return fInicio;
    }

    public void setfInicio(Date fInicio) {
        this.fInicio = fInicio;
    }

    public Proveedor getProveedor1() {
        return proveedor1;
    }

    public void setProveedor1(Proveedor proveedor1) {
        this.proveedor1 = proveedor1;
    }

    public void setAportPrivada(boolean aportPrivada) {
        this.aportPrivada = aportPrivada;
    }

    public Date getFInicio() {
        return fInicio;
    }

    public void setFInicio(Date fInicio) {
        this.fInicio = fInicio;
    }

    public Date getFFin() {
        return fFin;
    }

    public void setFFin(Date fFin) {
        this.fFin = fFin;
    }

    public String getHoraIMString() {
        return UtilidadesVista.getHoraString(horaIM);
    }

    public void setHoraIM(Date horaIM) {
        this.horaIM = horaIM;
    }

    public String getHoraFMString() {
        return UtilidadesVista.getHoraString(horaFM);
    }

    public void setHoraFM(Date horaFM) {
        this.horaFM = horaFM;
    }

    public String getHoraITString() {
        return UtilidadesVista.getHoraString(horaIT);
    }

    public void setHoraIT(Date horaIT) {
        this.horaIT = horaIT;
    }

    public String getHoraFTString() {
        return UtilidadesVista.getHoraString(horaFT);
    }

    public void setHoraFT(Date horaFT) {
        this.horaFT = horaFT;
    }

    public boolean getMediosP() {
        return mediosP;
    }

    public void setMediosP(boolean mediosP) {
        this.mediosP = mediosP;
    }

    public boolean getMediosEO() {
        return mediosEO;
    }

    public void setMediosEO(boolean mediosEO) {
        this.mediosEO = mediosEO;
    }

    public boolean getMediosC() {
        return mediosC;
    }

    public void setMediosC(boolean mediosC) {
        this.mediosC = mediosC;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @XmlTransient
    public List<Direccion> getDireccionList() {
        return direccionList;
    }

    public void setDireccionList(List<Direccion> direccionList) {
        this.direccionList = direccionList;
    }

    @XmlTransient
    public List<DiaSemana> getDiaSemanaList() {
        return diaSemanaList;
    }

    public void setDiaSemanaList(List<DiaSemana> diaSemanaList) {
        this.diaSemanaList = diaSemanaList;
    }

    public String getDiasSemana() {
        String di = "";
        Collections.sort(diaSemanaList);
        for (DiaSemana d : diaSemanaList) {
            di += d.getNombre() + ", ";
        }
        if (!di.equals("")) {
            di = di.substring(0, di.length() - 2);
            di += ".";
        }
        return di;
    }

    @XmlTransient
    public List<Tutor> getTutorList() {
        if (tutorList != null && !tutorList.isEmpty()) {
            for (Tutor t : tutorList) {
                if (t.getHorasTutoria() == null) {
                    Short th = null;
                    for (TutoriaHorario thl : t.getTutoriaHorarioList()) {
                        if (thl.getHorario1().getId() == -1 && thl.getGrupo1().getGrupoPK().getId() == this.getGrupoPK().getId()
                                && 
                                thl.getGrupo1().getGrupoPK().getAccionFormativa()== this.getGrupoPK().getAccionFormativa()
                                ) {
                            th = thl.getHoras();
                        }
                    }
                    if (th == null) {
                        t.setHorasTutoria(new Short("" + (getAccionFormativa1().getHoras() / tutorList.size())));
                    } else {
                        t.setHorasTutoria(th);
                    }
                }
            }
        }
        return tutorList;
    }

    public void setTutorList(List<Tutor> tutorList) {
        this.tutorList = tutorList;
    }

    @XmlTransient
    public List<Matricula> getMatriculaList() {
        return matriculaList;
    }

    public void setMatriculaList(List<Matricula> matriculaList) {
        this.matriculaList = matriculaList;
    }

    public PersonaTelefono getPersonaTelefono() {
        return personaTelefono;
    }

    public void setPersonaTelefono(PersonaTelefono personaTelefono) {
        this.personaTelefono = personaTelefono;
    }

    public AccionFormativa getAccionFormativa1() {
        return accionFormativa1;
    }

    public void setAccionFormativa1(AccionFormativa accionFormativa1) {
        this.accionFormativa1 = accionFormativa1;
    }

    public Date getHoraIM() {
        return horaIM;
    }

    public Date getHoraFM() {
        return horaFM;
    }

    public Date getHoraIT() {
        return horaIT;
    }

    public Date getHoraFT() {
        return horaFT;
    }

    @XmlTransient
    public List<TutoriaHorario> getTutoriaHorarioList() {
        return tutoriaHorarioList;
    }

    public void setTutoriaHorarioList(List<TutoriaHorario> tutoriaHorarioList) {
        this.tutoriaHorarioList = tutoriaHorarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoPK != null ? grupoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.grupoPK == null && other.grupoPK != null) || (this.grupoPK != null && !this.grupoPK.equals(other.grupoPK))) {
            return false;
        }
        return true;
    }

    public String getHorario() {
        if (horario == null) {
            String horario = "";
            TutoriaHorario thI = null;
            TutoriaHorario thF = null;
            for (TutoriaHorario t : this.getTutoriaHorarioList()) {
                if (thI == null || thI.getHorario1().getId() > t.getHorario1().getId()) {
                    thI = t;
                }
                if (thF == null || thF.getHorario1().getId() < t.getHorario1().getId()) {
                    thF = t;
                }
            }
            if (thI != null && thF != null) {
                horario = "de " + thI.getHorario1().getHorarioInicioString() + " a " + thF.getHorario1().getHorarioFinString() + ".";
            }
            this.horario = horario;
        }
        return horario;
    }

    public List<Tutor> getTutorGestionList() {
        if (this.getProveedor1() != null) {
            List<Tutor> t = accionFormativa1.getTutorGestionList();
            ArrayList<Tutor> t1 = new ArrayList<Tutor>();
            for (Tutor tutor : t) {
                if (tutor.getEmpresa().getNif().equals(this.getProveedor1().getEmpresa().getNif())) {
                    t1.add(tutor);
                }
            }
            return t1;
        } else {
            if (accionFormativa1 != null) {
                return accionFormativa1.getTutorGestionList();
            } else {
                return null;
            }
        }
    }

    @Override
    public String toString() {
        return "datos.Grupo[ grupoPK=" + grupoPK + " ]";
    }
}
