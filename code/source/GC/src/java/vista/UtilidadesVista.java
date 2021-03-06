/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.context.RequestContext;
import util.Excel;
import static util.Excel.crearEstilos;
import util.Fecha;

/**
 *
 *
 */
@ManagedBean(name = "utilidadesVista")
@SessionScoped
public class UtilidadesVista implements Serializable {

    /**
     * Creates a new instance of UtilidadesVista
     */
    public UtilidadesVista() {
    }

    public void redireccionar(String redireccion) {
        try {
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse())
                    .sendRedirect(((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getContextPath() + redireccion);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesVista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static SelectItem[] createFilterOptions(String[] data) {
        boolean defecto = false;
        for (String string : data) {
            if (string.contains("DEFECTO")) {
                defecto = true;
                break;
            }
        }
        SelectItem[] options = new SelectItem[data.length + 1];
        if (!defecto) {
            options[0] = new SelectItem("", "");
        } else {
            options = new SelectItem[data.length];
        }
        for (int i = 0; i < data.length; i++) {
            options[defecto ? i : i + 1] = new SelectItem(data[i].replace("DEFECTO", ""), data[i].replace("DEFECTO", ""));
        }

        return options;
    }

    public static void Mensaje(String mensaje, FacesMessage.Severity severidad) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, mensaje, mensaje));
    }

    public static String getParametroReq(String dato) {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter(dato);
    }

    public static Set<String> getParametrosReq() {
        Map<String, String[]> enum_param = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameterMap();
        Set set = enum_param.keySet();
        return set;
    }

    public static String getHoraString(Date hora) {
        if (hora == null) {
            return "";
        }
        java.text.SimpleDateFormat datef = new SimpleDateFormat("HH:mm");
        return datef.format(hora);
    }

    public static String getParametroSes(String dato) {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().getAttribute(dato).toString();
    }

    public String getURLPrevia() {
        try {
            String s = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getHeader("Referer");
            return s == null ? "" : s;
        } catch (Exception e) {
            return "";
        }
    }

    public static void setParametroSes(String dato, String valor) {
        ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().setAttribute(dato, valor);
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);


        Map<String, HSSFCellStyle> styles = crearEstilos(wb);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(styles.get("header"));
            sheet.autoSizeColumn(i);
        }
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            HSSFRow fila = sheet.getRow(i);


            for (int j = 0; j < fila.getPhysicalNumberOfCells(); j++) {
                HSSFCell cell = fila.getCell(j);
                cell.setCellStyle(styles.get("cell_normal"));
                try {
                    cell.setCellValue(cell.getStringCellValue().replaceAll("~~", ""));
                } catch (Exception e) {
                }

            }

        }
    }
}
