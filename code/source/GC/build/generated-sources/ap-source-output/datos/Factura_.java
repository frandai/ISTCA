package datos;

import datos.FacturaHistoricoEstado;
import datos.FormaPago;
import datos.MatriculaFactura;
import datos.Proveedor;
import datos.Remesa;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Factura.class)
public class Factura_ { 

    public static volatile SingularAttribute<Factura, String> id;
    public static volatile SingularAttribute<Factura, Date> fecha;
    public static volatile SingularAttribute<Factura, FormaPago> formaPago;
    public static volatile ListAttribute<Factura, Remesa> remesaList;
    public static volatile ListAttribute<Factura, MatriculaFactura> matriculaFacturaList;
    public static volatile SingularAttribute<Factura, String> observaciones;
    public static volatile ListAttribute<Factura, FacturaHistoricoEstado> facturaHistoricoEstadoList;
    public static volatile SingularAttribute<Factura, Proveedor> proveedor;

}