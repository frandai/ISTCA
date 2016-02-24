package datos;

import datos.Empresa;
import datos.EmpresaTelefonoPK;
import datos.TelefonoTipo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(EmpresaTelefono.class)
public class EmpresaTelefono_ { 

    public static volatile SingularAttribute<EmpresaTelefono, TelefonoTipo> tipo;
    public static volatile SingularAttribute<EmpresaTelefono, EmpresaTelefonoPK> empresaTelefonoPK;
    public static volatile SingularAttribute<EmpresaTelefono, Empresa> empresa1;

}