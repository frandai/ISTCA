/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import datos.AccionFormativa;
import datos.AccionFormativa_;
import datos.Grupo;
import datos.GrupoPK;
import datos.GrupoPK_;
import datos.Grupo_;
import java.util.ArrayList;
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
public class GrupoFacade extends AbstractFacade<Grupo> {

    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GrupoFacade() {
        super(Grupo.class);
    }
/**
 * Devuelve el último ID de grupo insertado dada cierto ID de acción formativa, para poder añadir el siguiente ID de grupo correlativo.
 * @param idAccionFormativa
 * @return 
 */
    public Integer findLastCodGrupoAccionFormativa(Integer idAccionFormativa) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<Grupo> gr = cq.from(Grupo.class);
        cq.select(gr);
        cq.where(getEntityManager().getCriteriaBuilder().equal(gr.get(Grupo_.accionFormativa1), new AccionFormativa(idAccionFormativa)));
        List<Grupo> l = (List<Grupo>) getEntityManager().createQuery(cq).getResultList();
        int i = 0;
        for (Grupo grupo : l) {
            if (i == 0 || i < grupo.getGrupoPK().getId()) {
                i = grupo.getGrupoPK().getId();
            }
        }
        if (i < 0) {
            i = 0;
        }
        return i;
    }
 /**
     * Busca Grupo por valores en Observaciones (Usada para migración de BD antigua)
     * @param string
     * @return 
     * 
     */
    public Grupo findCodGrupoObserv(String string) {
        javax.persistence.criteria.CriteriaBuilder builder =
                getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<Grupo> grupo = cq.from(Grupo.class);
        cq.select(grupo);

        cq.where(builder.like(builder.upper(grupo.get(Grupo_.observaciones)), "%/" + string + "/%"));

        if (getEntityManager().createQuery(cq).getResultList().isEmpty()) {
            return null;
        } else {
            return (Grupo) getEntityManager().createQuery(cq).getResultList().get(0);
        }


    }
}
