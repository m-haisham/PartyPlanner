package cerberus.models.list;

import com.jfoenix.controls.JFXListCell;

public class TrailingListCell extends JFXListCell<TrailingListItem> {

    @Override
    protected void updateItem(TrailingListItem item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            setText(null);
            setGraphic(item.asBox());
        }
    }
}
