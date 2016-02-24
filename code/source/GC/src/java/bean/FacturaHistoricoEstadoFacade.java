/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import datos.FacturaHistoricoEstado;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 *
 */
@Stateless
public class FacturaHistoricoEstadoFacade extends AbstractFacade<FacturaHistoricoEstado> {
    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacturaHistoricoEstadoFacade() {
        super(FacturaHistoricoEstado.class);
    }
    
}