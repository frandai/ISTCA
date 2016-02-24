/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import datos.Evento;
import datos.EventoTipo;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 *
 */
@ManagedBean(name = "descripcionEventos")
@ViewScoped
public class descripcionEventos implements Serializable {

    //Eventos definidos:
    public static final int CONFIRMACION_MATRICULA = 1;
    public static final int PAGO_POSTERIOR_TRASBON = 2;
    public static final int GESTION_MOROSO = 3;
    public static final int SEGUIMIENTO_TUTORIAL = 4;
    public static final int RECEPCION_FICHA_REGISTRO = 5;
    public static final int RECEPCION_EVALUACION = 6;
    public static final int RECEPCION_CUESTIONARIO_CALIDAD = 7;
    public static final int RECEPCION_DIPLOMA = 8;
    public static final int AUSENCIA_TUTOR = 9;
    public static final int PAGO_POSTERIOR_MESBON = 10;
    public static final int DIPLOMA_ADICCIONAL = 11;
    public static final int ENVIO_DOCUMENTACION = 12;
    public static final int PAGO_APLAZADO = 13;
    public static final int MATRICULA_BAJA = 14;
    public static final int RECEPCION_ACREDITACION = 15;
    public static final int GENERADO_XML_INICIO = 16;
    public static final int GENERADO_XML_FIN = 17;
    public static final int NOTA_EMPRESA = 18;
    public static final int COMUNICACION_INICIO_CURSO = 19;
    public static final int COMUNICACION_SEGUIMIENTO_CURSO = 20;
    public static final int COMUNICACION_FIN_CURSO = 21;
    public static final int COMUNICACION_RECEPCION_EVALUACION = 22;
    public static final int NO_DEFINIDO = -1;

    public static int getNOTA_EMPRESA() {
        return NOTA_EMPRESA;
    }

    public static int getPAGO_APLAZADO() {
        return PAGO_APLAZADO;
    }

    public static int getMATRICULA_BAJA() {
        return MATRICULA_BAJA;
    }

    public static int getCONFIRMACION_MATRICULA() {
        return CONFIRMACION_MATRICULA;
    }

    public static int getPAGO_POSTERIOR_TRASBON() {
        return PAGO_POSTERIOR_TRASBON;
    }

    public static int getGESTION_MOROSO() {
        return GESTION_MOROSO;
    }

    public static int getSEGUIMIENTO_TUTORIAL() {
        return SEGUIMIENTO_TUTORIAL;
    }

    public static int getRECEPCION_FICHA_REGISTRO() {
        return RECEPCION_FICHA_REGISTRO;
    }

    public static int getRECEPCION_EVALUACION() {
        return RECEPCION_EVALUACION;
    }

    public static int getRECEPCION_CUESTIONARIO_CALIDAD() {
        return RECEPCION_CUESTIONARIO_CALIDAD;
    }

    public static int getENVIO_DOCUMENTACION() {
        return ENVIO_DOCUMENTACION;
    }

    public static int getRECEPCION_DIPLOMA() {
        return RECEPCION_DIPLOMA;
    }

    public static int getAUSENCIA_TUTOR() {
        return AUSENCIA_TUTOR;
    }

    public static int getPAGO_POSTERIOR_MESBON() {
        return PAGO_POSTERIOR_MESBON;
    }

    public static int getDIPLOMA_ADICCIONAL() {
        return DIPLOMA_ADICCIONAL;
    }

    public static int getNO_DEFINIDO() {
        return NO_DEFINIDO;
    }

    public static int getRECEPCION_ACREDITACION() {
        return RECEPCION_ACREDITACION;
    }

    public static Evento configurarEvento(Evento ev, int desc_evento) {
        if (desc_evento != AUSENCIA_TUTOR && ev.getFechaCreacion() == null) {
            ev.setFechaCreacion(new Date());
        }
        if (desc_evento != GESTION_MOROSO && desc_evento != DIPLOMA_ADICCIONAL) {
            ev.setDescripcion(getTextoDescripcionContiene(desc_evento));
        }
        switch (desc_evento) {
            case PAGO_APLAZADO:
                ev.setTipo(new EventoTipo(1));
                ev.setFechaRealizacion(null);
                break;
            case CONFIRMACION_MATRICULA:
                ev.setTipo(new EventoTipo(1));
                ev.setFechaRealizacion(null);
                ev.setFechaVencimiento(Fecha.getFechaSetDias(ev.getFechaCreacion(), 15));
                break;
            case PAGO_POSTERIOR_TRASBON:
                ev.setTipo(new EventoTipo(1));
                ev.setFechaRealizacion(ev.getFechaRealizacion());
                ev.setFechaVencimiento(Fecha.getDiaNumMesSiguiente(ev.getMatricula().getGrupo().getFFin(), 28));
                break;
            case GESTION_MOROSO:
                ev.setTipo(new EventoTipo(1));
                ev.setFechaRealizacion(null);
                ev.setFechaVencimiento(null);
                break;
            case SEGUIMIENTO_TUTORIAL:
                ev.setTipo(new EventoTipo(1));
                ev.setFechaRealizacion(null);
                ev.setFechaVencimiento(Fecha.getFechaSetDias(ev.getFechaCreacion(), 15));
                break;
            case COMUNICACION_INICIO_CURSO:
            case COMUNICACION_SEGUIMIENTO_CURSO:
            case COMUNICACION_FIN_CURSO:
            case COMUNICACION_RECEPCION_EVALUACION:
                ev.setTipo(new EventoTipo(1));
                break;
            case ENVIO_DOCUMENTACION:
                ev.setTipo(new EventoTipo(2));
                ev.setFechaRealizacion(ev.getFechaCreacion());
                ev.setFechaVencimiento(null);
                break;
            case RECEPCION_FICHA_REGISTRO:
                ev.setTipo(new EventoTipo(3));
                ev.setFechaRealizacion(ev.getFechaCreacion());
                ev.setFechaVencimiento(null);
                break;
            case RECEPCION_EVALUACION:
                ev.setTipo(new EventoTipo(3));
                ev.setFechaRealizacion(ev.getFechaCreacion());
                ev.setFechaVencimiento(null);
                break;
            case RECEPCION_CUESTIONARIO_CALIDAD:
                ev.setTipo(new EventoTipo(3));
                ev.setFechaRealizacion(ev.getFechaCreacion());
                ev.setFechaVencimiento(null);
                break;
            case RECEPCION_DIPLOMA:
            case RECEPCION_ACREDITACION:
                ev.setTipo(new EventoTipo(3));
                ev.setFechaRealizacion(ev.getFechaCreacion());
                ev.setFechaVencimiento(null);
                break;
            case AUSENCIA_TUTOR:
                ev.setTipo(new EventoTipo(5));
                ev.setFechaRealizacion(ev.getFechaCreacion());
                ev.setFechaVencimiento(null);
                break;

            case NOTA_EMPRESA:
                ev.setTipo(new EventoTipo(8));
                ev.setFechaRealizacion(ev.getFechaCreacion());
                ev.setFechaVencimiento(null);
                break;
            case PAGO_POSTERIOR_MESBON:
                ev.setTipo(new EventoTipo(1));
                ev.setFechaRealizacion(ev.getFechaRealizacion());
                ev.setFechaVencimiento(Fecha.getDiaNumMesSiguiente(ev.getMatricula().getGrupo().getFFin(), 15));
                break;
            case DIPLOMA_ADICCIONAL:
                ev.setTipo(new EventoTipo(4));
                ev.setFechaRealizacion(ev.getFechaCreacion());
                ev.setFechaVencimiento(null);
                break;
            case MATRICULA_BAJA:
                ev.setTipo(new EventoTipo(6));
                ev.setFechaRealizacion(ev.getFechaCreacion());
                ev.setFechaVencimiento(null);
                break;
            case GENERADO_XML_INICIO:
            case GENERADO_XML_FIN:
                ev.setTipo(new EventoTipo(7));
                ev.setFechaRealizacion(ev.getFechaCreacion());
                ev.setFechaVencimiento(null);
                break;
        }
        return ev;
    }

    public static String getTextoDescripcionContiene(String desc_evento) {
        return getTextoDescripcionContiene(Integer.parseInt(desc_evento));
    }

    public static String getTextoDescripcionContiene(int desc_evento) {
        switch (desc_evento) {
            case CONFIRMACION_MATRICULA:
                return "Confirmación de Matrícula";
            case PAGO_POSTERIOR_TRASBON:
                return "Pago Posterior Tras Bonificar";
            case GESTION_MOROSO:
                return "Gestión de Moroso de la Factura";
            case SEGUIMIENTO_TUTORIAL:
                return "Seguimiento Tutorial";
            case ENVIO_DOCUMENTACION:
                return "Envío de Documentación";
            case RECEPCION_FICHA_REGISTRO:
                return "Recepción de Ficha de Registro";
            case RECEPCION_EVALUACION:
                return "Recepción de Evaluación";
            case RECEPCION_CUESTIONARIO_CALIDAD:
                return "Recepción de Cuestionario de Calidad";
            case RECEPCION_DIPLOMA:
                return "Recepción de Recibí Diploma";
            case AUSENCIA_TUTOR:
                return "Ausencia Tutor";
            case PAGO_POSTERIOR_MESBON:
                return "Pago Posterior En mes de Bonificación";
            case PAGO_APLAZADO:
                return "Pago Aplazado de la Factura";
            case MATRICULA_BAJA:
                return "Matrícula de Baja";
            case RECEPCION_ACREDITACION:
                return "Recepción de Acreditación";
            case GENERADO_XML_FIN:
                return "Generado XML de Finalización de Grupos";
            case GENERADO_XML_INICIO:
                return "Generado XML de Inicio de Grupos";
            case COMUNICACION_FIN_CURSO:
                return "Comunicación de Final de Curso";
            case COMUNICACION_INICIO_CURSO:
                return "Comunicación de Inicio de Curso";
            case COMUNICACION_SEGUIMIENTO_CURSO:
                return "Comunicación de Seguimiento de Curso";
            case COMUNICACION_RECEPCION_EVALUACION:
                return "Comunicación de Petición de Evaluacion";
        }
        return "NO DEFINIDO";
    }

    public static int getTipoEvento(Evento ev) {
        if (ev.getTipo().getId().equals((Integer) 4)) {
            return DIPLOMA_ADICCIONAL;
        }
        if (ev.getTipo().getId().equals((Integer) 6)) {
            return MATRICULA_BAJA;
        }
        if (ev.getTipo().getId().equals((Integer) 8)) {
            return NOTA_EMPRESA;
        }
        for (int i = 1; i <= getNumDE(); i++) {
            if (ev.getDescripcion().contains(getTextoDescripcionContiene(i))) {
                return i;
            }
        }


        return NO_DEFINIDO;
    }

    public static String getClaseEvento(Evento ev) {
        String clase = "";
        clase = getClaseEventoInt(getTipoEvento(ev));
        return clase;
    }

    public static String getClaseEventoInt(int numEv) {
        String clase = "evento0";
        switch (numEv) {
            case CONFIRMACION_MATRICULA:
                clase = "evento1";
                break;
            case PAGO_POSTERIOR_TRASBON:
                clase = "evento2";
                break;
            case GESTION_MOROSO:
                clase = "evento3";
                break;
            case SEGUIMIENTO_TUTORIAL:
            case COMUNICACION_FIN_CURSO:
            case COMUNICACION_INICIO_CURSO:
            case COMUNICACION_SEGUIMIENTO_CURSO:
            case COMUNICACION_RECEPCION_EVALUACION:
                clase = "evento4";
                break;
            case ENVIO_DOCUMENTACION:
                clase = "evento5";
                break;
            case RECEPCION_FICHA_REGISTRO:
                clase = "evento6";
                break;
            case RECEPCION_EVALUACION:
                clase = "evento7";
                break;
            case RECEPCION_CUESTIONARIO_CALIDAD:
                clase = "evento8";
                break;
            case PAGO_APLAZADO:
                clase = "evento9";
                break;
            case RECEPCION_DIPLOMA:
                clase = "evento11";
                break;
            case AUSENCIA_TUTOR:
                clase = "evento12";
                break;
            case PAGO_POSTERIOR_MESBON:
                clase = "evento13";
                break;
            case DIPLOMA_ADICCIONAL:
                clase = "evento14";
                break;
            case MATRICULA_BAJA:
                clase = "evento15";
                break;
            case RECEPCION_ACREDITACION:
                clase = "evento16";
                break;
            case NO_DEFINIDO:
            case GENERADO_XML_FIN:
            case GENERADO_XML_INICIO:
                clase = "evento0";
                break;

        }
        return clase;
    }

    public static int getNumDE() {
        return 22;
    }
}
