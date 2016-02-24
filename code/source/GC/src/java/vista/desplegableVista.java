/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import bean.AccionFormativaGrupoFacade;
import bean.AccionFormativaModalidadFacade;
import bean.ActividadGrupoFacade;
import bean.AlumnoAreaFuncionalFacade;
import bean.AlumnoCategoriaProfesionalFacade;
import bean.AlumnoGrupoCotizacionFacade;
import bean.AlumnoNivelEstudioFacade;
import bean.ComercialTipoFacade;
import bean.EmpresaEstadoFacade;
import bean.EventoEmpresaTipoFacade;
import bean.FacturaEstadoFacade;
import bean.ProvinciaFacade;
import bean.TelefonoTipoFacade;
import datos.AccionFormativaGrupo;
import datos.AccionFormativaModalidad;
import datos.ActividadGrupo;
import datos.AlumnoAreaFuncional;
import datos.AlumnoCategoriaProfesional;
import datos.AlumnoGrupoCotizacion;
import datos.AlumnoNivelEstudio;
import datos.ComercialTipo;
import datos.EmpresaEstado;
import datos.EventoEmpresaTipo;
import datos.FacturaEstado;
import datos.Provincia;
import datos.TelefonoTipo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 *dai
 */
@ManagedBean(name = "desplegableVista")
@SessionScoped
public class desplegableVista implements Serializable {

    @EJB
    private AccionFormativaModalidadFacade accionFormativaModalidadFacade;
    @EJB
    private ActividadGrupoFacade actividadGFacade;
    @EJB
    private ProvinciaFacade pvFacade;
    @EJB
    private TelefonoTipoFacade tiposTelefonoFacade;
    @EJB
    private AlumnoAreaFuncionalFacade aAreaFuncionalFacade;
    @EJB
    private AlumnoCategoriaProfesionalFacade acategProfFacade;
    @EJB
    private AlumnoGrupoCotizacionFacade agrupocotizacionFacade;
    @EJB
    private AlumnoNivelEstudioFacade anivelestudiosFacade;
    @EJB
    private ComercialTipoFacade nivelesecfacade;
    @EJB
    private EmpresaEstadoFacade empresaEstadoFac;
    @EJB
    private FacturaEstadoFacade facturaEstadoFac;
    @EJB
    private AccionFormativaGrupoFacade afGrupo;
    @EJB
    private EventoEmpresaTipoFacade eetfac;
    private SelectItem[] sinoOptions;
    private SelectItem[] sinoOptionsDefectoNo;
    private SelectItem[] ModalidadOptions;
    private SelectItem[] FormacionOptions;
    private SelectItem[] TipoOptions;
    private SelectItem[] tipoEmpresaOptions;
    private SelectItem[] gruposOptions;
    private SelectItem[] tipoPersonaOptions;
    private SelectItem[] tipoSexoOptions;
    private SelectItem[] provinciasOptions;
    private SelectItem[] tiposViaOptions;
    private SelectItem[] nivelesECOptions;
    private SelectItem[] estadoEmpresaOptions;
    private SelectItem[] familiaOptions;
    private SelectItem[] estadoFacturaOptions;
    private SelectItem[] eventoEmpresaTipoOptions;
    private SelectItem[] estadoEventoComOptions = new SelectItem[]{new SelectItem("", ""),
        new SelectItem("En Espera", "En Espera"), new SelectItem("Pendiente", "Pendiente"), new SelectItem("Realizado", "Realizado"), new SelectItem("Sin Evento", "Sin Evento")};
    private direccionVista dv;
    private List<TelefonoTipo> tiposTelefono;
    private List<AlumnoNivelEstudio> nivelEstudios;
    private List<AlumnoAreaFuncional> areaFuncional;
    private List<AlumnoCategoriaProfesional> categoriaProfesional;
    private List<AlumnoGrupoCotizacion> grupoCotizacion;
    private String nombreNuevaDescripcion;

    public SelectItem[] getEstadoEventoComOptions() {
        return estadoEventoComOptions;
    }

    public void setEstadoEventoComOptions(SelectItem[] estadoEventoComOptions) {
        this.estadoEventoComOptions = estadoEventoComOptions;
    }

    public String getNombreNuevaDescripcion() {
        return nombreNuevaDescripcion;
    }

    public void setNombreNuevaDescripcion(String nombreNuevaDescripcion) {
        this.nombreNuevaDescripcion = nombreNuevaDescripcion;
    }

    public void guardaNuevaDescripcion() {
        if (nombreNuevaDescripcion != null && !nombreNuevaDescripcion.equals("")) {
            EventoEmpresaTipo eet = new EventoEmpresaTipo();
            try {
                if (eetfac.findNamedQuery("CColectivo.findByNombre", "nombre", nombreNuevaDescripcion) != null) {
                    UtilidadesVista.Mensaje("No se ha guardado el convenio ya que ya existía en la plataforma.", FacesMessage.SEVERITY_ERROR);
                    return;
                }
            } catch (Exception e) {
            }
            eet.setNombreEvento(nombreNuevaDescripcion);
            eetfac.create(eet);
            eventoEmpresaTipoOptions = null;
            getEventoEmpresaTipoOptions();
        }
    }

    public desplegableVista() {
        sinoOptions = UtilidadesVista.createFilterOptions(new String[]{"Sí", "No"});
        sinoOptionsDefectoNo = UtilidadesVista.createFilterOptions(new String[]{"NoDEFECTO", "Sí", ""});
        FormacionOptions = UtilidadesVista.createFilterOptions(new String[]{"Básica", "Superior"});
        TipoOptions = UtilidadesVista.createFilterOptions(new String[]{"Genérica", "Específica"});
        tipoEmpresaOptions = UtilidadesVista.createFilterOptions(new String[]{"Matricular", "Proveedor", "Mat.+Prov.", "Cliente Potencial"});
        tipoPersonaOptions = UtilidadesVista.createFilterOptions(new String[]{"Tutor + Alumno", "Alumno", "Tutor", "Contacto"});
        tipoSexoOptions = UtilidadesVista.createFilterOptions(new String[]{"M", "F"});
    }

    public SelectItem[] getSinoOptions() {
        return sinoOptions;
    }

    public void setSinoOptions(SelectItem[] sinoOptions) {
        this.sinoOptions = sinoOptions;
    }

    public SelectItem[] getSinoOptionsDefectoNo() {
        return sinoOptionsDefectoNo;
    }

    public void setSinoOptionsDefectoNo(SelectItem[] sinoOptionsDefectoNo) {
        this.sinoOptionsDefectoNo = sinoOptionsDefectoNo;
    }

    public SelectItem[] getEventoEmpresaTipoOptions() {
        if (eventoEmpresaTipoOptions == null) {
            ArrayList<String> eeto = new ArrayList<String>();
            for (EventoEmpresaTipo m : eetfac.findAll()) {
                eeto.add(m.getNombreEvento());
            }
            String[] o = new String[eeto.size()];
            for (int i = 0; i < o.length; i++) {
                o[i] = eeto.get(i);

            }
            eventoEmpresaTipoOptions = UtilidadesVista.createFilterOptions(o);
        }
        return eventoEmpresaTipoOptions;
    }

    public void setEventoEmpresaTipoOptions(SelectItem[] eventoEmpresaTipoOptions) {
        this.eventoEmpresaTipoOptions = eventoEmpresaTipoOptions;
    }

    public SelectItem[] getModalidadOptions() {
        if (ModalidadOptions == null) {
            ArrayList<String> modalidad = new ArrayList<String>();
            for (AccionFormativaModalidad m : accionFormativaModalidadFacade.findAll()) {
                modalidad.add(m.getNombre());
            }
            String[] o = new String[modalidad.size()];
            for (int i = 0; i < o.length; i++) {
                o[i] = modalidad.get(i);

            }
            ModalidadOptions = UtilidadesVista.createFilterOptions(o);
        }
        return ModalidadOptions;
    }

    public void setModalidadOptions(SelectItem[] ModalidadOptions) {
        this.ModalidadOptions = ModalidadOptions;
    }

    public SelectItem[] getFormacionOptions() {
        return FormacionOptions;
    }

    public void setFormacionOptions(SelectItem[] FormacionOptions) {
        this.FormacionOptions = FormacionOptions;
    }

    public SelectItem[] getTipoOptions() {
        return TipoOptions;
    }

    public void setTipoOptions(SelectItem[] TipoOptions) {
        this.TipoOptions = TipoOptions;
    }

    public SelectItem[] getTipoEmpresaOptions() {
        return tipoEmpresaOptions;
    }

    public void setTipoEmpresaOptions(SelectItem[] tipoEmpresaOptions) {
        this.tipoEmpresaOptions = tipoEmpresaOptions;
    }

    public SelectItem[] getNivelesECOptions() {
        if (nivelesECOptions == null) {
            List<ComercialTipo> gr = nivelesecfacade.findAll();
            String[] g = new String[gr.size()];
            for (int i = 0; i < g.length; i++) {
                g[i] = gr.get(i).getNombre();

            }
            nivelesECOptions = UtilidadesVista.createFilterOptions(g);
        }
        return nivelesECOptions;
    }

    public SelectItem[] getFamiliaOptions() {
        if (familiaOptions == null) {
            List<AccionFormativaGrupo> gr = afGrupo.findAll();
            String[] g = new String[gr.size()];
            for (int i = 0; i < g.length; i++) {
                g[i] = gr.get(i).getNombre();

            }
            familiaOptions = UtilidadesVista.createFilterOptions(g);
        }
        return familiaOptions;
    }

    public SelectItem[] getEstadoEmpresaOptions() {
        if (estadoEmpresaOptions == null) {
            List<EmpresaEstado> gr = empresaEstadoFac.findAll();
            String[] g = new String[gr.size()];
            for (int i = 0; i < g.length; i++) {
                g[i] = gr.get(i).getNombre();

            }
            estadoEmpresaOptions = UtilidadesVista.createFilterOptions(g);
        }
        return estadoEmpresaOptions;
    }

    public SelectItem[] getEstadoFacturaOptions() {
        if (estadoFacturaOptions == null) {
            List<FacturaEstado> gr = facturaEstadoFac.findAll();
            String[] g = new String[gr.size()];
            for (int i = 0; i < g.length; i++) {
                g[i] = gr.get(i).getNombre();

            }
            estadoFacturaOptions = UtilidadesVista.createFilterOptions(g);
        }
        return estadoFacturaOptions;
    }

    public SelectItem[] getGruposOptions() {
        if (gruposOptions == null) {
            List<ActividadGrupo> gr = actividadGFacade.findAll();
            String[] g = new String[gr.size()];
            for (int i = 0; i < g.length; i++) {
                g[i] = gr.get(i).getNombre();

            }
            gruposOptions = UtilidadesVista.createFilterOptions(g);
        }
        return gruposOptions;
    }

    public void setGruposOptions(SelectItem[] gruposOptions) {
        this.gruposOptions = gruposOptions;
    }

    public SelectItem[] getTipoPersonaOptions() {
        return tipoPersonaOptions;
    }

    public void setTipoPersonaOptions(SelectItem[] tipoPersonaOptions) {
        this.tipoPersonaOptions = tipoPersonaOptions;
    }

    public SelectItem[] getTipoSexoOptions() {
        return tipoSexoOptions;
    }

    public void setTipoSexoOptions(SelectItem[] tipoSexoOptions) {
        this.tipoSexoOptions = tipoSexoOptions;
    }

    public SelectItem[] getProvinciasOptions() {
        if (provinciasOptions == null) {
            List<Provincia> prov = pvFacade.findAll();
            String[] tv = new String[prov.size()];
            for (int i = 0; i < tv.length; i++) {
                tv[i] = prov.get(i).getNombre();
            }
            provinciasOptions = UtilidadesVista.createFilterOptions(tv);
        }
        return provinciasOptions;
    }

    public void setProvinciasOptions(SelectItem[] provinciasOptions) {
        this.provinciasOptions = provinciasOptions;
    }

    public SelectItem[] getTiposViaOptions() {
        if (dv == null) {
            dv = new direccionVista();
        }
        if (tiposViaOptions == null) {
            String[] tv = new String[dv.getTiposVia().size()];
            for (int i = 0; i < tv.length; i++) {
                tv[i] = dv.getTiposVia().get(i).getNombre();
            }
            tiposViaOptions = UtilidadesVista.createFilterOptions(tv);
        }
        return tiposViaOptions;
    }

    public void setTiposViaOptions(SelectItem[] tiposViaOptions) {
        this.tiposViaOptions = tiposViaOptions;
    }

    public List<TelefonoTipo> getTiposTelefono() {
        if (tiposTelefono == null) {
            tiposTelefono = tiposTelefonoFacade.findAll();
        }
        return tiposTelefono;
    }

    public void setTiposTelefono(List<TelefonoTipo> tiposTelefono) {
        this.tiposTelefono = tiposTelefono;
    }

    public List<AlumnoNivelEstudio> getNivelEstudios() {
        if (nivelEstudios == null) {
            nivelEstudios = anivelestudiosFacade.findAll();
        }
        return nivelEstudios;
    }

    public void setNivelEstudios(List<AlumnoNivelEstudio> nivelEstudios) {
        this.nivelEstudios = nivelEstudios;
    }

    public List<AlumnoAreaFuncional> getAreaFuncional() {
        if (areaFuncional == null) {
            areaFuncional = aAreaFuncionalFacade.findAll();
        }
        return areaFuncional;
    }

    public void setAreaFuncional(List<AlumnoAreaFuncional> areaFuncional) {
        this.areaFuncional = areaFuncional;
    }

    public List<AlumnoCategoriaProfesional> getCategoriaProfesional() {
        if (categoriaProfesional == null) {
            categoriaProfesional = acategProfFacade.findAll();
        }
        return categoriaProfesional;
    }

    public void setCategoriaProfesional(List<AlumnoCategoriaProfesional> categoriaProfesional) {
        this.categoriaProfesional = categoriaProfesional;
    }

    public List<AlumnoGrupoCotizacion> getGrupoCotizacion() {
        if (grupoCotizacion == null) {
            grupoCotizacion = agrupocotizacionFacade.findAll();
        }
        return grupoCotizacion;
    }

    public void setGrupoCotizacion(List<AlumnoGrupoCotizacion> grupoCotizacion) {
        this.grupoCotizacion = grupoCotizacion;
    }
}
