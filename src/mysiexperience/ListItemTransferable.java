/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysiexperience;


import javax.swing.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.List;
/**
 *
 * @author z004kptc
 */
class ListItemTransferable implements Transferable {
    static DataFlavor listFlavor = new DataFlavor(List.class, "List of items");
    private List<?> data;

    public ListItemTransferable(List<?> data) {
        this.data = data;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{listFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return listFlavor.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return data;
    }
}

