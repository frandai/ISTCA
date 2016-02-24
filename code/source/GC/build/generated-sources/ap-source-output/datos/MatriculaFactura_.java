package datos;

import datos.Factura;
import datos.Iva;
import datos.Matricula;
import datos.MatriculaFacturaPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(MatriculaFactura.class)
public class MatriculaFactura_ { 

    public static volatile SingularAttribute<MatriculaFactura, Iva> iva;
    public static volatile SingularAttribute<MatriculaFactura, Matricula> matricula1;
    public static volatile SingularAttribute<MatriculaFactura, Double> precio;
    public static volatile SingularAttribute<MatriculaFactura, MatriculaFacturaPK> matriculaFacturaPK;
    public static volatile SingularAttribute<MatriculaFactura, String> observaciones;
    public static volatile SingularAttribute<MatriculaFactura, Factura> factura1;

}