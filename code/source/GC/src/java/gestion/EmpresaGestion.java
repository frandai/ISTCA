/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion;

import bean.ActividadFacade;
import bean.CColectivoFacade;
import bean.ComercialTipoFacade;
import bean.DireccionFacade;
import bean.EmpresaAgrupacionFacade;
import bean.EmpresaEstadoFacade;
import bean.EmpresaFacade;
import bean.EmpresaMatriculaAnioFacade;
import bean.EmpresaMatriculaCccFacade;
import bean.EmpresaMatriculaFacade;
import bean.EmpresaTelefonoFacade;
import bean.EventoEmpresaTipoFacade;
import bean.MatriculaFacade;
import bean.PersonaFacade;
import bean.ProveedorFacade;
import bean.TutorFacade;
import datos.Actividad;
import datos.CColectivo;
import datos.Comercial;
import datos.ComercialTipo;
import datos.CpLocalidad;
import datos.Direccion;
import datos.Empresa;
import datos.EmpresaAgrupacion;
import datos.EmpresaEstado;
import datos.EmpresaMatricula;
import datos.EmpresaMatriculaAnio;
import datos.EmpresaMatriculaCcc;
import datos.EmpresaMatriculaCccPK;
import datos.EmpresaTelefono;
import datos.Evento;
import datos.Matricula;
import datos.Persona;
import datos.Proveedor;
import datos.TelefonoTipo;
import datos.TipoVia;
import datosVistaFacade.NextEmpresaFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
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
@ManagedBean(name = "empresaGestion")
@ViewScoped
public class EmpresaGestion implements Serializable {

    @EJB
    private EmpresaFacade empresaFacade;
    @EJB
    private EmpresaEstadoFacade empresaEstadoFacade;
    @EJB
    private CColectivoFacade ccolectFacade;
    @EJB
    private EmpresaAgrupacionFacade agrupacionFacade;
    @EJB
    private ActividadFacade actividadFacade;
    @EJB
    private DireccionFacade dirFacade;
    @EJB
    private EmpresaMatriculaFacade emFacade;
    @EJB
    private EmpresaTelefonoFacade etFacade;
    @EJB
    private ProveedorFacade provFacade;
    @EJB
    private EmpresaMatriculaCccFacade emcFacade;
    @EJB
    private EmpresaMatriculaAnioFacade eanioFacade;
    @EJB
    private PersonaFacade persFacade;
    private Empresa empresaSeleccionada; //Variable Empresa que contiene la Empresa Seleccionada
    //Variables de edición
    private boolean modoAlta;//Variable booleana que identifica si estamos en Modo de Alta
    //o Modo de Guardado
    private boolean nifGenerado = false;
    private List<EmpresaEstado> estadosEmpresa;//Listado de Estados disponibles
    private List<CColectivo> ccolectivo;//Listado de Convenios Colectivos disponibles
    private List<EmpresaAgrupacion> agrupacion;//Listado de Agrupaciones disponibles
    private boolean esProveedor;//Variable que indica si la Empresa Seleccionada es un Proveedor
    private boolean esMatriculable;//Variable que indica si la Empresa Seleccionada es Matriculable
    private List<Actividad> actividades;//Listado de Actividades disponibles
    private String nombre_nuevo_estado;//Variable auxiliar para generar nuevo estado
    private EmpresaEstado ee = new EmpresaEstado();//Variable auxiliar para generar nuevo estado
    private CColectivo cc = new CColectivo();//Variable auxiliar para generar nuevo Conv. Col.
    private EmpresaAgrupacion ea = new EmpresaAgrupacion();//Variable auxiliar para generar nueva Agrupación
    private EmpresaTelefono telefonoSeleccionado;//Variable para Gestionar Teléfonos
    private EmpresaMatriculaAnio emAnioSeleccionado; //Variable para Gestionar Datos por Año
    private EmpresaMatriculaCcc emCCCSeleccionado; //Variable para Gestionar CCC
    private String nombre_nueva_agrupacion; //Variable auxiliar para generar nueva Agrupación
    private String nombre_nuevo_convenio;//Variable auxiliar para generar nuevo Conv. Col.
    private String NIfPrimario = "";//Variable que identifica el ID de la Empresa, si cambia se comprueba
    //que el nuevo ID no existe en la plataforma.
    private Persona personaSeleccionada;//Variable para Gestionar Personas
    private boolean vieneDesdeMatricula = false;//Variable booleana que indica si viene desde la página 
    //de Gestión de Matrícula, para hacer las gestiones determinadas.
    private boolean borrado;//Valor booleano que indica TRUE si se ha eliminado la AF
    //(para que se oculten los campos en el XHTML)
    private boolean tieneCCCAsociadosAMatriculas;//Variable que indica si existen CCC asoicados a Matrículas en la Empresa
    private boolean abrirASEC = false; //Variable para configuirar si abre el dialogo para Añadir Estructura Comercial
    private Comercial comerc = new Comercial(); //Variable Auxiliar para gestionar Estructura Comercial
    private String nombreNuevoTipoComercial; //Variable auxiliar para generar nuevo Tipo Comercial
    @EJB
    private ComercialTipoFacade ctfacade;
    private List<ComercialTipo> ctip;//Listado de Tipos de Comerciales disponibles
    private Date fechaNuevoEvento;//Variable para Generar Nuevas Notas de Empresa (eventos)
    private Date fechaNuevoEventoHasta;//Variable para Generar Nuevas Notas de Empresa (eventos)
    @ManagedProperty("#{sesionActual}")
    private SesionActual sesionActual;//Parámetro de Sesión que guarda los datos de la Sesión Actual
    @EJB
    private TutorFacade tutorFacade;
    private String matID;//Identificador de la Matrícula para volver a la Gestión de Matrículas
    private Matricula matricula;//Variable Matrícula para gestión de Matrículas
    @EJB
    private MatriculaFacade matFacade;
    private String descripcionEvento;//Variable para Generar Nuevos Tipos de Notas de Empresa (eventos)
    @EJB
    private NextEmpresaFacade nef;
    private boolean modoCrearProveedor = false;
    private boolean modoCrearProveedorGuardado = false;
    @ManagedProperty("#{configuracionSistemaGestion}")
    private ConfiguracionSistemaGestion configSisGest;

    public boolean isModoCrearProveedorGuardado() {
        return modoCrearProveedorGuardado;
    }

    public void setModoCrearProveedorGuardado(boolean modoCrearProveedorGuardado) {
        this.modoCrearProveedorGuardado = modoCrearProveedorGuardado;
    }

    public ConfiguracionSistemaGestion getConfigSisGest() {
        return configSisGest;
    }

    public void setConfigSisGest(ConfiguracionSistemaGestion configSisGest) {
        this.configSisGest = configSisGest;
    }

    /**
     * Busca el Rep. Leg. una vez introducido su NIF
     */
    public void buscaNIFRepLeg() {
        if (empresaSeleccionada.getRepresLegal().getNif() != null) {
            Persona rl = persFacade.find(empresaSeleccionada.getRepresLegal().getNif().toUpperCase());
            if (rl != null) {
                empresaSeleccionada.setRepresLegal(rl);
            }
        }
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public String getMatID() {
        return matID;
    }

    public void setMatID(String matID) {
        this.matID = matID;
    }

    public SesionActual getSesionActual() {
        return sesionActual;
    }

    public void setSesionActual(SesionActual sesionActual) {
        this.sesionActual = sesionActual;
    }

    public Date getFechaNuevoEvento() {
        return fechaNuevoEvento;
    }

    public void setFechaNuevoEvento(Date fechaNuevoEvento) {
        this.fechaNuevoEvento = fechaNuevoEvento;
    }

    public Date getFechaNuevoEventoHasta() {
        return fechaNuevoEventoHasta;
    }

    public void setFechaNuevoEventoHasta(Date fechaNuevoEventoHasta) {
        this.fechaNuevoEventoHasta = fechaNuevoEventoHasta;
    }

    /**
     * Función para generar Nuevas Notas para la Empresa.
     *
     * @param event
     */
    public void nuevoEventoEmpresa(ActionEvent event) {
        Evento evnto = new Evento();
        evnto.setCreador(getSesionActual().getUsuario_conectado().getPersona());
        evnto.setEmpresa(empresaSeleccionada);
        evnto.setFechaCreacion(fechaNuevoEvento);
        descripcionEventos.configurarEvento(evnto, descripcionEventos.NOTA_EMPRESA);
        evnto.setDescripcion(descripcionEvento);
        if (empresaSeleccionada.getEventoList() == null) {
            empresaSeleccionada.setEventoList(new ArrayList<Evento>());
        }
        empresaSeleccionada.getEventoList().add(evnto);
        Collections.sort(empresaSeleccionada.getEventoList());

        guarda(event);
    }

    /**
     * Función para generar Nuevas Ausencias para los Tutores de la Empresa
     *
     * @param event
     */
    public void nuevoEvento(ActionEvent event) {
        for (Persona p : empresaSeleccionada.getPersonaList()) {
            if (p.getTutor() != null) {
                Date fecha = fechaNuevoEvento;
                if (fechaNuevoEventoHasta == null || !fechaNuevoEvento.before(fechaNuevoEventoHasta)) {
                    fechaNuevoEventoHasta = fechaNuevoEvento;
                }
                while (fecha.before(fechaNuevoEventoHasta) || fecha.equals(fechaNuevoEventoHasta)) {
                    boolean crea = true;
                    for (Evento ev : p.getTutor().getEventoList()) {
                        if (Fecha.getFechaDiaMesAnio(ev.getFechaCreacion()).equals(Fecha.getFechaDiaMesAnio(fecha))
                                && ev.getTipoDescripcionEvento() == descripcionEventos.AUSENCIA_TUTOR) {
                            crea = false;
                            break;
                        }
                    }
                    if (crea) {
                        Evento evnto = new Evento();
                        evnto.setCreador(getSesionActual().getUsuario_conectado().getPersona());
                        evnto.setTutor(p.getTutor());
                        evnto.setFechaCreacion(fecha);
                        descripcionEventos.configurarEvento(evnto, descripcionEventos.AUSENCIA_TUTOR);
                        p.getTutor().getEventoList().add(evnto);
                    }
                    fecha = Fecha.getFechaSetDias(fecha, 1);
                }

                Collections.sort(p.getTutor().getEventoList());
                p.setTutor(tutorFacade.edit(p.getTutor()));

            }
        }
        fechaNuevoEvento = null;
        fechaNuevoEventoHasta = null;
        UtilidadesVista.Mensaje("Se han creado las Ausencias de los turores del proveedor.", FacesMessage.SEVERITY_INFO);
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

    /**
     * Función para Guardar Nuevos Tipos de Comercial
     *
     * @param ev
     */
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

    public List<ComercialTipo> getCtip() {
        if (ctip == null) {
            ctip = ctfacade.findAll();
        }
        return ctip;
    }

    public void setCtip(List<ComercialTipo> ctip) {
        this.ctip = ctip;
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

    /**
     * Función para Asociar Nivel Superior Comercial a Empresa
     *
     * @param e
     */
    public void asociaEC(ActionEvent e) {
        String nombre = this.getEmpresaSeleccionada().getRazonSocial() + " ("
                + this.getEmpresaSeleccionada().getNif() + ")";
        if (comerc.getComercialSuperior() != null && nombre.equals(comerc.getNombreSuperior())) {
            UtilidadesVista.Mensaje("No es posible asociar como Nivel superior el mismo comercial.", FacesMessage.SEVERITY_ERROR);
            return;
        }
        empresaSeleccionada.setComercial(comerc);
        //guarda(e);
        abrirASEC = false;
    }

    /**
     *      * Constructor: si viene por parámetro los datos de Empresa y/o
     * matrícula, se cargan en variables de la clase.
     */
    public EmpresaGestion() {
        modoAlta = (UtilidadesVista.getParametroReq("empresa") == null);
        vieneDesdeMatricula = (UtilidadesVista.getParametroReq("matricula") != null);
        if (vieneDesdeMatricula) {
            matID = UtilidadesVista.getParametroReq("matricula");
        }
        modoCrearProveedor = (UtilidadesVista.getParametroReq("crearProveedor") != null);
    }

    public boolean isModoCrearProveedor() {
        return modoCrearProveedor;
    }

    public void setModoCrearProveedor(boolean modoCrearProveedor) {
        this.modoCrearProveedor = modoCrearProveedor;
    }

    /**
     * Busca la Empresa seleccionada. En caso de no enviar Empresa seleccionada
     * por parámetro, se supone que se desea crear una AF nueva.
     *
     * @return
     */
    public Empresa getEmpresaSeleccionada() {
        if (empresaSeleccionada == null) {
            String empresa = UtilidadesVista.getParametroReq("empresa");
            if (empresa != null) {
                empresaSeleccionada = empresaFacade.find(empresa);

                //Se comprueba, si tiene permiso de ver las empresas de su EC,
                //que el comercial tiene permiso para ver ésta empresa.
                if (getSesionActual().tienePermiso(23)) {
                    boolean entra = false;
                    for (Comercial mc : empresaSeleccionada.getComercialSuperiorList()) {
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

                //Se cargan variables por defecto
                nifGenerado = Validacion.esNifGenerado(empresaSeleccionada.getNif());
                esMatriculable = empresaSeleccionada.getEmpresaMatricula() != null;
                esProveedor = empresaSeleccionada.getProveedor() != null;
                if (!esMatriculable) {
                    EmpresaMatricula em = new EmpresaMatricula(empresaSeleccionada.getNif());
                    em.setCColectivo(new CColectivo());
                    em.setAgrupacion(new EmpresaAgrupacion());
                    em.setEmpresaMatriculaAnioList(new ArrayList<EmpresaMatriculaAnio>());
                    em.setEmpresaMatriculaCccList(new ArrayList<EmpresaMatriculaCcc>());
                    empresaSeleccionada.setEmpresaMatricula(em);
                }
                if (!esProveedor) {
                    empresaSeleccionada.setProveedor(new Proveedor(empresaSeleccionada.getNif()));
                }
                NIfPrimario = empresaSeleccionada.getNif();
                if (empresaSeleccionada.getDireccion() == null) {
                    Direccion d = new Direccion();
                    d.setCpLocalidad(new CpLocalidad());
                    d.setTipoVia(new TipoVia(1));
                    empresaSeleccionada.setDireccion(d);
                }
            } else {
                //Se crea emrpesa nueva con valores por defecto
                empresaSeleccionada = new Empresa();
                empresaSeleccionada.setEmpresaTelefonoList(new ArrayList<EmpresaTelefono>());
                empresaSeleccionada.setEstado(empresaEstadoFacade.find(1));
                Direccion d = new Direccion();
                d.setCpLocalidad(new CpLocalidad());
                d.setTipoVia(new TipoVia(1));
                empresaSeleccionada.setDireccion(d);
                setEsMatriculable(!modoCrearProveedor);
                EmpresaMatricula em = new EmpresaMatricula(empresaSeleccionada.getNif());
                em.setCColectivo(new CColectivo(1));
                em.setAgrupacion(new EmpresaAgrupacion(1));
                em.setEmpresaMatriculaAnioList(new ArrayList<EmpresaMatriculaAnio>());
                em.setEmpresaMatriculaCccList(new ArrayList<EmpresaMatriculaCcc>());
                empresaSeleccionada.setEmpresaMatricula(em);
                setEsProveedor(modoCrearProveedor);
                empresaSeleccionada.setProveedor(new Proveedor(empresaSeleccionada.getNif()));
                empresaSeleccionada.setRepresLegal(new Persona());
                empresaSeleccionada.setPersonaList(new ArrayList<Persona>());
            }
            if (vieneDesdeMatricula) {
                if (empresaSeleccionada.getPersonaList() == null) {
                    empresaSeleccionada.setPersonaList(new ArrayList<Persona>());
                }
                try {
                    empresaSeleccionada.getPersonaList().add(getMatricula().getAlumno().getPersona());
                } catch (Exception e) {
                }
            }
            tieneCCCAsociadosAMatriculas = false;
            if (empresaSeleccionada.getEmpresaMatricula() != null && empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaCccList() != null) {
                for (EmpresaMatriculaCcc e : empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaCccList()) {
                    if (e.getMatriculaList().size() > 0) {
                        tieneCCCAsociadosAMatriculas = true;
                        break;
                    }
                }
            }
        }
        if (empresaSeleccionada.getComercial() != null) {
            comerc = empresaSeleccionada.getComercial();
        }

        return empresaSeleccionada;
    }

    public boolean isTieneCCCAsociadosAMatriculas() {
        getEmpresaSeleccionada();
        return tieneCCCAsociadosAMatriculas;
    }

    public void setTieneCCCAsociadosAMatriculas(boolean tieneCCCAsociadosAMatriculas) {
        this.tieneCCCAsociadosAMatriculas = tieneCCCAsociadosAMatriculas;
    }

    public void setEmpresaSeleccionada(Empresa empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public boolean isModoAlta() {
        return modoAlta;
    }

    public void setModoAlta(boolean modoAlta) {
        this.modoAlta = modoAlta;
    }

    public boolean isNifGenerado() {
        return nifGenerado;
    }

    public void setNifGenerado(boolean nifGenerado) {
        this.nifGenerado = nifGenerado;
    }

    /**
     * Función para guardar Nuevos Estados de Empresa. Comprueba que no exista
     * el estado a introducir.
     *
     * @param event
     */
    public void guardaNuevoEstado(ActionEvent event) {
        if (nombre_nuevo_estado != null && !nombre_nuevo_estado.equals("")) {
            try {
                if (empresaEstadoFacade.findNamedQuery("EmpresaEstado.findByNombre", "nombre", nombre_nuevo_estado) != null) {
                    UtilidadesVista.Mensaje("No se ha guardado el estado ya que ya existía en la plataforma.", FacesMessage.SEVERITY_ERROR);
                    return;
                }
            } catch (Exception e) {
            }
            ee.setNombre(nombre_nuevo_estado);
            empresaEstadoFacade.create(ee);
            estadosEmpresa = empresaEstadoFacade.findAll();
        }

    }

    /**
     * Función para guardar Nuevos Conv Col de Empresa. Comprueba que no exista
     * el Conv Col a introducir.
     *
     * @param event
     */
    public void guardaNuevoConvenio(ActionEvent event) {
        if (nombre_nuevo_convenio != null && !nombre_nuevo_convenio.equals("")) {
            try {
                if (ccolectFacade.findNamedQuery("CColectivo.findByNombre", "nombre", nombre_nuevo_convenio) != null) {
                    UtilidadesVista.Mensaje("No se ha guardado el convenio ya que ya existía en la plataforma.", FacesMessage.SEVERITY_ERROR);
                    return;
                }
            } catch (Exception e) {
            }
            cc.setNombre(nombre_nuevo_convenio);
            ccolectFacade.create(cc);
            ccolectivo = ccolectFacade.findAll();
        }

    }

    /**
     * Función para guardar Nuevas Agrupaciones de Empresa. Comprueba que no
     * exista la Agrupacion a introducir.
     *
     * @param event
     */
    public void guardaNuevaAgrupacion(ActionEvent event) {
        if (nombre_nueva_agrupacion != null && !nombre_nueva_agrupacion.equals("")) {
            try {
                if (agrupacionFacade.findNamedQuery("EmpresaAgrupacion.findByNombre", "nombre", nombre_nueva_agrupacion) != null) {
                    UtilidadesVista.Mensaje("No se ha guardado la agrupación ya que ya existía en la plataforma.", FacesMessage.SEVERITY_ERROR);
                    return;
                }
            } catch (Exception e) {
            }
            ea.setNombre(nombre_nueva_agrupacion);
            agrupacionFacade.create(ea);
            agrupacion = agrupacionFacade.findAll();
        }

    }

    /**
     * Función para guardar empresa. -Si la emrpesa no tiene relleno NIF, y no
     * es proveedor ni matriculable, se le generará un valor aleatorio "COMXXX"
     * -La empresa necesita tener una Actividad asociada. -La empresa necesita
     * tener una Dirección asociada si es Matriculable. -La empresa necesita
     * tener un Representante Legal asociado (si no es matriculable ni
     * proveedor, se le asignará el rep. leg. -100). -La empresa no puede tener
     * dos teléfonos iguales. -La empresa no puede tener más de un CCC vacío.
     * -La empresa no puede tener dos Datos de Año iguales.
     *
     * @param event
     */
    public void guarda(ActionEvent event) {
        try {
            boolean valido = true;
            //Se evalua si el NIF introducido para la empresa ya existía en la BD
            if (NIfPrimario.equals("")
                    || !NIfPrimario.equals(empresaSeleccionada.getNif())) {
                try {
                    if (empresaFacade.find(empresaSeleccionada.getNif()) != null) {
                        UtilidadesVista.Mensaje("El NIF introducido para la empresa ya existe en la plataforma.", FacesMessage.SEVERITY_ERROR);
                        valido = false;
                    }
                } catch (Exception e) {
                }
            }
            if (empresaSeleccionada.getNif() == null || empresaSeleccionada.getNif().equals("")) {
                if (!esMatriculable && !esProveedor) {
                    empresaSeleccionada.setNif("COM" + Validacion.generaID(empresaFacade, nef));
                } else {
                    UtilidadesVista.Mensaje("La empresa necesita tener un NIF relleno.", FacesMessage.SEVERITY_ERROR);
                    valido = false;
                }
            }
            if (empresaSeleccionada.getActividad() == null) {
                UtilidadesVista.Mensaje("La empresa necesita tener una Actividad asociada.", FacesMessage.SEVERITY_ERROR);
                valido = false;
            }
            if (empresaSeleccionada.getDireccion() == null || empresaSeleccionada.getDireccion().getVia() == null) {
                if (esMatriculable) {
                    UtilidadesVista.Mensaje("La empresa necesita tener una Dirección asociada si es Matriculable.", FacesMessage.SEVERITY_ERROR);
                    valido = false;
                } else {
                    empresaSeleccionada.setDireccion(null);
                }
            }
            if (empresaSeleccionada.getRepresLegal() == null || empresaSeleccionada.getRepresLegal().getNif() == null) {
                if (!esMatriculable && !esProveedor) {
                    empresaSeleccionada.setRepresLegal(persFacade.find("-100"));
                } else {
                    UtilidadesVista.Mensaje("La empresa necesita tener un Representante Legal asociado.", FacesMessage.SEVERITY_ERROR);
                    valido = false;
                }
            }
            //Control de Telefonos
            if (empresaSeleccionada.getEmpresaTelefonoList() != null && empresaSeleccionada.getEmpresaTelefonoList().size() > 0) {
                for (EmpresaTelefono t : empresaSeleccionada.getEmpresaTelefonoList()) {
                    int num_oc = 0;
                    for (EmpresaTelefono t2 : empresaSeleccionada.getEmpresaTelefonoList()) {
                        if (t.getEmpresaTelefonoPK().getNumero().equals(t2.getEmpresaTelefonoPK().getNumero())) {
                            num_oc++;
                        }
                    }
                    if (num_oc > 1) {
                        UtilidadesVista.Mensaje("La empresa no puede tener dos teléfonos iguales.", FacesMessage.SEVERITY_ERROR);
                        valido = false;
                        break;
                    }
                }
            }
            if (esMatriculable) {
                if (empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaCccList() != null && empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaCccList().size() > 0) {
                    boolean mas_de_1_ccc = false;
                    for (EmpresaMatriculaCcc c : empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaCccList()) {
                        if (c.getEmpresaMatriculaCccPK().getCcc().equals("")) {
                            if (!mas_de_1_ccc) {
                                mas_de_1_ccc = true;
                            } else {
                                UtilidadesVista.Mensaje("La empresa no puede tener más de un CCC vacío.", FacesMessage.SEVERITY_ERROR);
                                valido = false;
                                break;
                            }
                        }
                        int num_oc = 0;
                        for (EmpresaMatriculaCcc c2 : empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaCccList()) {
                            if (c.getEmpresaMatriculaCccPK().getCcc().equals(c2.getEmpresaMatriculaCccPK().getCcc())) {
                                num_oc++;
                            }
                        }
                        if (num_oc > 1) {
                            UtilidadesVista.Mensaje("La empresa no puede tener dos CCC iguales.", FacesMessage.SEVERITY_ERROR);
                            valido = false;
                            break;
                        }
                    }
                } else {
                    ArrayList<EmpresaMatriculaCcc> matVacias = new ArrayList<EmpresaMatriculaCcc>();
                    matVacias.add(new EmpresaMatriculaCcc(new EmpresaMatriculaCccPK(empresaSeleccionada.getNif(), "")));
                    empresaSeleccionada.getEmpresaMatricula().setEmpresaMatriculaCccList(matVacias);
                }
                if (empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaAnioList() != null && empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaAnioList().size() > 0) {
                    for (EmpresaMatriculaAnio c : empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaAnioList()) {
                        int num_oc = 0;
                        for (EmpresaMatriculaAnio c2 : empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaAnioList()) {
                            if (c.getEmpresaMatriculaAnioPK().getAnio() == c2.getEmpresaMatriculaAnioPK().getAnio()) {
                                num_oc++;
                            }
                        }
                        if (num_oc > 1) {
                            UtilidadesVista.Mensaje("La empresa no puede tener dos Datos de Año iguales.", FacesMessage.SEVERITY_ERROR);
                            valido = false;
                            break;
                        }
                    }
                }
            }
            if (empresaSeleccionada.getDireccion() != null && empresaSeleccionada.getDireccion().getVia() != null && (empresaSeleccionada.getDireccion().getCpLocalidad() == null
                    || empresaSeleccionada.getDireccion().getCpLocalidad().getCpLocalidadPK() == null)) {
                UtilidadesVista.Mensaje("Falta el CP de la dirección.", FacesMessage.SEVERITY_ERROR);
                valido = false;
            }
            if (valido) {
                if (!modoAlta) {
                    if (!empresaSeleccionada.getNif().equals(NIfPrimario)) {
                        empresaSeleccionada = empresaFacade.cambiaNif(empresaSeleccionada, NIfPrimario);
                    }
                }
                if (!esMatriculable && empresaSeleccionada.getEmpresaMatricula() != null) {

                    try {
                        if (empresaSeleccionada.getEmpresaMatricula() != null && empresaSeleccionada.getEmpresaMatricula().getNif() != null && emFacade.find(empresaSeleccionada.getEmpresaMatricula().getNif()) != null) {
                            emFacade.remove(empresaSeleccionada.getEmpresaMatricula());
                        }
                    } catch (EJBException e) {
                    }
                    empresaSeleccionada.setEmpresaMatricula(null);
                } else if (!modoAlta && esMatriculable) {
                    //updateo de CCC y de años...
                    //CCC
                    // if (!isTieneCCCAsociadosAMatriculas()) {
                    if (empresaFacade.find(empresaSeleccionada.getNif()).getEmpresaMatricula() != null) {
                        List<EmpresaMatriculaCcc> ec = empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaCccList();
                        for (EmpresaMatriculaCcc empresaC : empresaFacade.find(empresaSeleccionada.getNif()).getEmpresaMatricula().getEmpresaMatriculaCccList()) {
                            if (!empresaC.isEsNuevo() && (empresaC.getMatriculaList() == null || empresaC.getMatriculaList().isEmpty())) {
                                emcFacade.remove(emcFacade.find(empresaC.getEmpresaMatriculaCccPK()));
                            }
                        }
                        ArrayList<EmpresaMatriculaCcc> ec2 = new ArrayList<EmpresaMatriculaCcc>();
                        for (EmpresaMatriculaCcc empresaCCC : ec) {
                            if (empresaCCC.isEsNuevo() || (empresaCCC.getMatriculaList() == null || empresaCCC.getMatriculaList().isEmpty())) {
                                EmpresaMatriculaCcc ecT = new EmpresaMatriculaCcc(empresaSeleccionada.getNif(), empresaCCC.getEmpresaMatriculaCccPK().getCcc());
                                ecT.setIban(empresaCCC.getIban());
                                ecT.setMatriculaList(empresaCCC.getMatriculaList());
                                ec2.add(ecT);
                                emcFacade.create(ecT);
                            }
                        }
                        empresaSeleccionada.getEmpresaMatricula().setEmpresaMatriculaCccList(ec2);
                        // }
                        //...
                        //años..
                        List<EmpresaMatriculaAnio> eanio = empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaAnioList();
                        Empresa e = empresaFacade.find(empresaSeleccionada.getNif());
                        if (e.getEmpresaMatricula() != null && e.getEmpresaMatricula().getEmpresaMatriculaAnioList() != null) {
                            for (EmpresaMatriculaAnio empresaC : empresaFacade.find(empresaSeleccionada.getNif()).getEmpresaMatricula().getEmpresaMatriculaAnioList()) {
                                eanioFacade.remove(eanioFacade.find(empresaC.getEmpresaMatriculaAnioPK()));
                            }
                        }
                        ArrayList<EmpresaMatriculaAnio> eanio2 = new ArrayList<EmpresaMatriculaAnio>();
                        for (EmpresaMatriculaAnio empresaAnio : eanio) {
                            EmpresaMatriculaAnio ecT = new EmpresaMatriculaAnio(empresaSeleccionada.getNif(), empresaAnio.getEmpresaMatriculaAnioPK().getAnio());
                            ecT.setCreadoCentro(empresaAnio.getCreadoCentro());
                            ecT.setRecibeFormacion(empresaAnio.getRecibeFormacion());
                            ecT.setPlantilla(empresaAnio.getPlantilla());
                            ecT.setCreditoAsignado(empresaAnio.getCreditoAsignado());
                            eanio2.add(ecT);
                            eanioFacade.create(ecT);
                        }
                        empresaSeleccionada.getEmpresaMatricula().setEmpresaMatriculaAnioList(eanio2);

                    }
                }
                if (esMatriculable && empresaSeleccionada.getEmpresaMatricula() != null) {
                    empresaSeleccionada.getEmpresaMatricula().setNif(empresaSeleccionada.getNif());
                }
                if (!esProveedor && empresaSeleccionada.getProveedor() != null) {
                    try {
                        if (empresaSeleccionada.getProveedor() != null && empresaSeleccionada.getProveedor().getNif() != null && provFacade.find(empresaSeleccionada.getProveedor().getNif()) != null) {
                            provFacade.remove(empresaSeleccionada.getProveedor());
                        }
                    } catch (EJBException e) {
                    }

                    empresaSeleccionada.setProveedor(null);
                } else if (esProveedor) {
                    empresaSeleccionada.getProveedor().setNif(empresaSeleccionada.getNif());
                }
                //updateo manual de telefonos de empresa...
                if (!modoAlta) {
                    List<EmpresaTelefono> ep = empresaSeleccionada.getEmpresaTelefonoList();
                    for (EmpresaTelefono empresaTelefono : empresaFacade.find(empresaSeleccionada.getNif()).getEmpresaTelefonoList()) {
                        etFacade.remove(etFacade.find(empresaTelefono.getEmpresaTelefonoPK()));
                    }
                    if (ep != null) {
                        ArrayList<EmpresaTelefono> ep2 = new ArrayList<EmpresaTelefono>();
                        for (EmpresaTelefono empresaTelefono : ep) {
                            EmpresaTelefono epT = new EmpresaTelefono(empresaTelefono.getEmpresaTelefonoPK().getNumero(), empresaSeleccionada.getNif());
                            epT.setTipo(empresaTelefono.getTipo());
                            ep2.add(epT);
                            etFacade.create(epT);
                        }
                        empresaSeleccionada.setEmpresaTelefonoList(ep2);
                    }
                }
                //fin.

                empresaSeleccionada.getRepresLegal().setNif(empresaSeleccionada.getRepresLegal().getNif().toUpperCase());

                Persona rl = persFacade.find(empresaSeleccionada.getRepresLegal().getNif());

                if (rl == null) {
                    // Incidencia 830
                    // Crear un nuevo representante
                    Persona repLegal = new Persona(empresaSeleccionada.getRepresLegal().getNif(),
                            empresaSeleccionada.getRepresLegal().getNombre(),
                            empresaSeleccionada.getRepresLegal().getEmail(),
                            empresaSeleccionada.getRepresLegal().getMasculino());
                    repLegal.setApellido1(empresaSeleccionada.getRepresLegal().getApellido1());
                    repLegal.setApellido2(empresaSeleccionada.getRepresLegal().getApellido2());

                    // Asociar a la empresa
                    empresaSeleccionada.setRepresLegal(repLegal);

                    // Crear
                    persFacade.create(empresaSeleccionada.getRepresLegal());
                } else {
                    rl.setNombre(empresaSeleccionada.getRepresLegal().getNombre());
                    rl.setApellido1(empresaSeleccionada.getRepresLegal().getApellido1());
                    rl.setApellido2(empresaSeleccionada.getRepresLegal().getApellido2());
                    rl.setEmail(empresaSeleccionada.getRepresLegal().getEmail());
                    rl.setMasculino(empresaSeleccionada.getRepresLegal().getMasculino());
                    empresaSeleccionada.setRepresLegal(rl);
                    persFacade.edit(empresaSeleccionada.getRepresLegal());
                }
                if (empresaSeleccionada.getNif() != null) {
                    empresaSeleccionada.setNif(empresaSeleccionada.getNif().toUpperCase());
                }
                if (empresaSeleccionada.getEmpresaMatricula() != null && empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaAnioList() != null) {
                    for (EmpresaMatriculaAnio empresaEstado : empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaAnioList()) {
                        if (empresaEstado.getPlantilla() == null) {
                            empresaEstado.setPlantilla(0);
                        }
                    }
                }

                if (modoAlta) {
                    if (empresaSeleccionada.getDireccion() != null && empresaSeleccionada.getDireccion().getId() == null) {
                        dirFacade.create(empresaSeleccionada.getDireccion());
                    }
                    empresaFacade.create(empresaSeleccionada);
                    modoAlta = false;
                } else {

                    empresaSeleccionada = empresaFacade.edit(empresaSeleccionada);
                    empresaSeleccionada = empresaFacade.find(empresaSeleccionada.getNif());
                }

                for (Persona p : empresaSeleccionada.getPersonaList()) {
                    if (p.getEmpresaList() == null) {
                        p.setEmpresaList(new ArrayList<Empresa>());
                    }
                    p.getEmpresaList().add(empresaSeleccionada);
                    persFacade.edit(p);
                }
                NIfPrimario = empresaSeleccionada.getNif();
                UtilidadesVista.Mensaje("La empresa se ha guardado.", FacesMessage.SEVERITY_INFO);
                if (vieneDesdeMatricula) {
                    if (empresaSeleccionada.getEmpresaMatricula() != null && !empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaCccList().isEmpty()) {
                        try {
                            getMatricula().setAlumno(persFacade.find(getMatricula().getAlumno().getPersona().getNif()).getAlumno());

                        } catch (Exception e) {
                        }

                        EmpresaMatriculaCcc e = emcFacade.find(empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaCccList().get(0).getEmpresaMatriculaCccPK());

                        if (getMatricula().getEmpresaMatriculaCcc() == null
                                || getMatricula().getEmpresaMatriculaCcc().getEmpresaMatricula().getNif() == null
                                || !getMatricula().getEmpresaMatriculaCcc().getEmpresaMatricula().getNif().equals(empresaSeleccionada.getNif())) {
                            getMatricula().setEmpresaMatriculaCcc(e);
                        }
                        if (matID.contains("MAT")) {
                            getSesionActual().getMatriculasAFin().get(matID).getEmpresas_asoc().add(e);
                        }
                        UtilidadesVista.Mensaje("La empresa se ha añadido a la matrícula.", FacesMessage.SEVERITY_INFO);
                    } else {
                        UtilidadesVista.Mensaje("La empresa NO se ha añadido a la matrícula, puesto que no tiene CCC o no es matriculable.", FacesMessage.SEVERITY_ERROR);
                    }
                }

                if (!esMatriculable) {
                    EmpresaMatricula em = new EmpresaMatricula(empresaSeleccionada.getNif());
                    em.setCColectivo(new CColectivo());
                    em.setAgrupacion(new EmpresaAgrupacion());
                    em.setEmpresaMatriculaAnioList(new ArrayList<EmpresaMatriculaAnio>());
                    em.setEmpresaMatriculaCccList(new ArrayList<EmpresaMatriculaCcc>());
                    empresaSeleccionada.setEmpresaMatricula(em);
                } else {
                    boolean tieneTelefono = false;
                    for (EmpresaTelefono et : empresaSeleccionada.getEmpresaTelefonoList()) {
                        if (et.getTipo().getId().equals(3) || et.getTipo().getId().equals(1) || et.getTipo().getId().equals(5)) {
                            tieneTelefono = true;
                        }
                    }
                    if (!tieneTelefono) {
                        UtilidadesVista.Mensaje("Atención: la Empresa no tiene teléfono fijo asociado.", FacesMessage.SEVERITY_WARN);
                    }
                }
                if (!esProveedor) {
                    empresaSeleccionada.setProveedor(new Proveedor(empresaSeleccionada.getNif()));
                }
                if (modoCrearProveedor) {
                    getConfigSisGest().getSistema().setNIFempresaprincipal(empresaSeleccionada.getNif());
                    getConfigSisGest().setExisteProveedorPrincipal(true);
                    util.VariablesSistema.proveedor_principal = empresaSeleccionada.getProveedor();
                    modoCrearProveedorGuardado = true;
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
        if (empresaSeleccionada.getDireccion() == null) {

            Direccion d = new Direccion();
            d.setCpLocalidad(new CpLocalidad());
            d.setTipoVia(new TipoVia(1));
            empresaSeleccionada.setDireccion(d);
        }
    }

    public List<EmpresaEstado> getEstadosEmpresa() {
        if (estadosEmpresa == null) {
            estadosEmpresa = empresaEstadoFacade.findAll();
        }
        return estadosEmpresa;
    }

    public void setEstadosEmpresa(List<EmpresaEstado> estadosEmpresa) {
        this.estadosEmpresa = estadosEmpresa;
    }

    public void cambiaNif() {
        if (nifGenerado) {
            empresaSeleccionada.setNif("" + Validacion.generaID(empresaFacade, nef));
        }
    }

    public List<CColectivo> getCcolectivo() {
        if (ccolectivo == null) {
            ccolectivo = ccolectFacade.findAll();
        }
        return ccolectivo;
    }

    public void setCcolectivo(List<CColectivo> ccolectivo) {
        this.ccolectivo = ccolectivo;
    }

    public List<EmpresaAgrupacion> getAgrupacion() {
        if (agrupacion == null) {
            agrupacion = agrupacionFacade.findAll();
        }
        return agrupacion;
    }

    public void setAgrupacion(List<EmpresaAgrupacion> agrupacion) {
        this.agrupacion = agrupacion;
    }

    public boolean isEsProveedor() {
        return esProveedor;
    }

    public void setEsProveedor(boolean esProveedor) {
        this.esProveedor = esProveedor;
    }

    public boolean isEsMatriculable() {
        return esMatriculable;
    }

    public void setEsMatriculable(boolean esMatriculable) {
        this.esMatriculable = esMatriculable;
    }

    public List<Actividad> getActividades() {
        if (actividades == null) {
            actividades = actividadFacade.findAll();
        }
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public String getNombre_nuevo_estado() {
        return nombre_nuevo_estado;
    }

    public void setNombre_nuevo_estado(String nombre_nuevo_estado) {
        this.nombre_nuevo_estado = nombre_nuevo_estado;
    }

    public EmpresaTelefono getTelefonoSeleccionado() {
        return telefonoSeleccionado;
    }

    public void setTelefonoSeleccionado(EmpresaTelefono telefonoSeleccionado) {
        this.telefonoSeleccionado = telefonoSeleccionado;
    }

    public void borrarTelefono(ActionEvent actionEvent) {
        empresaSeleccionada.getEmpresaTelefonoList().remove(telefonoSeleccionado);
    }

    public void aniadirTelefono(ActionEvent actionEvent) {
        EmpresaTelefono ep = new EmpresaTelefono("000000000", empresaSeleccionada.getNif());
        ep.setTipo(new TelefonoTipo(3));
        empresaSeleccionada.getEmpresaTelefonoList().add(ep);
    }

    public void aniadirDatosPorAnio(ActionEvent actionEvent) {
        Calendar c = new GregorianCalendar();
        EmpresaMatriculaAnio datosAnio = new EmpresaMatriculaAnio(empresaSeleccionada.getNif(), c.get(GregorianCalendar.YEAR));
        empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaAnioList().add(datosAnio);
    }

    public void borrarDatosPorAnio(ActionEvent actionEvent) {
        empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaAnioList().remove(emAnioSeleccionado);
    }

    public void borrarDatosBancarios(ActionEvent actionEvent) {
        empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaCccList().remove(emCCCSeleccionado);
    }

    public void aniadirDatosBancarios(ActionEvent actionEvent) {
        EmpresaMatriculaCcc datosBancarios = new EmpresaMatriculaCcc(empresaSeleccionada.getNif(), "");
        datosBancarios.setEsNuevo(true);
        empresaSeleccionada.getEmpresaMatricula().getEmpresaMatriculaCccList().add(datosBancarios);
    }

    public String getNombre_nueva_agrupacion() {
        return nombre_nueva_agrupacion;
    }

    public void setNombre_nueva_agrupacion(String nombre_nueva_agrupacion) {
        this.nombre_nueva_agrupacion = nombre_nueva_agrupacion;
    }

    public String getNombre_nuevo_convenio() {
        return nombre_nuevo_convenio;
    }

    public void setNombre_nuevo_convenio(String nombre_nuevo_convenio) {
        this.nombre_nuevo_convenio = nombre_nuevo_convenio;
    }

    public EmpresaMatriculaAnio getEmAnioSeleccionado() {
        return emAnioSeleccionado;
    }

    public void setEmAnioSeleccionado(EmpresaMatriculaAnio emAnioSeleccionado) {
        this.emAnioSeleccionado = emAnioSeleccionado;
    }

    public EmpresaMatriculaCcc getEmCCCSeleccionado() {
        return emCCCSeleccionado;
    }

    public void setEmCCCSeleccionado(EmpresaMatriculaCcc emCCCSeleccionado) {
        this.emCCCSeleccionado = emCCCSeleccionado;
    }

    public Persona getPersonaSeleccionada() {
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(Persona personaSeleccionada) {
        this.personaSeleccionada = personaSeleccionada;
    }

    public Persona getAniadirPersona() {
        return null;
    }

    public void setAniadirPersona(Persona personaSeleccionada) {
        for (Persona per : getEmpresaSeleccionada().getPersonaList()) {
            if (personaSeleccionada.equals(per)) {
                return;
            }
        }
        empresaSeleccionada.getPersonaList().add(personaSeleccionada);
        personaSeleccionada.getEmpresaList().add(empresaSeleccionada);
    }

    public void borrarPersona(ActionEvent actionEvent) {
        empresaSeleccionada.getPersonaList().remove(personaSeleccionada);
    }

    public boolean isVieneDesdeMatricula() {
        return vieneDesdeMatricula;
    }

    public void setVieneDesdeMatricula(boolean vieneDesdeMatricula) {
        this.vieneDesdeMatricula = vieneDesdeMatricula;
    }

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

    public void elimina(ActionEvent event) throws IOException {
        if (!esProveedor) {
            empresaSeleccionada.setProveedor(null);
        }
        if (!esMatriculable) {
            empresaSeleccionada.setEmpresaMatricula(null);
        }
        empresaFacade.remove(empresaSeleccionada);
        System.out.println("[BORRAR][" + Fecha.getFechaAnioMesDiaHoraMinutoSegundo(new Date()) + "]Se ha Borrado la Empresa " + this.getEmpresaSeleccionada().getNif());
        borrado = true;
        UtilidadesVista.Mensaje("La empresa se ha eliminado", FacesMessage.SEVERITY_INFO);
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }
}
