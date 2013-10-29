/*
 * Copyright (C) 2013 Florian Frankenberger
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.lastdigitofpi.xdata.explorer;

import java.util.List;
import org.jdesktop.swingx.renderer.StringValue;

/**
 * 
 * @author Florian Frankenberger
 */
public class DataNodeStringValue implements StringValue {

    @Override
    public String getString(Object o) {
        DataElement dataElement = (DataElement) o;
        if (dataElement.getParent() != null && dataElement.getParent().getValue() instanceof List) {
            return "[" + dataElement.getKey() + "]";
        } else {
            return dataElement.getKey();
        }        
    }

}
