/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import bean.AbstractFacade;
import bean.AccionFormativaFacade;
import bean.EmpresaFacade;
import bean.PersonaFacade;
import datos.AccionFormativa;
import datos.Empresa;
import datos.Persona;
import datosVistaFacade.NextEmpresaFacade;
import java.math.BigInteger;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

/**
 *
 *dai
 */
public class Validacion {

    public static final int NIF_NOVALIDO = 0;
    public static final int NIF_GENERADO = 1;
    public static final int NIF_CIF = 2;
    public static final int NIF_NIE = 3;
    public static final int NIF_DNI = 4;
    public static final int NIF_OTRO = 5;
    public static final String[] valores_dni = new String[]{"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
    public static final String[] valores_nie_otros = new String[]{"J", "A", "B", "C", "D", "E", "F", "G", "H", "I"};

    public static String substring(String string, int desde, int hasta) {
        try {
            if (string.length() > desde && string.length() >= hasta) {
                return string.substring(desde, hasta);
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static String removeChars(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        if (output != null) {
            for (int i = 0; i < original.length(); i++) {
                // Reemplazamos los caracteres especiales.
                output = output.replace(original.charAt(i), ascii.charAt(i));
            }//for i
        }
        return output;
    }//remove1

    public static String rellenaDigitoControlNSS(String nss) {
        try {
            if (nss.length() == 9 || nss.length() == 10) {
                int x = Integer.parseInt(nss) % 97;
                return nss + (x > 9 ? x : ("0" + x));
            }
        } catch (Exception e) {
        }
        return nss;
    }

    public static double Redondear(double numero) {
        return ((int) Math.rint(numero * 100)) / 100.0;
    }

    public static int generaID(AbstractFacade facade) {
        return generaID(facade, null);
    }

    public static int generaID(AbstractFacade facade, NextEmpresaFacade nef) { //TODO: MEJORAR!
        int n = 1;
        try {
            Pattern p = Pattern.compile("^[0-9]*$");
            if (facade instanceof EmpresaFacade && nef != null) {
                n = nef.findAll().get(0).getNextEmpresa();
                while (facade.find("COM" + n) != null) {
                    n++;
                }
            } else if (facade instanceof PersonaFacade) {
                List<Persona> e = facade.findAll();
                for (Persona persona : e) {
                    if (p.matcher(persona.getNif()).find() && Integer.parseInt(persona.getNif()) > n) {
                        n = Integer.parseInt(persona.getNif());
                    }
                }
            } else if (facade instanceof AccionFormativaFacade) {
                n = ((AccionFormativaFacade) facade).findLastCodAccionFormativa();
            }
            n = n + 1;
        } catch (Exception e) {
        }
        return n;
    }

    public static boolean esNifValido(String nif) {
        if (getTipoNif(nif) != NIF_NOVALIDO) {
            return true;
        }
        return false;
    }

    public static boolean esNifGenerado(String nif) {
        if (nif.startsWith("COM")) {
            return true;
        }
        try {
            Integer.parseInt(nif);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int getTipoNif(String nif) {
        nif = nif.toUpperCase();
        if (esNifGenerado(nif)) {
            return NIF_GENERADO;
        } else if (esCif(nif)) {
            return NIF_CIF;
        } else if (esDni(nif)) {
            return NIF_DNI;
        } else if (esNie(nif)) {
            return NIF_NIE;
        } else if (esNifOtro(nif)) {
            return NIF_OTRO;
        }
        return NIF_NOVALIDO;
    }
    private static final Pattern cifPattern = Pattern.compile("[[A-H][J-N][P-S]UVW][0-9]{7}[0-9A-J]");
    private static final String CONTROL_SOLO_NUMEROS = "ABEH"; // Sólo admiten números como carácter de control
    private static final String CONTROL_SOLO_LETRAS = "KPQS"; // Sólo admiten letras como carácter de control
    private static final String CONTROL_NUMERO_A_LETRA = "JABCDEFGHI"; // Conversión de dígito a letra de control.

    public static boolean esCif(String cif) {
        try {
            if (!cifPattern.matcher(cif).matches()) {
                // No cumple el patrón
                return false;
            }

            int parA = 0;
            for (int i = 2; i < 8; i += 2) {
                final int digito = Character.digit(cif.charAt(i), 10);
                if (digito < 0) {
                    return false;
                }
                parA += digito;
            }

            int nonB = 0;
            for (int i = 1; i < 9; i += 2) {
                final int digito = Character.digit(cif.charAt(i), 10);
                if (digito < 0) {
                    return false;
                }
                int nn = 2 * digito;
                if (nn > 9) {
                    nn = 1 + (nn - 10);
                }
                nonB += nn;
            }

            final int parcialC = parA + nonB;
            final int digitoE = parcialC % 10;
            final int digitoD = (digitoE > 0)
                    ? (10 - digitoE)
                    : 0;
            final char letraIni = cif.charAt(0);
            final char caracterFin = cif.charAt(8);

            final boolean esControlValido =
                    // ¿el carácter de control es válido como letra?
                    (CONTROL_SOLO_NUMEROS.indexOf(letraIni) < 0
                    && CONTROL_NUMERO_A_LETRA.charAt(digitoD) == caracterFin)
                    || // ¿el carácter de control es válido como dígito?
                    (CONTROL_SOLO_LETRAS.indexOf(letraIni) < 0
                    && digitoD == Character.digit(caracterFin, 10));
            return esControlValido;

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean esDni(String nif) {
        if (nif.length() != 9) {
            return false;
        }
        try {
            int letra = Integer.parseInt(nif.substring(0, nif.length() - 1));
            return (nif.contains(valores_dni[letra % 23]));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean esNie(String nif) {
        nif = nif.replace("X", "0");
        nif = nif.replace("Y", "1");
        nif = nif.replace("Z", "2");
        if (esDni(nif)) {
            return true;
        }
        return nif.startsWith("T");
    }

    private static boolean esNifOtro(String nif) {
        if (nif.length() != 9) {
            return false;
        }
        try {
            nif = nif.substring(1);
            String n = nif.substring(0, nif.length() - 1);
            int n_1 = 0;
            int n_2 = 0;
            for (int i = 0; i < n.length(); i++) {
                n_1 += Integer.parseInt("" + n.charAt(i));
                if (i % 2 != 0) {
                    String n_21 = "" + (Integer.parseInt("" + n.charAt(i)) * 2);
                    int n_22 = 0;
                    for (int j = 0; j < n_21.length(); ++j) {
                        n_22 += (Integer.parseInt("" + n_21.charAt(j)));
                    }
                    n_2 += n_22;
                }
            }
            int n_3 = n_1 + n_2;
            n_3 = 10 - Integer.parseInt(("" + n_3).substring(("" + n_3).length() - 1));
            return nif.contains(valores_nie_otros[n_3]);
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean validaCCC(String cadena) {
        try {
            String EntidadOficina, DigCtrl, NCuenta;

            int vals1[] = new int[8];
            int vals2[] = new int[10];

            int peso[] = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6};

            EntidadOficina = new String(cadena.substring(0, 8));
            DigCtrl = new String(cadena.substring(8, 10));
            NCuenta = new String(cadena.substring(10, 20));

            int suma1 = 0;
            for (int i = 0; i < 8; i++) {
                vals1[i] = Integer.parseInt(EntidadOficina.substring(i, i + 1)) * peso[i + 2];
                suma1 = suma1 + vals1[i];
            }
            int result1 = suma1 % 11;
            result1 = 11 - result1;
            if (result1 == 10) {
                result1 = 1;
            } else if (result1 == 11) {
                result1 = 0;
            }

            int suma2 = 0;
            for (int i = 0; i < 10; i++) {
                vals2[i] = Integer.parseInt(NCuenta.substring(i, i + 1)) * peso[i];
                suma2 = suma2 + vals2[i];
            }
            int result2 = suma2 % 11;
            result2 = 11 - result2;
            if (result2 == 10) {
                result2 = 1;
            } else if (result2 == 11) {
                result2 = 0;
            }

            if (result1 == Integer.parseInt(DigCtrl.substring(0, 1)) && result2 == Integer.parseInt(DigCtrl.substring(1, 2))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validaIBAN(String iban) {
        IBANCheckDigit ibanc = new IBANCheckDigit();
        return ibanc.isValid(iban);
    }

    public static boolean validaEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public static boolean validaNSS(String NSS, boolean empresa) {
        if (NSS.length() != 12 && !empresa) {
            return false;
        }
        if (NSS.length() != 11 && empresa) {
            return false;
        }
        if (NSS.length() == 11) {
            NSS = "0" + NSS;
        }
        try {
            Long.parseLong(NSS);
        } catch (Exception e) {
            return false;
        }
        try {
            long a = Integer.parseInt(NSS.substring(0, 2));
            long b = Integer.parseInt(NSS.substring(2, (2 + 8)));
            long c = Integer.parseInt(NSS.substring(10, (10 + 2)));
            long d = 0;
            if ((b * 1) < 10000000) {
                d = (b * 1) + (a * 10000000);
            } else {
                d = Long.parseLong(a + "" + b);
            }
            if (c == d % 97) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean validaTelefono(String Telefono) {
        try {
            int i = Integer.parseInt(Telefono);
            if (Telefono.length() != 9) {
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}