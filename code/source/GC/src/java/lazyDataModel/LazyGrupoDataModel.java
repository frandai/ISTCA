/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import datosVista.Grupovista;
import datosVistaFacade.GrupovistaFacade;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 *
 */
public class LazyGrupoDataModel extends LazyDataModel<Grupovista> {
    
    private GrupovistaFacade grupoFacade;
    private Map<String, String> filters;
    private boolean desdeMat = false;
    
    public LazyGrupoDataModel(GrupovistaFacade grupoFacade) {
        this.grupoFacade = grupoFacade;
    }
    
    public LazyGrupoDataModel(GrupovistaFacade grupoFacade, boolean desdeMat) {
        this.grupoFacade = grupoFacade;
        this.desdeMat = desdeMat;
    }
    
    @Override
    public List<Grupovista> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<Grupovista> emp = grupoFacade.load(first, pageSize, sortField, sortOrder, filters);
        if (this.filters == null || !this.filters.equals(filters)) {
            if (!desdeMat) {
                this.setRowCount(grupoFacade.count(filters));
            } else {
                this.setRowCount(Integer.MAX_VALUE);
            }
            this.filters = filters;
        }
        return emp;
    }
}
