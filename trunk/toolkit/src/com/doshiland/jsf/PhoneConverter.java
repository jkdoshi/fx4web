package com.doshiland.jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class PhoneConverter implements Converter {
    public Object getAsObject(FacesContext facesContext, UIComponent component, String formattedString) throws ConverterException {
        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) throws ConverterException {
        StringBuffer buf = new StringBuffer();
        String string = String.valueOf(object);
        if(string != null) {
            int len = string.length();
            if(len > 7) {
                buf.append(string.substring(0, len-7));
                buf.append(string.substring(len-7, len-4));
                buf.append(string.substring(len-4, len));
            } else if(len > 4) {
                buf.append(string.substring(0, len-4));
                buf.append(string.substring(len-4, len));
            } else {
                buf.append(string);
            }
        }
        return buf.toString();
    }
}
