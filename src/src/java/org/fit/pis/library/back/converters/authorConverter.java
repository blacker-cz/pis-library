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
@FacesConverter(value="authorConverter")
public class authorConverter implements Converter {
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
                Collection<Author> authors = (Collection<Author>) value;
                StringBuilder sb = new StringBuilder();
                
                int i = 0, size = authors.size() - 1;
                for (Author a : authors) {
                    sb.append(a.getName());
                    if (i++ < size) {
                        sb.append(AUTHOR_SEPARATOR);
                    }
                }
                
                return sb.toString();
        } catch (ClassCastException e) {
                throw new ConverterException("Invalid Authors value");
        }
    }

}
