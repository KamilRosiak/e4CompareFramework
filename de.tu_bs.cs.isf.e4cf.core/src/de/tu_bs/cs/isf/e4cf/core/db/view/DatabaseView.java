package de.tu_bs.cs.isf.e4cf.core.db.view;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import de.tu_bs.cs.isf.e4cf.core.db.*;
import de.tu_bs.cs.isf.e4cf.core.db.model.Column;

/**
 * 
 * Class responsible for viewing the databases as GUI.
 *
 */
public final class DatabaseView extends Application {

	public static final String _DATABASEPATH = "./testDatabases/";

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage stage) {

		// draw the window
		stage.setTitle("DB View");
		stage.setWidth(450);
		stage.setHeight(200);

		final Button openButton = new Button("Open Database");
		final Label labelDb = new Label("Choose a database: ");
		ChoiceBox<String> dbListChoiceBox = new ChoiceBox<String>();

		// get the list of databases in path testDatabases
		final File file = new File(_DATABASEPATH);
		final ObservableList<String> dataFiles = FXCollections.observableArrayList(getFiles(file));

		// fill the choicebox with the given db list
		dbListChoiceBox.setItems(dataFiles);

		// when the button is clicked, use the method to show the tables of the database
		openButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				String selectedDb = dbListChoiceBox.getSelectionModel().getSelectedItem().toString();
				File dbFile = new File(selectedDb);
				selectTable(dbFile, stage);
			}
		});

		// use a grid pane to show the elements of the window
		final GridPane inputGridPane = new GridPane();
		GridPane.setConstraints(labelDb, 0, 0);
		GridPane.setConstraints(openButton, 1, 1);
		GridPane.setConstraints(dbListChoiceBox, 1, 0);
		inputGridPane.setHgap(10);
		inputGridPane.setVgap(20);
		inputGridPane.setPadding(new Insets(12, 12, 12, 12));
		inputGridPane.getChildren().addAll(labelDb, dbListChoiceBox, openButton);

		stage.setScene(new Scene(inputGridPane));
		stage.show();
	}

	/**
	 * Method to get all the databases in the given path as a list
	 * 
	 * @param folder
	 * @return list with names of all databases
	 */
	protected List<String> getFiles(final File folder) {
		List<String> files = new ArrayList<String>();

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				getFiles(fileEntry);
			} else {
				files.add(fileEntry.getName());
			}
		}
		return files;
	}

	/**
	 * Method to show the window where the table can be selected
	 * 
	 * @param file
	 * @param stage
	 */
	protected void selectTable(File file, Stage stage) {
		List<String> tableList = new ArrayList<String>();
		final Button openTable = new Button("Open Table");
		final Label labelTable = new Label("Choose a table: ");
		ChoiceBox<String> tableListChoiceBox = new ChoiceBox<String>();
		final Button backButton = new Button("Go Back");

		try {
			TableServiceImp tI = new TableServiceImp();

			// get the tables in the chosen database
			tableList = tI.getTables(_DATABASEPATH, file.getName());

			// fill choicebox with tables of the chosen database
			final ObservableList<String> tableNames = FXCollections.observableArrayList(tableList);
			tableListChoiceBox.setItems(tableNames);

			// when the open button is clicked, the method to show the table with its data is used
			openTable.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					String selectedTable = tableListChoiceBox.getSelectionModel().getSelectedItem().toString();
					showTable(file, selectedTable);
				}
			});

			// when the back button is used, we go back to the window where we can select a database
			backButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					start(stage);
				}
			});

			// use a grid pane to show the elements of the window
			final GridPane inputGridPane = new GridPane();
			GridPane.setConstraints(labelTable, 0, 0);
			GridPane.setConstraints(openTable, 1, 1);
			GridPane.setConstraints(tableListChoiceBox, 1, 0);
			GridPane.setConstraints(backButton, 2, 1);
			inputGridPane.setHgap(10);
			inputGridPane.setVgap(20);
			inputGridPane.setPadding(new Insets(12, 12, 12, 12));
			inputGridPane.getChildren().addAll(labelTable, tableListChoiceBox, openTable, backButton);

			stage.setScene(new Scene(inputGridPane));
			stage.show();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Method to show the data within a table
	 * 
	 * @param file
	 * @param tableName
	 */
	protected void showTable(File file, String tableName) {
		try {
			TableServiceImp tI = new TableServiceImp();

			Stage tableStage = new Stage();
			tableStage.setWidth(400);
			tableStage.setHeight(500);
			tableStage.setTitle(tableName);
			tableStage.setResizable(true);

			final GridPane tableGridPane = new GridPane();

			// get columns of the chosen table
			List<Column> cols = tI.getColumnsTable(_DATABASEPATH, file.getName(), tableName);
			final TableView<List<StringProperty>> table = new TableView<>();

			final Label label = new Label(tableName);
			label.setFont(new Font("Arial", 20));

			table.setEditable(false);
			table.isResizable();
			table.prefHeightProperty().bind(tableStage.heightProperty());
			table.prefWidthProperty().bind(tableStage.widthProperty());
			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			// create the columns for the table that is shown
			for (int i = 0; i < cols.size(); i++) {
				TableColumn<List<StringProperty>, String> column = new TableColumn<>(cols.get(i).getName());
				final int j = i;
				column.setCellValueFactory(celldata -> celldata.getValue().get(j));
				table.getColumns().add(column);
			}

			// get the data of the table and show it
			final ObservableList<List<StringProperty>> data = FXCollections.observableArrayList();
			List<StringProperty> myData = new ArrayList<>();
			ResultSet result = getDataTable(_DATABASEPATH, file.getName(), tableName);

			while (result.next()) {
				for (int i = 1; i <= cols.size(); i++) {
					myData.add(new SimpleStringProperty(result.getString(i)));
				}
			}

			for (int j = 0; j < (myData.size() / cols.size()); j++) {
				data.add(j, myData.subList(j * cols.size(), j * cols.size() + cols.size()));
			}

			table.setItems(data);

			// use a grid pane to show the elements of the window
			tableGridPane.setHgap(6);
			tableGridPane.setVgap(6);
			tableGridPane.setPadding(new Insets(12, 12, 12, 12));
			tableGridPane.add(table, 0, 0);

			tableStage.setScene(new Scene(tableGridPane));
			tableStage.show();

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Method to get all the data of a table using the sql statement "select"
	 * 
	 * @param pPath			String the path of the database
	 * @param pDbName		String the name of the database
	 * @param pTableName	String the name of the table
	 * @return				data of table as result set
	 * @throws SQLException
	 */
	protected ResultSet getDataTable(final String pPath, final String pDbName, final String pTableName)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement stm = con.createStatement();
		ResultSet result = stm.executeQuery(Messages.SELECT + Messages.STAR + Messages.FROM + pTableName);
		return result;
	}
}