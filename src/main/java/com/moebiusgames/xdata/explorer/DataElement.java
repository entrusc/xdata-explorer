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
package com.moebiusgames.xdata.explorer;

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.model != null ? this.model.hashCode() : 0);
        hash = 53 * hash + (this.key != null ? this.key.hashCode() : 0);
        return hash;
    }

    /**
     * as these items contain the whole subtree we only
     * compare based on key and model at this level
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataElement other = (DataElement) obj;
        if (this.model != other.model && (this.model == null || !this.model.equals(other.model))) {
            return false;
        }
        if ((this.key == null) ? (other.key != null) : !this.key.equals(other.key)) {
            return false;
        }
        return true;
    }

}
