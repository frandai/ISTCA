/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import datos.Tutor;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 *
 */
@Stateless
public class TutorFacade extends AbstractFacade<Tutor> {
    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TutorFacade() {
        super(Tutor.class);
    }
    
}
