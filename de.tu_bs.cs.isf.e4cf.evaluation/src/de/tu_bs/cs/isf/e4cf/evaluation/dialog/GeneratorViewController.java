package de.tu_bs.cs.isf.e4cf.evaluation.dialog;

import java.io.File;
import java.nio.file.Paths;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.Granularity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import javafx.util.StringConverter;

public class GeneratorViewController {
	
	private GeneratorOptions options;
	@Inject private ServiceContainer services;
	
	public GeneratorOptions getOptions() {
		
		options = new GeneratorOptions();
		
		// Path
		options.outputRoot = Paths.get(textRootPath.getText());
		
		// Granularity
		RadioButton selectedRadioButton = (RadioButton) granularityGroup.getSelectedToggle();
		String toggleGroupId = selectedRadioButton.getId();
		switch (toggleGroupId) {
		case "radioGranularityClass":
			options.granularity = Granularity.CLASS;
			break;
		case "radioGranularityMethod":
			options.granularity = Granularity.METHOD;
			break;
		default: // statement
			options.granularity = Granularity.STATEMENT;
			break;
		}
		
		// Options
		try {
			options.variants = Integer.parseInt(textNumberVariants.getText());
		} catch (NumberFormatException e) {
			System.out.println("WARN! There was an issue parsing the mutation count, falling back to default value.");
			options.variants = 2;
		}
		
		options.crossoverPercentage = (int)Math.round(sliderCrossoverVariants.getValue());
		options.modificationRatioPercentage = (int)Math.round(sliderModificationRatio.getValue());
		options.variantChangeDegree = (int)Math.round(sliderChangeDegree.getValue());
		
		return options;
	}
	
	public void init() {
		toggleGroup();
		textRootPath.setText(services.workspaceFileSystem.getWorkspaceDirectory().getFile().toString() + "\\ 02 Trees");
	
		// restrict some text fields to numbers only
		restrictTextfieldToNumbers(textNumberVariants);
		
		// link sliders to text fields
		linkTextWithSlider(textCrossoverVariants, sliderCrossoverVariants);
		linkTextWithSlider(textModificationRatio, sliderModificationRatio);
		
		// change tick labels of variant change
		sliderChangeDegree.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n <= 1.0) return "Less";
                if (n >= 5.0) return "More";
                return "";
            }

            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "Less":
                        return 1d;
                    case "More":
                    	return 5d;
                    default:
                        return 3d;
                }
            }
        });
	}
	
	private void restrictTextfieldToNumbers(TextField field) {
		field.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	field.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
	}
	
	/** Links a text field to a slider, limits it's input to numbers and range according to the slider*/
	private void linkTextWithSlider(TextField field, Slider slider) {
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			  @Override
			  public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) 
			  {   
				String v = String.valueOf(newValue);
				double d = Double.parseDouble(v);
			    field.setText("" + Math.round(d));
			  }
		});
		
		field.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	String v = newValue;
		        if (!newValue.matches("\\d*")) {
		        	v = newValue.replaceAll("[^\\d]", "");
		        }
		        
		        if (!v.equals("")) {
			        double d = Math.min(Math.max(Double.parseDouble(v), slider.getMin()), slider.getMax()); 		
			        field.setText(""+Math.round(d));
			        slider.setValue(d);
		        }
		    }
		});
		
	}
	
	// ------ RootPath Settings ------
	
	@FXML
	public TextField textRootPath;
	@FXML
	public Button btnBrowse;
	
	@FXML
	public void browseFiles() {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(services.workspaceFileSystem.getWorkspaceDirectory().getFile().toFile());
		File selectedFile = directoryChooser.showDialog(null);
		textRootPath.setText(selectedFile.toString());
	}
	
	// ------ Granularity Settings ------
	
	@FXML
	public RadioButton radioGranularityClass;
	@FXML
	public RadioButton radioGranularityMethod;
	@FXML
	public RadioButton radioGranularityStatement;

	public ToggleGroup granularityGroup = new ToggleGroup();

	private void toggleGroup() {
		radioGranularityClass.setToggleGroup(granularityGroup);
		radioGranularityMethod.setToggleGroup(granularityGroup);
		radioGranularityStatement.setToggleGroup(granularityGroup);
		radioGranularityStatement.setSelected(true);
	}
	
	// ------ Generation Settings ------
	
	@FXML 
	public TextField textNumberVariants;
	
	@FXML
	public TextField textCrossoverVariants;
	@FXML
	public Slider sliderCrossoverVariants;
	
	@FXML
	public TextField textModificationRatio;
	@FXML
	public Slider sliderModificationRatio;
	
	@FXML
	public Slider sliderChangeDegree;
	
}
