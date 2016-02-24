package jsf;

import datos.EmpresaMatriculaAnio;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import bean.EmpresaMatriculaAnioFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "empresaMatriculaAnioController")
@SessionScoped
public class EmpresaMatriculaAnioController implements Serializable {

    private EmpresaMatriculaAnio current;
    private DataModel items = null;
    @EJB
    private bean.EmpresaMatriculaAnioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public EmpresaMatriculaAnioController() {
    }

    public EmpresaMatriculaAnio getSelected() {
        if (current == null) {
            current = new EmpresaMatriculaAnio();
            current.setEmpresaMatriculaAnioPK(new datos.EmpresaMatriculaAnioPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private EmpresaMatriculaAnioFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (EmpresaMatriculaAnio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new EmpresaMatriculaAnio();
        current.setEmpresaMatriculaAnioPK(new datos.EmpresaMatriculaAnioPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EmpresaMatriculaAnioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (EmpresaMatriculaAnio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EmpresaMatriculaAnioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (EmpresaMatriculaAnio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EmpresaMatriculaAnioDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = EmpresaMatriculaAnio.class)
    public static class EmpresaMatriculaAnioControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmpresaMatriculaAnioController controller = (EmpresaMatriculaAnioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "empresaMatriculaAnioController");
            return controller.ejbFacade.find(getKey(value));
        }

        datos.EmpresaMatriculaAnioPK getKey(String value) {
            datos.EmpresaMatriculaAnioPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new datos.EmpresaMatriculaAnioPK();
            key.setNif(values[0]);
            key.setAnio(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(datos.EmpresaMatriculaAnioPK value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value.getNif());
            sb.append(SEPARATOR);
            sb.append(value.getAnio());
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof EmpresaMatriculaAnio) {
                EmpresaMatriculaAnio o = (EmpresaMatriculaAnio) object;
                return getStringKey(o.getEmpresaMatriculaAnioPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + EmpresaMatriculaAnio.class.getName());
            }
        }
    }
}
