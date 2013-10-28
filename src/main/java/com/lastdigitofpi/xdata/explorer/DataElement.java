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

/**
 * 
 * @author Florian Frankenberger
 */
public class DataElement {

    private final DataNodeTreeTableModel model;
    
    private final DataElement parent;
    private final String key;
    private Object value;

    public DataElement(DataNodeTreeTableModel model, DataElement parent, String key, Object value) {
        this.model = model;
        this.parent = parent;
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public DataNodeTreeTableModel getModel() {
        return model;
    }

    public DataElement getParent() {
        return parent;
    }
    
    public void setValue(Object value) {
        this.value = value;
        this.model.notifyOnChanged(this);
    }
}
