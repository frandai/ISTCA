/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import datos.AlumnoNivelEstudio;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 *
 */
@Stateless
public class AlumnoNivelEstudioFacade extends AbstractFacade<AlumnoNivelEstudio> {
    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlumnoNivelEstudioFacade() {
        super(AlumnoNivelEstudio.class);
    }
    
}
