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

import java.awt.Color;
import java.awt.Component;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.TreePath;

/**
 *
 * @author Florian Frankenberger
 */
public class EditDataDialog extends javax.swing.JDialog {

    private static abstract class TypeWrapper<T> {

        public abstract Class<T> getType();

        /**
         * if null is returned then the string is not valid
         *
         * @param string
         * @return
         */
        public abstract T wrap(String string);

        public String unWrap(T object) {
            return object.toString();
        }

        public boolean enableEditor() {
            return true;
        }

        @Override
        public String toString() {
            return getType().getSimpleName();
        }

        public abstract Icon getIcon();

    }

    private static final TypeWrapper[] TYPE_WRAPPERS = new TypeWrapper[]{
        new TypeWrapper<String>() {

            @Override
            public Class<String> getType() {
                return String.class;
            }

            @Override
            public String wrap(String string) {
                return string;
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_STRING;
            }

        },
        new TypeWrapper<Byte>() {

            @Override
            public Class<Byte> getType() {
                return Byte.class;
            }

            @Override
            public Byte wrap(String string) {
                try {
                    return Byte.parseByte(string);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_INTEGER;
            }

        },
        new TypeWrapper<Short>() {

            @Override
            public Class<Short> getType() {
                return Short.class;
            }

            @Override
            public Short wrap(String string) {
                try {
                    return Short.parseShort(string);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_INTEGER;
            }
        },
        new TypeWrapper<Character>() {

            @Override
            public Class<Character> getType() {
                return Character.class;
            }

            @Override
            public Character wrap(String string) {
                try {
                    return Character.forDigit(Integer.valueOf(string), 10);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_INTEGER;
            }
        },
        new TypeWrapper<Integer>() {

            @Override
            public Class<Integer> getType() {
                return Integer.class;
            }

            @Override
            public Integer wrap(String string) {
                try {
                    return Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_INTEGER;
            }
        },
        new TypeWrapper<Long>() {

            @Override
            public Class<Long> getType() {
                return Long.class;
            }

            @Override
            public Long wrap(String string) {
                try {
                    return Long.valueOf(string);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_INTEGER;
            }
        },
        new TypeWrapper<Float>() {

            @Override
            public Class<Float> getType() {
                return Float.class;
            }

            @Override
            public Float wrap(String string) {
                try {
                    return Float.valueOf(string);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_FLOAT;
            }
        },
        new TypeWrapper<Double>() {

            @Override
            public Class<Double> getType() {
                return Double.class;
            }

            @Override
            public Double wrap(String string) {
                try {
                    return Double.valueOf(string);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_FLOAT;
            }
        },
        new TypeWrapper<Boolean>() {
            
            @Override
            public Class<Boolean> getType() {
                return Boolean.class;
            }

            @Override
            public Boolean wrap(String string) {
                return Boolean.valueOf(string);
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_BOOLEAN;
            }
        },
        new TypeWrapper<Date>() {

            private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            @Override
            public Class<Date> getType() {
                return Date.class;
            }

            @Override
            public Date wrap(String string) {
                try {
                    return dateFormat.parse(string);
                } catch (ParseException e) {
                    return null;
                }
            }

            @Override
            public String unWrap(Date object) {
                return dateFormat.format(object);
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_DATE;
            }

        },
        new TypeWrapper<URL>() {

            @Override
            public Class<URL> getType() {
                return URL.class;
            }

            @Override
            public URL wrap(String string) {
                try {
                    return new URL(string);
                } catch (MalformedURLException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_URL;
            }

        },
        new TypeWrapper<Void>() {

            @Override
            public Class<Void> getType() {
                return Void.class;
            }

            @Override
            public Void wrap(String string) {
                return null;
            }

            @Override
            public String unWrap(Void object) {
                return "";
            }

            @Override
            public boolean enableEditor() {
                return false;
            }

            @Override
            public String toString() {
                return "Null";
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_NULL;
            }

        },
    };

    
    private final DataElement element;
    
    /**
     * Creates new form EditDataDialog
     *
     * @param parent
     * @param path
     * @param element
     */
    public EditDataDialog(java.awt.Frame parent, TreePath path, DataElement element) {
        super(parent, true);
        this.element = element;
        
        initComponents();

        Object value = element.getValue();
        
        typeSelector.setModel(new DefaultComboBoxModel(new Vector(Arrays.asList(TYPE_WRAPPERS))));

        typeSelector.setRenderer(new TypeWrapperRenderer());
        if (value != null) {
            for (TypeWrapper<Object> typeWrapper : TYPE_WRAPPERS) {
                if (typeWrapper.getType().equals(value.getClass())) {
                    valueField.setText(typeWrapper.unWrap(value));
                    typeSelector.setSelectedItem(typeWrapper);
                    break;
                }
            }
        } else {
            TypeWrapper<Object> typeWrapper = TYPE_WRAPPERS[TYPE_WRAPPERS.length - 1];
            valueField.setText(typeWrapper.unWrap(value));
            typeSelector.setSelectedItem(typeWrapper);
        }

        pathField.setText(Utils.pathToString(path));
        validateValue();

        valueField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateValue();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateValue();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateValue();
            }

        });

        setLocationRelativeTo(null);
    }

    private void validateValue() {
        final Object selectedItem = typeSelector.getSelectedItem();
        if (selectedItem instanceof TypeWrapper) {
            final TypeWrapper<Object> wrapper = (TypeWrapper<Object>) selectedItem;
            final String valueRaw = valueField.getText();

            Object object = wrapper.wrap(valueRaw);
            final boolean valid = object != null || !valueField.isEnabled();
            updateButton.setEnabled(valid);
            valueField.setBackground(valid ? javax.swing.UIManager.getDefaults().getColor("TextField.background") : new Color(255, 200, 200));
        }
    }

    private static class TypeWrapperRenderer extends JLabel implements ListCellRenderer {

        public TypeWrapperRenderer() {
            setOpaque(true);
        }

        /*
         * This method finds the image and text corresponding
         * to the selected value and returns the label, set up
         * to display the text and image.
         */
        @Override
        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            TypeWrapper<Object> wrapper = (TypeWrapper<Object>) value;

            setIcon(wrapper.getIcon());
            setText(wrapper.toString());
            return this;
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        valueField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        typeSelector = new javax.swing.JComboBox();
        updateButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        pathField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Value:");

        valueField.setText("jTextField1");

        jLabel2.setText("Type:");

        typeSelector.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        typeSelector.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                typeSelectorItemStateChanged(evt);
            }
        });

        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Path:");

        pathField.setEditable(false);
        pathField.setText("jTextField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(valueField)
                            .addComponent(typeSelector, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pathField)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 333, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(valueField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void typeSelectorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_typeSelectorItemStateChanged
        TypeWrapper<Object> wrapper = (TypeWrapper<Object>) typeSelector.getSelectedItem();
        valueField.setEnabled(wrapper.enableEditor());
        validateValue();
    }//GEN-LAST:event_typeSelectorItemStateChanged

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        TypeWrapper<Object> wrapper = (TypeWrapper<Object>) typeSelector.getSelectedItem();
        Object newValue = wrapper.wrap(valueField.getText());
        element.setValue(newValue);
        
        this.setVisible(false);
    }//GEN-LAST:event_updateButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField pathField;
    private javax.swing.JComboBox typeSelector;
    private javax.swing.JButton updateButton;
    private javax.swing.JTextField valueField;
    // End of variables declaration//GEN-END:variables
}
