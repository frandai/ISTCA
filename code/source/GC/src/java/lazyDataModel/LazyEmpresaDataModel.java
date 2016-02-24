/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import bean.EmpresaFacade;
import datos.Empresa;
import datos.Matricula;
import datosVista.Empresavista;
import datosVista.Matriculavista;
import datosVistaFacade.EmpresavistaFacade;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 *
 */
public class LazyEmpresaDataModel extends LazyDataModel<Empresavista> {

    private EmpresavistaFacade empresaFacade;
    private String nif_ec_filtrar;
    private List<Empresa> empresasPDF;
    private Map<String, String> filters;

    public List<Empresa> getEmpresasPDF() {
        return empresasPDF;
    }

    public void setEmpresasPDF(List<Empresa> empresasPDF) {
        this.empresasPDF = empresasPDF;
    }

    public LazyEmpresaDataModel(EmpresavistaFacade empresaFacade, String nif_ec_filtrar) {
        this.empresaFacade = empresaFacade;
        this.nif_ec_filtrar = nif_ec_filtrar;
    }

    @Override
    public List<Empresavista> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<Empresavista> emp = empresaFacade.load(first, pageSize, sortField, sortOrder, filters, nif_ec_filtrar);
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

        if (empresasPDF != null) {
            for (Empresavista ma : emp) {
                for (Empresa m : empresasPDF) {

                    if (ma.getNif().equals(m.getNif())) {
                        ma.getEmpresa().setSeleccionarPDF(true);
                        break;
                    }
                }
            }
        }

        return emp;
    }
}
