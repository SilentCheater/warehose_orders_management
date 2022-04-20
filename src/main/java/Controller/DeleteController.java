package Controller;

import DB.AbstractDAO;
import View.DeleteView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class DeleteController<T> {
    DeleteView deleteView;
    AbstractDAO<T> abstractDAO;
    public DeleteController(Class<T> type){
        abstractDAO = new AbstractDAO<>(type);
        List<T> objects = abstractDAO.findAll();
        String[] array = new String[objects.size()];
        for(T object : objects) array[objects.indexOf(object)] = object.toString();

        deleteView = new DeleteView(array);
        deleteView.getSubmit().addActionListener(
                e -> {
                    T t = objects.get(deleteView.getComboBox().getSelectedIndex());

                    try {
                        abstractDAO.delete(t);
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
