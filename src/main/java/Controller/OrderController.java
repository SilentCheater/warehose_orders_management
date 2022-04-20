package Controller;

import DB.AbstractDAO;
import Model.Client;
import Model.Order;
import Model.Product;
import View.OrderView;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class OrderController {
    OrderView orderView;
    AbstractDAO<Client> clientAbstractDAO = new AbstractDAO<>(Client.class);
    AbstractDAO<Product> productAbstractDAO = new AbstractDAO<>(Product.class);
    public OrderController(){

        List<Product> products = productAbstractDAO.findAll();
        String[] productsArray = new String[products.size()];
        for(Product object : products){
            productsArray[products.indexOf(object)] = object.toString();
        }

        List<Client> clients = clientAbstractDAO.findAll();
        String[] clientsArray = new String[clients.size()];
        for(Client object : clients){
            clientsArray[clients.indexOf(object)] = object.toString();
        }

        orderView = new OrderView(clientsArray, productsArray);
        orderView.getSubmit().addActionListener(
                e -> {
                    Client client = clients.get(orderView.getClients().getSelectedIndex());
                    Product product = products.get(orderView.getProducts().getSelectedIndex());
                    try{
                        Order order = new Order(client.getId(), product.getId(), Integer.parseInt(orderView.getQuantity().getText()));
                        AbstractDAO<Order> orderAbstractDAO = new AbstractDAO<>(Order.class);
                        if(order.getQuantity() != 0 && order.getQuantity()<=product.getQuantity()){
                            try {
                                orderAbstractDAO.insert(order);
                                product.setQuantity(product.getQuantity() - order.getQuantity());
                                productAbstractDAO.update(product);
                                JOptionPane.showMessageDialog(null, "order placed successfully", "ordered", JOptionPane.INFORMATION_MESSAGE);
                                createPDF(client, product, order);
                                orderView.getProducts().removeAllItems();
                                for(Product product1 : products){
                                    orderView.getProducts().addItem(product1.toString());
                                }
                            }catch(Exception exception){
                                exception.printStackTrace();
                            }
                        }
                        else JOptionPane.showMessageDialog(null, "Can't order so many " + product.getName() + "\nMax quantity = " + product.getQuantity(), "quantity error", JOptionPane.ERROR_MESSAGE);
                    }catch(NumberFormatException exception){
                        JOptionPane.showMessageDialog(null, "Quantity must be a number", "nan", JOptionPane.ERROR_MESSAGE);
                    }
                }
        );
    }

    private void createPDF(Client client, Product product, Order order){
        Document doc = new Document();
        try
        {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(System.getProperty("user.dir")+"\\src\\orders\\" + "order"+ ThreadLocalRandom.current().nextInt(1, 100000)+".pdf"));
            doc.open();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            doc.add(new Paragraph("Client " + client.getName() + " ordered " + order.getQuantity() + " units of " + product.getName() + " at time: " + dtf.format(now)
            ));
            doc.close();
            writer.close();
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
