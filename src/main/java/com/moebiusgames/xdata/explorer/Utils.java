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

import javax.swing.tree.TreePath;

/**
 *
 * @author Florian Frankenberger
 */
public class Utils {

    private Utils() {
    }

    public static String pathToString(TreePath path) {
        final StringBuilder sb = new StringBuilder();
        if (path != null) {
            final Object[] objectPath = path.getPath();
            for (int i = 0; i < objectPath.length; ++i) {
                DataElement element = (DataElement) objectPath[i];
                if (i > 0) {
                    sb.append(" > ");
                }
                sb.append(element.getKey());
            }
        }
        return sb.toString();
    }

}
