/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import datos.Evento;
import datos.Evento_;
import datos.Grupo;
import datos.Grupo_;
import datos.Matricula;
import datos.Matricula_;
import datos.Persona;
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
import javax.persistence.criteria.SetJoin;
import util.Email;
import util.Fecha;
import util.descripcionEventos;

/**
 *
 *
 */
@Stateless
public class MatriculaFacade extends AbstractFacade<Matricula> {

    @PersistenceContext(unitName = "GC_DAIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MatriculaFacade() {
        super(Matricula.class);
    }

    @Override
    public Matricula edit(Matricula entity) {
        return this.edit(entity, null);
    }
/**
 * Gestiones a realizar cuando se edita una matrícula.
 * 
 * Si no existen eventos de Comunicación de Inicio/Fin/Seguimiento de Cursos, y está asociada la matrícula
 * a un grupo No Pendiente de Gestionar con un proveedor que genera seguimiento tutorial,
 * se crearán dischos eventos de comunicación, con fechas de vencimiento:
 * -Inicio: 2 días despues de inicio de curso
 * -Seguimiento: en la mitad del inicio y fin de curso
 * -Fin: 2 días despues del fin de curso
 * y con fechas de creación - La fecha actual.
 * 
 * Si se da de baja una matrícula, y ya se ha enviado la autorización a GrupoIWI:
 * -se enviará un e-mail de control informando de ésto a Inma y Carmen, 
 * para controlar los servicios en GrupoIWI.
 * -Si anteiormente estaba de baja, y se ha cambiado a Alta, y ya se ha enviado la autorización a GrupoIWI:
 * se enviará un e-mail con dicha explicación.
 * 
 * Si la matrícula tiene un grupo diferente de Pendiente de Gestionar, 
 * o se le cambia el precio o se pone en modo Baja, se enviará la comunicación a GrupoIWI de alta de matrícula,
 * para que gestione los albaranes de liquidación en GrupoIWI.
 * 
 * Si se rellena el Importe a Bonificar, también se rellenará la fecha de relleno de importe a bonificar con importe actual.
 * 
 * Si no tiene Dirección de Envío, se borrará dicho objeto.
 * 
 * 
 * @param entity
 * @param p
 * @return 
 */
    public Matricula edit(Matricula entity, Persona p) {
        if (p != null) {
            if (entity.getGrupo().getProveedor1().isGeneraSeguimientoTutorial()
                    && !entity.getGrupo().getPendienteDeGestionar() && entity.getGrupo().getFFin().after(new Date())) {
                boolean existe_evento_INI_CUR = false;
                boolean existe_evento_SEG_CUR = false;
                boolean existe_evento_FIN_CUR = false;
                for (Evento ev : entity.getEventoList()) {
                    if (descripcionEventos.getTipoEvento(ev) == descripcionEventos.COMUNICACION_INICIO_CURSO) {
                        existe_evento_INI_CUR = true;
                    }
                    if (descripcionEventos.getTipoEvento(ev) == descripcionEventos.COMUNICACION_SEGUIMIENTO_CURSO) {
                        existe_evento_SEG_CUR = true;
                    }
                    if (descripcionEventos.getTipoEvento(ev) == descripcionEventos.COMUNICACION_FIN_CURSO) {
                        existe_evento_FIN_CUR = true;
                    }
                }
                if (!existe_evento_INI_CUR) {
                    Evento seguimientoTutorial = new Evento();
                    seguimientoTutorial.setCreador(p);
                    seguimientoTutorial.setMatricula(entity);
                    seguimientoTutorial.setFechaCreacion(entity.getGrupo().getFInicio());
                    seguimientoTutorial.setFechaVencimiento(Fecha.getFechaSetDias(entity.getGrupo().getFInicio(), 2));
                    descripcionEventos.configurarEvento(seguimientoTutorial, descripcionEventos.COMUNICACION_INICIO_CURSO);
                    entity.getEventoList().add(seguimientoTutorial);
                }
                if (!existe_evento_SEG_CUR) {
                    Evento seguimientoTutorial = new Evento();
                    seguimientoTutorial.setCreador(p);
                    seguimientoTutorial.setMatricula(entity);
                    seguimientoTutorial.setFechaCreacion(entity.getGrupo().getFInicio());
                    seguimientoTutorial.setFechaVencimiento(Fecha.getFechaSetDias(entity.getGrupo().getFInicio(), Fecha.getNumDiasEntre(entity.getGrupo().getFInicio(),entity.getGrupo().getFFin())/2));
                    descripcionEventos.configurarEvento(seguimientoTutorial, descripcionEventos.COMUNICACION_SEGUIMIENTO_CURSO);
                    entity.getEventoList().add(seguimientoTutorial);
                }
                if (!existe_evento_FIN_CUR) {
                    Evento seguimientoTutorial = new Evento();
                    seguimientoTutorial.setCreador(p);
                    seguimientoTutorial.setMatricula(entity);
                    seguimientoTutorial.setFechaCreacion(entity.getGrupo().getFInicio());
                    seguimientoTutorial.setFechaVencimiento(Fecha.getFechaSetDias(entity.getGrupo().getFFin(), 2));
                    descripcionEventos.configurarEvento(seguimientoTutorial, descripcionEventos.COMUNICACION_FIN_CURSO);
                    entity.getEventoList().add(seguimientoTutorial);
                }
            }

        }
        if (entity.getDireccionEnvio() != null && entity.getDireccionEnvio().getVia() == null) {
            entity.setDireccionEnvio(null);
        }
        if (entity.getImporteBonificar() != null && entity.getImporteBonificarFecha() == null) {
            entity.setImporteBonificarFecha(new Date());
        }
        return super.edit(entity);
    }

    /**
     * Busca Matrículas entre cierto Rango de Fecha de Fin de Grupo
     * 
     * @param start Fecha de Inicio para Rango
     * @param end Fecha de Fin para Rango
     * @return El listado de matrículas
     */
    public List<Matricula> findFFin(Date start, Date end) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Matricula> criteria = cb.createQuery(Matricula.class);
        Root<Matricula> matriculaRoot = criteria.from(Matricula.class);
        Join<Matricula, Grupo> matriculasGrupos = matriculaRoot.join(Matricula_.grupo);
        Predicate predicate = cb.between(matriculasGrupos.get(Grupo_.fFin), start, end);
        criteria.where(predicate);
        criteria.select(matriculaRoot);

        return getEntityManager().createQuery(criteria).getResultList();
    }
 /**
     * Busca Matrículas por valores en Observaciones (Usada para migración de BD antigua)
     * @param string
     * @return 
     * 
     */
    public Matricula findMatriculaObserv(String string) {
        javax.persistence.criteria.CriteriaBuilder builder =
                getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<Matricula> matricula = cq.from(Matricula.class);
        cq.select(matricula);

        cq.where(builder.like(builder.upper(matricula.get(Matricula_.observaciones)), "%/" + string + "/%"));

        if (getEntityManager().createQuery(cq).getResultList().isEmpty()) {
            return null;
        } else {
            return (Matricula) getEntityManager().createQuery(cq).getResultList().get(0);
        }
    }

    @Override
    public Matricula create(Matricula entity) {
        if (entity.getImporteBonificar() != null && entity.getImporteBonificarFecha() == null) {
            entity.setImporteBonificarFecha(new Date());
        }
        return super.create(entity);
    }
}
