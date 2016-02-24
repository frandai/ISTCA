package datos;

import datos.Evento;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(EventoTipo.class)
public class EventoTipo_ { 

    public static volatile SingularAttribute<EventoTipo, Integer> id;
    public static volatile SingularAttribute<EventoTipo, String> nombre;
    public static volatile ListAttribute<EventoTipo, Evento> eventoList;

}