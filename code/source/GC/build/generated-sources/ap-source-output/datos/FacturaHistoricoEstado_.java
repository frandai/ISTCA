package datos;

import datos.Factura;
import datos.FacturaEstado;
import datos.FacturaHistoricoEstadoPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(FacturaHistoricoEstado.class)
public class FacturaHistoricoEstado_ { 

    public static volatile SingularAttribute<FacturaHistoricoEstado, FacturaEstado> estado;
    public static volatile SingularAttribute<FacturaHistoricoEstado, FacturaHistoricoEstadoPK> facturaHistoricoEstadoPK;
    public static volatile SingularAttribute<FacturaHistoricoEstado, Factura> factura1;

}