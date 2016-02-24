/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import bean.MatriculaFacade;
import datos.Factura;
import datos.Matricula;
import datos.MatriculaFactura;
import datosVista.Matriculavista;
import datosVistaFacade.MatriculavistaFacade;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 *
 */
public class LazyMatriculaDataModel extends LazyDataModel<Matriculavista> {

    private MatriculavistaFacade matriculaFacade;
    private Date ffin_desde;
    private Date ffin_hasta;
    private List<Matricula> mats = null;
    private List<Matricula> matriculasPDF;
    private List<Matricula> matriculasEmail;
    private List<Matricula> matriculasEmail_ase;
    private List<Factura> matriculasFacturas;
    private boolean inicio_fin = false;
    private int numFilas = 0;
    private String sortField;
    private SortOrder sortOrder;
    private final String nif_ec_filtrar;

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<Factura> getMatriculasFacturas() {
        return matriculasFacturas;
    }

    public void setMatriculasFacturas(List<Factura> matriculasFacturas) {
        this.matriculasFacturas = matriculasFacturas;
    }

    public List<Matricula> getMats() {
        return mats;
    }

    public List<Matricula> getMatriculasPDF() {
        return matriculasPDF;
    }

    public void setMatriculasPDF(List<Matricula> matriculasPDF) {
        this.matriculasPDF = matriculasPDF;
    }

    public List<Matricula> getMatriculasEmail() {
        return matriculasEmail;
    }

    public void setMatriculasEmail(List<Matricula> matriculasEmail) {
        this.matriculasEmail = matriculasEmail;
    }

    public List<Matricula> getMatriculasEmail_ase() {
        return matriculasEmail_ase;
    }

    public void setMatriculasEmail_ase(List<Matricula> matriculasEmail_ase) {
        this.matriculasEmail_ase = matriculasEmail_ase;
    }

    public void setMats(List<Matricula> mats) {
        this.mats = mats;
    }

    public LazyMatriculaDataModel(MatriculavistaFacade mFacade, String nif_ec_filtrar) {
        this.matriculaFacade = mFacade;
        this.ffin_desde = null;
        this.ffin_hasta = null;
        this.nif_ec_filtrar = nif_ec_filtrar;
    }

    public LazyMatriculaDataModel(MatriculavistaFacade matriculavFacade, Date ffin_desde, Date ffin_hasta, boolean inicio_fin, String nif_ec_filtrar) {
        this.ffin_desde = ffin_desde;
        this.ffin_hasta = ffin_hasta;
        this.inicio_fin = inicio_fin;
        this.matriculaFacade = matriculavFacade;
        this.nif_ec_filtrar = nif_ec_filtrar;
    }

    @Override
    public List<Matriculavista> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        if(this.sortField != null && this.sortOrder != null){
            sortField = this.sortField;
            sortOrder = this.sortOrder;
        }
        List<Matriculavista> emp;
        if (ffin_desde != null && ffin_hasta != null) {
            emp = matriculaFacade.load(first, pageSize, sortField, sortOrder, filters, ffin_desde, ffin_hasta, inicio_fin, nif_ec_filtrar);
        } else {
            emp = matriculaFacade.load(first, pageSize, sortField, sortOrder, filters, null, null, false, nif_ec_filtrar);
        }
        /*if (emp.size() < pageSize) {
         this.setRowCount(first + pageSize);
         } else {
         this.setRowCount(Integer.MAX_VALUE);
         }*/
        if (ffin_desde != null && ffin_hasta != null) {
            this.setRowCount(matriculaFacade.count(filters, ffin_desde, ffin_hasta, inicio_fin, nif_ec_filtrar));
        } else {
            this.setRowCount(matriculaFacade.count(filters, null, null, false, nif_ec_filtrar));
        }
        for (Matriculavista ma : emp) {
            ma.getMatricula().setSeleccionarPDF(false);
            for (Factura f : ma.getMatricula().getFacturaList()) {
                f.setSeleccionado(false);
            }
        }
        if (matriculasFacturas != null) {
            for (Factura f : matriculasFacturas) {
                for (MatriculaFactura mf : f.getMatriculaFacturaList()) {
                    for (Matriculavista ma : emp) {
                        if (ma.getId().equals(mf.getMatricula1().getId())) {
                            for (Factura fac : ma.getMatricula().getFacturaList()) {
                                if (fac.getId().equals(f.getId())) {
                                    fac.setSeleccionado(true);
                                }
                            }
                        }
                    }
                }

            }
        }
        if (mats != null) {
            for (Matricula m : mats) {
                for (Matriculavista ma : emp) {
                    if (ma.getId().equals(m.getId())) {
                        ma.getMatricula().setEmailEnvio(m.getEmailEnvio());
                    }
                }
            }
        }
        if (matriculasPDF != null) {
            for (Matricula m : matriculasPDF) {
                for (Matriculavista ma : emp) {
                    if (ma.getId().equals(m.getId())) {
                        ma.getMatricula().setSeleccionarPDF(true);
                    }
                }
            }
        }
        if (matriculasEmail != null) {
            for (Matricula m : matriculasEmail) {
                for (Matriculavista ma : emp) {
                    if (ma.getId().equals(m.getId())) {
                        ma.getMatricula().setSeleccionarEmail(true);

                    }
                }
            }
        }
        if (matriculasEmail_ase != null) {
            for (Matricula m : matriculasEmail_ase) {
                for (Matriculavista ma : emp) {
                    if (ma.getId().equals(m.getId())) {
                        ma.getMatricula().setSeleccionarEmail_ase(true);
                    }
                }
            }
        }
        numFilas = emp.size();
        return emp;
    }

    public int getNumFilas() {
        return numFilas;
    }

    public void setNumFilas(int numFilas) {
        this.numFilas = numFilas;
    }
}
