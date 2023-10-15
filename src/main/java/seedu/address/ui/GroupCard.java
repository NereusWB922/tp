package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.profbook.Group;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class GroupCard extends UiPart<Region> {
    public static final String DISPLAY_NAME = "<< %1$s >>";
    public static final String DISPLAY_ID = "ID: %1$s";
    private static final String FXML = "GroupCard.fxml";

    public final Group group;

    @FXML
    private HBox cardPane;
    @FXML
    private Label index;
    @FXML
    private Label name;
    @FXML
    private Label id;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public GroupCard(Group group, int displayedIndex) {
        super(FXML);
        this.group = group;
        index.setText(displayedIndex + ". ");
        id.setText(String.format(DISPLAY_ID, group.getId().toString().toUpperCase()));
        name.setText(String.format(DISPLAY_NAME, group.getName().fullName.toUpperCase()));
    }
}
