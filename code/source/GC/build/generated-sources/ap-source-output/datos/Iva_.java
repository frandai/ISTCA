package datos;

import datos.MatriculaFactura;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Iva.class)
public class Iva_ { 

    public static volatile SingularAttribute<Iva, Integer> id;
    public static volatile SingularAttribute<Iva, Double> porcentaje;
    public static volatile ListAttribute<Iva, MatriculaFactura> matriculaFacturaList;
    public static volatile SingularAttribute<Iva, Date> fechaVigor;

}