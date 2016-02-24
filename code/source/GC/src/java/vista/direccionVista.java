/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import bean.CpLocalidadFacade;
import bean.DireccionFacade;
import bean.TipoViaFacade;
import datos.CpLocalidad;
import datos.Direccion;
import datos.TipoVia;
import datosVista.CpLocalidadProvincia;
import datosVistaFacade.CpLocalidadProvinciaFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lazyDataModel.LazyCPLocDataModel;
import org.primefaces.model.LazyDataModel;

/**
 *
 *dai
 */
@ManagedBean(name = "direccionVista")
@SessionScoped
public class direccionVista implements Serializable {

    @EJB
    private TipoViaFacade tipovFacade;
    @EJB
    private CpLocalidadProvinciaFacade cplFacade;
    //@EJB
    //private DireccionFacade dirFacade;
    private List<TipoVia> tiposVia;
    private LazyDataModel<CpLocalidadProvincia> CpLocalidades;
    //private List<Direccion> direcciones;

    public List<TipoVia> getTiposVia() {
        if (tiposVia == null) {
            tiposVia = tipovFacade.findAll();
        }
        return tiposVia;
    }

    public void setTiposVia(List<TipoVia> tiposVia) {
        this.tiposVia = tiposVia;
    }

    public LazyDataModel<CpLocalidadProvincia> getCpLocalidades() {
        if (CpLocalidades == null) {
            CpLocalidades = new LazyCPLocDataModel(cplFacade);
        }
        return CpLocalidades;
    }
/*
    public List<Direccion> getDirecciones() {
        if(direcciones==null){
            direcciones = dirFacade.findAll();
        }
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }*/
}
