/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import bean.PersonaFacade;
import datos.Empresa;
import datos.Persona;
import datosVista.Empresavista;
import datosVista.Personavista;
import datosVista.Personavista;
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
public class LazyPersonaDataModel extends LazyDataModel<Personavista> {

    private PersonavistaFacade personaFacade;
    private String nif_ec_filtrar;
    private List<Persona> personasPDF;

    public List<Persona> getPersonasPDF() {
        return personasPDF;
    }

    public void setPersonasPDF(List<Persona> personasPDF) {
        this.personasPDF = personasPDF;
    }

    public LazyPersonaDataModel(PersonavistaFacade personaFacade, String nif_ec_filtrar) {
        this.personaFacade = personaFacade;
        this.nif_ec_filtrar = nif_ec_filtrar;
    }

    @Override
    public List<Personavista> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<Personavista> emp = personaFacade.load(first, pageSize, sortField, sortOrder, filters, nif_ec_filtrar);
        if (emp.size() < pageSize) {
            this.setRowCount(emp.size());
            try {
                this.setRowIndex(emp.size());
            } catch (Exception e) {
                this.setRowIndex(-1);
            }
        } else {
            this.setRowIndex(-1);
            this.setRowCount(Integer.MAX_VALUE);
        }

        if (personasPDF != null) {
            for (Persona m : personasPDF) {
                for (Personavista ma : emp) {
                    if (ma.getNif().equals(m.getNif())) {
                        ma.getPersona().setSeleccionarPDF(true);
                    }
                }
            }
        }

        return emp;
    }
}
