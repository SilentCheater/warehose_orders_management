package Presentation;

import javax.swing.*;
import java.awt.*;

public class OPView extends JFrame{
    JButton add;
    JButton edit;
    JButton delete;
    JButton viewAll;

    public OPView(Class type) {
        add = new JButton("New "+ type.getSimpleName());
        edit = new JButton("Edit "+ type.getSimpleName());
        delete = new JButton("Delete "+ type.getSimpleName());
        viewAll = new JButton("View all "+ type.getSimpleName()+"s");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.add(add);
        this.add(edit);
        this.add(delete);
        this.add(viewAll);
        this.setVisible(true);
        this.pack();
    }

    public JButton getAdd() {
        return add;
    }

    public JButton getDelete() {
        return delete;
    }

    public JButton getEdit() {
        return edit;
    }

    public JButton getViewAll() {
        return viewAll;
    }
}
