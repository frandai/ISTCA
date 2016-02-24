package datos;

import datos.AccionFormativaCncp;
import datos.AccionFormativaExtra;
import datos.AccionFormativaGrupo;
import datos.AccionFormativaGrupoFundacion;
import datos.AccionFormativaModalidad;
import datos.Grupo;
import datos.Proveedor;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(AccionFormativa.class)
public class AccionFormativa_ { 

    public static volatile SingularAttribute<AccionFormativa, AccionFormativaExtra> accionFormativaExtra;
    public static volatile SingularAttribute<AccionFormativa, String> objetivos;
    public static volatile ListAttribute<AccionFormativa, Grupo> grupoList;
    public static volatile SingularAttribute<AccionFormativa, AccionFormativaModalidad> modalidad;
    public static volatile SingularAttribute<AccionFormativa, Short> horas;
    public static volatile SingularAttribute<AccionFormativa, Boolean> nivelFormacionSuperior;
    public static volatile SingularAttribute<AccionFormativa, Boolean> tipoAccionEspecifica;
    public static volatile SingularAttribute<AccionFormativa, String> observaciones;
    public static volatile SingularAttribute<AccionFormativa, AccionFormativaCncp> cncp;
    public static volatile SingularAttribute<AccionFormativa, Integer> id;
    public static volatile SingularAttribute<AccionFormativa, String> nombre;
    public static volatile SingularAttribute<AccionFormativa, Double> precio;
    public static volatile SingularAttribute<AccionFormativa, AccionFormativaGrupoFundacion> accionFormativaGrupoFundacion;
    public static volatile ListAttribute<AccionFormativa, Proveedor> proveedorList;
    public static volatile SingularAttribute<AccionFormativa, String> contenidos;
    public static volatile SingularAttribute<AccionFormativa, AccionFormativaGrupo> accionFormativaGrupo;

}