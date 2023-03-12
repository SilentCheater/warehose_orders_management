package Presentation;

import BLL.ClientBLL;
import BLL.ProductBLL;
import Model.Product;

import java.lang.reflect.Field;
import java.util.List;

public class JTableController<T> {
    JTableView jTableView;
    ProductBLL productBLL = new ProductBLL();
    ClientBLL clientBLL = new ClientBLL();
    public JTableController(Class<T> type){
        List<T> list;
        if(type.isAssignableFrom(Product.class)) {
            list = (List<T>) productBLL.findAll();
        }
        else {
            list = (List<T>) clientBLL.findAll();
        }
        Object[][] objects = new Object[list.size()][type.getDeclaredFields().length];

        for(int i = 0; i<list.size();i++){
            for(int j = 0; j<type.getDeclaredFields().length;j++){
                Field field = type.getDeclaredFields()[j];
                field.setAccessible(true);
                try {
                    objects[i][j]=field.get(list.get(i));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        String[] colNames = new String[type.getDeclaredFields().length];
        for(int i=0;i<type.getDeclaredFields().length;i++){
            colNames[i]=type.getDeclaredFields()[i].getName();
        }

        jTableView = new JTableView(objects, colNames);
    }
}
