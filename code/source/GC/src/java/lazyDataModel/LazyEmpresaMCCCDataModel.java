/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import datosVista.Empresamcccvista;
import datosVistaFacade.EmpresamcccvistaFacade;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 *
 */
public class LazyEmpresaMCCCDataModel extends LazyDataModel<Empresamcccvista> {

    private EmpresamcccvistaFacade accionformativaFacade;

    public LazyEmpresaMCCCDataModel(EmpresamcccvistaFacade accionformativaFacade) {
        this.accionformativaFacade = accionformativaFacade;
    }

    @Override
    public List<Empresamcccvista> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<Empresamcccvista> emp = accionformativaFacade.load(first, pageSize, sortField, sortOrder, filters);
        if (emp.size() < pageSize) {
            this.setRowCount(first + pageSize);
        } else {
            this.setRowCount(Integer.MAX_VALUE);
        }
        return emp;
    }
}
