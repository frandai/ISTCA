/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import datos.EmpresaMatriculaCcc;
import datos.Factura;
import datos.Remesa;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.io.FileUtils;

/**
 *
 *
 */
public class ExportarRemesa {

    public static void generarRemesa(Remesa remesa) throws IOException {

        String datoNif = VariablesSistema.proveedor_principal.getNif() + "000";
        String CCCProv = "";
        for (EmpresaMatriculaCcc emc : VariablesSistema.proveedor_principal.getEmpresa().getEmpresaMatricula().getEmpresaMatriculaCccList()) {
            if (!emc.getEmpresaMatriculaCccPK().getCcc().equals("")) {
                CCCProv = emc.getEmpresaMatriculaCccPK().getCcc();
            }
        }
        String fechaAct = Fecha.getfechaDDMMAA(remesa.getFecha());
        String fechaCob = Fecha.getfechaDDMMAA(remesa.getFechaCobro());

        String ficheroRemesa = "";

        //Primera fila (5180)
        ficheroRemesa += "5180" + datoNif + fechaAct;
        ficheroRemesa += completarCon("", 6, ' ', true);
        ficheroRemesa += completarCon(Validacion.removeChars(VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial()), 40, ' ', false);
        ficheroRemesa += completarCon("", 20, ' ', false);
        ficheroRemesa += CCCProv.substring(0, 8);
        ficheroRemesa += completarCon("", 66, ' ', true);
        ficheroRemesa += "\n";

        //Segunda fila (5380)
        ficheroRemesa += "5380" + datoNif + fechaAct + fechaCob;
        ficheroRemesa += completarCon(Validacion.removeChars(VariablesSistema.proveedor_principal.getEmpresa().getRazonSocial()), 40, ' ', false);
        ficheroRemesa += CCCProv;
        ficheroRemesa += completarCon("", 8, ' ', true) + "01";
        ficheroRemesa += completarCon("", 64, ' ', true);
        ficheroRemesa += "\n";

        //Filas de recibos (5680)
        ArrayList<datoFilaRecibo> filasRecibos = new ArrayList<datoFilaRecibo>();

        for (Factura f : remesa.getFacturaList()) {
            String nif = f.getMatriculaFacturaList().get(0).getMatricula1().getEmpresaMatriculaCcc().getEmpresaMatricula().getNif();
            int ocu = 0;
            int filaR = -1;
            for (int i = 0; i < filasRecibos.size(); i++) {
                if (filasRecibos.get(i).codigoReferencia.contains(nif)) {
                    if (remesa.getAgrupada()) {
                        filaR = i;
                        break;
                    } else {
                        ocu++;
                    }
                }
            }
            if (filaR != -1) {
                filasRecibos.get(filaR).concepto = "FACT. AGRUPADAS";
                filasRecibos.get(filaR).precioFilaRecibo += Validacion.Redondear(f.getImporte());
            } else {
                datoFilaRecibo fila = new datoFilaRecibo();
                fila.codigoReferencia = nif + (ocu == 0 ? "" : ocu);
                fila.razonSocial = f.getMatriculaFacturaList().get(0).getMatricula1().getEmpresaMatriculaCcc().getEmpresaMatricula().getEmpresa().getRazonSocial();
                fila.ccc = f.getMatriculaFacturaList().get(0).getMatricula1().getEmpresaMatriculaCcc().getEmpresaMatriculaCccPK().getCcc();
                fila.precioFilaRecibo = Validacion.Redondear(f.getImporte());
                fila.codigoDevoluciones = Fecha.getfechaAAMMDD(new Date());
                fila.concepto = "FACT. " + f.getId();
                filasRecibos.add(fila);
            }
        }

        Double sumaTotal = 0.0;

        for (int i = 0; i < filasRecibos.size(); i++) {
            ficheroRemesa += rellenarFilaRecibo(filasRecibos.get(i), datoNif, i);
            sumaTotal += filasRecibos.get(i).precioFilaRecibo;

        }

        //Fila final 1 (5880)
        ficheroRemesa += "5880" + datoNif;
        ficheroRemesa += completarCon("", 72, ' ', true);
        ficheroRemesa += completarCon(normalizarPrecio(sumaTotal), 10,
                '0', true);
        ficheroRemesa += completarCon("", 6, ' ', true);
        ficheroRemesa += completarCon("" + filasRecibos.size(), 10, '0', true);
        ficheroRemesa += completarCon("" + (filasRecibos.size() + 2), 10, '0', true);
        ficheroRemesa += completarCon("", 38, ' ', false);
        ficheroRemesa += "\n";
        //Fila final 2 (5980)
        ficheroRemesa += "5980" + datoNif;
        ficheroRemesa += completarCon("", 52, ' ', true) + "0001" + completarCon("", 16, ' ', true);
        ficheroRemesa += completarCon(normalizarPrecio(sumaTotal), 10,
                '0', true);
        ficheroRemesa += completarCon("", 6, ' ', true);
        ficheroRemesa += completarCon("" + filasRecibos.size(), 10, '0', true);
        ficheroRemesa += completarCon("" + (filasRecibos.size() + 4), 10, '0', true);
        ficheroRemesa += completarCon("", 38, ' ', false);
        ficheroRemesa += "\n";

        remesa.setArchivo(remesa.getId() + ".txt");
        remesa.setTotalRemesa(sumaTotal);
        remesa.setNumRegistros(filasRecibos.size());
        FileUtils.writeStringToFile(new File(VariablesSistema.ruta_absoluta_proyecto + "/docs/temp/" + remesa.getArchivo()), ficheroRemesa);
    }

    private static String rellenarFilaRecibo(datoFilaRecibo fila, String datoNif, int numFila) {
        String ficheroRemesa = "";
        ficheroRemesa += "5680" + datoNif + completarCon(Validacion.removeChars(fila.codigoReferencia), 12, ' ', false);
        ficheroRemesa += completarCon(Validacion.removeChars(fila.razonSocial), 40, ' ', false);
        ficheroRemesa += fila.ccc;
        ficheroRemesa += completarCon(normalizarPrecio(fila.precioFilaRecibo), 10,
                '0', true);
        ficheroRemesa += completarCon(Validacion.removeChars(fila.codigoDevoluciones), 6, '0', true);
        ficheroRemesa += completarCon("" + numFila, 10, '0', true);
        ficheroRemesa += completarCon(Validacion.removeChars(fila.concepto), 40, ' ', false);
        ficheroRemesa += completarCon("", 8, ' ', false);
        ficheroRemesa += "\n";

        return ficheroRemesa;
    }

    private static String normalizarPrecio(double precio) {
        String total = "" + precio;
        String precioNormalizado = total;
        String parteEntera = "";
        String parteDecimal = "";
        if (total.indexOf(".") > 0) {
            parteEntera = total.substring(0, total.indexOf("."));
            parteDecimal = total.substring(total.indexOf(".") + 1, total.length());
            if (parteDecimal.length() > 2) {
                parteDecimal = parteDecimal.substring(0, 2);
            } else if (parteDecimal.length() == 1) {
                parteDecimal += "0";
            } else if (parteDecimal.length() == 0) {
                parteDecimal = "00";
            }
            precioNormalizado = parteEntera + parteDecimal;
        } else {
            precioNormalizado += "00";
        }
        return precioNormalizado;
    }

    public static String completarCon(String campo, int caracteres, char completarCon,
            boolean izquierda) {
        if (campo.length() > caracteres) {
            campo = campo.substring(0, caracteres);
        }
        while (campo.length() < caracteres) {
            if (izquierda) {
                campo = completarCon + campo;
            } else {
                campo += completarCon;
            }
        }
        return campo;
    }
}

class datoFilaRecibo {

    public String codigoReferencia;
    public String razonSocial;
    public String ccc;
    public double precioFilaRecibo;
    public String codigoDevoluciones;
    public String concepto;
}