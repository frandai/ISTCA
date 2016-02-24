package datos;

import datos.Direccion;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(TipoVia.class)
public class TipoVia_ { 

    public static volatile SingularAttribute<TipoVia, Integer> id;
    public static volatile SingularAttribute<TipoVia, String> nombre;
    public static volatile SingularAttribute<TipoVia, String> abreviatura;
    public static volatile ListAttribute<TipoVia, Direccion> direccionList;

}