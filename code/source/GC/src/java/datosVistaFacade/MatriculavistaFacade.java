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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.primefaces.model.SortOrder;

/**
 *
 *
 */
@Stateless
public class MatriculavistaFacade extends AbstractFacade<Matriculavista> {

    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MatriculavistaFacade() {
        super(Matriculavista.class);
    }
/**
 * Carga matrículas de forma Lazy en tablas de PrimeFaces
 * 
 * 
 * @param first
 * @param count
 * @param sortField
 * @param sortOrder
 * @param filters
 * @param start - Fecha de Inicio para Rango de Inicio/Fin Grupos
 * @param end - Fecha de Fin para Rango de Inicio/Fin Grupos
 * @param inicio_fin - Determina si el rango a filtrar es de Inicio (TRUE) o Fin (False)
 * @param nif_ec_filtrar - Determina si sólo se muestras las matrículas de una E. Comercial con NIF determinado
 * (Para filtrar comerciales).
 * @return 
 */
    public List<Matriculavista> load(int first, int count, String sortField,
            SortOrder sortOrder, Map<String, String> filters, Date start, Date end, boolean inicio_fin, String nif_ec_filtrar) {
        Set<Map.Entry<String, String>> error_filter = new HashSet<Map.Entry<String, String>>();
        javax.persistence.criteria.CriteriaBuilder builder =
                getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = builder.createQuery();
        Root root = cq.from(Matriculavista.class);
        cq.select(root);
        if (sortField != null) {
            try {
                if (sortOrder == SortOrder.ASCENDING) {
                    cq.orderBy(builder.asc(root.get(sortField)));
                } else if (sortOrder == SortOrder.DESCENDING) {
                    cq.orderBy(builder.desc(root.get(sortField)));
                }
                sortOrderUsed = true;
            } catch (Exception e) {
            }
        }
        if (filters != null) {
            Set<Map.Entry<String, String>> entries = filters.entrySet();
            ArrayList<Predicate> predicatesList =
                    new ArrayList<Predicate>(entries.size());
            for (Map.Entry<String, String> filter : entries) {
                try {
                    Predicate p = null;
                    if (filter.getValue().contains("~~")) {
                        for (String value : filter.getValue().split("~~")) {
                            if (p == null) {
                                p = builder.like(builder.upper(builder.concat(root.get(filter.getKey()), "")),
                                        "%" + value + "%");
                            } else {
                                p = builder.or(p, builder.like(builder.upper(builder.concat(root.get(filter.getKey()), "")),
                                        "%" + value + "%"));
                            }
                        }
                    } else {
                        p = builder.like(builder.upper(builder.concat(root.get(filter.getKey()), "")),
                                "%" + filter.getValue().toUpperCase() + "%");
                    }
                    if (p != null) {
                        predicatesList.add(p);
                    }
                } catch (Exception e) {
                    error_filter.add(filter);
                }
            }
            if (start != null || end != null) {
                if (!inicio_fin) {
                    predicatesList.add(builder.between(root.get(Matriculavista_.fFin), start, end));
                } else {
                    predicatesList.add(builder.between(root.get(Matriculavista_.fInicio), start, end));
                }
            }
            if (!nif_ec_filtrar.equals("")) {
                predicatesList.add(builder.like(builder.upper(builder.concat(root.get(Matriculavista_.comercial), "")),
                                        "%~~(" + nif_ec_filtrar + ")~~%"));
            }
            cq.where(predicatesList.<Predicate>toArray(
                    new Predicate[predicatesList.size()]));
        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setFirstResult(first);
        q.setMaxResults(count);
        for (Map.Entry<String, String> entry : error_filter) {
            System.out.println("ERROR: " + entry.getKey() + " - " + entry.getValue());
        }
        return q.getResultList();
    }

    /**
 * Cuenta matrículas de forma Lazy en tablas de PrimeFaces
 * 
 * 
 * @param first
 * @param count
 * @param sortField
 * @param sortOrder
 * @param filters
 * @param start - Fecha de Inicio para Rango de Inicio/Fin Grupos
 * @param end - Fecha de Fin para Rango de Inicio/Fin Grupos
 * @param inicio_fin - Determina si el rango a filtrar es de Inicio (TRUE) o Fin (False)
 * @param nif_ec_filtrar - Determina si sólo se muestras las matrículas de una E. Comercial con NIF determinado
 * (Para filtrar comerciales).
 * @return 
 */
    public int count(Map<String, String> filters, Date start, Date end, boolean inicio_fin, String nif_ec_filtrar) {
        Set<Map.Entry<String, String>> error_filter = new HashSet<Map.Entry<String, String>>();
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root rt = cq.from(Matriculavista.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.criteria.CriteriaBuilder builder =
                getEntityManager().getCriteriaBuilder();

        if (filters != null) {
            Set<Map.Entry<String, String>> entries = filters.entrySet();
            ArrayList<Predicate> predicatesList =
                    new ArrayList<Predicate>(entries.size());
            for (Map.Entry<String, String> filter : entries) {
                try {
                    Predicate p = null;
                    if (filter.getValue().contains("~~")) {
                        for (String value : filter.getValue().split("~~")) {
                            if (p == null) {
                                p = builder.like(builder.upper(builder.concat(rt.get(filter.getKey()), "")),
                                        "%" + value + "%");
                            } else {
                                p = builder.or(p, builder.like(builder.upper(builder.concat(rt.get(filter.getKey()), "")),
                                        "%" + value + "%"));
                            }
                        }
                    } else {
                        p = builder.like(builder.upper(builder.concat(rt.get(filter.getKey()), "")),
                                "%" + filter.getValue().toUpperCase() + "%");
                    }
                    if (p != null) {
                        predicatesList.add(p);
                    }
                } catch (Exception e) {
                    error_filter.add(filter);
                }
            }
            if (start != null || end != null) {
                if (!inicio_fin) {
                    predicatesList.add(builder.between(rt.get(Matriculavista_.fFin), start, end));
                } else {
                    predicatesList.add(builder.between(rt.get(Matriculavista_.fInicio), start, end));
                }
            }
            if (!nif_ec_filtrar.equals("")) {
                predicatesList.add(builder.like(builder.upper(builder.concat(rt.get(Matriculavista_.comercial), "")),
                                        "%~~(" + nif_ec_filtrar + ")~~%"));
            }
            cq.where(predicatesList.<Predicate>toArray(
                    new Predicate[predicatesList.size()]));
        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        for (Map.Entry<String, String> entry : error_filter) {
            System.out.println("ERROR: " + entry.getKey() + " - " + entry.getValue());
        }

        return ((Long) q.getSingleResult()).intValue();
    }
}
