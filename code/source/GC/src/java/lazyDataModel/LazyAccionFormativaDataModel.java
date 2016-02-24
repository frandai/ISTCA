/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import datos.AccionFormativa;
import datosVista.Accionformativavista;
import datosVistaFacade.AccionformativavistaFacade;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 *
 */
public class LazyAccionFormativaDataModel extends LazyDataModel<Accionformativavista> {

    private AccionformativavistaFacade accionformativaFacade;
    private List<AccionFormativa> accionFormativasPDF;
    private Map<String, String> filters;

    public LazyAccionFormativaDataModel(AccionformativavistaFacade accionformativaFacade) {
        this.accionformativaFacade = accionformativaFacade;
    }

    public List<AccionFormativa> getAccionFormativasPDF() {
        return accionFormativasPDF;
    }

    public void setAccionFormativasPDF(List<AccionFormativa> accionFormativasPDF) {
        this.accionFormativasPDF = accionFormativasPDF;
    }

    @Override
    public List<Accionformativavista> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<Accionformativavista> emp = accionformativaFacade.load(first, pageSize, sortField, sortOrder, filters);
        if (this.filters == null || !this.filters.equals(filters)) {
            this.setRowCount(accionformativaFacade.count(filters));
            this.filters = filters;
        }

        if (accionFormativasPDF != null) {

            for (Accionformativavista ma : emp) {
                for (AccionFormativa m : accionFormativasPDF) {
                    if (ma.getId().equals(m.getId())) {
                        ma.getAccionFormativa().setSeleccionarPDF(true);
                        break;
                    }
                }

            }
        }
        return emp;
    }
}
