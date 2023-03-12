package Presentation;

import BLL.ClientBLL;
import BLL.ProductBLL;
import Model.Client;
import Model.Product;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class DeleteController<T> {
    DeleteView deleteView;
    ProductBLL productBLL = new ProductBLL();
    ClientBLL clientBLL = new ClientBLL();
    public DeleteController(Class<T> type){
        List<T> objects;
        if(type.isAssignableFrom(Product.class)) {
            objects = (List<T>) productBLL.findAll();
        }
        else {
            objects = (List<T>) clientBLL.findAll();
        }
        String[] array = new String[objects.size()];
        for(T object : objects) array[objects.indexOf(object)] = object.toString();

        deleteView = new DeleteView(array);
        deleteView.getSubmit().addActionListener(
                e -> {
                    T t = objects.get(deleteView.getComboBox().getSelectedIndex());

                    try {
                        if(type.isAssignableFrom(Product.class)) {
                            productBLL.delete((Product) t);
                        }
                        else {
                            clientBLL.delete((Client) t);
                        }
                        objects.remove(t);
                        deleteView.getComboBox().removeAllItems();
                        for (T object : objects) {
                            deleteView.getComboBox().addItem(object.toString());
                        }
                    }catch (SQLException exception){
                        JOptionPane.showMessageDialog(null, type.getName() + "DAO: " + "delete "  + exception.getMessage(), "SQL", JOptionPane.ERROR_MESSAGE);

                    }
                }
        );
    }

}
