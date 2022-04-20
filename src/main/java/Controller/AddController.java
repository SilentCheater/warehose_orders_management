package Controller;

import DB.AbstractDAO;
import Model.Product;
import View.AddView;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class AddController<T> {
    AddView addView;
    AbstractDAO<T> abstractDAO;
    public AddController(Class<T> type){
        addView = new AddView(type.getSimpleName());
        abstractDAO = new AbstractDAO<>(type);
        addView.getSubmit().addActionListener(
                e ->{
                    try{Integer.parseInt(addView.getSecondIn().getText());
                        if(type.isAssignableFrom(Product.class))
                            Integer.parseInt(addView.getThirdIn().getText());
                        List<Object> attributes = new ArrayList<>();
                        attributes.add(ThreadLocalRandom.current().nextInt(1, 5000000));
                        attributes.add(addView.getFirstIn().getText());
                        attributes.add(Integer.parseInt(addView.getSecondIn().getText()));
                        if(type.isAssignableFrom(Product.class))
                            attributes.add(Integer.parseInt(addView.getThirdIn().getText()));
                        else
                            attributes.add(addView.getThirdIn().getText());
                        abstractDAO.insert(createObject(type, attributes));
                        JOptionPane.showMessageDialog(null, "Successful insert", "good job", JOptionPane.INFORMATION_MESSAGE);
                        addView.dispose();
                    }catch(NumberFormatException exception){
                        JOptionPane.showMessageDialog(null, "You entered a non-number in a number filed", "nan", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException sqlException) {
                        JOptionPane.showMessageDialog(null, type.getName() + "DAO: " + "insert "  + sqlException.getMessage(), "SQL", JOptionPane.ERROR_MESSAGE);
                    }
                }
        );
    }

    /**
     * @param type is the type of the object that should be returned
     * @param attributes is the list of values to initialise the object with
     * @return an object of type "type"
     */
    private T createObject(Class<T> type, List<Object> attributes){
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (Constructor constructor : ctors) {
            ctor = constructor;
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            Objects.requireNonNull(ctor).setAccessible(true);
            T instance = (T) ctor.newInstance();
            for (int i = 0; i<type.getDeclaredFields().length; i++){
                Field field = type.getDeclaredFields()[i];
                String fieldName = field.getName();
                Object value = attributes.get(i);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                Method method = propertyDescriptor.getWriteMethod();
                method.invoke(instance, value);
            }
            return instance;

        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
