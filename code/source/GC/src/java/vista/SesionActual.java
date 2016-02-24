/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import bean.AyudaFacade;
import bean.ProveedorFacade;
import bean.UsuarioFacade;
import bean.VariablesSistemaFacade;
import datos.Ayuda;
import datos.Permiso;
import datos.Usuario;
import gestion.MatriculaGestion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;
import util.Email;
import util.Encriptacion;
import util.VariablesSistema;

/**
 *
 *
 */
@ManagedBean(name = "sesionActual")
@SessionScoped
public class SesionActual implements Serializable {

    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private ProveedorFacade provFacade;
    @EJB
    private VariablesSistemaFacade variablesSistemaFacade;
    @EJB
    private AyudaFacade ayudaFadace;
    datos.VariablesSistema variables = null;
    String usuario;
    String password;
    String usuario_recuerda;
    Usuario usuario_conectado = null;
    private String theme = "aristo";
    private Map<String, String> themes;
    private Map<String, MatriculaGestion> matriculasAFin;
    private boolean noUsuarios = false;
    private String textoAyuda;

    public String getTextoAyuda() {
        return textoAyuda;
    }

    public void setTextoAyuda(String textoAyuda) {
        this.textoAyuda = textoAyuda;
    }

    public boolean isNoUsuarios() {
        return noUsuarios;
    }

    public void setNoUsuarios(boolean noUsuarios) {
        this.noUsuarios = noUsuarios;
    }
    
    public boolean verAyuda(){
        boolean ayuda = (UtilidadesVista.getParametroReq("ayuda")!=null&&UtilidadesVista.getParametroReq("ayuda").equals("si"));
        cargaAyuda(null);
        return ayuda;
    }

    //DATOS PARA MIGRACION
    /*
     @ManagedProperty("#{direccionVista}")
     private direccionVista dVista;
     @ManagedProperty("#{desplegableVista}")
     private desplegableVista despVista;
     @EJB
     private ActividadFacade activfacade;
     @EJB
     private EmpresaFacade empFacade;
     private List<CpLocalidad> localidades;
     @EJB
     private TipoViaFacade tvFac;
     @EJB
     private EmpresaEstadoFacade eefac;
     @EJB
     private PersonaFacade persFac;
     @EJB
     private CColectivoFacade ccolectFacade;
     @EJB
     private EmpresaAgrupacionFacade agrupFacade;
     @EJB
     private AccionFormativaFacade affacade;
     @EJB
     private AccionFormativaModalidadFacade amfacade;
     @EJB
     private AccionFormativaGrupoFacade agfacade;
     @EJB
     private DiaSemanaFacade dsfacade;
     @EJB
     private GrupoFacade gfac;
     @EJB
     private HorarioFacade hfac;
     @EJB
     private MatriculaFacade mfac;
     @EJB
     private FormaPagoFacade fpfac;
     @EJB
     private ComercialFacade comFac;
     @EJB
     ComercialTipoFacade comTFac;
     @EJB
     FacturaFacade facFacade;
     @EJB
     FacturaEstadoFacade feFacade;
     public desplegableVista getDespVista() {
     return despVista;
     }
     public void setDespVista(desplegableVista despVista) {
     this.despVista = despVista;
     }
     public direccionVista getdVista() {
     return dVista;
     }
     public void setdVista(direccionVista dVista) {
     this.dVista = dVista;
     }
     */
    //FIN DATOS MIGRACION
    public Map<String, MatriculaGestion> getMatriculasAFin() {
        return matriculasAFin;
    }

    public void setMatriculasAFin(Map<String, MatriculaGestion> matriculasAFin) {
        this.matriculasAFin = matriculasAFin;
    }

    public Map<String, String> getThemes() {
        if (themes == null) {
            themes = new TreeMap<String, String>();
            themes.put("aristo", "aristo");
            themes.put("afterdark", "afterdark");
            themes.put("afternoon", "afternoon");
            themes.put("afterwork", "afterwork");
            themes.put("black-tie", "black-tie");
            themes.put("blitzer", "blitzer");
            themes.put("bluesky", "bluesky");
            themes.put("bootstrap", "bootstrap");
            themes.put("casablanca", "casablanca");
            themes.put("cruze", "cruze");
            themes.put("cupertino", "cupertino");
            themes.put("dark-hive", "dark-hive");
            themes.put("dot-luv", "dot-luv");
            themes.put("eggplant", "eggplant");
            themes.put("excite-bike", "excite-bike");
            themes.put("flick", "flick");
            themes.put("glass-x", "glass-x");
            themes.put("home", "home");
            themes.put("hot-sneaks", "hot-sneaks");
            themes.put("humanity", "humanity");
            themes.put("le-frog", "le-frog");
            themes.put("midnight", "midnight");
            themes.put("mint-choc", "mint-choc");
            themes.put("overcast", "overcast");
            themes.put("pepper-grinder", "pepper-grinder");
            themes.put("redmond", "redmond");
            themes.put("rocket", "rocket");
            themes.put("sam", "sam");
            themes.put("smoothness", "smoothness");
            themes.put("south-street", "south-street");
            themes.put("start", "start");
            themes.put("sunny", "sunny");
            themes.put("swanky-purse", "swanky-purse");
            themes.put("trontastic", "trontastic");
            themes.put("ui-darkness", "ui-darkness");
            themes.put("ui-lightness", "ui-lightness");
            themes.put("vader", "vader");
        }
        return themes;
    }

    public void setThemes(Map<String, String> themes) {
        this.themes = themes;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * Creates a new instance of SesionActual
     */
    public SesionActual() {
        matriculasAFin = new HashMap<String, MatriculaGestion>();
    }

    public Usuario getUsuario_conectado() {
        return usuario_conectado;
    }

    public void setUsuario_conectado(Usuario usuario_conectado) {
        this.usuario_conectado = usuario_conectado;
    }

    public boolean isUsuario_Conectado() {
        boolean is_conectado;
        try {
            is_conectado = (usuario_conectado != null && !((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString().contains("faces/JSF/vista/login.xhtml"));
        } catch (Exception e) {
            is_conectado = usuario_conectado != null;
        }
        return is_conectado;
    }

    public void cargaAyuda(ActionEvent actionEvent) {
        String url = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString();
        url = url.substring(url.indexOf("/faces/JSF/"), url.length());

        Ayuda ayuda = ayudaFadace.find(url);
        if (ayuda != null) {
            textoAyuda = ayuda.getAyuda();
        } else {
            textoAyuda = "No se encuentra la ayuda de "+url;
        }
    }

    public void salir(ActionEvent actionEvent) {
        usuario_conectado = null;
        UtilidadesVista uv = new UtilidadesVista();
        matriculasAFin.clear();
        uv.redireccionar("/faces/JSF/vista/login.xhtml");
    }

    public String getUsuario() {
        try {

            if (usuario_conectado == null && usuarioFacade.findAll().get(0) != null) {
                noUsuarios = false;
            } else {
                noUsuarios = (usuario_conectado == null);
            }
        } catch (Exception e) {
            noUsuarios = true;
        }
        if (noUsuarios) {
            UtilidadesVista uv = new UtilidadesVista();
            uv.redireccionar("/faces/JSF/vista/configuracion_sistema.xhtml");
        }
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario_recuerda() {
        return usuario_recuerda;
    }

    public void setUsuario_recuerda(String usuario_recuerda) {
        this.usuario_recuerda = usuario_recuerda;
    }

    public void cargaDatos(Usuario user) {
        usuario_conectado = user;

        if (variables == null) {
            variables = variablesSistemaFacade.findAll().get(0);
        }
        if (VariablesSistema.NIF_empresa_principal == null) {
            VariablesSistema.NIF_empresa_principal = variables.getNIFempresaprincipal();
        }
        if (VariablesSistema.dominioEmail == null) {
            VariablesSistema.dominioEmail = variables.getDominioEmail();
        }
        if (VariablesSistema.email_enviar == null) {
            VariablesSistema.email_enviar = variables.getEmailEnviar();
        }
        if (VariablesSistema.passwordEmail == null) {
            VariablesSistema.passwordEmail = variables.getPasswordEmail();
        }
        if (VariablesSistema.firmaEmail == null) {
            VariablesSistema.firmaEmail = variables.getFirmaEmail();
        }
        if (VariablesSistema.ruta_absoluta_proyecto == null) {
            VariablesSistema.ruta_absoluta_proyecto = variables.getRutaProyecto();
        }
        if (VariablesSistema.proveedor_principal == null) {
            VariablesSistema.proveedor_principal = provFacade.find(VariablesSistema.NIF_empresa_principal);
        }
        UtilidadesVista uv = new UtilidadesVista();
        uv.redireccionar("/faces/JSF/vista/bienvenido.xhtml");
        theme = leeTema();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("matriculaVista");
    }

    public void entra(ActionEvent event) throws ClassNotFoundException, SQLException {
        //FUNICONALIDAD PUSH
        /*PushContext pushContext = PushContextFactory.getDefault().getPushContext();  
          
         pushContext.push("/notifications", new FacesMessage("ENTRA", "HA ENTRADO "+usuario)); 
         */

        Usuario user = null;
        Usuario us = null;
        usuario = usuario.toLowerCase();
        try {
            us = usuarioFacade.findNamedQuery("Usuario.findByUsuario", "usuario", usuario);
        } catch (Exception e) {
        }
        if (us != null) {
            if (us.getUsuario().equals(usuario) && Encriptacion.getMD5(password).equals(us.getPassword())) {
                user = us;
            }
        }
        if (user == null) {
            UtilidadesVista.Mensaje("Usuario o Contraseña Incorrectos", FacesMessage.SEVERITY_ERROR);
        } else if (!user.getAccesible()) {
            UtilidadesVista.Mensaje("Usuario sin Acceso al Sistema", FacesMessage.SEVERITY_ERROR);
        } else {
            cargaDatos(user);
        }
    }

    public void recuerda(ActionEvent event) {
        Usuario us = null;
        try {
            us = usuarioFacade.findNamedQuery("Usuario.findByUsuario", "usuario", usuario_recuerda.toLowerCase());
        } catch (Exception e) {
        }
        if (us == null) {
            UtilidadesVista.Mensaje("Usuario o Contraseña Incorrectos", FacesMessage.SEVERITY_ERROR);
        } else if (!us.getAccesible()) {
            UtilidadesVista.Mensaje("Usuario sin Acceso al Sistema", FacesMessage.SEVERITY_ERROR);
        } else {
            try {
                Random r = new Random();
                String nuevaPassword = "";
                for (int i = 0; i < 7; i++) {
                    nuevaPassword += "" + (char) ((r.nextInt(20)) + 'a');
                }
                us.setPassword(Encriptacion.getMD5(nuevaPassword));
                usuarioFacade.edit(us);
                Email mail = new Email(util.VariablesSistema.email_enviar);
                mail.setPara(new String[]{us.getPersona().getEmail()});
                mail.setAsunto("Plataforma de Gestión de Cursos - Recuerdo de Contraseña");
                mail.setCuerpo("Su nueva contraseña para el usuario '" + us.getUsuario() + "' es '" + nuevaPassword + "'.");
                mail.enviar();
                UtilidadesVista.Mensaje("Se ha enviado un e-mail a " + us.getPersona().getEmail() + " con los nuevos datos de acceso.", FacesMessage.SEVERITY_INFO);
            } catch (Exception ex) {
                UtilidadesVista.Mensaje(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
            }

        }
    }

    private String leeTema() {
        String tema = "aristo";
        ObjectInputStream ois = null;
        try {
            File fichero = new File(VariablesSistema.ruta_absoluta_proyecto + "/docs/temas/" + getUsuario_conectado().getNif());
            ois = new ObjectInputStream(new FileInputStream(fichero));
            if (ois != null) {
                Object aux = ois.readObject();
                if (aux != null) {
                    if (aux instanceof String) {
                        tema = (String) aux;
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
        return tema;
    }

    public void guardaTema() {
        setTheme(theme);
        ObjectOutputStream oos = null;
        try {
            File fichero = new File(VariablesSistema.ruta_absoluta_proyecto + "/docs/temas/" + getUsuario_conectado().getNif());
            oos = new ObjectOutputStream(new FileOutputStream(fichero));
            if (oos != null) {

                oos.writeObject(theme);
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

    public boolean tienePermiso(int permiso) {
        try {
            if (noUsuarios) {
                boolean retorno = false;

                switch (permiso) {
                    case 1:
                        retorno = true;
                        break;
                    case 4:
                        retorno = true;
                        break;
                    case 16:
                        retorno = true;
                        break;
                    case 21:
                        retorno = true;
                        break;
                    case 27:
                        retorno = true;
                        break;
                }

                return retorno;
            }

            for (Permiso p : usuario_conectado.getPermisoList()) {
                if (p.getId() == permiso) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

}
