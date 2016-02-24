/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import datos.AlumnoGrupoCotizacion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 *
 */
@Stateless
public class AlumnoGrupoCotizacionFacade extends AbstractFacade<AlumnoGrupoCotizacion> {
    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlumnoGrupoCotizacionFacade() {
        super(AlumnoGrupoCotizacion.class);
    }
    
}
