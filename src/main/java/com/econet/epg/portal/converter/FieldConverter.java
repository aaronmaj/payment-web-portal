package com.econet.epg.portal.converter;

import com.econet.epg.portal.model.Field;
import com.econet.epg.portal.rest.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import java.util.List;
import java.util.function.Predicate;


@FacesConverter(forClass=Field.class)
public class FieldConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        Object object = null;
        if (!(value == null || value.isEmpty())) {
            object = this.getSelectedItemAsEntity(uiComponent, value);
        }
        return object;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof Field) {
            return String.valueOf(((Field) value).getId());
        } else {
            throw new ConverterException(new FacesMessage(value + " is not a valid Field"));
        }
    }
    private Field getSelectedItemAsEntity(UIComponent comp, String value) {
        Field item = null;
        List<Field> selectItems = null;
        for (UIComponent uic : comp.getChildren()) {
            if (uic instanceof UISelectItems) {
                Long itemId = Long.valueOf(value);
                selectItems = (List<Field>) ((UISelectItems) uic).getValue();

                if (itemId != null && selectItems != null && !selectItems.isEmpty()) {
                    Predicate<Field> predicate = i -> i.getId().equals(itemId);
                    item = selectItems.stream().filter(predicate).findFirst().orElse(null);
                }
            }
        }

        return item;
    }
}
