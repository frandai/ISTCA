package datos;

import datos.Comercial;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(ComercialTipo.class)
public class ComercialTipo_ { 

    public static volatile SingularAttribute<ComercialTipo, Integer> id;
    public static volatile SingularAttribute<ComercialTipo, String> nombre;
    public static volatile ListAttribute<ComercialTipo, Comercial> comercialList;

}