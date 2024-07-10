/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysiexperience;

import javax.swing.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.List;

class ListTransferHandler extends TransferHandler {
    private SchedulePWSL schedulePWSL;

    public ListTransferHandler(SchedulePWSL schedulePWSL) {
        this.schedulePWSL = schedulePWSL;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JList<?> list = (JList<?>) c;
        List<?> values = list.getSelectedValuesList();
        return new ListItemTransferable(values);
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        if (!support.isDataFlavorSupported(ListItemTransferable.listFlavor)) {
            return false;
        }
        JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
        return dl.getIndex() >= 0;
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        Transferable transferable = support.getTransferable();
        List<?> data;
        try {
            data = (List<?>) transferable.getTransferData(ListItemTransferable.listFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            return false;
        }

        JList<?> target = (JList<?>) support.getComponent();
        DefaultListModel<Object> listModel = (DefaultListModel<Object>) target.getModel();
        JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
        int index = dl.getIndex();

        for (Object item : data) {
            listModel.add(index++, item);
        }

        // Set the isChanged flag
        schedulePWSL.setIsChanged(true);

        return true;
    }
}