/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datosVistaFacade;

import bean.*;
import datosVista.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 *
 */
@Stateless
public class AccionformativavistaFacade extends AbstractFacade<Accionformativavista> {
    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccionformativavistaFacade() {
        super(Accionformativavista.class);
    }
    
}
