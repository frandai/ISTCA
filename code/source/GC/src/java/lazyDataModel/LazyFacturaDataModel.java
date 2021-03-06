/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import bean.FacturaFacade;
import bean.PersonaFacade;
import datos.Persona;
import datosVista.Facturavista;
import datosVista.Personavista;
import datosVista.Personavista;
import datosVistaFacade.FacturavistaFacade;
import datosVistaFacade.PersonavistaFacade;
import datosVistaFacade.PersonavistaFacade;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 *
 */
public class LazyFacturaDataModel extends LazyDataModel<Facturavista> {

    private FacturavistaFacade facturaFacade;

    public LazyFacturaDataModel(FacturavistaFacade facturaFacade) {
        this.facturaFacade = facturaFacade;
    }

    @Override
    public List<Facturavista> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<Facturavista> emp = facturaFacade.load(first, pageSize, sortField, sortOrder, filters);
        this.setRowCount(facturaFacade.count(filters));
        return emp;
    }

    @Override
    public Object getRowKey(Facturavista object) {
        return object.getIdfactura();
    }

    @Override
    public Facturavista getRowData(String rowKey) {
        return (Facturavista) facturaFacade.find(rowKey);
    }
    
    
    
  
}
