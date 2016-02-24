package datos;

import datos.CColectivo;
import datos.Empresa;
import datos.EmpresaAgrupacion;
import datos.EmpresaMatriculaAnio;
import datos.EmpresaMatriculaCcc;
import datos.EmpresaMatriculaCredito;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(EmpresaMatricula.class)
public class EmpresaMatricula_ { 

    public static volatile ListAttribute<EmpresaMatricula, EmpresaMatriculaCcc> empresaMatriculaCccList;
    public static volatile SingularAttribute<EmpresaMatricula, String> nif;
    public static volatile SingularAttribute<EmpresaMatricula, String> cotSs;
    public static volatile SingularAttribute<EmpresaMatricula, Empresa> empresa;
    public static volatile SingularAttribute<EmpresaMatricula, Date> fechaCreacion;
    public static volatile ListAttribute<EmpresaMatricula, EmpresaMatriculaCredito> empresaMatriculaCreditoList;
    public static volatile SingularAttribute<EmpresaMatricula, CColectivo> cColectivo;
    public static volatile SingularAttribute<EmpresaMatricula, Boolean> representacionLegal;
    public static volatile SingularAttribute<EmpresaMatricula, EmpresaAgrupacion> agrupacion;
    public static volatile ListAttribute<EmpresaMatricula, EmpresaMatriculaAnio> empresaMatriculaAnioList;

}