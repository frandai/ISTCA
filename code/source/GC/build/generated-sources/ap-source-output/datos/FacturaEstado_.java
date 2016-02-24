package datos;

import datos.FacturaHistoricoEstado;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(FacturaEstado.class)
public class FacturaEstado_ { 

    public static volatile SingularAttribute<FacturaEstado, Integer> id;
    public static volatile SingularAttribute<FacturaEstado, String> nombre;
    public static volatile ListAttribute<FacturaEstado, FacturaHistoricoEstado> facturaHistoricoEstadoList;

}