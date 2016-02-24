package datos;

import datos.Empresa;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(EmpresaEstado.class)
public class EmpresaEstado_ { 

    public static volatile SingularAttribute<EmpresaEstado, Integer> id;
    public static volatile SingularAttribute<EmpresaEstado, String> nombre;
    public static volatile ListAttribute<EmpresaEstado, Empresa> empresaList;

}