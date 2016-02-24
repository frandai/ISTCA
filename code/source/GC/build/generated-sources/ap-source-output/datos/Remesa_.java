package datos;

import datos.Factura;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Remesa.class)
public class Remesa_ { 

    public static volatile SingularAttribute<Remesa, String> id;
    public static volatile SingularAttribute<Remesa, Boolean> agrupada;
    public static volatile SingularAttribute<Remesa, Double> totalRemesa;
    public static volatile SingularAttribute<Remesa, Integer> numRegistros;
    public static volatile ListAttribute<Remesa, Factura> facturaList;
    public static volatile SingularAttribute<Remesa, Date> fecha;
    public static volatile SingularAttribute<Remesa, String> archivo;
    public static volatile SingularAttribute<Remesa, Boolean> cobrada;
    public static volatile SingularAttribute<Remesa, Date> fechaCobro;

}