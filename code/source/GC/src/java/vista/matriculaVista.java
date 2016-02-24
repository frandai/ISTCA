package vista;

import bean.EventoFacade;
import bean.FacturaFacade;
import bean.GrupoFacade;
import bean.MatriculaFacade;
import bean.ProveedorFacade;
import datos.AccionFormativa;
import datos.EmpresaAgrupacion;
import datos.Evento;
import datos.Matricula;
import datos.Factura;
import datos.FacturaEstado;
import datos.Grupo;
import datos.GrupoPK;
import datos.Persona;
import datosVista.Matriculavista;
import datosVistaFacade.MatriculavistaFacade;
import gestion.MatriculaGestion;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import lazyDataModel.LazyMatriculaDataModel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.data.SortEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;
import util.BooleanGenerarDoc;
import util.Documentacion;
import util.Email;
import util.Excel;
import util.Fecha;
import util.MatriculaModelo;
import util.Validacion;
import util.VariablesSistema;
import util.descripcionEventos;

/**
 *
 *
 */
@ManagedBean(name = "matriculaVista")
@SessionScoped
public class matriculaVista implements Serializable {

    /**
     * Creates a new instance of EmpresaVista
     */
    @EJB
    private MatriculavistaFacade matriculavFacade;
    @EJB
    private MatriculaFacade matriculaFacade;
    @EJB
    private EventoFacade evfac;
    @EJB
    private FacturaFacade f_fac;
    private LazyDataModel<Matriculavista> matriculas;
    private List<Matriculavista> matriculasFiltradas;
    private boolean muestraEmpresa = true;
    private boolean muestraAlumno = true;
    private boolean muestraGrupo = true;
    private boolean muestraPrecio = true;
    private boolean muestraFIG = true;
    private boolean muestraFFG = true;
    private boolean muestraCobro_Pendiente = false;
    private boolean muestraConfirmada = false;
    private boolean muestraA_finializar = false;
    private boolean muestraFinalizada = false;
    private boolean muestraEv_Rec = false;
    private boolean muestraIFC_Env = false;
    private boolean muestraID = false;
    private boolean muestraFP = false;
    private boolean muestraBaja = true;
    private boolean muestraEnv_Dip = false;
    private boolean muestraPago_Pos = false;
    private boolean muestraCCC = false;
    private boolean muestraFacturas = false;
    private boolean muestraModalidad = false;
    private boolean muestraEC = false;
    private boolean muestraProveedor = false;
    private boolean muestraEmail = false;
    private boolean muestraPagoAplazado = false;
    private boolean muestraCreadoXMLInicio = false;
    private boolean muestraFechaMatricula = false;
    private boolean muestraNumFila = true;
    private boolean muestraDiplAdicc = false;
    private boolean muestraAesoria = false;
    private boolean muestraObservaciones = false;
    private boolean muestraEmailAlumno = false;
    private boolean muestraTelefonoEmpresa = false;
    private boolean muestraTelefonoAlumno = false;
    private boolean muestraComInicioCurso = false;
    private boolean muestraComSegCurso = false;
    private boolean muestraComFinCurso = false;
    private Integer filasSelec;

    public boolean isMuestraEmailAlumno() {
        return muestraEmailAlumno;
    }

    public void setMuestraEmailAlumno(boolean muestraEmailAlumno) {
        this.muestraEmailAlumno = muestraEmailAlumno;
    }

    public boolean isMuestraTelefonoEmpresa() {
        return muestraTelefonoEmpresa;
    }

    public void setMuestraTelefonoEmpresa(boolean muestraTelefonoEmpresa) {
        this.muestraTelefonoEmpresa = muestraTelefonoEmpresa;
    }

    public boolean isMuestraTelefonoAlumno() {
        return muestraTelefonoAlumno;
    }

    public void setMuestraTelefonoAlumno(boolean muestraTelefonoAlumno) {
        this.muestraTelefonoAlumno = muestraTelefonoAlumno;
    }

    public boolean isMuestraComInicioCurso() {
        return muestraComInicioCurso;
    }

    public void setMuestraComInicioCurso(boolean muestraComInicioCurso) {
        this.muestraComInicioCurso = muestraComInicioCurso;
    }

    public boolean isMuestraComSegCurso() {
        return muestraComSegCurso;
    }

    public void setMuestraComSegCurso(boolean muestraComSegCurso) {
        this.muestraComSegCurso = muestraComSegCurso;
    }

    public boolean isMuestraComFinCurso() {
        return muestraComFinCurso;
    }

    public void setMuestraComFinCurso(boolean muestraComFinCurso) {
        this.muestraComFinCurso = muestraComFinCurso;
    }

    public void onSort(SortEvent event) {
        String sort = "";
        if (event.getSortColumn().getId() != null) {
            if (event.getSortColumn().getId().equals("razonSocial")) {
                sort = "razonSocial";
            }
            if (event.getSortColumn().getId().equals("matriculaID")) {
                sort = "id";

            }
            ((LazyMatriculaDataModel) matriculas).setSortField(sort);
            ((LazyMatriculaDataModel) matriculas).setSortOrder(event.isAscending() ? SortOrder.ASCENDING : SortOrder.DESCENDING);
        }
    }

    public Integer getFilasSelec() {
        if (filasSelec == null) {
            filasSelec = 5;
        }
        return filasSelec;
    }

    public void setFilasSelec(Integer filasSelec) {
        this.filasSelec = filasSelec;
    }
    private String enlaceDescarga = "";
    private Matricula matriculaSeleccionada;
    @ManagedProperty("#{sesionActual}")
    private SesionActual sesionActual;
    private BooleanGenerarDoc gen = new BooleanGenerarDoc();
    private Date ffin_desde;
    private Date ffin_hasta;
    private boolean inicio_fin = false;
    private Map<String, String> filterMap;
    private boolean muestraDesc;
    private UploadedFile excelImpBonif;
    @EJB
    private GrupoFacade gFacade;
    @EJB
    private FacturaFacade facturaFacade;
    @EJB
    private ProveedorFacade proveedorFacade;
    private boolean todasLasPaginas = false;
    private String anioSelect = "/" + Fecha.getAnio(new Date());
    private List<Matricula> matFactura;

    public List<Matricula> getMatFactura() {
        return matFactura;
    }

    public void setMatFactura(List<Matricula> matFactura) {
        this.matFactura = matFactura;
    }

    public String getAnioSelect() {
        return anioSelect;
    }

    public void setAnioSelect(String anioSelect) {
        this.anioSelect = anioSelect;
    }

    public boolean isMuestraFechaMatricula() {
        return muestraFechaMatricula;
    }

    public void setMuestraFechaMatricula(boolean muestraFechaMatricula) {
        this.muestraFechaMatricula = muestraFechaMatricula;
    }

    public boolean isMuestraDiplAdicc() {
        return muestraDiplAdicc;
    }

    public void setMuestraDiplAdicc(boolean muestraDiplAdicc) {
        this.muestraDiplAdicc = muestraDiplAdicc;
    }

    public void seleccionaTodosRegistros() {
        if (matriculavFacade.count(filterMap) >= 10000) {
            UtilidadesVista.Mensaje("No se pueden seleccionar todas las matrículas de un filtro de más de 10000 registros. Por favor, haga un filtro con menos registros.", FacesMessage.SEVERITY_ERROR);
        } else {
            List<Matriculavista> mv = matriculas.load(0, 10000, "", SortOrder.UNSORTED, filterMap);
            List<Matricula> m = new ArrayList<Matricula>();
            for (Matriculavista matriculavista : mv) {
                matriculavista.getMatricula().setSeleccionarPDF(true);
                m.add(matriculavista.getMatricula());

            }
            ((LazyMatriculaDataModel) matriculas).setMatriculasPDF(m);
        }
    }

    public void cambiaValorPag() {
        todasLasPaginas = !todasLasPaginas;
    }

    public boolean isTodasLasPaginas() {
        return todasLasPaginas;
    }

    public void setTodasLasPaginas(boolean todasLasPaginas) {
        this.todasLasPaginas = todasLasPaginas;
    }

    public UploadedFile getExcelImpBonif() {
        return excelImpBonif;
    }

    public void setExcelImpBonif(UploadedFile excelImpBonif) {
        this.excelImpBonif = excelImpBonif;
    }

    public void subirExcelImpBonif() throws IOException {
        if (excelImpBonif != null) {
            try {

                if (excelImpBonif.getFileName().endsWith(".xls")) {

                    ArrayList<ArrayList<String>> excel = Excel.getDatosExcel(excelImpBonif.getInputstream());
                    if (excel.get(0).get(0).equals("Grupo")
                            && excel.get(0).get(1).equals("Empresa")
                            && excel.get(0).get(2).equals("Imp. Bonif.")) {
                        for (ArrayList<String> fila : excel.subList(1, excel.size())) {
                            String grupo = fila.get(0);
                            String empresa = fila.get(1);
                            String importe = fila.get(2);
                            Grupo g = gFacade.find(new GrupoPK(Integer.parseInt(grupo.split("-")[0]), Integer.parseInt(grupo.split("-")[1])));
                            ArrayList<Matricula> mats = new ArrayList<Matricula>();
                            for (Matricula matricula : g.getMatriculaList()) {
                                if (matricula.getEmpresaMatriculaCcc().getEmpresaMatriculaCccPK().getNif().equals(empresa)) {
                                    mats.add(matricula);
                                }
                            }
                            if (!mats.isEmpty() && !importe.equals("")) {
                                double importe_bonif_matricula = (Double.parseDouble(importe.replaceAll(",", ".")) / mats.size());
                                for (Matricula matricula : mats) {
                                    matricula.setImporteBonificar(importe_bonif_matricula);
                                    matriculaFacade.edit(matricula);
                                }
                            }
                        }
                        UtilidadesVista.Mensaje("Se han importado los datos correctamente.", FacesMessage.SEVERITY_INFO);
                        return;
                    }
                }

            } catch (Exception e) {
            }
        }
        UtilidadesVista.Mensaje("Debe de seleccionar un archivo en formato 'xls' válido para este proceso.", FacesMessage.SEVERITY_ERROR);
    }

    public boolean isMuestraNumFila() {
        return muestraNumFila;
    }

    public void setMuestraNumFila(boolean muestraNumFila) {
        this.muestraNumFila = muestraNumFila;
    }

    public boolean isMuestraCreadoXMLInicio() {
        return muestraCreadoXMLInicio;
    }

    public void setMuestraCreadoXMLInicio(boolean muestraCreadoXMLInicio) {
        this.muestraCreadoXMLInicio = muestraCreadoXMLInicio;
    }

    public boolean isMuestraPagoAplazado() {
        return muestraPagoAplazado;
    }

    public void setMuestraPagoAplazado(boolean muestraPagoAplazado) {
        this.muestraPagoAplazado = muestraPagoAplazado;
    }

    public boolean isMuestraProveedor() {
        return muestraProveedor;
    }

    public void setMuestraProveedor(boolean muestraProveedor) {
        this.muestraProveedor = muestraProveedor;
    }

    public boolean isMuestraDesc() {
        boolean muestra = muestraDesc;
        muestraDesc = false;
        return muestra;
    }

    public void setMuestraDesc(boolean muestraDesc) {
        this.muestraDesc = muestraDesc;
    }

    public boolean isMuestraEmail() {
        return muestraEmail;
    }

    public void setMuestraEmail(boolean muestraEmail) {
        this.muestraEmail = muestraEmail;
    }

    public void borraSelec() {
        if (((LazyMatriculaDataModel) matriculas).getMatriculasFacturas() != null && !((LazyMatriculaDataModel) matriculas).getMatriculasFacturas().isEmpty()) {
            for (Factura matricula : ((LazyMatriculaDataModel) matriculas).getMatriculasFacturas()) {
                matricula.setSeleccionado(false);
            }
        }

        ((LazyMatriculaDataModel) matriculas).setMatriculasFacturas(new ArrayList<Factura>());
        if (((LazyMatriculaDataModel) matriculas).getMatriculasPDF() != null && !((LazyMatriculaDataModel) matriculas).getMatriculasPDF().isEmpty()) {
            for (Matricula matricula : ((LazyMatriculaDataModel) matriculas).getMatriculasPDF()) {
                matricula.setSeleccionarPDF(false);
            }
        }
        if (((LazyMatriculaDataModel) matriculas).getMatriculasEmail() != null && !((LazyMatriculaDataModel) matriculas).getMatriculasEmail().isEmpty()) {
            for (Matricula matricula : ((LazyMatriculaDataModel) matriculas).getMatriculasEmail()) {
                matricula.setSeleccionarEmail(false);
            }
        }
        if (((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase() != null && !((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase().isEmpty()) {
            for (Matricula matricula : ((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase()) {
                matricula.setSeleccionarEmail_ase(false);
            }
        }

        ((LazyMatriculaDataModel) matriculas).setMatriculasPDF(new ArrayList<Matricula>());
        ((LazyMatriculaDataModel) matriculas).setMatriculasEmail(new ArrayList<Matricula>());
        ((LazyMatriculaDataModel) matriculas).setMatriculasEmail_ase(new ArrayList<Matricula>());
        ((LazyMatriculaDataModel) matriculas).setMats(null);
    }

    public Map<String, String> getFilterMap() {
        return filterMap;
    }

    public void setFilterMap(Map<String, String> filterMap) {
        if (filterMap != null && filterMap.get("fechaMatricula") != null && !filterMap.get("fechaMatricula").equals("") && !filterMap.get("fechaMatricula").equals(anioSelect)) {
            anioSelect = filterMap.get("fechaMatricula");
        }
        if (filterMap == null) {
            muestraEmpresa = true;
            muestraAlumno = true;
            muestraGrupo = true;
            muestraPrecio = true;
            muestraFIG = true;
            muestraFFG = true;
            muestraAesoria = false;
            muestraObservaciones = false;
            muestraCobro_Pendiente = false;
            muestraConfirmada = false;
            muestraA_finializar = false;
            muestraFinalizada = false;
            muestraEv_Rec = false;
            muestraIFC_Env = false;
            muestraID = false;
            muestraFP = false;
            muestraBaja = true;
            muestraEnv_Dip = false;
            muestraPago_Pos = false;
            muestraCCC = false;
            muestraFacturas = false;
            muestraModalidad = false;
            muestraEC = false;
            ffin_desde = ffin_hasta = null;
            muestraPagoAplazado = false;
            muestraCreadoXMLInicio = false;
            muestraFechaMatricula = false;
            muestraDiplAdicc = false;
            muestraEmailAlumno = false;
            muestraTelefonoEmpresa = false;
            muestraTelefonoAlumno = false;
            muestraComInicioCurso = false;
            muestraComSegCurso = false;
            muestraComFinCurso = false;
            this.muestraEmail = false;
            anioSelect = "/" + Fecha.getAnio(new Date());
            matriculas = new LazyMatriculaDataModel(matriculavFacade, getSesionActual().tienePermiso(22) ? getSesionActual().getUsuario_conectado().getNif() : "");

        }
        this.filterMap = filterMap;
    }

    public Date getFfin_desde() {
        return ffin_desde;
    }

    public void setFfin_desde(Date ffin_desde) {
        this.ffin_desde = ffin_desde;
    }

    public Date getFfin_hasta() {
        return ffin_hasta;
    }

    public void setFfin_hasta(Date ffin_hasta) {
        this.ffin_hasta = ffin_hasta;
    }

    public BooleanGenerarDoc getGen() {
        return gen;
    }

    public void setGen(BooleanGenerarDoc gen) {
        this.gen = gen;
    }

    public SesionActual getSesionActual() {
        return sesionActual;
    }

    public void setSesionActual(SesionActual sesionActual) {
        this.sesionActual = sesionActual;
    }

    public void recargarMatriculasModeloEnvioDiplomas() {
        this.muestraEmpresa = true;
        this.muestraAlumno = true;
        this.muestraGrupo = false;
        this.muestraPrecio = false;
        this.muestraFIG = false;
        this.muestraFFG = false;
        this.muestraCobro_Pendiente = true;
        this.muestraConfirmada = false;
        this.muestraA_finializar = false;
        this.muestraFinalizada = false;
        this.muestraEv_Rec = true;
        this.muestraIFC_Env = false;
        this.muestraID = false;
        this.muestraFP = false;
        this.muestraBaja = true;
        this.muestraEnv_Dip = true;
        this.muestraPago_Pos = false;
        this.muestraCCC = false;
        this.muestraFacturas = false;
        this.muestraModalidad = false;
        this.muestraEC = false;
        muestraPagoAplazado = false;
        muestraCreadoXMLInicio = false;
        muestraFechaMatricula = false;
        muestraDiplAdicc = false;
        muestraEmailAlumno = false;
        muestraTelefonoEmpresa = false;
        muestraTelefonoAlumno = false;
        muestraComInicioCurso = false;
        muestraComSegCurso = false;
        muestraComFinCurso = false;
        this.muestraEmail = false;

        matriculasFiltradas = null;
    }

    public void recargarMatriculasModeloComPetEval() {
        this.muestraEmpresa = true;
        this.muestraFFG = true;
        this.muestraBaja = true;
        this.muestraFinalizada = true;
        this.muestraEv_Rec = true;
        this.muestraEmail = true;
        this.muestraAlumno = false;
        this.muestraGrupo = false;
        muestraEmailAlumno = false;
        muestraTelefonoEmpresa = false;
        muestraTelefonoAlumno = false;
        muestraComInicioCurso = false;
        muestraComSegCurso = false;
        muestraComFinCurso = false;
        this.muestraPrecio = false;
        this.muestraFIG = false;
        this.muestraCobro_Pendiente = false;
        this.muestraConfirmada = false;
        this.muestraA_finializar = false;
        this.muestraIFC_Env = false;
        this.muestraID = false;
        this.muestraFP = false;
        this.muestraEnv_Dip = false;
        this.muestraPago_Pos = false;
        this.muestraCCC = false;
        this.muestraFacturas = false;
        this.muestraModalidad = false;
        this.muestraEC = false;
        muestraPagoAplazado = false;
        muestraCreadoXMLInicio = false;
        muestraFechaMatricula = false;
        muestraDiplAdicc = false;

        matriculasFiltradas = null;
    }

    public void recargarMatriculasModeloComunicacionesAlumno() {
        this.muestraEmpresa = true;
        this.muestraAlumno = true;
        this.muestraGrupo = true;
        muestraEmailAlumno = true;
        muestraTelefonoEmpresa = true;
        muestraTelefonoAlumno = true;
        muestraComInicioCurso = true;
        muestraComSegCurso = true;
        muestraComFinCurso = true;
        this.muestraPrecio = false;
        this.muestraFIG = false;
        this.muestraFFG = false;
        this.muestraCobro_Pendiente = false;
        this.muestraConfirmada = false;
        this.muestraA_finializar = false;
        this.muestraFinalizada = false;
        this.muestraEv_Rec = false;
        this.muestraIFC_Env = false;
        this.muestraID = false;
        this.muestraFP = false;
        this.muestraBaja = true;
        this.muestraEnv_Dip = false;
        this.muestraPago_Pos = false;
        this.muestraCCC = false;
        this.muestraFacturas = false;
        this.muestraModalidad = false;
        this.muestraEC = false;
        muestraPagoAplazado = false;
        muestraCreadoXMLInicio = false;
        muestraFechaMatricula = false;
        muestraDiplAdicc = false;
        this.muestraEmail = false;

        matriculasFiltradas = null;
    }

    public void recargarMatriculasModeloMatriculasACobrar() {
        this.muestraEmpresa = true;
        this.muestraAlumno = false;
        this.muestraGrupo = false;
        this.muestraPrecio = true;
        this.muestraFIG = true;
        this.muestraFFG = false;
        this.muestraCobro_Pendiente = true;
        this.muestraConfirmada = false;
        this.muestraA_finializar = false;
        this.muestraFinalizada = false;
        this.muestraEv_Rec = false;
        this.muestraIFC_Env = false;
        this.muestraID = false;
        this.muestraFP = false;
        this.muestraBaja = true;
        this.muestraEnv_Dip = false;
        this.muestraPago_Pos = true;
        this.muestraCCC = true;
        this.muestraFacturas = true;
        this.muestraModalidad = false;
        this.muestraEC = false;
        this.muestraPagoAplazado = true;
        muestraCreadoXMLInicio = false;
        muestraFechaMatricula = false;
        muestraDiplAdicc = false;
        muestraEmailAlumno = false;
        muestraTelefonoEmpresa = false;
        muestraTelefonoAlumno = false;
        muestraComInicioCurso = false;
        muestraComSegCurso = false;
        muestraComFinCurso = false;
        this.muestraEmail = false;

        matriculasFiltradas = null;
    }

    public void recargarMatriculasModeloInicioGrupos() {
        this.muestraEmpresa = true;
        this.muestraAlumno = true;
        this.muestraGrupo = true;
        this.muestraPrecio = false;
        this.muestraFIG = true;
        this.muestraFFG = false;
        this.muestraCobro_Pendiente = false;
        this.muestraConfirmada = false;
        this.muestraA_finializar = false;
        this.muestraFinalizada = false;
        this.muestraEv_Rec = false;
        this.muestraIFC_Env = false;
        this.muestraID = false;
        this.muestraFP = false;
        this.muestraBaja = true;
        this.muestraEnv_Dip = false;
        this.muestraPago_Pos = false;
        this.muestraCCC = false;
        this.muestraFacturas = false;
        this.muestraModalidad = false;
        this.muestraEC = false;
        muestraPagoAplazado = false;
        muestraCreadoXMLInicio = true;
        muestraDiplAdicc = false;
        muestraEmailAlumno = false;
        muestraTelefonoEmpresa = false;
        muestraTelefonoAlumno = false;
        muestraComInicioCurso = false;
        muestraComSegCurso = false;
        muestraComFinCurso = false;
        this.muestraEmail = false;

        inicio_fin = true;

        anioSelect = "";

        matriculas = new LazyMatriculaDataModel(matriculavFacade, ffin_desde, ffin_hasta, inicio_fin, getSesionActual().tienePermiso(22) ? getSesionActual().getUsuario_conectado().getNif() : "");
        matriculasFiltradas = null;
    }

    public void recargarMatriculasModeloFinGrupos() {
        this.muestraEmpresa = true;
        this.muestraAlumno = true;
        this.muestraGrupo = true;
        this.muestraPrecio = true;
        this.muestraFIG = false;
        this.muestraFFG = true;
        this.muestraCobro_Pendiente = true;
        this.muestraConfirmada = false;
        this.muestraA_finializar = true;
        this.muestraFinalizada = true;
        this.muestraEv_Rec = true;
        this.muestraIFC_Env = true;
        this.muestraID = false;
        this.muestraFP = false;
        this.muestraBaja = true;
        this.muestraEnv_Dip = false;
        this.muestraPago_Pos = true;
        this.muestraCCC = false;
        this.muestraFacturas = false;
        this.muestraModalidad = false;
        this.muestraEC = false;
        muestraPagoAplazado = false;
        muestraCreadoXMLInicio = false;
        muestraFechaMatricula = false;
        muestraDiplAdicc = false;
        muestraEmailAlumno = false;
        muestraTelefonoEmpresa = false;
        muestraTelefonoAlumno = false;
        muestraComInicioCurso = false;
        muestraComSegCurso = false;
        muestraComFinCurso = false;
        this.muestraEmail = false;

        inicio_fin = false;
        anioSelect = "";

        matriculas = new LazyMatriculaDataModel(matriculavFacade, ffin_desde, ffin_hasta, inicio_fin, getSesionActual().tienePermiso(22) ? getSesionActual().getUsuario_conectado().getNif() : "");
        matriculasFiltradas = null;
    }

    public Matricula getMatriculaSeleccionada() {
        return matriculaSeleccionada;
    }

    public void setMatriculaSeleccionada(Matricula matriculaSeleccionada) {
        if (((LazyMatriculaDataModel) matriculas).getMats() == null) {
            ((LazyMatriculaDataModel) matriculas).setMats(new ArrayList<Matricula>());
        }
        ((LazyMatriculaDataModel) matriculas).getMats().add(matriculaSeleccionada);
        this.matriculaSeleccionada = matriculaSeleccionada;
    }

    public String getEnlaceDescarga() {
        return enlaceDescarga;
    }

    public void setEnlaceDescarga(String enlaceDescarga) {
        this.enlaceDescarga = enlaceDescarga;
    }

    public void setMatEmail(Matricula matricula) {
        if (((LazyMatriculaDataModel) matriculas).getMatriculasEmail() == null) {
            ((LazyMatriculaDataModel) matriculas).setMatriculasEmail(new ArrayList<Matricula>());
        }
        ((LazyMatriculaDataModel) matriculas).getMatriculasEmail().remove(matricula);
        if (matricula.isSeleccionarEmail()) {
            ((LazyMatriculaDataModel) matriculas).getMatriculasEmail().add(matricula);
        }
    }

    public void setMatEmail_ase(Matricula matricula) {
        if (((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase() == null) {
            ((LazyMatriculaDataModel) matriculas).setMatriculasEmail_ase(new ArrayList<Matricula>());
        }
        ((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase().remove(matricula);
        if (matricula.isSeleccionarEmail_ase()) {
            ((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase().add(matricula);
        }
    }

    public void setMatPDF(Matricula matricula) {
        if (((LazyMatriculaDataModel) matriculas).getMatriculasPDF() == null) {
            ((LazyMatriculaDataModel) matriculas).setMatriculasPDF(new ArrayList<Matricula>());
        }
        ((LazyMatriculaDataModel) matriculas).getMatriculasPDF().remove(matricula);
        if (matricula.isSeleccionarPDF()) {
            ((LazyMatriculaDataModel) matriculas).getMatriculasPDF().add(matricula);
        }
    }

    public void setMatriculasFac(Factura matricula) {
        if (((LazyMatriculaDataModel) matriculas).getMatriculasFacturas() == null) {
            ((LazyMatriculaDataModel) matriculas).setMatriculasFacturas(new ArrayList<Factura>());
        }
        ((LazyMatriculaDataModel) matriculas).getMatriculasFacturas().remove(matricula);
        if (matricula.isSeleccionado()) {
            ((LazyMatriculaDataModel) matriculas).getMatriculasFacturas().add(matricula);
        }
    }

    public boolean isMuestraAesoria() {
        return muestraAesoria;
    }

    public void setMuestraAesoria(boolean muestraAesoria) {
        this.muestraAesoria = muestraAesoria;
    }

    public boolean isMuestraObservaciones() {
        return muestraObservaciones;
    }

    public void setMuestraObservaciones(boolean muestraObservaciones) {
        this.muestraObservaciones = muestraObservaciones;
    }

    public List<Factura> getMatriculasFacturas() {
        return ((LazyMatriculaDataModel) matriculas).getMatriculasFacturas();
    }

    public void setMatriculasFacturas(List<Factura> matriculasFacturas) {
        ((LazyMatriculaDataModel) matriculas).setMatriculasFacturas(matriculasFacturas);
    }

    public List<Matricula> getMatriculasPDF() {
        return ((LazyMatriculaDataModel) matriculas).getMatriculasPDF();
    }

    public void setMatriculasPDF(List<Matricula> matriculasPDF) {
        ((LazyMatriculaDataModel) matriculas).setMatriculasPDF(matriculasPDF);
    }

    public void generarXMLAccionesFormativas() throws FileNotFoundException, IOException {
        boolean valido = true;
        if ((((LazyMatriculaDataModel) matriculas).getMatriculasPDF() == null || ((LazyMatriculaDataModel) matriculas).getMatriculasPDF().isEmpty())) {
            UtilidadesVista.Mensaje("No se pueden generar los XML de Acciones Formativas puesto que no se han seleccionado matrículas a generar.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        List<AccionFormativa> af = new ArrayList<AccionFormativa>();
        EmpresaAgrupacion ag = null;
        for (Matricula m : ((LazyMatriculaDataModel) matriculas).getMatriculasPDF()) {
            if (ag != null && !ag.getId().equals(m.getEmpresaMatriculaCcc().getEmpresaMatricula().getAgrupacion().getId())) {
                UtilidadesVista.Mensaje("La empresa de la matrícula " + m.getId() + " no pertenece a la agrupación: " + ag, FacesMessage.SEVERITY_ERROR);
                valido = false;
            }
            if (ag == null) {
                ag = m.getEmpresaMatriculaCcc().getEmpresaMatricula().getAgrupacion();
            }
            if (!af.contains(m.getGrupo().getAccionFormativa1())) {
                af.add(m.getGrupo().getAccionFormativa1());
            }
        }


        if (!valido) {
            enlaceDescarga = "";
            return;
        }
        String ret;
        ret = util.Xml.generarXMLAccionesFormativas(af, ag);

        UtilidadesVista.Mensaje("Se ha generado el XML de las Acciones Formativas correspondientes. Pulse 'Botón derecho -> Guardar Como' para descargarlo.", FacesMessage.SEVERITY_INFO);
        enlaceDescarga = ret;
        setMuestraDesc(true);
        ((LazyMatriculaDataModel) matriculas).setMatriculasPDF(new ArrayList<Matricula>());
    }

    private void generarXML(boolean inicio_fin) throws Exception {
        boolean valido = true;
        if ((((LazyMatriculaDataModel) matriculas).getMatriculasPDF() == null || ((LazyMatriculaDataModel) matriculas).getMatriculasPDF().isEmpty())) {
            UtilidadesVista.Mensaje("No se pueden generar los XML de " + (inicio_fin ? "Inicio" : "Fin") + " de Grupo puesto que no se han seleccionado matrículas a generar.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        List<Grupo> grupos = null;
        try {
            grupos = generaGruposXML(((LazyMatriculaDataModel) matriculas).getMatriculasPDF());
        } catch (Exception e) {
            for (String er : e.getMessage().split("&&--&&")) {
                UtilidadesVista.Mensaje(er, FacesMessage.SEVERITY_ERROR);
            }

            valido = false;
        }

        if (!valido) {
            enlaceDescarga = "";
            return;
        }
        String ret;
        if (inicio_fin) {
            ret = util.Xml.generarXMLInicioGrupo(grupos);
        } else {
            ret = util.Xml.generarXMLFinalizacionGrupo(grupos);
        }
        for (Matricula matricula : ((LazyMatriculaDataModel) matriculas).getMatriculasPDF()) {
            Evento generadoXML = new Evento();
            generadoXML.setCreador(getSesionActual().getUsuario_conectado().getPersona());
            generadoXML.setMatricula(matricula);
            descripcionEventos.configurarEvento(generadoXML, inicio_fin ? descripcionEventos.GENERADO_XML_INICIO : descripcionEventos.GENERADO_XML_FIN);
            matricula.getEventoList().add(generadoXML);

            matricula = matriculaFacade.edit(matricula);
        }
        UtilidadesVista.Mensaje("Se ha generado el ZIP de los XML de " + (inicio_fin ? "Inicio" : "Fin") + " de Grupo correspondiente. Pulse 'Descargar' para descargarlo.", FacesMessage.SEVERITY_INFO);
        enlaceDescarga = ret;
        setMuestraDesc(true);
        ((LazyMatriculaDataModel) matriculas).setMatriculasPDF(new ArrayList<Matricula>());
    }

    public void generarXMLFinGrupo() throws Exception {
        generarXML(false);
    }

    public void generarXMLInicioGrupo() throws Exception {
        generarXML(true);
    }

    public void generarFacturas() {
        boolean valido = true;
        if ((((LazyMatriculaDataModel) matriculas).getMatriculasPDF() == null || ((LazyMatriculaDataModel) matriculas).getMatriculasPDF().isEmpty())) {
            UtilidadesVista.Mensaje("No se pueden generar las facturas puesto que no se han seleccionado matrículas.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        } else {
            for (Matricula msel : ((LazyMatriculaDataModel) matriculas).getMatriculasPDF()) {
                if (msel.getGrupo().getPendienteDeGestionar()) {
                    UtilidadesVista.Mensaje("No se puede generar la factura para la Matrícula " + msel.getId() + " puesto que ésta asociada a un Grupo Pendiente de Gestionar.", FacesMessage.SEVERITY_ERROR);
                    valido = false;
                }
                if (msel.getImporteSinFacturar().equals(0.0)) {
                    UtilidadesVista.Mensaje("No se puede generar la factura para la Matrícula " + msel.getId() + " puesto que ésta matrícula tiene todo su precio facturado.", FacesMessage.SEVERITY_ERROR);
                    valido = false;
                } else if (!msel.getImporteSinFacturar().equals(msel.getPrecio())) {
                    UtilidadesVista.Mensaje("Puede generarse la factura para la matrícula " + msel.getId() + " con el precio que falta por facturar.", FacesMessage.SEVERITY_WARN);

                }
            }
        }
        if (!valido) {
            return;
        }

        MatriculaGestion mges = new MatriculaGestion();
        mges.setModoAlta(false);
        mges.setFacturaFacade(facturaFacade);
        mges.setProveedorFacade(proveedorFacade);

        for (Matricula msel : ((LazyMatriculaDataModel) matriculas).getMatriculasPDF()) {
            mges.setMatriculaSeleccionada(msel);
            mges.aniadirFactura(false, false);
            matriculaFacade.edit(msel);
        }

        UtilidadesVista.Mensaje("Se han generado todas las facturas asociadas a las matrículas seleccionadas.", FacesMessage.SEVERITY_INFO);
        ((LazyMatriculaDataModel) matriculas).setMatriculasPDF(new ArrayList<Matricula>());
    }

    public void generarDocumentacionExcelMensajeria() throws Exception {
        boolean valido = true;
        if ((((LazyMatriculaDataModel) matriculas).getMatriculasPDF() == null || ((LazyMatriculaDataModel) matriculas).getMatriculasPDF().isEmpty())) {
            UtilidadesVista.Mensaje("No se puede generar Excel de Mensajería puesto que no se han seleccionado matrículas a generar.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        if (!valido) {
            enlaceDescarga = "";
            return;
        }
        String ret = Documentacion.generar_excel_mensajeria(((LazyMatriculaDataModel) matriculas).getMatriculasPDF());
        UtilidadesVista.Mensaje("Se ha generado la hoja Excel correspondiente. Pulse 'Descargar' para descargarlo.", FacesMessage.SEVERITY_INFO);
        enlaceDescarga = ret;
        setMuestraDesc(true);
        ((LazyMatriculaDataModel) matriculas).setMatriculasPDF(new ArrayList<Matricula>());
    }

    public boolean isInicio_fin() {
        return inicio_fin;
    }

    public void setInicio_fin(boolean inicio_fin) {
        this.inicio_fin = inicio_fin;
    }

    public void generarDocumentacionFac() throws Exception {
        generarDocumentacion(true);
    }

    public void generarDocumentacion() throws Exception {
        generarDocumentacion(false);
    }

    public void generarDocumentacion(boolean desdeFac) throws Exception {
        boolean valido = true;
        if ((((LazyMatriculaDataModel) matriculas).getMatriculasPDF() == null || ((LazyMatriculaDataModel) matriculas).getMatriculasPDF().isEmpty())
                && (((LazyMatriculaDataModel) matriculas).getMatriculasEmail() == null || ((LazyMatriculaDataModel) matriculas).getMatriculasEmail().isEmpty())
                && (((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase() == null || ((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase().isEmpty())) {
            UtilidadesVista.Mensaje("No se puede generar Documentación puesto que no se han seleccionado matrículas a generar.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        if (getGen().isTodosFalse()) {
            UtilidadesVista.Mensaje("No se puede generar Documentación puesto que no se ha seleccionado documentación a generar.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        if (((LazyMatriculaDataModel) matriculas).getMatriculasPDF() != null) {
            for (Matricula ma : ((LazyMatriculaDataModel) matriculas).getMatriculasPDF()) {
                if (ma.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getDireccion() == null) {
                    UtilidadesVista.Mensaje("La empresa " + ma.getEmpresaMatriculaCcc().getEmpresaMatriculaCccPK().getNif() + ", asociada a la matrícula " + ma.getId() + " no tiene rellena la dirección.", FacesMessage.SEVERITY_ERROR);
                    valido = false;
                }
            }
        }
        if (((LazyMatriculaDataModel) matriculas).getMatriculasEmail() != null) {
            for (Matricula ma : ((LazyMatriculaDataModel) matriculas).getMatriculasEmail()) {
                if (ma.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getDireccion() == null) {
                    UtilidadesVista.Mensaje("La empresa " + ma.getEmpresaMatriculaCcc().getEmpresaMatriculaCccPK().getNif() + ", asociada a la matrícula " + ma.getId() + " no tiene rellena la dirección.", FacesMessage.SEVERITY_ERROR);
                    valido = false;
                }
            }
        }
        if (((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase() != null) {
            for (Matricula ma : ((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase()) {
                if (ma.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getDireccion() == null) {
                    UtilidadesVista.Mensaje("La empresa " + ma.getEmpresaMatriculaCcc().getEmpresaMatriculaCccPK().getNif() + ", asociada a la matrícula " + ma.getId() + " no tiene rellena la dirección.", FacesMessage.SEVERITY_ERROR);
                    valido = false;
                }
            }
        }
        if (!valido) {
            enlaceDescarga = "";
            return;
        }

        if (!desdeFac && gen.isGenerar_factura()) {
            matFactura = new ArrayList<Matricula>();
            rellenaDobleFactura(((LazyMatriculaDataModel) matriculas).getMatriculasEmail());
            rellenaDobleFactura(((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase());
            rellenaDobleFactura(((LazyMatriculaDataModel) matriculas).getMatriculasPDF());
            if (!matFactura.isEmpty()) {
                RequestContext.getCurrentInstance().execute("dialogoMasFactura.show()");
                return;
            }
        } else {
            RequestContext.getCurrentInstance().execute("dialogoMasFactura.hide()");
        }

        ArrayList<Matricula> mats = new ArrayList<Matricula>();
        if ((((LazyMatriculaDataModel) matriculas).getMatriculasEmail() != null && !((LazyMatriculaDataModel) matriculas).getMatriculasEmail().isEmpty()) || (((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase() != null && !((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase().isEmpty())) {
            if ((((LazyMatriculaDataModel) matriculas).getMatriculasEmail() != null && !((LazyMatriculaDataModel) matriculas).getMatriculasEmail().isEmpty())) {
                Documentacion.enviarMailsDocumentacion(((LazyMatriculaDataModel) matriculas).getMatriculasEmail(), false,
                        gen);
            }
            if ((((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase() != null && !((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase().isEmpty())) {
                Documentacion.enviarMailsDocumentacion(((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase(), true,
                        gen);
            }
            UtilidadesVista.Mensaje("Se han enviado los e-mails a los destinatarios correspondientes.", FacesMessage.SEVERITY_INFO);
            if ((((LazyMatriculaDataModel) matriculas).getMatriculasEmail() != null && !((LazyMatriculaDataModel) matriculas).getMatriculasEmail().isEmpty())) {
                mats.addAll(((LazyMatriculaDataModel) matriculas).getMatriculasEmail());
            }
            if ((((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase() != null && !((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase().isEmpty())) {
                mats.addAll(((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase());
            }
        }
        if (((LazyMatriculaDataModel) matriculas).getMatriculasPDF() != null && !((LazyMatriculaDataModel) matriculas).getMatriculasPDF().isEmpty()) {
            String ret = Documentacion.enviarPDFDocumentacion(((LazyMatriculaDataModel) matriculas).getMatriculasPDF(),
                    gen);
            UtilidadesVista.Mensaje("Se ha generado el PDF correspondiente. Pulse 'Descargar' para descargar los archivos.", FacesMessage.SEVERITY_INFO);
            enlaceDescarga = ret;
            setMuestraDesc(true);
            mats.addAll(((LazyMatriculaDataModel) matriculas).getMatriculasPDF());
        } else {
            enlaceDescarga = "";
        }
        if (mats != null && !mats.isEmpty()) {
            HashSet hs = new HashSet();
            hs.addAll(mats);
            mats.clear();
            mats.addAll(hs);
        }

        //Se genera el evento de documentacion:

        for (Matricula matricula : mats) {
            boolean generar = true;
            boolean esElPrimerEventoEnvioDocumentacion = true;
            Evento ev = null;
            for (Evento e : matricula.getEventoList()) {
                if (e.getTipoDescripcionEvento() == descripcionEventos.ENVIO_DOCUMENTACION) {
                    esElPrimerEventoEnvioDocumentacion = false;

                    if (Fecha.getFechaDiaMesAnio(e.getFechaCreacion()).equals(Fecha.getFechaDiaMesAnio(new Date()))) {
                        ev = e;
                        generar = false;
                        break;
                    }
                }
            }
            String mas = "";
            if (((LazyMatriculaDataModel) matriculas).getMatriculasPDF() != null && ((LazyMatriculaDataModel) matriculas).getMatriculasPDF().contains(matricula)) {
                mas += " - Generado en PDF";
            }
            if (((LazyMatriculaDataModel) matriculas).getMatriculasEmail() != null && ((LazyMatriculaDataModel) matriculas).getMatriculasEmail().contains(matricula)) {
                mas += " - Enviado por e-mail a Cliente (" + matricula.getEmailEnvio() + ")";
            }
            if (((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase() != null && ((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase().contains(matricula)) {
                mas += " - Enviado por e-mail a Asesoría (" + matricula.getEmailEnvio_ase() + ")";
            }
            String descripcion = "A las " + Fecha.getFechaHoraMinuto(new Date()) + " Se ha generado la siguiente documentación " + mas + ":<ul>";
            if (esElPrimerEventoEnvioDocumentacion) {
                descripcion += "<li>Se debe de haber enviado el material, al ser la primera documentación enviada al cliente.</li>";
            }
            if (gen.isGenerar_ficha_registro_alumno()) {
                descripcion += "<li>Se le ha generado la Ficha Alumno.</li>";
            }
            if (gen.isGenerar_factura()) {
                descripcion += "<li>Se le ha(n) generado la(s) Factura(s).</li>";
            }
            if (gen.isGenerar_informe_fin_curso()) {
                descripcion += "<li>Se le ha generado el Informe Fin Curso.</li>";
            }
            if (gen.isGenerar_diploma()) {
                descripcion += "<li>Se le ha generado el Diploma.</li>";
            }
            if (gen.isGenerar_recibi_diploma()) {
                descripcion += "<li>Se le ha generado el documento 'Recibí Diploma'.</li>";
            }
            if (gen.isGenerar_carta_rep_legal()) {
                descripcion += "<li>Se le ha generado la Carta Rep. Legal.</li>";
            }
            if (gen.isGenerar_autorizacion()) {
                descripcion += "<li>Se le ha generado la 'Autorización curso no relacionado con actividad'.</li>";
            }
            if (gen.isGenerar_acreditacion()) {
                descripcion += "<li>Se le ha generado la 'Acreditación Jornada Laboral'.</li>";
            }
            if (gen.isGenerar_recibi_material()) {
                descripcion += "<li>Se le ha generado la documentación 'Recibí Material'.</li>";
            }
            if (gen.isGenerar_datos_curso_alumno()) {
                descripcion += "<li>Se le ha generado la Ficha Curso.</li>";
            }
            if (gen.isGenerar_cartel_FEFE()) {
                descripcion += "<li>Se le ha generado el cartel FEFE.</li>";
            }
            if (gen.isGenerar_control_asistencia()) {
                descripcion += "<li>Se le ha generado el control de asistencia.</li>";
            }
            if (gen.getGenerar_matricula() != 0) {
                descripcion += "<li>Se le ha generado la matrícula.</li>";
            }
            descripcion += "</ul>";
            if (generar) {
                Evento envioDocumentacion = new Evento();
                envioDocumentacion.setCreador(getSesionActual().getUsuario_conectado().getPersona());
                envioDocumentacion.setMatricula(matricula);
                descripcionEventos.configurarEvento(envioDocumentacion, descripcionEventos.ENVIO_DOCUMENTACION);
                envioDocumentacion.setObservaciones(descripcion);
                matricula.getEventoList().add(envioDocumentacion);

                matricula = matriculaFacade.edit(matricula);
            } else {
                if (ev != null) {
                    ev.setObservaciones(ev.getObservaciones() + "<br/>" + descripcion);
                    evfac.edit(ev);
                }
            }


        }
        if (ffin_desde != null || ffin_hasta != null) {
            matriculas = new LazyMatriculaDataModel(matriculavFacade, ffin_desde, ffin_hasta, inicio_fin, getSesionActual().tienePermiso(22) ? getSesionActual().getUsuario_conectado().getNif() : "");
        } else {
            matriculas = new LazyMatriculaDataModel(matriculavFacade, getSesionActual().tienePermiso(22) ? getSesionActual().getUsuario_conectado().getNif() : "");
        }
        borrarMats();
    }

    public matriculaVista() {
    }

    public LazyDataModel<Matriculavista> getMatriculas() throws ParseException {
        UtilidadesVista.setParametroSes("desde_buscador_matricula", "si");
        if (matriculas == null) {
            if (ffin_desde != null || ffin_hasta != null) {
                matriculas = new LazyMatriculaDataModel(matriculavFacade, ffin_desde, ffin_hasta, inicio_fin, getSesionActual().tienePermiso(22) ? getSesionActual().getUsuario_conectado().getNif() : "");
            } else {
                matriculas = new LazyMatriculaDataModel(matriculavFacade, getSesionActual().tienePermiso(22) ? getSesionActual().getUsuario_conectado().getNif() : "");
            }
        }
        return matriculas;
    }

    public List<Matriculavista> getMatriculasFiltradas() {
        return matriculasFiltradas;
    }

    public void setMatriculasFiltradas(List<Matriculavista> matriculasFiltradas) {
        this.matriculasFiltradas = matriculasFiltradas;
    }

    public boolean isMuestraEmpresa() {
        return muestraEmpresa;
    }

    public void setMuestraEmpresa(boolean muestraEmpresa) {
        this.muestraEmpresa = muestraEmpresa;
    }

    public boolean isMuestraAlumno() {
        return muestraAlumno;
    }

    public void setMuestraAlumno(boolean muestraAlumno) {
        this.muestraAlumno = muestraAlumno;
    }

    public boolean isMuestraGrupo() {
        return muestraGrupo;
    }

    public void setMuestraGrupo(boolean muestraGrupo) {
        this.muestraGrupo = muestraGrupo;
    }

    public boolean isMuestraPrecio() {
        return muestraPrecio;
    }

    public void setMuestraPrecio(boolean muestraPrecio) {
        this.muestraPrecio = muestraPrecio;
    }

    public boolean isMuestraFIG() {
        return muestraFIG;
    }

    public void setMuestraFIG(boolean muestraFIG) {
        this.muestraFIG = muestraFIG;
    }

    public boolean isMuestraFFG() {
        return muestraFFG;
    }

    public void setMuestraFFG(boolean muestraFFG) {
        this.muestraFFG = muestraFFG;
    }

    public boolean isMuestraCobro_Pendiente() {
        return muestraCobro_Pendiente;
    }

    public void setMuestraCobro_Pendiente(boolean muestraCobro_Pendiente) {
        this.muestraCobro_Pendiente = muestraCobro_Pendiente;
    }

    public boolean isMuestraConfirmada() {
        return muestraConfirmada;
    }

    public void setMuestraConfirmada(boolean muestraConfirmada) {
        this.muestraConfirmada = muestraConfirmada;
    }

    public boolean isMuestraA_finializar() {
        return muestraA_finializar;
    }

    public void setMuestraA_finializar(boolean muestraA_finializar) {
        this.muestraA_finializar = muestraA_finializar;
    }

    public boolean isMuestraFinalizada() {
        return muestraFinalizada;
    }

    public void setMuestraFinalizada(boolean muestraFinalizada) {
        this.muestraFinalizada = muestraFinalizada;
    }

    public boolean isMuestraEv_Rec() {
        return muestraEv_Rec;
    }

    public void setMuestraEv_Rec(boolean muestraEv_Rec) {
        this.muestraEv_Rec = muestraEv_Rec;
    }

    public boolean isMuestraIFC_Env() {
        return muestraIFC_Env;
    }

    public void setMuestraIFC_Env(boolean muestraIFC_Env) {
        this.muestraIFC_Env = muestraIFC_Env;
    }

    public boolean isMuestraID() {
        return muestraID;
    }

    public void setMuestraID(boolean muestraID) {
        this.muestraID = muestraID;
    }

    public boolean isMuestraFP() {
        return muestraFP;
    }

    public void setMuestraFP(boolean muestraFP) {
        this.muestraFP = muestraFP;
    }

    public boolean isMuestraBaja() {
        return muestraBaja;
    }

    public void setMuestraBaja(boolean muestraBaja) {
        this.muestraBaja = muestraBaja;
    }

    public boolean isMuestraEnv_Dip() {
        return muestraEnv_Dip;
    }

    public void setMuestraEnv_Dip(boolean muestraEnv_Dip) {
        this.muestraEnv_Dip = muestraEnv_Dip;
    }

    public boolean isMuestraPago_Pos() {
        return muestraPago_Pos;
    }

    public void setMuestraPago_Pos(boolean muestraPago_Pos) {
        this.muestraPago_Pos = muestraPago_Pos;
    }

    public boolean isMuestraCCC() {
        return muestraCCC;
    }

    public void setMuestraCCC(boolean muestraCCC) {
        this.muestraCCC = muestraCCC;
    }

    public boolean isMuestraFacturas() {
        return muestraFacturas;
    }

    public void setMuestraFacturas(boolean muestraFacturas) {
        this.muestraFacturas = muestraFacturas;
    }

    public boolean isMuestraModalidad() {
        return muestraModalidad;
    }

    public void setMuestraModalidad(boolean muestraModalidad) {
        this.muestraModalidad = muestraModalidad;
    }

    public boolean isMuestraEC() {
        return muestraEC;
    }

    public void setMuestraEC(boolean muestraEC) {
        this.muestraEC = muestraEC;
    }

    private List<Grupo> generaGruposXML(List<Matricula> matriculasPDF) throws Exception {
        List<Grupo> g = new ArrayList<Grupo>();
        for (Matricula m : matriculasPDF) {
            if (!g.contains(m.getGrupo())) {
                g.add(m.getGrupo());
            }
        }
        String error = "";
        for (Grupo grupo : g) {
            for (Matricula m : grupo.getMatriculaList()) {
                if (m.getTextoCostes().contains("[")) {
                    error += "Error en la matricula " + m.getId() + " de la empresa " + m.getEmpresaMatriculaCcc().getEmpresaMatricula().getNif() + ": " + m.getTextoCostes() + ".&&--&&";
                }
                if (!Validacion.esNifValido(m.getAlumno().getNif())) {
                    error += "El alumno " + m.getAlumno().getNif() + " del grupo " + grupo.getNombre() + " no tiene un NIF Válido.&&--&&";
                }
                if (m.getAlumno().getPersona().getNss() == null || m.getAlumno().getPersona().getNss().equals("") || !Validacion.validaNSS(m.getAlumno().getPersona().getNss(), false)) {
                    error += "El alumno " + m.getAlumno().getNif() + " del grupo " + grupo.getNombre() + " no tiene un NSS Válido" + (m.getAlumno().getPersona().getNss() == null ? "" : " (" + m.getAlumno().getPersona().getNss() + ")") + ".&&--&&";
                }
                if (m.getAlumno().getPersona().getFechaNacimiento() == null) {
                    error += "El alumno " + m.getAlumno().getNif() + " del grupo " + grupo.getNombre() + " no tiene una Fecha de Nacimiento Válida.&&--&&";
                }
                if (!Validacion.esNifValido(m.getEmpresaMatriculaCcc().getEmpresaMatricula().getNif())) {
                    error += "La empresa " + m.getEmpresaMatriculaCcc().getEmpresaMatricula().getNif() + " del grupo " + grupo.getNombre() + " no tiene un NIF Válido.&&--&&";
                }
                if (m.getEmpresaMatriculaCcc().getEmpresaMatricula().getCotSs() == null || m.getEmpresaMatriculaCcc().getEmpresaMatricula().getCotSs().equals("") || !Validacion.validaNSS(m.getEmpresaMatriculaCcc().getEmpresaMatricula().getCotSs(), true)) {
                    error += "La empresa " + m.getEmpresaMatriculaCcc().getEmpresaMatricula().getNif() + " del grupo " + grupo.getNombre() + " no tiene un NSS Válido" + (m.getEmpresaMatriculaCcc().getEmpresaMatricula().getCotSs() == null ? "" : " (" + m.getEmpresaMatriculaCcc().getEmpresaMatricula().getCotSs() + ")") + ".&&--&&";
                }
            }
            if (grupo.getGrupoPK().getId() == 0) {
                error += "El grupo " + grupo.getNombre() + " no debe de ser Pendiente de Gestionar.&&--&&";
            }
            if (grupo.getAccionFormativa1().getModalidad().getId().equals(new Short("7"))
                    && (grupo.getDireccionList() == null || grupo.getDireccionList().isEmpty())) {
                error += "El grupo " + grupo.getNombre() + " debe de tener rellena una dirección de impartición (al ser de modalidad presencial).&&--&&";
            }
            if (grupo.getProveedor1().getEmpresa().getEmpresaTelefonoList() == null || grupo.getProveedor1().getEmpresa().getEmpresaTelefonoList().isEmpty()) {
                error += "El Proveedor " + grupo.getProveedor1().getNif() + " del grupo " + grupo.getNombre() + " debe de tener registrado algún número de teléfono.&&--&&";
            }
            if (grupo.getProveedor1().getEmpresa().getDireccion() == null || grupo.getProveedor1().getEmpresa().getDireccion().getVia() == null) {
                error += "El Proveedor " + grupo.getProveedor1().getNif() + " del grupo " + grupo.getNombre() + " debe de tener registrada una dirección.&&--&&";
            }
            if (!matriculasPDF.containsAll(grupo.getMatriculaList())) {
                error += "Para poder general XML del grupo " + grupo.getNombre() + " debe de seleccionar todas sus matrículas asociadas.&&--&&";
            }
        }
        if (!error.equals("")) {
            throw new Exception(error);
        }
        return g;
    }

    public static void realizarEventoComunicacion(Evento ev, Email e, EventoFacade evfac) {
        ev.setObservaciones("<div>-El " + Fecha.getFechaDiaMesAnio(new Date()) + " a las " + Fecha.getFechaHoraMinuto(new Date()) + " se le envió un un E-mail a " + e.getPara()[0] + ", desde "+util.VariablesSistema.email_enviar+", con los datos:"
                + "<br/><br/>Asunto: " + e.getAsunto()
                + "<br/><br/>Cuerpo:<br/>" + e.getCuerpo()
                + "</div><hr/>" + (ev.getObservaciones() == null ? "" : ev.getObservaciones()) + "");
        if (ev.getFechaRealizacion() == null) {
            ev.setFechaRealizacion(new Date());
        }
        evfac.edit(ev);
    }

    public void enviarEmailComunicacion(int comunicacion) throws Exception {
        boolean valido = true;
        if (comunicacion != 4) {
            if ((((LazyMatriculaDataModel) matriculas).getMatriculasPDF() == null || ((LazyMatriculaDataModel) matriculas).getMatriculasPDF().isEmpty())) {
                UtilidadesVista.Mensaje("No se pueden generar las Comunicaciones de Cursos puesto que no se han seleccionado matrículas a generar.", FacesMessage.SEVERITY_ERROR);
                valido = false;
            } else {
                for (Matricula m : ((LazyMatriculaDataModel) matriculas).getMatriculasPDF()) {
                    if (m.getAlumno().getPersona().getEmail() == null || !m.getAlumno().getPersona().getEmail().contains("@")) {
                        UtilidadesVista.Mensaje("La matrícula " + m.getId() + " no puede generar la comunicación puesto que el Alumno " + m.getAlumno().getNif() + " no tiene un E-mail asociado.", FacesMessage.SEVERITY_ERROR);
                        valido = false;
                    }
                }
            }
        } else {
            if ((((LazyMatriculaDataModel) matriculas).getMatriculasEmail() == null || ((LazyMatriculaDataModel) matriculas).getMatriculasEmail().isEmpty())) {
                UtilidadesVista.Mensaje("No se pueden generar las Comunicaciones de Petición de Evaluación puesto que no se han seleccionado e-mails a enviar.", FacesMessage.SEVERITY_ERROR);
                valido = false;
            }
        }
        if (valido) {
            for (Matricula m : ((comunicacion == 4) ? ((LazyMatriculaDataModel) matriculas).getMatriculasEmail() : ((LazyMatriculaDataModel) matriculas).getMatriculasPDF())) {
                Email e = new Email(util.VariablesSistema.email_enviar);
                if (comunicacion == 4) {
                    e.setPara(new String[]{m.getEmailEnvio()});
                } else {
                    e.setPara(new String[]{m.getAlumno().getPersona().getEmail()});
                }
                switch (comunicacion) {
                    case 4:
                        Persona repleg = m.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRepresLegal();
                        e.setAsunto(VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial() + " - PETICIÓN DE EVALUACIÓN");
                        e.setCuerpo((repleg.getMasculino() ? "Estimado " : "Estimada ") + repleg.getNombre() + ":"
                                + "<br/><br/>El curso que esta realizando con nuestro centro " + m.getAlumno().getPersona().getNombreApellidos() + " finalizó el " + Fecha.getFechaDiaMesAnio(m.getGrupo().getFFin()) + ", estamos a la espera de recibir la evaluación por parte del alumno para que quede concluido el curso.<br/>"
                                + "Quedamos a la espera de recibirla a la mayor brevedad posible. Si tiene cualquier consulta, no dude en ponerse en contacto con nosotros.<br/><br/>Reciba un cordial saludo.");
                        boolean encontrado = false;
                        for (Evento ev : m.getEventoList()) {
                            if (ev.getTipoDescripcionEvento() == descripcionEventos.COMUNICACION_RECEPCION_EVALUACION) {
                                realizarEventoComunicacion(ev, e, evfac);
                                encontrado = true;
                            }
                        }
                        if (!encontrado) {
                            Evento ev = new Evento();
                            descripcionEventos.configurarEvento(ev, descripcionEventos.COMUNICACION_RECEPCION_EVALUACION);
                            ev.setFechaCreacion(new Date());
                            ev.setFechaRealizacion(new Date());
                            ev.setCreador(sesionActual.getUsuario_conectado().getPersona());
                            m.getEventoList().add(ev);
                            ev.setMatricula(m);
                            evfac.create(ev);
                            realizarEventoComunicacion(ev, e, evfac);
                        }
                        break;
                    case 1:
                        e.setAsunto(VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial() + " - BIENVENIDA AL CURSO DE FORMACIÓN");
                        e.setCuerpo((m.getAlumno().getPersona().getMasculino() ? "Estimado " : "Estimada ") + m.getAlumno().getPersona().getNombre() + ":"
                                + "<br/><br/>Desde el departamento de tutorías, queremos darle la bienvenida al curso \"" + m.getGrupo().getAccionFormativa1().getNombre() + "\",  ante cualquier duda podrá ponerse en contacto con su tutor en el horario establecido.<br/>"
                                + "Esperamos que el material haya sido recibido correctamente, en caso contrario rogamos que se pongan en contacto con el  centro de formación.<br/><br/>Reciba un cordial saludo.");
                        for (Evento ev : m.getEventoList()) {
                            if (ev.getTipoDescripcionEvento() == descripcionEventos.COMUNICACION_INICIO_CURSO) {
                                realizarEventoComunicacion(ev, e, evfac);
                            }
                        }
                        break;
                    case 2:
                        e.setAsunto(VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial() + " - SEGUIMIENTO TUTORIAL");
                        e.setCuerpo((m.getAlumno().getPersona().getMasculino() ? "Estimado " : "Estimada ") + m.getAlumno().getPersona().getNombre() + ":"
                                + "<br/><br/>Desde departamento de tutorías, le mandamos este e-mail para conocer su situación a día de hoy con respecto al curso. Recordarle que tiene a su disposición al tutor asignado para la resolución de cualquier duda o consulta que se le plantee.<br/><br/>"
                                + "Quedamos a la espera de sus noticias.");
                        for (Evento ev : m.getEventoList()) {
                            if (ev.getTipoDescripcionEvento() == descripcionEventos.COMUNICACION_SEGUIMIENTO_CURSO) {
                                realizarEventoComunicacion(ev, e, evfac);
                            }
                        }
                        break;
                    case 3:
                        e.setAsunto(VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial() + " - FINALIZACIÓN DEL CURSO");
                        e.setCuerpo((m.getAlumno().getPersona().getMasculino() ? "Estimado " : "Estimada ") + m.getAlumno().getPersona().getNombre() + ":"
                                + "<br/><br/>" + VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial() + " y el equipo humano que lo compone le da las gracias por elegir nuestro centro de formación para ampliar sus conocimientos.<br/>"
                                + "Como usted sabe su curso ha finalizado, deseamos que haya cumplido sus expectativas.<br/>"
                                + "Quedamos a  la espera de la recepción de la evaluación para terminar el seguimiento del curso.<br/><br/>Un saludo y muchas gracias.");
                        for (Evento ev : m.getEventoList()) {
                            if (ev.getTipoDescripcionEvento() == descripcionEventos.COMUNICACION_FIN_CURSO) {
                                realizarEventoComunicacion(ev, e, evfac);
                            }
                        }
                        break;
                }

                e.enviar();
            }

            UtilidadesVista.Mensaje("Se han enviado los e-mails a los destinatarios correspondientes.", FacesMessage.SEVERITY_INFO);
            borrarMats();
        }
    }

    private void borrarMats() {
        if (((LazyMatriculaDataModel) matriculas).getMatriculasPDF() != null && !((LazyMatriculaDataModel) matriculas).getMatriculasPDF().isEmpty()) {
            for (Matricula matricula : ((LazyMatriculaDataModel) matriculas).getMatriculasPDF()) {
                matricula.setSeleccionarPDF(false);
            }
        }
        if (((LazyMatriculaDataModel) matriculas).getMatriculasEmail() != null && !((LazyMatriculaDataModel) matriculas).getMatriculasEmail().isEmpty()) {
            for (Matricula matricula : ((LazyMatriculaDataModel) matriculas).getMatriculasEmail()) {
                matricula.setSeleccionarEmail(false);
            }
        }
        if (((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase() != null && !((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase().isEmpty()) {
            for (Matricula matricula : ((LazyMatriculaDataModel) matriculas).getMatriculasEmail_ase()) {
                matricula.setSeleccionarEmail_ase(false);
            }
        }

        ((LazyMatriculaDataModel) matriculas).setMatriculasPDF(new ArrayList<Matricula>());
        ((LazyMatriculaDataModel) matriculas).setMatriculasEmail(new ArrayList<Matricula>());
        ((LazyMatriculaDataModel) matriculas).setMatriculasEmail_ase(new ArrayList<Matricula>());
        ((LazyMatriculaDataModel) matriculas).setMats(null);
    }

    private void rellenaDobleFactura(List<Matricula> matriculas) {
        if (matriculas != null && !matriculas.isEmpty()) {
            for (Matricula matricula : matriculas) {
                if (matricula.getFacturaList() != null && !matricula.getFacturaList().isEmpty()) {
                    matricula.getFacturaList().get(0).setSeleccionado(true);
                    if (matricula.getFacturaList().size() > 1) {
                        matFactura.add(matricula);
                    }
                }
            }
        }
    }
}