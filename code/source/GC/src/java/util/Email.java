/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Clase para enviar EMAILs
 *
 *
 */
public class Email {

    private String de;
    private String[] para = new String[0];
    private String[] cc = new String[0];
    private String[] bcc = new String[0];
    private String[] adjunto = new String[0];
    private String asunto;
    private String cuerpo;
    private DatosEmail datos;

    public Email(String de) throws Exception {
        if (!Validacion.validaEmail(de)) {
            throw new Exception("El email '" + de + "' no es válido.");
        }
        this.de = de;
        datos = VariablesSistema.getDatosEmail(de);
        if (datos.password == null) {
            throw new Exception("No existen datos para el email '" + de + "'.");
        }
    }

    public void enviar() throws Exception {
        if (para == null || para.length == 0) {
            throw new Exception("Falta el campo 'Para' en el email");
        }
        for (String p : para) {
            String email_invalidos = "";
            if (!Validacion.validaEmail(p)) {
                email_invalidos += p + " ";
            }
            if (!email_invalidos.equals("")) {
                throw new Exception("Los emails '" + email_invalidos + "'no son válidos.");
            }
        }
        if (asunto == null || asunto.equals("")) {
            throw new Exception("El email debe llevar asunto.");
        }
        if (cuerpo == null || cuerpo.equals("")) {
            throw new Exception("El email debe llevar cuerpo.");
        }
        if (!cuerpo.endsWith(datos.firma)) {
            cuerpo += datos.firma;
        }

        java.security.Security
                .addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        //Inicio código envio emails
        Properties prop = new Properties();
        prop.put("mail.smtp.host", datos.dominio);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(de, datos.password);
            }
        });
        MimeMessage msg = new MimeMessage(session);
        msg.setSubject(asunto, "utf-8");
        msg.setFrom(new InternetAddress(de));
        String TO = "";
        for (String p : para) {
            TO += p + ",";
        }
        if (!TO.equals("")) {
            msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(TO));
        }
        String CC = "";
        for (String p : cc) {
            CC += p + ",";
        }
        if (!CC.equals("")) {
            msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(CC));
        }
        String BCC = "";
        for (String p : bcc) {
            BCC += p + ",";
        }
        if (!BCC.equals("")) {
            msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(BCC));
        }
        MimeMultipart mp = new MimeMultipart("related");
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(cuerpo, "text/html; charset=utf-8");
        mp.addBodyPart(messageBodyPart);
        for (int i = 0; i < adjunto.length; i++) {
            FileDataSource fds = new FileDataSource(adjunto[i]);
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setFileName(fds.getName());
            mp.addBodyPart(messageBodyPart);
        }
        msg.setContent(mp);
        Transport.send(msg);
        //Fin código envio emails.

        System.out.println("[EMAIL][" + Fecha.getFechaAnioMesDiaHoraMinutoSegundo(new Date()) + "]Se ha enviado un E-mail: de: " + de + "; para: " + TO + "; cc: " + CC + "; bcc: " + BCC + "; asunto: " + asunto + ";");
    }

    public String[] getPara() {
        return para;
    }

    public void setPara(String[] para) {
        this.para = para;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public String[] getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(String[] adjunto) {
        for (int i = 0; i < adjunto.length; i++) {
            if (!adjunto[i].startsWith(VariablesSistema.ruta_absoluta_proyecto)) {
                adjunto[i] = VariablesSistema.ruta_absoluta_proyecto + adjunto[i];
            }

        }
        this.adjunto = adjunto;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
}
