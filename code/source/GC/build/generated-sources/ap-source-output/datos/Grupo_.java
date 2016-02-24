package datos;

import datos.AccionFormativa;
import datos.DiaSemana;
import datos.Direccion;
import datos.GrupoPK;
import datos.Matricula;
import datos.PersonaTelefono;
import datos.Proveedor;
import datos.Tutor;
import datos.TutoriaHorario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Grupo.class)
public class Grupo_ { 

    public static volatile ListAttribute<Grupo, Tutor> tutorList;
    public static volatile SingularAttribute<Grupo, Proveedor> proveedor1;
    public static volatile ListAttribute<Grupo, DiaSemana> diaSemanaList;
    public static volatile SingularAttribute<Grupo, AccionFormativa> accionFormativa1;
    public static volatile SingularAttribute<Grupo, Date> horaIM;
    public static volatile SingularAttribute<Grupo, Date> horaFT;
    public static volatile SingularAttribute<Grupo, PersonaTelefono> personaTelefono;
    public static volatile SingularAttribute<Grupo, GrupoPK> grupoPK;
    public static volatile SingularAttribute<Grupo, Date> fFin;
    public static volatile ListAttribute<Grupo, TutoriaHorario> tutoriaHorarioList;
    public static volatile SingularAttribute<Grupo, String> observaciones;
    public static volatile SingularAttribute<Grupo, Boolean> aportPrivada;
    public static volatile SingularAttribute<Grupo, String> nombre;
    public static volatile SingularAttribute<Grupo, Boolean> mediosC;
    public static volatile SingularAttribute<Grupo, Boolean> mediosEO;
    public static volatile ListAttribute<Grupo, Direccion> direccionList;
    public static volatile ListAttribute<Grupo, Matricula> matriculaList;
    public static volatile SingularAttribute<Grupo, Date> horaIT;
    public static volatile SingularAttribute<Grupo, Date> fInicio;
    public static volatile SingularAttribute<Grupo, Boolean> mediosP;
    public static volatile SingularAttribute<Grupo, Date> horaFM;

}