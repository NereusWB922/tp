package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the list of children items.
 */
public class ItemListPanel extends UiPart<Region> {
    private static final String FXML = "ItemListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ItemListPanel.class);

    @FXML
    private ListView<Displayable> itemListView;

    /**
     * Creates a {@code ListPanel} with the given {@code ObservableList} and FXML file name.
     */
    public ItemListPanel(ObservableList<Displayable> itemList) {
        super(FXML);
        logger.info(itemList.toString());
        itemListView.setItems(itemList);
        itemListView.setCellFactory(listView -> new ItemListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an item using its Displayable methods.
     */
    class ItemListViewCell extends ListCell<Displayable> {
        @Override
        protected void updateItem(Displayable item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(item.getDisplayCard(getIndex() + 1).getRoot());
            }
        }
    }
}
