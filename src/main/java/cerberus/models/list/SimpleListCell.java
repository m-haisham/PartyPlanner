package cerberus.models.list;

import com.jfoenix.controls.JFXListCell;

public class SimpleListCell extends JFXListCell<SimpleListItem> {

    @Override
    protected void updateItem(SimpleListItem item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            setText(null);
            setGraphic(item.asNode());
        }
    }
}
