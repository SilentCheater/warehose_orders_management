package View;

import javax.swing.*;
import java.awt.*;

public class JTableView extends JFrame {
    JTable table;

    public JTableView(Object[][] data, String[] columnNames){
        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 50));
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(scrollPane);
        this.setVisible(true);
        this.pack();
    }
}