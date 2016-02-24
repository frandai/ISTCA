package datos;

import datos.Alumno;
import datos.Direccion;
import datos.EmpresaMatriculaCcc;
import datos.Evento;
import datos.FormaPago;
import datos.Grupo;
import datos.MatriculaComercial;
import datos.MatriculaFactura;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Matricula.class)
public class Matricula_ { 

    public static volatile SingularAttribute<Matricula, EmpresaMatriculaCcc> empresaMatriculaCcc;
    public static volatile SingularAttribute<Matricula, Grupo> grupo;
    public static volatile SingularAttribute<Matricula, FormaPago> formaPago;
    public static volatile SingularAttribute<Matricula, Double> importeBonificar;
    public static volatile ListAttribute<Matricula, Evento> eventoList;
    public static volatile SingularAttribute<Matricula, String> observaciones;
    public static volatile SingularAttribute<Matricula, Alumno> alumno;
    public static volatile SingularAttribute<Matricula, Date> importeBonificarFecha;
    public static volatile SingularAttribute<Matricula, Boolean> baja;
    public static volatile SingularAttribute<Matricula, Integer> id;
    public static volatile SingularAttribute<Matricula, Double> precio;
    public static volatile SingularAttribute<Matricula, Direccion> direccionEnvio;
    public static volatile ListAttribute<Matricula, MatriculaFactura> matriculaFacturaList;
    public static volatile SingularAttribute<Matricula, Date> fechaEntrega;
    public static volatile ListAttribute<Matricula, MatriculaComercial> matriculaComercialList;
    public static volatile SingularAttribute<Matricula, String> observacionesEnivo;
    public static volatile SingularAttribute<Matricula, Date> fechaCreacion;
    public static volatile SingularAttribute<Matricula, Date> fechaFirma;

}