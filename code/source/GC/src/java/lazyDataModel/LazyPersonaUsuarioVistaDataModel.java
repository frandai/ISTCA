/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import datosVista.Personausuariovista;
import datosVistaFacade.PersonausuariovistaFacade;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 *
 */
public class LazyPersonaUsuarioVistaDataModel extends LazyDataModel<Personausuariovista> {

    private PersonausuariovistaFacade PersonausuariovistaFacade;

    public LazyPersonaUsuarioVistaDataModel(PersonausuariovistaFacade personaFacade) {
        this.PersonausuariovistaFacade = personaFacade;
    }

    @Override
    public List<Personausuariovista> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<Personausuariovista> emp = PersonausuariovistaFacade.load(first, pageSize, sortField, sortOrder, filters);
        if (emp.size() < pageSize) {
            this.setRowCount(first + pageSize);
        } else {
            this.setRowCount(Integer.MAX_VALUE);
        }
        return emp;
    }
    
    
    
  
}
