/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion;

import bean.AlumnoFacade;
import bean.ComercialFacade;
import bean.ComercialTipoFacade;
import bean.DireccionFacade;
import bean.EmpresaFacade;
import bean.MatriculaFacade;
import bean.PersonaFacade;
import bean.PersonaTelefonoFacade;
import bean.TutorFacade;
import datos.Alumno;
import datos.Comercial;
import datos.ComercialTipo;
import datos.Direccion;
import datos.Empresa;
import datos.EmpresaMatricula;
import datos.EmpresaMatriculaCcc;
import datos.EmpresaTelefono;
import datos.Evento;
import datos.Grupo;
import datos.Matricula;
import datos.Persona;
import datos.PersonaTelefono;
import datos.Proveedor;
import datos.TelefonoTipo;
import datos.Tutor;
import datos.TutoriaHorario;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import util.Fecha;
import util.Validacion;
import util.descripcionEventos;
import vista.SesionActual;
import vista.UtilidadesVista;

/**
 *
 *dai
 */
@ManagedBean(name = "personaGestion")
@ViewScoped
public class PersonaGestion implements Serializable {

    @EJB
    private PersonaFacade personaFacade;
    @EJB
    private AlumnoFacade alumnoFacade;
    @EJB
    private TutorFacade tutorFacade;
    @EJB
    private DireccionFacade dirFacade;
    @EJB
    private EmpresaFacade eFacade;
    @EJB
    private ComercialTipoFacade ctfacade;
    @EJB
    private ComercialFacade cmfacade;
    @EJB
    private PersonaTelefonoFacade etFacade;
    @EJB
    private MatriculaFacade matFacade;
    private boolean modoAlta;//Variable booleana que identifica si estamos en Modo de Alta
    //o Modo de Guardado
    private Persona personaSeleccionada;//Variable Person que contiene la Persona Seleccionada
    private boolean esAlumno;//Variable auxiliar que identifica si la persona es Alumno
    private boolean esTutor;//Variable auxiliar que identifica si la persona es Tutor
    private boolean nifGenerado;
    private String NIfPrimario = "";//Variable que guarda el NIF actual de la persona, para acutalizarlo si éste cambia
    private PersonaTelefono telefonoSeleccionado;//Gestión de Teléfono de persona
    private Empresa empresaSeleccionada;//Gestón de las Empresas asociadas a la Persona
    private Matricula matriculaSeleccionada;//Variable que guara la matrícula de forma auxiliar si vienen desde matrícula
    private List<Proveedor> proveedoresPersona;//Listado de proveedores de los que la Persona puede ser Tutor
    private List<Proveedor> proveedoresPersonaFiltrados;
    private boolean borrado;//Valor booleano que indica TRUE si se ha eliminado la Matrícula
    //(para que se oculten los campos en el XHTML)
    private boolean vieneDesdeMatricula = false;//Variable booleana que indica si viene desde la página 
    //de Gestión de Matrícula, para hacer las gestiones determinadas.
    private Date fechaNuevoEvento;//Gestión de Eventos (Ausencias)
    private Date fechaNuevoEventoHasta;//Gestión de Eventos (Ausencias)
    @ManagedProperty("#{sesionActual}")
    private SesionActual sesionActual;//Variable de sesión que guarda los datos del usuario
    private Comercial comerc = new Comercial();//Gestión de Estructura Comercial
    private List<ComercialTipo> ctip;//Creación de nuevos Tipos de Comerciales
    private String nombreNuevoTipoComercial;//Creación de nuevos Tipos de Comerciales
    private boolean abrirASEC = false;//Gestión de nuevos Tipos de Comerciales
    private List<Comercial> comerciales;//Gestión de Estructura Comercial
    private List<Comercial> comercialesFiltrados;//Gestión de Estructura Comercial
    private Matricula matricula;//Variable que guara la matrícula de forma auxiliar si vien desde matrícula
    private String matID;//Variable que guara la matrícula de forma auxiliar si vien desde matrícula
    private boolean esRepNoEx = false;//Variable especial que busca si es el personal especial -100 (rep. leg. no exist.)
    private boolean modoCrearAdministrador = false;
    private boolean modoCrearAdministradorGuardado = false;
    @ManagedProperty("#{configuracionSistemaGestion}")
    private ConfiguracionSistemaGestion configSisGest;

    public boolean isModoCrearAdministrador() {
        return modoCrearAdministrador;
    }

    public void setModoCrearAdministrador(boolean modoCrearAdministrador) {
        this.modoCrearAdministrador = modoCrearAdministrador;
    }

    public boolean isModoCrearAdministradorGuardado() {
        return modoCrearAdministradorGuardado;
    }

    public void setModoCrearAdministradorGuardado(boolean modoCrearAdministradorGuardado) {
        this.modoCrearAdministradorGuardado = modoCrearAdministradorGuardado;
    }

    public ConfiguracionSistemaGestion getConfigSisGest() {
        return configSisGest;
    }

    public void setConfigSisGest(ConfiguracionSistemaGestion configSisGest) {
        this.configSisGest = configSisGest;
    }

    public boolean isEsRepNoEx() {
        String persona = UtilidadesVista.getParametroReq("persona");
        if (persona != null && persona.equals("-100")) {
            esRepNoEx = true;
        }
        return esRepNoEx;
    }

    public void setEsRepNoEx(boolean esRepNoEx) {
        this.esRepNoEx = esRepNoEx;
    }

    public PersonaFacade getPersonaFacade() {
        return personaFacade;
    }

    public void setPersonaFacade(PersonaFacade personaFacade) {
        this.personaFacade = personaFacade;
    }

    public String getMatID() {
        return matID;
    }

    public void setMatID(String matID) {
        this.matID = matID;
    }

    /**
     * Busca la matrícula, si viene desde matrícula, en Sesión ó en
     *
     * @return
     */
    public Matricula getMatricula() {
        if (matricula == null) {
            if (getSesionActual().getMatriculasAFin().containsKey(matID)) {
                matricula = getSesionActual().getMatriculasAFin().get(matID).getMatriculaSeleccionada();
            } else {
                matricula = matFacade.find(Integer.parseInt(matID));
            }
        }
        return matricula;
    }

    public Date getFechaNuevoEventoHasta() {
        return fechaNuevoEventoHasta;
    }

    public void setFechaNuevoEventoHasta(Date fechaNuevoEventoHasta) {
        this.fechaNuevoEventoHasta = fechaNuevoEventoHasta;
    }

    public List<Comercial> getComercialesFiltrados() {
        return comercialesFiltrados;
    }

    public void setComercialesFiltrados(List<Comercial> comercialesFiltrados) {
        this.comercialesFiltrados = comercialesFiltrados;
    }

    public List<Comercial> getComerciales() {
        if (comerciales == null) {
            comerciales = cmfacade.findAll();
        }
        return comerciales;
    }

    public void setComerciales(List<Comercial> comerciales) {
        this.comerciales = comerciales;
    }

    public boolean isAbrirASEC() {
        return abrirASEC;
    }

    public void setAbrirASEC(boolean abrirASEC) {
        this.abrirASEC = abrirASEC;
    }

    public String getNombreNuevoTipoComercial() {
        return nombreNuevoTipoComercial;
    }

    public void setNombreNuevoTipoComercial(String nombreNuevoTipoComercial) {
        this.nombreNuevoTipoComercial = nombreNuevoTipoComercial;
    }

    public void guardaNuevoTipoComercial(ActionEvent ev) {
        if (nombreNuevoTipoComercial != null && !nombreNuevoTipoComercial.equals("")) {
            try {
                if (ctfacade.findNamedQuery("ComercialTipo.findByNombre", "nombre", nombreNuevoTipoComercial) != null) {
                    UtilidadesVista.Mensaje("No se ha guardado el tipo ya que ya existía en la plataforma.", FacesMessage.SEVERITY_ERROR);
                    return;
                }
            } catch (javax.persistence.NoResultException e) {
            } catch (javax.ejb.EJBException e) {
            }
            comerc.getComercialTipo().setNombre(nombreNuevoTipoComercial);
            ctfacade.create(comerc.getComercialTipo());
            ctip = ctfacade.findAll();
            abrirASEC = true;
        }
    }

    public Comercial getComerc() {
        if (comerc.getComercialTipo() == null) {
            comerc.setComercialTipo(new ComercialTipo());
        }
        return comerc;
    }

    public void setComerc(Comercial comerc) {
        this.comerc = comerc;
    }

    public List<ComercialTipo> getCtip() {
        if (ctip == null) {
            ctip = ctfacade.findAll();
        }
        return ctip;
    }

    public void setCtip(List<ComercialTipo> ctip) {
        this.ctip = ctip;
    }

    public void asociaEC(ActionEvent e) {
        String nombre = getPersonaSeleccionada().getNombre() + " "
                + getPersonaSeleccionada().getApellido1() + " "
                + getPersonaSeleccionada().getApellido2() + " ("
                + getPersonaSeleccionada().getNif() + ")";
        if (comerc.getComercialSuperior() != null && nombre.equals(comerc.getNombreSuperior())) {
            UtilidadesVista.Mensaje("No es posible asociar como comercial superior el mismo comercial.", FacesMessage.SEVERITY_ERROR);
            return;
        }
        personaSeleccionada.setComercial(comerc);
        //guarda(e);
        abrirASEC = false;
    }

    public SesionActual getSesionActual() {
        return sesionActual;
    }

    public void setSesionActual(SesionActual sesionActual) {
        this.sesionActual = sesionActual;
    }

    public void nuevoEvento(ActionEvent event) {
        Date fecha = fechaNuevoEvento;
        if (fechaNuevoEventoHasta == null || !fechaNuevoEvento.before(fechaNuevoEventoHasta)) {
            fechaNuevoEventoHasta = fechaNuevoEvento;
        }
        while (fecha.before(fechaNuevoEventoHasta) || fecha.equals(fechaNuevoEventoHasta)) {
            boolean crea = true;
            for (Evento ev : getPersonaSeleccionada().getTutor().getEventoList()) {
                if (Fecha.getFechaDiaMesAnio(ev.getFechaCreacion()).equals(Fecha.getFechaDiaMesAnio(fecha))
                        && ev.getTipoDescripcionEvento() == descripcionEventos.AUSENCIA_TUTOR) {
                    crea = false;
                    break;
                }
            }
            if (crea) {
                Evento evnto = new Evento();
                evnto.setCreador(getSesionActual().getUsuario_conectado().getPersona());
                evnto.setTutor(getPersonaSeleccionada().getTutor());
                evnto.setFechaCreacion(fecha);
                descripcionEventos.configurarEvento(evnto, descripcionEventos.AUSENCIA_TUTOR);
                getPersonaSeleccionada().getTutor().getEventoList().add(evnto);
            }
            fecha = Fecha.getFechaSetDias(fecha, 1);
        }

        Collections.sort(getPersonaSeleccionada().getTutor().getEventoList());
        personaSeleccionada.setTutor(tutorFacade.edit(getPersonaSeleccionada().getTutor()));
        fechaNuevoEvento = null;
        fechaNuevoEventoHasta = null;
    }

    public Date getFechaNuevoEvento() {
        return fechaNuevoEvento;
    }

    public void setFechaNuevoEvento(Date fechaNuevoEvento) {
        if (this.fechaNuevoEventoHasta == null || this.fechaNuevoEventoHasta.before(fechaNuevoEvento)) {
            this.fechaNuevoEventoHasta = this.fechaNuevoEvento;
        }
        this.fechaNuevoEvento = fechaNuevoEvento;
    }

    public boolean isVieneDesdeMatricula() {
        return vieneDesdeMatricula;
    }

    public void setVieneDesdeMatricula(boolean vieneDesdeMatricula) {
        this.vieneDesdeMatricula = vieneDesdeMatricula;
    }

    public PersonaGestion() throws Exception {
        modoAlta = (UtilidadesVista.getParametroReq("persona") == null);
        vieneDesdeMatricula = (UtilidadesVista.getParametroReq("matricula") != null);
        if (vieneDesdeMatricula) {
            matID = UtilidadesVista.getParametroReq("matricula");
        }
        modoCrearAdministrador = (UtilidadesVista.getParametroReq("crearAdministrador") != null);
    }

    public Persona getPersonaSeleccionada() {
        if (personaSeleccionada == null) {
            String persona = UtilidadesVista.getParametroReq("persona");
            if (persona != null) {
                personaSeleccionada = personaFacade.find(persona);

                if (getSesionActual().tienePermiso(24)) {
                    boolean entra = false;
                    if (personaSeleccionada.getNif().equals(getSesionActual().getUsuario_conectado().getNif())) {
                        entra = true;
                    }
                    for (Comercial mc : personaSeleccionada.getComercialSuperiorList()) {
                        for (Persona p : mc.getPersonaList()) {
                            if (p.getNif().equals(getSesionActual().getUsuario_conectado().getNif())) {
                                entra = true;
                                break;
                            }
                            if (entra) {
                                break;
                            }
                        }
                    }
                    if (!entra) {
                        UtilidadesVista uv = new UtilidadesVista();
                        uv.redireccionar("/faces/JSF/vista/sin_permiso.xhtml");
                    }
                }

                nifGenerado = Validacion.esNifGenerado(personaSeleccionada.getNif());
                esAlumno = getPersonaSeleccionada().getAlumno() != null;
                esTutor = getPersonaSeleccionada().getTutor() != null;
                datosTutorAlumnoDefecto();
                NIfPrimario = getPersonaSeleccionada().getNif();
            } else {
                personaSeleccionada = new Persona();
                personaSeleccionada.setEmpresaList(new ArrayList<Empresa>());
                personaSeleccionada.setEmpresaList1(new ArrayList<Empresa>());
                personaSeleccionada.setPersonaTelefonoList(new ArrayList<PersonaTelefono>());
                Alumno al = new Alumno(getPersonaSeleccionada().getNif());
                al.setFnac_inventada(false);
                al.setGrupo_cotizacion_inventado(false);
                al.setNivel_estidios_inventado(false);
                al.setArea_funcional_inventada(false);
                al.setCategoria_profesional_inventada(false);
                personaSeleccionada.setAlumno(al);
                esAlumno = !modoCrearAdministrador;
                Tutor t = new Tutor(getPersonaSeleccionada().getNif());
                t.setEventoList(new ArrayList<Evento>());
                t.setGrupoList(new ArrayList<Grupo>());
                t.setTutoriaHorarioList(new ArrayList<TutoriaHorario>());
                personaSeleccionada.setTutor(t);
                esTutor = false;
                if (vieneDesdeMatricula) {
                    try {
                        setAniadirEmpresa(getMatricula().getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa());
                    } catch (Exception e) {
                    }
                }
            }
            if (personaSeleccionada.getDireccion() == null) {
                personaSeleccionada.setDireccion(new Direccion());
            }

        }

        if (personaSeleccionada.getComercial() != null) {
            comerc = personaSeleccionada.getComercial();
        }

        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(Persona personaSeleccionada) {
        this.personaSeleccionada = personaSeleccionada;
        if (getPersonaSeleccionada().getDireccion() == null) {
            getPersonaSeleccionada().setDireccion(new Direccion());
        }
    }

    public void cambiaNif() {
        if (nifGenerado) {
            getPersonaSeleccionada().setNif("" + Validacion.generaID(personaFacade));
        }
    }

    public boolean isModoAlta() {
        return modoAlta;
    }

    public void setModoAlta(boolean modoAlta) {
        this.modoAlta = modoAlta;
    }

    public boolean isEsAlumno() {
        return esAlumno;
    }

    public void setEsAlumno(boolean esAlumno) {
        this.esAlumno = esAlumno;
    }

    public boolean isEsTutor() {
        return esTutor;
    }

    public void setEsTutor(boolean esTutor) {
        this.esTutor = esTutor;
    }

    public boolean isNifGenerado() {
        return nifGenerado;
    }

    public void setNifGenerado(boolean nifGenerado) {
        this.nifGenerado = nifGenerado;
    }

    public void guarda(ActionEvent event) {
        try {
            boolean valido = true;

            if (NIfPrimario.equals("")
                    || !NIfPrimario.equals(getPersonaSeleccionada().getNif())) {
                try {
                    if (personaFacade.find(getPersonaSeleccionada().getNif()) != null) {
                        UtilidadesVista.Mensaje("El NIF introducido para la persona ya existe en la plataforma.", FacesMessage.SEVERITY_ERROR);
                        valido = false;
                    }
                } catch (Exception e) {
                }
            }

            if (esTutor && (getPersonaSeleccionada().getTutor() == null || getPersonaSeleccionada().getTutor().getEmpresa() == null)) {
                UtilidadesVista.Mensaje("El Tutor necesita estar asociado a un Proveedor.", FacesMessage.SEVERITY_ERROR);
                valido = false;
            }

            if (esAlumno && (getPersonaSeleccionada().getEmpresaList() == null || noTieneMat(getPersonaSeleccionada().getEmpresaList()))) {
                UtilidadesVista.Mensaje("El Alumno necesita estar asociado a una Empresa Matriculabe.", FacesMessage.SEVERITY_ERROR);
                valido = false;
            }

            if (esAlumno && personaSeleccionada.getAlumno().getNivelEstudios() == null) {
                UtilidadesVista.Mensaje("El Nivel de Estudios es obligatorio para el Alumno.", FacesMessage.SEVERITY_ERROR);
                valido = false;
            }
            if (esAlumno && personaSeleccionada.getAlumno().getGrupoCotizacion() == null) {
                UtilidadesVista.Mensaje("El Grupo de Cotización es obligatorio para el Alumno.", FacesMessage.SEVERITY_ERROR);
                valido = false;
            }
            if (esAlumno && personaSeleccionada.getAlumno().getAreaFuncional() == null) {
                UtilidadesVista.Mensaje("El Área Funcional es obligatorio para el Alumno.", FacesMessage.SEVERITY_ERROR);
                valido = false;
            }
            if (esAlumno && personaSeleccionada.getAlumno().getCategoriaProfesional() == null) {
                UtilidadesVista.Mensaje("La Categoría Profesional es obligatorio para el Alumno.", FacesMessage.SEVERITY_ERROR);
                valido = false;
            }

            if (personaSeleccionada.getNif() != null) {
                personaSeleccionada.setNif(personaSeleccionada.getNif().toUpperCase());
            }

            if (valido) {
                if (!esAlumno && getPersonaSeleccionada().getAlumno() != null) {
                    try {
                        if (getPersonaSeleccionada().getAlumno() != null && getPersonaSeleccionada().getAlumno().getNif() != null && alumnoFacade.find(getPersonaSeleccionada().getAlumno().getNif()) != null) {
                            alumnoFacade.remove(getPersonaSeleccionada().getAlumno());
                        }
                    } catch (EJBException e) {
                    }
                    getPersonaSeleccionada().setAlumno(null);
                } else if (esAlumno) {
                    personaSeleccionada.getAlumno().setNif(personaSeleccionada.getNif());
                }
                if (!esTutor && getPersonaSeleccionada().getTutor() != null) {
                    try {
                        if (getPersonaSeleccionada().getTutor() != null && getPersonaSeleccionada().getTutor().getNif() != null && tutorFacade.find(getPersonaSeleccionada().getTutor().getNif()) != null) {
                            tutorFacade.remove(getPersonaSeleccionada().getTutor());
                        }
                    } catch (EJBException e) {
                    }
                    getPersonaSeleccionada().setTutor(null);
                } else if (esTutor) {
                    personaSeleccionada.getTutor().setNif(personaSeleccionada.getNif());
                }

                if (getPersonaSeleccionada().getDireccion() != null && getPersonaSeleccionada().getDireccion().getVia() == null) {
                    getPersonaSeleccionada().setDireccion(null);
                }

                if (modoAlta) {
                    boolean fni = false, nei = false, gci = false, afi = false, cpi = false;
                    if (esAlumno) {
                        fni = personaSeleccionada.getAlumno().getFnac_inventada();
                        nei = personaSeleccionada.getAlumno().getNivel_estidios_inventado();
                        gci = personaSeleccionada.getAlumno().getCategoria_profesional_inventada();
                        afi = personaSeleccionada.getAlumno().getArea_funcional_inventada();
                        cpi = personaSeleccionada.getAlumno().getCategoria_profesional_inventada();
                    }

                    personaSeleccionada = personaFacade.create(personaSeleccionada);
                    if (esAlumno) {
                        personaSeleccionada.getAlumno().setFnac_inventada(fni);
                        personaSeleccionada.getAlumno().setNivel_estidios_inventado(nei);
                        personaSeleccionada.getAlumno().setCategoria_profesional_inventada(gci);
                        personaSeleccionada.getAlumno().setArea_funcional_inventada(afi);
                        personaSeleccionada.getAlumno().setCategoria_profesional_inventada(cpi);

                        personaSeleccionada = personaFacade.edit(personaSeleccionada);
                    }

                    modoAlta = false;
                } else {

                    if (!personaSeleccionada.getNif().equals(NIfPrimario)) {
                        personaSeleccionada = personaFacade.cambiaNif(personaSeleccionada, NIfPrimario);
                    }

                    //updateo manual de telefonos de persona...
                    List<PersonaTelefono> ep = personaSeleccionada.getPersonaTelefonoList();
                    for (PersonaTelefono personaTelefono : personaFacade.find(personaSeleccionada.getNif()).getPersonaTelefonoList()) {
                        boolean borra = true;
                        for (PersonaTelefono personaTelefono1 : ep) {
                            if (borra && personaTelefono1.getPersonaTelefonoPK().getNumero().equals(personaTelefono.getPersonaTelefonoPK().getNumero())) {
                                borra = false;
                            }
                        }
                        if (borra) {
                            etFacade.remove(etFacade.find(personaTelefono.getPersonaTelefonoPK()));
                        }
                    }
                    //fin.

                    personaSeleccionada = personaFacade.edit(personaSeleccionada);
                    if (getPersonaSeleccionada().getDireccion() != null) {
                        dirFacade.edit(getPersonaSeleccionada().getDireccion());
                    }
                }

                for (Empresa e : personaSeleccionada.getEmpresaList()) {
                    if (!e.getPersonaList().contains(personaSeleccionada)) {
                        e.getPersonaList().add(personaSeleccionada);
                    }
                    eFacade.edit(e);
                }

                NIfPrimario = getPersonaSeleccionada().getNif();
                UtilidadesVista.Mensaje("La persona se ha guardado.", FacesMessage.SEVERITY_INFO);
                if (vieneDesdeMatricula) {
                    try {
                        getMatricula().setAlumno(alumnoFacade.find(personaSeleccionada.getAlumno().getNif()));
                        ArrayList<EmpresaMatriculaCcc> emc = new ArrayList<EmpresaMatriculaCcc>();
                        for (Empresa empresa : personaSeleccionada.getEmpresaList()) {
                            try {
                                emc.addAll(empresa.getEmpresaMatricula().getEmpresaMatriculaCccList());
                            } catch (Exception e) {
                            }

                        }
                        if (matID.contains("MAT")) {
                            getSesionActual().getMatriculasAFin().get(matID).setEmpresas_asoc(emc);
                        }
                        UtilidadesVista.Mensaje("La persona se ha añadido a la matrícula.", FacesMessage.SEVERITY_INFO);
                    } catch (Exception e) {
                        UtilidadesVista.Mensaje("La persona NO se ha añadido a la matrícula.", FacesMessage.SEVERITY_ERROR);

                    }

                }

                datosTutorAlumnoDefecto();
                if (getPersonaSeleccionada().getDireccion() == null) {
                    getPersonaSeleccionada().setDireccion(new Direccion());
                }
                
                if (modoCrearAdministrador) {
                    getConfigSisGest().setExistePersonaAdministrador(true);
                    modoCrearAdministradorGuardado = true;
                }
            }

        } catch (Exception e) {
            String c = "";
            for (StackTraceElement t : e.getStackTrace()) {
                if (t.getMethodName().contains("guarda")) {
                    c += t;
                }
            }
            UtilidadesVista.Mensaje(e.getMessage() + ": " + e.getCause() + " - " + c, FacesMessage.SEVERITY_FATAL);
        }
    }

    private void datosTutorAlumnoDefecto() {
        if (!esAlumno) {
            Alumno al = new Alumno(getPersonaSeleccionada().getNif());
            getPersonaSeleccionada().setAlumno(al);
        }
        if (!esTutor) {
            Tutor t = new Tutor(getPersonaSeleccionada().getNif());
            t.setEventoList(new ArrayList<Evento>());
            t.setGrupoList(new ArrayList<Grupo>());
            t.setTutoriaHorarioList(new ArrayList<TutoriaHorario>());
            getPersonaSeleccionada().setTutor(t);
        }
    }

    public PersonaTelefono getTelefonoSeleccionado() {
        return telefonoSeleccionado;
    }

    public void setTelefonoSeleccionado(PersonaTelefono telefonoSeleccionado) {
        this.telefonoSeleccionado = telefonoSeleccionado;
    }

    public void aniadirTelefono(ActionEvent actionEvent) {
        PersonaTelefono ep = new PersonaTelefono("000000000", getPersonaSeleccionada().getNif());
        ep.setTipo(new TelefonoTipo(3));
        getPersonaSeleccionada().getPersonaTelefonoList().add(ep);
    }

    public Empresa getAniadirEmpresa() {
        return null;
    }

    public void setAniadirEmpresa(Empresa p) {
        for (Empresa per : getPersonaSeleccionada().getEmpresaList()) {
            if (p.equals(per)) {
                return;
            }
        }
        getPersonaSeleccionada().getEmpresaList().add(p);
        p.getPersonaList().add(personaSeleccionada);
        if (esTutor && p.getProveedor() != null && p.getProveedor().getNif() != null && getPersonaSeleccionada().getTutor().getEmpresa() == null) {
            getPersonaSeleccionada().getTutor().setEmpresa(p);
            p.getTutorList().add(personaSeleccionada.getTutor());
        }
    }

    public Empresa getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresa empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public void borrarEmpresa(ActionEvent actionEvent) {
        empresaSeleccionada.getPersonaList().remove(personaSeleccionada);
        getPersonaSeleccionada().getEmpresaList().remove(empresaSeleccionada);
        if (!modoAlta) {
            empresaSeleccionada = eFacade.find(empresaSeleccionada.getNif());
            empresaSeleccionada.getPersonaList().remove(personaSeleccionada);
            eFacade.edit(empresaSeleccionada);
        }
    }

    public void borrarTelefono(ActionEvent actionEvent) {
        getPersonaSeleccionada().getPersonaTelefonoList().remove(telefonoSeleccionado);
    }

    public Matricula getMatriculaSeleccionada() {
        return matriculaSeleccionada;
    }

    public void setMatriculaSeleccionada(Matricula matriculaSeleccionada) {
        this.matriculaSeleccionada = matriculaSeleccionada;
    }

    public List<Proveedor> getProveedoresPersona() {
        proveedoresPersona = new ArrayList<Proveedor>();
        if (getPersonaSeleccionada().getEmpresaList() != null) {
            for (Empresa e : getPersonaSeleccionada().getEmpresaList()) {
                if (e.getProveedor() != null) {
                    proveedoresPersona.add(e.getProveedor());
                }
            }
        }
        return proveedoresPersona;
    }

    public void setProveedoresPersona(List<Proveedor> proveedoresPersona) {
        this.proveedoresPersona = proveedoresPersona;
    }

    public List<Proveedor> getProveedoresPersonaFiltrados() {
        return proveedoresPersonaFiltrados;
    }

    public void setProveedoresPersonaFiltrados(List<Proveedor> proveedoresPersonaFiltrados) {
        this.proveedoresPersonaFiltrados = proveedoresPersonaFiltrados;
    }

    private boolean noTieneMat(List<Empresa> empresaList) {
        for (Empresa empresa : empresaList) {
            if (empresa.getEmpresaMatricula() != null) {
                return false;
            }
        }
        return true;
    }

    public void elimina(ActionEvent event) throws IOException {
        if (!esTutor) {
            personaSeleccionada.setTutor(null);
        }
        if (!esAlumno) {
            personaSeleccionada.setAlumno(null);
        }
        if (!personaSeleccionada.getEmpresaList1().isEmpty()) {
            String nif_empresas = "";
            for (Empresa s : personaSeleccionada.getEmpresaList1()) {
                nif_empresas += s.getNif() + " ";
            }
            UtilidadesVista.Mensaje("No se puede eliminar la Persona ya que es representante legal de la(s) empresa(s): " + nif_empresas, FacesMessage.SEVERITY_ERROR);

        } else {
            personaSeleccionada.setDireccion(null);
            personaFacade.remove(personaSeleccionada);
            System.out.println("[BORRAR][" + Fecha.getFechaAnioMesDiaHoraMinutoSegundo(new Date()) + "]Se ha Borrado la Persona " + this.getPersonaSeleccionada().getNif());
            borrado = true;
            UtilidadesVista.Mensaje("La persona se ha eliminado", FacesMessage.SEVERITY_INFO);
        }
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }
}
