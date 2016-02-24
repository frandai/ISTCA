package datos;

import datos.EmpresaTelefono;
import datos.PersonaTelefono;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(TelefonoTipo.class)
public class TelefonoTipo_ { 

    public static volatile SingularAttribute<TelefonoTipo, Integer> id;
    public static volatile SingularAttribute<TelefonoTipo, String> nombre;
    public static volatile ListAttribute<TelefonoTipo, PersonaTelefono> personaTelefonoList;
    public static volatile ListAttribute<TelefonoTipo, EmpresaTelefono> empresaTelefonoList;

}