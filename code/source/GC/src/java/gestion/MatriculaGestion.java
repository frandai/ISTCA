/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion;

import bean.EmpresaFacade;
import bean.EmpresaMatriculaCccFacade;
import bean.EventoFacade;
import bean.FacturaEstadoFacade;
import bean.FacturaFacade;
import bean.FormaPagoFacade;
import bean.MatriculaFacade;
import bean.PersonaFacade;
import bean.ProveedorFacade;
import com.itextpdf.text.DocumentException;
import datos.Alumno;
import datos.Comercial;
import datos.ComercialTipo;
import datos.Direccion;
import datos.Empresa;
import datos.EmpresaMatriculaAnio;
import datos.EmpresaMatriculaCcc;
import datos.Evento;
import datos.Factura;
import datos.FacturaEstado;
import datos.FacturaHistoricoEstado;
import datos.FacturaHistoricoEstadoPK;
import datos.FormaPago;
import datos.Iva;
import datos.Matricula;
import datos.MatriculaComercial;
import datos.MatriculaComercialPK;
import datos.MatriculaFactura;
import datos.MatriculaFacturaPK;
import datos.Persona;
import datosVista.Empresamcccvista;
import datosVista.Grupovista;
import datosVistaFacade.EmpresamcccvistaFacade;
import datosVistaFacade.GrupovistaFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import lazyDataModel.LazyEmpresaMCCCDataModel;
import lazyDataModel.LazyGrupoDataModel;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import util.BooleanGenerarDoc;
import util.Documentacion;
import util.Email;
import util.Fecha;
import util.VariablesSistema;
import util.descripcionEventos;
import vista.SesionActual;
import vista.UtilidadesVista;
import vista.matriculaVista;

/**
 *
 *dai
 */
@ManagedBean(name = "matriculaGestion")
@ViewScoped
public class MatriculaGestion implements Serializable {

    @EJB
    private MatriculaFacade matriculaFacade;
    @EJB
    private EmpresaFacade empFacade;
    @EJB
    private FormaPagoFacade formaPagoFacade;
    @EJB
    private EmpresamcccvistaFacade emcFac;
    @EJB
    private PersonaFacade persFacade;
    @EJB
    private GrupovistaFacade grupoFacade;
    private LazyDataModel<Empresamcccvista> todasEmpresasCCC; //Modelo que guarda los CCC que se pueden seleccionar
    private boolean guardada = false;//Variable que identifica si se guarda la matricula
    private LazyDataModel<Grupovista> grupos;//Modelo para seleccionar los grupos

    public LazyDataModel<Grupovista> getGrupos() {
        if (grupos == null) {
            grupos = new LazyGrupoDataModel(grupoFacade, true);
        }
        return grupos;
    }

    public LazyDataModel<Empresamcccvista> getTodasEmpresasCCC() {
        if (todasEmpresasCCC == null) {
            todasEmpresasCCC = new LazyEmpresaMCCCDataModel(emcFac);
        }
        return todasEmpresasCCC;
    }

    public void setTodasEmpresasCCC(LazyDataModel<Empresamcccvista> todasEmpresasCCC) {
        this.todasEmpresasCCC = todasEmpresasCCC;
    }
    private boolean modoAlta;//Variable booleana que identifica si estamos en Modo de Alta
    //o Modo de Guardado
    private Matricula matriculaSeleccionada;//Variable Matrícula que contiene la Matrícula Seleccionada
    private List<FormaPago> formaPago;//Lista de Formas de Pago
    private String nombre_nueva_forma_pago;//Gestión de nuevas formas de pago
    private FormaPago cc = new FormaPago();//Gestión de nuevas formas de pago
    private List<EmpresaMatriculaCcc> empresas_asoc = new ArrayList<EmpresaMatriculaCcc>();//Lista de CCC a asociar a una matrícula
    private boolean borrado;//Valor booleano que indica TRUE si se ha eliminado la Matrícula
    //(para que se oculten los campos en el XHTML)
    private List<FacturaEstado> estadoFactura;//Estados de factura que pueden seleccionarse
    @EJB
    private FacturaEstadoFacade estadoFacturaFacade;
    @EJB
    private FacturaFacade facturaFacade;
    @EJB
    private ProveedorFacade proveedorFacade;
    @EJB
    private EventoFacade evfac;
    private Factura facturaSeleccionada;//Gestión de Facturas
    private double importeMatriculaAbono;//Gestión de Facturas
    @ManagedProperty("#{sesionActual}")
    private SesionActual sesionActual;//Variable de sesión que guarda los datos del usuario
    private int evento_a_agenerar;//Gestión de Eventos
    private Date fechaNuevoEvento = new Date();//Gestión de Eventos
    private String alumnoALUMNOA;//Gestión de Diplomas Adiccionales
    private String alumnoDNIA;//Gestión de Diplomas Adiccionales
    private Comercial comercialMatricula;//Gestión de Estructura Comercial
    private String urlDiploma = "";//Gestión de Impresión de Diplomas
    private Date fechaEvento = new Date();//Gestión de Eventos
    private double importeEvento;//Gestión de Eventos
    private Date hoy = new Date();//Variable auxiliar para escribir la fecha de hoy
    private String motivoBaja;//Gestión de Bajas - Motivos de Baja
    private String matriculaID;//ID auxiliar de la matrícula
    private boolean muestraAvisoImporte = false;//Variable que cotnrola el diálogo de diferencia de importes
    private double importeEmpresa;//Variable que guarda el importe de la emrpesa
    private double importeBonificar;//Variable que guarda el importe bonificable de la emrpesa
    private boolean muestraDialogoImporte = false;//Variable que cotnrola el diálogo de diferencia de importes

    public boolean isMuestraDialogoImporte() {
        return muestraDialogoImporte;
    }

    public void setMuestraDialogoImporte(boolean muestraDialogoImporte) {
        this.muestraDialogoImporte = muestraDialogoImporte;
    }

    public double getImporteEmpresa() {
        return importeEmpresa;
    }

    public void setImporteEmpresa(double importeEmpresa) {
        this.importeEmpresa = importeEmpresa;
    }

    public double getImporteBonificar() {
        return importeBonificar;
    }

    public void setImporteBonificar(double importeBonificar) {
        this.importeBonificar = importeBonificar;
    }

    public boolean isMuestraAvisoImporte() {
        return muestraAvisoImporte;
    }

    public void setMuestraAvisoImporte(boolean muestraAvisoImporte) {
        this.muestraAvisoImporte = muestraAvisoImporte;
    }

    public String getMatriculaID() {
        return matriculaID;
    }

    public void setMatriculaID(String matriculaID) {
        this.matriculaID = matriculaID;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }
/**
 * Función para guardar evento en BD.
 */
    public void guardarEvento() {
        Evento pagoAplazado = new Evento();
        pagoAplazado.setCreador(getSesionActual().getUsuario_conectado().getPersona());
        pagoAplazado.setMatricula(matriculaSeleccionada);
        descripcionEventos.configurarEvento(pagoAplazado, descripcionEventos.PAGO_APLAZADO);
        pagoAplazado.setFechaVencimiento(fechaEvento);
        pagoAplazado.setDescripcion(descripcionEventos.getTextoDescripcionContiene(descripcionEventos.PAGO_APLAZADO) + " " + facturaSeleccionada.getId() + " - IMPORTE: " + importeEvento + " €");
        matriculaSeleccionada.getEventoList().add(pagoAplazado);

        matriculaSeleccionada = matriculaFacade.edit(matriculaSeleccionada, getSesionActual().getUsuario_conectado().getPersona());
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public double getImporteEvento() {
        return importeEvento;
    }

    public void setImporteEvento(double importeEvento) {
        this.importeEvento = importeEvento;
    }

    public String getUrlDiploma() {
        return urlDiploma;
    }

    public void setUrlDiploma(String urlDiploma) {
        this.urlDiploma = urlDiploma;
    }
/**
 * Función para generar la documentación RLT.
 * @throws IOException
 * @throws DocumentException 
 */
    public void generarRLT() throws IOException, DocumentException {
        urlDiploma = Documentacion.generar_RLT(matriculaSeleccionada);
        //Se genera el evento:
        boolean generar = true;
        Evento evnt = null;
        for (Evento e : matriculaSeleccionada.getEventoList()) {
            if (e.getTipoDescripcionEvento() == descripcionEventos.ENVIO_DOCUMENTACION) {
                if (Fecha.getFechaDiaMesAnio(e.getFechaCreacion()).equals(Fecha.getFechaDiaMesAnio(new Date()))) {
                    evnt = e;
                    generar = false;
                    break;
                }
            }
        }
        String descripcion = "A las " + Fecha.getFechaHoraMinuto(new Date()) + " Se ha generado la siguiente documentación:<ul>";
        descripcion += "<li>Se le ha generado el documento RLT.</li></ul>";
        if (generar) {
            Evento envioDocumentacion = new Evento();
            envioDocumentacion.setCreador(getSesionActual().getUsuario_conectado().getPersona());
            envioDocumentacion.setMatricula(matriculaSeleccionada);
            descripcionEventos.configurarEvento(envioDocumentacion, descripcionEventos.ENVIO_DOCUMENTACION);
            envioDocumentacion.setObservaciones(descripcion);
            matriculaSeleccionada.getEventoList().add(envioDocumentacion);

            matriculaSeleccionada = matriculaFacade.edit(matriculaSeleccionada, getSesionActual().getUsuario_conectado().getPersona());
        } else {
            if (evnt != null) {
                evnt.setObservaciones(evnt.getObservaciones() + "<br/>" + descripcion);
                evfac.edit(evnt);
            }
        }
    }
/**
 * Función para enviar por e-mail la documentación RLT.
 * @throws IOException
 * @throws DocumentException 
 */
    public void generarRLTEmail() throws IOException, DocumentException, Exception {
        List<Matricula> ms = new ArrayList<Matricula>();
        ms.add(matriculaSeleccionada);
        BooleanGenerarDoc gen = new BooleanGenerarDoc();
        gen.setGenerar_RLT(true);
        Documentacion.enviarMailsDocumentacion(ms, false, gen);

        //Se genera el evento:
        boolean generar = true;
        Evento evnt = null;
        for (Evento e : matriculaSeleccionada.getEventoList()) {
            if (e.getTipoDescripcionEvento() == descripcionEventos.ENVIO_DOCUMENTACION) {
                if (Fecha.getFechaDiaMesAnio(e.getFechaCreacion()).equals(Fecha.getFechaDiaMesAnio(new Date()))) {
                    evnt = e;
                    generar = false;
                    break;
                }
            }
        }
        String descripcion = "A las " + Fecha.getFechaHoraMinuto(new Date()) + " Se ha enviado por e-mail (" + matriculaSeleccionada.getEmailEnvio() + ") la siguiente documentación:<ul>";
        descripcion += "<li>Documento RLT.</li></ul>";
        if (generar) {
            Evento envioDocumentacion = new Evento();
            envioDocumentacion.setCreador(getSesionActual().getUsuario_conectado().getPersona());
            envioDocumentacion.setMatricula(matriculaSeleccionada);
            descripcionEventos.configurarEvento(envioDocumentacion, descripcionEventos.ENVIO_DOCUMENTACION);
            envioDocumentacion.setObservaciones(descripcion);
            matriculaSeleccionada.getEventoList().add(envioDocumentacion);

            matriculaSeleccionada = matriculaFacade.edit(matriculaSeleccionada, getSesionActual().getUsuario_conectado().getPersona());
        } else {
            if (evnt != null) {
                evnt.setObservaciones(evnt.getObservaciones() + "<br/>" + descripcion);
                evfac.edit(evnt);
            }
        }
        UtilidadesVista.Mensaje("Se le ha enviado correctamente por e-mail el Informe de la Representación Legal de Trabajadores a " + matriculaSeleccionada.getEmailEnvio() + ".", FacesMessage.SEVERITY_INFO);
    }
/**
 * Función para generar Diplomas (adiccionales o no adiccionales) para la matrícula seleccionada
 * @param ev
 * @throws IOException
 * @throws DocumentException 
 */
    public void generarDiploma(Evento ev) throws IOException, DocumentException {
        if (ev == null) {
            urlDiploma = Documentacion.generar_diploma(matriculaSeleccionada, true);
        } else {
            urlDiploma = Documentacion.generar_diploma(matriculaSeleccionada, ev.getDescripcion().split("<>")[2], ev.getDescripcion().split("<>")[1], true);
        }
        //Se genera el evento:
        boolean generar = true;
        Evento evnt = null;
        for (Evento e : matriculaSeleccionada.getEventoList()) {
            if (e.getTipoDescripcionEvento() == descripcionEventos.ENVIO_DOCUMENTACION) {
                if (Fecha.getFechaDiaMesAnio(e.getFechaCreacion()).equals(Fecha.getFechaDiaMesAnio(new Date()))) {
                    evnt = e;
                    generar = false;
                    break;
                }
            }
        }
        String descripcion = "A las " + Fecha.getFechaHoraMinuto(new Date()) + " Se ha generado la siguiente documentación:<ul>";
        descripcion += "<li>Se le ha generado el Diploma" + ((ev == null) ? "" : " Adicional (" + ev.getDescripcion().split("<>")[1] + " - " + ev.getDescripcion().split("<>")[2] + ")") + ".</li></ul>";
        if (generar) {
            Evento envioDocumentacion = new Evento();
            envioDocumentacion.setCreador(getSesionActual().getUsuario_conectado().getPersona());
            envioDocumentacion.setMatricula(matriculaSeleccionada);
            descripcionEventos.configurarEvento(envioDocumentacion, descripcionEventos.ENVIO_DOCUMENTACION);
            envioDocumentacion.setObservaciones(descripcion);
            matriculaSeleccionada.getEventoList().add(envioDocumentacion);

            matriculaSeleccionada = matriculaFacade.edit(matriculaSeleccionada, getSesionActual().getUsuario_conectado().getPersona());
        } else {
            if (evnt != null) {
                evnt.setObservaciones(evnt.getObservaciones() + "<br/>" + descripcion);
                evfac.edit(evnt);
            }
        }
    }

    public Comercial getComercialMatricula() {
        return comercialMatricula;
    }
/**
 * Función que guarda la EC de la Matrícua
 * @param comercialMatricula 
 */
    public void setComercialMatricula(Comercial comercialMatricula) {
        if (comercialMatricula != null) {
            Comercial comercialMatriculaP = comercialMatricula;
            ArrayList<MatriculaComercial> mcList = new ArrayList<MatriculaComercial>();
            while (comercialMatricula != null) {
                MatriculaComercial mc = new MatriculaComercial();
                mc.setComercial1(comercialMatricula);
                mc.setMatricula1(matriculaSeleccionada);
                try {
                    mc.setMatriculaComercialPK(new MatriculaComercialPK(comercialMatricula.getId(), matriculaSeleccionada.getId()));
                } catch (NullPointerException e) {
                }

                mc.setPorcentaje(0.0);
                mcList.add(mc);
                comercialMatricula = comercialMatricula.getComercialSuperior();
            }
            matriculaSeleccionada.setMatriculaComercialList(mcList);
            actualizaECEMpresaAlumno(comercialMatriculaP);
            this.comercialMatricula = comercialMatriculaP;
        }
    }

    public String getAlumnoALUMNOA() {
        return alumnoALUMNOA;
    }

    public void setAlumnoALUMNOA(String alumnoALUMNOA) {
        this.alumnoALUMNOA = alumnoALUMNOA;
    }

    public String getAlumnoDNIA() {
        return alumnoDNIA;
    }

    public void setAlumnoDNIA(String alumnoDNIA) {
        this.alumnoDNIA = alumnoDNIA;
    }

    public Date getFechaNuevoEvento() {
        return fechaNuevoEvento;
    }

    public void setFechaNuevoEvento(Date nuevoEvento) {
        this.fechaNuevoEvento = nuevoEvento;
    }

    public int getEvento_a_agenerar() {
        return evento_a_agenerar;
    }

    public void setEvento_a_agenerar(int evento_a_agenerar) {
        this.evento_a_agenerar = evento_a_agenerar;
    }

    public SesionActual getSesionActual() {
        return sesionActual;
    }

    public void setSesionActual(SesionActual sesionActual) {
        this.sesionActual = sesionActual;
    }

    public MatriculaFacade getMatriculaFacade() {
        return matriculaFacade;
    }

    public void setMatriculaFacade(MatriculaFacade matriculaFacade) {
        this.matriculaFacade = matriculaFacade;
    }

    public MatriculaGestion() {
        modoAlta = (UtilidadesVista.getParametroReq("matricula") == null || UtilidadesVista.getParametroReq("matricula").contains("MAT"));
    }

    public boolean isModoAlta() {
        return modoAlta;
    }

    public void setModoAlta(boolean modoAlta) {
        this.modoAlta = modoAlta;
    }
/**
 * Función que busca la Matrícula eleccionada. La busca tanto en sesión como en BD.
 * Si no la encuentra la creará con los valores por defecto.
 * @return 
 */
    public Matricula getMatriculaSeleccionada() {
        if (UtilidadesVista.getParametroReq("matricula") == null || !UtilidadesVista.getParametroReq("matricula").contains("MAT")) {
            if (matriculaSeleccionada == null
                    || UtilidadesVista.getParametroSes("desde_buscador_matricula").equals("si")
                    || (UtilidadesVista.getParametroReq("matricula") != null && !UtilidadesVista.getParametroReq("matricula").contains("MAT")
                    && !matriculaSeleccionada.getId().equals(Integer.parseInt(UtilidadesVista.getParametroReq("matricula"))))) {
                urlDiploma = "";
                UtilidadesVista.setParametroSes("desde_buscador_matricula", "no");
                modoAlta = (UtilidadesVista.getParametroReq("matricula") == null);
                if (modoAlta) {
                    matriculaSeleccionada = new Matricula();
                    matriculaSeleccionada.setEventoList(new ArrayList<Evento>());
                    matriculaSeleccionada.setMatriculaComercialList(new ArrayList<MatriculaComercial>());
                    matriculaSeleccionada.setMatriculaFacturaList(new ArrayList<MatriculaFactura>());
                    matriculaSeleccionada.setFechaCreacion(new Date());
                    empresas_asoc = new ArrayList<EmpresaMatriculaCcc>();
                    Random r = new Random();
                    matriculaID = "MAT" + r.nextInt();
                    getSesionActual().getMatriculasAFin().put(matriculaID, this);
                } else {
                    matriculaSeleccionada = matriculaFacade.find(Integer.parseInt(UtilidadesVista.getParametroReq("matricula")));
                    if (getSesionActual().tienePermiso(22)) {
                        boolean entra = false;
                        for (MatriculaComercial mc : matriculaSeleccionada.getMatriculaComercialList()) {
                            for (Persona p : mc.getComercial1().getPersonaList()) {
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
                    matriculaID = "" + matriculaSeleccionada.getId();
                    empresas_asoc = new ArrayList<EmpresaMatriculaCcc>();
                    for (Empresa emp : matriculaSeleccionada.getAlumno().getPersona().getEmpresaList()) {
                        if (emp.getEmpresaMatricula() != null && !emp.getEmpresaMatricula().getEmpresaMatriculaCccList().isEmpty()) {
                            empresas_asoc.addAll(emp.getEmpresaMatricula().getEmpresaMatriculaCccList());
                        }
                    }
                }
                borrado = false;
                if (matriculaSeleccionada.getDireccionEnvio() == null) {
                    matriculaSeleccionada.setDireccionEnvio(new Direccion());
                }
            }
        } else {
            if (UtilidadesVista.getParametroReq("matricula") != null && UtilidadesVista.getParametroReq("matricula").contains("MAT")) {
                matriculaID = UtilidadesVista.getParametroReq("matricula");
                this.matriculaSeleccionada = getSesionActual().getMatriculasAFin().get(UtilidadesVista.getParametroReq("matricula")).matriculaSeleccionada;
            }
        }
        return matriculaSeleccionada;
    }

    public void setMatriculaSeleccionada(Matricula matriculaSeleccionada) {
        this.matriculaSeleccionada = matriculaSeleccionada;
    }

    public List<FormaPago> getFormaPago() {
        if (formaPago == null) {
            formaPago = formaPagoFacade.findAll();
        }
        return formaPago;
    }

    public void setFormaPago(List<FormaPago> formaPago) {
        this.formaPago = formaPago;
    }
/**
 * Función para usar la Dirección del Alumno en la Dirección de Envío de la Matrícula
 * @param event 
 */
    public void usarDirAlum(ActionEvent event) {
        if (getMatriculaSeleccionada().getDireccionEnvio() == null) {
            getMatriculaSeleccionada().setDireccionEnvio(new Direccion());
        }
        getMatriculaSeleccionada().getDireccionEnvio().setCpLocalidad(getMatriculaSeleccionada().getAlumno().getPersona().getDireccion().getCpLocalidad());
        getMatriculaSeleccionada().getDireccionEnvio().setNumero(getMatriculaSeleccionada().getAlumno().getPersona().getDireccion().getNumero());
        getMatriculaSeleccionada().getDireccionEnvio().setExtra(getMatriculaSeleccionada().getAlumno().getPersona().getDireccion().getExtra());
        getMatriculaSeleccionada().getDireccionEnvio().setTipoVia(getMatriculaSeleccionada().getAlumno().getPersona().getDireccion().getTipoVia());
        getMatriculaSeleccionada().getDireccionEnvio().setVia(getMatriculaSeleccionada().getAlumno().getPersona().getDireccion().getVia());
    }
/**
 * Función para usar la Dirección de la Asesoría en la Dirección de Envío de la Matrícula
 * @param event 
 */
    public void usarDirAse(ActionEvent event) {
        if (getMatriculaSeleccionada().getDireccionEnvio() == null) {
            getMatriculaSeleccionada().setDireccionEnvio(new Direccion());
        }
        getMatriculaSeleccionada().getDireccionEnvio().setCpLocalidad(getMatriculaSeleccionada().getDireccionAsesoria().getCpLocalidad());
        getMatriculaSeleccionada().getDireccionEnvio().setNumero(getMatriculaSeleccionada().getDireccionAsesoria().getNumero());
        getMatriculaSeleccionada().getDireccionEnvio().setExtra(getMatriculaSeleccionada().getDireccionAsesoria().getExtra());
        getMatriculaSeleccionada().getDireccionEnvio().setTipoVia(getMatriculaSeleccionada().getDireccionAsesoria().getTipoVia());
        getMatriculaSeleccionada().getDireccionEnvio().setVia(getMatriculaSeleccionada().getDireccionAsesoria().getVia());
    }
/**
 * Función para añadir una Factura a la Matrícula
 * @param event 
 */
    public void aniadeFactura(ActionEvent event) {
        masFactura(false);
    }
/**
 * Función para añadir un Evento a la Matrícula, según el tipo de evento seleccionado
 * @param event 
 */
    public void nuevoEvento(ActionEvent event) throws Exception {
        Evento evnto = new Evento();
        evnto.setCreador(getSesionActual().getUsuario_conectado().getPersona());
        evnto.setMatricula(matriculaSeleccionada);
        evnto.setFechaCreacion(fechaNuevoEvento);
        descripcionEventos.configurarEvento(evnto, getEvento_a_agenerar());
        if (evento_a_agenerar == descripcionEventos.DIPLOMA_ADICCIONAL) {
            evnto.setDescripcion("DIPOMA_ADICIONAL<>" + alumnoALUMNOA + "<>" + alumnoDNIA);
        }
        if (evento_a_agenerar == descripcionEventos.MATRICULA_BAJA) {
            evnto.setDescripcion(descripcionEventos.getTextoDescripcionContiene(descripcionEventos.MATRICULA_BAJA) + " - " + motivoBaja);
            matriculaSeleccionada.setBaja(true);
        } else {
            boolean cambia = true;
            for (Factura f : matriculaSeleccionada.getFacturaList()) {
                if (f.getImporte() < 0.0) {
                    cambia = false;
                    break;
                }
            }
            if (cambia) {
                matriculaSeleccionada.setBaja(false);
            }
        }
        if (evento_a_agenerar == descripcionEventos.RECEPCION_EVALUACION) {
            for (Evento e : matriculaSeleccionada.getEventoList()) {
                if (e.getTipoDescripcionEvento() == descripcionEventos.COMUNICACION_FIN_CURSO || e.getTipoDescripcionEvento() == descripcionEventos.COMUNICACION_SEGUIMIENTO_CURSO
                        || e.getTipoDescripcionEvento() == descripcionEventos.COMUNICACION_INICIO_CURSO) {
                    if (e.getFechaRealizacion() == null) {
                        e.setFechaRealizacion(evnto.getFechaCreacion());
                        evfac.edit(e);
                    }
                }
            }
        }
        matriculaSeleccionada.getEventoList().add(evnto);
        if (evento_a_agenerar == descripcionEventos.RECEPCION_EVALUACION) {
            if (matriculaSeleccionada.getAlumno().getPersona().getEmail() != null && matriculaSeleccionada.getAlumno().getPersona().getEmail().contains("@")) {
                Email e = new Email(util.VariablesSistema.email_enviar);
                e.setPara(new String[]{matriculaSeleccionada.getAlumno().getPersona().getEmail()});
                e.setAsunto(VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial() + " - CONFIRMACIÓN DE RECEPCIÓN DE EVALUACIÓN");
                e.setCuerpo((matriculaSeleccionada.getAlumno().getPersona().getMasculino() ? "Estimado " : "Estimada ") + matriculaSeleccionada.getAlumno().getPersona().getNombre() + ":"
                                + "<br/><br/>Desde departamento de tutorías, le confirmamos que hemos recibido correctamente la evalaución del curso \"" + matriculaSeleccionada.getGrupo().getAccionFormativa1().getNombre() + "\", en breve recibirá el diploma acreditativo."
                                + "<br/><br/>Un saludo y gracias por confiar en nuestro centro para su formación.");
                matriculaVista.realizarEventoComunicacion(evnto, e, evfac);
                e.enviar();
            }
        }
        Collections.sort(matriculaSeleccionada.getEventoList());
        matriculaSeleccionada = matriculaFacade.edit(matriculaSeleccionada, getSesionActual().getUsuario_conectado().getPersona());
    }
/**
 * Función para añadir Factura
 * @param abono Si es o no es Factura de Abono
 */
    public void masFactura(boolean abono) {
        //Primero: guardamos la matrícula
        guarda(null);
        if (!guardada) {
            return;
        }
        //Añadimos una nueva factura a la matrícula, con el importe que no esté en una factura de la factura
        //estado pendiente.. y forma de pago de la matrícula.
        aniadirFactura(abono, true);
    }
/**
 * Función para añadir una Factura de Abono a la Matrícula
 * @param event 
 */
    public void aniadeFacturaAbono(ActionEvent event) {
        masFactura(true);
    }

    public void guardaCorrecto() {
        muestraAvisoImporte = true;
        guarda(null);
    }

    public void guarda() {
        if (!modoAlta) {
            guarda(null);
        }
    }
/**
 * Función para Guardar la Matrícula en la BD.
 * -Todas las empresas asociadas al grupo de la matrícula deben de pertenecer a la misma agrupación
 * -El alumno no debe de estar ya matriculado en la AF seleccionada
 * -El alumno no debe de estar ya matriculado en el Grupo Seleccionado
 * -Se generan todos los datos (eventos, propiedades) de la Matrícula que debe de tener por defecto
 * @param event 
 */
    public void guarda(ActionEvent event) {
        try {
            guardada = true;
            //Si el grupo no es pendiente de gestionar, todas las empresas del grupo deben de pertenecer a la misma agrupación.
            if (matriculaSeleccionada.getGrupo().getGrupoPK().getId() != 0) {
                Integer agrupacion = null;
                for (Matricula m : matriculaSeleccionada.getGrupo().getMatriculaList()) {
                    if (agrupacion == null) {
                        agrupacion = m.getEmpresaMatriculaCcc().getEmpresaMatricula().getAgrupacion().getId();
                    }
                    if (!m.getEmpresaMatriculaCcc().getEmpresaMatricula().getAgrupacion().getId().equals(agrupacion)) {
                        UtilidadesVista.Mensaje("Todas las empresas asociadas al grupo " + matriculaSeleccionada.getGrupo().getNombre() + " deben de pertenecer a la misma agrupación.", FacesMessage.SEVERITY_ERROR);
                        guardada = false;
                    }
                }
                if (agrupacion != null && !matriculaSeleccionada.getEmpresaMatriculaCcc().getEmpresaMatricula().getAgrupacion().getId().equals(agrupacion)) {
                    UtilidadesVista.Mensaje("Todas las empresas asociadas al grupo " + matriculaSeleccionada.getGrupo().getNombre() + " deben de pertenecer a la misma agrupación.", FacesMessage.SEVERITY_ERROR);
                    guardada = false;
                }
            }
            //Si el alumno ya tiene asociada una matrícula de la misma AF,
            //y la matrícula no tiene asociado el evento X (TODO: definir eventos),
            //no se permitirá dar de alta la matricula.
            if (modoAlta) {
                for (Matricula m : matriculaSeleccionada.getAlumno().getMatriculaList()) {
                    if (!m.equals(matriculaSeleccionada) && m.getGrupo().getAccionFormativa1().equals(matriculaSeleccionada.getGrupo().getAccionFormativa1())) {
                        //TODO: definir el evento que invalida este control
                        if (!(m.getBaja() && m.getImporteBonificar() == null)) {
                            UtilidadesVista.Mensaje("El alumno ya está matriculado de la Acción Formativa seleccionada.", FacesMessage.SEVERITY_ERROR);
                            guardada = false;
                            break;
                        }
                    }
                }
            } else {
                for (Matricula m : matriculaSeleccionada.getAlumno().getMatriculaList()) {
                    if (!m.equals(matriculaSeleccionada) && m.getGrupo().equals(matriculaSeleccionada.getGrupo()) && !m.getBaja() ) {
                        //TODO: definir el evento que invalida este control
                        UtilidadesVista.Mensaje("El alumno ya está matriculado del Grupo seleccionado.", FacesMessage.SEVERITY_ERROR);
                        guardada = false;
                        break;
                    }
                }
            }

            if (guardada) {


                if (!muestraAvisoImporte && matriculaSeleccionada.getImporteBonificar() == null && modoAlta) {
                    EmpresaMatriculaAnio ema = null;
                    for (EmpresaMatriculaAnio em : matriculaSeleccionada.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresaMatriculaAnioList()) {
                        if (em.getEmpresaMatriculaAnioPK().getAnio() == Fecha.getAnio(matriculaSeleccionada.getFechaCreacion() == null ? new Date() : matriculaSeleccionada.getFechaCreacion())) {
                            ema = em;
                        }
                    }
                    if (ema != null) {
                        double importe = ema.getCreditoDisponible();
                        importeEmpresa = importe;
                        importe -= matriculaSeleccionada.getPrecio();
                        if (importe < 0.0) {
                            importeBonificar = importe;
                            muestraDialogoImporte = true;
                            return;
                        }
                    }
                }

                String x = "El precio de la matricula es distinto del precio de la acción formativa.";
                if (!matriculaSeleccionada.getPrecio().equals(matriculaSeleccionada.getGrupo().getAccionFormativa1().getPrecio())) {

                    if (!matriculaSeleccionada.getObservaciones().contains(x)) {
                        matriculaSeleccionada.setObservaciones(matriculaSeleccionada.getObservaciones() + " - " + x);
                    }
                } else {
                    matriculaSeleccionada.setObservaciones(matriculaSeleccionada.getObservaciones().replace(" - " + x, ""));
                }

                if (matriculaSeleccionada.getDireccionEnvio() != null && (matriculaSeleccionada.getDireccionEnvio().getVia() == null || matriculaSeleccionada.getDireccionEnvio().getVia().equals(""))) {
                    matriculaSeleccionada.setDireccionEnvio(null);
                }
                //Se generan los eventos asociados a la matrícula:
                if (matriculaSeleccionada.getEventoList() == null) {
                    matriculaSeleccionada.setEventoList(new ArrayList<Evento>());
                }
                if (modoAlta || (matriculaSeleccionada.getPagoPosterior() != matriculaFacade.find(matriculaSeleccionada.getId()).getPagoPosterior())) {
                    for (Evento e : matriculaSeleccionada.getEventoList()) {
                        int el = e.getTipoDescripcionEvento();
                        if (e.getFechaRealizacion() == null && (el == descripcionEventos.PAGO_POSTERIOR_MESBON
                                || el == descripcionEventos.PAGO_POSTERIOR_TRASBON)) {
                            e.setFechaCreacion(new Date());
                        }
                    }
                    if (matriculaSeleccionada.getPagoPosterior() != -1) {
                        ArrayList<Evento> x1 = new ArrayList<Evento>();
                        for (Evento e : matriculaSeleccionada.getEventoList()) {
                            if ((e.getTipoDescripcionEvento() == descripcionEventos.PAGO_POSTERIOR_MESBON || e.getTipoDescripcionEvento() == descripcionEventos.PAGO_POSTERIOR_TRASBON) && Fecha.getFechaDiaMesAnio(e.getFechaCreacion()).equals(Fecha.getFechaDiaMesAnio(new Date()))) {
                                x1.add(e);
                            }
                        }
                        if (x1.isEmpty()) {
                            Evento pagoPosterior = new Evento();
                            pagoPosterior.setCreador(getSesionActual().getUsuario_conectado().getPersona());
                            pagoPosterior.setMatricula(matriculaSeleccionada);
                            descripcionEventos.configurarEvento(pagoPosterior, matriculaSeleccionada.getPagoPosterior() == 1 ? descripcionEventos.PAGO_POSTERIOR_TRASBON : descripcionEventos.PAGO_POSTERIOR_MESBON);
                            matriculaSeleccionada.getEventoList().add(pagoPosterior);
                        } else {
                            for (Evento evx : x1) {
                                descripcionEventos.configurarEvento(evx, matriculaSeleccionada.getPagoPosterior() == 1 ? descripcionEventos.PAGO_POSTERIOR_TRASBON : descripcionEventos.PAGO_POSTERIOR_MESBON);
                                evfac.edit(evx);
                            }
                        }
                    }
                }
                if (modoAlta) {


                    Evento eventoConfirmacionMatricula = new Evento();
                    descripcionEventos.configurarEvento(eventoConfirmacionMatricula, descripcionEventos.CONFIRMACION_MATRICULA);
                    eventoConfirmacionMatricula.setCreador(getSesionActual().getUsuario_conectado().getPersona());
                    eventoConfirmacionMatricula.setMatricula(matriculaSeleccionada);
                    matriculaSeleccionada.getEventoList().add(eventoConfirmacionMatricula);


                    matriculaSeleccionada.setBaja(false);

                    if (matriculaSeleccionada.getMatriculaComercialList() != null && !matriculaSeleccionada.getMatriculaComercialList().isEmpty()) {
                        List<MatriculaComercial> mc = matriculaSeleccionada.getMatriculaComercialList();
                        matriculaSeleccionada.setMatriculaComercialList(null);


                        matriculaSeleccionada = matriculaFacade.create(matriculaSeleccionada);
                        for (MatriculaComercial matriculaComercial : mc) {
                            if (matriculaComercial.getMatriculaComercialPK() == null) {
                                matriculaComercial.setMatriculaComercialPK(new MatriculaComercialPK(matriculaComercial.getComercial1().getId(), matriculaSeleccionada.getId()));
                            }
                        }
                        matriculaSeleccionada.setMatriculaComercialList(mc);
                        matriculaSeleccionada = matriculaFacade.edit(matriculaSeleccionada, getSesionActual().getUsuario_conectado().getPersona());
                    } else {
                        matriculaSeleccionada = matriculaFacade.create(matriculaSeleccionada);
                        matriculaSeleccionada = matriculaFacade.edit(matriculaSeleccionada, getSesionActual().getUsuario_conectado().getPersona());
                    }



                    getSesionActual().getMatriculasAFin().remove(matriculaID);
                    matriculaID = "" + matriculaSeleccionada.getId();
                    modoAlta = false;
                } else {
                    matriculaSeleccionada = matriculaFacade.edit(matriculaSeleccionada, getSesionActual().getUsuario_conectado().getPersona());
                    for (Evento ev : this.getMatriculaSeleccionada().getEventoList()) {
                        if (ev.getEstado().equals("G")) {
                            switch (ev.getTipoDescripcionEvento()) {
                                case descripcionEventos.COMUNICACION_INICIO_CURSO:
                                    ev.setFechaCreacion(matriculaSeleccionada.getGrupo().getFInicio());
                                    ev.setFechaVencimiento(Fecha.getFechaSetDias(matriculaSeleccionada.getGrupo().getFInicio(), 2));
                                    descripcionEventos.configurarEvento(ev, descripcionEventos.COMUNICACION_INICIO_CURSO);
                                    evfac.edit(ev);
                                    break;
                                case descripcionEventos.COMUNICACION_SEGUIMIENTO_CURSO:
                                    ev.setFechaCreacion(matriculaSeleccionada.getGrupo().getFInicio());
                                    ev.setFechaVencimiento(Fecha.getFechaSetDias(matriculaSeleccionada.getGrupo().getFInicio(), Fecha.getNumDiasEntre(matriculaSeleccionada.getGrupo().getFInicio(), matriculaSeleccionada.getGrupo().getFFin()) / 2));
                                    descripcionEventos.configurarEvento(ev, descripcionEventos.COMUNICACION_SEGUIMIENTO_CURSO);
                                    evfac.edit(ev);
                                    break;
                                case descripcionEventos.COMUNICACION_FIN_CURSO:
                                    ev.setFechaCreacion(matriculaSeleccionada.getGrupo().getFInicio());
                                    ev.setFechaVencimiento(Fecha.getFechaSetDias(matriculaSeleccionada.getGrupo().getFFin(), 2));
                                    descripcionEventos.configurarEvento(ev, descripcionEventos.COMUNICACION_FIN_CURSO);
                                    evfac.edit(ev);
                                    break;
                            }
                            if (ev.getTipoDescripcionEvento() == descripcionEventos.SEGUIMIENTO_TUTORIAL) {
                                ev.setFechaCreacion(matriculaSeleccionada.getGrupo().getFInicio());
                                descripcionEventos.configurarEvento(ev, descripcionEventos.SEGUIMIENTO_TUTORIAL);
                                evfac.edit(ev);
                            }
                        }
                    }
                    Evento ev = this.getMatriculaSeleccionada().getUltimoEventoPago();
                    if (ev != null) {
                        descripcionEventos.configurarEvento(ev, ev.getTipoDescripcionEvento());
                        evfac.edit(ev);
                    }
                }



                UtilidadesVista.Mensaje("La matrícula se ha guardado.", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            if (e.getMessage() == null || e.getMessage().equals("null")) {
                UtilidadesVista.Mensaje("Faltan Datos Obligatorios a rellenar en la Matrícula", FacesMessage.SEVERITY_ERROR);
            } else {
                String c = "";
                for (StackTraceElement t : e.getStackTrace()) {
                    if (t.getMethodName().contains("guarda")) {
                        c += t;
                    }
                }
                UtilidadesVista.Mensaje(e.getMessage() + ": " + e.getCause() + " - " + c, FacesMessage.SEVERITY_FATAL);
            }
            guardada = false;
        }
        if (matriculaSeleccionada.getMatriculaComercialList() != null) {
            MatriculaComercial mc = null;
            for (MatriculaComercial mt : matriculaSeleccionada.getMatriculaComercialList()) {
                if (mc == null || mt.getComercial1().getComercialTipo().getId() < mc.getComercial1().getComercialTipo().getId()) {
                    mc = mt;
                }
            }
            if (mc != null) {
                actualizaECEMpresaAlumno(mc.getComercial1());
            }
        }
        muestraAvisoImporte = false;
        muestraDialogoImporte = false;
    }

    public String getNombre_nueva_forma_pago() {
        return nombre_nueva_forma_pago;
    }

    public void setNombre_nueva_forma_pago(String nombre_nueva_forma_pago) {
        this.nombre_nueva_forma_pago = nombre_nueva_forma_pago;
    }
/**
 * Gestionar y Guardar Nuevas Formas de Pago
 * @param event 
 */
    public void guardaNuevaFormaPago(ActionEvent event) {
        if (nombre_nueva_forma_pago != null && !nombre_nueva_forma_pago.equals("")) {
            try {
                if (formaPagoFacade.findNamedQuery("FormaPago.findByNombre", "nombre", nombre_nueva_forma_pago) != null) {
                    UtilidadesVista.Mensaje("No se ha guardado la forma de pago ya que ya existía en la plataforma.", FacesMessage.SEVERITY_ERROR);
                    return;
                }
            } catch (Exception e) {
            }
            cc.setNombre(nombre_nueva_forma_pago);
            formaPagoFacade.create(cc);
            formaPago = formaPagoFacade.findAll();
        }

    }

    public Alumno getAniadirAlumno() {
        return null;
    }
/**
 * Añadir alumno a la Matrícula.
 * Si la matrícula no tiene empresa asociada, o la emrpesa asociada no pertenece al alumno,
 * se cambia la asociación de la empresa a la matrícula.
 * @param p 
 */
    public void setAniadirAlumno(Alumno p) {
        empresas_asoc = new ArrayList<EmpresaMatriculaCcc>();
        Alumno al_ant = null;
        if (matriculaSeleccionada.getAlumno() != null) {
            al_ant = matriculaSeleccionada.getAlumno();
        }
        matriculaSeleccionada.setAlumno(p);
        for (Empresa emp : p.getPersona().getEmpresaList()) {
            if (emp.getEmpresaMatricula() != null && !emp.getEmpresaMatricula().getEmpresaMatriculaCccList().isEmpty()) {
                empresas_asoc.add(emp.getEmpresaMatricula().getEmpresaMatriculaCccList().get(0));
            }
        }
        boolean contiene_matricula = false;
        if (matriculaSeleccionada.getEmpresaMatriculaCcc() != null) {
            for (EmpresaMatriculaCcc mc : empresas_asoc) {
                if (mc.getEmpresaMatricula().getNif().equals(matriculaSeleccionada.getEmpresaMatriculaCcc().getEmpresaMatricula().getNif())) {
                    contiene_matricula = true;
                }
            }
        }

        if (!empresas_asoc.isEmpty() && (matriculaSeleccionada.getEmpresaMatriculaCcc() == null || !contiene_matricula)) {
            if (getMatriculaSeleccionada().getFacturaList() == null || getMatriculaSeleccionada().getFacturaList().isEmpty()) {
                matriculaSeleccionada.setEmpresaMatriculaCcc(empresas_asoc.get(0));
                UtilidadesVista.Mensaje("Atención: se ha cambiado la Empresa de la Matrícula ya que el alumno no estaba asociado a dicha empresa.", FacesMessage.SEVERITY_WARN);
            } else {
                UtilidadesVista.Mensaje("No se puede cambiar de Alumno puesto que el Alumno seleccionado no está asociado a la empresa seleccionada.", FacesMessage.SEVERITY_ERROR);
                if (al_ant != null && !al_ant.getNif().equals(p.getNif())) {
                    setAniadirAlumno(al_ant);
                }
            }
        }
    }

    public List<EmpresaMatriculaCcc> getEmpresas_asoc() {
        return empresas_asoc;
    }

    public void setEmpresas_asoc(List<EmpresaMatriculaCcc> empresas_asoc) {
        this.empresas_asoc = empresas_asoc;
    }
/**
 * Borrar Dirección de Envío de Matrícula
 * @param actionEvent 
 */
    public void borrarDireccion(ActionEvent actionEvent) {
        getMatriculaSeleccionada().setDireccionEnvio(new Direccion());
    }
/**
 * Borrar Factura Seleccionada.
 * -Si se elimina una factura de abono, y la matrícula no tiene eventos de Baja, la matrícula deja de estar en estado baja.
 * @param actionEvent 
 */
    public void borrarFactura(ActionEvent actionEvent) {
        boolean borraAbono = facturaSeleccionada.getImporte() < 0.0;
        facturaFacade.remove(facturaSeleccionada);
        matriculaSeleccionada = matriculaFacade.find(matriculaSeleccionada.getId());
        if (borraAbono) {
            Evento ult = null;
            for (Evento e : matriculaSeleccionada.getEventoList()) {
                if (ult == null || ult.getFechaCreacion().before(e.getFechaCreacion())) {
                    ult = e;
                }
            }
            if (ult == null || !ult.getTipo().getId().equals(6)) {
                matriculaSeleccionada.setBaja(false);
                matriculaSeleccionada = matriculaFacade.edit(matriculaSeleccionada, getSesionActual().getUsuario_conectado().getPersona());
            }
        }
        System.out.println("[BORRAR][" + Fecha.getFechaAnioMesDiaHoraMinutoSegundo(new Date()) + "]Se ha Borrado la Factura " + facturaSeleccionada.getId());
        UtilidadesVista.Mensaje("La factura " + facturaSeleccionada.getId() + " se ha eliminado", FacesMessage.SEVERITY_INFO);
    }
/**
 * Guarda datos de la factura.
 * -Si pasa a estado devuelta, se genera el evento de gestión de moroso
 * @param actionEvent
 * @throws Exception 
 */
    public void guardarFactura(ActionEvent actionEvent) throws Exception {
        Factura fac = facturaFacade.find(facturaSeleccionada.getId());
        if (!fac.getUltimoEstado().getEstado().getId().equals(facturaSeleccionada.getUltimoEstado().getEstado().getId())
                && facturaSeleccionada.getUltimoEstado().getEstado().getId().equals(2)) {
            ArrayList<Matricula> matGen = new ArrayList<Matricula>();
            for (MatriculaFactura mf : facturaSeleccionada.getMatriculaFacturaList()) {
                Matricula m = mf.getMatricula1();
                if (!matGen.contains(m)) {
                    matGen.add(m);
                    Evento gestionMoroso = new Evento();
                    gestionMoroso.setCreador(getSesionActual().getUsuario_conectado().getPersona());
                    gestionMoroso.setMatricula(m);
                    descripcionEventos.configurarEvento(gestionMoroso, descripcionEventos.GESTION_MOROSO);
                    gestionMoroso.setDescripcion(descripcionEventos.getTextoDescripcionContiene(descripcionEventos.GESTION_MOROSO) + " " + mf.getFactura1().getId() + " (" + mf.getFactura1().getImporte() + " €)");
                    m.getEventoList().add(gestionMoroso);
                    matriculaFacade.edit(m, getSesionActual().getUsuario_conectado().getPersona());
                }
            }
        }
        facturaFacade.edit(facturaSeleccionada);
        for (Factura q : matriculaSeleccionada.getFacturaList()) {
            if (q.getImporte() >= 0.0 && !q.getId().equals(facturaSeleccionada.getId())) {
                q.setPorcentaje(facturaSeleccionada.getPorcentaje());
                q.setImporte(q.getImporte());
                facturaFacade.edit(q);
            }
        }
        matriculaSeleccionada = matriculaFacade.find(matriculaSeleccionada.getId());
        if (matriculaSeleccionada.getDireccionEnvio() == null) {
            matriculaSeleccionada.setDireccionEnvio(new Direccion());
        }
        UtilidadesVista.Mensaje("Se ha guardado la factura " + facturaSeleccionada.getId() + "", FacesMessage.SEVERITY_INFO);
    }
/**
 * Elimina la matrícula.
 * @param event
 * @throws IOException 
 */
    public void elimina(ActionEvent event) throws IOException {
        matriculaSeleccionada.setDireccionEnvio(null);
        matriculaFacade.remove(matriculaSeleccionada);
        System.out.println("[BORRAR][" + Fecha.getFechaAnioMesDiaHoraMinutoSegundo(new Date()) + "]Se ha Borrado la Matrícula " + this.getMatriculaSeleccionada().getId());
        borrado = true;
        UtilidadesVista.Mensaje("La matrícula se ha eliminado", FacesMessage.SEVERITY_INFO);
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    public List<FacturaEstado> getEstadoFactura() {
        if (estadoFactura == null) {
            estadoFactura = estadoFacturaFacade.findAll();
        }
        return estadoFactura;
    }

    public void setEstadoFactura(List<FacturaEstado> estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public Factura getFacturaSeleccionada() {
        return facturaSeleccionada;
    }

    public void setFacturaSeleccionada(Factura facturaSeleccionada) {
        this.facturaSeleccionada = facturaSeleccionada;
    }

    public void validaImporte(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        //matriculaGestion.matriculaSeleccionada.importeSinFacturar+matriculaGestion.facturaSeleccionada.importe
        Double importe = ((Double) arg2);
        if (importe < 0.0 /*|| importe > (matriculaSeleccionada.getImporteSinFacturar() + facturaSeleccionada.getImporte())*/) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El importe seleccionado está fuera de rango.", ""));
        }
    }

    public void validaFechaHoy(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        //matriculaGestion.matriculaSeleccionada.importeSinFacturar+matriculaGestion.facturaSeleccionada.importe
        Date fecha = ((Date) arg2);
        if (fecha.before(new Date())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "La fecha del evento está fuera de rango.", ""));
        }
    }
/**
 * Devuelve el Importe de las Facturas asociadas a la Matrícula
 * @return 
 */
    public double getImporteSumFacturas() {
        double importeFacturas = 0;
        for (Factura f1 : getMatriculaSeleccionada().getFacturaList()) {
            if (f1.getImporte() > 0.0) {
                importeFacturas += f1.getImporte();
            }
        }
        return importeFacturas;
    }

    public void validaImporteAbono(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        //matriculaGestion.matriculaSeleccionada.importeSinFacturar+matriculaGestion.facturaSeleccionada.importe
        Double importe = ((Double) arg2);
        double importeFacturas = getImporteSumFacturas();

        if (importe >= 0.0 || importe < (-importeFacturas)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El importe seleccionado está fuera de rango.", ""));
        }
    }

    public double getImporteMatriculaAbono() {
        return importeMatriculaAbono;
    }

    public void setImporteMatriculaAbono(double importeMatriculaAbono) {
        this.importeMatriculaAbono = importeMatriculaAbono;
    }

    public void actualizaPrecioImporteFacturas() {
        matriculaSeleccionada.setPrecio(matriculaSeleccionada.getPrecio() - matriculaSeleccionada.getImporteSinFacturar());
        guarda();
    }

    public FacturaFacade getFacturaFacade() {
        return facturaFacade;
    }

    public void setFacturaFacade(FacturaFacade facturaFacade) {
        this.facturaFacade = facturaFacade;
    }

    public ProveedorFacade getProveedorFacade() {
        return proveedorFacade;
    }

    public void setProveedorFacade(ProveedorFacade proveedorFacade) {
        this.proveedorFacade = proveedorFacade;
    }
/**
 * Añade nueva factura a la matrícula.
 * 
 * Se generan los porcentajes de la factura conforme a los porcentajes que tengan las demás facturas
 * (Coste de impartición/Coste de organización)
 * 
 * @param abono Si TRUE, genera una factura de abono
 * @param guardar Si TRUE, recarga los datos de la matrícula con los nuevos datos de la factura
 */
    public void aniadirFactura(boolean abono, boolean guardar) {
        try {
            Factura f = new Factura(facturaFacade.findNextID(matriculaSeleccionada.getFechaCreacion()));
            f.setFacturaHistoricoEstadoList(new ArrayList<FacturaHistoricoEstado>());
            FacturaHistoricoEstado fh = new FacturaHistoricoEstado(new FacturaHistoricoEstadoPK(f.getId(), new Date()));
            fh.setEstado(new FacturaEstado(abono ? 3 : 1));
            f.getFacturaHistoricoEstadoList().add(fh);
            f.setFecha(new Date());
            f.setProveedor(proveedorFacade.find(util.VariablesSistema.NIF_empresa_principal));
            f.setFormaPago(matriculaSeleccionada.getFormaPago());
            f.setMatriculaFacturaList(new ArrayList<MatriculaFactura>());
            Double importe_sin_facturar;
            if (abono) {
                importe_sin_facturar = importeMatriculaAbono;
            } else {
                importe_sin_facturar = matriculaSeleccionada.getImporteSinFacturar();
            }
            double pct = 0.2;
            if (matriculaSeleccionada.getFacturaList() != null && !matriculaSeleccionada.getFacturaList().isEmpty()) {
                pct = (matriculaSeleccionada.getFacturaList().get(0).getPorcentaje() / 100.0);
            }
            MatriculaFactura m1 = new MatriculaFactura(new MatriculaFacturaPK(matriculaSeleccionada.getId(), f.getId()), util.Validacion.Redondear(importe_sin_facturar * (1 - pct)));
            MatriculaFactura m2 = new MatriculaFactura(new MatriculaFacturaPK(matriculaSeleccionada.getId(), f.getId()), util.Validacion.Redondear(importe_sin_facturar * pct));
            m1.setObservaciones("COSTE DE IMPARTICIÓN DEL CURSO");
            m2.setObservaciones("COSTE DE ORGANIZACIÓN DEL CURSO");
            m1.setIva(new Iva(2));
            m2.setIva(new Iva(2));
            f.getMatriculaFacturaList().add(m1);
            f.getMatriculaFacturaList().add(m2);
            facturaFacade.create(f);

            if (guardar) {
                matriculaSeleccionada = matriculaFacade.find(matriculaSeleccionada.getId());
                if (abono) {
                    matriculaSeleccionada.setBaja(true);
                    matriculaSeleccionada = matriculaFacade.edit(matriculaSeleccionada, getSesionActual().getUsuario_conectado().getPersona());
                }
                UtilidadesVista.Mensaje("La factura se ha guardado.", FacesMessage.SEVERITY_INFO);
            }


        } catch (Exception e) {
            String c = "";
            for (StackTraceElement t : e.getStackTrace()) {
                if (t.getMethodName().contains("aniadeFactura")) {
                    c += t;
                }
            }
            UtilidadesVista.Mensaje(e.getMessage() + ": " + e.getCause() + " - " + c, FacesMessage.SEVERITY_FATAL);
        }
    }
/**
 * Función para acutalizar la EC de la empresa y del alumno, si no tienen EC asociada, conforme a la EC de la matrícula.
 * @param comercialMatriculaP 
 */
    private void actualizaECEMpresaAlumno(Comercial comercialMatriculaP) {
        if (matriculaSeleccionada.getEmpresaMatriculaCcc() != null
                && matriculaSeleccionada.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getComercial() == null) {
            Comercial c = new Comercial();
            c.setComercialSuperior(comercialMatriculaP);
            c.setComercialTipo(new ComercialTipo(16));
            matriculaSeleccionada.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().setComercial(c);
            empFacade.edit(matriculaSeleccionada.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa());
            UtilidadesVista.Mensaje("Al no tener E.C. la empresa seleccionada, se le ha asociado la E.C. de la Matrícula.", FacesMessage.SEVERITY_WARN);
        }
        if (matriculaSeleccionada.getAlumno() != null
                && matriculaSeleccionada.getAlumno().getPersona().getComercial() == null) {
            Comercial c = new Comercial();
            c.setComercialSuperior(comercialMatriculaP);
            c.setComercialTipo(new ComercialTipo(17));
            matriculaSeleccionada.getAlumno().getPersona().setComercial(c);
            persFacade.edit(matriculaSeleccionada.getAlumno().getPersona());
            UtilidadesVista.Mensaje("Al no tener E.C. el alumno seleccionado, se le ha asociado la E.C. de la Matrícula.", FacesMessage.SEVERITY_WARN);
        }
    }
}
