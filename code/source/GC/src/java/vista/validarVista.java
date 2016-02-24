/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import util.Validacion;

/**
 *
 *dai
 */
@ManagedBean(name = "validarVista")
@SessionScoped
public class validarVista implements Serializable {

    public void validaNIF(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        if(((String) arg2).equals("")){return;}
        if (!Validacion.esNifValido(((String) arg2))) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El NIF escrito no es válido.", ""));
        }
    }

    public void validaEmail(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        if (!Validacion.validaEmail(((String) arg2)) && !(((String) arg2)).equals("")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Email escrito no es válido.", ""));
        }
    }

    public void validaNSS(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        if (((String) arg2).equals("")) {
            return;
        }
        if (!Validacion.validaNSS(((String) arg2), false)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El NSS escrito no es válido.", ""));
        }
    }

    public void validaNSSEmpresa(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        if (((String) arg2).equals("")) {
            return;
        }
        if (!Validacion.validaNSS(((String) arg2), true)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El NSS escrito no es válido.", ""));
        }
    }

    public void validaCCC(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        if (!Validacion.validaCCC(((String) arg2))) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El CCC '" + ((String) arg2) + "' no es válido.", ""));
        }
    }

    public void validaIBAN(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        if (((String) arg2).equals("")) {
            return;
        }
        if (!Validacion.validaIBAN(((String) arg2))) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El IBAN '" + ((String) arg2) + "' no es válido.", ""));
        }
    }

    public void validaTelefono(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        if (!Validacion.validaTelefono(((String) arg2))) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Teléfono '" + ((String) arg2) + "' no es válido.", ""));
        }
    }

    public String volverMatricula(String mat) {
        return "window.location = '../matricula/matricula.xhtml?matricula=" + mat + "'";
    }

    public String numeroAPrecio(Double precio) {
        if (precio != null) {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
            DecimalFormat df = (DecimalFormat) nf;
            df.applyPattern("###,###,###,##0.00");
            String output = df.format(precio);
            return output;
        } else {
            return "";
        }
    }

    public static Double precioPuntoSeparadorComaDecimal(String precio) throws ParseException {
        NumberFormat df = NumberFormat.getInstance(Locale.FRANCE);
        return df.parse(precio).doubleValue();
    }
}
