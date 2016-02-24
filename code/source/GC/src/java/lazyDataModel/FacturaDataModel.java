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
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.model.ListDataModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 *
 */
public class FacturaDataModel extends ListDataModel<Facturavista> implements SelectableDataModel<Facturavista>, Serializable {


    public FacturaDataModel(List<Facturavista> list) {
        super(list);
    }

    @Override
    public Object getRowKey(Facturavista object) {
        return object.getIdfactura();
    }

    @Override
    public Facturavista getRowData(String rowKey) {
        List<Facturavista> fls = (List<Facturavista>) getWrappedData();  
          
        for(Facturavista fl : fls) {  
            if(fl.getIdfactura().equals(rowKey))  
                return fl;  
        }  
          
        return null;  
    }
}
