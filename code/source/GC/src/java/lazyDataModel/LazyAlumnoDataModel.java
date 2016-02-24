/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import datosVista.Alumnovista;
import datosVistaFacade.AlumnovistaFacade;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 *
 */
public class LazyAlumnoDataModel extends LazyDataModel<Alumnovista> {

    private AlumnovistaFacade alumnoFacade;

    public LazyAlumnoDataModel(AlumnovistaFacade accionformativaFacade) {
        this.alumnoFacade = accionformativaFacade;
    }

    @Override
    public List<Alumnovista> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<Alumnovista> emp = alumnoFacade.load(first, pageSize, sortField, sortOrder, filters);
        if (emp.size() < pageSize) {
            this.setRowCount(first + pageSize);
        } else {
            this.setRowCount(Integer.MAX_VALUE);
        }
        return emp;
    }
}
