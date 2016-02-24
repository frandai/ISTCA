package vista;

import bean.EmpresaFacade;
import bean.EventoFacade;
import bean.EventoTipoFacade;
import bean.MatriculaFacade;
import bean.TutorFacade;
import datos.CpLocalidad;
import datos.Direccion;
import datos.Empresa;
import datos.Evento;
import datos.EventoTipo;
import datos.Factura;
import datos.Matricula;
import datos.TipoVia;
import datos.Tutor;
import gestion.EmpresaGestion;
import gestion.MatriculaGestion;
import gestion.PersonaGestion;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;
import util.ParStringBoolean;
import util.descripcionEventos;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import util.Fecha;
import util.VariablesSistema;
import util.datosCalendario;

@ManagedBean(name = "controlCalendario")
@ViewScoped
public class controlCalendario implements Serializable {

    @ManagedProperty("#{sesionActual}")
    private SesionActual sesionActual;
    @ManagedProperty("#{matriculaGestion}")
    private MatriculaGestion mges;
    @ManagedProperty("#{personaGestion}")
    private PersonaGestion pges;
    @ManagedProperty("#{empresaGestion}")
    private EmpresaGestion eges;
    private ScheduleModel lazyEventModel;
    @EJB
    private EventoFacade evfacade;
    @EJB
    private EventoTipoFacade evtfacade;
    @EJB
    private TutorFacade tutorFacade;
    @EJB
    private EmpresaFacade empresaFacade;
    @EJB
    private MatriculaFacade matriculaFacade;
    private ArrayList<ParStringBoolean> numEstados;
    private Evento event = null;
    private Date fechaNuevoEvento = new Date();
    private List<EventoTipo> tiposEvento;
    private boolean estado_pendiente = true;
    private boolean estado_realizado = false;
    private boolean estado_vencido = true;
    private boolean estado_rv = false;
    private Matricula[] matriculasSeleccionadas;
    private boolean datosCargados = false;
    private boolean agenda = false;
    private int eventos_fecha = 1; //1: creacion - 2: realizacion - 3: vencimiento
    private Date fechainicio_agenda = Fecha.getFechaSetDias(new Date(), -7);
    private Date fechafin_agenda = Fecha.getFechaSetDias(new Date(), 7);
    private List<Evento> eventos_agenda;
    private List<Evento> eventos_agenda_filtrados;
    private Date fechaNuevoEventoVenc;

    public Date getFechaNuevoEventoVenc() {
        return fechaNuevoEventoVenc;
    }

    public void setFechaNuevoEventoVenc(Date fechaNuevoEventoVenc) {
        this.fechaNuevoEventoVenc = fechaNuevoEventoVenc;
    }

    public EmpresaGestion getEges() {
        return eges;
    }

    public void setEges(EmpresaGestion eges) {
        this.eges = eges;
    }

    public PersonaGestion getPges() {
        return pges;
    }

    public void setPges(PersonaGestion pges) {
        this.pges = pges;
    }

    public List<Evento> getEventos_agenda_filtrados() {
        return eventos_agenda_filtrados;
    }

    public void setEventos_agenda_filtrados(List<Evento> eventos_agenda_filtrados) {
        this.eventos_agenda_filtrados = eventos_agenda_filtrados;
    }

    public List<Evento> getEventos_agenda() {
        if (eventos_agenda == null) {
            eventos_agenda = new ArrayList<Evento>();
            List<Evento> ev = evfacade.findFechas(fechainicio_agenda, fechafin_agenda, estado_pendiente, estado_realizado, estado_vencido, eventos_fecha);
            for (Evento evento : ev) {
                if (seleccionaEventoDatos(evento, null) != null) {
                    eventos_agenda.add(evento);
                }
            }
        }
        return eventos_agenda;
    }

    public void setEventos_agenda(List<Evento> eventos_agenda) {
        this.eventos_agenda = eventos_agenda;
    }

    public Date getFechainicio_agenda() {
        return fechainicio_agenda;
    }

    public void setFechainicio_agenda(Date fechainicio_agenda) {
        eventos_agenda = null;
        this.fechainicio_agenda = fechainicio_agenda;
        getEventos_agenda();
    }

    public Date getFechafin_agenda() {
        return fechafin_agenda;
    }

    public void setFechafin_agenda(Date fechafin_agenda) {
        eventos_agenda = null;
        this.fechafin_agenda = fechafin_agenda;
        getEventos_agenda();
    }

    public boolean isAgenda() {
        if (!datosCargados) {
            datosCargados = true;
            cargarDatosGuardados(getSesionActual().getUsuario_conectado().getNif());
        }
        return agenda;
    }

    public void setAgenda(boolean agenda) {
        this.agenda = agenda;
        if (datosCargados) {
            guardaDatos(null);
        }
    }

    public int getEventos_fecha() {
        if (!datosCargados) {
            datosCargados = true;
            cargarDatosGuardados(getSesionActual().getUsuario_conectado().getNif());
        }
        return eventos_fecha;
    }

    public void setEventos_fecha(int eventos_fecha) {
        this.eventos_fecha = eventos_fecha;
    }

    public void elimina(ActionEvent e) throws IOException {

        evfacade.remove(getEvent());
        if (getEvent().getTutor() != null) {
            getEvent().getTutor().getEventoList().remove(getEvent());
            Tutor t = tutorFacade.edit(getEvent().getTutor());
            if (pges != null && pges.getPersonaSeleccionada() != null && pges.getPersonaSeleccionada().getNif() != null
                    && t.getNif().equals(pges.getPersonaSeleccionada().getNif())) {
                pges.setPersonaSeleccionada(t.getPersona());
            }
        }
        if (getEvent().getEmpresa() != null) {
            getEvent().getEmpresa().getEventoList().remove(getEvent());
            Empresa t = empresaFacade.edit(getEvent().getEmpresa());
            if (t.getDireccion() == null) {

                Direccion d = new Direccion();
                d.setCpLocalidad(new CpLocalidad());
                d.setTipoVia(new TipoVia(1));
                t.setDireccion(d);
            }
            if (eges != null && eges.getEmpresaSeleccionada() != null && eges.getEmpresaSeleccionada().getNif() != null
                    && t.getNif().equals(eges.getEmpresaSeleccionada().getNif())) {
                eges.setEmpresaSeleccionada(t);
            }

        }
        if (getEvent().getMatricula() != null) {
            getEvent().getMatricula().getEventoList().remove(getEvent());
            Matricula m = matriculaFacade.edit(getEvent().getMatricula());
            if (getEvent().getTipo().getId().equals(6)) {
                Evento ult = null;
                for (Evento en : m.getEventoList()) {
                    if (ult == null || ult.getFechaCreacion().before(en.getFechaCreacion())) {
                        ult = en;
                    }
                }
                boolean cambia = true;
                for (Factura f : m.getFacturaList()) {
                    if (f.getImporte() < 0.0) {
                        cambia = false;
                        break;
                    }
                }
                if (cambia) {
                    if (ult == null || !ult.getTipo().getId().equals(6)) {
                        m.setBaja(false);
                        m = matriculaFacade.edit(m);
                    }
                }
            }
            if (mges != null && mges.getMatriculaSeleccionada() != null && mges.getMatriculaSeleccionada().getId() != null
                    && m.getId().equals(mges.getMatriculaSeleccionada().getId())) {
                mges.setMatriculaSeleccionada(m);
            }
        }
        System.out.println("[BORRAR][" + Fecha.getFechaAnioMesDiaHoraMinutoSegundo(new Date()) + "]Se ha Borrado el Evento " + this.getEvent().getId());
        UtilidadesVista.Mensaje("El evento se ha eliminado", FacesMessage.SEVERITY_INFO);
        if (agenda) {
            getEventos_agenda();
        }
    }

    public MatriculaGestion getMges() {
        return mges;
    }

    public void setMges(MatriculaGestion mges) {
        this.mges = mges;
    }

    public Matricula[] getMatriculasSeleccionadas() {
        return matriculasSeleccionadas;
    }

    public void setMatriculasSeleccionadas(Matricula[] matriculasSeleccionadas) {
        this.matriculasSeleccionadas = matriculasSeleccionadas;
    }

    public boolean isEstado_pendiente() {
        if (!datosCargados) {
            datosCargados = true;
            cargarDatosGuardados(getSesionActual().getUsuario_conectado().getNif());
        }
        return estado_pendiente;
    }

    public void setEstado_pendiente(boolean estado_pendiente) {
        this.estado_pendiente = estado_pendiente;
    }

    public boolean isEstado_realizado() {
        if (!datosCargados) {
            datosCargados = true;
            cargarDatosGuardados(getSesionActual().getUsuario_conectado().getNif());
        }
        return estado_realizado;
    }

    public void setEstado_realizado(boolean estado_realizado) {
        this.estado_realizado = estado_realizado;
    }

    public boolean isEstado_vencido() {
        if (!datosCargados) {
            datosCargados = true;
            cargarDatosGuardados(getSesionActual().getUsuario_conectado().getNif());
        }
        return estado_vencido;
    }

    public void setEstado_vencido(boolean estado_vencido) {
        this.estado_vencido = estado_vencido;

    }

    public boolean isEstado_rv() {
        if (!datosCargados) {
            datosCargados = true;
            cargarDatosGuardados(getSesionActual().getUsuario_conectado().getNif());
        }
        return estado_rv;
    }

    public void setEstado_rv(boolean estado_rv) {
        this.estado_rv = estado_rv;
    }

    public List<EventoTipo> getTiposEvento() {
        if (!datosCargados) {
            datosCargados = true;
            cargarDatosGuardados(getSesionActual().getUsuario_conectado().getNif());
        }
        if (tiposEvento == null) {
            tiposEvento = evtfacade.findAll();
        }
        return tiposEvento;
    }

    public void setTiposEvento(List<EventoTipo> tiposEvento) {
        this.tiposEvento = tiposEvento;
    }

    public ArrayList<ParStringBoolean> getNumEstados() {
        if (!datosCargados) {
            datosCargados = true;
            cargarDatosGuardados(getSesionActual().getUsuario_conectado().getNif());
        }
        if (numEstados == null) {
            numEstados = new ArrayList<ParStringBoolean>();
            for (int i = 1; i <= descripcionEventos.getNumDE(); i++) {
                numEstados.add(new ParStringBoolean("" + i, true));
            }
        }
        return numEstados;
    }

    public void setNumEstados(ArrayList<ParStringBoolean> numEstados) {
        this.numEstados = numEstados;
    }

    public void guardaDatos(ActionEvent evt) {
        guardarDatos(getSesionActual().getUsuario_conectado().getNif());
        if (agenda) {
            eventos_agenda = null;
        }
    }

    private Date seleccionaEventoDatos(Evento evento, ArrayList<Matricula> matsel) {
        if (matsel == null || matsel.isEmpty() || evento.getMatricula() == null || matsel.contains(evento.getMatricula())) {
            if (evento.getTipoDescripcionEvento() != descripcionEventos.DIPLOMA_ADICCIONAL
                    && evento.getTipoDescripcionEvento() != descripcionEventos.NO_DEFINIDO) {
                if ((evento.getEstado().equals("P") && estado_pendiente)
                        || (evento.getEstado().equals("R") && estado_realizado)
                        || (evento.getEstado().equals("V") && estado_vencido)
                        || (evento.getEstado().equals("RV") && estado_rv)) {
                    for (EventoTipo e1 : getTiposEvento()) {
                        if (e1.isSeleccionado() && e1.getId().equals(evento.getTipo().getId())) {
                            for (ParStringBoolean p1 : getNumEstados()) {
                                if (p1.isB() && p1.getS().equals("" + descripcionEventos.getTipoEvento(evento))) {
                                    switch (eventos_fecha) {
                                        case 1:
                                            return evento.getFechaCreacion();
                                        case 2:
                                            return evento.getFechaRealizacion();
                                        case 3:
                                            return evento.getFechaVencimiento();
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        return null;
    }

    public controlCalendario() {

        lazyEventModel = new LazyScheduleModel() {
            @Override
            public void loadEvents(Date start, Date end) {
                clear();

                List<Evento> eventos = evfacade.findFechas(start, end, estado_pendiente, estado_realizado, estado_vencido, eventos_fecha);
                ArrayList<Matricula> matsel = null;
                if (matriculasSeleccionadas != null) {
                    matsel = new ArrayList<Matricula>(Arrays.asList(matriculasSeleccionadas));
                }

                for (Evento evento : eventos) {
                    Date fecha = seleccionaEventoDatos(evento, matsel);
                    if (fecha != null) {
                        DefaultScheduleEvent ev = new DefaultScheduleEvent(
                                (evento.getNifID()),
                                fecha,
                                fecha);
                        ev.setData(evento);
                        ev.setStyleClass(evento.getClaseEvento());
                        ev.setAllDay(true);
                        addEvent(ev);
                    }
                }

            }
        };
    }

    public ScheduleModel getLazyEventModel() {
        if (!datosCargados) {
            datosCargados = true;
            cargarDatosGuardados(getSesionActual().getUsuario_conectado().getNif());
        }
        return lazyEventModel;
    }

    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

    public void onEventSelect(org.primefaces.event.ScheduleEntrySelectEvent selectEvent) {
        event = (Evento) selectEvent.getScheduleEvent().getData();
    }

    public Evento getEvent() {
        return event;
    }

    public void setEvent(Evento event) {
        this.event = event;
    }

    public void setEventId(int id) {
        this.event = evfacade.find(id);
    }

    public int getEventId() {
        try {
            return this.event.getId();
        } catch (Exception e) {
            return -1;
        }

    }

    public Date getFechaNuevoEvento() {
        return fechaNuevoEvento;
    }

    public void setFechaNuevoEvento(Date fechaNuevoEvento) {
        this.fechaNuevoEvento = fechaNuevoEvento;
    }

    public void guardarEvento(ActionEvent eve) {
        evfacade.edit(event);
        if (mges != null && mges.getMatriculaSeleccionada() != null && mges.getMatriculaSeleccionada().getId() != null) {
            mges.setMatriculaSeleccionada(mges.getMatriculaFacade().find(mges.getMatriculaSeleccionada().getId()));
        }
        if (pges != null && pges.getPersonaSeleccionada() != null && pges.getPersonaSeleccionada().getNif() != null) {
            pges.setPersonaSeleccionada(pges.getPersonaFacade().find(pges.getPersonaSeleccionada().getNif()));
        }
        if (eges != null && eges.getEmpresaSeleccionada() != null && eges.getEmpresaSeleccionada().getNif() != null) {
            eges.setEmpresaSeleccionada(empresaFacade.find(eges.getEmpresaSeleccionada().getNif()));
        }
    }

    public void nuevoEvento(ActionEvent eve) {
        guardarEvento(eve);
        Evento e = new Evento();
        e.setCreador(getSesionActual().usuario_conectado.getPersona());
        e.setMatricula(event.getMatricula());
        e.setTutor(event.getTutor());
        e.setObservaciones("<div>-</div><hr/><p>Observaciones del evento con fecha de creación " + Fecha.getFechaDiaMesAnio(event.getFechaCreacion()) + ":</p>" + event.getObservaciones() + "");
        descripcionEventos.configurarEvento(e, event.getTipoDescripcionEvento());
        e.setDescripcion(event.getDescripcion());
        e.setFechaCreacion(fechaNuevoEvento);
        e.setFechaVencimiento(fechaNuevoEventoVenc);
        if (e.getDescripcion() == null) {
            e.setDescripcion(event.getDescripcion());
        }
        evfacade.create(e);
        if (mges != null && mges.getMatriculaSeleccionada() != null && mges.getMatriculaSeleccionada().getId() != null) {
            mges.setMatriculaSeleccionada(mges.getMatriculaFacade().find(mges.getMatriculaSeleccionada().getId()));
        }
        fechaNuevoEventoVenc = null;

    }

    public SesionActual getSesionActual() {
        return sesionActual;
    }

    public void setSesionActual(SesionActual sesionActual) {
        this.sesionActual = sesionActual;
    }

    private void guardarDatos(String nif) {
        ObjectOutputStream oos = null;
        try {
            File fichero = new File(VariablesSistema.ruta_absoluta_proyecto + "/docs/datoscal/" + nif);
            oos = new ObjectOutputStream(new FileOutputStream(fichero));
            if (oos != null) {
                datosCalendario dc = new datosCalendario();
                ArrayList<ParStringBoolean> tiposev = new ArrayList<ParStringBoolean>();
                for (EventoTipo et : getTiposEvento()) {
                    tiposev.add(new ParStringBoolean("" + et.getId(), et.isSeleccionado()));
                }
                dc.tiposEvento = tiposev;
                dc.estado_pendiente = this.estado_pendiente;
                dc.estado_realizado = this.estado_realizado;
                dc.estado_vencido = this.estado_vencido;
                dc.estado_rv = this.estado_rv;
                dc.numEstados = this.numEstados;
                dc.agenda = this.agenda;
                dc.eventos_fecha = this.eventos_fecha;
                oos.writeObject(dc);
                oos.close();
            }
        } catch (NullPointerException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException ex) {
            Logger.getLogger(controlCalendario.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(controlCalendario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void cargarDatosGuardados(String nif) {
        ObjectInputStream ois = null;
        try {
            File fichero = new File(VariablesSistema.ruta_absoluta_proyecto + "/docs/datoscal/" + nif);
            ois = new ObjectInputStream(new FileInputStream(fichero));
            if (ois != null) {
                Object aux = ois.readObject();
                if (aux != null) {
                    if (aux instanceof datosCalendario) {
                        datosCalendario dc = (datosCalendario) aux;
                        List<ParStringBoolean> tiposev;
                        tiposev = dc.tiposEvento;
                        for (ParStringBoolean parStringBoolean : tiposev) {
                            for (EventoTipo et : getTiposEvento()) {
                                if (et.getId().equals(Integer.parseInt(parStringBoolean.getS()))) {
                                    et.setSeleccionado(parStringBoolean.isB());
                                }
                            }
                        }
                        this.estado_pendiente = dc.estado_pendiente;
                        this.estado_realizado = dc.estado_realizado;
                        this.estado_vencido = dc.estado_vencido;
                        this.estado_rv = dc.estado_rv;
                        this.numEstados = dc.numEstados;
                        this.agenda = dc.agenda;
                        this.eventos_fecha = dc.eventos_fecha;
                    }
                }
                ois.close();
            }
        } catch (NullPointerException e) {
        } catch (FileNotFoundException e) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(controlCalendario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(controlCalendario.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(controlCalendario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
