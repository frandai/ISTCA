/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import datos.AccionFormativaGrupo;
import datos.AccionFormativaGrupoFundacion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 *
 */
@Stateless
public class AccionFormativaGrupoFundacionFacade extends AbstractFacade<AccionFormativaGrupoFundacion> {
    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccionFormativaGrupoFundacionFacade() {
        super(AccionFormativaGrupoFundacion.class);
    }
    
}
