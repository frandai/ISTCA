package vista;

import bean.FacturaEstadoFacade;
import bean.FacturaFacade;
import bean.RemesaFacade;
import datos.EmpresaMatriculaCcc;
import datos.Factura;
import datos.FacturaEstado;
import datos.FacturaHistoricoEstado;
import datos.FacturaHistoricoEstadoPK;
import datos.Remesa;
import datosVista.Facturavista;
import datosVistaFacade.FacturavistaFacade;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import lazyDataModel.FacturaDataModel;
import lazyDataModel.LazyFacturaDataModel;
import lazyDataModel.LazyMatriculaDataModel;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import util.ExportarRemesa;
import util.Fecha;
import util.VariablesSistema;

/**
 *
 *
 */
@ManagedBean(name = "facturaVista")
@ViewScoped
public class FacturaVista implements Serializable {

    /**
     * Creates a new instance of EmpresaVista
     */
    @EJB
    private FacturavistaFacade facturaFacade;
    @EJB
    private FacturaFacade facFacade;
    @EJB
    private RemesaFacade remesaFacade;
    @EJB
    private FacturaEstadoFacade fef;
    private Remesa remesaSeleccionada;
    private DataModel<Facturavista> facturas;
    private List<Facturavista> facturasFiltradas;
    private boolean muestraIdFactura = true;
    private boolean muestraRazonSocial = true;
    private boolean muestraAlumno = false;
    private boolean muestraImporte = true;
    private boolean muestraGrupo = false;
    private boolean muestraFechaMatricula = false;
    private boolean muestraBaja = true;
    private boolean muestraPagoPosterior = false;
    private boolean muestraCcc = false;
    private boolean muestraFechaEstado = false;
    private boolean muestraFechaCreacionFactura = true;
    private boolean muestraIdMatricula = false;
    private boolean muestraPagoAplazado = false;
    private boolean muestraFechasPagoPosterior = false;
    private boolean muestraEstadoFactura = true;
    private boolean muestraNumFila = true;
    private boolean muestraPuedeGenerarRemesa = false;
    private boolean muestraFechasPagoAplazado = false;
    private boolean muestraFechaInicioGrupo = false;
    private boolean muestraFechaFinGrupo = false;
    private boolean muestraRemesas = false;
    private boolean muestraTotalFacturado = false;
    private boolean muestraProveedor = false;
    private Facturavista[] facturasSeleccionadas;
    private Map<String, String> filterMap;
    private boolean seleccionadoTodo = false;
    private Double desdeImporteFac;
    private Double hastaImporteFac;
    private Date desdeFechaEstadoFac;
    private Date hastaFechaEstadoFac;
    private Factura borrarFactura;
    private String facturaAniadir;

    public String getFacturaAniadir() {
        return facturaAniadir;
    }

    public void setFacturaAniadir(String facturaAniadir) {
        this.facturaAniadir = facturaAniadir;
    }

    public Factura getBorrarFactura() {
        return borrarFactura;
    }

    public void setBorrarFactura(Factura borrarFactura) {
        remesaSeleccionada.getFacturaList().remove(borrarFactura);
    }

    public Remesa getRemesaSeleccionada() {
        return remesaSeleccionada;
    }

    public void setRemesaSeleccionada(Remesa remesaSeleccionada) {
        this.remesaSeleccionada = remesaSeleccionada;
    }

    public Date getDesdeFechaEstadoFac() {
        return desdeFechaEstadoFac;
    }

    public void setDesdeFechaEstadoFac(Date desdeFechaEstadoFac) {
        this.desdeFechaEstadoFac = desdeFechaEstadoFac;
    }

    public Date getHastaFechaEstadoFac() {
        return hastaFechaEstadoFac;
    }

    public void setHastaFechaEstadoFac(Date hastaFechaEstadoFac) {
        this.hastaFechaEstadoFac = hastaFechaEstadoFac;
    }

    public void buscaFiltroFechaEstadoFac() {
        if (desdeFechaEstadoFac != null || desdeFechaEstadoFac != null) {
            desdeImporteFac = hastaImporteFac = null;
            facturas = new FacturaDataModel(facturaFacade.findAll(desdeFechaEstadoFac, hastaFechaEstadoFac));

            muestraNumFila = true;
            muestraIdFactura = true;
            muestraRazonSocial = true;
            muestraImporte = true;
            muestraBaja = true;
            muestraEstadoFactura = true;
            muestraFechaEstado = true;
            muestraFechaCreacionFactura = true;
            muestraTotalFacturado = false;
            muestraAlumno = false;
            muestraGrupo = false;
            muestraFechaMatricula = false;
            muestraPagoPosterior = false;
            muestraCcc = false;
            muestraIdMatricula = false;
            muestraPagoAplazado = false;
            muestraFechasPagoPosterior = false;
            muestraPuedeGenerarRemesa = false;
            muestraFechasPagoAplazado = false;
            muestraFechaInicioGrupo = false;
            muestraFechaFinGrupo = false;
            muestraRemesas = false;
            muestraProveedor = false;

            facturasFiltradas = new ArrayList<Facturavista>();

        }
    }

    public void buscaFiltroImporteFac() {
        if (desdeImporteFac != null || hastaImporteFac != null) {
            desdeFechaEstadoFac = hastaFechaEstadoFac = null;
            facturas = new FacturaDataModel(facturaFacade.findAll(desdeImporteFac, hastaImporteFac));

            muestraNumFila = true;
            muestraIdFactura = true;
            muestraRazonSocial = true;
            muestraImporte = true;
            muestraFechaCreacionFactura = true;
            muestraTotalFacturado = true;
            muestraAlumno = false;
            muestraGrupo = false;
            muestraFechaMatricula = false;
            muestraBaja = false;
            muestraPagoPosterior = false;
            muestraCcc = false;
            muestraFechaEstado = false;
            muestraIdMatricula = false;
            muestraPagoAplazado = false;
            muestraFechasPagoPosterior = false;
            muestraEstadoFactura = false;
            muestraPuedeGenerarRemesa = false;
            muestraFechasPagoAplazado = false;
            muestraFechaInicioGrupo = false;
            muestraFechaFinGrupo = false;
            muestraRemesas = false;
            muestraProveedor = false;

            facturasFiltradas = new ArrayList<Facturavista>();

        }
    }

    public void borraFiltro() {
        desdeImporteFac = hastaImporteFac = null;
        desdeFechaEstadoFac = hastaFechaEstadoFac = null;
        facturas = new FacturaDataModel(facturaFacade.findAll());
    }

    public Double getDesdeImporteFac() {
        return desdeImporteFac;
    }

    public void setDesdeImporteFac(Double desdeImporteFac) {
        this.desdeImporteFac = desdeImporteFac;
    }

    public Double getHastaImporteFac() {
        return hastaImporteFac;
    }

    public void setHastaImporteFac(Double hastaImporteFac) {
        this.hastaImporteFac = hastaImporteFac;
    }

    public void filtrarRemesasPendientes() {
        muestraNumFila = true;
        muestraIdFactura = true;
        muestraRazonSocial = true;
        muestraImporte = true;
        muestraFechaInicioGrupo = true;
        muestraBaja = true;
        muestraFechasPagoPosterior = true;
        muestraEstadoFactura = true;
        muestraFechasPagoAplazado = true;
        muestraPuedeGenerarRemesa = true;
        muestraRemesas = true;
        muestraFechaMatricula = false;
        muestraPagoPosterior = false;
        muestraCcc = false;
        muestraFechaEstado = false;
        muestraFechaCreacionFactura = false;
        muestraIdMatricula = false;
        muestraPagoAplazado = false;
        muestraFechaFinGrupo = false;
        muestraTotalFacturado = false;
        muestraProveedor = false;
        muestraAlumno = false;
        muestraGrupo = false;

        facturasFiltradas = new ArrayList<Facturavista>();
    }

    public void filtrarDefecto() {
        muestraIdFactura = true;
        muestraRazonSocial = true;
        muestraAlumno = false;
        muestraImporte = true;
        muestraGrupo = false;
        muestraFechaMatricula = false;
        muestraBaja = true;
        muestraPagoPosterior = false;
        muestraCcc = false;
        muestraFechaEstado = false;
        muestraFechaCreacionFactura = true;
        muestraIdMatricula = false;
        muestraPagoAplazado = false;
        muestraFechasPagoPosterior = false;
        muestraEstadoFactura = true;
        muestraNumFila = true;
        muestraPuedeGenerarRemesa = false;
        muestraFechasPagoAplazado = false;
        muestraFechaInicioGrupo = false;
        muestraFechaFinGrupo = false;
        muestraRemesas = false;
        muestraTotalFacturado = false;
        muestraProveedor = false;

        facturasFiltradas = new ArrayList<Facturavista>();
    }

    public void filtrarFacturasACobrarPagoAplazado() {
        muestraNumFila = true;
        muestraIdFactura = true;
        muestraRazonSocial = true;
        muestraAlumno = false;
        muestraImporte = true;
        muestraGrupo = false;
        muestraFechaMatricula = false;
        muestraBaja = true;
        muestraPagoPosterior = true;
        muestraCcc = true;
        muestraFechaEstado = false;
        muestraFechaCreacionFactura = false;
        muestraIdMatricula = false;
        muestraPagoAplazado = true;
        muestraFechasPagoPosterior = false;
        muestraEstadoFactura = true;
        muestraPuedeGenerarRemesa = false;
        muestraFechasPagoAplazado = true;
        muestraFechaInicioGrupo = true;
        muestraFechaFinGrupo = false;
        muestraRemesas = false;
        muestraTotalFacturado = false;
        muestraProveedor = false;

        facturasFiltradas = new ArrayList<Facturavista>();

    }

    public void filtrarFacturasACobrarPagoPosterior() {
        muestraNumFila = true;
        muestraIdFactura = true;
        muestraRazonSocial = true;
        muestraAlumno = false;
        muestraImporte = true;
        muestraGrupo = false;
        muestraFechaMatricula = false;
        muestraBaja = true;
        muestraPagoPosterior = true;
        muestraCcc = true;
        muestraFechaEstado = false;
        muestraFechaCreacionFactura = false;
        muestraIdMatricula = false;
        muestraPagoAplazado = true;
        muestraFechasPagoPosterior = true;
        muestraEstadoFactura = true;
        muestraPuedeGenerarRemesa = false;
        muestraFechasPagoAplazado = false;
        muestraFechaInicioGrupo = true;
        muestraFechaFinGrupo = false;
        muestraRemesas = false;
        muestraTotalFacturado = false;
        muestraProveedor = false;

        facturasFiltradas = new ArrayList<Facturavista>();

    }

    public void filtrarFacturasACobrar() {
        muestraNumFila = true;
        muestraIdFactura = true;
        muestraRazonSocial = true;
        muestraAlumno = false;
        muestraImporte = true;
        muestraGrupo = false;
        muestraFechaMatricula = false;
        muestraBaja = true;
        muestraPagoPosterior = true;
        muestraCcc = true;
        muestraFechaEstado = false;
        muestraFechaCreacionFactura = false;
        muestraIdMatricula = false;
        muestraPagoAplazado = true;
        muestraFechasPagoPosterior = false;
        muestraEstadoFactura = true;
        muestraPuedeGenerarRemesa = false;
        muestraFechasPagoAplazado = false;
        muestraFechaInicioGrupo = true;
        muestraFechaFinGrupo = false;
        muestraRemesas = false;
        muestraTotalFacturado = false;
        muestraProveedor = false;

        facturasFiltradas = new ArrayList<Facturavista>();

    }

    public boolean isMuestraFechaInicioGrupo() {
        return muestraFechaInicioGrupo;
    }

    public void setMuestraFechaInicioGrupo(boolean muestraFechaInicioGrupo) {
        this.muestraFechaInicioGrupo = muestraFechaInicioGrupo;
    }

    public boolean isMuestraFechaFinGrupo() {
        return muestraFechaFinGrupo;
    }

    public void setMuestraFechaFinGrupo(boolean muestraFechaFinGrupo) {
        this.muestraFechaFinGrupo = muestraFechaFinGrupo;
    }

    public boolean isMuestraRemesas() {
        return muestraRemesas;
    }

    public void setMuestraRemesas(boolean muestraRemesas) {
        this.muestraRemesas = muestraRemesas;
    }

    public boolean isMuestraTotalFacturado() {
        return muestraTotalFacturado;
    }

    public void setMuestraTotalFacturado(boolean muestraTotalFacturado) {
        this.muestraTotalFacturado = muestraTotalFacturado;
    }

    public boolean isMuestraProveedor() {
        return muestraProveedor;
    }

    public void setMuestraProveedor(boolean muestraProveedor) {
        this.muestraProveedor = muestraProveedor;
    }

    public boolean isSeleccionadoTodo() {
        return seleccionadoTodo;
    }

    public void setSeleccionadoTodo(boolean seleccionadoTodo) {
        this.seleccionadoTodo = seleccionadoTodo;
    }

    public Map<String, String> getFilterMap() {
        return filterMap;
    }

    public void setFilterMap(Map<String, String> filterMap) {
        this.filterMap = filterMap;
    }

    public Facturavista[] getFacturasSeleccionadas() {
        return facturasSeleccionadas;
    }

    public void setFacturasSeleccionadas(Facturavista[] facturasSeleccionadas) {
        this.facturasSeleccionadas = facturasSeleccionadas;
    }

    public Set<Factura> getFacturaLista(Facturavista[] fac) {
        final Facturavista[] fa = fac;
        List<Factura> f = new AbstractList<Factura>() {
            @Override
            public Factura get(int index) {
                return facFacade.find(fa[index].getIdfactura());
            }

            @Override
            public int size() {
                return fa.length;
            }
        };
        return new HashSet<Factura>(f);
    }

    public FacturaVista() {
    }

    public DataModel<Facturavista> getFacturas() {
        if (facturas == null) {
            facturas = new FacturaDataModel(facturaFacade.findAll());
        }
        return facturas;
    }

    public void seleccionaTodo() {
        if (!seleccionadoTodo) {
            List<Facturavista> fv = facturaFacade.load(0, Integer.MAX_VALUE, "", SortOrder.UNSORTED, filterMap);
            facturasSeleccionadas = fv.toArray(new Facturavista[fv.size()]);
        } else {
            facturasSeleccionadas = null;
        }
        seleccionadoTodo = !seleccionadoTodo;
    }

    public List<Facturavista> getFacturasFiltradas() {
        return facturasFiltradas;
    }

    public boolean isMuestraFechasPagoAplazado() {
        return muestraFechasPagoAplazado;
    }

    public void setMuestraFechasPagoAplazado(boolean muestraFechasPagoAplazado) {
        this.muestraFechasPagoAplazado = muestraFechasPagoAplazado;
    }

    public void setFacturasFiltradas(List<Facturavista> facturasFiltradas) {
        this.facturasFiltradas = facturasFiltradas;
    }

    public boolean isMuestraIdFactura() {
        return muestraIdFactura;
    }

    public void setMuestraIdFactura(boolean muestraIdFactura) {
        this.muestraIdFactura = muestraIdFactura;
    }

    public boolean isMuestraRazonSocial() {
        return muestraRazonSocial;
    }

    public void setMuestraRazonSocial(boolean muestraRazonSocial) {
        this.muestraRazonSocial = muestraRazonSocial;
    }

    public boolean isMuestraAlumno() {
        return muestraAlumno;
    }

    public void setMuestraAlumno(boolean muestraAlumno) {
        this.muestraAlumno = muestraAlumno;
    }

    public boolean isMuestraImporte() {
        return muestraImporte;
    }

    public void setMuestraImporte(boolean muestraImporte) {
        this.muestraImporte = muestraImporte;
    }

    public boolean isMuestraGrupo() {
        return muestraGrupo;
    }

    public void setMuestraGrupo(boolean muestraGrupo) {
        this.muestraGrupo = muestraGrupo;
    }

    public boolean isMuestraFechaMatricula() {
        return muestraFechaMatricula;
    }

    public void setMuestraFechaMatricula(boolean muestraFechaMatricula) {
        this.muestraFechaMatricula = muestraFechaMatricula;
    }

    public boolean isMuestraBaja() {
        return muestraBaja;
    }

    public void setMuestraBaja(boolean muestraBaja) {
        this.muestraBaja = muestraBaja;
    }

    public boolean isMuestraPagoPosterior() {
        return muestraPagoPosterior;
    }

    public void setMuestraPagoPosterior(boolean muestraPagoPosterior) {
        this.muestraPagoPosterior = muestraPagoPosterior;
    }

    public boolean isMuestraCcc() {
        return muestraCcc;
    }

    public void setMuestraCcc(boolean muestraCcc) {
        this.muestraCcc = muestraCcc;
    }

    public boolean isMuestraFechaEstado() {
        return muestraFechaEstado;
    }

    public void setMuestraFechaEstado(boolean muestraFechaEstado) {
        this.muestraFechaEstado = muestraFechaEstado;
    }

    public boolean isMuestraFechaCreacionFactura() {
        return muestraFechaCreacionFactura;
    }

    public void setMuestraFechaCreacionFactura(boolean muestraFechaCreacionFactura) {
        this.muestraFechaCreacionFactura = muestraFechaCreacionFactura;
    }

    public boolean isMuestraIdMatricula() {
        return muestraIdMatricula;
    }

    public void setMuestraIdMatricula(boolean muestraIdMatricula) {
        this.muestraIdMatricula = muestraIdMatricula;
    }

    public boolean isMuestraPagoAplazado() {
        return muestraPagoAplazado;
    }

    public void setMuestraPagoAplazado(boolean muestraPagoAplazado) {
        this.muestraPagoAplazado = muestraPagoAplazado;
    }

    public boolean isMuestraFechasPagoPosterior() {
        return muestraFechasPagoPosterior;
    }

    public void setMuestraFechasPagoPosterior(boolean muestraFechasPagoPosterior) {
        this.muestraFechasPagoPosterior = muestraFechasPagoPosterior;
    }

    public boolean isMuestraEstadoFactura() {
        return muestraEstadoFactura;
    }

    public void setMuestraEstadoFactura(boolean muestraEstadoFactura) {
        this.muestraEstadoFactura = muestraEstadoFactura;
    }

    public boolean isMuestraNumFila() {
        return muestraNumFila;
    }

    public void setMuestraNumFila(boolean muestraNumFila) {
        this.muestraNumFila = muestraNumFila;
    }

    public boolean isMuestraPuedeGenerarRemesa() {
        return muestraPuedeGenerarRemesa;
    }

    public void setMuestraPuedeGenerarRemesa(boolean muestraPuedeGenerarRemesa) {
        this.muestraPuedeGenerarRemesa = muestraPuedeGenerarRemesa;
    }

    public void cambiarEstadoFac(int estado) {
        boolean valido = true;
        if (facturasSeleccionadas == null || facturasSeleccionadas.length == 0) {
            UtilidadesVista.Mensaje("No se pueden procesar las Facturas puesto que no se han seleccionado facturas.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        if (valido) {
            Set<Factura> fac = getFacturaLista(facturasSeleccionadas);
            String fac_cob = "";
            String fab_e = "";
            for (Factura f : fac) {
                if (f.getUltimoEstado().getEstado().getId().equals(3)) {
                    fac_cob += f.getId() + " ";
                }
                if (estado == 4) {
                    if (f.getUltimoEstado().getEstado().getId().equals(4)) {
                        fab_e += f.getId() + " ";
                    }
                }
            }
            if (!fac_cob.equals("")) {
                UtilidadesVista.Mensaje("No se pueden procesar las Facturas puesto que ya existen facturas en estado 'Cobrada': " + fac_cob, FacesMessage.SEVERITY_ERROR);
                valido = false;
            }
            if (!fab_e.equals("")) {
                UtilidadesVista.Mensaje("No se pueden procesar las Facturas puesto que ya existen facturas en estado 'Gestión Remesa': " + fab_e, FacesMessage.SEVERITY_ERROR);
                valido = false;
            }
            if (!valido) {
                return;
            }
            for (Factura factura : fac) {
                factura.getUltimoEstado().setEstado(new FacturaEstado(estado));
                factura = facFacade.edit(factura);
            }
            UtilidadesVista.Mensaje("Se han pasado todas las facturas seleccionadas a estado 'Cobrada'.", FacesMessage.SEVERITY_INFO);

            facturasSeleccionadas = null;

            if (desdeImporteFac != null || hastaImporteFac != null) {
                facturas = new FacturaDataModel(facturaFacade.findAll(desdeImporteFac, hastaImporteFac));
            } else if (desdeFechaEstadoFac != null || hastaFechaEstadoFac != null) {
                facturas = new FacturaDataModel(facturaFacade.findAll(desdeFechaEstadoFac, hastaFechaEstadoFac));
            } else {
                facturas = new FacturaDataModel(facturaFacade.findAll());
            }
        }
    }

    public void generarRemesa() {
        boolean valido = true;
        boolean tieneCCC = false;
        try {
            List<EmpresaMatriculaCcc> emc = VariablesSistema.proveedor_principal.getEmpresa().getEmpresaMatricula().getEmpresaMatriculaCccList();
            for (EmpresaMatriculaCcc empresaMatriculaCcc : emc) {
                if(empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() == 20){
                    tieneCCC = true;
                }
            }
        } catch (Exception e) {
        }
        
        if(!tieneCCC){
        UtilidadesVista.Mensaje("No se pueden procesar las Facturas puesto que el Proveedor Principal de la aplicación no tiene asociado un CCC.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        
        if (facturasSeleccionadas == null || facturasSeleccionadas.length == 0) {
            UtilidadesVista.Mensaje("No se pueden procesar las Facturas puesto que no se han seleccionado facturas.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        if (valido) {
            String fac_cob = "";
            for (Facturavista f : facturasSeleccionadas) {
                if (f.getPuedeGenerarRemesa().equals("No")) {
                    fac_cob += f.getIdfactura() + " ";
                }
            }
            if (!fac_cob.equals("")) {
                UtilidadesVista.Mensaje("No se pueden procesar las Facturas puesto que no cumplen los requisitos para añadirse a una remesa: " + fac_cob, FacesMessage.SEVERITY_ERROR);
                valido = false;
            }
        }
        if (valido) {
            Set<Factura> fac = getFacturaLista(facturasSeleccionadas);

            remesaSeleccionada = new Remesa();
            remesaSeleccionada.setFacturaList(new ArrayList<Factura>(fac));
            remesaSeleccionada.setFecha(new Date());
            remesaSeleccionada.setAgrupada(true);

            RequestContext.getCurrentInstance().execute("dialogoRemesa.show()");
        }

    }

    public void seleccionarRemesa(String remesa) throws IOException {
        remesaSeleccionada = remesaFacade.find(remesa.trim().split(" ")[0]);
        File f = new File(VariablesSistema.ruta_absoluta_proyecto + "/docs/temp/" + remesaSeleccionada.getArchivo());
        if (!f.exists()) {
            ExportarRemesa.generarRemesa(remesaSeleccionada);
        }
        RequestContext.getCurrentInstance().execute("dialogoRemesa.show()");
    }

    public void guardaRemesa() throws IOException {
        boolean valido = true;
        if (!Fecha.getFechaDiaMesAnio(remesaSeleccionada.getFechaCobro()).equals(Fecha.getFechaDiaMesAnio(remesaSeleccionada.getFecha()))
                && remesaSeleccionada.getFechaCobro().before(remesaSeleccionada.getFecha())) {
            UtilidadesVista.Mensaje("La Fecha de Cobro debe de ser posterior a la fecha de creación de la remesa.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        if (remesaSeleccionada.getFacturaList().isEmpty()) {
            UtilidadesVista.Mensaje("La remesa debe de tener facturas asociadas.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        if (valido) {
            boolean editar = true;
            if (remesaSeleccionada.getId() == null) {
                remesaSeleccionada.setId("remesa_" + Fecha.getMilisec(new Date()));
                editar = false;
            }
            ExportarRemesa.generarRemesa(remesaSeleccionada);
            if (editar) {
                remesaSeleccionada = remesaFacade.edit(remesaSeleccionada);
            } else {
                remesaSeleccionada.setCobrada(false);
                remesaSeleccionada = remesaFacade.create(remesaSeleccionada);
            }
            for (Factura f : remesaSeleccionada.getFacturaList()) {
                FacturaHistoricoEstado fhe = new FacturaHistoricoEstado(new FacturaHistoricoEstadoPK(f.getId(), new Date()));
                fhe.setEstado(fef.find(4));
                f.getFacturaHistoricoEstadoList().add(fhe);
                if (f.getRemesaList() == null) {
                    f.setRemesaList(new ArrayList<Remesa>());
                }
                f.getRemesaList().add(remesaSeleccionada);
                f = facFacade.edit(f);
            }

            UtilidadesVista.Mensaje("La remesa ha sido guardada con éxito.", FacesMessage.SEVERITY_INFO);
            if (desdeImporteFac != null || hastaImporteFac != null) {
                facturas = new FacturaDataModel(facturaFacade.findAll(desdeImporteFac, hastaImporteFac));
            } else if (desdeFechaEstadoFac != null || hastaFechaEstadoFac != null) {
                facturas = new FacturaDataModel(facturaFacade.findAll(desdeFechaEstadoFac, hastaFechaEstadoFac));
            } else {
                facturas = new FacturaDataModel(facturaFacade.findAll());
            }
            facturasSeleccionadas = null;
            facturasFiltradas = null;
            RequestContext.getCurrentInstance().execute("filtra()");
            RequestContext.getCurrentInstance().execute("dialogoRemesa.show()");
        } else {
            RequestContext.getCurrentInstance().execute("dialogoRemesa.show()");
        }
    }

    public void eliminaRemesa() {
        for (Factura f : remesaSeleccionada.getFacturaList()) {
            if (f.getUltimoEstado().getEstado().getId().equals(4)) {
                f.getFacturaHistoricoEstadoList().remove(f.getUltimoEstado());
                f = facFacade.edit(f, false);
            }
        }

        remesaFacade.remove(remesaSeleccionada);
        UtilidadesVista.Mensaje("La remesa " + remesaSeleccionada.getId() + " ha sido eliminada.", FacesMessage.SEVERITY_WARN);
        if (desdeImporteFac != null || hastaImporteFac != null) {
            facturas = new FacturaDataModel(facturaFacade.findAll(desdeImporteFac, hastaImporteFac));
        } else if (desdeFechaEstadoFac != null || hastaFechaEstadoFac != null) {
            facturas = new FacturaDataModel(facturaFacade.findAll(desdeFechaEstadoFac, hastaFechaEstadoFac));
        } else {
            facturas = new FacturaDataModel(facturaFacade.findAll());
        }
        facturasSeleccionadas = null;
        facturasFiltradas = null;
        RequestContext.getCurrentInstance().execute("filtra()");
        RequestContext.getCurrentInstance().execute("dialogoRemesa.hide()");
    }

    public void cobraRemesa() {
        boolean valido = true;
        if (!Fecha.getFechaDiaMesAnio(remesaSeleccionada.getFechaCobro()).equals(Fecha.getFechaDiaMesAnio(remesaSeleccionada.getFecha()))
                && remesaSeleccionada.getFechaCobro().before(remesaSeleccionada.getFecha())) {
            UtilidadesVista.Mensaje("La Fecha de Cobro debe de ser posterior a la fecha de creación de la remesa.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        if (remesaSeleccionada.getFacturaList().isEmpty()) {
            UtilidadesVista.Mensaje("La remesa debe de tener facturas asociadas.", FacesMessage.SEVERITY_ERROR);
            valido = false;
        }
        if (valido) {
            remesaFacade.cobrar(remesaSeleccionada, facFacade);
            UtilidadesVista.Mensaje("La remesa ha sido cobrada con éxito.", FacesMessage.SEVERITY_INFO);
            if (desdeImporteFac != null || hastaImporteFac != null) {
                facturas = new FacturaDataModel(facturaFacade.findAll(desdeImporteFac, hastaImporteFac));
            } else if (desdeFechaEstadoFac != null || hastaFechaEstadoFac != null) {
                facturas = new FacturaDataModel(facturaFacade.findAll(desdeFechaEstadoFac, hastaFechaEstadoFac));
            } else {
                facturas = new FacturaDataModel(facturaFacade.findAll());
            }
            facturasSeleccionadas = null;
            facturasFiltradas = null;
            RequestContext.getCurrentInstance().execute("filtra()");
            RequestContext.getCurrentInstance().execute("dialogoRemesa.hide()");
        } else {
            RequestContext.getCurrentInstance().execute("dialogoRemesa.show()");
        }

    }

    public void recargarDatos() {
        if (desdeImporteFac != null || hastaImporteFac != null) {
            facturas = new FacturaDataModel(facturaFacade.findAll(desdeImporteFac, hastaImporteFac));
        } else if (desdeFechaEstadoFac != null || hastaFechaEstadoFac != null) {
            facturas = new FacturaDataModel(facturaFacade.findAll(desdeFechaEstadoFac, hastaFechaEstadoFac));
        } else {
            facturas = new FacturaDataModel(facturaFacade.findAll());
        }
    }

    public void aniadirFacturaRemesa() throws IOException {
        String fac_cob = "";
        Facturavista f = facturaFacade.find(facturaAniadir);

        if (f == null) {
            UtilidadesVista.Mensaje("No existe la factura " + facturaAniadir, FacesMessage.SEVERITY_ERROR);
            return;
        }

        if (f.getPuedeGenerarRemesa().equals("No")) {
            fac_cob += f.getIdfactura() + " ";
        }
        if (!fac_cob.equals("")) {
            UtilidadesVista.Mensaje("No se pueden procesar las Facturas puesto que no cumplen los requisitos para añadirse a una remesa: " + fac_cob, FacesMessage.SEVERITY_ERROR);
            return;
        }

        Factura fac = facFacade.find(facturaAniadir);

        for (Factura fa : remesaSeleccionada.getFacturaList()) {
            if (fa.getId().equals(fac.getId())) {
                RequestContext.getCurrentInstance().execute("dialogoRemesa.show()");
                RequestContext.getCurrentInstance().execute("dlgAniadirFacturaRemesa.hide()");
                return;
            }
        }

        remesaSeleccionada.getFacturaList().add(fac);

        FacturaHistoricoEstado fhe = new FacturaHistoricoEstado(new FacturaHistoricoEstadoPK(fac.getId(), new Date()));
        fhe.setEstado(fef.find(4));
        fac.getFacturaHistoricoEstadoList().add(fhe);
        if (fac.getRemesaList() == null) {
            fac.setRemesaList(new ArrayList<Remesa>());
        }
        fac.getRemesaList().add(remesaSeleccionada);
        fac = facFacade.edit(fac);
        
        guardaRemesa();

        RequestContext.getCurrentInstance().execute("dialogoRemesa.show()");
        RequestContext.getCurrentInstance().execute("dlgAniadirFacturaRemesa.hide()");

    }
}
