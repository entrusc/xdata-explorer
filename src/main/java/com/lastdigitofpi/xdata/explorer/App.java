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

import java.io.File;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * XData Explorer
 */
public class App {

    public static void main(String[] args) throws Exception {
        activateLookAndFeel();
        
        File fileToOpen = null;
        if (args.length > 0) {
            fileToOpen = new File(args[0]);
        }
        
        MainFrame frame = new MainFrame(fileToOpen);
        frame.setVisible(true);
    }
    
    private static void activateLookAndFeel() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
    }        
}
