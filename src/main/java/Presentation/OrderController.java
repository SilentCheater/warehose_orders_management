package Presentation;

import BLL.ClientBLL;
import BLL.Exceptions.NegativeNumber;
import BLL.OrderBLL;
import BLL.ProductBLL;
import DB.AbstractDAO;
import Model.Client;
import Model.Order;
import Model.Product;
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
    ClientBLL clientBLL = new ClientBLL();
    ProductBLL productBLL = new ProductBLL();
    public OrderController(){

        List<Product> products = productBLL.findAll();
        String[] productsArray = new String[products.size()];
        for(Product object : products){
            productsArray[products.indexOf(object)] = object.toString();
        }

        List<Client> clients = clientBLL.findAll();
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
                        OrderBLL orderBLL = new OrderBLL(client.getId(), product.getId(), orderView.getQuantity().getText());
                        Order order = new Order(client.getId(), product.getId(), Integer.parseInt(orderView.getQuantity().getText()));
                        try {
                            if(orderBLL.insert(order, product)) {
                                product.setQuantity(product.getQuantity() - order.getQuantity());
                                productBLL.update(product);
                                JOptionPane.showMessageDialog(null, "order placed successfully", "ordered", JOptionPane.INFORMATION_MESSAGE);
                                createPDF(client, product, order);
                                orderView.getProducts().removeAllItems();
                                for (Product product1 : products) {
                                    orderView.getProducts().addItem(product1.toString());
                                }
                            }
                        }catch(Exception exception){
                            exception.printStackTrace();
                        }

                    }catch(NumberFormatException | NegativeNumber exception){
                        JOptionPane.showMessageDialog(null, "Quantity must be a positive number", "nan", JOptionPane.ERROR_MESSAGE);
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
