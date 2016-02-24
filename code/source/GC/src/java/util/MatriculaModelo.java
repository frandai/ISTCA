/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import bean.MatriculaFacade;
import datos.Matricula;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 *
 */
public class MatriculaModelo extends ListDataModel<Matricula> implements SelectableDataModel<Matricula> {

    MatriculaFacade matfacade;

    public MatriculaModelo(MatriculaFacade mf) {
        matfacade = mf;
    }

    public MatriculaModelo(List<Matricula> list, MatriculaFacade mf) {
        super(list);
        matfacade = mf;
    }

    @Override
    public Object getRowKey(Matricula object) {
        return object.getId();
    }

    @Override
    public Matricula getRowData(String rowKey) {
        return matfacade.find(Integer.parseInt(rowKey));
    }
    
}
