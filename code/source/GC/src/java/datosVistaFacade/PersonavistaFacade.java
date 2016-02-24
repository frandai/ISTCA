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
public class PersonavistaFacade extends AbstractFacade<Personavista> {
    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonavistaFacade() {
        super(Personavista.class);
    }
    
}
