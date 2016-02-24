/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import datos.AccionFormativa;
import datos.AccionFormativa_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Root;

/**
 *
 *
 */
@Stateless
public class AccionFormativaFacade extends AbstractFacade<AccionFormativa> {

    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccionFormativaFacade() {
        super(AccionFormativa.class);
    }

    /**
     * Devuelve la última Acción Formativa en el Sistema, para poder añadir la siguiente AF.
     * @return 
     */
    public Integer findLastCodAccionFormativa() {
        try {
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            Root<AccionFormativa> af = cq.from(AccionFormativa.class);
            cq.select(af);
            cq.orderBy(getEntityManager().getCriteriaBuilder().desc(af.get(AccionFormativa_.id)));
            AccionFormativa l = (AccionFormativa) getEntityManager().createQuery(cq).getResultList().get(0);
            return l.getId();
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Busca AF por valores en Observaciones (Usada para migración de BD antigua)
     * @param string
     * @return 
     * 
     */
    public AccionFormativa findCodAFObserv(String string) {
        javax.persistence.criteria.CriteriaBuilder builder =
                getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<AccionFormativa> af = cq.from(AccionFormativa.class);
        cq.select(af);
        
        cq.where(builder.like(builder.upper(af.get(AccionFormativa_.observaciones)), "%/"+string+"/%"));
        
        if(getEntityManager().createQuery(cq).getResultList().isEmpty()){
            return null;
        } else {
            return (AccionFormativa) getEntityManager().createQuery(cq).getResultList().get(0);
        }
        
        
    }
}
