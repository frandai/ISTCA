/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import datos.EmpresaMatriculaCcc;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 *
 */
@Stateless
public class EmpresaMatriculaCccFacade extends AbstractFacade<EmpresaMatriculaCcc> {
    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpresaMatriculaCccFacade() {
        super(EmpresaMatriculaCcc.class);
    }
    
}
