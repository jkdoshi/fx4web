/*
 * Copyright (c) 2006 Jitesh Doshi
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.doshiland.fx4web.jsf;

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
