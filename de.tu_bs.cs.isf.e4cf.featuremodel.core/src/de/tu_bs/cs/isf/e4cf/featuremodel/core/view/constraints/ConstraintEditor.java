package de.tu_bs.cs.isf.e4cf.featuremodel.core.view.constraints;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.event.MouseOverTextEvent;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import CrossTreeConstraints.AbstractConstraint;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.templates.AbstractDialog;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.JavaFXBuilder;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.controller.TabController;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.dialogs.FMESimpleNoticeDialog;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.constraints.elements.FXFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.model.parser.ConstraintParser;
import de.tu_bs.cs.isf.e4cf.featuremodel.model.parser.handler.ParserError;
import de.tu_bs.cs.isf.e4cf.featuremodel.model.stringtable.ConstraintGrammerKeywords;
import de.tu_bs.cs.isf.e4cf.featuremodel.model.util.ConstraintUtil;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

/**
 * An Editor for constraint with syntax highlighting 
 * @author Kamil Rosiak
 */
@Creatable
public class ConstraintEditor extends AbstractDialog {
	private static double ICON_COL_WIDTH = 40d;
	private Map<Integer, String> errorToolTips = new HashMap<>();

	private FeatureDiagram diagram;
	AbstractConstraint currentConstraint = null;
	

	
	private static final Pattern PATTERN = Pattern.compile(
			"(?<OPERATOR>" + ConstraintGrammerKeywords.KEYWORD_PATTERN + ")"
			+ "|(?<PAREN>" + ConstraintGrammerKeywords.PAREN_PATTERN + ")");

	private CodeArea codeArea;
	
	public ConstraintEditor() {
		super("ConstraintEditor", 800, 600);
		styleButtons();
		setIcon();
		createContent();
	}
	/**
	 * This method initializes the content of the constraint editor.
	 */
	private void createContent() {
		VBox layout = new VBox();
		TableView<FXFeature> table = createFeatureList();
		HBox opSelction = createOperatorSelection();
		CodeArea codeArea = createCodeArea();
		
		//auto layout for the parts
		ReadOnlyDoubleProperty heightProp = getScene().heightProperty();
		double opHeight = opSelction.getHeight();
		
		//initial call the its binded on the scene
		table.setPrefHeight(heightProp.add(- opHeight).divide(5).multiply(4).get());
		codeArea.setPrefHeight(heightProp.add(- opHeight).divide(5).get());
		
		getScene().heightProperty().addListener(e->{
			table.setPrefHeight(heightProp.add(- opHeight).divide(5).multiply(4).get());
			codeArea.setPrefHeight(heightProp.add(- opHeight).divide(5).get());
		});

		layout.getChildren().addAll(table,opSelction, codeArea);
		setCenter(layout);	
	}
	
	/**
	 * This method creates all buttons for the operators and layouts them
	 */
	private HBox createOperatorSelection() {
		HBox layout = new HBox();
	
		Button notOperator = JavaFXBuilder.createButton("NOT", e -> {
			insertText(" "+ConstraintGrammerKeywords.NOT_OP+" ");	
		});
		Button andOperator = JavaFXBuilder.createButton("AND", e -> {
			insertText(" "+ConstraintGrammerKeywords.AND_OP+" ");
		});
		Button orOperator = JavaFXBuilder.createButton("OR", e -> {
			insertText(" "+ConstraintGrammerKeywords.OR_OP+" ");
		});
		Button implOperator = JavaFXBuilder.createButton("IMPLICATION", e -> {
			insertText(" "+ConstraintGrammerKeywords.IMPL_OP+" ");
		});
		Button equalsOperator = JavaFXBuilder.createButton("EQUALS", e -> {
			insertText(" "+ConstraintGrammerKeywords.EQUALS_OP+" ");
		});
		Button leftBracketOperator = JavaFXBuilder.createButton("(", e -> {
			insertText(ConstraintGrammerKeywords.LEFT_BRACKET);
		});
		Button rightBracketOperator = JavaFXBuilder.createButton(")", e -> {
			insertText(ConstraintGrammerKeywords.RIGHT_BRACKET);
		});
		notOperator.prefWidthProperty().bind(getScene().widthProperty());
		andOperator.prefWidthProperty().bind(getScene().widthProperty());
		orOperator.prefWidthProperty().bind(getScene().widthProperty());
		implOperator.prefWidthProperty().bind(getScene().widthProperty());
		equalsOperator.prefWidthProperty().bind(getScene().widthProperty());
		leftBracketOperator.prefWidthProperty().bind(getScene().widthProperty());
		rightBracketOperator.prefWidthProperty().bind(getScene().widthProperty());

		layout.getChildren().addAll(notOperator, andOperator, orOperator, implOperator,equalsOperator,leftBracketOperator, rightBracketOperator);
		
		return layout;
	}
	
	
	/**
	 * This method creates the CodeArea and adds highlighting to it.
	 */
	private CodeArea createCodeArea() {
		codeArea = new CodeArea();
		/** stores the last position of courser in the code area **/
		
		setCSS(services.imageService.getURL(FDStringTable.BUNDLE_NAME, "css/constraint_editor.css"));
		codeArea.multiPlainChanges().successionEnds(Duration.ofMillis(100)).subscribe(event -> {
			codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
			computeSyntaxErrors(codeArea.getText());
		});
		
		Popup popup = new Popup();
		
		codeArea.setMouseOverTextDelay(Duration.ofMillis(500));
		final int indexShift = 1;
		final int popupShift = 10;
		codeArea.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_BEGIN, e -> {
	            int chIdx = e.getCharacterIndex();
	            if(errorToolTips.containsKey(chIdx + indexShift)) {
	            	popup.getContent().clear();
	            	popup.getContent().add(createPopupMsg(errorToolTips.get(chIdx + indexShift)));
	            	popup.show(codeArea,  e.getScreenPosition().getX() + popupShift, e.getScreenPosition().getY() + popupShift);
	            }
		         
	        });
		codeArea.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_END, e -> {
            popup.hide();
        });
		return codeArea;
	}
	
	/**
	 * This method computes syntax errors and creates the related error messages
	 */
	private void computeSyntaxErrors(String text) {
		ConstraintParser parser = new ConstraintParser();
		parser.parserConstraint(text);
		errorToolTips.clear();
		for(ParserError error : parser.getErrorListener().getErrorList()) {
			errorToolTips.put(error.getCharPositionInLine(), error.getMsg());
			int charPos = error.getCharPositionInLine() - 1;
			if(charPos < 0) {
				charPos = 0;
			}
			codeArea.setStyleClass(charPos , error.getCharPositionInLine() , "error");	
		}
	}
	
	/**
	 * Creates a styled label
	 */
	private Label createPopupMsg(String txt) {
        Label popupMsg = new Label(txt);
        popupMsg.setStyle(
                "-fx-background-color: #faf6bd;" +
                "-fx-text-fill: black;" +
                "-fx-padding: 5;" +
                "-fx-border-style: solid;");
        return popupMsg;
	}
	
	/**
	 * Compute the highlighting for the given text
	 */
	private StyleSpans<Collection<String>> computeHighlighting(String text) {
		Matcher matcher = PATTERN.matcher(text);
		int lastKwEnd = 0;
	
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		
		while(matcher.find()) {
			String styleClass = matcher.group("OPERATOR") != null ? "operator" :
								matcher.group("PAREN") != null ? "paren"
								: null;
			
			spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
			spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
			lastKwEnd = matcher.end();
		}
		
		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
		return spansBuilder.create();
	}
	
	public void editConstraint(AbstractConstraint constraint) {
		currentConstraint = constraint;
		codeArea.appendText(ConstraintUtil.createConstraintText(constraint));
		showStage();
	}
	
	/**
	 * This method creates the list with features that are currently in the feature diagram
	 */
	private TableView<FXFeature> createFeatureList() {
		TabController tabController = ContextInjectionFactory.make(TabController.class, EclipseContextFactory.create());
		this.diagram = tabController.getCurrentFeatureDiagram();
		ObservableList<FXFeature> featureList = FXCollections.observableArrayList();
		for(IFeature feature : this.diagram.getAllFeatures()) {
			//TODO: add different icons for different feature types
			featureList.add(new FXFeature(feature,services.imageService.getURL(FDStringTable.BUNDLE_NAME, "icon/feature_blue_24.png")));
		}

		TableView<FXFeature> table = new TableView<FXFeature>(featureList);
        TableColumn<FXFeature, ImageView> iconCol = new TableColumn<FXFeature, ImageView>();
        iconCol.setCellValueFactory(new PropertyValueFactory<FXFeature, ImageView>("image"));
 
        
		TableColumn<FXFeature, String> featureNameCol = new TableColumn<FXFeature, String> ("Feature");
		featureNameCol.setCellValueFactory(new PropertyValueFactory<>("featureName"));
		
		TableColumn<FXFeature, String> descriptionCol = new TableColumn<FXFeature, String> ("Description");
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("featureDescription"));

	    iconCol.setPrefWidth(ICON_COL_WIDTH);

		featureNameCol.prefWidthProperty().bind(table.widthProperty().add(-ICON_COL_WIDTH).divide(3));
		descriptionCol.prefWidthProperty().bind(table.widthProperty().add(-ICON_COL_WIDTH).divide(3).multiply(2));
		
		table.getColumns().add(iconCol);
		table.getColumns().add(featureNameCol);
		table.getColumns().add(descriptionCol);
		
		table.setOnMouseClicked(e -> {
			if(e.getClickCount() == 2 && e.getButton().equals(MouseButton.PRIMARY)) {
				insertText(" "+ table.getSelectionModel().getSelectedItem().getFeatureName()+" ");
			}	
		});
		
		return table;
	}
	/**
	 * Setting the icon of the abstract dialog
	 */
	private void setIcon() {
		setSceneIcon(services.imageService.getURL(FDStringTable.BUNDLE_NAME, "icon/feature_model_editor_16.png"));	
	}
	
	/**
	 * This method styles and adds behavior to the right button. 
	 */
	private void styleButtons() {
		getRightButton().setText("Add Constraint");
		getRightButton().setOnMouseClicked(e-> {
			try {
				if(errorToolTips.isEmpty()) {
					ConstraintParser parser = new ConstraintParser();
					AbstractConstraint constraint = parser.parserConstraint(codeArea.getText());
					//remove edited constraint
					if(currentConstraint != null) {
						diagram.getConstraints().remove(currentConstraint);
						currentConstraint = null;
					} 
					
					diagram.getConstraints().add(constraint);

					codeArea.clear();
					System.out.println("Constraint added to " + diagram.getName());
					services.partService.showPart(FDStringTable.CONSTRAINT_VIEW);
					services.eventBroker.send(FDEventTable.SHOW_CONSTRAINT_EVENT, diagram);
				} else {
					new FMESimpleNoticeDialog("Syntax Error", "Constriant has incorrect syntax");
				}


			} catch (Exception ed) {
				ed.printStackTrace();
			}
		});
	}
	
	/**
	 * This method inserts the given text to the last courser position.
	 */
	private void insertText(String text) {
		codeArea.insertText(codeArea.caretPositionProperty().getValue(), text);
	}
}
