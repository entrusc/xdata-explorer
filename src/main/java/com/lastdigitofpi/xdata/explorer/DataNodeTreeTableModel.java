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

import com.lastdigitofpi.xdata.DataKey;
import com.lastdigitofpi.xdata.DataNode;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import org.jdesktop.swingx.treetable.TreeTableModel;

/**
 * 
 * @author Florian Frankenberger
 */
public class DataNodeTreeTableModel implements TreeTableModel {

    private final DataNode node;
    private final List<TreeModelListener> treeModelListeners = new ArrayList<TreeModelListener>();

    public DataNodeTreeTableModel(DataNode node) {
        this.node = node;
    }
    
    @Override
    public Class<?> getColumnClass(int i) {
        switch (i) {
            case 0:
                return Object.class;
            default:
                return String.class;
        }
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int i) {
        switch (i) {
            case 0:
                return "Element";
            case 1:
                return "Type";
            case 2:
                return "Value";
            default:
                return "";
        }
    }

    @Override
    public int getHierarchicalColumn() {
        return 0;
    }

    @Override
    public Object getValueAt(Object o, int i) {
        DataElement dataElement = (DataElement) o;
        Object value = dataElement.getValue();
        switch (i) {
            case 0:
                return "";
            case 1:
                if (value == null) {
                    return "";
                } else if (value instanceof Integer) {
                    return "int";
                } else if (value instanceof Long) {
                    return "long";
                } else if (value instanceof Short) {
                    return "short";
                } else if (value instanceof Byte) {
                    return "byte";
                } else if (value instanceof Character) {
                    return "char";
                } else if (value instanceof Float) {
                    return "float";
                } else if (value instanceof Double) {
                    return "double";
                } else if (value instanceof Boolean) {
                    return "boolean";
                } else if (value instanceof String) {
                    return "string";
                } else if (value instanceof Date) {
                    return "date";
                } else if (value instanceof URL) {
                    return "url";
                } else if (value instanceof List) {
                    return "list";
                } else if (value instanceof DataNode) {
                    return "datanode";
                } else {
                    return "unknown";
                }
            case 2:
                if (isLeaf(o)) {
                    return value == null ? "null" : value.toString();
                } else {
                    return "";
                }
        }

        return "";
    }

    @Override
    public boolean isCellEditable(Object o, int i) {
        return false;
    }

    @Override
    public void setValueAt(Object o, Object o1, int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getRoot() {
        return new DataElement(this, null, "root", node);
    }

    @Override
    public Object getChild(Object parent, int index) {
        DataElement dataElement = (DataElement) parent;
        Object value = dataElement.getValue();
        if (value instanceof DataNode) {
            DataNode aNode = (DataNode) value;
            String key = aNode.getRawKeys().get(index);
            Object aValue = aNode.getRawValues().get(index);
            return new DataElement(this, dataElement, key, aValue);
        } else
            if (value instanceof List) {
                List<Object> list = (List<Object>) value;
                Object aValue = list.get(index);
                return new DataElement(this, dataElement, String.valueOf(index), aValue);
            } else {
                return null;
            }
    }
    
    @Override
    public int getChildCount(Object parent) {
        DataElement dataElement = (DataElement) parent;
        Object value = dataElement.getValue();
        if (value instanceof DataNode) {
            DataNode aNode = (DataNode) value;
            return aNode.getSize();
        } else
            if (value instanceof List) {
                List<Object> list = (List<Object>) value;
                return list.size();
            } else {
                return 0;
            }
    }

    @Override
    public boolean isLeaf(Object node) {
        DataElement dataElement = (DataElement) node;
        Object value = dataElement.getValue();
        return !(value instanceof DataNode || value instanceof List);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if (parent == null) {
            return 0;
        }
        DataElement dataElement = (DataElement) parent;
        Object value = dataElement.getValue();
        if (value instanceof DataNode) {
            DataNode aNode = (DataNode) value;
            return aNode.getRawValues().indexOf(child);
        } else
            if (value instanceof List) {
                List<Object> list = (List<Object>) value;
                return list.indexOf(child);
            } else {
                return 0;
            }
    }
    
    /**
     * synchronizes the data in the element with the actual data node and
     * notifies the view about the change.
     * 
     * @param element 
     */
    public void notifyOnChanged(DataElement element) {
        final List<DataElement> pathList = new ArrayList<DataElement>();
        DataElement currentElement = element;
        while (currentElement != null) {
            pathList.add(currentElement);
            currentElement = currentElement.getParent();
        }
        pathList.remove(0);
        DataElement parent = pathList.get(0);
        Collections.reverse(pathList);

        final Object parentObject;
        if (parent == null) {
            parentObject = node;
        } else {
            parentObject = parent.getValue();
        }
        
        if (parentObject instanceof DataNode) {
            DataNode aNode = (DataNode) parentObject;
            aNode.setObject(DataKey.create(element.getKey(), Object.class), element.getValue());
        } else
            if (parentObject instanceof List) {
                List<Object> list = (List<Object>) parentObject;
                int index = Integer.valueOf(element.getKey());
                list.set(index, element.getValue());
            }
        
        final Object[] path = pathList.toArray();
        final TreeModelEvent e = new TreeModelEvent(this, path, new int[] {getIndexOfChild(parent, element.getValue())}, new Object[] {element});
        
        for (TreeModelListener listener : treeModelListeners) {
            listener.treeNodesChanged(e);
        }
    }
    
    @Override
    public void addTreeModelListener(TreeModelListener l) {
        treeModelListeners.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        treeModelListeners.remove(l);
    }

}
