/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import bean.AccionFormativaFacade;
import datos.AccionFormativa;
import datos.AccionFormativa;
import datos.Empresa;
import datos.EmpresaAgrupacion;
import datosVista.Accionformativavista;
import datosVista.Accionformativavista;
import datosVista.Personavista;
import datosVistaFacade.AccionformativavistaFacade;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lazyDataModel.LazyAccionFormativaDataModel;
import lazyDataModel.LazyAccionFormativaDataModel;
import lazyDataModel.LazyEmpresaDataModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 *
 */
@ManagedBean(name = "accionFormativaVista")
@ViewScoped
public class AccionFormativaVista implements Serializable {

    @EJB
    private AccionformativavistaFacade accionFormativaFacade;
    private LazyDataModel<Accionformativavista> accionesFormativas;
    private List<Accionformativavista> accionesFormativasFiltradas;
    private boolean muestraRef = true;
    private boolean muestraNombre = true;
    private boolean muestraModalidad = false;
    private boolean muestraPrecio = true;
    private boolean muestraHoras = true;
    private boolean muestraProveedor = true;
    private boolean muestraTipo = false;
    private Map<String, String> filterMap;
    private String enlaceDescarga = "";
    private EmpresaAgrupacion agrupacion;

    public void generarXMLAF() throws FileNotFoundException, IOException {
        enlaceDescarga = "";
        boolean valido = true;
        if (((LazyAccionFormativaDataModel) accionesFormativas).getAccionFormativasPDF() == null || ((LazyAccionFormativaDataModel) accionesFormativas).getAccionFormativasPDF().isEmpty()) {
            UtilidadesVista.Mensaje("No se puede generar XML puesto que no se han seleccionado Acciones Formativas.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        if (agrupacion == null) {
            UtilidadesVista.Mensaje("No se puede generar XML puesto que no se ha seleccionado Agrupación.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        if (valido) {
            enlaceDescarga = util.Xml.generarXMLAccionesFormativas(((LazyAccionFormativaDataModel) accionesFormativas).getAccionFormativasPDF(), agrupacion);
            if (((LazyAccionFormativaDataModel) accionesFormativas).getAccionFormativasPDF() != null && !((LazyAccionFormativaDataModel) accionesFormativas).getAccionFormativasPDF().isEmpty()) {
                for (AccionFormativa empresa : ((LazyAccionFormativaDataModel) accionesFormativas).getAccionFormativasPDF()) {
                    empresa.setSeleccionarPDF(false);
                }
            }
            ((LazyAccionFormativaDataModel) accionesFormativas).setAccionFormativasPDF(new ArrayList<AccionFormativa>());
        }

    }

    public EmpresaAgrupacion getAgrupacion() {
        return agrupacion;
    }

    public void setAgrupacion(EmpresaAgrupacion agrupacion) {
        this.agrupacion = agrupacion;
    }

    public Map<String, String> getFilterMap() {
        return filterMap;
    }

    public void setFilterMap(Map<String, String> filterMap) {
        this.filterMap = filterMap;
    }

    public String getEnlaceDescarga() {
        return enlaceDescarga;
    }

    public void setEnlaceDescarga(String enlaceDescarga) {
        this.enlaceDescarga = enlaceDescarga;
    }

    public void seleccionaTodosRegistros() {
        if (accionFormativaFacade.count(filterMap) >= 10000) {
            UtilidadesVista.Mensaje("No se pueden seleccionar todas las empresa de un filtro de más de 10000 registros. Por favor, haga un filtro con menos registros.", FacesMessage.SEVERITY_ERROR);
        } else {
            List<Accionformativavista> mv = accionesFormativas.load(0, 10000, "", SortOrder.UNSORTED, filterMap);
            List<AccionFormativa> m = new ArrayList<AccionFormativa>();
            for (Accionformativavista matriculavista : mv) {
                matriculavista.getAccionFormativa().setSeleccionarPDF(true);
                m.add(matriculavista.getAccionFormativa());

            }
            ((LazyAccionFormativaDataModel) accionesFormativas).setAccionFormativasPDF(m);
        }
    }

    public void setEmpPDF(AccionFormativa epresa) {
        if (((LazyAccionFormativaDataModel) accionesFormativas).getAccionFormativasPDF() == null) {
            ((LazyAccionFormativaDataModel) accionesFormativas).setAccionFormativasPDF(new ArrayList<AccionFormativa>());
        }
        ((LazyAccionFormativaDataModel) accionesFormativas).getAccionFormativasPDF().remove(epresa);
        if (epresa.isSeleccionarPDF()) {
            ((LazyAccionFormativaDataModel) accionesFormativas).getAccionFormativasPDF().add(epresa);
        }
    }

    public LazyDataModel<Accionformativavista> getAccionesFormativas() {
        if (accionesFormativas == null) {
            accionesFormativas = new LazyAccionFormativaDataModel(accionFormativaFacade);
        }
        return accionesFormativas;
    }

    public List<Accionformativavista> getAccionesFormativasFiltradas() {
        return accionesFormativasFiltradas;
    }

    public void setAccionesFormativasFiltradas(List<Accionformativavista> accionesFormativasFiltradas) {
        this.accionesFormativasFiltradas = accionesFormativasFiltradas;
    }

    public AccionFormativaVista() {
    }

    public boolean isMuestraNombre() {
        return muestraNombre;
    }

    public void setMuestraNombre(boolean muestraNombre) {
        this.muestraNombre = muestraNombre;
    }

    public boolean isMuestraModalidad() {
        return muestraModalidad;
    }

    public void setMuestraModalidad(boolean muestraModalidad) {
        this.muestraModalidad = muestraModalidad;
    }

    public boolean isMuestraPrecio() {
        return muestraPrecio;
    }

    public void setMuestraPrecio(boolean muestraPrecio) {
        this.muestraPrecio = muestraPrecio;
    }

    public boolean isMuestraProveedor() {
        return muestraProveedor;
    }

    public void setMuestraProveedor(boolean muestraProveedor) {
        this.muestraProveedor = muestraProveedor;
    }

    public boolean isMuestraTipo() {
        return muestraTipo;
    }

    public void setMuestraTipo(boolean muestraTipo) {
        this.muestraTipo = muestraTipo;
    }

    public boolean isMuestraHoras() {
        return muestraHoras;
    }

    public void setMuestraHoras(boolean muestraHoras) {
        this.muestraHoras = muestraHoras;
    }

    public boolean isMuestraRef() {
        return muestraRef;
    }

    public void setMuestraRef(boolean muestraRef) {
        this.muestraRef = muestraRef;
    }
}
