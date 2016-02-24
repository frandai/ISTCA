package datos;

import datos.EmpresaMatricula;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(CColectivo.class)
public class CColectivo_ { 

    public static volatile SingularAttribute<CColectivo, Integer> id;
    public static volatile SingularAttribute<CColectivo, String> nombre;
    public static volatile ListAttribute<CColectivo, EmpresaMatricula> empresaMatriculaList;

}