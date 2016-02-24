package datos;

import datos.AlumnoAreaFuncional;
import datos.AlumnoCategoriaProfesional;
import datos.AlumnoComprobacion;
import datos.AlumnoGrupoCotizacion;
import datos.AlumnoNivelEstudio;
import datos.Matricula;
import datos.Persona;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Alumno.class)
public class Alumno_ { 

    public static volatile ListAttribute<Alumno, AlumnoComprobacion> alumnoComprobacionList;
    public static volatile SingularAttribute<Alumno, Boolean> victimaTerrorismo;
    public static volatile SingularAttribute<Alumno, String> otrasTitulaciones;
    public static volatile SingularAttribute<Alumno, Boolean> victimaVG;
    public static volatile SingularAttribute<Alumno, Boolean> discapacitado;
    public static volatile SingularAttribute<Alumno, String> nif;
    public static volatile SingularAttribute<Alumno, AlumnoGrupoCotizacion> grupoCotizacion;
    public static volatile ListAttribute<Alumno, Matricula> matriculaList;
    public static volatile SingularAttribute<Alumno, Boolean> autonomo;
    public static volatile SingularAttribute<Alumno, AlumnoAreaFuncional> areaFuncional;
    public static volatile SingularAttribute<Alumno, String> observaciones;
    public static volatile SingularAttribute<Alumno, AlumnoCategoriaProfesional> categoriaProfesional;
    public static volatile SingularAttribute<Alumno, AlumnoNivelEstudio> nivelEstudios;
    public static volatile SingularAttribute<Alumno, Persona> persona;

}