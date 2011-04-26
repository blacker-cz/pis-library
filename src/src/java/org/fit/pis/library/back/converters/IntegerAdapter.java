/*
 */

package org.fit.pis.library.back.converters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
public class IntegerAdapter extends XmlAdapter<String, Integer> {

    public Integer unmarshal(String s) {
        return Integer.parseInt(s);
    }

    public String marshal(Integer number) {
        if (number == null) return "";

        return number.toString();
    }
}