/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import datos.MatriculaComercial;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 *
 */
@Stateless
public class MatriculaComercialFacade extends AbstractFacade<MatriculaComercial> {
    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MatriculaComercialFacade() {
        super(MatriculaComercial.class);
    }
    
}
