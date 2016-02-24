/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.itextpdf.text.DocumentException;
import datos.Alumno;
import datos.Comercial;
import datos.Direccion;
import datos.Empresa;
import datos.EmpresaMatriculaAnio;
import datos.EmpresaMatriculaCcc;
import datos.EmpresaTelefono;
import datos.Evento;
import datos.Factura;
import datos.Matricula;
import datos.MatriculaComercial;
import datos.MatriculaFactura;
import datos.Persona;
import datos.PersonaTelefono;
import datos.Tutor;
import datos.TutoriaHorario;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 *
 */
public class Documentacion {

    public static void enviarMailsDocumentacion(List<Matricula> matriculasEmail, boolean asesoria, BooleanGenerarDoc gen) throws Exception {
        if (matriculasEmail.isEmpty()) {
            return;
        }
        Email mail = new Email(util.VariablesSistema.email_enviar);
        String cuerpoIni = "<ul>";

        if (gen.isGenerar_RLT()) {
            cuerpoIni += "<li>Le adjuntamos en este e-mail el documento que debe de firmar la representación legal de los trabajadores donde se INFORMA FAVORABLEMENTE sobre el Plan de Formación. Es imprescindible que nos lo devuelva firmado lo antes posible por Fax 958990003 o e-mail.</li>";
        }
        if (gen.isGenerar_carta_rep_legal()) {
            cuerpoIni += "<li>Carta representante legal: Informacion para la empresa sobre el curso y su bonificación.</li>";
        }
        if (gen.isGenerar_datos_curso_alumno()) {
            cuerpoIni += "<li>Ficha curso: Informacion para el alumno sobre fecha de inicio y fin y horario de tutoria. Esta documentación deberá ser entregada al alumno.</li>";
        }
        if (gen.isGenerar_ficha_registro_alumno()) {
            cuerpoIni += "<li>Ficha registro alumno: Datos a confirmar por alumno. Esta documentación deberá ser entregada al alumno.</li>";
        }
        if (gen.isGenerar_factura()) {
            cuerpoIni += "<li>Factura: esta será a efectos contables, la empresa deberá esperar recepción del informe de empresa para aplicar la bonificación del curso.</li>";
        }
        if (gen.isGenerar_informe_fin_curso()) {
            cuerpoIni += "<li>Informe de fin de curso: Esta documentación deberá ser entregada a la asesoría para que proceda a bonificar el importe del curso en los seguros sociales del mes que corresponda."
                    + " Los pagos correspondientes a la formación bonificada deberán efectuarse antes del último día hábil para la presentación del boletín de cotización del mes de diciembre del ejercicio econónico que corresponda."
                    + "<br/>IMPORTANTE: ES NECESARIO RESPONDER A ESTE CORREO PARA COMPROBAR LA RECEPCIÓN DE LA INFORMACIÓN.</li>";
        }
        if (gen.isGenerar_diploma()) {
            cuerpoIni += "";
        }
        if (gen.isGenerar_recibi_diploma()) {
            cuerpoIni += "";
        }
        if (gen.isGenerar_acreditacion()) {
            cuerpoIni += "<li>Acreditación Jornada Laboral: Firma por parte del alumno del horario que emplea para la formación durante la Jornada Laboral. Esta documentación deberá ser entregada al alumno.</li>";
        }
        if (gen.isGenerar_autorizacion()) {
            cuerpoIni += "<li>Autorización de curso no relacionado con actividad: Esta documentación deberá de ser firmada por el representante legal y devuelta al centro de formación para la gestión de dicha formación.</li>";
        }
        if (gen.isGenerar_cartel_FEFE()) {
            cuerpoIni += "<li>Cartel FEFE.</li>";
        }
        if (gen.isGenerar_control_asistencia()) {
            cuerpoIni += "<li></li>";
        }
        if (gen.isGenerar_recibi_material()) {
            cuerpoIni += "<li></li>";
        }
        if (gen.getGenerar_matricula() != 0) {
            cuerpoIni += "<li>Anexo De Adhesión Al Convenio De Agrupación De Empresas.</li>";
        }

        cuerpoIni += "</ul>";

        for (Matricula matricula : matriculasEmail) {
            if (asesoria) {
                mail.setPara(matricula.getEmailEnvio_ase().split(","));
            } else {
                mail.setPara(matricula.getEmailEnvio().split(","));
            }
            ArrayList<String> adjuntos = generarPDFsDocumentacion(matricula, gen);
            mail.setAdjunto(adjuntos.toArray(new String[adjuntos.size()]));
            String cuerpo;
            String cuerpoFin = "";
            if (asesoria) {
                cuerpo = "Estimado colaborador,<br/><br/>"
                        + "Le adjuntamos la documentación de la empresa que ha realizado formación continua con nuestro centro.<br/>"
                        + "Le detallo documentación adjunta:<br/>";
                cuerpoFin = "<br/>Saludos y muchas gracias.";
            } else {
                if (gen.isGenerar_RLT()) {
                    cuerpo = "Estimado cliente:<br/><br/>"
                            + "Gracias por confiar en nuestro centro para formar a sus trabajadores.<br/>";
                } else {
                    cuerpo = "Estimado cliente:<br/><br/>"
                            + "Gracias por confiar en nuestro centro para formar a sus trabajadores.<br/>"
                            + "Le adjuntamos en éste e-mail documentación necesaria del alumno " + matricula.getAlumno().getPersona().getNombre() + " " + matricula.getAlumno().getPersona().getApellido1() + "  " + matricula.getAlumno().getPersona().getApellido2() + " (" + matricula.getAlumno().getPersona().getNif() + ") matriculado en el curso de " + matricula.getGrupo().getAccionFormativa1().getNombre() + ":";
                }
                cuerpoFin += "<br/>Saludos y muchas gracias.";
            }
            mail.setCuerpo(cuerpo + cuerpoIni + cuerpoFin);
            if (gen.isGenerar_RLT()) {
                mail.setAsunto(VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial() + " - " + matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRepresLegal().getNombreApellidos());
            } else {
                mail.setAsunto(VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial() + " - Curso Formación de " + matricula.getGrupo().getAccionFormativa1().getNombre() + " para " + matricula.getAlumno().getPersona().getNombre() + " " + matricula.getAlumno().getPersona().getApellido1() + "  " + matricula.getAlumno().getPersona().getApellido2() + " (" + matricula.getAlumno().getPersona().getNif() + ")");
            }
            mail.enviar();
        }
    }

    public static String enviarPDFMatricula(List<EmpresaMatriculaCcc> empresas) throws Exception {

        Random r = new Random();
        String ret = "/docs/temp/documentacion_" + r.nextInt() + ".pdf";
        ArrayList<String> documentos = new ArrayList<String>();
        for (EmpresaMatriculaCcc matricula : empresas) {
            documentos.add(generar_matricula(null, matricula, null));
        }
        Pdf.concatenarPDF(documentos, ret);
        return ret;
    }

    public static String enviarPDFMatricula(Map<Alumno, EmpresaMatriculaCcc> empresas) throws Exception {

        Random r = new Random();
        String ret = "/docs/temp/documentacion_" + r.nextInt() + ".pdf";
        ArrayList<String> documentos = new ArrayList<String>();
        for (Map.Entry<Alumno, EmpresaMatriculaCcc> e : empresas.entrySet()) {
            documentos.add(generar_matricula(null, e.getValue(), e.getKey()));
        }
        Pdf.concatenarPDF(documentos, ret);
        return ret;
    }

    public static String enviarPDFDocumentacion(List<Matricula> matriculasPDF, BooleanGenerarDoc gen) throws Exception {
        if (matriculasPDF.isEmpty()) {
            return "";
        }
        Random r = new Random();
        String ret = "/docs/temp/documentacion_" + r.nextInt() + ".pdf";
        ArrayList<String> documentos = new ArrayList<String>();
        ArrayList<Matricula> mat = new ArrayList<Matricula>();
        //Si hemos seleccionado generar matrículas por Empresa, eliminamos duplicados:
        if (gen.getGenerar_matricula() == 3) {
            for (Matricula matricula : matriculasPDF) {
                boolean insertar = true;
                for (Matricula matricula1 : mat) {
                    if (matricula1.getEmpresaMatriculaCcc().getEmpresaMatriculaCccPK().getNif()
                            .equals(matricula.getEmpresaMatriculaCcc().getEmpresaMatriculaCccPK().getNif())) {
                        insertar = false;
                        break;
                    }
                }
                if (insertar) {
                    mat.add(matricula);
                }
            }
        }
        for (Matricula matricula : ((gen.getGenerar_matricula() == 3) ? mat : matriculasPDF)) {
            documentos.addAll(generarPDFsDocumentacion(matricula, gen));
            //añadir separador? - MCarmen dice que no hace falta.
        }
        Pdf.concatenarPDF(documentos, ret);
        return ret;
    }

    public static ArrayList<String> generarPDFsDocumentacion(Matricula matricula, BooleanGenerarDoc gen) throws IOException, DocumentException {
        ArrayList<String> docs = new ArrayList<String>();
        if (gen.isGenerar_datos_curso_alumno()) {
            docs.add(generar_datos_curso_alumno(matricula));
        }
        if (gen.isGenerar_ficha_registro_alumno()) {
            docs.add(generar_ficha_registro_alumno(matricula));
        }
        if (gen.isGenerar_factura()) {
            docs.add(generar_factura(matricula));
        }
        if (gen.isGenerar_informe_fin_curso()) {
            docs.add(generar_informe_fin_curso(matricula));
        }
        if (gen.isGenerar_diploma()) {
            docs.add(generar_diploma(matricula, false));
        }
        if (gen.isGenerar_recibi_diploma()) {
            docs.add(generar_recibi_diploma(matricula));
        }
        if (gen.isGenerar_carta_rep_legal()) {
            docs.add(generar_carta_rep_legal(matricula));
        }
        if (gen.isGenerar_acreditacion()) {
            docs.add(generar_acreditacion(matricula));
        }
        if (gen.isGenerar_autorizacion()) {
            docs.add(generar_autorizacion(matricula));
        }
        if (gen.isGenerar_recibi_material()) {
            docs.add(generar_recibi_material(matricula));
        }
        if (gen.isGenerar_cartel_FEFE()) {
            docs.add(generar_cartel_FEFE(matricula));
        }
        if (gen.isGenerar_control_asistencia()) {
            docs.add(generar_control_asistencia(matricula));
        }
        if (gen.isGenerar_RLT()) {
            docs.add(generar_RLT(matricula));
        }
        if (gen.getGenerar_matricula() != 0) {
            switch (gen.getGenerar_matricula()) {
                case 1:
                    docs.add(generar_matricula(matricula, matricula.getEmpresaMatriculaCcc(), matricula.getAlumno()));
                    break;
                case 2:
                    docs.add(generar_matricula(null, matricula.getEmpresaMatriculaCcc(), matricula.getAlumno()));
                    break;
                case 3:
                    docs.add(generar_matricula(null, matricula.getEmpresaMatriculaCcc(), null));
                    break;
            }
        }
        return docs;
    }

    public static String generar_RLT(Matricula matricula) throws IOException, DocumentException {
        String doc = "/docs/temp/RLT_" + matricula.getId() + ".pdf";
        String plantilla = "/docs/plantillas/RLT.pdf";

        String s = Fecha.getFechaDiaMesAnio(new Date());

        String[][] campos = new String[][]{
            {"nombre_empresa", matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRazonSocial()},
            {"cif_empresa", matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getNif()},
            {"nombre_prov", VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial()},
            {"cif_prov", VariablesSistema.proveedor_principal.getEmpresa().getNif()},
            {"loc_empresa", matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getDireccion().getCpLocalidad().getLocalidad1().getNombre()},
            {"dia_hoy", s.split("/")[0]},
            {"mes_hoy", Fecha.getStringMes(Integer.parseInt(s.split("/")[1]))},
            {"anio_hoy", s.split("/")[2]}
        };
        Pdf.rellenarPDF(campos, VariablesSistema.ruta_absoluta_proyecto + plantilla, VariablesSistema.ruta_absoluta_proyecto + doc, false);
        return doc;
    }

    public static String generar_datos_curso_alumno(Matricula matricula) throws IOException, DocumentException {
        String doc = "/docs/temp/ficha_curso_" + matricula.getId() + ".pdf";
        String plantilla = "/docs/plantillas/ficha_curso_plantilla.pdf";
        Tutor tutor = null;
        String tlf1 = "";
        String fax = "";
        for (Tutor t : matricula.getGrupo().getTutorList()) {
            tutor = t;
            break;
        }
        for (EmpresaTelefono t : VariablesSistema.proveedor_principal.getEmpresa().getEmpresaTelefonoList()) {
            if (t.getTipo().getId().equals(3) || t.getTipo().getId().equals(5)) {
                tlf1 = t.getEmpresaTelefonoPK().getNumero();
            }
            if (t.getTipo().getId().equals(2)) {
                fax = t.getEmpresaTelefonoPK().getNumero();
            }
        }
        String horario = "Tutorías " + matricula.getGrupo().getHorario();
        String[][] campos = new String[][]{
            {"rs_empresa", matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRazonSocial()},
            {"nombre_alumno", matricula.getAlumno().getPersona().getNombre() + " " + matricula.getAlumno().getPersona().getApellido1() + " " + matricula.getAlumno().getPersona().getApellido2()},
            {"nombre_curso", matricula.getGrupo().getAccionFormativa1().getNombre()},
            {"id_af", "" + matricula.getGrupo().getAccionFormativa1().getId()},
            {"id_grupo", "" + matricula.getGrupo().getGrupoPK().getId()},
            {"modalidad", matricula.getGrupo().getAccionFormativa1().getModalidad().getNombre()},
            {"duracion", matricula.getGrupo().getAccionFormativa1().getHoras() + " horas"},
            {"finicio", Fecha.getFechaDiaMesAnio(matricula.getGrupo().getFInicio())},
            {"ffin", Fecha.getFechaDiaMesAnio(matricula.getGrupo().getFFin())},
            {"observaciones", matricula.getGrupo().getObservaciones().replaceAll("<br/>", "")},
            {"fax_proovedor", fax},
            {"fax_prov1", fax},
            {"tlf_prov1", tlf1},
            {"email_prov1", VariablesSistema.proveedor_principal.getEmpresa().getEmail()},
            {"nombre_tutor", tutor == null ? "" : tutor.getPersona().getNombre() + " " + tutor.getPersona().getApellido1()},
            {"nombre_tutor2", tutor == null || tutor.getPersona().getPersonaTelefonoList().isEmpty() ? "" : tutor.getPersona().getPersonaTelefonoList().get(0).getPersonaTelefonoPK().getNumero()},
            {"dato_fin", horario}};
        Pdf.rellenarPDF(campos, VariablesSistema.ruta_absoluta_proyecto + plantilla, VariablesSistema.ruta_absoluta_proyecto + doc, false);
        return doc;
    }

    public static String generar_ficha_registro_alumno(Matricula matricula) throws IOException, DocumentException {
        String doc = "/docs/temp/ficha_registro_alumno_" + matricula.getId() + ".pdf";
        String plantilla = "/docs/plantillas/ficha_alumno.pdf";
        String fax_proveedor = "";
        String telefono_proveedor = "";
        String telefono_alumno = "";
        for (EmpresaTelefono et : VariablesSistema.proveedor_principal.getEmpresa().getEmpresaTelefonoList()) {
            if (et.getTipo().getId().equals(new Integer("3"))) {
                telefono_proveedor = et.getEmpresaTelefonoPK().getNumero();
                break;
            }
        }
        for (EmpresaTelefono et : VariablesSistema.proveedor_principal.getEmpresa().getEmpresaTelefonoList()) {
            if (et.getTipo().getId().equals(new Integer("2"))) {
                fax_proveedor = et.getEmpresaTelefonoPK().getNumero();
                break;
            }
        }
        for (PersonaTelefono pt : matricula.getAlumno().getPersona().getPersonaTelefonoList()) {
            telefono_alumno = pt.getPersonaTelefonoPK().getNumero();
            break;
        }
        String[][] campos = new String[][]{
            //{"nombre_empresa", VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial()},
            //{"fax_prov1", fax_proveedor},
            {"fichaalumno", "Con el objeto de certificar la entrega del curso de formación y finalizar su inscripción como alumn" + (matricula.getAlumno().getPersona().getMasculino() ? "o" : "a") + " de " + VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial() + " "
                + "es necesario que cumplimente o corrija los posibles errores de los datos que aparecen"
                + " a continuación y nos los envíe al centro de formación (fax nº " + fax_proveedor + " o "+util.VariablesSistema.proveedor_principal.getEmpresa().getEmail()+"). "
                + "Es muy importante que todos los campos estén cumplimentados, para la correcta tramitación de la bonificación del coste del curso."},
            {"nombre_al", matricula.getAlumno().getPersona().getNombre()},
            {"apellidos_al", matricula.getAlumno().getPersona().getApellido1() + " " + matricula.getAlumno().getPersona().getApellido2()},
            {"dni_al", matricula.getAlumno().getPersona().getNif()},
            {"nss_al", matricula.getAlumno().getPersona().getNss()},
            {"direccion_al", matricula.getAlumno().getPersona().getDireccion() == null ? "" : matricula.getAlumno().getPersona().getDireccion().getdireccionString()},
            {"localidad_al", matricula.getAlumno().getPersona().getDireccion() == null ? "" : matricula.getAlumno().getPersona().getDireccion().getCpLocalidad().getLocalidad1().getNombre()},
            {"provincia_al", matricula.getAlumno().getPersona().getDireccion() == null ? "" : matricula.getAlumno().getPersona().getDireccion().getCpLocalidad().getLocalidad1().getProvincia().getNombre()},
            {"cp_al", matricula.getAlumno().getPersona().getDireccion() == null ? "" : "" + matricula.getAlumno().getPersona().getDireccion().getCpLocalidad().getCpLocalidadPK().getCp()},
            {"fnac_al", matricula.getAlumno().getPersona().getFechaNacimientoString()},
            {"email_al", matricula.getAlumno().getPersona().getEmail()},
            {"tlf_al", telefono_alumno},
            {"af_1", matricula.getAlumno().getAreaFuncional().getId().equals(new Short("1")) ? "X" : "_"},
            {"af_2", matricula.getAlumno().getAreaFuncional().getId().equals(new Short("3")) ? "X" : "_"},
            {"af_3", matricula.getAlumno().getAreaFuncional().getId().equals(new Short("5")) ? "X" : "_"},
            {"af_4", matricula.getAlumno().getAreaFuncional().getId().equals(new Short("2")) ? "X" : "_"},
            {"af_5", matricula.getAlumno().getAreaFuncional().getId().equals(new Short("4")) ? "X" : "_"},
            {"cp_1", matricula.getAlumno().getCategoriaProfesional().getId().equals(new Short("1")) ? "X" : "_"},
            {"cp_2", matricula.getAlumno().getCategoriaProfesional().getId().equals(new Short("3")) ? "X" : "_"},
            {"cp_3", matricula.getAlumno().getCategoriaProfesional().getId().equals(new Short("4")) ? "X" : "_"},
            {"cp_4", matricula.getAlumno().getCategoriaProfesional().getId().equals(new Short("5")) ? "X" : "_"},
            {"gc", "" + (matricula.getAlumno().getGrupoCotizacion().getId().equals(new Short("1")) ? "X" : "_") + "       " + (matricula.getAlumno().getGrupoCotizacion().getId().equals(new Short("2")) ? "X" : "_") + "        " + (matricula.getAlumno().getGrupoCotizacion().getId().equals(new Short("3")) ? "X" : "_") + "       " + (matricula.getAlumno().getGrupoCotizacion().getId().equals(new Short("4")) ? "X" : "_") + "       " + (matricula.getAlumno().getGrupoCotizacion().getId().equals(new Short("5")) ? "X" : "_") + "        " + (matricula.getAlumno().getGrupoCotizacion().getId().equals(new Short("6")) ? "X" : "_") + "        " + (matricula.getAlumno().getGrupoCotizacion().getId().equals(new Short("7")) ? "X" : "_") + "        " + (matricula.getAlumno().getGrupoCotizacion().getId().equals(new Short("8")) ? "X" : "_") + "        " + (matricula.getAlumno().getGrupoCotizacion().getId().equals(new Short("9")) ? "X" : "_") + "        " + (matricula.getAlumno().getGrupoCotizacion().getId().equals(new Short("10")) ? "X" : "_") + ""},
            {"campo1", matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRazonSocial() + " " + matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getNif()},
            {"na_1", matricula.getAlumno().getNivelEstudios().getId().equals(new Short("1")) ? "X" : "_"},
            {"na_2", matricula.getAlumno().getNivelEstudios().getId().equals(new Short("2")) ? "X" : "_"},
            {"na_3", matricula.getAlumno().getNivelEstudios().getId().equals(new Short("3")) ? "X" : "_"},
            {"na_4", matricula.getAlumno().getNivelEstudios().getId().equals(new Short("4")) ? "X" : "_"},
            {"na_5", matricula.getAlumno().getNivelEstudios().getId().equals(new Short("5")) ? "X" : "_"},
            {"afgrupo", matricula.getGrupo().getNombre()},
            {"nombre_curso", matricula.getGrupo().getAccionFormativa1().getNombre()},
            {"accion_form", matricula.getGrupo().getAccionFormativa1().getId() + "/" + matricula.getGrupo().getGrupoPK().getId()},
            {"nom_prov1", VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial()},
            {"tlf_prov2", telefono_proveedor},
            {"fax_prov2", fax_proveedor}
        };
        Pdf.rellenarPDF(campos, VariablesSistema.ruta_absoluta_proyecto + plantilla, VariablesSistema.ruta_absoluta_proyecto + doc, false);
        return doc;
    }

    public static String generar_factura(Matricula matricula) throws IOException, DocumentException {
        String ret = "/docs/temp/factura_matricula_" + matricula.getId() + ".pdf";
        ArrayList<String> listaFacturas = new ArrayList<String>();
        for (Factura f : matricula.getFacturaList()) {
            if (f.isSeleccionado()) {
                listaFacturas.add(generar_factura(f));
                f.setSeleccionado(false);
            }
        }
        Pdf.concatenarPDF(listaFacturas, ret);
        return ret;
    }

    public static String generar_factura(Factura factura) throws IOException, DocumentException {
        String doc = "/docs/temp/factura_" + factura.getId() + ".pdf";
        String plantilla = "/docs/plantillas/plant_factura.pdf";
        Matricula mat = null;
        MatriculaFactura costeImparticion = null;
        MatriculaFactura costeOrganizacion = null;
        for (MatriculaFactura mf : factura.getMatriculaFacturaList()) {
            mat = mf.getMatricula1();
            break;
        }

        for (MatriculaFactura mf : factura.getMatriculaFacturaList()) {
            if (mf.getObservaciones().contains("IMPART")) {
                costeImparticion = mf;
            }
            if (mf.getObservaciones().contains("ORGANIZ")) {
                costeOrganizacion = mf;
            }
        }

        String datos_prov = VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial() + "\n"
                + VariablesSistema.proveedor_principal.getEmpresa().getDireccion().getTipoVia().getAbreviatura() + " " + VariablesSistema.proveedor_principal.getEmpresa().getDireccion().getVia() + "\n"
                + "Nº " + VariablesSistema.proveedor_principal.getEmpresa().getDireccion().getNumero() + (VariablesSistema.proveedor_principal.getEmpresa().getDireccion().getExtra().equals("") ? "" : (", " + VariablesSistema.proveedor_principal.getEmpresa().getDireccion().getExtra())) + "\n"
                + VariablesSistema.proveedor_principal.getEmpresa().getDireccion().getCpLocalidad().getCpLocalidadPK().getCp() + " " + VariablesSistema.proveedor_principal.getEmpresa().getDireccion().getCpLocalidad().getLocalidad1().getNombre() + "\n"
                + "CIF: " + VariablesSistema.proveedor_principal.getNif();
        Empresa e = mat.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa();
        String datos_emp = e.getRazonSocial() + "\n"
                + e.getDireccion().getTipoVia().getAbreviatura() + " " + e.getDireccion().getVia() + "\n"
                + "Nº " + e.getDireccion().getNumero() + (e.getDireccion().getExtra().equals("") ? "" : (", " + e.getDireccion().getExtra())) + "\n"
                + e.getDireccion().getCpLocalidad().getCpLocalidadPK().getCp() + " " + e.getDireccion().getCpLocalidad().getLocalidad1().getNombre() + "";

        String ccc = "";
        for (EmpresaMatriculaCcc emc : VariablesSistema.proveedor_principal.getEmpresa().getEmpresaMatricula().getEmpresaMatriculaCccList()) {
            if (!emc.getEmpresaMatriculaCccPK().getCcc().equals("")) {
                ccc = emc.getEmpresaMatriculaCccPK().getCcc();
            }
        }

        String[][] campos = new String[][]{
            {"datos_prov", datos_prov},
            {"cccprov", (factura.getFormaPago().getId().equals(1) || factura.getFormaPago().getId().equals(3)) ? "CCC: " + ccc : ""},
            {"direccion", datos_emp},
            {"cif_empresa", e.getNif()},
            {"numero_fac", factura.getId()},
            {"fecha_fac", util.Fecha.getFechaDiaMesAnio(factura.getFecha())},
            {"pago_fac", factura.getFormaPago().getNombre()},
            {"id_af", mat.getGrupo().getAccionFormativa1().getId() + "/" + mat.getGrupo().getGrupoPK().getId()},
            {"nom_af", mat.getGrupo().getAccionFormativa1().getNombre()},
            {"modalidad", mat.getGrupo().getAccionFormativa1().getModalidad().getNombre()},
            {"duracion", mat.getGrupo().getAccionFormativa1().getHoras() + " horas"},
            {"finicio", Fecha.getFechaDiaMesAnio(mat.getGrupo().getFInicio())},
            {"ffin", Fecha.getFechaDiaMesAnio(mat.getGrupo().getFFin())},
            {"alumno", mat.getAlumno().getPersona().getNombre() + " " + mat.getAlumno().getPersona().getApellido1() + " " + mat.getAlumno().getPersona().getApellido2() + " - " + mat.getAlumno().getPersona().getNif()},
            {"c_imparticion", Validacion.Redondear(costeImparticion.getPrecio()) + " €"},
            {"c_gestion", Validacion.Redondear(costeOrganizacion.getPrecio()) + " €"},
            {"total_fac", (Validacion.Redondear(costeImparticion.getPrecio() + costeOrganizacion.getPrecio())) + " €"}
        };
        Pdf.rellenarPDF(campos, VariablesSistema.ruta_absoluta_proyecto + plantilla, VariablesSistema.ruta_absoluta_proyecto + doc, false);
        return doc;
    }

    public static String generar_informe_fin_curso(Matricula matricula) throws IOException, DocumentException {
        String doc = "/docs/temp/informe_fin_curso_" + matricula.getId() + ".pdf";
        String plantilla = "/docs/plantillas/plantilla_fin_curso.pdf";
        Persona repleg = matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRepresLegal();
        Direccion dempresa = matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getDireccion();
        String fax_proveedor = "";
        String telefono_proveedor = "";
        for (EmpresaTelefono et : VariablesSistema.proveedor_principal.getEmpresa().getEmpresaTelefonoList()) {
            if (et.getTipo().getId().equals(new Integer("3"))) {
                telefono_proveedor = et.getEmpresaTelefonoPK().getNumero();
                break;
            }
        }
        for (EmpresaTelefono et : VariablesSistema.proveedor_principal.getEmpresa().getEmpresaTelefonoList()) {
            if (et.getTipo().getId().equals(new Integer("2"))) {
                fax_proveedor = et.getEmpresaTelefonoPK().getNumero();
                break;
            }
        }
        String[][] campos = new String[][]{
            {"aa1", matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRazonSocial()},
            {"aa2", repleg.getNombre() + " " + repleg.getApellido1() + " " + repleg.getApellido2()},
            {"aa3", dempresa.getTipoVia().getAbreviatura() + " " + dempresa.getVia() + ", " + dempresa.getNumero() + (dempresa.getExtra().equals("") ? "" : ", " + dempresa.getExtra())},
            {"aa4", dempresa.getCpLocalidad().getCpLocalidadPK().getCp() + ", " + dempresa.getCpLocalidad().getLocalidad1().getNombre() + " (" + dempresa.getCpLocalidad().getLocalidad1().getProvincia().getNombre() + ")"},
            {"replegal", (repleg.getMasculino() ? "Estimado " : "Estimada ") + repleg.getNombre() + " " + repleg.getApellido1() + " " + repleg.getApellido2()},
            {"tlfp1", telefono_proveedor},
            {"faxp1", fax_proveedor},
            {"mailp1", VariablesSistema.proveedor_principal.getEmpresa().getEmail()},
            {"aa01", repleg.getNombre() + " " + repleg.getApellido1() + " " + repleg.getApellido2()},
            {"repleg2", (repleg.getMasculino() ? "Estimado Sr.: " : "Estimada Sra.: ") + repleg.getNombre() + " " + repleg.getApellido1() + " " + repleg.getApellido2()},
            {"trabajador", matricula.getAlumno().getPersona().getNombre() + " " + matricula.getAlumno().getPersona().getApellido1() + " " + matricula.getAlumno().getPersona().getApellido2()},
            {"curso", matricula.getGrupo().getAccionFormativa1().getNombre()},
            {"af", matricula.getGrupo().getAccionFormativa1().getId() + "/" + matricula.getGrupo().getGrupoPK().getId()},
            {"horas", "" + matricula.getGrupo().getAccionFormativa1().getHoras()},
            {"fini_ffin", Fecha.getFechaDiaMesAnio(matricula.getGrupo().getFInicio()) + "\n" + Fecha.getFechaDiaMesAnio(matricula.getGrupo().getFFin())},
            {"c_impart", "" + matricula.getC_imparticion() + " €"},
            {"c_org", "" + matricula.getC_organizacion() + " €"},
            {"c_tot", "" + matricula.getPrecio() + " €"},
            {"importe_b", "" + matricula.getImporteBonificar() + " €"},
            {"mes_b", Fecha.getStringMes(Integer.parseInt(Fecha.getFechaDiaMesAnio(matricula.getGrupo().getFFin()).split("/")[1]))},
            {"aport_m", matricula.getC_salariales() + " €"},
            {"priv_abon", "0.0 €"},
            {"priv_cost", matricula.getC_salariales() + " €"},
            {"tlfp2", telefono_proveedor},
            {"faxp2", fax_proveedor},
            {"mailp2", VariablesSistema.proveedor_principal.getEmpresa().getEmail()}
        };
        Pdf.rellenarPDF(campos, VariablesSistema.ruta_absoluta_proyecto + plantilla, VariablesSistema.ruta_absoluta_proyecto + doc, false);
        return doc;
    }

    public static String generar_diploma(Matricula matricula, boolean solo_principal) throws IOException, DocumentException {
        return generar_diploma(matricula, matricula.getAlumno().getNif(), matricula.getAlumno().getPersona().getNombre() + " " + matricula.getAlumno().getPersona().getApellido1() + " " + matricula.getAlumno().getPersona().getApellido2(), solo_principal);
    }

    public static String generar_diploma(Matricula matricula, String nif_alumno, String nombre_alumno, boolean solo_principal) throws IOException, DocumentException {
        if (solo_principal) {
            Random r = new Random();
            String doc = "/docs/temp/diploma_" + matricula.getId() + "_" + nif_alumno.replace(" ", "_") + "_" + r.nextInt() + ".pdf";
            String plantilla = "/docs/plantillas/diploma.pdf";
            String c = matricula.getGrupo().getAccionFormativa1().getContenidos();
            String[] cont = new String[4];
            for (int i = 0; i < cont.length; i++) {
                if (c.length() > 900) {
                    cont[i] = c.substring(0, c.substring(0, 900).lastIndexOf(" "));
                    c = c.substring(c.substring(0, 900).lastIndexOf(" "));
                } else {
                    cont[i] = c;
                    c = "";
                }
            }
            nombre_alumno = nombre_alumno + " con DNI " + nif_alumno;
            String[][] campos = new String[][]{
                {"nombre_alumno", nombre_alumno},
                {"diploma2", "Prestando sus servicios en la Empresa " + matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRazonSocial() + ", ha participado con aprovechamiento en la Acción Formativa nº " + matricula.getGrupo().getGrupoPK().getAccionFormativa() + "/" + matricula.getGrupo().getGrupoPK().getId() + ""
                    + " denominada " + matricula.getGrupo().getAccionFormativa1().getNombre() + ", de Formación Continua, impartida en la modalidad formativa " + matricula.getGrupo().getAccionFormativa1().getModalidad().getNombre() + ", entre las fechas "
                    + "" + Fecha.getFechaDiaMesAnio(matricula.getGrupo().getFInicio()) + " y " + Fecha.getFechaDiaMesAnio(matricula.getGrupo().getFFin()) + ", con un total de " + matricula.getGrupo().getAccionFormativa1().getHoras() + " horas y según el contenido especifico en el reverso."},
                {"lugar_fecha", VariablesSistema.proveedor_principal.getEmpresa().getDireccion().getCpLocalidad().getLocalidad1().getNombre() + ", " + Fecha.getFechaDiaMesAnio(new Date())},
                {"contenido1", cont[0]},
                {"proveedor_princ",VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial()},
                {"contenido2", cont[1]},
                {"contenido3", cont[2]},
                {"contenido4", cont[3]}
            };
            Pdf.rellenarPDF(campos, VariablesSistema.ruta_absoluta_proyecto + plantilla, VariablesSistema.ruta_absoluta_proyecto + doc, false);
            return doc;
        } else {
            String doc = "/docs/temp/diploma__" + matricula.getId() + ".pdf";
            ArrayList<String> documentos = new ArrayList<String>();
            documentos.add(generar_diploma(matricula, matricula.getAlumno().getNif(), (matricula.getAlumno().getPersona().getMasculino() ? "D " : "Dña ") + matricula.getAlumno().getPersona().getNombre() + " " + matricula.getAlumno().getPersona().getApellido1() + " " + matricula.getAlumno().getPersona().getApellido2(), true));
            for (Evento ev : matricula.getEventoList()) {
                if (ev.getTipoDescripcionEvento() == descripcionEventos.DIPLOMA_ADICCIONAL) {
                    documentos.add(generar_diploma(matricula, ev.getDescripcion().split("<>")[2], "D/Dña " + ev.getDescripcion().split("<>")[1], true));
                }
            }
            Pdf.concatenarPDF(documentos, doc);
            return doc;
        }
    }

    public static String generar_recibi_diploma(Matricula matricula) throws IOException, DocumentException {
        String fax_proveedor = "";
        String telefono_proveedor = "";
        for (EmpresaTelefono et : VariablesSistema.proveedor_principal.getEmpresa().getEmpresaTelefonoList()) {
            if (et.getTipo().getId().equals(new Integer("3"))) {
                telefono_proveedor = et.getEmpresaTelefonoPK().getNumero();
                break;
            }
        }
        for (EmpresaTelefono et : VariablesSistema.proveedor_principal.getEmpresa().getEmpresaTelefonoList()) {
            if (et.getTipo().getId().equals(new Integer("2"))) {
                fax_proveedor = et.getEmpresaTelefonoPK().getNumero();
                break;
            }
        }
        Persona alumno = matricula.getAlumno().getPersona();
        String doc = "/docs/temp/recibi_diploma_" + matricula.getId() + ".pdf";
        String plantilla = "/docs/plantillas/recibi_diploma.pdf";
        Direccion dempresa = matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getDireccion();
        String[][] campos = new String[][]{
            {"aa1", matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRazonSocial()},
            {"aa2", dempresa.getTipoVia().getAbreviatura() + " " + dempresa.getVia() + ", " + dempresa.getNumero() + (dempresa.getExtra().equals("") ? "" : ", " + dempresa.getExtra())},
            {"aa3", dempresa.getCpLocalidad().getCpLocalidadPK().getCp() + ", " + dempresa.getCpLocalidad().getLocalidad1().getNombre() + " (" + dempresa.getCpLocalidad().getLocalidad1().getProvincia().getNombre() + ")"},
            {"de_diploma", matricula.getAlumno().getPersona().getNombre() + " " + matricula.getAlumno().getPersona().getApellido1() + " " + matricula.getAlumno().getPersona().getApellido2()},
            {"dni", matricula.getAlumno().getPersona().getNif()},
            {"curso", "Curso: " + matricula.getGrupo().getAccionFormativa1().getNombre()},
            {"af", "" + matricula.getGrupo().getAccionFormativa1().getId()},
            {"grupo", "" + matricula.getGrupo().getGrupoPK().getId()},
            {"fax", fax_proveedor},
            {"tlfp1", telefono_proveedor},
            {"faxp1", fax_proveedor},
            {"emailp1", VariablesSistema.proveedor_principal.getEmpresa().getEmail()},
            {"aa", "A/A: " + alumno.getNombre() + " " + alumno.getApellido1() + " " + alumno.getApellido2()}
        };
        Pdf.rellenarPDF(campos, VariablesSistema.ruta_absoluta_proyecto + plantilla, VariablesSistema.ruta_absoluta_proyecto + doc, false);
        return doc;
    }

    public static String generar_carta_rep_legal(Matricula matricula) throws IOException, DocumentException {
        String doc = "/docs/temp/carta_rep_legal_" + matricula.getId() + ".pdf";
        String plantilla = "/docs/plantillas/repleg.pdf";
        String fax_proveedor = "";
        String telefono_proveedor = "";
        for (EmpresaTelefono et : VariablesSistema.proveedor_principal.getEmpresa().getEmpresaTelefonoList()) {
            if (et.getTipo().getId().equals(new Integer("3"))) {
                telefono_proveedor = et.getEmpresaTelefonoPK().getNumero();
                break;
            }
        }
        for (EmpresaTelefono et : VariablesSistema.proveedor_principal.getEmpresa().getEmpresaTelefonoList()) {
            if (et.getTipo().getId().equals(new Integer("2"))) {
                fax_proveedor = et.getEmpresaTelefonoPK().getNumero();
                break;
            }
        }
        Persona repleg = matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRepresLegal();
        Direccion dempresa = matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getDireccion();
        String[][] campos = new String[][]{
            {"razon_social_empresa", matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRazonSocial()},
            {"representante_legal_empresa", repleg.getNombre() + " " + repleg.getApellido1() + " " + repleg.getApellido2()},
            {"direccion_empresa", dempresa.getTipoVia().getAbreviatura() + " " + dempresa.getVia() + ", " + dempresa.getNumero() + (dempresa.getExtra().equals("") ? "" : ", " + dempresa.getExtra())},
            {"cp_empresa", "" + dempresa.getCpLocalidad().getCpLocalidadPK().getCp()},
            {"localidad_empresa", dempresa.getCpLocalidad().getLocalidad1().getNombre()},
            {"provincia_empresa", dempresa.getCpLocalidad().getLocalidad1().getProvincia().getNombre()},
            {"nonrep", (repleg.getMasculino() ? "Estimado " : "Estimada ") + repleg.getNombre() + " " + repleg.getApellido1() + " " + repleg.getApellido2()},
            {"tlf_prov_1", telefono_proveedor},
            {"fax_prov_1", fax_proveedor},
            {"email_prov_1", VariablesSistema.proveedor_principal.getEmpresa().getEmail()}};
        Pdf.rellenarPDF(campos, VariablesSistema.ruta_absoluta_proyecto + plantilla, VariablesSistema.ruta_absoluta_proyecto + doc, false);

        return doc;
    }

    private static String generar_acreditacion(Matricula matricula) throws IOException, DocumentException {
        String doc = "/docs/temp/acreditacion_jornada_laboral" + matricula.getId() + ".pdf";
        String plantilla = "/docs/plantillas/acreditacion_plant.pdf";
        String fax_proveedor = "";
        String telefono_proveedor = "";
        for (EmpresaTelefono et : VariablesSistema.proveedor_principal.getEmpresa().getEmpresaTelefonoList()) {
            if (et.getTipo().getId().equals(new Integer("3"))) {
                telefono_proveedor = et.getEmpresaTelefonoPK().getNumero();
                break;
            }
        }
        for (EmpresaTelefono et : VariablesSistema.proveedor_principal.getEmpresa().getEmpresaTelefonoList()) {
            if (et.getTipo().getId().equals(new Integer("2"))) {
                fax_proveedor = et.getEmpresaTelefonoPK().getNumero();
                break;
            }
        }
        Persona RepresLeg = matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRepresLegal();
        String[][] campos = new String[][]{
            {"empresa", "Empresa Bonificada: " + matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRazonSocial() + ", CIF: " + matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getNif()},
            {"denom_accion_form", matricula.getGrupo().getAccionFormativa1().getNombre()},
            {"n_af_grupo", "" + matricula.getGrupo().getAccionFormativa1().getId() + "/" + matricula.getGrupo().getGrupoPK().getId()},
            {"finicio", Fecha.getFechaDiaMesAnio(matricula.getGrupo().getFInicio())},
            {"ffin", Fecha.getFechaDiaMesAnio(matricula.getGrupo().getFFin())},
            {"alumno",
                (matricula.getAlumno().getPersona().getMasculino() ? "D (alumno): " : "Dª (alumna): ")
                + matricula.getAlumno().getPersona().getNombre() + " " + matricula.getAlumno().getPersona().getApellido1() + " " + matricula.getAlumno().getPersona().getApellido2()
                + " con DNI: " + matricula.getAlumno().getPersona().getNif()
            },
            {"tlf_prov_1", telefono_proveedor},
            {"fax_prov_1", fax_proveedor},
            {"email_prov_1", VariablesSistema.proveedor_principal.getEmpresa().getEmail()},
            {"repleg", "\n" + (RepresLeg.getMasculino() ? " D: " : " Dª: ") + RepresLeg.getNombre() + " " + RepresLeg.getApellido1() + " " + RepresLeg.getApellido2() + "\n "
                + "Con NIF " + RepresLeg.getNif() + " como representante legal de: " + matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRazonSocial() + " \n Con CIF:" + matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getNif()},
            {"tlf_prov_2", telefono_proveedor},
            {"fax_prov_2", fax_proveedor},
            {"email_prov_2", VariablesSistema.proveedor_principal.getEmpresa().getEmail()}
        };
        Pdf.rellenarPDF(campos, VariablesSistema.ruta_absoluta_proyecto + plantilla, VariablesSistema.ruta_absoluta_proyecto + doc, false);
        return doc;
    }

    private static String generar_recibi_material(Matricula matricula) throws IOException, DocumentException {
        String doc = "/docs/temp/recibi_material_" + matricula.getId() + ".pdf";
        String plantilla = "/docs/plantillas/recibi_material.pdf";
        String[][] campos = new String[][]{
            {"recibi", ((matricula.getAlumno().getPersona().getMasculino() ? "D:" : "Dña:") + " " + matricula.getAlumno().getPersona().getNombre() + " " + matricula.getAlumno().getPersona().getApellido1() + " " + matricula.getAlumno().getPersona().getApellido2() + "\n"
                + "con DNI " + matricula.getAlumno().getPersona().getNif() + " ha recibido el material del curso denominado " + matricula.getGrupo().getAccionFormativa1().getNombre() + " de " + VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial()) + "."}
        };
        Pdf.rellenarPDF(campos, VariablesSistema.ruta_absoluta_proyecto + plantilla, VariablesSistema.ruta_absoluta_proyecto + doc, false);
        return doc;
    }

    public static String generar_excel_mensajeria(List<Matricula> matriculas) throws IOException, DocumentException {
        Random r = new Random();
        String doc = "/docs/temp/excel_mensajeria_" + r.nextInt() + ".xls";
        String[][] datos = new String[matriculas.size() + 1][10];
        datos[0][0] = "Razón Social";
        datos[0][1] = "Dirección Envío";
        datos[0][2] = "Código Postal";
        datos[0][3] = "Localidad";
        datos[0][4] = "Provincia";
        datos[0][5] = "Teléfono";
        datos[0][6] = "Móvil";
        datos[0][7] = "Representante";
        datos[0][8] = "Observaciones";
        datos[0][9] = "Referencia";
        for (int i = 1; i < datos.length; i++) {
            datos[i][0] = "" + matriculas.get(i - 1).getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRazonSocial();
            Direccion direccion_envio = null;
            if (matriculas.get(i - 1).getDireccionEnvio() != null && matriculas.get(i - 1).getDireccionEnvio().getId() != null) {
                direccion_envio = matriculas.get(i - 1).getDireccionEnvio();
            } else {
                direccion_envio = matriculas.get(i - 1).getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getDireccion();
            }
            try {
                datos[i][1] = direccion_envio.getTipoVia().getAbreviatura() + " " + direccion_envio.getVia() + " " + direccion_envio.getNumero() + " " + ((direccion_envio.getExtra() == null) ? "" : direccion_envio.getExtra());

            } catch (Exception e) {
                datos[i][1] = "[DIRECCIÓN NO EXISTENTE]";
            }
            try {
                datos[i][2] = "" + direccion_envio.getCpLocalidad().getCpLocalidadPK().getCp();
            } catch (Exception e) {
                datos[i][2] = "[DIRECCIÓN NO EXISTENTE]";
            }
            try {
                datos[i][3] = "" + direccion_envio.getCpLocalidad().getLocalidad1().getNombre();
            } catch (Exception e) {
                datos[i][3] = "[DIRECCIÓN NO EXISTENTE]";
            }
            try {
                datos[i][4] = "" + direccion_envio.getCpLocalidad().getLocalidad1().getProvincia().getNombre();
            } catch (Exception e) {
                datos[i][4] = "[DIRECCIÓN NO EXISTENTE]";
            }
            String telefono = "";
            String movil = "";
            try {
                for (EmpresaTelefono t : matriculas.get(i - 1).getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getEmpresaTelefonoList()) {
                    if ((t.getTipo().getId().equals(3) || t.getTipo().getId().equals(5)) && telefono.equals("")) {
                        telefono = "" + t.getEmpresaTelefonoPK().getNumero();
                    }
                    if (t.getTipo().getId().equals(1) && movil.equals("")) {
                        movil = "" + t.getEmpresaTelefonoPK().getNumero();
                    }
                }
            } catch (Exception e) {
            }
            datos[i][5] = telefono;
            datos[i][6] = movil;
            datos[i][7] = matriculas.get(i - 1).getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRepresLegal().getNombre() + " " + matriculas.get(i - 1).getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRepresLegal().getApellido1() + " " + matriculas.get(i - 1).getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRepresLegal().getApellido2();
            datos[i][8] = ((matriculas.get(i - 1).getObservacionesEnivo() == null) ? "" : matriculas.get(i - 1).getObservacionesEnivo());
            datos[i][9] = "" + matriculas.get(i - 1).getId();
        }
        Excel.exportarExcelEstandar(datos, VariablesSistema.ruta_absoluta_proyecto + doc);
        return doc;
    }

    public static String generar_autorizacion(Matricula matricula) throws IOException, DocumentException {
        String doc = "/docs/temp/autorizacion_" + matricula.getId() + ".pdf";
        String plantilla = "/docs/plantillas/autor.pdf";
        Persona responsable = matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRepresLegal();
        Persona alumno = matricula.getAlumno().getPersona();
        String texto = "" + VariablesSistema.proveedor_principal.getEmpresa().getDireccion().getCpLocalidad().getLocalidad1().getNombre() + " a " + Fecha.getfechaFechaDiaMesTextoAnio(new Date()) + "\n\n"
                + (responsable.getNombre() + " " + responsable.getApellido1() + " " + responsable.getApellido2()) + ", con DNI " + (responsable.getNif()) + ", como representante legal de " + (matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRazonSocial()) + " con CIF " + (matricula.getEmpresaMatriculaCcc().getEmpresaMatricula().getNif()) + ", autorizo a que " + (VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial()) + " gestione un curso de formación a distancia a nombre de " + (alumno.getNombre() + " " + alumno.getApellido1() + " " + alumno.getApellido2()) + " no relacionado con la actividad de la empresa. En caso de inspección del curso, la empresa  que realiza la formación se hace responsable.\n\nFdo: " + (responsable.getNombre() + " " + responsable.getApellido1() + " " + responsable.getApellido2());
        String[][] campos = new String[][]{
            {"texto", texto}
        };
        Pdf.rellenarPDF(campos, VariablesSistema.ruta_absoluta_proyecto + plantilla, VariablesSistema.ruta_absoluta_proyecto + doc, false);
        return doc;
    }

    public static String generar_cartel_FEFE(Matricula matricula) throws IOException, DocumentException {
        String doc = "/docs/temp/cartel_FEFE_" + matricula.getId() + ".pdf";
        String plantilla = "/docs/plantillas/fefe.pdf";
        String[][] campos = new String[][]{
            {"nom_af", matricula.getGrupo().getAccionFormativa1().getNombre()},
            {"id_af", "" + matricula.getGrupo().getAccionFormativa1().getId()},
            {"id_grupo", "" + matricula.getGrupo().getGrupoPK().getId()},
            {"f_inicio", Fecha.getFechaDiaMesAnio(matricula.getGrupo().getFInicio())},
            {"f_fin", Fecha.getFechaDiaMesAnio(matricula.getGrupo().getFFin())},
            {"horario", matricula.getGrupo().getHorario()}
        };
        Pdf.rellenarPDF(campos, VariablesSistema.ruta_absoluta_proyecto + plantilla, VariablesSistema.ruta_absoluta_proyecto + doc, false);
        return doc;
    }

    private static String generar_control_asistencia(Matricula matricula) throws IOException, IOException, DocumentException {
        String doc = "/docs/temp/control_asistencia_" + matricula.getId() + ".pdf";
        String plantilla = "/docs/plantillas/asistencia.pdf";
        Persona tut = null;
        if (!matricula.getGrupo().getTutorList().isEmpty()) {
            tut = matricula.getGrupo().getTutorList().get(0).getPersona();
        }
        List<Matricula> als = matricula.getGrupo().getMatriculaList();
        String[][] campos = new String[][]{
            {"nom_af", matricula.getGrupo().getAccionFormativa1().getNombre()},
            {"id_af", "" + matricula.getGrupo().getAccionFormativa1().getId()},
            {"id_grupo", "" + matricula.getGrupo().getGrupoPK().getId()},
            {"f_inicio", Fecha.getFechaDiaMesAnio(matricula.getGrupo().getFInicio())},
            {"f_fin", Fecha.getFechaDiaMesAnio(matricula.getGrupo().getFFin())},
            {"horario", matricula.getGrupo().getHorario()},
            {"formador", tut == null ? "" : (tut.getNombre() + " " + tut.getApellido1() + " " + tut.getApellido2())},
            {"sesion", ""},
            {"fecha", ""},
            {"mt", ""},
            {"nom_1", als.size() >= 1 ? (als.get(0).getAlumno().getPersona().getNombre() + " " + als.get(0).getAlumno().getPersona().getApellido1() + " " + als.get(0).getAlumno().getPersona().getApellido2()) : ""},
            {"nom_2", als.size() >= 2 ? (als.get(1).getAlumno().getPersona().getNombre() + " " + als.get(1).getAlumno().getPersona().getApellido1() + " " + als.get(1).getAlumno().getPersona().getApellido2()) : ""},
            {"nom_3", als.size() >= 3 ? (als.get(2).getAlumno().getPersona().getNombre() + " " + als.get(2).getAlumno().getPersona().getApellido1() + " " + als.get(2).getAlumno().getPersona().getApellido2()) : ""},
            {"nom_4", als.size() >= 4 ? (als.get(3).getAlumno().getPersona().getNombre() + " " + als.get(3).getAlumno().getPersona().getApellido1() + " " + als.get(3).getAlumno().getPersona().getApellido2()) : ""},
            {"nom_5", als.size() >= 5 ? (als.get(4).getAlumno().getPersona().getNombre() + " " + als.get(4).getAlumno().getPersona().getApellido1() + " " + als.get(4).getAlumno().getPersona().getApellido2()) : ""},
            {"nom_6", als.size() >= 6 ? (als.get(5).getAlumno().getPersona().getNombre() + " " + als.get(5).getAlumno().getPersona().getApellido1() + " " + als.get(5).getAlumno().getPersona().getApellido2()) : ""},
            {"nom_7", als.size() >= 7 ? (als.get(6).getAlumno().getPersona().getNombre() + " " + als.get(6).getAlumno().getPersona().getApellido1() + " " + als.get(6).getAlumno().getPersona().getApellido2()) : ""},
            {"nom_8", als.size() >= 8 ? (als.get(7).getAlumno().getPersona().getNombre() + " " + als.get(7).getAlumno().getPersona().getApellido1() + " " + als.get(7).getAlumno().getPersona().getApellido2()) : ""},
            {"nom_9", als.size() >= 9 ? (als.get(8).getAlumno().getPersona().getNombre() + " " + als.get(8).getAlumno().getPersona().getApellido1() + " " + als.get(8).getAlumno().getPersona().getApellido2()) : ""},
            {"nom_10", als.size() >= 10 ? (als.get(9).getAlumno().getPersona().getNombre() + " " + als.get(9).getAlumno().getPersona().getApellido1() + " " + als.get(9).getAlumno().getPersona().getApellido2()) : ""},
            {"nom_11", als.size() >= 11 ? (als.get(10).getAlumno().getPersona().getNombre() + " " + als.get(10).getAlumno().getPersona().getApellido1() + " " + als.get(10).getAlumno().getPersona().getApellido2()) : ""},
            {"nom_12", als.size() >= 12 ? (als.get(11).getAlumno().getPersona().getNombre() + " " + als.get(11).getAlumno().getPersona().getApellido1() + " " + als.get(11).getAlumno().getPersona().getApellido2()) : ""},
            {"nom_13", als.size() >= 13 ? (als.get(12).getAlumno().getPersona().getNombre() + " " + als.get(12).getAlumno().getPersona().getApellido1() + " " + als.get(12).getAlumno().getPersona().getApellido2()) : ""},
            {"nif_1", als.size() >= 1 ? (als.get(0).getAlumno().getPersona().getNif()) : ""},
            {"nif_2", als.size() >= 2 ? (als.get(1).getAlumno().getPersona().getNif()) : ""},
            {"nif_3", als.size() >= 3 ? (als.get(2).getAlumno().getPersona().getNif()) : ""},
            {"nif_4", als.size() >= 4 ? (als.get(3).getAlumno().getPersona().getNif()) : ""},
            {"nif_5", als.size() >= 5 ? (als.get(4).getAlumno().getPersona().getNif()) : ""},
            {"nif_6", als.size() >= 6 ? (als.get(5).getAlumno().getPersona().getNif()) : ""},
            {"nif_7", als.size() >= 7 ? (als.get(6).getAlumno().getPersona().getNif()) : ""},
            {"nif_8", als.size() >= 8 ? (als.get(7).getAlumno().getPersona().getNif()) : ""},
            {"nif_9", als.size() >= 9 ? (als.get(8).getAlumno().getPersona().getNif()) : ""},
            {"nif_10", als.size() >= 10 ? (als.get(9).getAlumno().getPersona().getNif()) : ""},
            {"nif_11", als.size() >= 11 ? (als.get(10).getAlumno().getPersona().getNif()) : ""},
            {"nif_12", als.size() >= 12 ? (als.get(11).getAlumno().getPersona().getNif()) : ""},
            {"nif_13", als.size() >= 13 ? (als.get(12).getAlumno().getPersona().getNif()) : ""}};
        Pdf.rellenarPDF(campos, VariablesSistema.ruta_absoluta_proyecto
                + plantilla, VariablesSistema.ruta_absoluta_proyecto + doc, false);
        return doc;
    }

    public static String generar_listado_facturas(List<Factura> facturas) throws FileNotFoundException, IOException {
        Random r = new Random();
        String doc = "/docs/temp/facturas_" + r.nextInt() + ".xls";
        String[][] datos = new String[facturas.size() + 1][6];
        datos[0][0] = "Razón Social";
        datos[0][1] = "CIF";
        datos[0][2] = "Nº Factura";
        datos[0][3] = "Importe";
        datos[0][4] = "Estado Factura";
        datos[0][5] = "Nº Cuenta";
        for (int i = 1; i < datos.length; i++) {
            datos[i][0] = "" + facturas.get(i - 1).getMatriculaFacturaList().get(0).getMatricula1().getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRazonSocial() + "_TEXTO_";
            datos[i][1] = "" + facturas.get(i - 1).getMatriculaFacturaList().get(0).getMatricula1().getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getNif() + "_TEXTO_";
            datos[i][2] = "" + facturas.get(i - 1).getId();
            datos[i][3] = "" + facturas.get(i - 1).getImporte();
            datos[i][4] = "" + (facturas.get(i - 1).getImporte() < 0.0 ? "Abono" : facturas.get(i - 1).getUltimoEstado().getEstado().getNombre()) + "_TEXTO_";
            datos[i][5] = "" + facturas.get(i - 1).getMatriculaFacturaList().get(0).getMatricula1().getEmpresaMatriculaCcc().getEmpresaMatriculaCccPK().getCcc() + "_TEXTO_";
        }
        Excel.exportarExcelEstandar(datos, VariablesSistema.ruta_absoluta_proyecto + doc);
        return doc;
    }

    private static String generar_matricula(Matricula matricula, EmpresaMatriculaCcc empresaMatriculaCcc, Alumno alumno) throws IOException, DocumentException {
        String doc;
        if (matricula != null) {
            doc = "/docs/temp/matricula_" + matricula.getId() + ".pdf";
        } else {
            doc = "/docs/temp/matricula_" + empresaMatriculaCcc.getEmpresaMatricula().getNif() + ".pdf";
        }
        String plantilla = "/docs/plantillas/matricula.pdf";

        Direccion d = empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getDireccion();
        String telefono = "";
        String movil = "";
        String fax = "";
        String tlf_alumno = "";
        if (alumno != null) {
            for (PersonaTelefono et : alumno.getPersona().getPersonaTelefonoList()) {
                if (et.getTipo().getId().equals(3) || et.getTipo().getId().equals(5)) {
                    tlf_alumno = et.getPersonaTelefonoPK().getNumero();
                }
            }
        }

        for (EmpresaTelefono et : empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getEmpresaTelefonoList()) {
            if (et.getTipo().getId().equals(3) || et.getTipo().getId().equals(5)) {
                telefono = et.getEmpresaTelefonoPK().getNumero();
            }
            if (et.getTipo().getId().equals(2)) {
                fax = et.getEmpresaTelefonoPK().getNumero();
            }
            if (et.getTipo().getId().equals(1)) {
                movil = et.getEmpresaTelefonoPK().getNumero();
            }
        }

        EmpresaMatriculaAnio eam = null;
        for (EmpresaMatriculaAnio m : empresaMatriculaCcc.getEmpresaMatricula().getEmpresaMatriculaAnioList()) {
            if (m.getEmpresaMatriculaAnioPK().getAnio() == Fecha.getAnio(matricula == null ? (new Date()) : matricula.getFechaFirma())) {
                eam = m;
            }
        }

        String comercial = "";
        String asesoria = "";
        String contacto = "";

        if (matricula != null) {
            for (MatriculaComercial mc : matricula.getMatriculaComercialList()) {
                if (mc.getComercial1().getComercialTipo().getId().equals(8)) {
                    comercial = mc.getComercial1().getNombreNifComercial();
                }
                if (mc.getComercial1().getComercialTipo().getId().equals(7)) {
                    asesoria = mc.getComercial1().getNombreNifComercial();
                }
                if (mc.getComercial1().getComercialTipo().getId().equals(15)) {
                    contacto = mc.getComercial1().getNombreNifComercial();
                }
            }
        } else if (empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getComercialSuperiorList() != null) {
            for (Comercial mc : empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getComercialSuperiorList()) {
                if (mc.getComercialTipo().getId().equals(8)) {
                    comercial = mc.getNombreNifComercial();
                }
                if (mc.getComercialTipo().getId().equals(7)) {
                    asesoria = mc.getNombreNifComercial();
                }
                if (mc.getComercialTipo().getId().equals(15)) {
                    contacto = mc.getNombreNifComercial();
                }
            }
        }
        String[][] campos = new String[][]{
            {"agrupacion", empresaMatriculaCcc.getEmpresaMatricula().getAgrupacion().getNombre()},
            {"razon_social", empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getRazonSocial()},
            {"cif", empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getNif()},
            {"ss1", Validacion.substring(empresaMatriculaCcc.getEmpresaMatricula().getCotSs(), 0, 2)},
            {"nss2", Validacion.substring(empresaMatriculaCcc.getEmpresaMatricula().getCotSs(), 2, empresaMatriculaCcc.getEmpresaMatricula().getCotSs() == null ? 2 : empresaMatriculaCcc.getEmpresaMatricula().getCotSs().length())},
            {"via", d.getTipoVia().getAbreviatura() + " " + d.getVia()},
            {"numero", d.getNumero() + " " + d.getExtra()},
            {"cp", "" + d.getCpLocalidad().getCpLocalidadPK().getCp()},
            {"poblacion", d.getCpLocalidad().getLocalidad1().getNombre()},
            {"provincia", d.getCpLocalidad().getLocalidad1().getProvincia().getNombre()},
            {"telefono", telefono},
            {"movil", movil},
            {"fax", fax},
            {"email", empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getEmail()},
            {"pagweb", empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getPagWeb() == null ? "" : empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getPagWeb()},
            {"actividad", empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getActividad().getNombre()},
            {"ccolectivo", empresaMatriculaCcc.getEmpresaMatricula().getCColectivo().getNombre()},
            {"plantilla", eam == null ? "" : eam.getPlantilla() + ""},
            {"presente_anio_no", empresaMatriculaCcc.getEmpresaMatricula().getFechaCreacion() != null ? (Fecha.getAnio(empresaMatriculaCcc.getEmpresaMatricula().getFechaCreacion()) == Fecha.getAnio(matricula == null ? (new Date()) : matricula.getFechaFirma())
                ? "" : "X") : ""},
            {"presente_anio_si", empresaMatriculaCcc.getEmpresaMatricula().getFechaCreacion() != null ? (Fecha.getAnio(empresaMatriculaCcc.getEmpresaMatricula().getFechaCreacion()) == Fecha.getAnio(matricula == null ? (new Date()) : matricula.getFechaFirma())
                ? "X" : "") : ""},
            {"dia_creacion", empresaMatriculaCcc.getEmpresaMatricula().getFechaCreacion() != null ? (Fecha.getDia(empresaMatriculaCcc.getEmpresaMatricula().getFechaCreacion())) : ""},
            {"mes_creacion", empresaMatriculaCcc.getEmpresaMatricula().getFechaCreacion() != null ? (Fecha.getMes(empresaMatriculaCcc.getEmpresaMatricula().getFechaCreacion())) + "" : ""},
            {"anio_creacion", empresaMatriculaCcc.getEmpresaMatricula().getFechaCreacion() != null ? ("" + Fecha.getAnio(empresaMatriculaCcc.getEmpresaMatricula().getFechaCreacion())) : ""},
            {"rlt_no", empresaMatriculaCcc.getEmpresaMatricula().getRepresentacionLegal() ? "" : "X"},
            {"rlt_si", empresaMatriculaCcc.getEmpresaMatricula().getRepresentacionLegal() ? "X" : ""},
            {"centro_trabajo_no", eam == null ? "" : (eam.getCreadoCentro() ? "" : "X")},
            {"centro_trabajo_si", eam == null ? "" : (eam.getCreadoCentro() ? "X" : "")},
            {"recibe_formacion_no", eam == null ? "" : (eam.getRecibeFormacion() ? "" : "X")},
            {"recibe_formacion_si", eam == null ? "" : (eam.getRecibeFormacion() ? "X" : "")},
            {"nom_ap_rep_leg", empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getRepresLegal().getNombreApellidos()},
            {"nif_rep_leg", empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getRepresLegal().getNif()},
            {"nom_ap_rep_leg_2", empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getRepresLegal().getNombreApellidos()},
            {"dia_firma", matricula == null || matricula.getFechaFirma() == null ? "" : Fecha.getDia(matricula.getFechaFirma())},
            {"mes_firma", matricula == null || matricula.getFechaFirma() == null ? "" : Fecha.getStringMes(Fecha.getMes(matricula.getFechaFirma()))},
            {"anio_firma", matricula == null || matricula.getFechaFirma() == null ? "" : "" + Fecha.getAnio(matricula.getFechaFirma())},
            {"comercial", comercial},
            {"asesoria", asesoria},
            {"contacto", contacto},
            {"id_matricula", "ID Matrícula: " + (matricula != null ? matricula.getId() : "")},
            {"razon_social_2", empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getRazonSocial()},
            {"cif_2", empresaMatriculaCcc.getEmpresaMatricula().getEmpresa().getNif()},
            {"nss_empresa_1", Validacion.substring(empresaMatriculaCcc.getEmpresaMatricula().getCotSs(), 0, 2)},
            {"nss_empresa_2", Validacion.substring(empresaMatriculaCcc.getEmpresaMatricula().getCotSs(), 2, empresaMatriculaCcc.getEmpresaMatricula().getCotSs() == null ? 2 : empresaMatriculaCcc.getEmpresaMatricula().getCotSs().length())},
            {"aff_grupo", matricula == null ? "" : matricula.getGrupo().getNombre()},
            {"nom_ap_alumno", (alumno == null ? ""
                : alumno.getPersona().getNombreApellidos())},
            {"dni_alumno", (alumno == null ? ""
                : alumno.getPersona().getNif())},
            {"nss_alumno_1", (alumno == null ? ""
                : Validacion.substring(alumno.getPersona().getNss(), 0, 2))},
            {"nss_alumno_2", (alumno == null ? ""
                : Validacion.substring(alumno.getPersona().getNss(), 2, alumno.getPersona().getNss() == null ? 2 : alumno.getPersona().getNss().length()))},
            {"email_alumno", (alumno == null ? ""
                : alumno.getPersona().getEmail())},
            {"tlf_alumno", (alumno == null ? ""
                : tlf_alumno)},
            {"fnac_alumno", (alumno == null ? ""
                : Fecha.getFechaDiaMesAnio(alumno.getPersona().getFechaNacimiento()))},
            {"ref_curso", matricula == null ? "" : "" + matricula.getGrupo().getAccionFormativa1().getId()},
            {"horas", matricula == null ? "" : "" + matricula.getGrupo().getAccionFormativa1().getHoras()},
            {"modalidad_peq", matricula == null ? "" : "" + matricula.getGrupo().getAccionFormativa1().getModalidad().getNombre().substring(0, 3) + "."},
            {"precio_mat", matricula == null ? "" : "" + Validacion.Redondear(matricula.getPrecio())},
            {"observaciones", "Observaciones: " + (matricula == null ? "" : "" + matricula.getObservaciones())},
            {"autonomo_si", (alumno == null ? ""
                : alumno.getAutonomo() ? "X" : "")},
            {"autonomo_no", (alumno == null ? ""
                : alumno.getAutonomo() ? "" : "X")},
            {"masculino_si", (alumno == null ? ""
                : alumno.getPersona().getMasculino() ? "X" : "")},
            {"masculino_no", (alumno == null ? ""
                : alumno.getPersona().getMasculino() ? "" : "X")},
            {"nivel_estudios_otros", (alumno == null ? ""
                : alumno.getOtrasTitulaciones() == null || alumno.getOtrasTitulaciones().equals("") ? "" : "X")},
            {"nivel_estudios_1", (alumno == null ? ""
                : alumno.getNivelEstudios().getId().equals(new Short("1")) ? "X" : "")},
            {"nivel_estudios_2", (alumno == null ? ""
                : alumno.getNivelEstudios().getId().equals(new Short("2")) ? "X" : "")},
            {"nivel_estudios_3", (alumno == null ? ""
                : alumno.getNivelEstudios().getId().equals(new Short("3")) ? "X" : "")},
            {"nivel_estudios_4", (alumno == null ? ""
                : alumno.getNivelEstudios().getId().equals(new Short("4")) ? "X" : "")},
            {"nivel_estudios_5", (alumno == null ? ""
                : alumno.getNivelEstudios().getId().equals(new Short("5")) ? "X" : "")},
            {"otras_titulaciones", (alumno == null ? ""
                : alumno.getOtrasTitulaciones() == null ? "" : alumno.getOtrasTitulaciones())},
            {"grupo_cotizacion_10", (alumno == null ? ""
                : alumno.getGrupoCotizacion().getId().equals(new Short("10")) ? "X" : "")},
            {"grupo_cotizacion_11", (alumno == null ? ""
                : alumno.getGrupoCotizacion().getId().equals(new Short("11")) ? "X" : "")},
            {"grupo_cotizacion_9", (alumno == null ? ""
                : alumno.getGrupoCotizacion().getId().equals(new Short("9")) ? "X" : "")},
            {"grupo_cotizacion_8", (alumno == null ? ""
                : alumno.getGrupoCotizacion().getId().equals(new Short("8")) ? "X" : "")},
            {"grupo_cotizacion_7", (alumno == null ? ""
                : alumno.getGrupoCotizacion().getId().equals(new Short("7")) ? "X" : "")},
            {"grupo_cotizacion_6", (alumno == null ? ""
                : alumno.getGrupoCotizacion().getId().equals(new Short("6")) ? "X" : "")},
            {"grupo_cotizacion_5", (alumno == null ? ""
                : alumno.getGrupoCotizacion().getId().equals(new Short("5")) ? "X" : "")},
            {"grupo_cotizacion_4", (alumno == null ? ""
                : alumno.getGrupoCotizacion().getId().equals(new Short("4")) ? "X" : "")},
            {"grupo_cotizacion_3", (alumno == null ? ""
                : alumno.getGrupoCotizacion().getId().equals(new Short("3")) ? "X" : "")},
            {"grupo_cotizacion_2", (alumno == null ? ""
                : alumno.getGrupoCotizacion().getId().equals(new Short("2")) ? "X" : "")},
            {"grupo_cotizacion_1", (alumno == null ? ""
                : alumno.getGrupoCotizacion().getId().equals(new Short("1")) ? "X" : "")},
            {"discapacidad_si", (alumno == null ? ""
                : alumno.getDiscapacitado() ? "X" : "")},
            {"discapacidad_no", (alumno == null ? ""
                : alumno.getDiscapacitado() ? "" : "X")},
            {"area_funcional_1", (alumno == null ? ""
                : alumno.getAreaFuncional().getId().equals(new Short("1")) ? "X" : "")},
            {"area_funcional_2", (alumno == null ? ""
                : alumno.getAreaFuncional().getId().equals(new Short("2")) ? "X" : "")},
            {"area_funcional_3", (alumno == null ? ""
                : alumno.getAreaFuncional().getId().equals(new Short("3")) ? "X" : "")},
            {"area_funcional_4", (alumno == null ? ""
                : alumno.getAreaFuncional().getId().equals(new Short("4")) ? "X" : "")},
            {"area_funcional_5", (alumno == null ? ""
                : alumno.getAreaFuncional().getId().equals(new Short("5")) ? "X" : "")},
            {"categoria_profesional_1", (alumno == null ? ""
                : alumno.getCategoriaProfesional().getId().equals(new Short("1")) ? "X" : "")},
            {"categoria_profesional_2", (alumno == null ? ""
                : alumno.getCategoriaProfesional().getId().equals(new Short("2")) ? "X" : "")},
            {"categoria_profesional_3", (alumno == null ? ""
                : alumno.getCategoriaProfesional().getId().equals(new Short("3")) ? "X" : "")},
            {"categoria_profesional_4", (alumno == null ? ""
                : alumno.getCategoriaProfesional().getId().equals(new Short("4")) ? "X" : "")},
            {"categoria_profesional_5", (alumno == null ? ""
                : alumno.getCategoriaProfesional().getId().equals(new Short("5")) ? "X" : "")},
            {"victima_terrorismo_si", (alumno == null ? ""
                : alumno.getVictimaTerrorismo() ? "X" : "")},
            {"victima_terrorismo_no", (alumno == null ? ""
                : alumno.getVictimaTerrorismo() ? "" : "X")},
            {"victima_genero_si", (alumno == null ? ""
                : alumno.getVictimaVG() ? "X" : "")},
            {"victima_genero_no", (alumno == null ? ""
                : alumno.getVictimaVG() ? "" : "X")},
            {"precio_mat_2", matricula == null ? "" : "" + Validacion.Redondear(matricula.getPrecio())},
            {"forma_pago", matricula == null ? "" : "" + matricula.getFormaPago().getNombre()},
            {"fecha_entrega", matricula == null || matricula.getFechaEntrega() == null ? "Fecha de Entrega: " : "Fecha de Entrega: " + Fecha.getFechaDiaMesAnio(matricula.getFechaEntrega())},
            {"iban", empresaMatriculaCcc.getIban()},
            {"ccc_1", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 0 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(0) : ""},
            {"ccc_2", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 1 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(1) : ""},
            {"ccc_3", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 2 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(2) : ""},
            {"ccc_4", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 3 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(3) : ""},
            {"ccc_5", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 4 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(4) : ""},
            {"ccc_6", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 5 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(5) : ""},
            {"ccc_7", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 6 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(6) : ""},
            {"ccc_8", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 7 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(7) : ""},
            {"ccc_9", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 8 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(8) : ""},
            {"ccc_10", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 9 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(9) : ""},
            {"ccc_11", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 10 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(10) : ""},
            {"ccc_12", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 11 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(11) : ""},
            {"ccc_13", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 12 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(12) : ""},
            {"ccc_14", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 13 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(13) : ""},
            {"ccc_15", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 14 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(14) : ""},
            {"ccc_16", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 15 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(15) : ""},
            {"ccc_17", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 16 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(16) : ""},
            {"ccc_18", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 17 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(17) : ""},
            {"ccc_19", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 18 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(18) : ""},
            {"ccc_20", empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc() != null && empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().length() > 19 ? "" + empresaMatriculaCcc.getEmpresaMatriculaCccPK().getCcc().charAt(19) : ""},
            {"dia_firma_1", matricula == null || matricula.getFechaFirma() == null ? "" : Fecha.getDia(matricula.getFechaFirma())},
            {"mes_firma_1", matricula == null || matricula.getFechaFirma() == null ? "" : Fecha.getStringMes(Fecha.getMes(matricula.getFechaFirma()))},
            {"anio_firma_1", matricula == null || matricula.getFechaFirma() == null ? "" : "" + Fecha.getAnio(matricula.getFechaFirma())}
        };
        Pdf.rellenarPDF(campos, VariablesSistema.ruta_absoluta_proyecto + plantilla, VariablesSistema.ruta_absoluta_proyecto + doc, false);
        return doc;
    }
}