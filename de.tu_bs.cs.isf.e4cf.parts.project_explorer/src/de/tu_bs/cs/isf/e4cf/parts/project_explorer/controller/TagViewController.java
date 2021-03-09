package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.tagging.Tag;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Controller for the TagView FXML
 */
public class TagViewController implements Initializable {

    @FXML
    public ListView<Tag> listView;

    @FXML
    public Text errorText;

    @FXML
    public HBox tag;

    @FXML
    public Button addBtn;

    @FXML
    public Button deleteBtn;

    @FXML
    public Button selectAllBtn;

    @FXML
    public Button deselectAllBtn;

    @FXML
    public TextField tagTextField;

    @FXML
    public ColorPicker colorPicker;

    @FXML
    public Button cancelBtn;

    private IEclipseContext context;

    private Tag tagToUpdate;

    private List<Tag> sessionTags;
    private List<Tag> currentlySelectedTags;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	cancelBtn.setVisible(false);
	deleteBtn.setDisable(true);
	colorPicker.setValue(Color.WHITE);
	errorText.setVisible(false);

	listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	listView.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
	    int amountSelected = listView.getSelectionModel().getSelectedItems().size();
	    if (amountSelected > 0) {
		deleteBtn.setDisable(false);
	    } else {
		deleteBtn.setDisable(true);
	    }
	});
    }

    /**
     * Setup button functionality inside of TagView.
     */
    public void initViewControlls(List<Tag> sessionTags, List<Tag> currentlySelectedTags, IEclipseContext context) {
	this.context = context;
	this.sessionTags = sessionTags;
	this.currentlySelectedTags = currentlySelectedTags;

	// setup addBtn functionality
	// only enable add button if user typed an actual name
	tagTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    if (errorText.isVisible()) {
		errorText.setVisible(false);
	    }
	});

	initListView();
    }

    /**
     * Setup custom listview with custom cells described by fxml files.
     */
    private void initListView() {
	listView.getItems().addAll(sessionTags);

	listView.setCellFactory(listView -> new ListCell<Tag>() {

	    @Override
	    protected void updateItem(Tag item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
		    setGraphic(null);
		} else {
		    FXMLLoader<TagListCellController> loader = new FXMLLoader<TagListCellController>(context,
			    StringTable.BUNDLE_NAME, FileTable.TAG_LIST_CELL);
		    TagListCellController controller = loader.getController();

		    controller.tagName.setText(item.getName());
		    controller.root.getChildren().add(item.getTagIcon());
		    controller.selectChechbox.setSelected(currentlySelectedTags.contains(item));
		    controller.selectChechbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
			    currentlySelectedTags.add(item);
			} else {
			    currentlySelectedTags.remove(item);
			}
		    });
		    setGraphic(loader.getNode());
		}

		// update when user double clicks the specific tag.
		setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
			if (!empty && click.getClickCount() == 2) {
			    tagTextField.setText(item.getName());
			    colorPicker.setValue(item.getColor());
			    tagToUpdate = item;
			    showTagUpdateUI(true);
			}
		    }
		});
	    }
	});
    }

    /**
     * Adds a new tag / updates an existing tag.
     */
    @FXML
    public void addTag() {
	// check if user wants to update or add a tag
	if (addBtn.getText().equals(StringTable.TAG_ADD_BUTTON_TEXT)) {
	    // check if name is valid
	    String name = tagTextField.getText();
	    if (name.trim().equals("") || name.contains(":")) {
		// no valid name, so display error
		errorText.setText(StringTable.TAG_NO_VALID_NAME_TEXT);
		errorText.setVisible(true);
	    } else if (sessionTags.contains((new Tag(name.trim(), Color.WHITE)))) {
		// tag with same name already included
		errorText.setText(String.format(StringTable.TAG_NAME_ALREADY_EXISTS_TEXT, name));
		errorText.setVisible(true);
	    } else {
		Tag newTag = new Tag(tagTextField.getText(), colorPicker.getValue());
		sessionTags.add(newTag);
		errorText.setVisible(false);
		resetTagUI();
	    }
	} else {
	    sessionTags.get(sessionTags.indexOf(tagToUpdate)).setColor(colorPicker.getValue());
	    resetTagUI();
	}
    }

    /**
     * Deletes all tags that are selected in the listview
     */
    @FXML
    public void deleteTag() {
	for (Tag tag : listView.getSelectionModel().getSelectedItems()) {
	    sessionTags.remove(tag);
	    currentlySelectedTags.remove(tag);
	}
	updateList();
    }

    /**
     * Cancels an update operation and updates the view
     */
    @FXML
    public void cancel() {
	tagTextField.setText("");
	colorPicker.setValue(Color.WHITE);
	showTagUpdateUI(false);
    }

    /**
     * Selects all available tags
     */
    @FXML
    public void selectAll() {
	currentlySelectedTags.clear();
	currentlySelectedTags.addAll(listView.getItems());
	updateList();
    }

    /**
     * Deselects all available tags
     */
    @FXML
    public void deselectAll() {
	currentlySelectedTags.clear();
	updateList();
    }

    /**
     * Update the ui after a successful add / update operation
     */
    private void resetTagUI() {
	showTagUpdateUI(false);
	tagTextField.setText("");
	colorPicker.setValue(Color.WHITE);
	updateList();
    }

    /**
     * Toggle the UI elements to represent either the add or update tag
     * functionality.
     * 
     * @param update true if the UI should represent update, else false.
     */
    private void showTagUpdateUI(boolean update) {
	addBtn.setText(update ? StringTable.TAG_UPDATE_BUTTON_TEXT : StringTable.TAG_ADD_BUTTON_TEXT);
	// user is not supposed to update the name of the tag.
	tagTextField.setDisable(update);
	cancelBtn.setVisible(update);
    }

    // update the list with the new tags in it.
    private void updateList() {
	listView.getItems().clear();
	listView.getItems().addAll(sessionTags);
    }
}
