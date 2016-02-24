/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import datos.Horario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 *
 */
@Stateless
public class HorarioFacade extends AbstractFacade<Horario> {
    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HorarioFacade() {
        super(Horario.class);
    }
/**
 * Devuelve los horarios a mostrar, filtrando los horarios especiales "-1", éstos
 * horarios se usan para ver las horas de tutoría de un tutor en un grupo.
 * 
 * @return 
 */
    @Override
    public List<Horario> findAll() {
        List<Horario> h = super.findAll();
        int borrar = -1;
        for (int i = 0; i < h.size(); i++) {
            if(h.get(i).getId() < 0){
                borrar = i;
            }
            
        }
        h.remove(borrar);
        return  h;
    }
    
    
    
}