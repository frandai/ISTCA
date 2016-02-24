/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 *
 */
@ManagedBean(name = "Fecha")
@ViewScoped
public class Fecha implements Serializable {

    public static Date getFechaStringDiaMesAnio(String fecha) throws ParseException {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        return formatoDelTexto.parse(fecha);
    }
    
    public static String getMilisec(Date d){
        return ""+d.getTime();
    }

    public static String getFechaAnioMesDiaHoraMinutoSegundo(Date d) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(d);
    }

    public static String getPGes(String p) {
        if (p.contains("1900")) {
            return "[Pte. Gestionar]";
        }
        return p;
    }

    public static String getFechaDiaMesAnio(Date d) {
        if (d == null) {
            return "";
        }
        if (getAnio(d) == 1900) {
            return "[Pte. Gestionar]";
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(d);
    }

    public static String getFechaAnioMesFactura(Date d) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-");
        return df.format(d);
    }

    public static String getFechaHoraMinuto(Date d) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(d);
    }

    public static Date getFechaSetDias(Date fecha, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.DATE, dias);
        return cal.getTime();
    }

    public static Date getFechaSetMinutos(Date fecha, int minutos) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.MINUTE, minutos);
        return cal.getTime();
    }

    static String getfechaDDMMAA(Date date) {
        DateFormat df = new SimpleDateFormat("ddMMyy");
        return df.format(date);
    }
            static String getfechaAAMMDD(Date date) {
        DateFormat df = new SimpleDateFormat("yyMMdd");
        return df.format(date);
    }

    static String getDia(Date fechaCreacion) {
        DateFormat df = new SimpleDateFormat("dd");
        return df.format(fechaCreacion);
    }

    public static int getNumDiasEntre(Date fInicio, Date fFin) {
        return (int)( (fFin.getTime() - fInicio.getTime()) 
                 / (1000 * 60 * 60 * 24) );
    }
    
    public int getAnioActual(){
        return getAnio(new Date());
    }

    public static int getAnio(Date fecha) {
        try {


            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);
            return cal.get(Calendar.YEAR);
        } catch (Exception e) {
            return -9999;
        }
    }

    public static char getDiaSemana(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                return 'L';
            case Calendar.TUESDAY:
                return 'M';
            case Calendar.WEDNESDAY:
                return 'X';
            case Calendar.THURSDAY:
                return 'J';
            case Calendar.FRIDAY:
                return 'V';
            case Calendar.SATURDAY:
                return 'S';
            case Calendar.SUNDAY:
                return 'D';
            default:
                return '\0';
        }
    }

    static Date getDiaNumMesSiguiente(Date fFin, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fFin);
        cal.add(Calendar.MONTH, 1);
        cal.set(cal.DAY_OF_MONTH, i);
        return cal.getTime();
    }

    public static String getTimeZone() {
        return "GMT+" + ((int) (TimeZone.getDefault().getOffset(new Date().getTime()) / 60 / 60 / 1000));
    }

    public static String getStringMes(int mes) {
        switch (mes) {
            case 1:
                return "enero";
            case 2:
                return "febrero";
            case 3:
                return "marzo";
            case 4:
                return "abril";
            case 5:
                return "mayo";
            case 6:
                return "junio";
            case 7:
                return "julio";
            case 8:
                return "agosto";
            case 9:
                return "septiembre";
            case 10:
                return "octubre";
            case 11:
                return "noviembre";
            case 12:
                return "diciembre";
        }
        return "";
    }

    static String getfechaFechaDiaMesTextoAnio(Date d) {
        if (d == null) {
            return "";
        }
        DateFormat df1 = new SimpleDateFormat("dd");
        DateFormat df2 = new SimpleDateFormat("MM");
        String texto = "" + df1.format(d) + " de " + getStringMes(Integer.parseInt(df2.format(d))) + " del " + getAnio(d);
        return texto;
    }

    static int getMes(Date fecha) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);
            return (cal.get(Calendar.MONTH)+1);
        } catch (Exception e) {
            return -1;
        }
    }
}
