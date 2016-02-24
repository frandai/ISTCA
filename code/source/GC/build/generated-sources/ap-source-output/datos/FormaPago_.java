package datos;

import datos.Factura;
import datos.Matricula;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(FormaPago.class)
public class FormaPago_ { 

    public static volatile SingularAttribute<FormaPago, Integer> id;
    public static volatile SingularAttribute<FormaPago, String> nombre;
    public static volatile ListAttribute<FormaPago, Factura> facturaList;
    public static volatile ListAttribute<FormaPago, Matricula> matriculaList;

}