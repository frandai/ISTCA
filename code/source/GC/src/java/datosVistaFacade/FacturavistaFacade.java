/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datosVistaFacade;

import bean.*;
import datos.Grupo;
import datos.Grupo_;
import datos.Matricula;
import datos.Matricula_;
import datosVista.*;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 *
 */
@Stateless
public class FacturavistaFacade extends AbstractFacade<Facturavista> {

    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacturavistaFacade() {
        super(Facturavista.class);
    }

    /**
     * Función para buscar facturas en un rango de Importes
     *
     * @param desdeImporteFac rango Desde de importe de facturas
     * @param hastaImporteFac rango Hasta de importe de facturas
     * @return
     */
    public List<Facturavista> findAll(Double desdeImporteFac, Double hastaImporteFac) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Facturavista> criteria = cb.createQuery(Facturavista.class);
        Root<Facturavista> matriculaRoot = criteria.from(Facturavista.class);
        Predicate predicate = null;
        if (desdeImporteFac != null && hastaImporteFac == null) {
            predicate = cb.gt(matriculaRoot.get(Facturavista_.sumImportesFacturas), desdeImporteFac);
        }
        if (desdeImporteFac == null && hastaImporteFac != null) {
            predicate = cb.lt(matriculaRoot.get(Facturavista_.sumImportesFacturas), hastaImporteFac);
        }
        if (desdeImporteFac != null && hastaImporteFac != null) {
            predicate = cb.and(cb.gt(matriculaRoot.get(Facturavista_.sumImportesFacturas), desdeImporteFac), cb.lt(matriculaRoot.get(Facturavista_.sumImportesFacturas), hastaImporteFac));
        }
        if (predicate != null) {
            criteria.where(predicate);
        }
        criteria.select(matriculaRoot);

        return getEntityManager().createQuery(criteria).getResultList();
    }
/**
     * Función para buscar facturas en un rango de Fecha de Creación
     *
     * @param desdeImporteFac rango Desde de Fecha de Creación
     * @param hastaImporteFac rango Hasta de Fecha de Creación
     * @return
     */
    public List<Facturavista> findAll(Date desdeImporteFac, Date hastaImporteFac) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Facturavista> criteria = cb.createQuery(Facturavista.class);
        Root<Facturavista> matriculaRoot = criteria.from(Facturavista.class);
        Predicate predicate = null;
        if (desdeImporteFac != null && hastaImporteFac == null) {
            predicate = cb.greaterThan(matriculaRoot.get(Facturavista_.fCreacionFac), desdeImporteFac);
        }
        if (desdeImporteFac == null && hastaImporteFac != null) {
            predicate = cb.lessThan(matriculaRoot.get(Facturavista_.fCreacionFac), hastaImporteFac);
        }
        if (desdeImporteFac != null && hastaImporteFac != null) {
            predicate = cb.and(cb.greaterThan(matriculaRoot.get(Facturavista_.fCreacionFac), desdeImporteFac), cb.lessThan(matriculaRoot.get(Facturavista_.fCreacionFac), hastaImporteFac));
        }
        if (predicate != null) {
            criteria.where(predicate);
        }
        criteria.select(matriculaRoot);

        return getEntityManager().createQuery(criteria).getResultList();
    }
}
