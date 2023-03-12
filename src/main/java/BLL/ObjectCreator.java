package BLL;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class ObjectCreator<T> {
    /**
     * @param type is the type of the object that should be returned
     * @param attributes is the list of values to initialise the object with
     * @return an object of type "type"
     */
    public T createObject(Class<T> type, List<Object> attributes){
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
