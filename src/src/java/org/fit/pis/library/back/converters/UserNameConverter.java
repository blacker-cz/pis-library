/**
 * 
 */

package org.fit.pis.library.back.converters;

import java.util.Collection;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.fit.pis.library.data.*;

/**
 *
 * @author Vojtěch Sysel <xsysel03@stud.fit.vutbr.cz>
 */
@FacesConverter(value="userNameConverter")
public class UserNameConverter implements Converter {
    public static final String AUTHOR_SEPARATOR = ", ";

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Get authors collection and make nice string
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
                User user = (User) value;
                StringBuilder sb = new StringBuilder();
                
                sb.append(user.getForename())
					.append(" ")
					.append(user.getSurname());
                
                return sb.toString();
        } catch (ClassCastException e) {
                throw new ConverterException("Invalid Authors value (" + value.toString() + ")");
        }
    }

}
