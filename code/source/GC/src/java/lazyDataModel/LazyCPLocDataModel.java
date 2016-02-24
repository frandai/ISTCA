/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import datosVista.CpLocalidadProvincia;
import datosVistaFacade.CpLocalidadProvinciaFacade;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 *
 */
public class LazyCPLocDataModel extends LazyDataModel<CpLocalidadProvincia> {

    private CpLocalidadProvinciaFacade CpLocalidadProvinciaFacade;

    public LazyCPLocDataModel(CpLocalidadProvinciaFacade personaFacade) {
        this.CpLocalidadProvinciaFacade = personaFacade;
    }

    @Override
    public List<CpLocalidadProvincia> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<CpLocalidadProvincia> emp = CpLocalidadProvinciaFacade.load(first, pageSize, sortField, sortOrder, filters);
        if (emp.size() < pageSize) {
            this.setRowCount(first + pageSize);
        } else {
            this.setRowCount(Integer.MAX_VALUE);
        }
        return emp;
    }
    
    
    
  
}