/**
 * 
 */

package org.fit.pis.library.back.converters;

import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Vojtěch Sysel <xsysel03@stud.fit.vutbr.cz>
 */
@FacesConverter(value="dateConverter")
public class DateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date d = df.parse(value);            
            return d;
        } catch (ParseException e) {
            throw new ConverterException("Invalid ID format");
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
		Calendar cal = Calendar.getInstance();
        try {
            Date val = (Date) value;
			// add one day
			cal.setTime(val);
			cal.add(Calendar.HOUR, 24);
			val = cal.getTime();
			
			// format date
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
            return dateformat.format(val);
            
        } catch (ClassCastException e) {
            throw new ConverterException("Invalid ID value");
        }
    }

}
