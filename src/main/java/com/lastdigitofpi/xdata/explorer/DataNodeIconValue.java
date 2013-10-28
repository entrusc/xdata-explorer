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

import com.lastdigitofpi.xdata.DataNode;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.jdesktop.swingx.renderer.IconValue;

/**
 *
 * @author Florian Frankenberger
 */
public class DataNodeIconValue implements IconValue {

    public static final Icon ICON_INTEGER, ICON_STRING, ICON_FLOAT, ICON_BOOLEAN, ICON_LIST, ICON_DATANODE, ICON_DATE, ICON_URL, ICON_NULL;

    static {
        try {
            ICON_INTEGER = new ImageIcon(ImageIO.read(DataNodeIconValue.class.getResourceAsStream("/int.png")));
            ICON_STRING = new ImageIcon(ImageIO.read(DataNodeIconValue.class.getResourceAsStream("/string.png")));
            ICON_FLOAT = new ImageIcon(ImageIO.read(DataNodeIconValue.class.getResourceAsStream("/float.png")));
            ICON_BOOLEAN = new ImageIcon(ImageIO.read(DataNodeIconValue.class.getResourceAsStream("/boolean.png")));
            ICON_DATE = new ImageIcon(ImageIO.read(DataNodeIconValue.class.getResourceAsStream("/date.png")));
            ICON_URL = new ImageIcon(ImageIO.read(DataNodeIconValue.class.getResourceAsStream("/url.png")));
            ICON_LIST = new ImageIcon(ImageIO.read(DataNodeIconValue.class.getResourceAsStream("/list.png")));
            ICON_DATANODE = new ImageIcon(ImageIO.read(DataNodeIconValue.class.getResourceAsStream("/node.png")));
            ICON_NULL = new ImageIcon(ImageIO.read(DataNodeIconValue.class.getResourceAsStream("/null.png")));
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public Icon getIcon(Object o) {
        DataElement element = (DataElement) o;
        Object value = element.getValue();
        if (value instanceof Integer
                || value instanceof Long
                || value instanceof Short
                || value instanceof Byte
                || value instanceof Character) {
            return ICON_INTEGER;
        } else if (value instanceof Float
                || value instanceof Double) {
            return ICON_FLOAT;
        } else if (value instanceof Date) {
            return ICON_DATE;
        } else if (value instanceof URL) {
            return ICON_URL;
        } else if (value instanceof String) {
            return ICON_STRING;
        } else if (value instanceof List) {
            return ICON_LIST;
        } else if (value instanceof Boolean) {
            return ICON_BOOLEAN;
        } else if (value instanceof DataNode) {
            return ICON_DATANODE;
        } else {
            return ICON_NULL;
        }

    }

}
