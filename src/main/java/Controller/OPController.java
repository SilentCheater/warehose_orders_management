package Controller;

import View.OPView;

public class OPController<T> {
    OPView opView;

    public OPController(Class<T> type){
        opView = new OPView(type);
        opView.getAdd().addActionListener(
                e -> new AddController<>(type)
        );
        opView.getDelete().addActionListener(
                e -> new DeleteController<>(type)
        );
        opView.getEdit().addActionListener(
                e -> new EditController<>(type)
        );
        opView.getViewAll().addActionListener(
                e -> new JTableController<>(type)
        );
    }
}
