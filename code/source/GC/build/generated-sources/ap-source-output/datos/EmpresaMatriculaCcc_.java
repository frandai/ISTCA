package datos;

import datos.EmpresaMatricula;
import datos.EmpresaMatriculaCccPK;
import datos.Matricula;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(EmpresaMatriculaCcc.class)
public class EmpresaMatriculaCcc_ { 

    public static volatile SingularAttribute<EmpresaMatriculaCcc, EmpresaMatriculaCccPK> empresaMatriculaCccPK;
    public static volatile SingularAttribute<EmpresaMatriculaCcc, EmpresaMatricula> empresaMatricula;
    public static volatile ListAttribute<EmpresaMatriculaCcc, Matricula> matriculaList;
    public static volatile SingularAttribute<EmpresaMatriculaCcc, String> iban;

}