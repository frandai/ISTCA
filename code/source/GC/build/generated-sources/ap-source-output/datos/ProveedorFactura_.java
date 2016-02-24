package datos;

import datos.Proveedor;
import datos.ProveedorFacturaPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(ProveedorFactura.class)
public class ProveedorFactura_ { 

    public static volatile SingularAttribute<ProveedorFactura, Proveedor> proveedor1;
    public static volatile SingularAttribute<ProveedorFactura, Date> fecha;
    public static volatile SingularAttribute<ProveedorFactura, ProveedorFacturaPK> proveedorFacturaPK;

}