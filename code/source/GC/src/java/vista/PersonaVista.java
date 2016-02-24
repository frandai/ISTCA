package vista;

import bean.AlumnoFacade;
import bean.EmpresaMatriculaCccFacade;
import bean.PersonaFacade;
import datos.Alumno;
import datos.Empresa;
import datos.EmpresaMatriculaCcc;
import datos.Persona;
import datosVista.Alumnovista;
import datosVista.Empresavista;
import datosVista.Personavista;
import datosVistaFacade.AlumnovistaFacade;
import datosVistaFacade.PersonavistaFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import lazyDataModel.LazyAlumnoDataModel;
import lazyDataModel.LazyEmpresaDataModel;
import lazyDataModel.LazyPersonaDataModel;
import lazyDataModel.LazyPersonaDataModel;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import util.Documentacion;
import util.TripleSelectorAlumnoCCC;

/**
 *
 *
 */
@ManagedBean(name = "personaVista")
@ViewScoped
public class PersonaVista implements Serializable {

    /**
     * Creates a new instance of EmpresaVista
     */
    @EJB
    private EmpresaMatriculaCccFacade emcfac;
    @EJB
    private PersonavistaFacade personaFacade;
    private LazyDataModel<Personavista> personas;
    private List<Personavista> personasFiltradas;
    @EJB
    private AlumnovistaFacade alumnoFacade;
    private LazyDataModel<Alumnovista> alumnos;
    private List<Alumnovista> alumnosFiltrados;
    private boolean muestraNIF = true;
    private boolean muestraNombre = true;
    private boolean muestraEmpresa = true;
    private boolean muestraTelefono = true;
    private boolean muestraTipo = false;
    private boolean muestraNivelEsudios = false;
    private boolean muestraGrupoCotizacion = false;
    private boolean muestraAreaFuncional = false;
    private boolean muestraCatProf = false;
    private boolean muestraProvincia = false;
    private boolean muestraLocalidad = false;
    private boolean muestraECSuperior = false;
    private boolean muestraNivelEC = false;
    private boolean muestraNSS = false;
    @ManagedProperty("#{sesionActual}")
    private SesionActual sesionActual;
    private Map<String, String> filterMap;
    private String enlaceDescarga = "";
    private List<TripleSelectorAlumnoCCC> matriculaCambiar = new ArrayList<TripleSelectorAlumnoCCC>();

    public boolean isMuestraNSS() {
        return muestraNSS;
    }

    public void setMuestraNSS(boolean muestraNSS) {
        this.muestraNSS = muestraNSS;
    }

    public List<TripleSelectorAlumnoCCC> getMatriculaCambiar() {
        return matriculaCambiar;
    }

    public void setMatriculaCambiar(List<TripleSelectorAlumnoCCC> matriculaCambiar) {
        this.matriculaCambiar = matriculaCambiar;
    }

    public String getEnlaceDescarga() {
        return enlaceDescarga;
    }

    public void setEnlaceDescarga(String enlaceDescarga) {
        this.enlaceDescarga = enlaceDescarga;
    }

    public void generarMatriculas() throws Exception {
        enlaceDescarga = "";
        boolean valido = true;
        if (((LazyPersonaDataModel) personas).getPersonasPDF() == null || ((LazyPersonaDataModel) personas).getPersonasPDF().isEmpty()) {
            UtilidadesVista.Mensaje("No se puede generar Documentación puesto que no se han seleccionado personas.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        if (valido) {
            String nif_error = "";
            for (Persona e : ((LazyPersonaDataModel) personas).getPersonasPDF()) {
                if (e.getAlumno() == null || e.getEmpresaList() == null || e.getEmpresaList().isEmpty()) {
                    nif_error += " " + e.getNif();
                    valido = false;
                }
            }
            if (!valido) {
                UtilidadesVista.Mensaje("No se puede generar Documentación puesto que las siguientes personas seleccionadas no son alumnos:" + nif_error + ".", FacesMessage.SEVERITY_ERROR);
            } else {
                if (matriculaCambiar.isEmpty()) {
                    for (Persona p : ((LazyPersonaDataModel) personas).getPersonasPDF()) {
                        boolean masCCC = false;
                        EmpresaMatriculaCcc ex = null;
                        for (Empresa e : p.getEmpresaList()) {
                            if (e.getEmpresaMatricula() != null && e.getEmpresaMatricula().getEmpresaMatriculaCccList() != null) {
                                masCCC = e.getEmpresaMatricula().getEmpresaMatriculaCccList().size() > 2;
                                for (EmpresaMatriculaCcc ccc : e.getEmpresaMatricula().getEmpresaMatriculaCccList()) {
                                    if (ex == null || ex.getEmpresaMatriculaCccPK().getCcc().equals("")) {
                                        ex = ccc;
                                    }
                                }
                            }
                        }
                        matriculaCambiar.add(new TripleSelectorAlumnoCCC((p.getEmpresaList().size() > 1 || masCCC), p.getAlumno(), ex));
                    }
                    for (TripleSelectorAlumnoCCC m : matriculaCambiar) {
                        if (m.isSelec()) {
                            RequestContext.getCurrentInstance().execute("dialogoCCC.show()");
                            return;
                        }
                    }
                    generarMatriculas();
                } else {
                    Map<Alumno, EmpresaMatriculaCcc> emc = new HashMap<Alumno, EmpresaMatriculaCcc>();
                    for (TripleSelectorAlumnoCCC m : matriculaCambiar) {
                        emc.put(m.getAlumno(), emcfac.find(m.getEmcc().getEmpresaMatriculaCccPK()));
                    }
                    enlaceDescarga = Documentacion.enviarPDFMatricula(emc);
                    if (((LazyPersonaDataModel) personas).getPersonasPDF() != null && !((LazyPersonaDataModel) personas).getPersonasPDF().isEmpty()) {
                        for (Persona empresa : ((LazyPersonaDataModel) personas).getPersonasPDF()) {
                            empresa.setSeleccionarPDF(false);
                        }
                    }
                    ((LazyPersonaDataModel) personas).setPersonasPDF(new ArrayList<Persona>());
                    matriculaCambiar = new ArrayList<TripleSelectorAlumnoCCC>();
                }
            }
        }
    }

    public void seleccionaTodosRegistros() {
        if (personaFacade.count(filterMap) >= 10000) {
            UtilidadesVista.Mensaje("No se pueden seleccionar todas las empresa de un filtro de más de 10000 registros. Por favor, haga un filtro con menos registros.", FacesMessage.SEVERITY_ERROR);
        } else {
            List<Personavista> mv = personas.load(0, 10000, "", SortOrder.UNSORTED, filterMap);
            List<Persona> m = new ArrayList<Persona>();
            for (Personavista matriculavista : mv) {
                matriculavista.getPersona().setSeleccionarPDF(true);
                m.add(matriculavista.getPersona());

            }
            ((LazyPersonaDataModel) personas).setPersonasPDF(m);
        }
    }

    public void setEmpPDF(Persona epresa) {
        if (((LazyPersonaDataModel) personas).getPersonasPDF() == null) {
            ((LazyPersonaDataModel) personas).setPersonasPDF(new ArrayList<Persona>());
        }
        ((LazyPersonaDataModel) personas).getPersonasPDF().remove(epresa);
        if (epresa.isSeleccionarPDF()) {
            ((LazyPersonaDataModel) personas).getPersonasPDF().add(epresa);
        }
    }

    public Map<String, String> getFilterMap() {
        return filterMap;
    }

    public void setFilterMap(Map<String, String> filterMap) {
        this.filterMap = filterMap;
    }

    public SesionActual getSesionActual() {
        return sesionActual;
    }

    public void setSesionActual(SesionActual sesionActual) {
        this.sesionActual = sesionActual;
    }

    public boolean isMuestraProvincia() {
        return muestraProvincia;
    }

    public void setMuestraProvincia(boolean muestraProvincia) {
        this.muestraProvincia = muestraProvincia;
    }

    public boolean isMuestraLocalidad() {
        return muestraLocalidad;
    }

    public void setMuestraLocalidad(boolean muestraLocalidad) {
        this.muestraLocalidad = muestraLocalidad;
    }

    public boolean isMuestraECSuperior() {
        return muestraECSuperior;
    }

    public void setMuestraECSuperior(boolean muestraECSuperior) {
        this.muestraECSuperior = muestraECSuperior;
    }

    public boolean isMuestraNivelEC() {
        return muestraNivelEC;
    }

    public void setMuestraNivelEC(boolean muestraNivelEC) {
        this.muestraNivelEC = muestraNivelEC;
    }

    public PersonaVista() {
    }

    public LazyDataModel<Personavista> getPersonas() {
        if (personas == null) {
            personas = new LazyPersonaDataModel(personaFacade, getSesionActual().tienePermiso(24) ? getSesionActual().getUsuario_conectado().getNif() : "");
        }
        return personas;
    }

    public List<Personavista> getPersonasFiltradas() {
        return personasFiltradas;
    }

    public void setPersonasFiltradas(List<Personavista> personasFiltradas) {
        this.personasFiltradas = personasFiltradas;
    }

    public LazyDataModel<Alumnovista> getAlumnos() {
        if (alumnos == null) {
            alumnos = new LazyAlumnoDataModel(alumnoFacade);
        }
        return alumnos;
    }

    public List<Alumnovista> getAlumnosFiltrados() {
        return alumnosFiltrados;
    }

    public void setAlumnosFiltrados(List<Alumnovista> alumnosFiltrados) {
        this.alumnosFiltrados = alumnosFiltrados;
    }

    public boolean isMuestraNombre() {
        return muestraNombre;
    }

    public void setMuestraNombre(boolean muestraNombre) {
        this.muestraNombre = muestraNombre;
    }

    public boolean isMuestraNIF() {
        return muestraNIF;
    }

    public void setMuestraNIF(boolean muestraNIF) {
        this.muestraNIF = muestraNIF;
    }

    public boolean isMuestraTipo() {
        return muestraTipo;
    }

    public void setMuestraTipo(boolean muestraTipo) {
        this.muestraTipo = muestraTipo;
    }

    public boolean isMuestraNivelEsudios() {
        return muestraNivelEsudios;
    }

    public void setMuestraNivelEsudios(boolean muestraNivelEsudios) {
        this.muestraNivelEsudios = muestraNivelEsudios;
    }

    public boolean isMuestraGrupoCotizacion() {
        return muestraGrupoCotizacion;
    }

    public void setMuestraGrupoCotizacion(boolean muestraGrupoCotizacion) {
        this.muestraGrupoCotizacion = muestraGrupoCotizacion;
    }

    public boolean isMuestraAreaFuncional() {
        return muestraAreaFuncional;
    }

    public void setMuestraAreaFuncional(boolean muestraAreaFuncional) {
        this.muestraAreaFuncional = muestraAreaFuncional;
    }

    public boolean isMuestraCatProf() {
        return muestraCatProf;
    }

    public void setMuestraCatProf(boolean muestraCatProf) {
        this.muestraCatProf = muestraCatProf;
    }

    public boolean isMuestraEmpresa() {
        return muestraEmpresa;
    }

    public void setMuestraEmpresa(boolean muestraEmpresa) {
        this.muestraEmpresa = muestraEmpresa;
    }

    public boolean isMuestraTelefono() {
        return muestraTelefono;
    }

    public void setMuestraTelefono(boolean muestraTelefono) {
        this.muestraTelefono = muestraTelefono;
    }
}
