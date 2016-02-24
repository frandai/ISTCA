/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import datos.Factura;
import datos.FacturaEstado;
import datos.FacturaHistoricoEstado;
import datos.FacturaHistoricoEstadoPK;
import datos.Remesa;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 *
 */
@Stateless
public class RemesaFacade extends AbstractFacade<Remesa> {
    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RemesaFacade() {
        super(Remesa.class);
    }

/**
 * Cobra las Facturas asociadas a la Remesa (las pone en estado "Cobrado").
 * 
 * @param remesaSeleccionada
 * @param facFacade 
 */
    public void cobrar(Remesa remesaSeleccionada, FacturaFacade facFacade) {
        for (Factura f : remesaSeleccionada.getFacturaList()) {
            if(f.getUltimoEstado().getEstado().getId().equals(4)){
                FacturaHistoricoEstado fhh = new FacturaHistoricoEstado(new FacturaHistoricoEstadoPK(f.getId(), new Date()));
                fhh.setEstado(new FacturaEstado(3));
                f.getFacturaHistoricoEstadoList().add(fhh);
                f = facFacade.edit(f);
            }
        }
        remesaSeleccionada.setCobrada(true);
        edit(remesaSeleccionada);
    }
    
}
