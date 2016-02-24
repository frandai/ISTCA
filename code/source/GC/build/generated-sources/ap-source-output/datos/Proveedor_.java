package datos;

import datos.AccionFormativa;
import datos.Empresa;
import datos.Factura;
import datos.Grupo;
import datos.ProveedorFactura;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Proveedor.class)
public class Proveedor_ { 

    public static volatile ListAttribute<Proveedor, Grupo> grupoList;
    public static volatile ListAttribute<Proveedor, Factura> facturaList;
    public static volatile ListAttribute<Proveedor, ProveedorFactura> proveedorFacturaList;
    public static volatile SingularAttribute<Proveedor, String> nif;
    public static volatile SingularAttribute<Proveedor, Boolean> generaSeguimientoTutorial;
    public static volatile SingularAttribute<Proveedor, Empresa> empresa;
    public static volatile ListAttribute<Proveedor, AccionFormativa> accionFormativaList;

}