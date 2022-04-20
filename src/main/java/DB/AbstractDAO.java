package DB;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A class that implements methods which allow CRUD operations on a given database
 * It uses generics so the methods are available on any class
 * A new instance of this class should be created for different classes
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    public AbstractDAO(Class<T> type) {
        this.type = type;
    }

    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM ";
        query+=type.getSimpleName();
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * @param resultSet contains all the values for each object to be initialised
     * @return a list of objects of type T
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (Constructor constructor : ctors) {
            ctor = constructor;
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                Objects.requireNonNull(ctor).setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param t object of type T used to get the actual name of the class
     *          and the values of each filed - in getProperties method
     * @return an SQL string that is used to insert a new row in the database
     */
    private String insertQuery(T t){
        StringBuilder query = new StringBuilder("INSERT INTO schooldb." + t.getClass().getSimpleName().toLowerCase(Locale.ROOT) + " (");
        for(Field field : t.getClass().getDeclaredFields()){
            String fieldName = field.getName();
            query.append(fieldName);
            if(!field.equals(t.getClass().getDeclaredFields()[t.getClass().getDeclaredFields().length-1]))
                query.append(", ");
            else
                query.append(")");
        }
        query.append(" VALUES (");

        query.append(getProperties(t));
        return query.toString();
    }

    /**
     * @param t the object that contains the values
     * @return a formatted string that contains the values of the object and is used in SQL queries
     */
    private String getProperties(T t) {
        StringBuilder query = new StringBuilder();
        for(Field field : t.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                if(String.class.isAssignableFrom(field.getType()))
                    query.append("\"");
                query.append(field.get(t).toString());
                if(String.class.isAssignableFrom(field.getType()))
                    query.append("\"");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(!field.equals(t.getClass().getDeclaredFields()[(t.getClass().getDeclaredFields().length)-1]))
                query.append(", ");
            else
                query.append(")");
        }
        return query.toString();
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfStringBuilder")
    public void insert(T t) throws SQLException{
        String SQL = insertQuery(t);
        connectAndExecute(SQL);
    }

    private String updateQuery(T t){
        StringBuilder query = new StringBuilder("UPDATE " + t.getClass().getSimpleName() + " SET ");
        String id = null;
        for(Field field : t.getClass().getDeclaredFields()){
            field.setAccessible(true);
            String fieldName = field.getName();
            try {

                query.append(fieldName).append(" = ");
                if(String.class.isAssignableFrom(field.getType()))
                    query.append("\"");
                query.append(field.get(t).toString());
                if(String.class.isAssignableFrom(field.getType()))
                    query.append("\"");

                if(fieldName.equals("id"))
                    id=field.get(t).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(!field.equals(t.getClass().getDeclaredFields()[t.getClass().getDeclaredFields().length-1]))
                query.append(", ");
            else {
                    query.append(" WHERE id = ").append(id);
            }
        }

        return query.toString();
    }

    public void update(T t) throws SQLException{
        String SQL = updateQuery(t);
        connectAndExecute(SQL);
    }

    /**
     * @param SQL an SQL query that will be executed
     * @throws SQLException - should be handled locally where this method is used
     */
    private void connectAndExecute(String SQL) throws SQLException{
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.executeUpdate();
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
    }

    private String deleteQuery(T t){
        StringBuilder query = new StringBuilder("DELETE FROM "+ t.getClass().getSimpleName()+ " WHERE id = ");
        try {
            Field field = t.getClass().getDeclaredFields()[0];
            field.setAccessible(true);
            query.append(field.get(t).toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return query.toString();
    }

    public void delete(T t) throws SQLException{
        String SQL = deleteQuery(t);
        connectAndExecute(SQL);
    }


}
