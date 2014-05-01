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

import com.moebiusgames.xdata.DataKey;
import com.moebiusgames.xdata.DataNode;
import com.moebiusgames.xdata.explorer.TypeWrapperModel.TypeWrapper;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.renderer.DefaultTreeRenderer;
import org.jdesktop.swingx.treetable.TreeTableModel;

/**
 *
 * @author Florian Frankenberger
 */
public class SearchDialog extends javax.swing.JDialog {

    private DataNode searchNode;
    private JXTreeTable tree;

    private final JXTreeTable mainTree;
    private final DataNode mainNode;

    /**
     * Creates new form SearchDialog
     */
    public SearchDialog(java.awt.Frame parent, JXTreeTable mainTree, DataNode mainNode) {
        super(parent, false);
        initComponents();

        this.mainTree = mainTree;
        this.mainNode = mainNode;

        typeSelector.setModel(new TypeWrapperModel());
        typeSelector.setRenderer(new TypeWrapperRenderer());

        clearTree();

        final DocumentListener listener = new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateAll();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateAll();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateAll();
            }

        };

        valueField.getDocument().addDocumentListener(listener);
        keyField.getDocument().addDocumentListener(listener);


        validateAll();
        deleteButton.setVisible(false);

        setLocationRelativeTo(null);
    }

    private void clearTree() {
        searchNode = new DataNode();
        tree = newTree(new DataNodeTreeTableModel(searchNode));
        tree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                selectionChanged(e.getPath());
            }
        });

        searchButton.setEnabled(false);
        clearSearch();
    }

    private JXTreeTable newTree(TreeTableModel model) {
        final JXTreeTable tableTree = new JXTreeTable(model);
        tableTree.setShowsRootHandles(true);
        tableTree.packColumn(tableTree.getHierarchicalColumn(), -1);
        tableTree.putClientProperty("JTree.lineStyle", "Horizontal");
        scrollPane.setViewportView(tableTree);

        tableTree.setRootVisible(true);
        tableTree.setTreeCellRenderer(new DefaultTreeRenderer(new DataNodeIconValue(), new DataNodeStringValue()));

        tableTree.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        return tableTree;
    }

    private void selectionChanged(TreePath path) {
        DataElement selectedObject = ((DataElement) path.getLastPathComponent());

        if (selectedObject.getValue() instanceof DataNode) {
            keyField.setText("");
            valueField.setText("");
            typeSelector.setSelectedItem(TypeWrapperModel.TYPE_WRAPPERS[0]);
            keyField.setEditable(true);
            keyField.requestFocus();

            deleteButton.setVisible(false);
        } else {
            keyField.setText(selectedObject.getKey());
            Object value = selectedObject.getValue();
            if (value != null) {
                for (TypeWrapper<Object> typeWrapper : TypeWrapperModel.TYPE_WRAPPERS) {
                    if (typeWrapper.getType().equals(value.getClass())) {
                        valueField.setText(typeWrapper.unWrap(value));
                        typeSelector.setSelectedItem(typeWrapper);
                        break;
                    }
                }
            } else {
                TypeWrapper<Object> typeWrapper = TypeWrapperModel.TYPE_WRAPPERS[TypeWrapperModel.TYPE_WRAPPERS.length - 1];
                valueField.setText(typeWrapper.unWrap(value));
                typeSelector.setSelectedItem(typeWrapper);
            }
            keyField.setEditable(false);
            valueField.selectAll();
            valueField.requestFocus();

            deleteButton.setVisible(true);
        }

        validateAll();
    }

    private void validateAll() {
        final boolean validPath = tree.getSelectedRow() > -1;

        keyField.setEnabled(validPath);
        valueField.setEnabled(validPath);
        typeSelector.setEnabled(validPath);

        final Object selectedItem = typeSelector.getSelectedItem();
        if (validPath) {
            final TypeWrapper<Object> wrapper = (TypeWrapper<Object>) selectedItem;
            final String valueRaw = valueField.getText();

            Object object = wrapper.wrap(valueRaw);
            final boolean validValue = object != null || !valueField.isEnabled();
            final boolean validKey = !keyField.getText().isEmpty();
            final boolean isDataNode = validPath && ((DataElement) tree.getPathForRow(tree.getSelectedRow()).getLastPathComponent()).getValue() instanceof DataNode;

            addButton.setEnabled(validValue && validKey && validPath);
            addButton.setText(isDataNode ? "Add" : "Update");
            valueField.setBackground(validValue ? javax.swing.UIManager.getDefaults().getColor("TextField.background") : new Color(255, 200, 200));
            keyField.setBackground(validKey ? javax.swing.UIManager.getDefaults().getColor("TextField.background") : new Color(255, 200, 200));
        } else {
            valueField.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField.background"));
            keyField.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField.background"));

            addButton.setText("Add");
            addButton.setEnabled(false);
            deleteButton.setVisible(false);

            keyField.setText("");
            valueField.setText("");
        }

        searchButton.setEnabled(searchNode.getSize() > 0);
    }

    private void clearSearch() {
        nodesToSearch.isEmpty();
        searchButton.setText("Search");

        validateAll();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        valueField = new javax.swing.JTextField();
        typeSelector = new javax.swing.JComboBox();
        addButton = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        keyField = new javax.swing.JTextField();
        deleteButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search");

        jLabel2.setText("Type:");

        jLabel1.setText("Value:");

        valueField.setEnabled(false);

        typeSelector.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        typeSelector.setEnabled(false);
        typeSelector.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                typeSelectorItemStateChanged(evt);
            }
        });

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Key:");

        keyField.setEnabled(false);

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyField))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(valueField, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                            .addComponent(typeSelector, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(clearButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(closeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(searchButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(keyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(valueField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(deleteButton)
                    .addComponent(clearButton))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchButton)
                    .addComponent(closeButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void typeSelectorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_typeSelectorItemStateChanged
        TypeWrapper<Object> wrapper = (TypeWrapper<Object>) typeSelector.getSelectedItem();
        valueField.setEnabled(wrapper.enableEditor());
        validateAll();
    }//GEN-LAST:event_typeSelectorItemStateChanged

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        final int selectedRow = tree.getSelectedRow();
        final TreePath path = tree.getPathForRow(selectedRow);
        final DataElement selected = (DataElement) path.getLastPathComponent();
        if (selected.getValue() instanceof DataNode) {
            DataNode dataNode = (DataNode) selected.getValue();
            TypeWrapper typeWrapper = (TypeWrapper<?>) typeSelector.getSelectedItem();
            dataNode.setObject(DataKey.create(keyField.getText(), typeWrapper.getType()), typeWrapper.wrap(valueField.getText()));

            final DataNodeTreeTableModel model = (DataNodeTreeTableModel) tree.getTreeTableModel();
            model.notifyOnChildrenChanged(selected);
        } else {
            TypeWrapper typeWrapper = (TypeWrapper<?>) typeSelector.getSelectedItem();

            selected.setValue(typeWrapper.wrap(valueField.getText()));
            tree.repaint();

            selectionChanged(path);
        }

        clearSearch();
    }//GEN-LAST:event_addButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        final int selectedRow = tree.getSelectedRow();
        final TreePath path = tree.getPathForRow(selectedRow);
        final DataElement selected = (DataElement) path.getLastPathComponent();

        DataNode containingNode = (DataNode) selected.getParent().getValue();
        containingNode.clearObject(DataKey.create(selected.getKey(), Object.class));

        final DataNodeTreeTableModel model = (DataNodeTreeTableModel) tree.getTreeTableModel();
        model.notifyOnChildrenChanged(selected.getParent());

        validateAll();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clearTree();
        validateAll();
    }//GEN-LAST:event_clearButtonActionPerformed

    private static class TreeElement {
        private final TreeElement parent;
        private final String key;
        private final Object value;

        public TreeElement(TreeElement parent, String key, Object value) {
            this.parent = parent;
            this.key = key;
            this.value = value;
        }
    }
    private final List<TreeElement> nodesToSearch = new ArrayList<TreeElement>();

    private TreePath buildPath(TreeElement element) {
        //1. create all parents
        List<TreeElement> parents = new ArrayList<TreeElement>();
        TreeElement currentElement = element;
        while (currentElement != null) {
            parents.add(0, currentElement);
            currentElement = currentElement.parent;
        }

        final DataNodeTreeTableModel model = (DataNodeTreeTableModel) mainTree.getTreeTableModel();
        List<DataElement> parentsDataElements = new ArrayList<DataElement>();
        DataElement parentBeforeDataElement = null;
        for (TreeElement parent : parents) {
            DataElement parentDataElement = new DataElement(model, parentBeforeDataElement, parent.key, parent.value);
            parentsDataElements.add(parentDataElement);
            parentBeforeDataElement = parentDataElement;
        }

        return new TreePath(parentsDataElements.toArray());
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        if (nodesToSearch.isEmpty()) {
            nodesToSearch.clear();
            nodesToSearch.add(new TreeElement(null, "root", mainNode));

            searchButton.setText("Search next");
        }

        while (!nodesToSearch.isEmpty()) {
            TreeElement currentElement = nodesToSearch.remove(0);
            DataNode currentNode = (DataNode) currentElement.value;

            //now we check if the given node corresponds to the node we search for
            boolean matches = true;
            for (String key : searchNode.getRawKeys()) {
                if (!currentNode.containsKey(DataKey.create(key, Object.class))) {
                    matches = false;
                    break;
                }
                Object value = searchNode.getRawObject(key);
                if (!currentNode.getRawObject(key).equals(value)) {
                    matches = false;
                    break;
                }
            }

            if (matches) {
                TreePath treePath = buildPath(currentElement);

                mainTree.expandPath(treePath);
                mainTree.scrollPathToVisible(treePath);
                int row = mainTree.getRowForPath(treePath);
                mainTree.getSelectionModel().setSelectionInterval(row, row);

                return;
            }

            for (String key : currentNode.getRawKeys()) {
                final Object value = currentNode.getRawObject(key);
                if (value instanceof DataNode) {
                    nodesToSearch.add(new TreeElement(currentElement, key, value));
                } else
                    if (value instanceof List) {
                        final TreeElement listTreeElement = new TreeElement(currentElement, key, value);

                        int counter = 0;
                        for (Object o : (List<?>) value) {
                            if (o instanceof DataNode) {
                                nodesToSearch.add(new TreeElement(listTreeElement, String.valueOf(counter), o));
                            }
                            counter++;
                        }
                    }
            }
        }

        JOptionPane.showMessageDialog(this, "No (more) match(es) found", "No match", JOptionPane.INFORMATION_MESSAGE);

        searchButton.setText("Search");

    }//GEN-LAST:event_searchButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField keyField;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton searchButton;
    private javax.swing.JComboBox typeSelector;
    private javax.swing.JTextField valueField;
    // End of variables declaration//GEN-END:variables
}
